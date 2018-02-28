package com.example.dhrumil.healthcare.resetPassword.fragment;


import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.resetPassword.OnClickInFragment;


public class EnterOtpFragment extends Fragment implements View.OnClickListener{


    private View view;
    private EditText edt_otp;
    private Button btn_continue;
    private Button btn_resend;
    private OnClickInFragment onClickFragment;

    public EnterOtpFragment() {

    }

    @Override
    public void onAttach(Context context) {
        onClickFragment = (OnClickInFragment)context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_enter_otp, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
        register();
    }

    private void inti() {
        edt_otp = (EditText) view.findViewById(R.id.edt_otp);
        btn_continue = (Button) view.findViewById(R.id.btn_continue);
        btn_resend = (Button) view.findViewById(R.id.btn_resend);
    }

    private void register() {
        btn_continue.setOnClickListener(this);
        btn_resend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_continue:
               onClickFragment.onClickButton();
                break;
           case R.id.btn_resend:
                break;
        }
    }
}
