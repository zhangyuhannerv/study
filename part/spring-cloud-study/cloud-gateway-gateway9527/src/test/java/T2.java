import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

class T2 {
    /**
     * gateway,predicate的after时间获取方式
     */
    @Test
    void getPredicateTimeStr() {
        ZonedDateTime now = ZonedDateTime.now();// 默认时区
        System.out.println(now);// 2022-06-30T11:27:12.042+08:00[Asia/Shanghai]
    }
}
