package com.apocalypse.cms.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.cms.model.single.AwardedHistory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2021/3/7
 */
public class DoubleHappy {
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(8);
        RedissonClient redissonClient = Redisson.create(config);
        RQueue<Object> queue = redissonClient.getQueue("double:history");
        Snowflake snowflake = IdUtil.getSnowflake(1, 2);

        for (int year = 2003; year <= 2021; year++) {
            Document document = Jsoup.connect(StrUtil.format("https://chart.cp.360.cn/kaijiang/ssq?spanType=3&span={}001_{}154", year, year)).get();
            Element dataTab = document.getElementById("data-tab");
            Elements trs = dataTab.getElementsByTag("tr");
            for (int i = trs.size() - 1; i >= 0 ; i--) {
                Element tr = trs.get(i);
                Elements td = tr.getElementsByTag("td");
                String issue = td.get(0).text();
                String awardedDateStr = td.get(1).text();
                LocalDate revealDate = LocalDate.parse(awardedDateStr.substring(0, 10));
                String dayOfWeek = awardedDateStr.substring(11, 12);
                Elements redBalls = td.get(2).getElementsByTag("span");
                Integer redOne = Integer.valueOf(redBalls.get(0).text());
                Integer redTwo = Integer.valueOf(redBalls.get(1).text());
                Integer redThree = Integer.valueOf(redBalls.get(2).text());
                Integer redFour = Integer.valueOf(redBalls.get(3).text());
                Integer redFive = Integer.valueOf(redBalls.get(4).text());
                Integer redSix = Integer.valueOf(redBalls.get(5).text());
                Integer blue = Integer.valueOf(td.get(3).text());
                LocalDateTime now = LocalDateTime.now();
                StringBuilder format = new StringBuilder();
                format.append(String.format("%2d ", redOne));
                format.append(String.format("%2d ", redTwo));
                format.append(String.format("%2d ", redThree));
                format.append(String.format("%2d ", redFour));
                format.append(String.format("%2d ", redFive));
                format.append(String.format("%2d+", redSix));
                format.append(String.format("%2d", blue));
                AwardedHistory awardedHistory = new AwardedHistory()
                        .setId(snowflake.nextId())
                        .setIssue(Integer.valueOf(issue))
                        .setDayOfWeek(dayOfWeek)
                        .setRevealDate(revealDate)
                        .setRedOne(redOne)
                        .setRedTwo(redTwo)
                        .setRedThree(redThree)
                        .setRedFour(redFour)
                        .setRedFive(redFive)
                        .setRedSix(redSix)
                        .setBlue(blue)
                        .setFormat(format.toString())
                        .setIsDeleted(false)
                        .setCreateIdentityId(0L)
                        .setUpdateIdentityId(0L)
                        .setCreateAccountId(0L)
                        .setUpdateAccountId(0L)
                        .setCreateAgentIdentityId(0L)
                        .setUpdateAgentIdentityId(0L)
                        .setCreateTime(now)
                        .setUpdateTime(now);
                queue.add(awardedHistory);
                System.out.println(awardedHistory);
            }
            Thread.sleep(10000);
        }
    }
}
