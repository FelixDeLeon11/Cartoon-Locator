package com.example.cartoonlocator.DataSources;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Model.Show;
import com.example.cartoonlocator.ShowDataSourceFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchShowDataSource extends ShowDataSource {
    public static final String TAG = "CartoonListActivity";
    public ShowClient mClient;
    public String query;

    public SearchShowDataSource(ShowClient client, String query){
//        super();
        mClient = client;
        this.query = query;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Show> callback) {
        Log.i(TAG, "NEED MORE DATA - Load Initial ");
        JsonHttpResponseHandler jsonHttpResponseHandler = createShowHandler(callback, true);
        mClient.getSearchShowsList(jsonHttpResponseHandler, 1, query);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Show> callback) {
        Log.i(TAG, "NEED MORE DATA - Load After "+ params.key.toString());
        JsonHttpResponseHandler jsonHttpResponseHandler = createShowHandler(callback, true);
        mClient.getSearchShowsList(jsonHttpResponseHandler, params.key+1, query);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Show> callback) {}

    @NonNull
    @Override
    public Integer getKey(@NonNull Show item) {
        return item.getPage();
    }
}