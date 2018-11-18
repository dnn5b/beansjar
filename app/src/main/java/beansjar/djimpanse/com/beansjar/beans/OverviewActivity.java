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
        mFloatingActionBtn.setOnClickListener(view -> fabClicked());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            mListFragment = new BeansListFragment();
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.content, mListFragment, mListFragment.getClass()
                                                                                      .getSimpleName())
                                       .disallowAddToBackStack()
                                       .commit();
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

    private void refreshList() {
        if (mListFragment != null) {
            mListFragment.refreshOverview();
        }
    }

    // TODO sometimes the add button isn't shown after the create fragment is closed.
    private void showAddBtn(boolean isVisible) {
        mFloatingActionBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * If the floating action button is clicked the {@link CreateBeanFragment} is shown.
     */
    private void fabClicked() {
        float fabCenterX = mFloatingActionBtn.getX() + mFloatingActionBtn.getHeight() / 2;
        float fabCenterY = mFloatingActionBtn.getY() - mFloatingActionBtn.getHeight() / 2;

        Fragment createFragment = CreateBeanFragment.newInstance(fabCenterX, fabCenterY);

        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.content, createFragment, BeansListFragment.class.getSimpleName())
                                   .addToBackStack(BeansListFragment.class.getSimpleName())
                                   .commit();
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
