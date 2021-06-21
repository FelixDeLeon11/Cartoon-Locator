package com.example.cartoonlocator.RecyclerViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cartoonlocator.Fragments.ShowDetailActivity;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.NavigationHost;
import com.example.cartoonlocator.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MainShowListAdapter extends PagedListAdapter<Show, MainShowListAdapter.ViewHolder> {
    List<Show> shows;
    Context context;
    NavigationHost nav;

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static final DiffUtil.ItemCallback<Show> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Show>() {
                @Override
                public boolean areItemsTheSame(Show oldItem, Show newItem) {
                    return oldItem.getShowId() == newItem.getShowId();
                }

                @Override
                public boolean areContentsTheSame(Show oldItem, Show newItem) {
                    return (oldItem.getTitle().equals(newItem.getTitle()));
                }
            };

    public MainShowListAdapter(Context context, NavigationHost navigationHost) {
        this();
        this.context = context;
        nav = navigationHost;
    }

    public MainShowListAdapter() {
        super(DIFF_CALLBACK);
    }

    //TODO OOOOOOOOOOOOOOOOOOOOOO
    public void addMoreContacts(PagedList<Show> newShows) {
        shows.addAll(newShows);
        submitList(newShows); // DiffUtil takes care of the check
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View showView = LayoutInflater.from(context).inflate(R.layout.activity_show_detail, parent, false);
        return new ViewHolder(showView, (OnItemClickListener) listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Show show = getItem(position);

        if (show == null) {
            return;
        }
        holder.bind(show);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView showImage;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener clickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            showImage = itemView.findViewById(R.id.showImage);
            card = itemView.findViewById(R.id.showContainer);
        }

        public void bind(Show show) {
            name.setText(show.getTitle());
            String imageURL = show.getBackdrop();
            Glide.with(context).load(imageURL).placeholder(R.drawable.ic_image_gallery).into(showImage);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    clickListener.onItemClick(itemView, getAdapterPosition());
                    Toast.makeText(context, "from adapter", Toast.LENGTH_SHORT).show();
                    nav.navigateTo(new ShowDetailActivity(show), true);
                }
            });
        }
    }
}