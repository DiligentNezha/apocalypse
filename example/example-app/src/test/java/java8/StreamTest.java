package java8;

//import org.junit.Test;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/19
 */
public class StreamTest {

    /**
     * forEach 用来迭代流中的每个数据
     */
//    @Test
    public void forEachTest() {
        //使用 forEach 输出 10 个随机数
        ThreadLocalRandom.current()
                .ints()
                .limit(10)
                .forEach(System.out::println);
    }

    /**
     * map 用于映射每个元素到对应的结果
     */
//    @Test
    public void mapTest() {
        Stream<Integer> integerStream = Stream.of(0, 1, 2, 3, 4, 5);
        List<Integer> collect = integerStream
                //输出集合中元素的 2 的次方
                .map(integer -> 2 << (integer - 1))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 过滤元素
     */
//    @Test
    public void filterTest() {
        Stream<Integer> integerStream = Stream.of(0, 1, 2, 3, 4, 5);
        List<Integer> collect = integerStream
                //过滤所有的偶数
                .filter(value -> (value & 1) == 0)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 获取指定数量
     */
//    @Test
    public void limitTest() {
        ThreadLocalRandom
                .current()
                .ints()
                //获取 10 条数据
                .limit(10)
                .forEachOrdered(System.out::println);
    }

    /**
     * 排序
     */
//    @Test
    public void sortedTest() {
        ThreadLocalRandom
                .current()
                .ints()
                //获取 10 条数据
                .limit(10)
                .sorted()
                .forEach(System.out::println);
    }

//    @Test
    public void summaryTest() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        IntSummaryStatistics intSummaryStatistics = integerStream.mapToInt(value -> value).summaryStatistics();
        long count = intSummaryStatistics.getCount();
        int max = intSummaryStatistics.getMax();
        int min = intSummaryStatistics.getMin();
        double average = intSummaryStatistics.getAverage();
        long sum = intSummaryStatistics.getSum();

        System.out.println("count:" + count);
        System.out.println("max:" + max);
        System.out.println("min:" + min);
        System.out.println("sum:" + sum);
        System.out.println("average:" + average);
    }
}
