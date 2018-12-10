package mobile.bny.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import mobile.bny.androidtools.BandroidAsyncTask;
import mobile.bny.javatools.StringUtils;

public class AsyncTaskDialog  extends BaseBandroidAlertDialog {

    int horizontalPadding, verticalPadding;
    AppCompatTextView tvMessage, tvTitle;
    //Instance with Strings
    public static AsyncTaskDialog newInstance(String title, @NonNull String message) {

        AsyncTaskDialog dialog = new AsyncTaskDialog();

        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putString(MESSAGE,message);

        dialog.setArguments(bundle);

        return dialog;
    }

    //Instance with Strings from resources
    public static AsyncTaskDialog newInstance(@NonNull int title, @NonNull int message) {

        AsyncTaskDialog dialog = new AsyncTaskDialog();

        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_RES,title);
        bundle.putInt(MESSAGE_RES,message);
        bundle.putBoolean(USE_RESOURCE,true);

        dialog.setArguments(bundle);
        return dialog;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            if(bundle.getBoolean(USE_RESOURCE, false)){
                title = getString(bundle.getInt(TITLE_RES));
                message = getString(bundle.getInt(MESSAGE_RES));
            } else {
                title = bundle.getString(TITLE);
                message = bundle.getString(MESSAGE);
            }
            buttons = (DialogButton[]) bundle.getParcelableArray(BUTTONS);
        }

        horizontalPadding = (int) getResources().getDimension(R.dimen.dialog_horizontal_padding);
        verticalPadding = (int) getResources().getDimension(R.dimen.dialog_top_padding);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(getDialogView());

        setCancelable(false);
        return builder.create();
    }

    @Override
    public void show(FragmentManager manager) {
        super.show(manager);
    }

    @Override
    protected void addButtons(AlertDialog.Builder builder, DialogButton[] buttons) {
        return;
    }

    @Override
    protected void setTitle(AlertDialog.Builder builder, String title) {
        this.title = title;
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void updateDialog(@StringRes int message){
        if(message != 0 && tvMessage != null)
            tvMessage.setText(message);
    }

    public void updateDialog(String message){
        if(StringUtils.notNullOrEmpty(message) && tvMessage != null)
            tvMessage.setText(message);
    }

    public void updateDialog(@StringRes int title, @StringRes int message){
        if(message != 0 && tvMessage != null)
            tvMessage.setText(message);
        if(title != 0 && tvTitle != null)
            tvTitle.setText(title);
    }

    public void updateDialog(String title, String message){
        if(StringUtils.notNullOrEmpty(message) && tvMessage != null)
            tvMessage.setText(message);

        if(StringUtils.notNullOrEmpty(title) && tvTitle != null)
            tvTitle.setText(message);
    }

    protected View getDialogView(){

        LinearLayoutCompat root = getRootView();

        LinearLayoutCompat.LayoutParams pbParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
        pbParams.gravity = Gravity.CENTER_VERTICAL;
        root.addView(progressBar,pbParams);

        LinearLayoutCompat textContainer = new LinearLayoutCompat(getActivity());
        LinearLayoutCompat.LayoutParams textContainerParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textContainer.setPadding(horizontalPadding,0,0,0);
        textContainerParams.gravity = Gravity.CENTER_VERTICAL;
        textContainer.setOrientation(LinearLayoutCompat.VERTICAL);
        textContainer.setLayoutParams(textContainerParams);

        LinearLayoutCompat.LayoutParams textParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tvTitle = new AppCompatTextView(getActivity());
        tvTitle.setText(title);
        TextViewCompat.setTextAppearance(tvTitle, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);

        tvMessage = new AppCompatTextView(getActivity());
        tvMessage.setText(message);
        tvMessage.setPadding(0,verticalPadding,0,0);
        TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance_DeviceDefault_Medium);
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTitle.getTextSize() * 0.7f);

        textContainer.addView(tvTitle,textParams);
        textContainer.addView(tvMessage,textParams);

        root.addView(textContainer,textContainerParams);

        return root;
    }

    protected LinearLayoutCompat getRootView(){

        LinearLayoutCompat root = new LinearLayoutCompat(getActivity());
        LinearLayoutCompat.LayoutParams rootParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setPadding(horizontalPadding,verticalPadding,horizontalPadding,verticalPadding);
        rootParams.gravity = Gravity.CENTER_VERTICAL;
        root.setOrientation(LinearLayoutCompat.HORIZONTAL);
        root.setLayoutParams(rootParams);

        return root;
    }
}
