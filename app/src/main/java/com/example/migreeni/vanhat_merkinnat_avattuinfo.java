package com.example.migreeni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * vanhat_merkinnat_avattuinfo class and activity shows the details of the chosen data entry
 */
public class vanhat_merkinnat_avattuinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanhat_merkinnat_avattuinfo);

        asetaTiedot();
    }

    /**
     *  Retrieves and sets the information to the specified textViews
     *
     */
    public void asetaTiedot(){

        Bundle b = getIntent().getExtras();
        int position = b.getInt(vanhat_merkinnat.EXTRA, 0);

        String date = "Päivämäärä: " + Merkinta_lista.getInstance().getMerkinnat().get(position).getPaivamaara();
        String time = "Aika: " + Merkinta_lista.getInstance().getMerkinnat().get(position).getAika();
        String medic = "Otettu lääke: " + Merkinta_lista.getInstance().getMerkinnat().get(position).getLaake();
        String pain = "Kipu: " + Merkinta_lista.getInstance().getMerkinnat().get(position).getKipu();
        String extra = "Lisätietoja: " + Merkinta_lista.getInstance().getMerkinnat().get(position).getLisatiedot();


        TextView tv1 = findViewById(R.id.textView_date);
        tv1.setText(date);

        TextView tv2 = findViewById(R.id.textView_time);
        tv2.setText(time);

        TextView tv3 = findViewById(R.id.textView_medic);
        tv3.setText(medic);

        TextView tv4 = findViewById(R.id.textView_pain);
        tv4.setText(pain);

        TextView tv5 = findViewById(R.id.textView_extra);
        tv5.setText(extra);
    }

}