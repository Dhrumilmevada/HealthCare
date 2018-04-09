package com.example.dhrumil.healthcare;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dhrumil.healthcare.common.Config;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.homePage.HomePage;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class SlashScreen extends AppCompatActivity {
    private SharedPreferences preferences;
    private Handler handler;
    private ImageView img_view_logo;
    private Animation slash_animation;
    private String username;
    private String usertype;
    private DatabaseReference db;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String name;
    private String image;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);
        inti();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Config.REQUEST_LOCATION);
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                /*preferences = new SharedPreference(getBaseContext(),SharedPreference.USER_INFO);
                username  = preferences.getSharedPreference(SharedPreference.USER_NAME);*/
               // usertype  = preferences.getSharedPreference(SharedPreference.USER_TYPE);
               // usertype = db.getUsertype();

                intiSharedPreference(/*username ,usertype*/);
            }
        }, 3000);
    }

    private void inti() {

        handler = new Handler();
        img_view_logo = (ImageView) findViewById(R.id.img_view_logo);
        slash_animation = AnimationUtils.loadAnimation(this,R.anim.slash_animation);
        img_view_logo.startAnimation(slash_animation);
       // db = new Database(this);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("login_string");
    }

    private void intiSharedPreference(/*String username , final String usertype*/){
        preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        String userName = preferences.getString("USER_NAME","");
        String userType = preferences.getString("USER_TYPE","");
        if(userName.isEmpty()){
            Intent i = new Intent(SlashScreen.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            /*Intent i = new Intent(SlashScreen.this,HomePage.class);
            i.putExtra(LoginActivity.USER_TYPE,usertype);
            startActivity(i);
            finish();*/
            final String user_id = firebaseAuth.getCurrentUser().getUid().toString();
            DatabaseReference db1 = db.child(user_id);
            Log.d("ANY_TAG", "entered onDataChange");
            db1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("ANY_TAG", "entered onDataChange");
                    Map<String,String> map= (Map) dataSnapshot.getValue();
                       name = map.get("name");
                        usertype = map.get("user_type");
                        email = map.get("email");
                        image = map.get("image");
                        Intent accountsIntent = new Intent(SlashScreen.this, HomePage.class);
                        accountsIntent.putExtra(MainActivity.USER_TYPE,usertype);
                        accountsIntent.putExtra(MainActivity.NAME,name);
                        accountsIntent.putExtra(MainActivity.EMAIL_ID,email);
                        accountsIntent.putExtra(MainActivity.URI,image);
                        startActivity(accountsIntent);
                        finish();
                        //}else{
                        //  Toast.makeText(MainActivity.this, "invalid Username and password" , Toast.LENGTH_SHORT).show();
                        //}
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SlashScreen.this , "invalid Username and password" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
}

