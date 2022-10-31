package love.isuper.core.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * Created by shichao on 2022/5/18.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public final class ToastUtils {

    private static Toast toast;
    private static String toastStr;
    private static long time;
    public static Handler mHandler = new Handler(Looper.getMainLooper());

    private ToastUtils() {}

    public static void showToast(@StringRes int strRes) {
        showToast(strRes, Toast.LENGTH_SHORT);
    }

    public static void showToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(@StringRes int strRes) {
        showToast(strRes, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(@StringRes int strRes) {
        showToast(strRes, Toast.LENGTH_LONG);
    }

    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    public static void showToast(@StringRes final int strRes, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(strRes, duration);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(strRes, duration);
                }
            });
        }
    }

    public static void showToast(final CharSequence text, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(text, duration);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(text, duration);
                }
            });
        }
    }

    private static void show(@StringRes int strRes, int duration) {
        show(AppInfo.getApplication().getResources().getText(strRes), duration);
    }

    private static void show(CharSequence text, int duration) {
        long newTime = System.currentTimeMillis();
        if (newTime - time < 3_000 && TextUtils.equals(toastStr, text.toString())) {
            return;
        }
        toastStr = text.toString();
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(AppInfo.getApplication(), text, duration);
        toast.show();
        time = newTime;
    }

}
