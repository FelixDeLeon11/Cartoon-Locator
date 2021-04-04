package com.example.cartoonlocator.Model;

import com.example.cartoonlocator.BookClient.ShowClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Show {
    String backdrop;
    String title;
    int showId;
    int page;

    public Show(){} // For Parceler

    public Show(JSONObject jsonObject, int page) throws JSONException {
        backdrop = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("name");
        showId = jsonObject.getInt("id");
        this.page = page;
    }

    // Obtain an array list from the json
    public static List<Show> fromJsonArray(JSONArray showJsonArray, int page) throws JSONException {
        List<Show> shows = new ArrayList<>();
        for (int i =0; i < showJsonArray.length(); i++){
            shows.add(new Show(showJsonArray.getJSONObject(i) , page));
        }
        return  shows;
    }

    public int getPage() {
        return page;
    }
    public String getTitle() {
        return title;
    }
    public int getShowId() {
        return showId;
    }

    public String getBackdrop() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdrop);
    }
}