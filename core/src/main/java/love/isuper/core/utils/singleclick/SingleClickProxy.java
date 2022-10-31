package love.isuper.core.utils.singleclick;

import android.view.View;

/**
 * Created by guoshichao on 2020/10/19
 * 防止多次点击代理类
 */
public final class SingleClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private long lastclick = 0;
    private long timems = 1000; //ms
    private IClickAgain mIAgain;

    public SingleClickProxy(View.OnClickListener origin, long timems, IClickAgain again) {
        this.origin = origin;
        this.mIAgain = again;
        this.timems = timems;
    }

    public SingleClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastclick >= timems) {
            origin.onClick(v);
            lastclick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) {
                mIAgain.onAgain();
            }
        }
    }

}