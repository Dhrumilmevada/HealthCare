package com.example.dhrumil.healthcare.appointment.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;

/**
 * Created by Dhrumil on 5/10/2018.
 */

public class PatientDetailAppoint extends Fragment {

    private View view;
    private TextView txt_hos_name;
    private TextView txt_doc_name;
    private TextView txt_hospital_add;
    private TextView txt_appointment;
    private TextView txt_appointment_date;
    private TextView txt_appointment_time;
    private TextView txt_desc_appoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_patient_detail_appoint,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
    }

    private void inti() {
        txt_hos_name = view.findViewById(R.id.txt_hos_name);
        txt_doc_name = view.findViewById(R.id.txt_doc_name);
        txt_hospital_add = view.findViewById(R.id.txt_hospital_add);
        txt_appointment = view.findViewById(R.id.txt_appointment);
        txt_appointment_time = view.findViewById(R.id.txt_appointment_time);
        txt_appointment_date = view.findViewById(R.id.txt_appointment_date);
        txt_desc_appoint = view.findViewById(R.id.txt_desc_appoint);
    }
}
