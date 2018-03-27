package com.example.dhrumil.healthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.LoginCheck;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.resetPassword.ResetPassword;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txt_forgot_password;
    private RelativeLayout rel_lay_footer_text;
    private TextView txt_footer_desc;
    private TextView txt_signup_text;
    private TextView txt_not_now;
    private Button btn_login;
    private EditText edt_username;
    private EditText edt_password;
    private Database db;
    private LoginCheck login_Check;
    private SharedPreference preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inti();
        register();
    }


    private void register() {
        txt_forgot_password.setOnClickListener(this);
        rel_lay_footer_text.setOnClickListener(this);
        txt_footer_desc.setOnClickListener(this);
        txt_signup_text.setOnClickListener(this);
        txt_not_now.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void inti() {
        txt_forgot_password = (TextView) findViewById(R.id.txt_forgot_password);
        rel_lay_footer_text = (RelativeLayout) findViewById(R.id.rel_lay_footer_text);
        txt_footer_desc = (TextView) findViewById(R.id.txt_footer_desc);
        txt_signup_text = (TextView) findViewById(R.id.txt_signup_text);
        txt_not_now = (TextView) findViewById(R.id.txt_not_now);
        btn_login = (Button) findViewById(R.id.btn_login);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        db = new Database(this);
        login_Check = new LoginCheck(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_forgot_password:
                startActivity(new Intent(MainActivity.this, ResetPassword.class));
                break;

            case R.id.rel_lay_footer_text:
            case R.id.txt_footer_desc:
            case R.id.txt_signup_text:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.txt_not_now :
                String user_type = getResources().getString(R.string.naive);
                Intent i = new Intent(MainActivity.this, HomePage.class);
                i.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(i);
                break;
			case R.id.btn_login:
                preferences = new SharedPreference(this,SharedPreference.USER_INFO);
                preferences.setSharedPreference(SharedPreference.USER_NAME,edt_username.getText().toString());
                login();
                break;
        }
    }
	
	public void login(){
        if (!login_Check.isInputEditTextFilled(edt_username, "please fill all line")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_password, "please fill all line")) {
            return;
        }

        if (db.checkUser(edt_username.getText().toString().trim(), edt_password.getText().toString().trim()))
        {

            String user_type = db.getUsertype();
            Intent accountsIntent = new Intent(MainActivity.this, HomePage.class);
            accountsIntent.putExtra(LoginActivity.USER_TYPE,user_type);
            startActivity(accountsIntent);
            finish();
        }
        else
        {
             Toast.makeText(this, "invalid Username and password" , Toast.LENGTH_SHORT).show();
        }
    }

    /*private void setSharedPreference(String username){
        preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        if(preferences != null)
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USER_NAME",username);
            editor.commit();
        }
    }*/
}

