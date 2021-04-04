package com.example.cartoonlocator;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.DataSources.SearchShowDataSource;
import com.example.cartoonlocator.DataSources.ShowDataSource;
import com.example.cartoonlocator.Model.Show;

public class ShowDataSourceFactory extends DataSource.Factory<Integer, Show>{
    private final String type;
    ShowClient client;
    public MutableLiveData<ShowDataSource> showLiveData;
    public String query;

    public ShowDataSourceFactory(ShowClient client){
        this.client = client;
        this.type = "ShowMain";
    }

    public ShowDataSourceFactory(ShowClient client, String query){
        this.client = client;
        this.type = "ShowSearch";
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, Show> create() {
        ShowDataSource dataSource;

        if (type.equals("ShowMain")){
            dataSource = new ShowDataSource(this.client);
        } else {
            dataSource = new SearchShowDataSource(this.client, query);
        }

        showLiveData = new MutableLiveData<>();
        showLiveData.postValue(dataSource);

        return dataSource;
    }
}
