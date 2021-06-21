package com.example.cartoonlocator.DataSources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.cartoonlocator.BookClient.ShowClient;
import com.example.cartoonlocator.Model.Show;

public class RangeShowDataSource extends ShowDataSource {
    public static final String TAG = "CartoonListActivity";
    private final ShowClient mClient;
    private final String fromDate;
    private final String toDate;

    public RangeShowDataSource(ShowClient mClient, String fromDate, String toDate){
        this.mClient = mClient;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Show> callback) {
        JsonHttpResponseHandler jsonHttpResponseHandler = createShowHandler(callback, true);
        mClient.getRangeShowsList(jsonHttpResponseHandler, 1, fromDate, toDate);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Show> callback) {
        Log.i(TAG, "NEED MORE DATA - Load After "+ params.key.toString());
        JsonHttpResponseHandler jsonHttpResponseHandler = createShowHandler(callback, true);
        mClient.getRangeShowsList(jsonHttpResponseHandler, params.key+1, fromDate, toDate);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Show> callback) { }

    @NonNull
    @Override
    public Integer getKey(@NonNull Show item) {
        return item.getPage();
    }
}
