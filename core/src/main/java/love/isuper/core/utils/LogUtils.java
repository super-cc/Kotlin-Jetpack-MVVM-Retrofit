package love.isuper.core.utils;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Created by shichao on 2022/5/16.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public final class LogUtils {

    private static final String TAG = "LOVE";

    private LogUtils() {}

    private static boolean isShowLog() {
        return AppInfo.isDebug();
    }

    public static void v(@NonNull Object log){
        v(TAG, log);
    }

    public static void v(@NonNull Object... log){
        v(TAG, log);
    }

    public static void v(String tag, @NonNull Object... log){
        StringBuilder sb = new StringBuilder();
        for (Object s : log) {
            sb.append(s);
        }
        v(tag, sb.toString());
    }

    private static void v(String tag, @NonNull String log){
        if (!isShowLog()) {
            return;
        }
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多
        int maxStrLength = 2001 - tag.length();
        while (log.length() > maxStrLength) {
            Log.v(tag, log.substring(0, maxStrLength));
            log = log.substring(maxStrLength);
        }
        //剩余部分
        Log.v(tag, log);
    }

    public static void d(@NonNull Object log){
        d(TAG, log);
    }

    public static void d(@NonNull Object... log){
        d(TAG, log);
    }

    public static void d(String tag, @NonNull Object... log){
        StringBuilder sb = new StringBuilder();
        for (Object s : log) {
            sb.append(s);
        }
        d(tag, sb.toString());
    }

    private static void d(String tag, @NonNull String log){
        if (!isShowLog()) {
            return;
        }
        int maxStrLength = 2001 - tag.length();
        while (log.length() > maxStrLength) {
            Log.d(tag, log.substring(0, maxStrLength));
            log = log.substring(maxStrLength);
        }
        Log.d(tag, log);
    }

    public static void i(@NonNull Object log){
        i(TAG, log);
    }

    public static void i(@NonNull Object... log){
        i(TAG, log);
    }

    public static void i(String tag, @NonNull Object... log){
        StringBuilder sb = new StringBuilder();
        for (Object s : log) {
            sb.append(s);
        }
        i(tag, sb.toString());
    }

    private static void i(String tag, @NonNull String log){
        if (!isShowLog()) {
            return;
        }
        int maxStrLength = 2001 - tag.length();
        while (log.length() > maxStrLength) {
            Log.i(tag, log.substring(0, maxStrLength));
            log = log.substring(maxStrLength);
        }
        Log.i(tag, log);
    }

    public static void w(@NonNull Object log){
        w(TAG, log);
    }

    public static void w(@NonNull Object... log){
        w(TAG, log);
    }

    public static void w(String tag, @NonNull Object... log){
        StringBuilder sb = new StringBuilder();
        for (Object s : log) {
            sb.append(s);
        }
        w(tag, sb.toString());
    }

    private static void w(String tag, @NonNull String log){
        if (!isShowLog()) {
            return;
        }
        int maxStrLength = 2001 - tag.length();
        while (log.length() > maxStrLength) {
            Log.w(tag, log.substring(0, maxStrLength));
            log = log.substring(maxStrLength);
        }
        Log.w(tag, log);
    }

    public static void e(@NonNull Object log){
        e(TAG, log);
    }

    public static void e(@NonNull Object... log){
        e(TAG, log);
    }

    public static void e(String tag, @NonNull Object... log){
        StringBuilder sb = new StringBuilder();
        for (Object s : log) {
            sb.append(s);
        }
        e(tag, sb.toString());
    }

    private static void e(String tag, @NonNull String log){
        if (!isShowLog()) {
            return;
        }
        int maxStrLength = 2001 - tag.length();
        while (log.length() > maxStrLength) {
            Log.e(tag, log.substring(0, maxStrLength));
            log = log.substring(maxStrLength);
        }
        Log.e(tag, log);
    }

}
