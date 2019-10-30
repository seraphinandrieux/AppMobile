package cpe.mobile.mybrowzik.webServices;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cpe.mobile.mybrowzik.listeners.MyHttpListener;
import cpe.mobile.mybrowzik.models.AudioFile;


public class LastFMService extends Thread {


    private String urlAPI = "https://ws.audioscrobbler.com";
    private String MY_API_KEY = "33dd1acf621f203b41edc0d7dfde0301";
    private JSONObject responseRequest;
    private AudioFile audioFile;
    private MyHttpListener listener;
    //private String artist;

    public LastFMService(AudioFile audioFile,MyHttpListener listener) {
        this.audioFile = audioFile;
        this.listener = listener;

    }

    public void run() {
        this.responseRequest = getInfoTrack(this.audioFile.getTitle(), this.audioFile.getArtist());
        updateEmptyInfoFromAudioFile();
    }

    public void updateEmptyInfoFromAudioFile() {
        listener.setAlbum(getAlbum());

        listener.setAlbumPath(getAlbumPath());

        // we start another thread which will get back a bitmap of the image which is pointed with the albumPath
        ImageAlbumService myImageAlbumService = new ImageAlbumService(getAlbumPath(),listener); // we inside the url + the listener which contains the ImageView to update
        myImageAlbumService.start();



    }


    public JSONObject getInfoTrack(String track, String artist) {
        String tempUrl = urlAPI + "/2.0/?method=track.getInfo&api_key=" + MY_API_KEY + "&artist=" + artist + "&track=" + track + "&format=json";
        InputStream istream;
        JSONObject jsonObject =null;

        try {
            //tempUrl = tempUrl.replace(",","");
            //tempUrl = tempUrl.replace(" ","%20");
            URL url = new URL(tempUrl);
            System.out.println(url);

            HttpURLConnection c;
            try {
                c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoInput(true);
                c.setRequestProperty("Accept", "*/*");
                istream = c.getInputStream();

                InputStreamReader isr = new InputStreamReader(istream);
                BufferedReader br = new BufferedReader(isr);
                String resp = "";
                String data = br.readLine();

                while (data != null) {
                    resp += data;
                    System.out.println(data);
                    data = br.readLine();
                }

                try {

                    jsonObject = new JSONObject(resp);

                } catch (JSONException e) {

                    System.out.println(e);
                }


                istream.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(tempUrl);
            }
        } catch (MalformedURLException e) {
            System.out.println(e);
        }

        return jsonObject;


    }

    public String getAlbumPath() { // will find the link to the big image
        String lreturn = "";
        System.out.println(this.responseRequest);
        try {
            lreturn = this.responseRequest.getJSONObject("track").getJSONObject("album").getJSONArray("image").getJSONObject(2).getString("#text");
        } catch (JSONException e) {
            System.out.println(e);
        }
        System.out.println("url de album "+ lreturn);
        return lreturn;
    }

    public String getAlbum() { // return album name
        String lreturn = "";
        try {
            lreturn = this.responseRequest.getJSONObject("track").getJSONObject("album").getString("title");
        } catch (JSONException e) {
            System.out.println(e);
        }
        return lreturn;
    }





}







