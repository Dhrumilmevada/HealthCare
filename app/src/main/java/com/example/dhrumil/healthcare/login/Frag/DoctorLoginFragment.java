package com.example.dhrumil.healthcare.login.Frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
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

    private void inti(){
        btn_submit_login = view.findViewById(R.id.btn_submit_login);
        btn_cancel_login = view.findViewById(R.id.btn_cancel_login);
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
                    onClickFragment.onClickButton(HomePage.class);
                    break;
                case R.id.btn_cancel_login:
                    onClickFragment.onClickButton(MainActivity.class);
                    break;
            }
    }
}
