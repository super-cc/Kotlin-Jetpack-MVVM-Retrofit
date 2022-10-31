package love.isuper.core.ext

import android.util.Log

/**
 * Created by shichao on 2022/5/23.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */

fun String.logV(TAG: String, trace: Boolean = false) {
    if (trace) {
        Log.v(TAG, "trace = ${Log.getStackTraceString(Throwable())} \n msg = $this")
    } else {
        Log.v(TAG, this)
    }
}

fun String.logD(TAG: String, trace: Boolean = false) {
    if (trace) {
        Log.d(TAG, "trace = ${Log.getStackTraceString(Throwable())} \n msg = $this")
    } else {
        Log.d(TAG, this)
    }
}

fun String.logI(TAG: String, trace: Boolean = false) {
    if (trace) {
        Log.i(TAG, "trace = ${Log.getStackTraceString(Throwable())} \n msg = $this")
    } else {
        Log.i(TAG, this)
    }
}

fun String.logW(TAG: String, trace: Boolean = false) {
    if (trace) {
        Log.w(TAG, "trace = ${Log.getStackTraceString(Throwable())} \n msg = $this")
    } else {
        Log.w(TAG, this)
    }
}

fun String.logE(TAG: String, trace: Boolean = false) {
    if (trace) {
        Log.e(TAG, "trace = ${Log.getStackTraceString(Throwable())} \n msg = $this")
    } else {
        Log.e(TAG, this)
    }
}
