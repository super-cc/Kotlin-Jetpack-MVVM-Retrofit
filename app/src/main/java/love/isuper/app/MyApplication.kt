package love.isuper.app

import android.app.Application

/**
 * Created by 郭士超 on 2022/11/6 16:48
 * Describe：
 */
class MyApplication: Application() {

    companion object {
        lateinit var instance : MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}