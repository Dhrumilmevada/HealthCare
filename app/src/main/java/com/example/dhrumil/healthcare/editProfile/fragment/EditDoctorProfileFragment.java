package com.example.dhrumil.healthcare.editProfile.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dhrumil.healthcare.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dhrumil on 4/2/2018.
 */

public class EditDoctorProfileFragment extends Fragment implements View.OnClickListener,TimePickerDialog.OnTimeSetListener,RadioGroup.OnCheckedChangeListener
                                                                                                    ,DatePickerDialog.OnDateSetListener{

    private View view;
    private CircleImageView cir_img_doctor_edit;
    private EditText edt_name_doctor_edit;
    private EditText edt_phone_doctor_edit;
    private TextView txt_date_of_birth_doctor_edit;
    private RadioGroup rdo_grp_gender;
    private RadioButton rdo_btn_male;
    private RadioButton rdo_btn_female;
    private EditText edt_high_qualification_doctor_edit;
    private EditText edt_speciality_doctor_edit;
    private EditText edt_practice_as_doctor_edit;
    private EditText edt_exprience_doctor_edit;
    private TextView txt_morning_to_time;
    private TextView txt_morning_from_time;
    private TextView txt_evening_to_time;
    private TextView txt_evening_from_time;
    private EditText edt_hos_name_doctor_edit;
    private EditText edt_hos_address_doctor_edit;
    private TextView txt_hos_close_at;
    private TextView txt_hos_open_at;
    private Button btn_updated_doctor_edit;
    private int date,year,month;
    private int hour,minute;
    private int datePickerInputID;
    private String gender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_doctor_edit,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
        register();
    }

    private void inti() {
        cir_img_doctor_edit = view.findViewById(R.id.cir_img_doctor_edit);
        edt_name_doctor_edit = view.findViewById(R.id.edt_name_doctor_edit);
        edt_phone_doctor_edit = view.findViewById(R.id.edt_phone_doctor_edit);
        txt_date_of_birth_doctor_edit =  view.findViewById(R.id.txt_date_of_birth_doctor_edit);
        rdo_grp_gender = view.findViewById(R.id.rdo_grp_gender);
        rdo_btn_male = view.findViewById(R.id.rdo_btn_male);
        rdo_btn_female = view.findViewById(R.id.rdo_btn_female);
        edt_high_qualification_doctor_edit = view.findViewById(R.id.edt_high_qualification_doctor_edit);
        edt_speciality_doctor_edit = view.findViewById(R.id.edt_speciality_doctor_edit);
        edt_exprience_doctor_edit = view.findViewById(R.id.edt_exprience_doctor_edit);
        edt_practice_as_doctor_edit= view.findViewById(R.id.edt_practice_as_doctor_edit);
        txt_morning_from_time = view.findViewById(R.id.txt_morning_from_time);
        txt_morning_to_time = view.findViewById(R.id.txt_morning_to_time);;
        txt_evening_from_time = view.findViewById(R.id.txt_evening_from_time);
        txt_evening_to_time = view.findViewById(R.id.txt_evening_to_time);
        edt_hos_name_doctor_edit = view.findViewById(R.id.edt_hos_name_doctor_edit);
        edt_hos_address_doctor_edit = view.findViewById(R.id.edt_hos_address_doctor_edit);
        txt_hos_close_at = view.findViewById(R.id.txt_hos_close_at);
        txt_hos_open_at = view.findViewById(R.id.txt_hos_open_at);
        btn_updated_doctor_edit = view.findViewById(R.id.btn_update_doctor_edit);
        datePickerInputID = -1;
    }

    private void register() {
        txt_date_of_birth_doctor_edit.setOnClickListener(this);
        btn_updated_doctor_edit.setOnClickListener(this);
        rdo_grp_gender.setOnCheckedChangeListener(this);
        txt_morning_to_time.setOnClickListener(this);
        txt_morning_from_time.setOnClickListener(this);
        txt_evening_to_time.setOnClickListener(this);
        txt_evening_from_time.setOnClickListener(this);
        txt_hos_close_at.setOnClickListener(this);
        txt_hos_open_at.setOnClickListener(this);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
       switch (datePickerInputID){
           case R.id.txt_morning_to_time:
               txt_morning_to_time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
               break;
           case R.id.txt_morning_from_time:
               txt_morning_from_time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
                break;
           case R.id.txt_evening_to_time:
               txt_evening_to_time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
               break;
           case R.id.txt_evening_from_time:
               txt_evening_from_time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
               break;
           case R.id.txt_hos_close_at:
               txt_hos_close_at.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
               break;
           case R.id.txt_hos_open_at:
               txt_hos_open_at.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
               break;
       }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.txt_date_of_birth_doctor_edit:
                hideSoftKeyboard(edt_phone_doctor_edit);
                setCurrentDate();
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(),this,year,month,date);
                dateDialog.show();
                break;
            case R.id.txt_morning_from_time:
                hideSoftKeyboard(edt_exprience_doctor_edit);
                setCurrentTime();
                datePickerInputID = view.getId();
                TimePickerDialog timeDialog = new TimePickerDialog(getContext(),this,hour,minute,true);
                timeDialog.show();
                break;
            case R.id.txt_morning_to_time:
                setCurrentTime();
                datePickerInputID = view.getId();
                TimePickerDialog timeDialog1 = new TimePickerDialog(getContext(),this,hour,minute,true);
                timeDialog1.show();
                break;
            case R.id.txt_evening_from_time:
                setCurrentTime();
                datePickerInputID = view.getId();
                TimePickerDialog timeDialog2 = new TimePickerDialog(getContext(),this,hour,minute,true);
                timeDialog2.show();
                break;
            case R.id.txt_evening_to_time:
                setCurrentTime();
                datePickerInputID = view.getId();
                TimePickerDialog timeDialog3 = new TimePickerDialog(getContext(),this,hour,minute,true);
                timeDialog3.show();
                break;
            case R.id.txt_hos_close_at:
                hideSoftKeyboard(edt_hos_address_doctor_edit);
                setCurrentTime();
                datePickerInputID = view.getId();
                TimePickerDialog timeDialog4 = new TimePickerDialog(getContext(),this,hour,minute,true);
                timeDialog4.show();
                break;
            case R.id.txt_hos_open_at:
                setCurrentTime();
                datePickerInputID = view.getId();
                TimePickerDialog timeDialog5 = new TimePickerDialog(getContext(),this,hour,minute,true);
                timeDialog5.show();
                break;
            case R.id.btn_update_patient_edit:
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.rdo_grp_gender:
                getGender(i);
                break;
        }
    }

    private void getGender(int radioButton) {
        switch (radioButton){
            case R.id.rdo_btn_male:
                gender = rdo_btn_male.getText().toString();
                break;
            case R.id.rdo_btn_female:
                gender = rdo_btn_female.getText().toString();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
        txt_date_of_birth_doctor_edit.setText(new StringBuilder().append(date).append("-").append(month+1).append("-").append(year));
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setCurrentDate() {
        Calendar cal = Calendar.getInstance();
        date = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
    }
    private void setCurrentTime() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }
}
