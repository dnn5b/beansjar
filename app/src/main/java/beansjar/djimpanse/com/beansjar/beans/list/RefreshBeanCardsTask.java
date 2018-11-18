package beansjar.djimpanse.com.beansjar.beans.list;


import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import beansjar.djimpanse.com.beansjar.database.AppDatabase;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanDao;


public class RefreshBeanCardsTask extends AsyncTask<Void, Void, List<Bean>> {

    private final BeanCardsAdapter adapter;
    private final SwipeRefreshLayout refreshLayout;
    private Activity activity;

    public RefreshBeanCardsTask(Activity activity, BeanCardsAdapter adapter,
                                SwipeRefreshLayout refreshLayout) {
        this.activity = activity;
        this.adapter = adapter;
        this.refreshLayout = refreshLayout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        refreshLayout.setRefreshing(true);
    }

    @Override
    protected List<Bean> doInBackground(Void... params) {
        BeanDao beanDao = AppDatabase.getInstance(activity).beanDao();
        return beanDao.getAll();
    }

    @Override
    protected void onPostExecute(List<Bean> beans) {
        if (activity == null) {
            return;
        }
        adapter.refreshData(beans);

        if (refreshLayout.isRefreshing()) {
            activity.runOnUiThread(() -> {
                refreshLayout.setRefreshing(false);
            });
        }
    }

}
