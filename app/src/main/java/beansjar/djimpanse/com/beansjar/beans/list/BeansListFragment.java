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
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.delete.ConfirmDeleteBeanDialog;
import beansjar.djimpanse.com.beansjar.beans.details.BeanDetailsFragment;


public class BeansListFragment extends Fragment implements BeanClickedCallback {

    private RefreshBeanCardsTask refreshTask;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BeanCardsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public BeansListFragment() {
        // Required empty public constructor
    }

    public void setupList(View view) {
        mRecyclerView = view.findViewById(R.id.overview_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout_beans_card size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout_beans_card manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new BeanCardsAdapter(getActivity(), this);
        mRecyclerView.setAdapter(mAdapter);
        refreshOverview();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout_beans_card for this fragment
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
            refreshTask = new RefreshBeanCardsTask(getActivity(), mAdapter,
                    getActivity(), swipeRefreshLayout);
            refreshTask.execute();
        }
    }

    @Override
    public void onLongClick(Bean bean) {
        new ConfirmDeleteBeanDialog(getActivity(), bean).show();
    }

    @Override
    public void onClick(Bean bean) {
        BeanDetailsFragment detailsFragment = BeanDetailsFragment.newInstance(bean);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, detailsFragment, BeanDetailsFragment.class.getSimpleName())
                .addToBackStack(BeanDetailsFragment.class.getSimpleName())
                .commit();
    }
}
