package love.isuper.app

import love.isuper.app.databinding.FragmentDataStoreBinding
import love.isuper.core.mvvm.BaseFragment
import love.isuper.core.mvvm.EmptyViewModel
import love.isuper.core.utils.ToastUtils

/**
 * Created by 郭士超 on 2022/11/6 16:32
 * Describe：
 */
class DataStoreFragment: BaseFragment<EmptyViewModel, FragmentDataStoreBinding>() {

    override fun liveDataObserver() {

    }

    override fun init() {
        mViewBinding.apply {
            btnPut.setOnClickListener {
                AppDataStore.putNumber(1)
            }
            btnGet.setOnClickListener {
                ToastUtils.showToast("数字：${AppDataStore.getNumber()}")
            }
            btnClear.setOnClickListener {
                AppDataStore.clear()
            }
        }
    }

}