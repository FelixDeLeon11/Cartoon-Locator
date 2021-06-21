package com.example.cartoonlocator.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private String networkImagePath;
    private String networkName;

    public Network() {
    }

    public Network(JSONObject jsonObject) throws JSONException {
        networkImagePath = jsonObject.getString("logo_path");
        networkName = jsonObject.getString("provider_name");
    }

    public static List<Network> fromJsonArray(JSONArray networkJsonArray) throws JSONException {
        List<Network> networks = new ArrayList<>();

        if (networkJsonArray != null) {
            for (int i = 0; i < networkJsonArray.length(); i++) {
                networks.add(new Network(networkJsonArray.getJSONObject(i)));
            }
        }
        return networks;
    }

    public String getNetworkImagePath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", networkImagePath);
    }

    public String getNetworkName() {
        return networkName;
    }
}
