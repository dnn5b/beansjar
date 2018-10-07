package beansjar.djimpanse.com.beansjar.beans;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;
import java.util.concurrent.Executors;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.create.CreateBeanFragment;
import beansjar.djimpanse.com.beansjar.beans.create.CreateBeanFragment
        .OnFragmentInteractionListener;
import beansjar.djimpanse.com.beansjar.beans.create.CreateCallback;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.list.BeansListFragment;


public class OverviewActivity extends AppCompatActivity implements NavigationView
        .OnNavigationItemSelectedListener, CreateCallback, OnFragmentInteractionListener {

    private FrameLayout content;
private BeansListFragment listFragment;
    private FloatingActionButton floatingActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionBtn = findViewById(R.id.fab);
        floatingActionBtn.setOnClickListener(view -> {
                Fragment newFragment = new CreateBeanFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content, newFragment, "tes")
                        .disallowAddToBackStack()
                        .commit();
                showAddBtn(false);
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        content = findViewById(R.id.content);
        if (savedInstanceState == null) {
            listFragment = new BeansListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, listFragment, "tes")
                    .disallowAddToBackStack()
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                List<Bean> all = AppDatabase.getInstance(OverviewActivity.this).beanDao()
                            .getAll();
                    for (Bean bean : all) {
                        AppDatabase.getInstance(OverviewActivity.this).beanDao().delete(bean);
                    }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //if (id == R.id.nav_camera) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void beanCreated() {
        if (listFragment != null) {
            listFragment.refreshOverview();
        }
    }

    @Override
    public void showAddBtn(boolean isVisible) {
        floatingActionBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
