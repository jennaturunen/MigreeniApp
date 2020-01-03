package com.example.migreeni;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    public static final String tag = "tagaus";
    public static final String TAG = "Ilmanpaine";
    SharedPreferences sharedPref;
    public static final String mypreference = "shared preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData(); // Load data from Shared Preferences
        lataaPaiviaValissa();

        paivita_ilmanpaine();

    }

    @Override
    public void onResume()
    {
        super.onResume();

        paivita_ilmanpaine();
    }

    /**
     * Returns the difference between two days given to it
     * Calculates the difference and divides that by the amount of milliseconds in a day
     *
     * @param one is the first given day
     * @param two is the second given day
     * @return returns the amount of days between the given days
     */
    public long paiviaValissa(Date one, Date two){

        long erotus = ((one.getTime()-two.getTime())/86400000);
        return Math.abs(erotus);
    }

    /**
     * Update the main-views pressure-view to show the saved pressure from the shared preferences
     */
    private void paivita_ilmanpaine() {

        SharedPreferences ilmanpaine_psharedpreferences = getSharedPreferences("ilmanpaine_sharedpreferences", MODE_PRIVATE);
        String ilpaine = ilmanpaine_psharedpreferences.getString("ilmanpaine", "0");
        Log.d(TAG, ilpaine);

        if (ilpaine != null) {
            TextView ipaine_main = findViewById(R.id.ilmanpaine_main);
            ipaine_main.setText(ilpaine);
            Log.d(TAG, ilpaine);
        }
    }

    /**
     * Kalenteri_icon method opens the kalenteri activity when clicked
     * @param view
     */
    public void kalenteri_icon(View view) {
        Intent intent = new Intent(this, kalenteri.class);
        startActivity(intent);
    }
    /**
     * Saa_icon method opens the saa activity when clicked
     * @param view
     */
    public void saa_icon(View view) {
        Intent intent = new Intent(this, koordinaatit.class);
        startActivity(intent);
    }
    /**
     * Merkinta_icon method opens the Merkinta activity when clicked
     * @param view
     */
    public void merkinta_icon(View view) {
        Intent intent = new Intent(this, merkinta.class);
        startActivity(intent);
    }
    /**
     * Viime_merkinta_icon method opens the vanhat_nerkinnat activity when clicked
     * @param view
     */
    public void viime_merkinta_icon(View view) {
        Intent intent = new Intent(this, vanhat_merkinnat.class);
        startActivity(intent);
    }

    /**
     * Loaddata method loads the saved data from shared preferences
     * Our data is saved as objects in Arraylist
     */
    // Load list of entries from shared preferences
    public void loadData() {
       sharedPref = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
       Gson gson = new Gson();
       String json = sharedPref.getString("list", "");
       Type type = new TypeToken<ArrayList<Uusi_merkinta>>() {
       }.getType();
       ArrayList<Uusi_merkinta> listanen;
       listanen = gson.fromJson(json, type);
       if(listanen == null){
           listanen = new ArrayList<>();
           listanen.add(new Uusi_merkinta("Esimerkki","Kohtauksen alkamis ja päättymis aika","Mahdollinen lääkitys","Arvioidaan kipumittarilla","Käyttäjän kommentit"));
       }
       Merkinta_lista.getInstance().setMerkinnat(listanen);

    }

    /**
     * saveData mehtod saves the Arraylist with the migraine entries to the shared preferences
     */
    public void saveData() {
        if(Merkinta_lista.getInstance().getMerkinnat().get(0).getPaivamaara().equals("Esimerkki"))
        {
            Log.d("MSG","kärsh it");
            Merkinta_lista.getInstance().getMerkinnat().remove(0);
            sharedPref = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(Merkinta_lista.getInstance().getMerkinnat());
            editor.putString("list", json);
            editor.apply();
        }else {
            sharedPref = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(Merkinta_lista.getInstance().getMerkinnat());
            editor.putString("list", json);
            editor.commit();
        }
    }

    /**
     *  Finds the day of the last entry and the current day
     *  Takes the days to long paiviaValissa to calculate the difference
     *  Returns the result to be shown in a textView
     *
     */
    public void lataaPaiviaValissa(){

        Calendar kal = Calendar.getInstance();
        String paivamaara = "";
        kal.set(2019, 4, 10);

        int index = Merkinta_lista.getInstance().getMerkinnat().size();
        index--;

        if(index >= 0){

            paivamaara = Merkinta_lista.getInstance().getMerkinnat().get(index).getPaivamaara();
        }

        Date viime_merkinta_paiva = kal.getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {

            viime_merkinta_paiva = formatter.parse(paivamaara);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        Date tanaan = new Date();
        MainActivity obj = new MainActivity();

        long paivat = obj.paiviaValissa(tanaan, viime_merkinta_paiva);

        TextView tvPaivia = findViewById(R.id.viime_merkinta_arvo);
        tvPaivia.setText(String.valueOf(paivat));
    }

    /**
     *  Finds the current date and time
     *  Creates an object with those and adds it to the listView
     * @param view
     */
    public void pikaMerkinta(View view){

        Date date = new Date();
        DateFormat format1 = new SimpleDateFormat("d/M/yyyy");
        String tanaan = format1.format(date);

        Date time = Calendar.getInstance().getTime();
        DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
        String aika = format2.format(time);

        Uusi_merkinta pika = new Uusi_merkinta(tanaan, aika, "","","Pikamerkintä");
        Merkinta_lista.getInstance().getMerkinnat().add(pika);
        saveData();
        Toast.makeText(this,"Merkintä tallennettu",Toast.LENGTH_LONG).show();
    }
}