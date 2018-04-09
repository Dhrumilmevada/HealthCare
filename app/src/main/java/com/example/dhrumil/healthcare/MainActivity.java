package com.example.dhrumil.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txt_forgot_password;
    private RelativeLayout rel_lay_footer_text;
    private TextView txt_footer_desc;
    private TextView txt_signup_text;
    private TextView txt_not_now;
    private Button btn_login;
    private EditText edt_username;
    private EditText edt_password;
    //private Database db;
    private LoginCheck login_Check;
    private SharedPreferences preferences;

    private static final int REQ_CODE = 234;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    public static String USER_TYPE = "USER_TYPE";
    public static String NAME = "NAME";
    public static String IMAGE = "IMAGE";
    public static String EMAIL_ID = "EMAIL_ID";
    public static String URI = "URI";
    private GoogleApiClient googleApiClient;
    private SignInButton google_signin;
    private GoogleSignInClient googleclient;
    private GoogleSignInOptions googleSignInOptions;
    private String user_type;
    private String name;
    private String email;

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
        google_signin.setOnClickListener(this);
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
      //  db = new Database(this);
        google_signin = (SignInButton) findViewById(R.id.google_signin);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("login_string");
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //   googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();
        googleclient = GoogleSignIn.getClient(this, googleSignInOptions);
        //  db = new Database(this);
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
                break;

            case R.id.txt_not_now :
                String user_type = getResources().getString(R.string.naive);
                Intent i = new Intent(MainActivity.this, HomePage.class);
                i.putExtra(LoginActivity.USER_TYPE,user_type);
                startActivity(i);
                break;
			case R.id.btn_login:
               setSharedPreference(edt_username.getText().toString());
                login();
                break;
            case R.id.google_signin:
                signIn();
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

        /*if (db.checkUser(edt_username.getText().toString().trim(), edt_password.getText().toString().trim()))
        {
            Cursor cursor = db.getUserType();
            String user_type = cursor.getString(4);
            //String user_type = db.getUsertype();
            //Shared_pre sh = new Shared_pre(this);
            String name = edt_username.getText().toString().trim();
            //sh.setname(name);
            Intent accountsIntent = new Intent(MainActivity.this, HomePage.class);
            accountsIntent.putExtra(LoginActivity.USER_TYPE,user_type);
            startActivity(accountsIntent);
            finish();
        }*/


        String edt_username1 = edt_username.getText().toString().trim();
        String edt_password1 = edt_password.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(edt_username1,edt_password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    checkUser();
                }else{
                    Toast.makeText(MainActivity.this, "Error login" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkUser(){
        final String user_id = firebaseAuth.getCurrentUser().getUid().toString();
        DatabaseReference db1 = db.child(user_id);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  if(dataSnapshot.hasChild(user_id)){
                Map<String,String> map= (Map) dataSnapshot.getValue();
                name = map.get("name");
                user_type = map.get("user_type");
                email = map.get("email");
                Intent accountsIntent = new Intent(MainActivity.this, HomePage.class);
                accountsIntent.putExtra(MainActivity.USER_TYPE,user_type);
                accountsIntent.putExtra(MainActivity.NAME,name);
                accountsIntent.putExtra(MainActivity.EMAIL_ID,email);
                startActivity(accountsIntent);
                //}else{
                //  Toast.makeText(MainActivity.this, "invalid Username and password" , Toast.LENGTH_SHORT).show();
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setSharedPreference(String username){
        preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        if(preferences != null)
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USER_NAME",username);
            editor.commit();
        }
    }

    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = googleclient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == REQ_CODE) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "signInWithCredential:success");
                            user_type = "Doctor";
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String user_id = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference id = db.child(user_id);
                            name = user.getDisplayName();
                            email = user.getEmail();
                            String image = user.getPhotoUrl().toString();
                            id.child("name").setValue(user.getDisplayName());
                            id.child("email").setValue(user.getEmail());
                            Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            Intent accountsIntent = new Intent(MainActivity.this, HomePage.class);
                            accountsIntent.putExtra(MainActivity.USER_TYPE,user_type);
                            accountsIntent.putExtra(MainActivity.NAME,name);
                            accountsIntent.putExtra(MainActivity.EMAIL_ID,email);
                            //accountsIntent.putExtra(MainActivity.IMAGE,image);
                            startActivity(accountsIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

