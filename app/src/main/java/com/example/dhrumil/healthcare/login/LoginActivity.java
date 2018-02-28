package com.example.dhrumil.healthcare.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity  implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    private CircleImageView cir_img_profile;
    private EditText edt_name_login;
    private TextView txt_alert_name_login;
    private EditText edt_username_login;
    private TextView txt_alert_username_login;
    private EditText edt_password_login;
    private TextView txt_alert_password_login;
    private EditText edt_reenter_password_login;
    private TextView txt_alert_reenter_password_login;
    private RadioGroup rdo_grp_type;
    private RadioButton rdo_btn_patient;
    private RadioButton rdo_btn_doctor;
    private TextView txt_alert_type_login;
    private Button btn_continue_login;
    private Toolbar tool_bar_login;


    private  String user_type;

    public static String USER_TYPE = "USER_TYPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inti();
        register();
    }

    private void register() {
        rdo_grp_type.setOnCheckedChangeListener(this);
        btn_continue_login.setOnClickListener(this);
        tool_bar_login.setTitle(getResources().getString(R.string.login));
        setSupportActionBar(tool_bar_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void inti() {
        cir_img_profile = (CircleImageView) findViewById(R.id.cir_img_profile);
        edt_name_login = (EditText) findViewById(R.id.edt_name_login);
        txt_alert_name_login = (TextView) findViewById(R.id.txt_alert_name_login);
        edt_username_login = (EditText) findViewById(R.id.edt_username_login);
        txt_alert_username_login = (TextView) findViewById(R.id.txt_alert_username_login);
        edt_password_login = (EditText) findViewById(R.id.edt_password_login);
        txt_alert_password_login = (TextView) findViewById(R.id.txt_alert_password_login);
        edt_reenter_password_login = (EditText) findViewById(R.id.edt_reenter_password_login);
        txt_alert_reenter_password_login = (TextView) findViewById(R.id.txt_alert_reenter_password_login);
        rdo_grp_type = (RadioGroup) findViewById(R.id.rdo_grp_type);
        rdo_btn_doctor = (RadioButton) findViewById(R.id.rdo_btn_doctor);
        rdo_btn_patient = (RadioButton) findViewById(R.id.rdo_btn_patient);
        txt_alert_type_login = (TextView) findViewById(R.id.txt_alert_type_login);
        btn_continue_login = (Button) findViewById(R.id.btn_continue_login);
        tool_bar_login = (Toolbar) findViewById(R.id.tool_bar_login);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch(radioGroup.getId())
        {
            case R.id.rdo_grp_type:
                getUserType(i);
                break;
        }
    }
    private void getUserType(int radioButtonId){
        switch (radioButtonId)
        {
            case R.id.rdo_btn_patient:
                user_type = rdo_btn_patient.getText().toString();
                break;
            case R.id.rdo_btn_doctor:
                user_type = rdo_btn_doctor.getText().toString();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId())
        {
            case R.id.btn_continue_login:
                if(TextUtils.isEmpty(user_type))
                {
                    Toast.makeText(this,"select Doctor or Patient",Toast.LENGTH_LONG).show();
                }
                else {
                    i = new Intent(LoginActivity.this, LoginContinueActivity.class);
                    i.putExtra(USER_TYPE, user_type);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
       // super.onBackPressed();
    }
}
