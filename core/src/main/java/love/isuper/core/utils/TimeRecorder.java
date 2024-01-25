package love.isuper.core.utils;

import android.os.SystemClock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  author : guoshichao
 *  date : 2024/1/25 11:03
 *  description : 时间记录器，用于计算时间差
 */
public class TimeRecorder {

    private static final Map<String, Long> RECORDS = new ConcurrentHashMap<>();
    private static final Map<String, Long> INTERVALS = new ConcurrentHashMap<>();

    /**
     * 开始计时
     * @param tag 标识符
     */
    public static void start(String tag) {
        if (tag != null) {
            RECORDS.put(tag, SystemClock.elapsedRealtime());
        }
    }

    /**
     * 停止计时
     * @param tag 标识符
     * @return 距start操作的时间差（毫秒）
     */
    public static long stop(String tag) {
        long timeDiff = -1L;
        if (tag != null) {
            Long start = RECORDS.get(tag);
            if (start != null) {
                timeDiff = SystemClock.elapsedRealtime() - start;
                RECORDS.remove(tag);
            }
        }
        return timeDiff;
    }



    /**
     * 检查是否为快速事件，间隔=500ms
     * @param tag 事件tag
     */
    public static boolean isFastEvent500(String tag) {
        return isFastEvent(tag, 500);
    }

    /**
     * 检查是否为快速事件，间隔=1000ms
     * @param tag 事件tag
     */
    public static boolean isFastEvent1000(String tag) {
        return isFastEvent(tag, 1000);
    }

    /**
     * 检查是否为快速事件
     * @param tag 事件tag
     * @param intervalMillis 间隔时间（毫秒）
     */
    public static boolean isFastEvent(String tag, long intervalMillis) {
        if (tag != null) {
            Long currentTimeMillis = SystemClock.elapsedRealtime();
            Long lastCheckTimeMillis = INTERVALS.get(tag);
            INTERVALS.put(tag, currentTimeMillis);
            return lastCheckTimeMillis != null && currentTimeMillis - lastCheckTimeMillis <= intervalMillis;
        }
        return false;
    }

}
