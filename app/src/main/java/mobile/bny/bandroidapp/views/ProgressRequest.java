package mobile.bny.bandroidapp.views;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by BNY on 25/11/2017.
 */

public class ProgressRequest {

    ProgressImageView progressImageView;

    public ProgressRequest(ProgressImageView progressImageView) {
        this.progressImageView = progressImageView;
    }

    public RequestListener<Drawable> getListener() {
        return drawableRequestListener;
    }

    private RequestListener<Drawable> drawableRequestListener = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            progressImageView.hideProgressBar();
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            progressImageView.hideProgressBar();
            return false;
        }
    };
}
