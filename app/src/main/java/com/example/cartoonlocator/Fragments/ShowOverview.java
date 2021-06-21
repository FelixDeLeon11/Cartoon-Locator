package com.example.cartoonlocator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.R;

public class ShowOverview extends Fragment {
    private RatingBar ratingBar;
    private final Show show;
    private TextView showvote;
    private TextView showdescription;

    public ShowOverview(Show show) {
        this.show = show;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ratingBar = view.findViewById(R.id.stars);
        TextView name = view.findViewById(R.id.showname);
        showvote = view.findViewById(R.id.showvote);
        showdescription = view.findViewById(R.id.showDescription);

        showvote.setText(String.valueOf(show.getVote()));
        double temp = show.getVote() / 10;
        name.setText(show.getTitle());
        ratingBar.setRating((float) temp);
        showdescription.setText(show.getOverview());
    }
}
