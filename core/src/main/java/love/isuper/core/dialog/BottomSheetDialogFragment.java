package love.isuper.core.dialog;


import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import love.isuper.core.R;
import love.isuper.core.base.BaseLogDialogFragment;
import love.isuper.core.utils.AppInfo;

/**
 * Created by guoshichao on 2020/8/20
 * 从下弹出Dialog 和 复制于com.google.android.material.bottomsheet
 */
public class BottomSheetDialogFragment extends BaseLogDialogFragment {

    public BottomSheetDialogFragment() {
    }

    /**
     * Tracks if we are waiting for a dismissAllowingStateLoss or a regular dismiss once the
     * BottomSheet is hidden and onStateChanged() is called.
     */
    private boolean waitingForDismissAllowingStateLoss;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), isDialogTransparent() ? R.style.AppBottomSheetDialogTheme : 0);

        if (isWindowTransparent()) {
            setTransparentBackground(dialog);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setWhiteNavigationBar(dialog);
            }
        }

        setSlideCancelable(dialog, isSlideCancelable());

        setDialogAnim(dialog);

        return dialog;
    }

    @Override
    @CallSuper
    public void setupDialog(@NonNull Dialog dialog, int style) {

    }

    @Nullable
    public BottomSheetDialog getBottomSheetDialog() {
        Dialog dialog = getDialog();
        if (dialog instanceof BottomSheetDialog) {
            return (BottomSheetDialog) getDialog();
        }
        return null;
    }

    @Override
    public void dismiss() {
        if (!tryDismissWithAnimation(false)) {
            try {
                super.dismiss();
            }catch (Exception e){}
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if (!tryDismissWithAnimation(true)) {
            super.dismissAllowingStateLoss();
        }
    }

    /**
     * Tries to dismiss the dialog fragment with the bottom sheet animation. Returns true if possible,
     * false otherwise.
     */
    private boolean tryDismissWithAnimation(boolean allowingStateLoss) {
        Dialog baseDialog = getDialog();
        if (baseDialog instanceof BottomSheetDialog) {
            BottomSheetDialog dialog = (BottomSheetDialog) baseDialog;
            BottomSheetBehavior<?> behavior = dialog.getBehavior();
            if (behavior.isHideable() && dialog.getDismissWithAnimation()) {
                dismissWithAnimation(behavior, allowingStateLoss);
                return true;
            }
        }

        return false;
    }

    private void dismissWithAnimation(@NonNull BottomSheetBehavior<?> behavior, boolean allowingStateLoss) {
        waitingForDismissAllowingStateLoss = allowingStateLoss;

        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            dismissAfterAnimation();
        } else {
            if (getDialog() instanceof BottomSheetDialog) {
                ((BottomSheetDialog) getDialog()).removeDefaultCallback();
            }
            behavior.addBottomSheetCallback(new BottomSheetDismissCallback());
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    private void dismissAfterAnimation() {
        if (waitingForDismissAllowingStateLoss) {
            super.dismissAllowingStateLoss();
        } else {
            super.dismiss();
        }
    }

    private class BottomSheetDismissCallback extends BottomSheetBehavior.BottomSheetCallback {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismissAfterAnimation();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
    }

    private void setTransparentBackground(BottomSheetDialog dialog) {
        Window window = dialog.getWindow();
        if (window != null && getContext() != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setDimAmount(0f);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setWhiteNavigationBar(BottomSheetDialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            GradientDrawable dimDrawable = new GradientDrawable();

            GradientDrawable navigationBarDrawable = new GradientDrawable();
            navigationBarDrawable.setShape(GradientDrawable.RECTANGLE);
            navigationBarDrawable.setColor(AppInfo.getApplication().getResources().getColor(R.color.white));

            Drawable[] layers = {dimDrawable, navigationBarDrawable};

            LayerDrawable windowBackground = new LayerDrawable(layers);
            windowBackground.setLayerInsetTop(1, metrics.heightPixels);

            window.setBackgroundDrawable(windowBackground);
        }
    }

    protected boolean isWindowTransparent() {
        return false;
    }

    protected boolean isDialogTransparent() {
        return true;
    }

    protected boolean isSlideCancelable() {
        return true;
    }

    protected void setSlideCancelable(BottomSheetDialog dialog, boolean slideCancelable) {
        dialog.getBehavior().addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    if (!slideCancelable) {
                        dialog.getBehavior().setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setDialogAnim(BottomSheetDialog dialog) {
        Window window = dialog.getWindow();
        if (window != null && getAnimRes() != 0) {
            window.setWindowAnimations(getAnimRes());
        }
    }

    protected int getAnimRes() {
        return 0;
    }

    protected void setPeekHeight(int peekHeight) {
        if (getDialog() != null && getDialog() instanceof BottomSheetDialog) {
            BottomSheetDialog bottomSheetDialog = ((BottomSheetDialog)getDialog());
            bottomSheetDialog.getBehavior().setPeekHeight(peekHeight);
        }
    }

}