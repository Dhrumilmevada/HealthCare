package com.example.dhrumil.healthcare.dataBase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dhrumil on 3/10/2018.
 */

public class SharedPreference {

    private /*final */ SharedPreferences preference;
    private Context mContext;
    private String preference_name;
    public final static String USER_INFO = "USER_INFO";
    public final static String USER_NAME = "USER_NAME";
    public final static String USER_TYPE = "USER_TYPE";

    public SharedPreference(Context mContext,String preference_name) {
        this.mContext = mContext;
        //preference = mContext.getSharedPreferences(preference_name,mContext.MODE_PRIVATE);
        this.preference_name = preference_name;
    }

    public void setSharedPreference(String key,String value){
        preference = mContext.getSharedPreferences(preference_name,mContext.MODE_PRIVATE);
        if (preference != null)
        {
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(key,value);
            editor.commit();
        }
    }

    public String getSharedPreference(String key)
    {
        preference = mContext.getSharedPreferences(preference_name,mContext.MODE_PRIVATE);
        if(preference != null)
        {
            String s = preference.getString(key,"");
            return s;
        }
        return null;
    }

    public void clear()
    {
        preference = mContext.getSharedPreferences(preference_name,mContext.MODE_PRIVATE);
        preference.edit().clear().commit();
    }
}
