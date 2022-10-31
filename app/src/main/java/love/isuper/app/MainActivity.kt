package love.isuper.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import love.isuper.core.utils.AppInfo
import love.isuper.core.view.CustomTitleBar

class MainActivity : AppCompatActivity() {

    lateinit var titleBar: CustomTitleBar
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppInfo.init(application)
        initRefreshLayout()

        titleBar = findViewById(R.id.title_bar)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        init()

        titleBar.setIvLeftOnClickListener {

        }
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

    fun init() {
        //初始化viewpager2
        //是否可滑动
        viewPager.isUserInputEnabled = true
        viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        //设置适配器
        viewPager.adapter = MyFragmentAdapter(this)

        // 联动ViewPager2和TabLayout
        val tabs = arrayOf("QList", "暂无", "暂无")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    private class MyFragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return ListFragment()
                }
                1 -> {
                    return ListFragment()
                }
                3 -> {
                    return ListFragment()
                }
                else -> {
                    return ListFragment()
                }
            }
        }

        override fun getItemCount() = 3
    }

}