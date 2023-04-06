package love.isuper.app

import android.app.Application
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import love.isuper.core.utils.AppInfo

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

        AppInfo.init(instance)
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, R.color.main) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"))  //指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

}