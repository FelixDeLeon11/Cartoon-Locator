package com.example.cartoonlocator.DataSources;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Model.Show;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class ShowDataSource extends ItemKeyedDataSource<Integer, Show> {
    public static final String TAG = "CartoonListActivity";
    public ShowClient mClient;

    public ShowDataSource(ShowClient client) {
        mClient = client;
    }

    public ShowDataSource() {
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Show> callback) {
        Log.i(TAG, "NEED MORE DATA - Load Initial ");
        JsonHttpResponseHandler jsonHttpResponseHandler = createShowHandler(callback, true);
        mClient.getNextShowsList(jsonHttpResponseHandler, 1);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Show> callback) {
        Log.i(TAG, "NEED MORE DATA - Load After " + params.key.toString());
        JsonHttpResponseHandler jsonHttpResponseHandler = createShowHandler(callback, true);
        mClient.getNextShowsList(jsonHttpResponseHandler, params.key + 1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Show> callback) {
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Show item) {
        return item.getPage();
    }

    public JsonHttpResponseHandler createShowHandler(final LoadCallback<Show> callback, boolean isAsync) {
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON response) {
                try {
                    JSONArray jsonShows;
                    if (response != null) {
                        List<Show> shows;
                        // Get the results json array
                        jsonShows = response.jsonObject.getJSONArray("results");
                        int maxPage = response.jsonObject.getInt("page");
                        Log.i(TAG, "Successful Request");
                        int pages = response.jsonObject.getInt("total_pages");
//                        mClient.setTotalPages(pages);
                        shows = Show.fromJsonArray(jsonShows, maxPage);
                        callback.onResult(shows);
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    Log.e(TAG, "UNsuccessful Request", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String responseString, Throwable throwable) {
                // Handle failed request here
                Log.e(TAG, "Request failed with code " + statusCode + ". Response message: " + responseString);
            }
        };
        return handler;
    }
}