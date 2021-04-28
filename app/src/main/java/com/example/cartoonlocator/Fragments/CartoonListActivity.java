package com.example.cartoonlocator.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.NavigationHost;
import com.example.cartoonlocator.R;
import com.example.cartoonlocator.RecyclerViewAdapters.MainShowListAdapter;
import com.example.cartoonlocator.ShowDataSourceFactory;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.material.navigation.NavigationView;

public class CartoonListActivity extends Fragment {
    public static final String TAG = "CartoonListActivity";
    private ShowClient client;
    public LiveData<PagedList<Show>> shows;
    public ShowDataSourceFactory factory;
    public SearchView showSearch;
    public MainShowListAdapter showAdapter;
    public LinearLayoutManager manager;
    public RecyclerView rvShows;
    public PagedList.Config config;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    public CartoonListActivity(){}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAdapter = new MainShowListAdapter(getContext());

        client = new ShowClient();

        factory = new ShowDataSourceFactory(client);
        config = new PagedList.Config.Builder().setPageSize(20).build();

        shows = new LivePagedListBuilder(factory, config).build();
        shows.observe(this, new Observer<PagedList<Show>>() {
            @Override
            public void onChanged(PagedList<Show> shows) {
                showAdapter.submitList(shows);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState); // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_cartoon_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvShows = view.findViewById(R.id.mainShowList);
        rvShows.setAdapter(showAdapter);

        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvShows.setLayoutManager(manager);

        SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(rvShows);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar( toolbar );

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) getView().findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
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

    private void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = CartoonListActivity.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = CartoonListActivity.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = CartoonListActivity.class;
                break;
            default:
                fragmentClass = CartoonListActivity.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((NavigationHost) getActivity()).navigateTo(fragment, true);

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
//        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        Fragment test = this;

        showSearch = (SearchView) menu.getItem(0).getActionView();
        showSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getContext(), "dd", Toast.LENGTH_SHORT).show();
//                firstItem = manager.findFirstVisibleItemPosition();
//                ((MainActivity)getActivity()).GOTO(new SearchFragment());
                ((NavigationHost) getActivity()).navigateTo(new SearchFragment(query), true);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}