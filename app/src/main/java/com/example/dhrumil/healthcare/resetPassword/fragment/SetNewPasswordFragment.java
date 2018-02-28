package com.example.dhrumil.healthcare.resetPassword.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dhrumil.healthcare.R;


public class SetNewPasswordFragment extends Fragment implements View.OnClickListener{

    View view;
    TextView txt_otp_info;
    EditText edt_enter_info;
    EditText edt_new_password;
    EditText edt_reenter_new_password;
    Button btn_submit;
    Button btn_cancel;


    public SetNewPasswordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set_new_password, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inti();
        register();

    }

    private void register() {
        btn_submit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    private void inti() {
        txt_otp_info = (TextView) view.findViewById(R.id.txt_otp_info);
        edt_enter_info = (EditText)view.findViewById(R.id.edt_enter_info);
        edt_new_password = (EditText)view.findViewById(R.id.edt_new_password);
        edt_reenter_new_password = (EditText)view.findViewById(R.id.edt_reenter_new_password);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_submit:
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}
