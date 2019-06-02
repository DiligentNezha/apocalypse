import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.apocalypse.example.ExampleApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;

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
