package com.example.dhrumil.healthcare.casePaper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Map;

public class NewCasePaper extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_casepaper_date;
    private TextView txt_casepaper_time;
    private EditText edt_casepaper_pat_name;
    private EditText edt_casepaper_symptoms;
    private EditText edt_casepaper_do;
    private EditText edt_casepaper_dont;
    private EditText edt_casepaper_prescription;
    private Button btn_casepaper_done;
    private int date,month,year,hour,minute;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    private int count = 1;
    private String pat_name,day,time,symptoms,dos,dont,prescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_paper);
        inti();
        setCurrentDate();
        setCurrentTime();
    }

    private void inti() {
        txt_casepaper_date = findViewById(R.id.txt_casepaper_date);
        txt_casepaper_time = findViewById(R.id.txt_casepaper_time);
        edt_casepaper_symptoms = findViewById(R.id.edt_casepaper_symptoms) ;
        edt_casepaper_do = findViewById(R.id.edt_casepaper_do);
        edt_casepaper_dont = findViewById(R.id.edt_casepaper_dont);
        edt_casepaper_prescription = findViewById(R.id.edt_casepaper_prescription);
        btn_casepaper_done = findViewById(R.id.btn_casepaper_done);
        edt_casepaper_pat_name = findViewById(R.id.edt_casepaper_pat_name);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("casepaper");
        btn_casepaper_done.setOnClickListener(this);
    }

    private void setCurrentDate() {
        Calendar cal = Calendar.getInstance();
        date = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        txt_casepaper_date.setText(new StringBuilder().append(date).append("-").append(month+1).append("-").append(year));
    }
    private void setCurrentTime() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        txt_casepaper_time.setText(new StringBuilder().append(hour).append(":").append(minute));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_casepaper_done:
                casepaper();
        }
    }
    public void casepaper(){
        pat_name = edt_casepaper_pat_name.getText().toString();
        day = txt_casepaper_date.getText().toString();
        time = txt_casepaper_time.getText().toString();
        symptoms = edt_casepaper_symptoms.getText().toString();
        dos = edt_casepaper_do.getText().toString();
        dont = edt_casepaper_dont.getText().toString();
        prescription = edt_casepaper_prescription.getText().toString();
        final String user_id = firebaseAuth.getCurrentUser().getUid().toString();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  if(dataSnapshot.hasChild(user_id)){
                DatabaseReference db1 = db.child(pat_name);
                db1.child("patient_name").setValue(pat_name);
                db1.child("date").setValue(day);
                db1.child("time").setValue(time);
                db1.child("symptoms").setValue(symptoms);
                db1.child("do's").setValue(dos);
                db1.child("don'ts").setValue(dont);
                db1.child("prescription").setValue(prescription);
                db1.child("doctor").setValue(user_id);
                Intent i = new Intent(NewCasePaper.this,CasePaper.class);
                startActivity(i);
                //}else{
                //  Toast.makeText(MainActivity.this, "invalid Username and password" , Toast.LENGTH_SHORT).show();
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
