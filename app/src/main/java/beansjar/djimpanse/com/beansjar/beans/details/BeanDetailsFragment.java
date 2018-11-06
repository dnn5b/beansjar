package beansjar.djimpanse.com.beansjar.beans.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.animations.AnimationSettings;
import beansjar.djimpanse.com.beansjar.animations.Animator;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.ratings.RatingIcon;


public class BeanDetailsFragment extends Fragment {

    private static final String START_POSITION_X = "startPositionX";
    private static final String START_POSITION_Y = "startPositionY";
    private static final String ARG_BEAN = "fragment_argument_bean";
    private static final  DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private AnimationSettings settings;
    private View mView;

    protected Bean mBean;

    public BeanDetailsFragment() {
        // Required empty public constructor
    }

    public static BeanDetailsFragment newInstance(Bean bean, float startPosX, float startPosY) {
        BeanDetailsFragment fragment = new BeanDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_BEAN, bean);
        args.putFloat(START_POSITION_X, startPosX);
        args.putFloat(START_POSITION_Y, startPosY);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBean = getArguments().getParcelable(ARG_BEAN);
        }

        // Entry and exit animation settings
        settings = AnimationSettings.constructRevealAnimation(getActivity(), getArguments()
                        .getFloat(START_POSITION_X), getArguments().getFloat(START_POSITION_Y), 600,
                getActivity().getColor(R.color.colorAccent), getActivity().getColor(R.color
                        .default_background));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_beans_details, container, false);

        // Create and start reveal animation
        //new Animator(settings).startCircularEnter(mView);

        ImageView imageView = mView.findViewById(R.id.imageView);

        mBean.getImage().loadIntoImageView(imageView);

        showEventText(mView.findViewById(R.id.eventTxt));
        showDateText(mView.findViewById(R.id.dateTxt));

        showRating(mView.findViewById(R.id.rating1));
        showRating(mView.findViewById(R.id.rating2));
        showRating(mView.findViewById(R.id.rating3));

        return mView;
    }

    protected void showEventText(TextView eventTxt) {
        if (eventTxt != null) {
            eventTxt.setText(mBean.getEvent());
        }
    }

    protected void showDateText(TextView dateTxt) {
        if (dateTxt != null && mBean.getDate() != null) {
            dateTxt.setText(FORMATTER.format(mBean.getDate()));
        }
    }

    protected void showRating(RatingIcon rating) {
        if (rating != null) {
            rating.colorRedOrHide(mBean.getRating());
        }
    }

}
