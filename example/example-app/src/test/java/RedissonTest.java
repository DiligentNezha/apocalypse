import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.apocalypse.example.ExampleApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void longAdderTest() throws Exception {
        String keyLongAdder = "longAdderTest";
        RLongAdder longAdder = redissonClient.getLongAdder(keyLongAdder);
        longAdder.reset();
        log.info("init Value【{}】", longAdder.sumAsync().get());
        CountDownLatch cdl = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            ThreadUtil.execute(() -> {
                longAdder.increment();
                cdl.countDown();
                try {
                    log.info("currentValue 【{}】", longAdder.sumAsync().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
        cdl.await();
        log.info("expectValue【{}】;finalValue 【{}】", 1000, longAdder.sumAsync().get());
    }

    @Test
    public void atomicLongCompareAndSetTest() throws InterruptedException {
        String keyAtomicLongCompareAndSet = "atomicLongCompareAndSetTest";
        RAtomicLong atomicLong = redissonClient.getAtomicLong(keyAtomicLongCompareAndSet);
        atomicLong.set(100);
        log.info("init Value【{}】", atomicLong.get());
        CountDownLatch cdl = new CountDownLatch(120);
        for (int i = 0; i < 120; i++) {
            ThreadUtil.execAsync(() -> {
                long currentValue = atomicLong.decrementAndGet();
                log.info("currentValue 【{}】", currentValue);
                if (currentValue < 0) {
                    currentValue = atomicLong.incrementAndGet();
                    atomicLong.compareAndSet(0, 0);
                } else {
                    atomicLong.compareAndSet(currentValue, currentValue);
                }
                cdl.countDown();
            });
        }
        cdl.await();
        log.info("finalValue 【{}】", atomicLong.get());
    }

    @Test
    public void atomicLongAsyncTest() throws InterruptedException {
        String keyAtomicLongAsync = "atomicLongAsyncTest";
        RAtomicLong atomicLong = redissonClient.getAtomicLong(keyAtomicLongAsync);
        atomicLong.set(100);
        log.info("init Value【{}】", atomicLong.get());
        CountDownLatch cdl = new CountDownLatch(120);
        for (int i = 0; i < 120; i++) {
            ThreadUtil.execAsync(() -> {
                RFuture<Long> longRFuture = atomicLong.decrementAndGetAsync();
                longRFuture.handleAsync(new BiFunction<Long, Throwable, Object>() {
                    @Override
                    public Object apply(Long currentValue, Throwable throwable) {
                        log.info("currentValue 【{}】", currentValue);
                        if (currentValue < 0) {
                            //减多了之后补偿，最终只有100个线程可以减成功，剩下的20个线程减失败，通过补偿，最终结果为0
                            long afterIncrementValue = atomicLong.incrementAndGet();
                            currentValue = afterIncrementValue;
                            log.info("afterIncrementValue 【{}】", afterIncrementValue);
                        }
                        return currentValue;
                    }
                });
                cdl.countDown();
            });
        }
        cdl.await();
        log.info("finalValue 【{}】", atomicLong.get());
    }

    @Test
    public void atomicLongTest() throws InterruptedException {
        String keyAtomicLong = "atomicLongTest";
        RAtomicLong atomicLong = redissonClient.getAtomicLong(keyAtomicLong);
        atomicLong.set(100);
        log.info("init Value【{}】", atomicLong.get());
        CountDownLatch cdl = new CountDownLatch(120);
        for (int i = 0; i < 120; i++) {
            ThreadUtil.execAsync(() -> {
                long currentValue = atomicLong.decrementAndGet();
                log.info("currentValue 【{}】", currentValue);
                if (currentValue < 0) {
                    //减多了之后补偿，最终只有100个线程可以减成功，剩下的20个线程减失败，通过补偿，最终结果为0
                    long afterIncrementValue = atomicLong.incrementAndGet();
                    log.info("afterIncrementValue 【{}】", afterIncrementValue);
                }
                cdl.countDown();
            });
        }
        cdl.await();
        log.info("finalValue 【{}】", atomicLong.get());
    }

    @Test
    public void fairLockTest() {
        String keyFairLock = "fairLockTest";
        String keyAtomicLong = "atomicLongTest";
        RAtomicDouble atomicDouble = redissonClient.getAtomicDouble(keyAtomicLong);
        //设置初始值为20
        BigDecimal initValue = NumberUtil.toBigDecimal("200").setScale(2, BigDecimal.ROUND_HALF_UP);
        atomicDouble.set(initValue.doubleValue());
        RLock fairLock = redissonClient.getFairLock(keyFairLock);
        AtomicReference<Double> sum = new AtomicReference<>((double) 0);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            ThreadUtil.execute(() -> {
                if (!fairLock.isLocked()) {
                    if (fairLock.tryLock()) {
                        log.info("get lock success【{}】", finalI);

                        double randomDouble = RandomUtil.randomDouble(5, 15);
                        BigDecimal decrement = NumberUtil.toBigDecimal(randomDouble).setScale(2,
                                BigDecimal.ROUND_HALF_UP);

                        sum.updateAndGet(v -> v + decrement.doubleValue());
                        initValue.subtract(decrement);

                        log.info("decrement【{}】", decrement.negate());

                        double currentValue = atomicDouble.addAndGet(decrement.negate().doubleValue());
                        log.info("current value【{}】", BigDecimal.valueOf(currentValue).setScale(2, BigDecimal.ROUND_HALF_UP));
                        fairLock.unlock();
                    } else {
                        log.warn("get lock fail【{}】", finalI);
                    }
                } else {
                    log.info("locking");
                }
            });
            //休眠5毫秒，不然获取锁成功的机会很少，睡眠时间越长，获取锁成功的机会就越多
            ThreadUtil.sleep(5);
        }
        log.info("last value【{}】",
                initValue.subtract(BigDecimal.valueOf(sum.get()).setScale(2,BigDecimal.ROUND_HALF_UP)).doubleValue());
        atomicDouble.delete();
    }
}
