package love.isuper.mvvm.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by guoshichao on 2021/4/28
 */
public class BaseFragment extends Fragment {

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("BaseFragment", this.getClass().getName() + this + " onAttach()");
    }

    @TargetApi(22)
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("BaseFragment", this.getClass().getName() + this + " onAttach()");
    }

    public void onDetach() {
        Log.v("BaseFragment", this.getClass().getName() + this + " onDetach()");
        super.onDetach();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("BaseFragment", this.getClass().getName() + this + " onCreate()");
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("BaseFragment", this.getClass().getName() + this + " onCreateView()");
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v("BaseFragment", this.getClass().getName() + this + " setUserVisibleHint(), isVisibleToUser:" + isVisibleToUser);
    }

    public void onStart() {
        super.onStart();
        Log.v("BaseFragment", this.getClass().getName() + this + " onStart()");
    }

    public void onResume() {
        super.onResume();
        Log.v("BaseFragment", this.getClass().getName() + this + " onResume()");
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("BaseFragment", this.getClass().getName() + this + " onSaveInstanceState()");
    }

    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v("BaseFragment", this.getClass().getName() + this + " onViewStateRestored()");
    }

    public void onPause() {
        super.onPause();
        Log.v("BaseFragment", this.getClass().getName() + this + " onPause()");
    }

    public void onStop() {
        super.onStop();
        Log.v("BaseFragment", this.getClass().getName() + this + " onStop()");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.v("BaseFragment", this.getClass().getName() + this + " onDestroyView()");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.v("BaseFragment", this.getClass().getName() + this + " onDestroy()");
    }

}
