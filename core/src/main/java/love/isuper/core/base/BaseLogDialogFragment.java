package love.isuper.core.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Created by shichao on 2022/6/27.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
public class BaseLogDialogFragment extends AppCompatDialogFragment {

    private static final String _TAG = "BaseFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.v(_TAG, this.getClass().getSimpleName() + " onCreateDialog() " + this.getClass().getName() + " " + this);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Log.v(_TAG, this.getClass().getSimpleName() + " onDismiss() " + this.getClass().getName() + " " + this);
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        Log.v(_TAG, this.getClass().getSimpleName() + " onCancel() " + this.getClass().getName() + " " + this);
        super.onCancel(dialog);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(_TAG, this.getClass().getSimpleName() + " onAttach() " + this.getClass().getName() + " " + this);
    }

    @TargetApi(22)
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(_TAG, this.getClass().getSimpleName() + " onAttach() " + this.getClass().getName() + " " + this);
    }

    public void onDetach() {
        super.onDetach();
        Log.v(_TAG, this.getClass().getSimpleName() + " onDetach() " + this.getClass().getName() + " " + this);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(_TAG, this.getClass().getSimpleName() + " onCreate() " + this.getClass().getName() + " " + this);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(_TAG, this.getClass().getSimpleName() + " onCreateView() " + this.getClass().getName() + " " + this);
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.v(_TAG, this.getClass().getSimpleName() + " onViewCreated() " + this.getClass().getName() + " " + this);
        super.onViewCreated(view, savedInstanceState);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(_TAG, this.getClass().getSimpleName() + " setUserVisibleHint(), isVisibleToUser: " + this.getClass().getName() + " " + this);
    }

    public void onStart() {
        super.onStart();
        Log.v(_TAG, this.getClass().getSimpleName() + " onStart() " + this.getClass().getName() + " " + this);
    }

    public void onResume() {
        super.onResume();
        Log.v(_TAG, this.getClass().getSimpleName() + " onResume() " + this.getClass().getName() + " " + this);
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(_TAG, this.getClass().getSimpleName() + " onSaveInstanceState() " + this.getClass().getName() + " " + this);
    }

    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v(_TAG, this.getClass().getSimpleName() + " onViewStateRestored() " + this.getClass().getName() + " " + this);
    }

    public void onPause() {
        super.onPause();
        Log.v(_TAG, this.getClass().getSimpleName() + " onPause() " + this.getClass().getName() + " " + this);
    }

    public void onStop() {
        super.onStop();
        Log.v(_TAG, this.getClass().getSimpleName() + " onStop() " + this.getClass().getName() + " " + this);
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.v(_TAG, this.getClass().getSimpleName() + " onDestroyView() " + this.getClass().getName() + " " + this);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.v(_TAG, this.getClass().getSimpleName() + " onDestroy() " + this.getClass().getName() + " " + this);
    }

}
