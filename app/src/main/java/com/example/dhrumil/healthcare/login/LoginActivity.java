package com.example.dhrumil.healthcare.login;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhrumil.healthcare.MainActivity;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.dataBase.LoginCheck;
import com.example.dhrumil.healthcare.dataBase.SharedPreference;
import com.example.dhrumil.healthcare.dataBase.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
    private LoginCheck login_Check;
//    private Database db;
    private User user;
    private  String user_type;
    private SharedPreferences preferences;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    private Uri uri=null;
    private Uri uri1;
    public static String URI1 = "URI";
    private StorageReference image_ref;
    public static String NAME = "NAME";
    final int REQUEST_CODE_GALLERY = 999;

    public static String USER_TYPE = "USER_TYPE";
    public static String EMAIL_ID = "EMAIL_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("login_string");
        image_ref = FirebaseStorage.getInstance().getReference();
        inti();
        register();
    }

    private void register() {
        rdo_grp_type.setOnCheckedChangeListener(this);
        btn_continue_login.setOnClickListener(this);
        tool_bar_login.setTitle(getResources().getString(R.string.signup));
        cir_img_profile.setOnClickListener(this);
        setSupportActionBar(tool_bar_login);

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
        login_Check = new LoginCheck(this);
       // db = new Database(this);
        user = new User();

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

   private void setSharedPreference(String userType,String username){
        preferences = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        if(preferences != null)
        {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USER_TYPE",userType);
            editor.putString("USER_NAME",username);
            editor.commit();
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
                   // preferences.setSharedPreference(SharedPreference.USER_TYPE,user_type);
                    setSharedPreference(user_type,edt_username_login.getText().toString());
                    check();
                }
                break;
            case R.id.cir_img_profile:
                ActivityCompat.requestPermissions(
                        LoginActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data!=null){
            uri = data.getData();
            cir_img_profile.setImageURI(uri);
           /* filepath = image_ref.child("images").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("ANY_TAG", "entered onDataChange");
                    Uri down_uri = taskSnapshot.getDownloadUrl();
                    uri1 = down_uri;
                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference id = db.child(user_id);

                    if (uri == null) {

                        id.child("image").setValue("no image");
                    } else {
                        Log.d("ANY_TAG", "entered onDataChange/childExists");

                        id.child("image").setValue(down_uri.toString());
                    }
                }
            });*/
            /*try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                cir_img_profile.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
        }


    }

    public void check(){
        if (!login_Check.isInputEditTextFilled(edt_name_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_username_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextEmail(edt_username_login, "please enter valid email id")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_password_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextFilled(edt_reenter_password_login, "please fill the all line")) {
            return;
        }
        if (!login_Check.isInputEditTextMatches(edt_password_login, edt_reenter_password_login,"please make sure confirmation password is same as password" )) {
            return;
        }

        final String name = edt_name_login.getText().toString();
        final String email = edt_username_login.getText().toString();
        String password = edt_password_login.getText().toString();
        String conf_password = edt_reenter_password_login.getText().toString();

        Log.d("ANY_TAG", "entered onDataChange");
        StorageReference filepath = image_ref.child("images").child(uri.getLastPathSegment());
        Log.d("ANY_TAG", "entered onDataChange");
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("ANY_TAG", "entered onDataChange");
                Uri down_uri = taskSnapshot.getDownloadUrl();
                uri1 = down_uri;
                Log.d("ANY_TAG", "entered onDataChange");
                String user_id = firebaseAuth.getCurrentUser().getUid();
                DatabaseReference id = db.child(user_id);
                Log.d("ANY_TAG", "entered onDataChange");
                if (uri == null) {
                    id.child("image").setValue("no image");
                } else {
                    id.child("image").setValue(down_uri.toString());
                }
            }
        });

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this, "createUserWithEmail:1onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String user_id = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference id = db.child(user_id);
                            id.child("name").setValue(name);
                            id.child("email").setValue(email);
                            id.child("user_type").setValue(user_type);
                            Intent i = new Intent(LoginActivity.this, LoginContinueActivity.class);
                            i.putExtra(USER_TYPE,user_type);
                            i.putExtra(URI1,uri1);
                            i.putExtra(NAME,name);
                            i.putExtra(EMAIL_ID,email);
                            startActivity(i);
                        }
                    }
                });
         /*firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()) {
                     String user_id = firebaseAuth.getCurrentUser().getUid();
                     DatabaseReference id = db.child(user_id);
                     id.child("name").setValue(name);
                     id.child("user_type").setValue(user_type);
                     id.child("image").setValue("image");
                     Intent i = new Intent(LoginActivity.this, LoginContinueActivity.class);
                     i.putExtra(USER_TYPE,user_type);
                     startActivity(i);
                     finish();

                 } else {
                     edt_username_login.setText(null);
                     Toast.makeText(LoginActivity.this,"This record is already register",Toast.LENGTH_SHORT).show();
                 }
             }
         });
        /*if (!db.checkUser(edt_username_login.getText().toString().trim())) {
            user.setName(edt_name_login.getText().toString().trim());
            user.setEmail(edt_username_login.getText().toString().trim());
            user.setPassword(edt_password_login.getText().toString().trim());
            user.setusertype(user_type);
            user.setImage(imageViewToByte(cir_img_profile));
            db.addUser(user);


            Intent i = new Intent(LoginActivity.this, LoginContinueActivity.class);
            i.putExtra(USER_TYPE,user_type);
            startActivity(i);
            finish();
        }
        else {
            edt_username_login.setText(null);
            Toast.makeText(this,"This record is already register",Toast.LENGTH_SHORT).show();
            finish();
        }*/

    }
    @Override
    public void onBackPressed() {
        /*Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();*/
        super.onBackPressed();
    }
}
