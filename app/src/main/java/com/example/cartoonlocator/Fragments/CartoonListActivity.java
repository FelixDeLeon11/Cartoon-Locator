package com.example.cartoonlocator.Fragments;

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

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class CartoonListActivity extends Fragment {
    public static final String TAG = "CartoonListActivity";
    private ShowClient client;
    public LiveData<PagedList<Show>> shows;
    public ShowDataSourceFactory factory;
    public SearchView showSearch;
    public MainShowListAdapter showAdapter;
    public LinearLayoutManager manager;
    public RecyclerView rvShows;

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
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(20).build();

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
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar( toolbar );

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
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
}