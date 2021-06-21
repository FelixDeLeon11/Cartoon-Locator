package com.example.cartoonlocator.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ShowDetailActivity extends Fragment {
    private Show show;
    private ViewPager2 viewPager;
    private FragmentAdapter fragmentAdapter;

    public ShowDetailActivity(Show show) {
        this.show = show;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView image = view.findViewById(R.id.imageDetail);
        Glide.with(getContext()).load(show.getBackdrop()).placeholder(R.drawable.ic_image_gallery).into(image);

        fragmentAdapter = new FragmentAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position + 1 == 1) {
                        tab.setText("DESCRIPTION");
                    } else {
                        tab.setText("WHERE TO WATCH");
                    }
//                    tab.setText("OBJECT " + (position + 1));
                }
        ).attach();
    }

    public class FragmentAdapter extends FragmentStateAdapter {
        public FragmentAdapter(@NonNull ShowDetailActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new ShowOverview(show);
            } else {
                return new ShowWhereToWatch(show);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
