package com.example.cartoonlocator.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.R;

import java.util.List;

public class MainShowListAdapter extends RecyclerView.Adapter<MainShowListAdapter.ViewHolder> {
    List<Show> shows;
    Context context;

    public MainShowListAdapter(Context context, List<Show> shows){
        this.shows = shows;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View showView = LayoutInflater.from(context).inflate(R.layout.activity_show_detail, parent, false);
        return new ViewHolder(showView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Show show = shows.get(position);
        holder.bind(show);
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView showImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            showImage = itemView.findViewById(R.id.showImage);
        }

        public void bind(Show show){
            name.setText(show.getTitle());
            String imageURL = show.getBackdrop();
            Glide.with(context).load(imageURL).into(showImage);
        }
    }
}
