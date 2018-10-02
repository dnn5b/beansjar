package beansjar.djimpanse.com.beansjar.beans.data;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.beans.overview.MyAdapter;


public class RefreshBeansOverviewTask extends AsyncTask<Void, Void, List<Bean>> {

    private final Context context;
    private final MyAdapter adapter;
    private final SwipeRefreshLayout refreshLayout;

    //Prevent leak
    private WeakReference<Activity> weakActivity;

    public RefreshBeansOverviewTask(Activity activity, MyAdapter adapter, Context context, SwipeRefreshLayout refreshLayout) {
        weakActivity = new WeakReference<>(activity);
        this.context = context;
        this.adapter = adapter;
        this.refreshLayout = refreshLayout;
    }

    @Override
    protected List<Bean> doInBackground(Void... params) {
        BeanDao beanDao = AppDatabase.getInstance(context).beanDao();
        return beanDao.getAll();
    }

    @Override
    protected void onPostExecute(List<Bean> beans) {
        Activity activity = weakActivity.get();
        if (activity == null) {
            return;
        }
        adapter.refreshData(beans);

        if (refreshLayout.isRefreshing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            });

        }
    }

}
