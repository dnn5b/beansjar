package beansjar.djimpanse.com.beansjar.beans.create;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanRatingEnum;


public class CreateBeanFragment extends Fragment {

    private EditText eventTxt;
    private CalendarView calendarView;

    private ImageView rating1;
    private ImageView rating2;
    private ImageView rating3;

    private Bean mBean;

    private OnFragmentInteractionListener mListener;

    public CreateBeanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement " +
                    "OnFragmentInteractionListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBean = new Bean();
        mBean.setDate(LocalDate.now());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beans_create, container, false);

        eventTxt = view.findViewById(R.id.event);
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calView, year, month, day) -> {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
            mBean.setDate(LocalDate.of(year, month, day));
        });
        rating1 = view.findViewById(R.id.rating1);
        applyColorFilterOnView(rating1, false);
        rating1.setOnClickListener(evt -> {
            mBean.setRating(BeanRatingEnum.LOW);
            applyColorFilterOnView(rating1, true);
            applyColorFilterOnView(rating2, false);
            applyColorFilterOnView(rating3, false);
        });

        rating2 = view.findViewById(R.id.rating2);
        applyColorFilterOnView(rating2, false);
        rating2.setOnClickListener(evt -> {
            mBean.setRating(BeanRatingEnum.MEDIUM);
            applyColorFilterOnView(rating1, true);
            applyColorFilterOnView(rating2, true);
            applyColorFilterOnView(rating3, false);
        });

        rating3 = view.findViewById(R.id.rating3);
        applyColorFilterOnView(rating3, false);
        rating3.setOnClickListener(evt -> {
            mBean.setRating(BeanRatingEnum.HIGH);
            applyColorFilterOnView(rating1, true);
            applyColorFilterOnView(rating2, true);
            applyColorFilterOnView(rating3, true);

        });

        view.findViewById(R.id.btn_create).setOnClickListener(evt -> createBean());
        view.findViewById(R.id.btn_cancel).setOnClickListener(evt -> close());

        return view;
    }

    private void applyColorFilterOnView(ImageView imageView, boolean apply) {
        if (apply) {
            imageView.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        } else {
            imageView.clearColorFilter();
        }
    }

    private void close() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        mListener.showAddBtn(true);
    }

    public void createBean() {
        mBean.setEvent(eventTxt.getText().toString());

        Executors.newSingleThreadScheduledExecutor().execute(() ->
            AppDatabase.getInstance(getActivity()).beanDao().insertAll(mBean)
        );

        close();
        mListener.beanCreated();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void beanCreated();

        void showAddBtn(boolean isVisible);
    }
}
