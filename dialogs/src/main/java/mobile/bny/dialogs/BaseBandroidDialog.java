package mobile.bny.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BNY on 16/11/2017.
 */

public abstract class BaseBandroidDialog extends DialogFragment {
    List<Dialog.OnDismissListener> listeners;


    public BaseBandroidDialog() {
        super();
        listeners = new ArrayList<>();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {

        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();

        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        //resize

        window.setLayout((int) (size.x *0.90), (int) (size.y *0.9));
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);


        super.onResume();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        if(listeners != null && listeners.size()>0)
            for(Dialog.OnDismissListener listener : listeners)
                if(listener != null)
                    listener.onDismiss(dialog);

        isShowing = false;

        super.onDismiss(dialog);
    }

    public  void setOnDismissListener(Dialog.OnDismissListener onDismissListener){
        if(onDismissListener != null && listeners != null)
            listeners.add(onDismissListener);
    }


    private static boolean isShowing= false;
    public void show(FragmentManager manager){
        if (!isShowing  && !manager.isStateSaved()) {
            show(manager, getTag());
            isShowing = true;
        }
    }
}
