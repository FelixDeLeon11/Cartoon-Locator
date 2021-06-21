package com.example.cartoonlocator.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.cartoonlocator.Model.Network;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.R;
import com.example.cartoonlocator.RecyclerViewAdapters.DetailNetworksAdapter;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

public class ShowWhereToWatch extends Fragment {
    public Show show;
    public RecyclerView rvNetworks;
    public DetailNetworksAdapter networksAdapter;
    List<Network> networks;
    private LinearLayoutManager manager;

    public ShowWhereToWatch(Show show) {
        this.show = show;
        Log.e("","");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networks = new ArrayList<>();
        networksAdapter = new DetailNetworksAdapter(getContext(), networks);

        networks.addAll(show.getNetworks());
        networksAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.show_where_to_watch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvNetworks = view.findViewById(R.id.rvNetworks);
        rvNetworks.setAdapter(networksAdapter);

        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNetworks.setLayoutManager(manager);

        SnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(rvNetworks);
    }
}