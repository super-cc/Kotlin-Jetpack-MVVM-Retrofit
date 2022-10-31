package love.isuper.app;

import android.app.Dialog;

import androidx.annotation.NonNull;

import love.isuper.core.dialog.BottomSheetDialogFragment;

/**
 * Created by shichao on 2022/6/27.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public class DialogDemo extends BottomSheetDialogFragment {

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        dialog.setContentView(R.layout.dialog_menu);
    }
}
