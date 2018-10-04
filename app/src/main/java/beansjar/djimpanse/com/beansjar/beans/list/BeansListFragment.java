package beansjar.djimpanse.com.beansjar.beans.list;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.RefreshBeansOverviewTask;


public class BeansListFragment extends Fragment {

    private RefreshBeansOverviewTask refreshTask;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BeansListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public BeansListFragment() {
        // Required empty public constructor
    }

    public void setupList(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.overview_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new BeansListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        refreshOverview();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beans_list, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
                Log.i("TEST", "onRefresh called from SwipeRefreshLayout");

                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                refreshOverview();
        });

        setupList(view);

        return view;
    }

    public void refreshOverview() {
        if (refreshTask == null || refreshTask.getStatus() != AsyncTask.Status.RUNNING) {
            refreshTask = new RefreshBeansOverviewTask(getActivity(), mAdapter,
                    getActivity(), swipeRefreshLayout);
            refreshTask.execute();
        }
    }

}
