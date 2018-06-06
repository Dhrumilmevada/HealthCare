package com.example.dhrumil.healthcare.appointment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.fragment.DocPendingDetailAppoint;
import com.example.dhrumil.healthcare.appointment.fragment.DoctorDetailAppoint;
import com.example.dhrumil.healthcare.appointment.fragment.PatientDetailAppoint;
import com.example.dhrumil.healthcare.login.LoginActivity;

public class DetailAppointment extends AppCompatActivity {

    private String user_type;
    private FrameLayout frame_lay_detail_appointment;
    private FragmentManager fm;
    private String appoinment_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_appointment);
        getIntentData();
        inti();
        setFragment();
    }

    private void inti() {
        frame_lay_detail_appointment = findViewById(R.id.frame_lay_detail_appointment);
    }

    private void setFragment(){
        fm = getSupportFragmentManager();
        Fragment frag;
        if(user_type.equals(getResources().getString(R.string.patient))){
            frag = new PatientDetailAppoint();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_lay_detail_appointment,frag).commit();
        }
        else if(user_type.equals(getResources().getString(R.string.doctor)) && !appoinment_status.equals(getResources().getString(R.string.panding))){
            frag = new DoctorDetailAppoint();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_lay_detail_appointment,frag).commit();
        }
        else if(user_type.equals(getResources().getString(R.string.doctor)) && appoinment_status.equals(getResources().getString(R.string.panding))){
            frag = new DocPendingDetailAppoint();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_lay_detail_appointment,frag).commit();
        }
    }

    public void getIntentData() {
        user_type = getIntent().getStringExtra(LoginActivity.USER_TYPE);
    }
}
