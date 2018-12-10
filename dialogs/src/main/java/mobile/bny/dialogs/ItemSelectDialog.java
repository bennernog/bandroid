package mobile.bny.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * Created by BNY on 17/11/2017.
 */

public class ItemSelectDialog extends BaseBandroidAlertDialog{

    int defItem;

    public static ItemSelectDialog newInstance(int title, @NonNull int itemsId) {
        return newInstance(title, itemsId, (DialogButton[])null);
    }

    public static ItemSelectDialog newInstance(String title, @NonNull String[] items) {
        return newInstance(title, items, (DialogButton[])null);
    }

    public static ItemSelectDialog newInstance(String title, @NonNull String[] items, DialogButton... buttons) {
        return newInstance(title, items, NO_DEFAULT_ITEM, buttons);
    }

    public static ItemSelectDialog newInstance(int title, @NonNull int itemsId, DialogButton... buttons) {
        return newInstance(title, itemsId, NO_DEFAULT_ITEM, buttons);
    }

    public static ItemSelectDialog newInstance(String title, @NonNull String[] items, int defaultItem, DialogButton... buttons){

        checkButtons(buttons);

        ItemSelectDialog dialog = new ItemSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putStringArray(ITEMS,items);
        bundle.putParcelableArray(BUTTONS, buttons);
        bundle.putInt(DEFAULT_ITEM, defaultItem);
        dialog.setArguments(bundle);
        return dialog;
    }


    public static ItemSelectDialog newInstance(int title, @NonNull int itemsId, int defaultItem, DialogButton... buttons){

        checkButtons(buttons);

        ItemSelectDialog dialog = new ItemSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(USE_RESOURCE,true);
        bundle.putInt(TITLE_RES,title);
        bundle.putInt(ITEMS_RES,itemsId);
        bundle.putParcelableArray(BUTTONS, buttons);
        bundle.putInt(DEFAULT_ITEM, defaultItem);
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
                items = getResources().getStringArray(bundle.getInt(ITEMS_RES));
            } else {
                title = bundle.getString(TITLE);
                items = bundle.getStringArray(ITEMS);
            }
            defItem = bundle.getInt(DEFAULT_ITEM);
            buttons = (DialogButton[]) bundle.getParcelableArray(BUTTONS);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        setTitle(builder,title);

        //Set items with or without radiobutton
        if(defItem == NO_DEFAULT_ITEM)
            builder.setItems(items, this);
        else
            builder.setSingleChoiceItems(items,defItem, this);

        //Add buttons if needed
        addButtons(builder,buttons);

        return builder.create();
    }
}
