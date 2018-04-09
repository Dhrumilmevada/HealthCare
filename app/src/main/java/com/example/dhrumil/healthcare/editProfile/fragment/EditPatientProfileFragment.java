package com.example.dhrumil.healthcare.editProfile.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dhrumil on 4/1/2018.
 */

public class EditPatientProfileFragment extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateSetListener,
                                        RadioGroup.OnCheckedChangeListener,AdapterView.OnItemSelectedListener{
    private View view;
    private CircleImageView cir_img_patient_edit;
    private EditText edt_name_patient_edit;
    private EditText edt_phone_patient_edit;
    private TextView txt_date_of_birth_patient_edit;
    private RadioGroup rdo_grp_gender;
    private RadioButton rdo_btn_male;
    private RadioButton rdo_btn_female;
    private EditText edt_address_patient_edit;
    private TextView txt_height_patient_edit;
    private TextView txt_feet_patient_edit;
    private TextView txt_weight_patient_edit;
    private TextView txt_kilo_patient_edit;
    private TextView txt_inch_patient_edit;
    private Spinner spin_feet_patient_edit;
    private Spinner spin_inch_patient_edit;
    private Spinner spin_weight_patient_edit;
    private EditText edt_past_disease;
    private Button btn_update_patient_edit;
    private int date,month,year;
    private String gender;

    private ArrayAdapter<CharSequence> adapterFeetSpinner;
    private ArrayAdapter<CharSequence> adapterInchSpinner;
    private ArrayAdapter<String> adapterWeightSpinner;
    private String weight_scale[];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_patient_edit,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
        register();
    }

    private void register() {
        txt_date_of_birth_patient_edit.setOnClickListener(this);
        rdo_grp_gender.setOnCheckedChangeListener(this);
        spin_inch_patient_edit.setOnItemSelectedListener(this);
        spin_feet_patient_edit.setOnItemSelectedListener(this);
        spin_weight_patient_edit.setOnItemSelectedListener(this);
        btn_update_patient_edit.setOnClickListener(this);
    }

    private void inti() {
        cir_img_patient_edit = view.findViewById(R.id.cir_img_patient_edit);
        edt_name_patient_edit = view.findViewById(R.id.edt_name_patient_edit);
        edt_phone_patient_edit = view.findViewById(R.id.edt_phone_patient_edit);
        txt_date_of_birth_patient_edit = view.findViewById(R.id.txt_date_of_birth_patient_edit);
        rdo_grp_gender = view.findViewById(R.id.rdo_grp_gender);
        rdo_btn_male = view.findViewById(R.id.rdo_btn_male);
        rdo_btn_female = view.findViewById(R.id.rdo_btn_female);
        edt_address_patient_edit = view.findViewById(R.id.edt_address_patient_edit);
        txt_height_patient_edit = view.findViewById(R.id.txt_height_patient_edit);
        txt_feet_patient_edit = view.findViewById(R.id.txt_feet_patient_edit);
        txt_inch_patient_edit = view.findViewById(R.id.txt_inch_patient_edit);
        txt_weight_patient_edit = view.findViewById(R.id.txt_weight_patient_edit);
        txt_kilo_patient_edit = view.findViewById(R.id.txt_kilo_patient_edit);
        spin_feet_patient_edit = view.findViewById(R.id.spin_feet_patient_edit);
        spin_inch_patient_edit = view.findViewById(R.id.spin_inch_patient_edit);
        spin_weight_patient_edit = view.findViewById(R.id.spin_weight_patient_edit);
        edt_past_disease = view.findViewById(R.id.edt_past_disease);
        btn_update_patient_edit = view.findViewById(R.id.btn_update_patient_edit);

        adapterFeetSpinner = ArrayAdapter.createFromResource(getContext(),R.array.feets_scale,android.R.layout.simple_spinner_item);
        adapterFeetSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_feet_patient_edit.setAdapter(adapterFeetSpinner);
        spin_feet_patient_edit.setDropDownWidth(200);

        adapterInchSpinner = ArrayAdapter.createFromResource(getContext(),R.array.inches_scale,android.R.layout.simple_spinner_item);
        adapterInchSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_inch_patient_edit.setAdapter(adapterInchSpinner);
        spin_inch_patient_edit.setDropDownWidth(200);

        weight_scale = new String[150];
        fillStringArray(weight_scale);
        adapterWeightSpinner = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,weight_scale);
        adapterWeightSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_weight_patient_edit.setAdapter(adapterWeightSpinner);
        spin_weight_patient_edit.setSelection(50);
        spin_weight_patient_edit.setDropDownWidth(200);
    }

    private void fillStringArray(String[] weight_scale) {
        for (int i=0 ; i< weight_scale.length ; i++) {
            weight_scale[i] = String.valueOf(i+1);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
        txt_date_of_birth_patient_edit.setText(new StringBuilder().append(date).append("-").append(month+1).append("-").append(year));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.txt_date_of_birth_patient_edit:
                hideSoftKeyboard(edt_phone_patient_edit);
                setCurrentDate();
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(),this,year,month,date);
                dateDialog.show();
                break;
            case R.id.btn_update_patient_edit:
                break;

        }
    }

    private void setCurrentDate() {
        Calendar cal = Calendar.getInstance();
        date = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch(radioGroup.getId()) {
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void hideSoftKeyboard(View view){
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
