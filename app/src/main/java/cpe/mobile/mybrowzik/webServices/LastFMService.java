package cpe.mobile.mybrowzik.webServices;


import android.database.sqlite.SQLiteDatabase;
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
import java.util.ArrayList;

import cpe.mobile.mybrowzik.listeners.MyDBListener;
import cpe.mobile.mybrowzik.listeners.MyHttpListener;
import cpe.mobile.mybrowzik.models.AudioFile;
import cpe.mobile.mybrowzik.models.DbConstants;
import cpe.mobile.mybrowzik.models.DbRequestType;
import cpe.mobile.mybrowzik.services.DBService;


public class LastFMService extends Thread {


    private String urlAPI = "https://ws.audioscrobbler.com";
    private String MY_API_KEY = "33dd1acf621f203b41edc0d7dfde0301";
    private JSONObject responseRequest;
    private AudioFile audioFile;
    private MyHttpListener listener;
    private MyDBListener myDBListener;
    private ImageView albumImage;
    //private String artist;

    public LastFMService(AudioFile audioFile, MyHttpListener listener, MyDBListener myDBListener, ImageView albumImage) {

        this.audioFile          = audioFile;
        this.listener           = listener;
        this.myDBListener       = myDBListener;
        this.albumImage         = albumImage;


    }

    public void run() {

        this.responseRequest = getInfoTrack(this.audioFile.getTitle(), this.audioFile.getArtist());

        //Update DB with news infos
        updateDB();

        //update image view album
        ImageAlbumService myImageAlbumService = new ImageAlbumService(getAlbumPath(),albumImage); // we inside the url + the listener which contains the ImageView to update
        myImageAlbumService.start();

    }




    public JSONObject getInfoTrack(String track, String artist) {
        String tempUrl = urlAPI + "/2.0/?method=track.getInfo&api_key=" + MY_API_KEY + "&artist=" + artist + "&track=" + track + "&format=json";
        InputStream istream;
        JSONObject jsonObject =null;
        System.out.println("lalala");
        try {
            //tempUrl = tempUrl.replace(",","");
            //tempUrl = tempUrl.replace(" ","%20");
            URL url = new URL(tempUrl);


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
                    br.close();
                    System.out.println(e);
                    istream.close();
                }

                br.close();
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
        try {
            lreturn = this.responseRequest.getJSONObject("track").getJSONObject("album").getJSONArray("image").getJSONObject(2).getString("#text");
        } catch (JSONException e) {
            System.out.println(e);
        }
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

    public Integer getYear() { // return album year
        Integer lreturn = 0;
        String lstrReturn = "";
        try {

            lstrReturn = this.responseRequest.getJSONObject("track").getJSONObject("wiki").getString("published");

            try{
                lstrReturn = lstrReturn.split(",")[0];

                lreturn= Integer.parseInt( lstrReturn.substring(lstrReturn.length()-4) );
            }catch(NumberFormatException e){
                System.out.println(e);
            }
        } catch (JSONException e) {
            System.out.println(e);
        }
        return lreturn;
    }

    public String getGenre() { // return album name
        String lreturn = "";
        try {
            lreturn = this.responseRequest.getJSONObject("track").getJSONObject("toptags").getJSONArray("tag").getJSONObject(0).getString("name");
        } catch (JSONException e) {
            System.out.println(e);
        }


        return lreturn;
    }


    public void updateDB(){ //Once we get back info from this song we write on DB these news infos
        try {
            myDBListener.updateMyDB(audioFile.getTitle(), audioFile.getArtist(), getAlbum(), getGenre(), getYear(), getAlbumPath());

            //We don't forget to update the viewHolder which could be already generated before the get back;
            listener.setAlbum(getAlbum());
            listener.setAlbumPath(getAlbumPath());
            listener.setGenre(getGenre());
            listener.setYear(getYear());
        }catch(NullPointerException e){
            System.out.println(e);
        }

    }




}







