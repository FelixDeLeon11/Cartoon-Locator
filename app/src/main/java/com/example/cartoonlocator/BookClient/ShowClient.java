package com.example.cartoonlocator.BookClient;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

public class ShowClient {
    private static final String DISCOVER = "https://api.themoviedb.org/3/discover/tv?";
    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String SORT_BY = "sort_by=popularity.desc&";
    private static int MAIN_PAGE = 1;
    private static int RANGE_PAGE = 1;
    private static int SEARCH_PAGE = 1;
    private static final String GENRE_ID = "with_genres=16&";
    private static final String ORIGINAL_LANGUAGE = "with_original_language=en";
    private int totalPages;
    private AsyncHttpClient client;

    public ShowClient() {
        this.client = new AsyncHttpClient();
    }

    private static String GET_SHOWS_API_URL() {
        return DISCOVER +
                String.format("api_key=%s&", API_KEY) +
                "language=en-US&" +
                SORT_BY +
                String.format("page=%d&", MAIN_PAGE) +
                "timezone=America%2FNew_York&" +
                GENRE_ID +
                "include_null_first_air_dates=false&" +
                ORIGINAL_LANGUAGE;
    }

    private static String SEARCH_SHOWS_URL(String query) {
        return "https://api.themoviedb.org/3/search/tv?api_key=" +
                API_KEY + "&" +
                "language=en-US&" +
                String.format("page=%d&", SEARCH_PAGE) +
                "query=" + query + "&" +
                "include_adult=false";
    }

    private static String RANGE_SHOWS_URL(String fromDate, String toDate) {
        return DISCOVER +
                String.format("api_key=%s&", API_KEY) +
                "language=en-US&" +
                SORT_BY +
                String.format("first_air_date.gte=%s&", fromDate) +
                String.format("first_air_date.lte=%s&", toDate) +
                String.format("page=%d&", RANGE_PAGE) +
                "timezone=America%2FNew_York&" +
                GENRE_ID +
                "include_null_first_air_dates=false&" +
                ORIGINAL_LANGUAGE;
    }

    private static String GET_NETWORKS(int showId) {
        return String.format(Locale.US, "https://api.themoviedb.org/3/tv/%d/watch/providers?api_key=%s", showId, API_KEY);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void getNextShowsList(JsonHttpResponseHandler handler, int page) {
        MAIN_PAGE = page;
        client.get(GET_SHOWS_API_URL(), handler);
    }

    public void getSearchShowsList(JsonHttpResponseHandler handler, int page, String query) {
        SEARCH_PAGE = page;
        client.get(SEARCH_SHOWS_URL(query), handler);
    }

    public void getRangeShowsList(JsonHttpResponseHandler handler, int page, String fromDate, String toDate) {
        ///todo DATE FORMAT = YYYY-MM-DD use moviedb api DISCOVER TV
        RANGE_PAGE = page;
        client.get(RANGE_SHOWS_URL(fromDate, toDate), handler);
    }

    public void getNetworks(JsonHttpResponseHandler handler, int showId) {
        client.get(GET_NETWORKS(showId), handler);
        Log.e(",", "");
    }
}

