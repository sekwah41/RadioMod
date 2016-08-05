package com.sekwah.radiomod.onlineservices.soundcloud;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class SoundCloud {

    private final String clientId;

    public SoundCloud(String clientId){
        this.clientId = clientId;
    }

    public enum LoadedType{
        TRACKDATA
    }

    /*
        https://developers.soundcloud.com/docs/api/reference#tracks

        track query data


     */


    public void searchSongs(String search){

    }


    /**
     * Will return null if failed and return the json string if successful.
     *
     * @param requestTags the requests to be added to the end of the url
     */
    public String requestTrackJson(String... requestTags){
        return this.requestJson("tracks", requestTags);
    }

    /**
     * Will return null if failed and return the json string if successful.
     *
     * This also allows the access of other parts of the api rather than just what has been set here.
     *
     * @param requestTags the requests to be added to the end of the url
     */
    public String requestJson(String requestType, String... requestTags){
        String requestString = "";

        for(String requestTag : requestTags){
            requestString += "&" + requestTags;
        }

        try {
            InputStream in = new URL("https://api.soundcloud.com/ " + requestType + "?client_id=23c5983facf3240a2f14515f05f34873").openStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
