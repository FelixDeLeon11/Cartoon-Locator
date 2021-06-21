package com.example.cartoonlocator.Model;

import android.util.Log;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.cartoonlocator.BookClient.ShowClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class Show {
    private static final ShowClient client = new ShowClient();
    private String title;
    private String backdrop;
    private String poster;
    private String overview;
    private double vote;
    private int showId;
    private int page;
    private List<Network> networks;

    public Show() {
    } // For Parceler

    public Show(JSONObject jsonObject, int page) throws JSONException {
        backdrop = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("name");
        showId = jsonObject.getInt("id");
        poster = jsonObject.getString("poster_path");
        vote = jsonObject.getDouble("vote_average");
        overview = jsonObject.getString("overview");
        this.page = page;
        client.getNetworks(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONArray jsonArray = null;
                JSONArray moveArray = null;
                try {
                    jsonArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("flatrate");
                    String test = "hi";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (jsonArray == null) {
                        jsonArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("buy");
                    } else {
                        moveArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("buy");
                        for (int j = 0; j < moveArray.length(); j++) {
                            jsonArray.put(moveArray.getJSONObject(j));
                        }
                    }
                    String test = "hi";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (jsonArray == null) {
                        jsonArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("rent");
                    } else {
                        moveArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("rent");
                        for (int j = 0; j < moveArray.length(); j++) {
                            jsonArray.put(moveArray.getJSONObject(j));
                        }
                    }
                    String test = "hi";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (jsonArray == null) {
                        jsonArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("ads");
                    } else {
                        moveArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("ads");
                        for (int j = 0; j < moveArray.length(); j++) {
                            jsonArray.put(moveArray.getJSONObject(j));
                        }
                    }
                    String test = "hi";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (jsonArray == null) {
                        jsonArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("free");
                    } else {
                        moveArray = json.jsonObject.getJSONObject("results").getJSONObject("US").getJSONArray("free");
                        for (int j = 0; j < moveArray.length(); j++) {
                            jsonArray.put(moveArray.getJSONObject(j));
                        }
                    }
                    String test = "hi";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    networks = Network.fromJsonArray(jsonArray);
                    String test = "hi";
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e("E", "Request failed with code " + i + ". Response message: " + s);
                throwable.printStackTrace();
            }
        }, showId);
    }

    // Obtain an array list from the json
    public static List<Show> fromJsonArray(JSONArray showJsonArray, int page) throws JSONException {
        List<Show> shows = new ArrayList<>();
        for (int i = 0; i < showJsonArray.length(); i++) {
            shows.add(new Show(showJsonArray.getJSONObject(i), page));
        }
        return shows;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdrop);
    }

    public String getPoster() {
        return String.format("https://image.tmdb.org/t/p/w400/%s", poster);
    }

    public double getVote() {
        return vote;
    }

    public int getPage() {
        return page;
    }

    public int getShowId() {
        return showId;
    }

    public List<Network> getNetworks() {
        return networks;
    }
}