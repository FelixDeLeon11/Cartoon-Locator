package com.example.cartoonlocator.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.NavigationHost;
import com.example.cartoonlocator.R;
import com.example.cartoonlocator.RecyclerViewAdapters.MainShowListAdapter;
import com.example.cartoonlocator.ShowDataSourceFactory;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

public class RangeFragment extends Fragment {
    public String fromDate;
    public String toDate;
    private RecyclerView rvShows;
    private MainShowListAdapter showAdapter;
    private LinearLayoutManager manager;
    private ShowClient client;
    private ShowDataSourceFactory factory;
    private LiveData shows;

    public RangeFragment(String fromDate, String toDate){
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cartoon_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvShows = view.findViewById(R.id.mainShowList);

        showAdapter = new MainShowListAdapter(getContext(), (NavigationHost) getActivity());
        rvShows.setAdapter(showAdapter);

        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvShows.setLayoutManager(manager);

        SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(rvShows);

        client = new ShowClient();

        factory = new ShowDataSourceFactory(client,fromDate,toDate);
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
