package cpe.mobile.mybrowzik.webServices;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class LastFMService extends Thread {



    private String urlAPI = "https://ws.audioscrobbler.com" ;
    private String MY_API_KEY = "33dd1acf621f203b41edc0d7dfde0301" ;
    public String strReturn;


    public void getInfoTrack(String track, String artist){
        String tempUrl = urlAPI + "/2.0/?method=track.getInfo&api_key=" + MY_API_KEY + "&artist=" + artist + "&track="+ track + "&format=json";
        InputStream istream;
        try {
            tempUrl = tempUrl.replace(",","");
            tempUrl = tempUrl.replace(" ","%20");
            URL url = new URL(tempUrl);
            HttpURLConnection c;
            try {
                c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoInput(true);
                c.setRequestProperty("Accept","*/*");
                istream = c.getInputStream();

                InputStreamReader isr = new InputStreamReader(istream);
                BufferedReader br = new BufferedReader(isr);
                String data = br.readLine();
                while(data != null ){
                    System.out.println(data);
                    data = br.readLine();
                }

                istream.close();
                //while data != nullistream.close();
            }catch(IOException e){
                e.printStackTrace();
                System.out.println(tempUrl);
            }
        }catch(MalformedURLException e){
            System.out.println(e);
        }


    }


}







