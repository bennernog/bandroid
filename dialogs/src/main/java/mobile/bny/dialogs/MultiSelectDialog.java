package mobile.bny.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * Created by BNY on 24/11/2017.
 */

public class MultiSelectDialog extends BaseBandroidAlertDialog {

    boolean[] checkedState;

    public static MultiSelectDialog newInstance(int title, @NonNull int itemsId) {
        return newInstance(title, itemsId, (DialogButton[]) null);
    }

    public static MultiSelectDialog newInstance(String title, @NonNull String[] items) {
        return newInstance(title, items, (DialogButton[]) null);
    }

    public static MultiSelectDialog newInstance(String title, @NonNull String[] items, DialogButton... buttons) {

        boolean[] checked = new boolean[items.length];
        for (int i = 0; i < items.length; i++)
            checked[i] = false;

        return newInstance(title, items, checked,buttons);
    }

    public static MultiSelectDialog newInstance(int title, @NonNull int itemsId, DialogButton... buttons) {

        return newInstance(title, itemsId, null, buttons);
    }

    public static MultiSelectDialog newInstance(String title, @NonNull String[] items, boolean[] checked, DialogButton... buttons){

        checkButtons(buttons);

        MultiSelectDialog dialog = new MultiSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putStringArray(ITEMS,items);
        bundle.putBooleanArray(CHECKED_STATE,checked);
        bundle.putParcelableArray(BUTTONS, buttons);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static MultiSelectDialog newInstance(int title, @NonNull int itemsId, boolean[] checked, DialogButton... buttons){

        checkButtons(buttons);

        MultiSelectDialog dialog = new MultiSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(USE_RESOURCE,true);
        bundle.putInt(TITLE_RES,title);
        bundle.putInt(ITEMS_RES,itemsId);
        bundle.putBooleanArray(CHECKED_STATE,checked);
        bundle.putParcelableArray(BUTTONS, buttons);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            checkedState = bundle.getBooleanArray(CHECKED_STATE);
            if(bundle.getBoolean(USE_RESOURCE, false)){

                title = getString(bundle.getInt(TITLE_RES));
                items = getResources().getStringArray(bundle.getInt(ITEMS_RES));

                if (checkedState == null || checkedState.length != items.length) {
                    checkedState = new boolean[items.length];
                    for (int i = 0; i < items.length; i++)
                        checkedState[i] = false;
                }
            } else {
                title = bundle.getString(TITLE);
                items = bundle.getStringArray(ITEMS);
            }
            buttons = (DialogButton[]) bundle.getParcelableArray(BUTTONS);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        setTitle(builder,title);

        builder.setMultiChoiceItems(items, checkedState, this);

        addButtons(builder,buttons);

        return builder.create();
    }
}
