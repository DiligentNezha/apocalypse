import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/25
 */
@Slf4j
public class jdk8DateTest {

    /**
     * Instant 代替 Date
     */
    @Test
    public void instantTest() {
        Instant now = Instant.now();
        log.info("now:{}", now);
    }

    /**
     * LocalDateTime 代替 Calendar
     */
    @Test
    public void localDateTimeTest() {
        LocalDateTime now = LocalDateTime.now();
        log.info("now:{}", now);
    }

}
