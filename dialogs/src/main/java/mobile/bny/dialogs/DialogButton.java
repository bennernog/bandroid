package mobile.bny.dialogs;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by BNY on 21/11/2017.
 */

public class DialogButton implements Parcelable{

//    public DialogButton() {}
    public DialogButton(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public int getButtonId() {
        return type.getId();
    }

    private Type type;
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        POSITIVE (DialogInterface.BUTTON_POSITIVE),
        NEUTRAL (DialogInterface.BUTTON_NEUTRAL),
        NEGATIVE (DialogInterface.BUTTON_NEGATIVE);

        int id;

        Type(int id){this.id = id;}

        public static Type valueOf(int id) {
            for (Type button : values()) {
                if (button.id == id) return button;
            }
            throw new IllegalArgumentException();
        }

        public int getId() {
            return id;
        }
    }

    //Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(type.getId());
        parcel.writeString(text);

    }

    public static final Creator<DialogButton> CREATOR
            = new Creator<DialogButton>() {

        public DialogButton createFromParcel(Parcel in) {
            return new DialogButton(in);
        }

        public DialogButton[] newArray(int size) {
            return new DialogButton[size];
        }

    };

    private DialogButton(Parcel in) {
        type = Type.valueOf(in.readInt());
        text = in.readString();
    }
}
