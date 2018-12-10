package mobile.bny.bandroidapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mobile.bny.androidtools.Toaster;
import mobile.bny.bandroidapp.R;
import mobile.bny.bandroidapp.activities.MainActivity;
import mobile.bny.dialogs.DialogButton;
import mobile.bny.dialogs.ItemSelectDialog;
import mobile.bny.dialogs.MessageDialog;
import mobile.bny.dialogs.MultiSelectDialog;

/**
 * Created by BNY on 25/11/2017.
 */

public class DialogSampleFragment extends ListFragment {

    String[] listItems = {
            "MessageDialog",
            "MessageDialog No action",
            "MessageDialog Cancel & Dismiss",
            "ItemSelectDialog",
            "SingleItemSelectDialog",
            "ItemSelectDialog NoTitle",
            "MultiSelectDialog"
    };
    String[] dialogItems;
    int position;

    public static DialogSampleFragment newInstance(){

        DialogSampleFragment fragment = new DialogSampleFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = new Bundle();
//        if(bundle != null){
//
//        }
        dialogItems = new String[6];
        for(int i=0; i < 6;i++)
            dialogItems[i]=String.format("Item %s",i+1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setListAdapter(new ArrayAdapter<String>(getMainActivity(),
                R.layout.list_text_item,
                listItems));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getListView().setDivider(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.list_divider, null));
//        getListView().setDividerHeight(1);
        getListView().setVerticalScrollBarEnabled(false);
        getListView().setHorizontalScrollBarEnabled(false);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (position){
            case 0:
                showMessageDialog();
                break;
            case 1:
                showSimpleMessageDialog();
                break;
            case 2:
                showSimpleMessageDialogWith();
                break;
            case 3:
                showItemSelectDialog();
                break;
            case 4:
                showSingleItemSelectDialog();
                break;
            case 5:
                showItemSelectDialogNoTitle();
                break;
            case 6:
                showMultiSelectDialog();
                break;
            default:
                Toaster.shortToast(getMainActivity(),"You pressed %s", listItems[position]);
                super.onListItemClick(l, v, position, id);
                break;
        }

    }

    void showMessageDialog(){
        final MessageDialog dialog = MessageDialog.newInstance(
                "This is the title",
                "This is the message I have for my users",
                new DialogButton(DialogButton.Type.POSITIVE,"POSITIVE"),
                new DialogButton(DialogButton.Type.NEUTRAL,"NEUTRAL"),
                new DialogButton(DialogButton.Type.NEGATIVE,"NEGATIVE"));

        dialog.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toaster.shortToast(getMainActivity(), "YES");
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        Toaster.shortToast(getMainActivity(), "MAYBE");
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Toaster.shortToast(getMainActivity(), "NOPE");
                        break;
                }
            }
        });

        dialog.show(getFragmentManager(),listItems[0]);
    }

    private void showSimpleMessageDialog() {
        MessageDialog.newInstance(
                R.string.message_dialog_title,
                R.string.message_dialog_message,
                new DialogButton(DialogButton.Type.POSITIVE, "OK"))
                .show(getFragmentManager(), listItems[1]);
    }


    private void showSimpleMessageDialogWith() {
        MessageDialog dialog = MessageDialog.newInstance(
                "Dialog with dismiss and cancel listenens",
                "Press ok to dismiss and outside the dialog or back button to cancel",
                new DialogButton(DialogButton.Type.POSITIVE, "OK"));

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toaster.shortToast(getMainActivity(), "CANCELED");
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Toaster.shortToast(getMainActivity(), "DISMISSED");
            }
        });
        dialog.show(getFragmentManager(), listItems[2]);
    }

    void showItemSelectDialog(){
        ItemSelectDialog dialog = ItemSelectDialog.newInstance(
                "This is the title",
                dialogItems);
        dialog.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toaster.shortToast(getMainActivity(),"You selected %s", dialogItems[i]);
            }
        });
        dialog.show(getFragmentManager(),listItems[3]);
    }

    void showSingleItemSelectDialog(){
        int defaultItem = position = 1;
        ItemSelectDialog dialog = ItemSelectDialog.newInstance(
                "This is the title",
                dialogItems,
                defaultItem,
                new DialogButton(DialogButton.Type.POSITIVE,"OK"),
                new DialogButton(DialogButton.Type.NEGATIVE,"CANCEL"));

        dialog.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toaster.shortToast(getMainActivity(),"You selected %s", dialogItems[position]);
                        break;
                    default:
                        if(i >= 0) position = i;
                }
            }
        });

        dialog.show(getFragmentManager(), listItems[4]);
    }

    void showItemSelectDialogNoTitle(){
        final ItemSelectDialog dialog = ItemSelectDialog.newInstance(
                null,
                dialogItems);
        dialog.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toaster.shortToast(getMainActivity(),"You selected %s", dialogItems[i]);
                dialog.dismiss();
            }
        });
        dialog.show(getFragmentManager(),listItems[5]);
    }

    void showMultiSelectDialog(){
        final ArrayList<String> selection = new ArrayList<>(dialogItems.length);
        MultiSelectDialog dialog = MultiSelectDialog.newInstance(
                "This is the title",
                dialogItems,
                new DialogButton(DialogButton.Type.POSITIVE,"Show selected"));

        dialog.setOnMultiChoiceClickListener(new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean checked) {
                if(checked)
                    selection.add(dialogItems[position]);
                else
                    selection.remove(dialogItems[position]);
            }
        });

        dialog.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE) {
                    if(selection.size() == 0) {
                        Toaster.shortToast(getMainActivity(), "Nothing selected");
                        return;
                    }

                    java.util.Collections.sort(selection);

                    StringBuilder builder = new StringBuilder("You selected ");

                    builder.append(selection.get(0));

                    for(int i = 1; i < selection.size();i++){
                        builder.append(", ");
                        builder.append(selection.get(i));
                    }

                    builder.append(".");

                    Toaster.longToast(getMainActivity(),builder.toString());
                }
            }
        });
        dialog.show(getFragmentManager(),listItems[6]);
    }

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    public MainActivity getMainActivity(){
        return (MainActivity)(getActivity() == null ? context : getActivity());
    }

}
