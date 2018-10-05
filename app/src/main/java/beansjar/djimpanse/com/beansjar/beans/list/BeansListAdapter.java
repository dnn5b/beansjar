package beansjar.djimpanse.com.beansjar.beans.list;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.ratings.RatingIcon;


public class BeansListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    protected List<Bean> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BeansListAdapter() {
        this.mDataset = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            TextView dateText = (TextView) LayoutInflater.from(parent.getContext()).inflate(R
                    .layout.layout_beans_list_heading, parent, false);
            return new HeadingViewHolder(dateText);

        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .layout_beans_list_item, parent, false);
            return new ItemViewHolder(v);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Bean item = mDataset.get(position);

        if (item.isHeader()) {
            String text = item.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            ((HeadingViewHolder) holder).dateTextView.setText(text);

        } else {
            String text = item.getEvent();
            ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
            itemViewHolder.eventTextView.setText(text);
            itemViewHolder.rating1.colorRedOrHide(item.getRating());
            itemViewHolder.rating2.colorRedOrHide(item.getRating());
            itemViewHolder.rating3.colorRedOrHide(item.getRating());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position).isHeader()) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void refreshData(List<Bean> beans) {
        buildListWithHeadings(beans);
        notifyDataSetChanged();
    }

    /**
     * Adds all passed beans to {@link #mDataset} so they will be shown in the UI. Also adds a
     * header object
     * containing the date into the list.
     *
     * @param beans the list of all beans to show
     */
    protected void buildListWithHeadings(List<Bean> beans) {
        List<Bean> newListWithHeadings = new ArrayList<>();
        if (beans != null && !beans.isEmpty()) {

            // Sort by date
            beans = beans.stream().sorted((b1, b2) -> b1.getDate().isAfter(b2.getDate()) ? 1 :
                    -1).collect(Collectors.toList());

            // Add headings
            newListWithHeadings.add(new Bean(beans.get(0).getDate(), true));
            for (int i = 0; i < beans.size(); i++) {
                Bean bean = beans.get(i);

                if ((beans.size() - 1) == i) {
                    // last bean will be just added
                    newListWithHeadings.add(bean);

                } else {
                    newListWithHeadings.add(bean);

                    // heading if date is different
                    Bean nextBean = beans.get(i + 1);
                    if (!bean.getDate().equals(nextBean.getDate())) {
                        newListWithHeadings.add(new Bean(nextBean.getDate(), true));
                    }
                }
            }
        }
        mDataset.clear();
        mDataset.addAll(newListWithHeadings);
    }

    private static class HeadingViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTextView;

        public HeadingViewHolder(TextView v) {
            super(v);
            dateTextView = v;
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView eventTextView;
        public RatingIcon rating1, rating2, rating3;

        public ItemViewHolder(View v) {
            super(v);
            eventTextView = v.findViewById(R.id.event);
            rating1 = v.findViewById(R.id.rating1);
            rating2 = v.findViewById(R.id.rating2);
            rating3 = v.findViewById(R.id.rating3);
        }
    }
}