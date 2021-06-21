package com.example.cartoonlocator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;

import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Fragments.CartoonListActivity;
import com.example.cartoonlocator.Fragments.RangeFragment;
import com.example.cartoonlocator.Fragments.SearchFragment;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.RecyclerViewAdapters.MainShowListAdapter;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationHost {
    private SearchView showSearch;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        drawerToggle = setupDrawerToggle();
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        mDrawer.addDrawerListener(drawerToggle);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CartoonListActivity())
                    .commit();
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

        // The action bar home/up action should open or close the drawer.
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawer.openDrawer(GravityCompat.START);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
    }

    private void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        RangeFragment fragment = null;
//        Class fragmentClass = RangeFragment.class;

        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragment = new RangeFragment("2015-01-01", "2019-12-31");
                break;
            case R.id.nav_second_fragment:
                fragment = new RangeFragment("2015-01-01", "2019-12-31");
                break;
            case R.id.nav_third_fragment:
                fragment = new RangeFragment("2010-01-01", "2014-12-31");
                break;
            case R.id.nav_fourth_fragment:
                fragment = new RangeFragment("2005-01-01", "2009-12-31");
                break;
            case R.id.nav_fifth_fragment:
                fragment = new RangeFragment("2000-01-01", "2004-12-31");
                break;
            case R.id.nav_sixth_fragment:
                fragment = new RangeFragment("1990-01-01", "1999-12-31");
                break;
            case R.id.nav_seventh_fragment:
                fragment = new RangeFragment("1980-01-01", "1989-12-31");
                break;
            case R.id.nav_eigth_fragment:
                fragment = new RangeFragment("1970-01-01", "1979-12-31");
                break;
            case R.id.nav_ninth_fragment:
                fragment = new RangeFragment("1960-01-01", "1969-12-31");
                break;
            case R.id.nav_10_fragment:
                fragment = new RangeFragment("1950-01-01", "1959-12-31");
                break;
            case R.id.nav_11_fragment:
                fragment = new RangeFragment("1940-01-01", "1949-12-31");
                break;
            default:
                fragment = new RangeFragment("1930-01-01", "1939-12-31");
        }

//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        navigateTo(fragment, true);
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out)//.hide(fragment);  transaction.add(R.id.container, fragmentTO);
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        showSearch = (SearchView) menu.getItem(0).getActionView();
        showSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                navigateTo(new SearchFragment(query), true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}