package com.example.dhrumil.healthcare.login.Frag;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.LoginCheck;
import com.example.dhrumil.healthcare.dataBase.User;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.resetPassword.OnClickInFragment;

import java.util.Calendar;

/**
 * Created by Dhrumil on 2/20/2018.
 */

public class PatientLoginFragment extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemSelectedListener{


    View view;
    private TextView txt_date_of_birth_login;
   // private EditText edt_email_id_login;
    private EditText edt_phone_no_login;
    private EditText edt_age_login;
    private EditText edt_address_login;
    private Calendar myCalender;
    private int month,year,day;
    private Spinner spin_feet_login;
    private Spinner spin_inch_login;
    private Spinner spin_weight_login;
    private ArrayAdapter<CharSequence> adapterFeetSpinner;
    private ArrayAdapter<CharSequence> adapterInchSpinner;
    private String weight_scale [];
    private ArrayAdapter<String> adapterWeightSpinner;
    private Button btn_submit_login;
    private Button btn_cancel_login;
    private OnClickInFragment onClickFragment;
    private Database db;
    private LoginCheck login_Check;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_patient_login,container,false);
            return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
        register();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onClickFragment = (OnClickInFragment) context;
    }

    private void inti() {
        txt_date_of_birth_login = (TextView) view.findViewById(R.id.txt_date_of_birth_login);
        spin_feet_login = view.findViewById(R.id.spin_feet_login);
        spin_inch_login = view.findViewById(R.id.spin_inch_login);
        spin_weight_login = view.findViewById(R.id.spin_weight_login);

        adapterFeetSpinner = ArrayAdapter.createFromResource
                (getActivity().getApplicationContext(),R.array.feets_scale,android.R.layout.simple_spinner_item);
        adapterFeetSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_feet_login.setAdapter(adapterFeetSpinner);
        spin_feet_login.setDropDownWidth(200);

        adapterInchSpinner = ArrayAdapter.createFromResource
                (getContext(),R.array.inches_scale,android.R.layout.simple_spinner_item);
        adapterInchSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_inch_login.setAdapter(adapterInchSpinner);
        spin_inch_login.setDropDownWidth(200);

        weight_scale = new String[150];
        fillStringArray(weight_scale);
        adapterWeightSpinner = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,weight_scale);
        adapterWeightSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_weight_login.setAdapter(adapterWeightSpinner);
        spin_weight_login.setSelection(50);
        spin_weight_login.setDropDownWidth(200);

        btn_submit_login = view.findViewById(R.id.btn_submit_login);
        btn_cancel_login = view.findViewById(R.id.btn_cancel_login);
        //edt_email_id_login = view.findViewById(R.id.edt_email_id_login);
        edt_address_login = view.findViewById(R.id.edt_address_login);
        edt_age_login = view.findViewById(R.id.edt_age_login);
        edt_phone_no_login = view.findViewById(R.id.edt_phone_no_login);
        db = new Database(getActivity());
        login_Check = new LoginCheck(getActivity());
        user = new User();
    }


    private void register() {
        txt_date_of_birth_login.setOnClickListener(this);
        spin_feet_login.setOnItemSelectedListener(this);
        spin_inch_login.setOnItemSelectedListener(this);
        spin_weight_login.setOnItemSelectedListener(this);
        btn_submit_login.setOnClickListener(this);
        btn_cancel_login.setOnClickListener(this);
    }

    private void setCurrentDate() {
        myCalender = Calendar.getInstance();
        day = myCalender.get(Calendar.DAY_OF_MONTH);
        month = myCalender.get(Calendar.MONTH);
        year = myCalender.get(Calendar.YEAR);
    }

    private void fillStringArray(String[] weight_scale) {
        for (int i=0 ; i< weight_scale.length ; i++)
        {
            weight_scale[i] = String.valueOf(i+1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_date_of_birth_login:
                hideSoftKeyboard(edt_phone_no_login);
                setCurrentDate();
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(),this,year,month,day);
                dateDialog.show();
                break;
            case R.id.btn_submit_login:
                //onClickFragment.onClickButton(HomePage.class);
				patient_Register();
                break;
            case R.id.btn_cancel_login:
                onClickFragment.onClickButton(MainActivity.class);
                break;
        }
    }
	
	public void patient_Register(){
       /* if (!login_Check.isInputEditTextFilled(edt_email_id_login, "please fill the all line")) {
            return;
        }*/
        if (!login_Check.isInputEditTextFilled(edt_phone_no_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isMobileCheck(edt_phone_no_login, "please add mobile")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_age_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_address_login, "please fill the all line")) {
            return;
        }
        if(!login_Check.isInputEditTextFilled(txt_date_of_birth_login,"please add the birth of date")){
            return;
        }

        if (!db.checkUse_patient(edt_phone_no_login.getText().toString().trim())) {

            user.setMobile(edt_phone_no_login.getText().toString().trim());
           // user.setEmail(edt_email_id_login.getText().toString().trim());
            user.setAddr(edt_address_login.getText().toString().trim());
            user.setBirthDate(txt_date_of_birth_login.getText().toString().trim());
            db.addPatient(user);
            onClickFragment.onClickButton(HomePage.class);
            // Snack Bar to show success message that record saved successfully
        }
        else {
           // edt_email_id_login.setText(null);
            Toast.makeText(getActivity(),"This mobile no or email id is already register", Toast.LENGTH_SHORT).show();
        }
    }
	
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        txt_date_of_birth_login.setText(new StringBuilder().append(day).append("-").append(month+1).append("-").append(year));
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
