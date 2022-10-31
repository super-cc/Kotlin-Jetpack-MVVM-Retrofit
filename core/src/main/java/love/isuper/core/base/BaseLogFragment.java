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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("BaseFragment", this.getClass().getName() + this + " onAttach()");
    }

    @TargetApi(22)
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("BaseFragment", this.getClass().getName() + this + " onAttach()");
    }

    @CallSuper
    public void onDetach() {
        Log.v("BaseFragment", this.getClass().getName() + this + " onDetach()");
        super.onDetach();
    }

    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("BaseFragment", this.getClass().getName() + this + " onCreate()");
    }

    @CallSuper
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("BaseFragment", this.getClass().getName() + this + " onCreateView()");
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    @CallSuper
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.v("BaseFragment", this.getClass().getName() + this + " onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @CallSuper
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v("BaseFragment", this.getClass().getName() + this + " setUserVisibleHint(), isVisibleToUser:" + isVisibleToUser);
    }

    @CallSuper
    public void onStart() {
        super.onStart();
        Log.v("BaseFragment", this.getClass().getName() + this + " onStart()");
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        Log.v("BaseFragment", this.getClass().getName() + this + " onResume()");
    }

    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("BaseFragment", this.getClass().getName() + this + " onSaveInstanceState()");
    }

    @CallSuper
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v("BaseFragment", this.getClass().getName() + this + " onViewStateRestored()");
    }

    @CallSuper
    public void onPause() {
        super.onPause();
        Log.v("BaseFragment", this.getClass().getName() + this + " onPause()");
    }

    @CallSuper
    public void onStop() {
        super.onStop();
        Log.v("BaseFragment", this.getClass().getName() + this + " onStop()");
    }

    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("BaseFragment", this.getClass().getName() + this + " onDestroyView()");
    }

    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        Log.v("BaseFragment", this.getClass().getName() + this + " onDestroy()");
    }

}
