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
import com.example.cartoonlocator.Model.Network;
import com.example.cartoonlocator.R;

import java.util.List;

public class DetailNetworksAdapter extends RecyclerView.Adapter<DetailNetworksAdapter.ViewHolder> {
    Context context;
    List<Network> networks;

    public DetailNetworksAdapter(Context context, List<Network> networks){
        this.context = context;
        this.networks = networks;
    }

    public void addAll(List<Network> list){
        networks.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View networkView = LayoutInflater.from(context).inflate(R.layout.show_where_to_watch_detail, parent,false);
        return new ViewHolder(networkView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Network network = networks.get(position);
        holder.bind(network);
    }

    @Override
    public int getItemCount() { return networks.size();  }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView networkImage;
        TextView networkName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            networkImage = itemView.findViewById(R.id.networkImage);
            networkName = itemView.findViewById(R.id.networkName);
        }

        public void bind(Network network) {
            networkName.setText(network.getNetworkName());
            Glide.with(context).load(network.getNetworkImagePath()).placeholder(R.drawable.ic_image_gallery).into(networkImage);
        }
    }
}
