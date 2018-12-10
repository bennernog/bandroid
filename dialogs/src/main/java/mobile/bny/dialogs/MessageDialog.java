package mobile.bny.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * Created by BNY on 17/11/2017.
 */

public class MessageDialog extends BaseBandroidAlertDialog {

    //Instance with Strings
    public static MessageDialog newInstance(String title, @NonNull String message) {
        return newInstance(title, message, (DialogButton[]) null);
    }

    public static MessageDialog newInstance(String title, @NonNull String message, DialogButton... buttons){

        checkButtons(buttons);

        MessageDialog dialog = new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putString(MESSAGE,message);
        bundle.putParcelableArray(BUTTONS, buttons);
        dialog.setArguments(bundle);
        return dialog;
    }

    //Instance with Strings from resources
    public static MessageDialog newInstance(@NonNull int title, @NonNull int message) {
        return newInstance(title, message, (DialogButton[]) null);
    }

    public static MessageDialog newInstance(@NonNull int title, @NonNull int message, DialogButton... buttons) {
        checkButtons(buttons);

        MessageDialog dialog = new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_RES,title);
        bundle.putInt(MESSAGE_RES,message);
        bundle.putParcelableArray(BUTTONS, buttons);
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
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        setTitle(builder,title);

        builder.setMessage(message);

        addButtons(builder,buttons);

        return builder.create();
    }
}
