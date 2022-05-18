package love.isuper.app

import android.widget.Toast
import love.isuper.app.databinding.ActivityMainBinding
import love.isuper.mvvm.extensions.viewBinding
import love.isuper.mvvm.mvvm.MVVMBaseActivity
import love.isuper.mvvm.mvvm.observe

class MainActivity : MVVMBaseActivity<MainViewModel>(R.layout.activity_main) {

    private val mViewBinding by viewBinding(ActivityMainBinding::bind)

    override fun liveDataObserver() {
        observe(mViewModel.modelLiveData, ::showToast)
    }

    private fun showToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun init() {
        mViewBinding?.apply {
            tvHello.setOnClickListener{
                mViewModel.getModel()
            }
        }
    }

}