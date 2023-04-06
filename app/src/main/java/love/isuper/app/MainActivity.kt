package love.isuper.app

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import love.isuper.app.databinding.ActivityMainBinding
import love.isuper.core.ext.nav
import love.isuper.core.ext.navigateAction
import love.isuper.core.ext.navigateSingleAction
import love.isuper.core.mvvm.BaseActivity
import love.isuper.core.mvvm.EmptyViewModel
import love.isuper.core.utils.ToastUtils
import love.isuper.core.utils.singleclick.setOnSingleClickListener

class MainActivity : BaseActivity<EmptyViewModel, ActivityMainBinding>() {

    override fun init() {
        initNav()

        var n = 0
        mViewBinding.titleBar.ivLeft.setOnSingleClickListener {
            n++
            ToastUtils.showShortToast("点击${n}次")
        }
        mViewBinding.titleBar.tvRight.setOnClickListener {
            DialogDemo().show(supportFragmentManager, DialogDemo::javaClass.name)
        }
    }

    private fun initNav() {
        val navView: BottomNavigationView = mViewBinding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.list_fragment,
                R.id.data_store_fragment,
                R.id.list_fragment_2,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener {
            navController.navigateSingleAction(it.itemId, interval = 0)
        }
    }

    override fun liveDataObserver() {

    }

}