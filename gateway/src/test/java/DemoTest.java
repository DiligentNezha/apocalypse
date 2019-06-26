import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import org.junit.Test;

public class DemoTest {
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            ThreadUtil.execute(() -> {
                System.out.println(HttpUtil.createGet("http://localhost:51001/aliyun/product/ecs").execute().body());
            });
        }

    }
}
