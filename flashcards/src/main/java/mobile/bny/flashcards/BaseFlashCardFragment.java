package mobile.bny.flashcards;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
/**
 * Created by BNY on 11/11/2017.
 */

public abstract class BaseFlashCardFragment extends Fragment {

    private Handler mHandler = new Handler();
    private boolean mShowingBack = false;
    FrameLayout frameLayout;

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public void setFrameLayout(FrameLayout frameLayout) {
        this.frameLayout = frameLayout;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_flip, container, false);
//        frameLayout = (FrameLayout) rootView.findViewById(R.id.child_frame);
//
//        return rootView;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(frameLayout.getId(), getFrontCard())
                    .commit();
        } else {
            mShowingBack = (getChildFragmentManager().getBackStackEntryCount() > 0);
        }
        getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                mShowingBack = (getChildFragmentManager().getBackStackEntryCount() > 0);
                getActivity().invalidateOptionsMenu();
            }
        });
    }

    public abstract Fragment getFrontCard();
    public abstract Fragment getBackCard();
    public abstract void onFlip(boolean isShowingBack);

    public void flipFragment() {
        if (mShowingBack) {
            getChildFragmentManager().popBackStack();

            mShowingBack = false;
            onFlip(mShowingBack);

            return;
        }
        mShowingBack = true;

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_enter, R.animator.card_flip_exit,
                        R.animator.card_flip_pop_enter, R.animator.card_flip_pop_exit)
                .replace(frameLayout.getId(), getBackCard())
                .addToBackStack(null)
                .commit();

        onFlip(mShowingBack);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                getActivity().invalidateOptionsMenu();
            }
        });
    }
}
