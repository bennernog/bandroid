package mobile.bny.views;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by BNY on 09/11/2017.
 */

public class MarqueeToolbar extends Toolbar {

    TextView title, subTitle;

    public MarqueeToolbar(Context context) {
        super(context);
    }

    public MarqueeToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //for Title
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (!reflected) {
            reflected = reflectTitle();
        }
        selectTitle();
    }

    @Override
    public void setTitle(int resId) {
        super.setTitle(resId);
        if (!reflected) {
            reflected = reflectTitle();
        }
        selectTitle();
    }

    boolean reflected = false;
    private boolean reflectTitle() {
        try {
            Field field = Toolbar.class.getDeclaredField("mTitleTextView");
            field.setAccessible(true);
            title = (TextView) field.get(this);
            title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            title.setMarqueeRepeatLimit(-1);
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectTitle() {
        if (title != null)
            title.setSelected(true);
    }

    // for Subtitle
    @Override

    public void setSubtitle(CharSequence subTitle) {
        super.setSubtitle(subTitle);
        if (!reflectedSub) {
            reflectedSub = reflectSubTitle();
        }
        selectSubtitle();
    }


    @Override
    public void setSubtitle(int resId) {
        super.setSubtitle(resId);
        if (!reflected) {
            reflectedSub = reflectSubTitle();
        }
        selectSubtitle();
    }

    boolean reflectedSub = false;
    private boolean reflectSubTitle() {
        try {
            Field field = Toolbar.class.getDeclaredField("mSubtitleTextView");
            field.setAccessible(true);
            subTitle = (TextView) field.get(this);
            subTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            subTitle.setMarqueeRepeatLimit(-1);
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectSubtitle() {
        if (subTitle != null)
            subTitle.setSelected(true);
    }

}
