package com.example.dhrumil.healthcare.login.Frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.LoginCheck;
import com.example.dhrumil.healthcare.dataBase.User;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.login.LoginContinueActivity;
import com.example.dhrumil.healthcare.resetPassword.OnClickInFragment;


/**
 * Created by Dhrumil on 2/19/2018.
 */

public class DoctorLoginFragment extends Fragment implements View.OnClickListener {

    View view;
    private Button btn_submit_login;
    private Button btn_cancel_login;
    private OnClickInFragment onClickFragment;
    private EditText edt_profession_login;
    private EditText edt_phone_no_login;
    private EditText edt_registration_no_login;
    private EditText edt_clinic_name_login;
    private EditText edt_clinic_address_login;
    private EditText edt_quilification_login;
   // private EditText edt_email_id_login;
    private LoginCheck login_Check;
    private Database db;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_doctor_login,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onClickFragment = (OnClickInFragment) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
        register();
    }

    private void inti() {
        btn_submit_login = view.findViewById(R.id.btn_submit_login);
        btn_cancel_login = view.findViewById(R.id.btn_cancel_login);
        user = new User();
        db = new Database(getActivity().getApplication());
        login_Check = new LoginCheck(getActivity().getApplication());
        //edt_email_id_login = view.findViewById(R.id.edt_email_id_login);
        //edt_email_id_login.setEnabled(false);
        edt_profession_login =  view.findViewById(R.id.edt_profession_login);
        edt_phone_no_login =  view.findViewById(R.id.edt_phone_no_login);
         edt_registration_no_login =  view.findViewById(R.id.edt_registration_no_login);
         edt_clinic_name_login =  view.findViewById(R.id.edt_clinic_name_login);
         edt_clinic_address_login =  view.findViewById(R.id.edt_clinic_address_login);
        edt_quilification_login =  view.findViewById(R.id.edt_quilification_login);
    }
        

    private void register(){
        btn_submit_login.setOnClickListener(this);
        btn_cancel_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.btn_submit_login:
                    //onClickFragment.onClickButton(HomePage.class);
					doctor_register();
                    break;
                case R.id.btn_cancel_login:
                    onClickFragment.onClickButton(MainActivity.class);
                    break;
            }
    }
	
	public void doctor_register(){

       /* if (!login_Check.isInputEditTextFilled(edt_email_id_login, "please fill the all line")) {
            return;
        }*/
        if (!login_Check.isInputEditTextFilled(edt_phone_no_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isMobileCheck(edt_phone_no_login, "please add mobile")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_registration_no_login, "please fill the all line")) {
            return;
        }

        if (!login_Check.isInputEditTextFilled(edt_clinic_name_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_clinic_address_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_quilification_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_profession_login, "please fill the all line")) {
            return;
        }
        if (!db.checkUse(edt_phone_no_login.getText().toString().trim())) {

            user.setMobile(edt_phone_no_login.getText().toString().trim());
            //user.setEmail(edt_email_id_login.getText().toString().trim());
            user.setRegistrationId(edt_registration_no_login.getText().toString().trim());
            user.setClinicName(edt_clinic_name_login.getText().toString().trim());
            user.setClinicAddr(edt_clinic_address_login.getText().toString().trim());
            user.setHigher(edt_quilification_login.getText().toString().trim());
            user.setpractice(edt_profession_login.getText().toString().trim());
            db.addDoctor(user);
            onClickFragment.onClickButton(HomePage.class);
        }
        else {
            edt_phone_no_login.setText(null);
            Toast.makeText(getActivity(),"This mobile no or email id is already register",Toast.LENGTH_SHORT).show();
        }
    }
}
