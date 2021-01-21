package java8.date;

import cn.hutool.core.date.*;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.StringHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.alibaba.druid.pool.DruidDataSource;
import com.apocalypse.common.util.misc.WeekUtil;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/8
 */
public class DateUtilTest {

    private DataSource dataSource;

    @Test
    public void dateUtilTest() {
        DateTime now = DateUtil.date();

        System.out.println(DateUtil.beginOfDay(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));
        System.out.println(DateUtil.endOfDay(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));

        System.out.println(DateUtil.beginOfWeek(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));
        System.out.println(DateUtil.endOfWeek(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));

        System.out.println(DateUtil.beginOfMonth(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));
        System.out.println(DateUtil.endOfMonth(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));

        System.out.println(DateUtil.beginOfYear(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));
        System.out.println(DateUtil.endOfYear(now).toString(DatePattern.NORM_DATETIME_MS_PATTERN));

        System.out.println(DateUtil.month(now));
        System.out.println(DateUtil.monthEnum(now));

        System.out.println(DateUtil.weekOfYear(now));
    }

    @Before
    public void setUp() throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/apocalypse_example?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai";
        String userName = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driver);
        dataSource = druidDataSource;
    }

    /**
     * 测试所有的天
     * @throws SQLException
     */
    @Test
    public void dateFormatAllDayTest() throws SQLException {
        for (int year = 1980; year < 2050; year++) {
            DateTime firstDayOfYear = DateUtil.parseDate(year + "-01-01");
            DateTime lastDayOfYear = DateUtil.parseDate(year + "-12-31");
            DateRange dates = new DateRange(firstDayOfYear, lastDayOfYear, DateField.DAY_OF_YEAR);
            for (DateTime date : dates) {
                String hutoolWeek = date.year() + "-" + String.format("%02d", date.weekOfYear());
                String weekOfYear = WeekUtil.weekOfYear(date);
                String weekRange = WeekUtil.weekRange(date);
                Connection connection = dataSource.getConnection();
                String dbWeek = SqlExecutor.query(connection, "select date_format('" + date.toDateStr() + "', '%Y-%u')", new StringHandler());
                connection.close();
                System.out.println(StrUtil.format("java8.date【{}】;hutool week【{}】;weekOfYear【{}】;weekRange【{}】;db week【{}】;hutoolWeek equals dbWeek【{}】;weekOfYear equals dbWeek【{}】", date.toDateStr(), hutoolWeek, weekOfYear, weekRange, dbWeek, hutoolWeek.equals(dbWeek), weekOfYear.equals(dbWeek)));
            }
        }
    }

    /**
     * 测试跨年部分
     * @throws SQLException
     */
    @Test
    public void dateFormatCrossYearTest() throws SQLException {
        for (int i = 1980; i <= 2050; i++) {
            for (int j = 20; j < 31; j++) {
                DateTime date = DateUtil.parseDate(i + "-12" + "-" + j);
                String hutoolWeek = date.year() + "-" + String.format("%02d", date.weekOfYear());
                String weekOfYear = WeekUtil.weekOfYear(date);
                String weekRange = WeekUtil.weekRange(date);
                Connection connection = dataSource.getConnection();
                String dbWeek = SqlExecutor.query(connection, "select date_format('" + date.toDateStr() + "', '%Y-%u')", new StringHandler());
                connection.close();
                System.out.println(StrUtil.format("java8.date【{}】;hutool week【{}】;weekOfYear【{}】;weekRange【{}】;db week【{}】;hutoolWeek equals dbWeek【{}】;weekOfYear equals dbWeek【{}】", date.toDateStr(), hutoolWeek, weekOfYear, weekRange, dbWeek, hutoolWeek.equals(dbWeek), weekOfYear.equals(dbWeek)));
            }
            i++;
            for (int j = 1; j < 10; j++) {
                DateTime date = DateUtil.parseDate(i + "-01" + "-" + j);
                String hutoolWeek = date.year() + "-" + String.format("%02d", date.weekOfYear());
                String weekOfYear = WeekUtil.weekOfYear(date);
                String weekRange = WeekUtil.weekRange(date);
                Connection connection = dataSource.getConnection();
                String dbWeek = SqlExecutor.query(connection, "select date_format('" + date.toDateStr() + "', '%Y-%u')", new StringHandler());
                connection.close();
                System.out.println(StrUtil.format("java8.date【{}】;hutool week【{}】;weekOfYear【{}】;weekRange【{}】;db week【{}】;hutoolWeek equals dbWeek【{}】;weekOfYear equals dbWeek【{}】", date.toDateStr(), hutoolWeek, weekOfYear, weekRange, dbWeek, hutoolWeek.equals(dbWeek), weekOfYear.equals(dbWeek)));
            }
        }
    }

}
