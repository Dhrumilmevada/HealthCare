package com.example.dhrumil.healthcare.appointment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.Map;

public class New_Appointment extends AppCompatActivity implements View.OnClickListener{

    private Toolbar tool_bar_new_appointment;
    private EditText edt_hospital_appointment;
    private TextView txt_select_date_appointment;
    private TextView txt_select_time_appointment;
    private EditText edt_desc_appointment;
    private Button btn_done_Appointment;
    private String appointment_status;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    private String hosp_name,date,time,problem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__appointment);
        inti();
        register();
    }

    private void inti() {
        tool_bar_new_appointment = findViewById(R.id.tool_bar_new_appointment);
        edt_hospital_appointment = findViewById(R.id.edt_hospital_appointment);
        txt_select_date_appointment = findViewById(R.id.txt_select_date_appointment);
        txt_select_time_appointment = findViewById(R.id.txt_select_time_appointment);
        edt_desc_appointment = findViewById(R.id.edt_desc_appointment);
        btn_done_Appointment= findViewById(R.id.btn_done_appointment);
        appointment_status  = getResources().getString(R.string.panding);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
    }
    private void register(){
        tool_bar_new_appointment.setTitle(getResources().getString(R.string.book_appointment));
        setSupportActionBar(tool_bar_new_appointment);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_done_appointment:
                book_appoi();
        }
    }
    public void book_appoi(){
        final String user_id = firebaseAuth.getCurrentUser().getUid().toString();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  if(dataSnapshot.hasChild(user_id)){
                DatabaseReference db1 = db.child("Appointment");
                hosp_name = edt_hospital_appointment.getText().toString();
                date = txt_select_date_appointment.getText().toString();
                time = txt_select_time_appointment.getText().toString();
                problem = edt_desc_appointment.getText().toString();
                db1.child("hospital name").setValue(hosp_name);
                db1.child("date").setValue(date);
                db1.child("time").setValue(time);
                db1.child("problem").setValue(problem);
                db1.child("user").setValue(user_id);
                Intent i = new Intent(New_Appointment.this,Appointment.class);
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

