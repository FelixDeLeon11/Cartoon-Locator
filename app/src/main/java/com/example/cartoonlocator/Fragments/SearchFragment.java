package com.example.cartoonlocator.Fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.R;
import com.example.cartoonlocator.RecyclerViewAdapters.MainShowListAdapter;
import com.example.cartoonlocator.ShowDataSourceFactory;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

public class SearchFragment extends Fragment {
    public SearchView showSearch;
    private MainShowListAdapter showAdapter;
    private ShowClient client;
    private ShowDataSourceFactory factory;
    private LiveData shows;
    private String query;
    private RecyclerView rvShows;
    private LinearLayoutManager manager;

    public SearchFragment(String query) {
        this.query = query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvShows = view.findViewById(R.id.mainShowList);

        showAdapter = new MainShowListAdapter(getContext());
        rvShows.setAdapter(showAdapter);

        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvShows.setLayoutManager(manager);

        SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(rvShows);

        client = new ShowClient();

        factory = new ShowDataSourceFactory(client,query);
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(20).build();

        shows = new LivePagedListBuilder(factory, config).build();
        shows.observe(this, new Observer<PagedList<Show>>() {
            @Override
            public void onChanged(PagedList<Show> shows) {
                showAdapter.submitList(shows);
            }
        });
    }
}