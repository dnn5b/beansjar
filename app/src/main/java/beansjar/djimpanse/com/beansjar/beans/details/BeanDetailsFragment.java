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

    private Bean mBean;

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

        TextView eventTxt = view.findViewById(R.id.eventTxt);
        eventTxt.setText(mBean.getEvent());

        TextView dateTxt = view.findViewById(R.id.dateTxt);
        dateTxt.setText(mBean.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        RatingIcon rating1 = view.findViewById(R.id.rating1);
        rating1.colorRedOrHide(mBean.getRating());

        RatingIcon rating2 = view.findViewById(R.id.rating2);
        rating2.colorRedOrHide(mBean.getRating());

        RatingIcon rating3 = view.findViewById(R.id.rating3);
        rating3.colorRedOrHide(mBean.getRating());

        return view;
    }

}
