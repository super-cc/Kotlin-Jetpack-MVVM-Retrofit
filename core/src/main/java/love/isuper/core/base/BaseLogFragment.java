package love.isuper.core.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by guoshichao on 2021/4/28
 */
public class BaseLogFragment extends Fragment {

    private static final String _TAG = "BaseFragment";

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(_TAG, this.getClass().getSimpleName() + " onAttach() " + this.getClass().getName() + " " + this);
    }

    @TargetApi(22)
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(_TAG, this.getClass().getSimpleName() + " onAttach() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onDetach() {
        super.onDetach();
        Log.v(_TAG, this.getClass().getSimpleName() + " onDetach() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(_TAG, this.getClass().getSimpleName() + " onCreate() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(_TAG, this.getClass().getSimpleName() + " onCreateView() " + this.getClass().getName() + " " + this);
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    @CallSuper
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.v(_TAG, this.getClass().getSimpleName() + " onViewCreated() " + this.getClass().getName() + " " + this);
        super.onViewCreated(view, savedInstanceState);
    }

    @CallSuper
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(_TAG, this.getClass().getSimpleName() + " setUserVisibleHint(), isVisibleToUser: " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onStart() {
        super.onStart();
        Log.v(_TAG, this.getClass().getSimpleName() + " onStart() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        Log.v(_TAG, this.getClass().getSimpleName() + " onResume() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(_TAG, this.getClass().getSimpleName() + " onSaveInstanceState() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v(_TAG, this.getClass().getSimpleName() + " onViewStateRestored() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onPause() {
        super.onPause();
        Log.v(_TAG, this.getClass().getSimpleName() + " onPause() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onStop() {
        super.onStop();
        Log.v(_TAG, this.getClass().getSimpleName() + " onStop() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(_TAG, this.getClass().getSimpleName() + " onDestroyView() " + this.getClass().getName() + " " + this);
    }

    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        Log.v(_TAG, this.getClass().getSimpleName() + " onDestroy() " + this.getClass().getName() + " " + this);
    }

}
