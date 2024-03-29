package beansjar.djimpanse.com.beansjar.beans.list;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.ratings.RatingIcon;
import beansjar.djimpanse.com.beansjar.util.StringUtils;


/**
 * Adapter to show a list of {@link Bean}s.
 */
public class BeansListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /** Max length of event in list. */
    private static final int EVENT_MAX_LENGTH = 25;

    private BeanClickedCallback callback;
    private List<Bean> beans;

    protected BeansListAdapter(List<Bean> beans, BeanClickedCallback callback) {
        this.beans = beans;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .layout_beans_list_item, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bean item = beans.get(position);
        String text = item.getEvent();
        BeansListAdapter.ViewHolder viewHolder = ((BeansListAdapter.ViewHolder) holder);
        viewHolder.eventTextView.setText(StringUtils.cutIfTooLong(text, EVENT_MAX_LENGTH));
        viewHolder.rating1.colorRedOrHide(item.getRating());
        viewHolder.rating2.colorRedOrHide(item.getRating());
        viewHolder.rating3.colorRedOrHide(item.getRating());
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    private void beanItemLongClicked(int itemPosition) {
        callback.onLongClick(beans.get(itemPosition));
    }

    private void beanItemClicked(int itemPosition, int[] viewCenter) {
        callback.onClick(beans.get(itemPosition), viewCenter);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnLongClickListener, View.OnClickListener {

        private BeansListAdapter adapter;
        public TextView eventTextView;
        public RatingIcon rating1, rating2, rating3;

        public ViewHolder(View v, BeansListAdapter adapter) {
            super(v);
            this.adapter = adapter;

            eventTextView = v.findViewById(R.id.event);
            rating1 = v.findViewById(R.id.rating1);
            rating2 = v.findViewById(R.id.rating2);
            rating3 = v.findViewById(R.id.rating3);

            v.setOnLongClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            adapter.beanItemLongClicked(getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View v) {
            int[] viewCenter = new int[2];
            v.getLocationInWindow(viewCenter);
            viewCenter[0] = viewCenter[0] + (v.getWidth() / 2);
            viewCenter[1] = viewCenter[1] + (v.getHeight() / 2);

            adapter.beanItemClicked(getAdapterPosition(), viewCenter);
        }
    }

}