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
    public LiveData<PagedList<Show>> shows;
    public ShowDataSourceFactory factory;
    public MainShowListAdapter showAdapter;
    public LinearLayoutManager manager;
    public RecyclerView rvShows;
    public PagedList.Config config;

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

        ShowClient client = new ShowClient();

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
}