package love.isuper.core.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by guoshichao on 2021/4/28
 */
public class BaseLogActivity extends AppCompatActivity {

    public static final String _TAG = "BaseActivity";

    @CallSuper
    protected void onCreate(Bundle args) {
        if (Build.VERSION.SDK_INT == 26 && this.isTranslucentOrFloating()) {
            boolean result = this.fixOrientation();
            Log.i(_TAG, "onCreate fixOrientation when Oreo, result = " + result);
        }

        super.onCreate(args);
        Log.v(_TAG,  this.getClass().getSimpleName() + " onCreate() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(_TAG,  this.getClass().getSimpleName() + " onSaveInstanceState() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(_TAG,  this.getClass().getSimpleName() + " onRestoreInstanceState() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    protected void onStart() {
        super.onStart();
        Log.v(_TAG,  this.getClass().getSimpleName() + " onStart() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    protected void onResume() {
        super.onResume();
        Log.v(_TAG,  this.getClass().getSimpleName() + " onResume() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    protected void onPause() {
        super.onPause();
        Log.v(_TAG,  this.getClass().getSimpleName() + " onPause() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    protected void onStop() {
        super.onStop();
        Log.v(_TAG,  this.getClass().getSimpleName() + " onStop() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        Log.v(_TAG,  this.getClass().getSimpleName() + " onDestroy() " + this.getClass().getName() + " " + this);
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;

        try {
            int[] styleableRes = (int[])((int[])Class.forName("com.android.internal.R$styleable").getField("Window").get((Object)null));
            TypedArray ta = this.obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (Boolean)m.invoke((Object)null, ta);
            m.setAccessible(false);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo)field.get(this);
            o.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
            field.setAccessible(false);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    @CallSuper
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v(_TAG,  this.getClass().getSimpleName() + " onNewIntent() " + this.getClass().getName() + " " + this);
    }

}
