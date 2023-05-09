package love.isuper.app

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import love.isuper.app.R
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import love.isuper.app.databinding.DialogMenuBinding
import love.isuper.core.dialog.BottomSheetDialogFragment

/**
 * Created by shichao on 2022/6/27.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
class DialogDemo : BottomSheetDialogFragment() {

    private val viewBinding by lazy { DialogMenuBinding.inflate(layoutInflater) }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialog.setContentView(viewBinding.root)
//        setDraggable(false)
        viewBinding.textView.movementMethod = ScrollingMovementMethod.getInstance()
    }
}