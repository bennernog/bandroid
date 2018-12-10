package mobile.bny.bandroidapp.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import mobile.bny.bandroidapp.R;


public class ProgressImageView extends RelativeLayout {

    public ImageView ImageView;
    public ProgressBar ProgressBar;
    public boolean isPortrait;

    public ProgressImageView(Context context) {
        super(context);
        init(context, null, Style.NONE);
    }

    public ProgressImageView(Context context, Style style) {
        super(context);
        init(context, null, style);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, Style.NONE);
    }

    public ProgressImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, Style.NONE);
    }

    private void init(Context context, AttributeSet attrs, Style style) {
        //Check attributes if view is generated from layout resource
        int img = 0;
        if(attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressImageView, 0, 0);
            int styleId=0;
            if (a.hasValue(R.styleable.ProgressImageView_progressImageViewStyle)) {
                styleId = a.getInt(R.styleable.ProgressImageView_progressImageViewStyle, 0);
                img = a.getInt(R.styleable.ProgressImageView_android_src,0);
            }
            a.recycle();
            style = style == Style.NONE ? Style.valueOf(styleId) : style;
        }
        // load resource matching desired style (large or small progessbar)
        switch (style){
            case LARGE:
                inflate(context, R.layout.view_progress_imageview_large,this);
                break;
            default:
                inflate(context, R.layout.view_progress_imageview_small,this);
                break;
        }

        ImageView = findViewById(R.id.imageView);
        if(img != 0)
            ImageView.setImageResource(img);

        LayoutParams ivParams = new LayoutParams(ImageView.getLayoutParams());
        ivParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        ImageView.setLayoutParams(ivParams);

        ProgressBar = findViewById(R.id.progressBar);
        LayoutParams pbParams = new LayoutParams(ProgressBar.getLayoutParams());
        pbParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        ProgressBar.setLayoutParams(pbParams);
    }

    public void hideProgressBar(){
        if(ProgressBar != null){
            ProgressBar.setVisibility(GONE);
        }
    }

    public void showProgressBar(){
        if(ProgressBar != null){
            ProgressBar.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isPortrait = (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    public enum Style {
        NONE(0),
        SMALL(1),
        LARGE(2);

        int id;

        Style(int id){this.id = id;}

        static Style valueOf(int id) {
            for (Style style : values()) {
                if (style.id == id) return style;
            }
            throw new IllegalArgumentException();
        }
    }
}
