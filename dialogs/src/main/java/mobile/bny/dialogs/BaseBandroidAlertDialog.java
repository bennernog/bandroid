package mobile.bny.dialogs;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by BNY on 24/11/2017.
 */

public abstract class BaseBandroidAlertDialog extends DialogFragment implements
        DialogInterface.OnMultiChoiceClickListener,
        DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener,
        DialogInterface.OnCancelListener {

    //Keys for passing data to bundle
    protected static final String TITLE = ".bandroid.dialog.title.key";
    protected static final String TITLE_RES = ".bandroid.dialog.title.from.resource.key";
    protected static final String MESSAGE = ".bandroid.dialog.message.key";
    protected static final String MESSAGE_RES = ".bandroid.dialog.message.from.resource.key";
    protected static final String BUTTONS = ".bandroid.dialog.buttons.key";
    protected static final String ITEMS = ".bandroid.dialog.items.key";
    protected static final String ITEMS_RES = ".bandroid.dialog.items.from.resource.key";
    protected static final String CHECKED_STATE = ".bandroid.dialog.checked.state.key";
    protected static final String DEFAULT_ITEM = ".bandroid.dialog.default.item.key";
    protected static final String USE_RESOURCE= ".bandroid.dialog.use.resource.key";
    protected static final int NO_DEFAULT_ITEM = -24;

    //Common local variables
    protected String title;
    protected String message;
    protected String[] items;
    protected DialogButton[] buttons;

    //Common DialogBuilder methods
    protected void addButtons(AlertDialog.Builder builder, DialogButton[] buttons){

        if(buttons != null && buttons.length > 0) {
            for (DialogButton button : buttons)

                switch (button.getButtonId()) {
                    case DialogInterface.BUTTON_POSITIVE:
                        builder.setPositiveButton(button.getText(), this);
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        builder.setNeutralButton(button.getText(), this);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        builder.setNegativeButton(button.getText(), this);
                        break;
                }
        }

        //Prep listeners
        builder.setOnDismissListener(this);
        builder.setOnCancelListener(this);
        builder.setCancelable(true);
    }

    protected void setTitle(AlertDialog.Builder builder, String title){

        if(TextUtils.isEmpty(title)) { //No titleview if no title

            setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());

        } else { //Custom title view to handle longer titles
            TextView textView = new TextView(getContext().getApplicationContext());
            textView.setText(title);
            TextViewCompat.setTextAppearance(textView, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
            int horizontalPadding = (int) getResources().getDimension(R.dimen.dialog_horizontal_padding);
            int topPadding = (int) getResources().getDimension(R.dimen.dialog_top_padding);
            textView.setPadding(horizontalPadding, topPadding, horizontalPadding, 0);
            builder.setCustomTitle(textView);

        }
    }

    //All listeners

    //handle onClick event
    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if (clickListeners != null && clickListeners.size() > 0)
            for (DialogInterface.OnClickListener listener : clickListeners)
                if (listener != null)
                    listener.onClick(dialogInterface, button);
    }

    private ArrayList<DialogInterface.OnClickListener > clickListeners;

    public final void setOnClickListener(DialogInterface.OnClickListener  listener) {
        if(clickListeners == null)
            clickListeners = new ArrayList<>();

        this.clickListeners.add(listener);
    }

    public final void removeOnClickListener(DialogInterface.OnClickListener  listener) {
        if(clickListeners != null && clickListeners.contains(listener))
            clickListeners.remove(listener);
    }

    public final void clearOnClickListeners() {
        if(clickListeners == null)
            return;

        this.clickListeners.clear();
        this.clickListeners = null;
    }

    //Handle onMultiClick event
    @Override
    public void onClick(DialogInterface dialogInterface, int position, boolean checked) {
        if(multiChoiceClickListeners != null && multiChoiceClickListeners.size() >0)
            for(DialogInterface.OnMultiChoiceClickListener listener : multiChoiceClickListeners)
                listener.onClick(dialogInterface,position,checked);
    }

    private ArrayList<DialogInterface.OnMultiChoiceClickListener > multiChoiceClickListeners;

    public final void setOnMultiChoiceClickListener(DialogInterface.OnMultiChoiceClickListener  listener) {
        if(multiChoiceClickListeners == null)
            multiChoiceClickListeners = new ArrayList<>();

        this.multiChoiceClickListeners.add(listener);
    }

    public final void removeOnMultiChoiceClickListener(DialogInterface.OnMultiChoiceClickListener  listener) {
        if(multiChoiceClickListeners != null && multiChoiceClickListeners.contains(listener))
            multiChoiceClickListeners.remove(listener);
    }

    public final void clearOnMultiChoiceClickListeners() {
        if(multiChoiceClickListeners == null)
            return;

        this.multiChoiceClickListeners.clear();
        this.multiChoiceClickListeners = null;
    }

    //Handle onCancelevent
    @Override
    public void onCancel(DialogInterface dialogInterface){
        if(onCancelListeners != null && onCancelListeners.size() >0)
            for(DialogInterface.OnCancelListener listener : onCancelListeners)
                listener.onCancel(dialogInterface);

        super.onCancel(dialogInterface);
    }

    private ArrayList<DialogInterface.OnCancelListener > onCancelListeners;

    public final void setOnCancelListener(DialogInterface.OnCancelListener  listener) {
        if(onCancelListeners == null)
            onCancelListeners = new ArrayList<>();

        this.onCancelListeners.add(listener);
    }

    public final void removeOnCancelListener(DialogInterface.OnCancelListener  listener) {
        if(onCancelListeners != null && onCancelListeners.contains(listener))
            onCancelListeners.remove(listener);
    }

    public final void clearOnCancelListeners() {
        if(onCancelListeners == null)
            return;

        this.onCancelListeners.clear();
        this.onCancelListeners = null;
    }

    //Handle onDismiss event
    @Override
    public void onDismiss(DialogInterface dialogInterface){
        if(onCancelListeners != null && onCancelListeners.size() >0)
            for(DialogInterface.OnDismissListener listener : onDismissListeners)
                listener.onDismiss(dialogInterface);

        isShowing = false;
        super.onDismiss(dialogInterface);
    }

    private ArrayList<DialogInterface.OnDismissListener > onDismissListeners;

    public final void setOnDismissListener(DialogInterface.OnDismissListener  listener) {
        if(onDismissListeners == null)
            onDismissListeners = new ArrayList<>();

        this.onDismissListeners.add(listener);
    }

    public final void removeOnDismissListener(DialogInterface.OnDismissListener  listener) {
        if(onDismissListeners != null && onDismissListeners.contains(listener))
            onDismissListeners.remove(listener);
    }

    public final void clearOnDismissListeners() {
        if(onDismissListeners == null)
            return;

        this.onDismissListeners.clear();
        this.onDismissListeners = null;
    }

    //Check buttons for usability
    protected static void checkButtons(final DialogButton[] buttons){
        if(buttons != null && buttons.length>0) {
            if (buttons.length > 3)
                throw new IndexOutOfBoundsException(String.format(
                        "You cannot have  %s buttons in a dialog, the maximum is 3",
                        buttons.length));
            else if (hasDuplicateTypes(buttons))
                throw new IllegalArgumentException("Cannot have more than one button of the same type");
        }
    }

    private static boolean hasDuplicateTypes(final DialogButton[] dialogButtons) {

        Set<Integer> CheckSet = new HashSet<Integer>();

        for (DialogButton button : dialogButtons) {

            if (CheckSet.contains(button.getButtonId()))
                return true;

            CheckSet.add(button.getButtonId());
        }

        return false;
    }


    private static boolean isShowing= false;
    public void show(FragmentManager manager){
        if (!isShowing  && !manager.isStateSaved()) {
            show(manager, getTag());
            isShowing = true;
        }
    }
}
