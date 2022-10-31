package love.isuper.core.utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

/**
 * Created by shichao on 2022/5/16.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public final class AppInfo {

    private static Application application;
    private static boolean isDebug;
    private static Gson gson;
    private static Handler mainHandler;

    private AppInfo() {
    }

    public static void init(Application app) {
        AppInfo.application = app;
        isDebug = application.getApplicationInfo() != null &&
                (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        gson = new Gson();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static Application getApplication() {
        return application;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void exitApplication() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static Gson getGson() {
        return gson;
    }

    public static void postUI(Runnable runnable) {
        mainHandler.post(runnable);
    }

    public static void postDelayedUI(long delayMillis, Runnable runnable) {
        mainHandler.postDelayed(runnable, delayMillis);
    }

}
