package mobile.bny.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by BNY on 16/11/2017.
 */

public class SmartScrollView extends ScrollView {
    private ArrayList<ScrollViewListener> scrollViewListeners;

    public SmartScrollView(Context context) {
        super(context);
    }

    public SmartScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        if(scrollViewListeners == null)
            scrollViewListeners = new ArrayList<>();

        this.scrollViewListeners.add(scrollViewListener);
    }
    public void removeScrollViewListener(ScrollViewListener scrollViewListener) {
        if(scrollViewListener == null)
            return;
        else if(scrollViewListeners.contains(scrollViewListener))
            this.scrollViewListeners.remove(scrollViewListener);
    }

    public void clearScrollViewListeners(){
        scrollViewListeners = null;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if(scrollViewListeners != null && scrollViewListeners.size() > 0)
            for(ScrollViewListener scrollViewListener : scrollViewListeners)
                scrollViewListener.onScrollChanged(this, x, y);

    }

    public interface ScrollViewListener {

        void onScrollChanged(SmartScrollView scrollView, int x, int y);

    }
}
