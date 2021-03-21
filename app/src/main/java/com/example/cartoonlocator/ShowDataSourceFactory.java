package com.example.cartoonlocator;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Model.Show;

public class ShowDataSourceFactory extends DataSource.Factory<Integer, Show>{
    ShowClient client;
    public ShowDataSourceFactory(ShowClient client){
        this.client = client;
    }

    @NonNull
    @Override
    public DataSource<Integer, Show> create() {
        ShowDataSource dataSource = new ShowDataSource(this.client);
        return dataSource;
    }
}
