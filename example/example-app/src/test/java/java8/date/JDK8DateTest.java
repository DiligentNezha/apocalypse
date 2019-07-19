package java8.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/25
 */
@Slf4j
public class JDK8DateTest {

    /**
     * Instant 代替 Date
     */
    @Test
    public void instantTest() {
        Instant now = Instant.now();
        System.out.println(now);
    }

    @Test
    public void durationTest() {
        Duration duration = Duration.of(10, ChronoUnit.DAYS);
        System.out.println(duration.toMinutes());
    }

    @Test
    public void localDateTest() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        LocalDate localDate = LocalDate.ofYearDay(now.getYear(), now.getDayOfYear());
        System.out.println(localDate);
        System.out.println(LocalDate.MIN.toEpochDay());
        System.out.println(LocalDate.MAX.toEpochDay());
    }

    @Test
    public void localTimeTest() {
        LocalTime now = LocalTime.now();
        System.out.println(now);
    }

    @Test
    public void localDateTimeTest() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

    @Test
    public void zonedDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock);
        Clock clock1 = Clock.systemUTC();
        System.out.println(clock1);
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(availableZoneIds);
    }

    @Test
    public void monthTest() {
        System.out.println(Stream.of(Month.values()).collect(Collectors.toList()));
    }

    @Test
    public void dayOfWeekTest() {
        System.out.println(Stream.of(DayOfWeek.values()).collect(Collectors.toList()));
    }

    @Test
    public void monthDayTest() {
        MonthDay now = MonthDay.now();
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getMonth());
    }

    @Test
    public void yearMonthTest() {
        YearMonth now = YearMonth.now();
        System.out.println(now.atEndOfMonth());
        System.out.println(now.getMonth());
    }

    @Test
    public void periodTest() {
        Period period = Period.between(LocalDate.parse("2019-07-01"), LocalDate.parse("2019-08-31"));
        System.out.println(period);
    }

}
