package beansjar.djimpanse.com.beansjar.beans.list;


import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;

import static android.support.v7.widget.RecyclerView.VERTICAL;


/**
 * Adapter that shows {@link BeansCardModel} in CardViews.
 */
public class BeanCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static DividerItemDecoration mItemDecoration;

    private final Activity mActivity;
    private final BeanClickedCallback mCallback;

    protected List<BeansCardModel> mDataset;

    public BeanCardsAdapter(Activity activity, BeanClickedCallback callback) {
        this.mDataset = new ArrayList<>();
        this.mActivity = activity;
        this.mCallback = callback;

        mItemDecoration = new DividerItemDecoration(activity, VERTICAL);
        mItemDecoration.setDrawable(activity.getDrawable(R.drawable.divider_bean_list));
    }

    // Create new views (invoked by the layout_beans_card manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.layout_beans_card, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout_beans_card manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BeansCardModel item = mDataset.get(position);

        ViewHolder viewHolder = ((ViewHolder) holder);
        viewHolder.dateTextView.setText(item.getDate()
                                            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        if (item.getImage() != null) {
            item.getImage()
                .loadIntoImageView(viewHolder.titleImageView, 300, 100);
        } else {
            viewHolder.titleImageView.setImageBitmap(null);
        }

        viewHolder.recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        viewHolder.recyclerView.setLayoutManager(layoutManager);
        if (viewHolder.recyclerView.getItemDecorationCount() == 0) {
            viewHolder.recyclerView.addItemDecoration(mItemDecoration);
        }

        RecyclerView.Adapter listAdapter = new BeansListAdapter(item.getBeans(), mCallback);
        viewHolder.recyclerView.setAdapter(listAdapter);
    }

    // Return the size of your dataset (invoked by the layout_beans_card manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void refreshData(List<Bean> beans) {
        List<BeansCardModel> models = BeansCardModel.createModels(beans);
        mDataset.clear();
        mDataset.addAll(models);
        mDataset.sort((b1, b2) -> b1.getDate()
                                    .isBefore(b2.getDate()) ? 1 : -1);
        notifyDataSetChanged();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView titleImageView;
        private TextView dateTextView;
        private RecyclerView recyclerView;

        public ViewHolder(View v) {
            super(v);
            titleImageView = v.findViewById(R.id.titleImg);
            dateTextView = v.findViewById(R.id.dateTxt);
            recyclerView = v.findViewById(R.id.recyclerView);
        }
    }
}