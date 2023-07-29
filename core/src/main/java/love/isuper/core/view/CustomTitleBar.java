package love.isuper.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import love.isuper.core.R;


/**
 * Created by shichao on 2022/5/17.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public class CustomTitleBar extends ConstraintLayout {

    private View rootView;
    private ImageView ivLeft;
    private ImageView ivRight;
    private TextView tvCenter;
    private TextView tvLeft;
    private TextView tvRight;

    private int leftResId;
    private int rightResId;
    private CharSequence centerText;
    private int centerTextColor;
    private float centerTextSize;
    private CharSequence leftText;
    private int leftTextColor;
    private float leftTextSize;
    private CharSequence rightText;
    private int rightTextColor;
    private float rightTextSize;
    private int titleBarBackground;

    public CustomTitleBar(@NonNull Context context) {
        this(context, null);
    }

    public CustomTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    private void initView(Context context) {
        rootView = (ConstraintLayout) View.inflate(context, R.layout.view_custom_title_bar, this);
        ivLeft = rootView.findViewById(R.id.iv_left);
        ivRight = rootView.findViewById(R.id.iv_right);
        tvCenter = rootView.findViewById(R.id.tv_center);
        tvLeft = rootView.findViewById(R.id.tv_left);
        tvRight = rootView.findViewById(R.id.tv_right);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomerTitleBar);
        leftResId = typedArray.getResourceId(R.styleable.CustomerTitleBar_left_icon, 0);
        rightResId = typedArray.getResourceId(R.styleable.CustomerTitleBar_right_icon, 0);
        centerText = typedArray.getText(R.styleable.CustomerTitleBar_center_text);
        centerTextColor = typedArray.getColor(R.styleable.CustomerTitleBar_center_text_color, Color.WHITE);
        centerTextSize = typedArray.getDimension(R.styleable.CustomerTitleBar_center_text_size, 0f);
        leftText = typedArray.getText(R.styleable.CustomerTitleBar_left_text);
        leftTextColor = typedArray.getColor(R.styleable.CustomerTitleBar_left_text_color, Color.WHITE);
        leftTextSize = typedArray.getDimension(R.styleable.CustomerTitleBar_left_text_size, 0f);
        rightText = typedArray.getText(R.styleable.CustomerTitleBar_right_text);
        rightTextColor = typedArray.getColor(R.styleable.CustomerTitleBar_right_text_color, Color.WHITE);
        rightTextSize = typedArray.getDimension(R.styleable.CustomerTitleBar_right_text_size, 0f);
        titleBarBackground = typedArray.getResourceId(R.styleable.CustomerTitleBar_title_bar_background, 0);
        typedArray.recycle();
        setView();
    }

    private void setView() {
        if (leftResId != 0) {
            ivLeft.setImageResource(leftResId);
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.GONE);
        }
        if (rightResId != 0) {
            ivRight.setImageResource(rightResId);
            ivRight.setVisibility(View.VISIBLE);
        } else {
            ivRight.setVisibility(View.GONE);
        }

        setCenterText(centerText);
        setLeftText(leftText);
        setRightText(rightText);

        if (titleBarBackground != 0) {
            rootView.setBackgroundResource(titleBarBackground);
        }
    }

    public void setCenterText(@StringRes int resId) {
        this.centerText = getContext().getResources().getText(resId);
        setCenterText(this.centerText);
    }

    public void setCenterText(CharSequence text) {
        this.centerText = text;
        if (!TextUtils.isEmpty(centerText)) {
            tvCenter.setText(centerText);
            tvCenter.setTextColor(centerTextColor);
            if (centerTextSize != 0) {
                tvCenter.setTextSize(centerTextSize);
            }
            tvCenter.setVisibility(View.VISIBLE);
        } else {
            tvCenter.setVisibility(View.GONE);
        }
    }

    public void setCenterTextColor(@ColorInt int color) {
        if (color != -1) {
            this.tvCenter.setTextColor(color);
        }
    }

    public void setLeftText(@StringRes int resId) {
        this.leftText = getContext().getResources().getText(resId);
        setLeftText(this.leftText);
    }

    public void setLeftText(CharSequence text) {
        this.leftText = text;
        if (!TextUtils.isEmpty(leftText)) {
            tvLeft.setText(leftText);
            tvLeft.setTextColor(leftTextColor);
            if (leftTextSize != 0) {
                tvLeft.setTextSize(leftTextSize);
            }
            tvLeft.setVisibility(View.VISIBLE);
        } else {
            tvLeft.setVisibility(View.GONE);
        }
    }

    public void setLeftTextColor(@ColorInt int color) {
        if (color != -1) {
            this.tvLeft.setTextColor(color);
        }
    }

    public void setRightText(@StringRes int resId) {
        this.rightText = getContext().getResources().getText(resId);
        setRightText(this.rightText);
    }

    public void setRightText(CharSequence text) {
        this.rightText = text;
        if (!TextUtils.isEmpty(rightText)) {
            tvRight.setText(rightText);
            tvRight.setTextColor(rightTextColor);
            if (rightTextSize != 0) {
                tvRight.setTextSize(rightTextSize);
            }
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }
    }

    public void setRightTextColor(@ColorInt int color) {
        if (color != -1) {
            this.tvLeft.setTextColor(color);
        }
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public void setIvLeftOnClickListener(OnClickListener onClickListener) {
        ivLeft.setOnClickListener(onClickListener);
    }

    public void setIvRightOnClickListener(OnClickListener onClickListener) {
        ivRight.setOnClickListener(onClickListener);
    }

    public void setTvCenterOnClickListener(OnClickListener onClickListener) {
        tvCenter.setOnClickListener(onClickListener);
    }

    public void setTvLeftOnClickListener(OnClickListener onClickListener) {
        tvLeft.setOnClickListener(onClickListener);
    }

    public void setTvRightOnClickListener(OnClickListener onClickListener) {
        tvRight.setOnClickListener(onClickListener);
    }

}
