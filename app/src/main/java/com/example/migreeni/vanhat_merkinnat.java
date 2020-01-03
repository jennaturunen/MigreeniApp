package com.example.migreeni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

/**
 * Vanhat_merkinnat class and activity shows the old migraine note entries in a listview
 */
public class vanhat_merkinnat extends AppCompatActivity {

    public static final String EXTRA = "";
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanhat_merkinnat);

        luoLista();

    }

    /**
     * Creates a listView in which it will show all the entries by the user.
     * By clicking an item from the list, an activity that shows details of the chosen item starts.
     *
     */
    public void luoLista(){

        lv = findViewById(R.id.vanhat_merkinnat_lista);
        ArrayAdapter adapter = (new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Merkinta_lista.getInstance().getMerkinnat()));
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextActivity = new Intent(vanhat_merkinnat.this, vanhat_merkinnat_avattuinfo.class);
                nextActivity.putExtra(EXTRA, position);

                startActivity(nextActivity);
            }
        });
    }

    /**
     * Deletes all the entries from the listView
     *
     * @param view the view
     */
    public void poista_merkinnat(View view){
        Merkinta_lista.getInstance().clearMerkinnat();
        lv.setAdapter(null);
        saveData();
    }

    public void saveData() {
        SharedPreferences sharedPref = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Merkinta_lista.getInstance().getMerkinnat());
        editor.putString("list", json);
        editor.apply();
    }
}
