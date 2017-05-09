package com.software.upc.fluency.activity;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.software.upc.fluency.R;
import com.software.upc.fluency.fragment.HomeFragment;
import com.software.upc.fluency.fragment.InfoFragment;
import com.software.upc.fluency.fragment.ParterFragment;
import com.software.upc.fluency.fragment.PlanFragment;
import com.software.upc.fluency.fragment.TestFragment;
import com.software.upc.fluency.model.User;

public class PlanActivity extends AppCompatActivity {

    private TextView textView;
    private User user;

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        user = (User) getIntent().getSerializableExtra("user");

        fragmentManager = getSupportFragmentManager();

        /*设定NavigationView菜单的选择事件*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        textView = (TextView) header.findViewById(R.id.username2);
        textView.setText(user.getUsername());

        setupView();
        if (savedInstanceState == null) showHome();
    }

    private void setupView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    private void showHome() {
        selectDrawerItem(navigationView.getMenu().getItem(0));
        //drawerLayout.openDrawer(GravityCompat.START);
        drawerLayout.closeDrawers();
    }

    private void selectDrawerItem(MenuItem menuItem){
        boolean specialToolbarBehaviour = false;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.nav_top:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_plan:
                fragmentClass = PlanFragment.class;
                break;
            case R.id.nav_parter:
                fragmentClass = ParterFragment.class;
                break;
            case R.id.nav_test:
                fragmentClass = TestFragment.class;
                break;
            case R.id.nav_information:
                fragmentClass = InfoFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
                break;
        }
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        setToolbarElevation(specialToolbarBehaviour);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setToolbarElevation(boolean specialToolbarBehaviour) {
        if (specialToolbarBehaviour) {
            toolbar.setElevation(0.0f);
            frameLayout.setElevation(getResources().getDimension(R.dimen.elevation_toolbar));
        } else {
            toolbar.setElevation(getResources().getDimension(R.dimen.elevation_toolbar));
            frameLayout.setElevation(0.0f);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
