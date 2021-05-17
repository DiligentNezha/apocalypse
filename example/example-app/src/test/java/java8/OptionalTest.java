package java8;

//import org.junit.Test;

import java.util.Optional;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/19
 */
public class OptionalTest {

//    @Test
    public void emptyTest() {
        Optional<String> empty = Optional.empty();
        System.out.println(empty.isPresent());
    }

//    @Test
    public void ofTest() {
        Optional<Integer> integerOptional = Optional.of(1);
        integerOptional.ifPresent(value -> System.out.println(value));
    }
}
