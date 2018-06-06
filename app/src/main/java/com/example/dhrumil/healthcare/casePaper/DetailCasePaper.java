package com.example.dhrumil.healthcare.casePaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;

import java.util.Calendar;

public class DetailCasePaper extends AppCompatActivity {

    private TextView txt_casepaper_pat_name;    
    private TextView txt_casepaper_pat_age;    
    private TextView txt_casepaper_pat_weight;    
    private TextView txt_casepaper_date;
    private TextView txt_casepaper_time;
    private TextView txt_casepaper_symptoms;
    private TextView txt_casepaper_do;
    private TextView txt_casepaper_dont;
    private TextView txt_casepaper_prescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_case_paper);
        inti();
        register();

    }

    private void register() {

    }

    private void inti() {
        txt_casepaper_pat_name = findViewById(R.id.txt_casepaper_pat_name);
        txt_casepaper_pat_age = findViewById(R.id.txt_casepaper_pat_age);
        txt_casepaper_pat_weight = findViewById(R.id.txt_casepaper_pat_weight);
        txt_casepaper_date = findViewById(R.id.txt_casepaper_date);
        txt_casepaper_time = findViewById(R.id.txt_casepaper_time);
        txt_casepaper_symptoms = findViewById(R.id.txt_casepaper_symptoms) ;
        txt_casepaper_do = findViewById(R.id.txt_casepaper_do);
        txt_casepaper_dont = findViewById(R.id.txt_casepaper_dont);
        txt_casepaper_prescription = findViewById(R.id.txt_casepaper_prescription);
    }

}
