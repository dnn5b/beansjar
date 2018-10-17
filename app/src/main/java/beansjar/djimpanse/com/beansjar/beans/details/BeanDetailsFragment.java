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
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.ratings.RatingIcon;


public class BeanDetailsFragment extends Fragment {

    private static final String ARG_BEAN = "fragment_argument_bean";

    private static final  DateTimeFormatter mFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected Bean mBean;

    public BeanDetailsFragment() {
        // Required empty public constructor
    }

    public static BeanDetailsFragment newInstance(Bean bean) {
        BeanDetailsFragment fragment = new BeanDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BEAN, bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBean = getArguments().getParcelable(ARG_BEAN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beans_details, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        mBean.getImage().loadIntoImageView(imageView);

        showEventText(view.findViewById(R.id.eventTxt));
        showDateText(view.findViewById(R.id.dateTxt));

        showRating(view.findViewById(R.id.rating1));
        showRating(view.findViewById(R.id.rating2));
        showRating(view.findViewById(R.id.rating3));

        return view;
    }

    protected void showEventText(TextView eventTxt) {
        if (eventTxt != null) {
            eventTxt.setText(mBean.getEvent());
        }
    }

    protected void showDateText(TextView dateTxt) {
        if (dateTxt != null && mBean.getDate() != null) {
            dateTxt.setText(mFormatter.format(mBean.getDate()));
        }
    }

    protected void showRating(RatingIcon rating) {
        if (rating != null) {
            rating.colorRedOrHide(mBean.getRating());
        }
    }

}
