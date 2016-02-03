package com.passTheBall;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/**
 * Created by John on 1/11/2016.
 */
public class HttpHelper {
    private final String USER_AGENT = "Mozilla/5.0";

    public JSONObject sendPostToGetTeamRoster(int teamId) throws Exception {

        String url = "http://stats.nba.com/stats/commonteamroster";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);


        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("Season", "2015-16"));
        urlParameters.add(new BasicNameValuePair("TeamID", String.valueOf(teamId)));


        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        if(response.getStatusLine().getStatusCode() != 200) {
            System.out.println("There was a problem getting the roster: " + teamId);
            return null;
        }

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return new JSONObject(result.toString());

    }

    public JSONObject sendPostToGetUserCareerStats(String playerId) throws Exception {

        String url = "http://stats.nba.com/stats/playercareerstats";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);


        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("PlayerID", playerId));
        urlParameters.add(new BasicNameValuePair("PerMode", "PerGame"));


        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        if(response.getStatusLine().getStatusCode() != 200) {
            System.out.println("There was a problem getting the roster: " + playerId);
            return null;
        }

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return new JSONObject(result.toString());

    }
}
