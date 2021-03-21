//package com.example.cartoonlocator;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.paging.ItemKeyedDataSource;
//
//import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
//import com.example.cartoonlocator.BookClient.ShowClient;
//import com.example.cartoonlocator.Model.Show;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import java.util.ArrayList;
//
//import okhttp3.Headers;
//
//public class ShowDataSource extends ItemKeyedDataSource <Long, Show> {
//    public static final String TAG = "CartoonListActivity";
//    ShowClient mclient;
//    public ShowDataSource (ShowClient client){
//        mclient = client;
//    }
//
//    @Override
//    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Show> callback) {
//        // Fetch data synchronously (second parameter is set to true)
//        // load an initial data set so the paged list is not empty.
//        // See https://issuetracker.google.com/u/2/issues/110843692?pli=1
//
//        JsonHttpResponseHandler jsonHttpResponseHandler = createShowHandler(callback, true);
//    }
//
//    public JsonHttpResponseHandler createShowHandler(final LoadCallback<Show> callback, boolean isAsync) {
//
//        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Headers headers, JSON json) {
//                ArrayList<Show> shows = new ArrayList<Show>();
//                try {
//                    shows = Show.fromJsonArray(json.jsonObject.getJSONArray("results"));
//                    Log.i(TAG, "Successful Request");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                // send back to PagedList handler
//                callback.onResult(shows);
//            }
//
//            @Override
//            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
//
//            }
//        };
//        return handler;
//    }
//
//    @Override
//    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Show> callback) {
//
//    }
//    @Override
//    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Show> callback) {
//
//    }
//    // First type of ItemKeyedDataSource should match return type of getKey()
//    @NonNull
//    @Override
//    public Long getKey(@NonNull Show item) {
//        return item.getId();
//    }
//}
