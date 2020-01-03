package com.example.migreeni;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

/**
 * Kalenteri class and activity shows the calendar view for the user
 */
public class kalenteri extends AppCompatActivity {

    CalendarView kalenteri;
    TextView dateview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalenteri);

        kalenteri = findViewById(R.id.kalenteri_view);
        dateview = findViewById(R.id.pvm_muuttuva);

        kalenteri.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                dateview.setText(date);
            }
        });

    }
}
