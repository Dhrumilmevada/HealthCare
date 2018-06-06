package com.example.dhrumil.healthcare.appointment.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.Appointment;
import com.example.dhrumil.healthcare.appointment.adapter.AppointmentAdapter;
import com.example.dhrumil.healthcare.appointment.model.AppointmentData;
import com.example.dhrumil.healthcare.login.LoginActivity;

import java.util.ArrayList;

/**
 * Created by Dhrumil on 5/9/2018.
 */

public class Pending_Appointment extends Fragment {

    private View view;
    private RecyclerView rec_view_pending_appointment;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<AppointmentData> appointmentsList;
    private AppointmentData appointmentData;
    private String user_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_peding__appointment,container,false);
        Appointment activity = (Appointment) getActivity();
        Bundle data = activity.getUserType();
        user_type = data.getString(LoginActivity.USER_TYPE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
        register();
    }

    private void inti() {
        rec_view_pending_appointment = view.findViewById(R.id.rec_view_pending_appointment);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        appointmentsList = new ArrayList<AppointmentData>();
        rec_view_pending_appointment.setLayoutManager(linearLayoutManager);
    }

    private void register() {
        appointmentData = new AppointmentData("Dhrumil","12/12/4589","23:12");
        appointmentsList.add(appointmentData);
        AppointmentAdapter adapter = new AppointmentAdapter(appointmentsList,getContext(),user_type);
        rec_view_pending_appointment.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
