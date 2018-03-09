package com.example.dhrumil.healthcare.dataBase;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.message;

/**
 * Created by Nilesh on 2/23/2018.
 */

public class LoginCheck {
    private Context context;

    public LoginCheck(Context context){
        this.context = context;
    }

    public boolean isInputEditTextFilled(EditText ed_text, String msg){
        String value = ed_text.getText().toString().trim();
        if (value.isEmpty()){
            ed_text.setError(msg);
            hideKeyboardFrom(ed_text);
            return false;
        }

        return true;
    }
    public boolean isInputEditTextFilled(TextView ed_text, String msg){
        String value = ed_text.getText().toString().trim();
        if (value.isEmpty()){
            ed_text.setError(msg);
            hideKeyboardFrom(ed_text);
            return false;
        }

        return true;
    }



    public boolean isInputEditTextEmail(EditText ed_text, String msg){
        String value = ed_text.getText().toString().trim();
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            ed_text.setError(msg);
            hideKeyboardFrom(ed_text);
            return false;

        }
        return true;
    }
    public boolean isInputEditTextMatches(EditText EditText1, EditText EditText2, String msg ){
        String value1 = EditText1.getText().toString().trim();
        String value2 = EditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)){
            EditText2.setError(msg);
            hideKeyboardFrom(EditText2);

            return false;
        }
        return true;
    }
    public boolean isMobileCheck(EditText ed_mob, String msg){
        String value = ed_mob.getText().toString().trim();
        if(value.length()<10){
            ed_mob.setError(msg);
            hideKeyboardFrom(ed_mob);
            return false;
        }
        return true;
    }


    private void hideKeyboardFrom(View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
