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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.LoginCheck;
import com.example.dhrumil.healthcare.dataBase.User;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.login.LoginContinueActivity;
import com.example.dhrumil.healthcare.resetPassword.OnClickInFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.dhrumil.healthcare.R.string.clinic_name;


/**
 * Created by Dhrumil on 2/19/2018.
 */

public class DoctorLoginFragment extends Fragment implements View.OnClickListener ,RadioGroup.OnCheckedChangeListener{

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
    private RadioGroup rdo_grp_gender;
    private RadioButton rdo_btn_male;
    private RadioButton rdo_btn_female;
   // private EditText edt_email_id_login;
    private LoginCheck login_Check;

    private DatabaseReference db;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private String append = "";
    private User user;
    private String mobile,clinic_name,clinic_addr,qualification,profession,registration_no;
    private String gender;

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
       // db = new Database(getActivity().getApplication());
        login_Check = new LoginCheck(getActivity().getApplication());
        //edt_email_id_login = view.findViewById(R.id.edt_email_id_login);
        //edt_email_id_login.setEnabled(false);
        edt_profession_login =  view.findViewById(R.id.edt_profession_login);
        edt_phone_no_login =  view.findViewById(R.id.edt_phone_no_login);
        edt_registration_no_login =  view.findViewById(R.id.edt_registration_no_login);
        edt_clinic_name_login =  view.findViewById(R.id.edt_clinic_name_login);
        edt_clinic_address_login =  view.findViewById(R.id.edt_clinic_address_login);
        edt_quilification_login =  view.findViewById(R.id.edt_quilification_login);
        rdo_grp_gender = view.findViewById(R.id.rdo_grp_gender);
        rdo_btn_male = view.findViewById(R.id.rdo_btn_male);
        rdo_btn_female = view.findViewById(R.id.rdo_btn_female);
    }
        

    private void register(){
        btn_submit_login.setOnClickListener(this);
        btn_cancel_login.setOnClickListener(this);
        rdo_grp_gender.setOnCheckedChangeListener(this);
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

    public void doctor_register() {

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
        mobile = edt_phone_no_login.getText().toString().trim();
        registration_no = edt_registration_no_login.getText().toString().trim();
        clinic_name = edt_clinic_name_login.getText().toString().trim();
        clinic_addr = edt_clinic_address_login.getText().toString().trim();
        qualification = edt_quilification_login.getText().toString().trim();
        profession = edt_profession_login.getText().toString().trim();
        Query query = FirebaseDatabase.getInstance().getReference().child("login_string").orderByChild("mobile").equalTo(mobile);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    Toast.makeText(getActivity(),"This mobile no is already register",Toast.LENGTH_SHORT).show();
                    edt_phone_no_login.setText(null);
                    return;

                }else{
                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference id = db.child(user_id);
                    id.child("mobile").setValue(mobile);
                    id.child("registration_no").setValue(registration_no);
                    id.child("clinic_name").setValue(clinic_name);
                    id.child("clinic addr").setValue(clinic_addr);
                    id.child("qualification").setValue(qualification);
                    id.child("profession").setValue(profession);
                    onClickFragment.onClickButton(HomePage.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()){
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
        }
    }
}
