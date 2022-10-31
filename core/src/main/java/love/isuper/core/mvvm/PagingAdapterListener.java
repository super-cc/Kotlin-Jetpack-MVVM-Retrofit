package love.isuper.core.mvvm;

import android.view.View;

import androidx.annotation.NonNull;

import love.isuper.core.mvvm.BasePagingAdapter;

/**
 * Created by shichao on 2022/9/7.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public class PagingAdapterListener {

    public interface OnItemChildClickListener {
        void onItemChildClick(@NonNull BasePagingAdapter<?,?> adapter, @NonNull View view, int position);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(@NonNull BasePagingAdapter<?,?> adapter, @NonNull View view, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull BasePagingAdapter<?,?> adapter, @NonNull View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(@NonNull BasePagingAdapter<?,?> adapter, @NonNull View view, int position);
    }

}
