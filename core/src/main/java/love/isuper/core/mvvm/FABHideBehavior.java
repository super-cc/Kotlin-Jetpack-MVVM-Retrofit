package love.isuper.core.mvvm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by shichao on 2022/8/29.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public class FABHideBehavior extends FloatingActionButton.Behavior {

    // 动画插值器，可以控制动画的变化率
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    // 动画时长
    private static final Long DURATION_TIME = 200L;

    // 是否正在执行隐藏的动画
    private boolean mIsAnimatingOut = false;
    private boolean mIsAnimatingIn = false;

    public FABHideBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        // 如果是上下滑动的，则返回true
        if (type == ViewCompat.TYPE_TOUCH) {
            return axes == ViewCompat.SCROLL_AXIS_VERTICAL
                    || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        }
        return false;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                               int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            // 用户向下滑动时判断是否正在执行隐藏动画，如果不是，而且FAB当前是可见的，则执行隐藏动画隐藏FAB
            animateOut(child);
        } else if (dyConsumed < 0 && !this.mIsAnimatingIn && child.getVisibility() != View.VISIBLE) {
            // 用户向上滑动时判断FAB是否可见，如果不可见则执行显示动画显示FAB
            animateIn(child);
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    // 执行隐藏动画隐藏FAB
    private void animateOut(final FloatingActionButton button) {
        ViewCompat.animate(button)
                .translationY(button.getHeight() * 1.5f)
                .setInterpolator(INTERPOLATOR)
                .setDuration(DURATION_TIME)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        FABHideBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        FABHideBehavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        FABHideBehavior.this.mIsAnimatingOut = false;
                        view.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    // 执行显示动画显示FAB
    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button)
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .setDuration(DURATION_TIME)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        FABHideBehavior.this.mIsAnimatingIn = true;
                    }

                    public void onAnimationCancel(View view) {
                        FABHideBehavior.this.mIsAnimatingIn = false;
                    }

                    public void onAnimationEnd(View view) {
                        FABHideBehavior.this.mIsAnimatingIn = false;
                    }
                })
                .start();
    }

}
