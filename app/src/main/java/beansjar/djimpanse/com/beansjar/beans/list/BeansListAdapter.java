package beansjar.djimpanse.com.beansjar.beans.list;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;


public class BeansListAdapter extends RecyclerView.Adapter<BeansListAdapter.MyViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Bean> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView mTextView;

        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BeansListAdapter() {
        this.mDataset = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BeansListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView v;
        if (viewType == TYPE_HEADER) {
            v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .layout_beans_list_heading, parent, false);
        } else {
            v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .layout_beans_list_item, parent, false);

        }
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Bean item = mDataset.get(position);

        String text;
        if (item.isHeader()) {
            text = item.getDate().format(DateTimeFormatter.ISO_DATE);
        } else {
            text = item.getEvent();
            text += item.getRating() == null ? "" : " (" + item.getRating().getValue() + ")";

        }
        holder.mTextView.setText(text);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void refreshData(List<Bean> beans) {
        if (beans != null && !beans.isEmpty()) {

            List<Bean> newListWithHeadings = new ArrayList<>();

            // Add first heading
            newListWithHeadings.add(new Bean(beans.get(0).getDate(), true));

            for (int i = 0; i < (beans.size() - 1); i++) {
                Bean bean = beans.get(i);
                Bean nextBean = beans.get(i + 1);
                if (bean.getDate().isEqual((nextBean.getDate()))) {
                    newListWithHeadings.add(new Bean(nextBean.getDate(), true));
                }
                newListWithHeadings.add(bean);
            }

            mDataset.clear();
            mDataset.addAll(newListWithHeadings);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position).isHeader()) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }
}