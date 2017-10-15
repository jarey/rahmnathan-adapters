package com.github.rahmnathan.google.commute.provider;

import com.github.rahmnathan.commute.provider.CommuteProvider;
import com.github.rahmnathan.http.control.HttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleCommuteProvider implements CommuteProvider {
    private final String apiKey;

    public GoogleCommuteProvider(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCommuteTime(String startLocation, String endLocation){
        JSONObject jsonCommute = getCommuteContent(startLocation, endLocation);
        return getTime(jsonCommute);
    }

    private JSONObject getCommuteContent(String startLocation, String endLocation){
        String uri = "https://maps.googleapis.com/maps/api/directions/json?origin=" + startLocation +
                "&destination=" + endLocation + "&key=" + apiKey;

        String response = HttpClient.getResponseAsString(uri);
        return new JSONObject(response);
    }

    private String getTime(JSONObject jsonObject){
        JSONObject firstRoute = ((JSONArray) jsonObject.get("routes")).getJSONObject(0);
        JSONObject legs = ((JSONArray) firstRoute.get("legs")).getJSONObject(0);
        JSONObject duration = (JSONObject) legs.get("duration");
        return duration.getString("text");
    }
}
