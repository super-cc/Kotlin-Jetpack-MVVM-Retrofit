package love.isuper.core.view;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import love.isuper.core.R;


/**
 * 搜索组件
 */
public class SearchView extends LinearLayout {

    private EditText edtInput;
    private ImageView ivDelete;

    private OnSearchChangeListener searchChangeListener;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void setSearchListener(OnSearchChangeListener searchListener) {
        this.searchChangeListener = searchListener;
    }

    public void setSearchText(String searchText) {
        if (TextUtils.isEmpty(searchText)) return;
        edtInput.setText(searchText);
        edtInput.setSelection(searchText.length());
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_search_view, this, true);
        edtInput = view.findViewById(R.id.edt_input);
        ivDelete = view.findViewById(R.id.iv_delete);
        setListener();
    }

    private void setListener() {
        edtInput.setOnKeyListener(new OnKeyListener() {//点击搜索按钮监听事件
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(((Activity) getContext()).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    edtInput.clearFocus();
                    //搜索
                    if (searchChangeListener != null) {
                        searchChangeListener.onSearch(edtInput.getText().toString().trim());
                    }
                }
                return false;
            }
        });

        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    ivDelete.setVisibility(View.VISIBLE);
                } else {
                    ivDelete.setVisibility(View.GONE);
                }
                if (searchChangeListener != null) {
                    String keyWord = s.toString().trim();
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.equals(keyWord, getKeyWord())){
                                searchChangeListener.onSearch(keyWord);
                            }
                        }
                    }, 1000);
                }
            }
        });

        ivDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edtInput.setText("");
            }
        });
    }

    public void setHint(@StringRes int resId) {
        edtInput.setHint(resId);
    }

    public void setHint(CharSequence hint) {
        edtInput.setHint(hint);
    }

    public String getHint() {
        return edtInput.getHint().toString();
    }

    public String getKeyWord() {
        return edtInput.getText().toString();
    }

    public interface OnSearchChangeListener {
        void onSearch(String keyWord);
    }

}