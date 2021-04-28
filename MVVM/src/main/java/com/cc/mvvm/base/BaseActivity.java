package com.cc.mvvm.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by guoshichao on 2021/4/28
 */
public class BaseActivity extends AppCompatActivity {

    protected void onCreate(Bundle args) {
        if (Build.VERSION.SDK_INT == 26 && this.isTranslucentOrFloating()) {
            boolean result = this.fixOrientation();
            Log.i("BaseActivity", "onCreate fixOrientation when Oreo, result = " + result);
        }

        super.onCreate(args);
        Log.v("BaseActivity", this.getClass().getName() + " onCreate()");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("BaseActivity", this.getClass().getName() + " onSaveInstanceState()");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("BaseActivity", this.getClass().getName() + " onRestoreInstanceState()");
    }

    protected void onStart() {
        super.onStart();
        Log.v("BaseActivity", this.getClass().getName() + " onStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.v("BaseActivity", this.getClass().getName() + " onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.v("BaseActivity", this.getClass().getName() + " onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.v("BaseActivity", this.getClass().getName() + " onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.v("BaseActivity", this.getClass().getName() + " onCreate()");
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
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("BaseActivity", this.getClass().getName() + " onNewIntent()");
    }

}
