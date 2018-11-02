package beansjar.djimpanse.com.beansjar.beans.create;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.time.LocalDate;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.animations.AnimationSettings;
import beansjar.djimpanse.com.beansjar.animations.Animator;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanRatingEnum;
import beansjar.djimpanse.com.beansjar.images.Image;
import beansjar.djimpanse.com.beansjar.permissions.PermissionsHandler;


public class CreateBeanFragment extends Fragment implements Animator.OnDismissedListener {

    private static final String START_POSITION_X = "startPositionX";
    private static final String START_POSITION_Y = "startPositionY";

    private static final int INTENT_SELECT_IMAGE = 1337;

    private EditText eventTxt;
    private CalendarView calendarView;
    private ImageView rating1;
    private ImageView rating2;
    private ImageView rating3;
    private Button selectImgBtn;
    private Bean mBean;
    private CreateCallback mListener;
    private Uri selectedImageUri;
    private AnimationSettings settings;
    private View mView;

    public CreateBeanFragment() {
        // Required empty public constructor
    }

    public static CreateBeanFragment newInstance(float startPosX, float startPosY) {
        CreateBeanFragment fragment = new CreateBeanFragment();

        Bundle args = new Bundle();
        args.putFloat(START_POSITION_X, startPosX);
        args.putFloat(START_POSITION_Y, startPosY);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateCallback) {
            mListener = (CreateCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement " + "CreateCallback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBean = new Bean();
        mBean.setDate(LocalDate.now());

        // Entry and exit animation settings
        settings = AnimationSettings.constructRevealAnimation(getActivity(), getArguments()
                .getFloat(START_POSITION_X), getArguments().getFloat(START_POSITION_Y), 700,
                getActivity().getColor(R.color.colorAccent), getActivity().getColor(R.color
                        .default_background));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout_beans_card for this fragment
        mView = inflater.inflate(R.layout.fragment_beans_create, container, false);

        // Create and start reveal animation
        new Animator(settings).startCircularEnter(mView);

        eventTxt = mView.findViewById(R.id.event);
        calendarView = mView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calView, year, month, day) -> {
            // Month is 0 based in widget
            mBean.setDate(LocalDate.of(year, month + 1, day));
        });
        rating1 = mView.findViewById(R.id.rating1);
        applyColorFilterOnView(rating1, false);
        rating1.setOnClickListener(evt -> {
            mBean.setRating(BeanRatingEnum.LOW);
            applyColorFilterOnView(rating1, true);
            applyColorFilterOnView(rating2, false);
            applyColorFilterOnView(rating3, false);
        });

        rating2 = mView.findViewById(R.id.rating2);
        applyColorFilterOnView(rating2, false);
        rating2.setOnClickListener(evt -> {
            mBean.setRating(BeanRatingEnum.MEDIUM);
            applyColorFilterOnView(rating1, true);
            applyColorFilterOnView(rating2, true);
            applyColorFilterOnView(rating3, false);
        });

        rating3 = mView.findViewById(R.id.rating3);
        applyColorFilterOnView(rating3, false);
        rating3.setOnClickListener(evt -> {
            mBean.setRating(BeanRatingEnum.HIGH);
            applyColorFilterOnView(rating1, true);
            applyColorFilterOnView(rating2, true);
            applyColorFilterOnView(rating3, true);

        });

        mView.findViewById(R.id.btn_create).setOnClickListener(evt -> createBean());
        mView.findViewById(R.id.btn_cancel).setOnClickListener(evt -> close());

        selectImgBtn = mView.findViewById(R.id.btn_select_image);
        selectImgBtn.setOnClickListener(evt -> selectImage());

        return mView;
    }

    private void applyColorFilterOnView(ImageView imageView, boolean apply) {
        if (apply) {
            imageView.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        } else {
            imageView.clearColorFilter();
        }
    }

    /**
     * Triggers an intent with the request code {@link #INTENT_SELECT_IMAGE}. The result will be
     * handled in {@link #onActivityResult(int, int, Intent)}.
     */
    private void selectImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, INTENT_SELECT_IMAGE);
    }

    /**
     * Handles the result of an intent with the request code {@link #INTENT_SELECT_IMAGE}.
     * Automatically called by the framework.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the intent's result data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case INTENT_SELECT_IMAGE:
                    selectedImageUri = data.getData();
                    selectImgBtn.setBackgroundResource(R.color.colorPrimary);
                    break;
            }
        }
    }

    /**
     * Hides the keyboard if showing.
     */
    private void close() {
        // Check if no mView has focus and then hide the keyboard
        View focusedView = getActivity().getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
                    .INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }

        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().remove(this).commit();
        manager.popBackStack();

        // TODO Create and start exit animation
        //new Animator(settings).startCircularExit(mView, this);
    }

    public void createBean() {
        mBean.setEvent(eventTxt.getText().toString());

        if (mBean.isValid()) {
            // Save image to internal storage
            if (selectedImageUri != null) {
                PermissionsHandler permissionsHandler = new PermissionsHandler(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                if (!permissionsHandler.isGranted()) {
                    permissionsHandler.request();
                }
                Image image = new Image(selectedImageUri);
                String filePath = image.saveToInternalStorage(getActivity());
                mBean.setImagePath(filePath);
            }

            // Trigger creation
            new CreateBeanTask(getActivity(), mBean, mListener).execute();
            /*Executors.newSingleThreadScheduledExecutor().execute(() -> AppDatabase.getInstance
                    (getActivity()).beanDao().insertAll(mBean));

            mListener.beanCreated();*/
            close();

        } else {
            Toast.makeText(getActivity(), R.string.beans_create_fragment_toast_data_missing,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDismissed() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().remove(this).commit();
        manager.popBackStack();
    }
}
