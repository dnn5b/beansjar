package beansjar.djimpanse.com.beansjar.beans;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.concurrent.Executors;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.create.CreateBeanFragment;
import beansjar.djimpanse.com.beansjar.beans.create.CreateCallback;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.delete.DeleteCallback;
import beansjar.djimpanse.com.beansjar.beans.list.BeansListFragment;


public class OverviewActivity extends AppCompatActivity implements CreateCallback, DeleteCallback {

    private BeansListFragment mListFragment;
    private FloatingActionButton mFloatingActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(() -> {
            List<Fragment> fragments = fragmentManager.getFragments();
            if (fragments != null) {
                boolean showBtn = false;
                if (fragments.size() == 1 && fragments.get(0) == mListFragment) {
                    showBtn = true;
                }
                showAddBtn(showBtn);
            }

        });

        mFloatingActionBtn = findViewById(R.id.fab);
        mFloatingActionBtn.setOnClickListener(view -> {
            Fragment createFragment = CreateBeanFragment.newInstance(mFloatingActionBtn.getX(),
                    mFloatingActionBtn.getY());
            getSupportFragmentManager().beginTransaction().add(R.id.content, createFragment,
                    BeansListFragment.class.getSimpleName()).addToBackStack(BeansListFragment
                    .class.getSimpleName()).commit();
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            mListFragment = new BeansListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content, mListFragment,
                    mListFragment.getClass().getSimpleName()).disallowAddToBackStack().commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                List<Bean> all = AppDatabase.getInstance(OverviewActivity.this).beanDao().getAll();
                for (Bean bean : all) {
                    AppDatabase.getInstance(OverviewActivity.this).beanDao().delete(bean);
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshList() {
        if (mListFragment != null) {
            mListFragment.refreshOverview();
        }
    }

    private void showAddBtn(boolean isVisible) {
        mFloatingActionBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void beanCreated() {
        refreshList();
    }

    @Override
    public void beanDeleted() {
        refreshList();
    }
}
