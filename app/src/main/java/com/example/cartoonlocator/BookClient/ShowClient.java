package com.example.cartoonlocator.BookClient;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ShowClient {
    private static final String API_KEY =  "api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&";
    private static final String SORT_BY = "sort_by=popularity.desc&";
    private static int PAGE = 1;
    private static final String GENRE_ID = "with_genres=16&";
    private static final String ORIGINAL_LANGUAGE = "with_original_language=en";
    private int totalPages;
    private AsyncHttpClient client;

    public ShowClient() {
        this.client = new AsyncHttpClient();
    }

    private static String GET_SHOWS_API_URL(){
            return "https://api.themoviedb.org/3/discover/tv?" +
                    API_KEY +
                    "language=en-US&" +
                     SORT_BY+
                    String.format("page=%d&", PAGE) +
                    "timezone=America%2FNew_York&" +
                     GENRE_ID+
                    "include_null_first_air_dates=false&" +
                    ORIGINAL_LANGUAGE;
    }

    private String getApiUrl(String relativeUrl) {
        return GET_SHOWS_API_URL() + relativeUrl;
    }

    // Method for accessing the movie db API
    public void getShowsList(JsonHttpResponseHandler handler) {
        //String t = GET_SHOWS_API_URL;
        client.get(GET_SHOWS_API_URL(), handler);
    }

    public void getNextShowsList(JsonHttpResponseHandler handler, int page){
        PAGE = page;
        String temp = GET_SHOWS_API_URL();
        client.get(GET_SHOWS_API_URL(), handler);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

