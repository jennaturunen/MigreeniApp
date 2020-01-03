package com.example.migreeni;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;

/**
 * Class that gets the current weather using Openweathermap API
 */

public class saa extends AppCompatActivity {

    private static final String TAG = "SAA";

    private TextView tv_kaupunki, tv_lampotila, tv_ilmanpaine, tv_kosteus, tv_yksityiskohta, tv_saaikoni;
    private RequestQueue mQueue;
    private String ilmpaine;
    Typeface weatherFont;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saa);

        // Find the widgets of saa-layout using ids
        tv_kaupunki = findViewById(R.id.kaupunki_textview);
        tv_ilmanpaine = findViewById(R.id.ilmanpaine_textview);
        tv_kosteus = findViewById(R.id.kosteus_textview);
        tv_lampotila = findViewById(R.id.lampotila_textview);
        tv_yksityiskohta = findViewById(R.id.yksityiskohta_textview);
        tv_saaikoni = findViewById(R.id.saaikoni_imageview);

        // We use weather-icons that we get from this asset
        weatherFont = Typeface.createFromAsset(getAssets(), "weathericons-regular-webfont.ttf");
        tv_saaikoni.setTypeface(weatherFont);

        //RequestQueue manages worker threads for running the network operations, reading from and writing to the cache, and parsing responses.
        mQueue = Volley.newRequestQueue(this);

        //Get the coordinates from the koordinaatit-class
        Bundle extras = getIntent().getExtras();
        String lat = extras.getString("latitude");
        String longi = extras.getString("longtitude");

        //Log.d(TAG, "latitude: " + lat);
        //Log.d(TAG, "longitude: " + longi);

        hae_saa(lat,longi);
    }


    /**
     * Gets the weather info and shows the values in the view
     * @param lat latitude, we get from koordinaatit-class
     * @param longt longitude, we get from koordinaatit-class
     */
    public void hae_saa(String lat, String longt) {

        String url = "https:/api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + longt + "&appid=e629dbb8cc92982ffed615b4524532b6&units=metric";

        //get the JSON-Object based on  your location, using Openweathermap API
        JsonObjectRequest haku = new JsonObjectRequest (Request.Method.GET, url,null, new Response.Listener<JSONObject>() {

            @Override //if we get the response, set the values from the API to the saa-layout
            public void onResponse(JSONObject response) {

                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    JSONObject sys = response.getJSONObject("sys");

                    String lampo = String.valueOf(main_object.getInt("temp"));
                    String kuvaus = object.getString("description");
                    String kaupunki = response.getString("name");
                    String ipaine = String.valueOf(main_object.getInt("pressure"));
                    String kosteus = String.valueOf(main_object.getInt("humidity"));

                    tv_lampotila.setText(lampo);
                    tv_yksityiskohta.setText(kuvaus);
                    tv_kaupunki.setText(kaupunki);
                    tv_ilmanpaine.setText(ipaine);
                    tv_kosteus.setText(kosteus);

                    ilmpaine = ipaine;

                    // Save "ilmanpaine" to the shared preferences, so we get the value to MainActivity
                    SharedPreferences ilmanpaine_sharedpreferences = getSharedPreferences("ilmanpaine_sharedpreferences", MODE_PRIVATE);
                    SharedPreferences.Editor ilmanpaine_editor = ilmanpaine_sharedpreferences.edit();

                    ilmanpaine_editor.putString("ilmanpaine", ilmpaine);
                    ilmanpaine_editor.commit();

                    // Get the info to set the weather icon
                    int id = object.getInt("id");
                    long sunri = sys.getLong("sunrise") * 1000;
                    long sunse = sys.getLong("sunset") * 1000;

                    // Set the weather icon to view
                    tv_saaikoni.setText(Html.fromHtml(setWeatherIcon(id ,sunri, sunse)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // If there is no response, print an error-log
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse (VolleyError error) {
                    Log.d(TAG, "something went wrong");
                    error.printStackTrace();
                }
            }
        );

        // Add the request to the RequestQueue.
        mQueue.add(haku);

    }

    /**
     * Gets the new weather icon using the assets
     * @param actualId , id for the icon we got from api
     * @param sunrise, sunrise time, got from api
     * @param sunset, sunset time, got from api
     * @return icon we show in the view
     */
    // Code for this method got from here: https://androstock.com/tutorials/create-a-weather-app-on-android-android-studio.html
    public static String setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch(id) {
                case 2 : icon = "&#xf01e;";
                    break;
                case 3 : icon = "&#xf01c;";
                    break;
                case 7 : icon = "&#xf014;";
                    break;
                case 8 : icon = "&#xf013;";
                    break;
                case 6 : icon = "&#xf01b;";
                    break;
                case 5 : icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }
}
