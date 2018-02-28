package com.example.dhrumil.healthcare.resetPassword.fragment;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.resetPassword.OnClickInFragment;

public class PhoneNoFragment extends Fragment implements View.OnClickListener{

    private View view;
    private EditText edt_phone_no;
    private Button btn_submit;
    private Button btn_cancel;
    private OnClickInFragment onClickFragment;

    public PhoneNoFragment() {
    }

    @Override
    public void onAttach(Context context) {
        onClickFragment = (OnClickInFragment) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone_no, container, false);
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
        edt_phone_no =  (EditText) view.findViewById(R.id.edt_phone_no);
        btn_submit =  (Button) view.findViewById(R.id.btn_submit);
        btn_cancel =  (Button) view.findViewById(R.id.btn_cancel);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case  R.id.btn_submit:
                onClickFragment.onClickButton();
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}
