package love.isuper.core.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * Created by shichao on 2022/7/7.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public final class AppMethod {

    public static String getString(@StringRes int resId) {
        return AppInfo.getApplication().getString(resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId) {
        return AppInfo.getApplication().getDrawable(resId);
    }

    public static int getColor(@ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return AppInfo.getApplication().getColor(resId);
        } else {
            return AppInfo.getApplication().getResources().getColor(resId);
        }
    }

}
