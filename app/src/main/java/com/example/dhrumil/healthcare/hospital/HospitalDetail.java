package com.example.dhrumil.healthcare.hospital;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.appointment.New_Appointment;

import de.hdodenhof.circleimageview.CircleImageView;

public class HospitalDetail extends AppCompatActivity implements View.OnClickListener,RatingDialog.RatingDialogListener{

    private Toolbar tool_bar_hospital_detail;
    private TextView txt_hospital_name_hospital_detail;
    private TextView txt_hospital_phone_hospital_detail;
    private TextView txt_hospital_speciality_hospital_detail;
    private TextView txt_hospital_time_hospital_detail;
    private TextView txt_hospital_add_hospital_detail;
    private ImageView img_view_gmap_link;
    private CircleImageView cir_img_doctor_profile;
    private TextView txt_doctor_name_hospital_detail;
    private TextView txt_doctor_study_hospital_detail;
    private TextView txt_doctor_exprience_hospital_detail;
    private TextView txt_hospital_rating;
    private ImageView img_view_rate_1_hos_detail;
    private ImageView img_view_rate_2_hos_detail;
    private ImageView img_view_rate_3_hos_detail;
    private ImageView img_view_rate_4_hos_detail;
    private ImageView img_view_rate_5_hos_detail;
    private TextView txt_give_rating;
    private RelativeLayout rel_lay_book_appointment;
    private TextView txt_footer_desc;
    private TextView txt_signup_text;
    private Drawable img;
    private Drawable img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        inti();
        register();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRatingStar();
    }

    private void inti() {
        tool_bar_hospital_detail = findViewById(R.id.tool_bar_hospital_detail);
        txt_hospital_name_hospital_detail= findViewById(R.id.txt_hospital_name_hospital_detail);
        txt_hospital_phone_hospital_detail= findViewById(R.id.txt_hospital_phone_hospital_detail);
        txt_hospital_speciality_hospital_detail= findViewById(R.id.txt_hospital_speciality_hospital_detail);
        txt_hospital_time_hospital_detail= findViewById(R.id.txt_hospital_time_hospital_detail);
        txt_hospital_add_hospital_detail= findViewById(R.id.txt_hospital_add_hospital_detail);
        img_view_gmap_link= findViewById(R.id.img_view_gmap_link);
        cir_img_doctor_profile= findViewById(R.id.cir_img_doctor_profile);
        txt_doctor_name_hospital_detail= findViewById(R.id.txt_doctor_name_hospital_detail);
        txt_doctor_study_hospital_detail= findViewById(R.id.txt_doctor_study_hospital_detail);
        txt_doctor_exprience_hospital_detail= findViewById(R.id.txt_doctor_exprience_hospital_detail);
        txt_hospital_rating= findViewById(R.id.txt_hospital_rating);
        img_view_rate_1_hos_detail= findViewById(R.id.img_view_rate_1_hos_detail);
        img_view_rate_2_hos_detail= findViewById(R.id.img_view_rate_2_hos_detail);
        img_view_rate_3_hos_detail= findViewById(R.id.img_view_rate_3_hos_detail);
        img_view_rate_4_hos_detail= findViewById(R.id.img_view_rate_4_hos_detail);
        img_view_rate_5_hos_detail= findViewById(R.id.img_view_rate_5_hos_detail);
        txt_give_rating= findViewById(R.id.txt_give_rating);
        rel_lay_book_appointment= findViewById(R.id.rel_lay_book_appointment);
        txt_footer_desc= findViewById(R.id.txt_footer_desc);
        txt_signup_text= findViewById(R.id.txt_signup_text);
        img = ContextCompat.getDrawable(this,R.drawable.ic_star_yellow_24dp);
        img2 = ContextCompat.getDrawable(this,R.drawable.ic_star_border_black_24dp);

    }

    private void register() {
        txt_footer_desc.setText("Book an Appointment");
        txt_signup_text.setText("");
        img_view_gmap_link.setOnClickListener(this);
        txt_give_rating.setOnClickListener(this);
        rel_lay_book_appointment.setOnClickListener(this);
        txt_footer_desc.setOnClickListener(this);
        txt_signup_text.setOnClickListener(this);
        tool_bar_hospital_detail.setTitle(getResources().getString(R.string.hospital));
        setSupportActionBar(tool_bar_hospital_detail);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_view_gmap_link:
                String hospital = txt_hospital_name_hospital_detail.getText().toString();
                String uri = "http://maps.google.com/maps?saddr=" + 23.0338049 + "," + 72.5443953 + "("+hospital+")"+
                        "&daddr=" + 22.9879723 + "," + 72.6337599+"(Your Location)";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.txt_give_rating:
                openDialog();
                break;
            case R.id.rel_lay_book_appointment:
            case R.id.txt_footer_desc:
            case R.id.txt_signup_text:
                Intent book = new Intent(HospitalDetail.this, New_Appointment.class);
                startActivity(book);
                break;
        }
        
    }

    private void openDialog() {
        RatingDialog dialog = new RatingDialog();
        dialog.show(getSupportFragmentManager(),"rating dialog");
    }

    @Override
    public void getRating(int rate) {
        txt_hospital_rating.setText(String.valueOf(rate)+".0");
        setRatingStar();
    }

    private void setRatingStar() {
        int rate = (int) Float.parseFloat(txt_hospital_rating.getText().toString());
        switch (rate){
            case 1:
                img_view_rate_1_hos_detail.setImageDrawable(img);
                img_view_rate_2_hos_detail.setImageDrawable(img2);
                img_view_rate_3_hos_detail.setImageDrawable(img2);
                img_view_rate_4_hos_detail.setImageDrawable(img2);
                img_view_rate_5_hos_detail.setImageDrawable(img2);
                break;
            case 2:
                img_view_rate_1_hos_detail.setImageDrawable(img);
                img_view_rate_2_hos_detail.setImageDrawable(img);
                img_view_rate_3_hos_detail.setImageDrawable(img2);
                img_view_rate_4_hos_detail.setImageDrawable(img2);
                img_view_rate_5_hos_detail.setImageDrawable(img2);
                break;
            case 3:
                img_view_rate_1_hos_detail.setImageDrawable(img);
                img_view_rate_2_hos_detail.setImageDrawable(img);
                img_view_rate_3_hos_detail.setImageDrawable(img);
                img_view_rate_4_hos_detail.setImageDrawable(img2);
                img_view_rate_5_hos_detail.setImageDrawable(img2);
                break;
            case 4:
                img_view_rate_1_hos_detail.setImageDrawable(img);
                img_view_rate_2_hos_detail.setImageDrawable(img);
                img_view_rate_3_hos_detail.setImageDrawable(img);
                img_view_rate_4_hos_detail.setImageDrawable(img);
                img_view_rate_5_hos_detail.setImageDrawable(img2);
                break;
            case 5:
                img_view_rate_1_hos_detail.setImageDrawable(img);
                img_view_rate_2_hos_detail.setImageDrawable(img);
                img_view_rate_3_hos_detail.setImageDrawable(img);
                img_view_rate_4_hos_detail.setImageDrawable(img);
                img_view_rate_5_hos_detail.setImageDrawable(img);
                break;
            default:
                img_view_rate_3_hos_detail.setImageDrawable(img);
        }
    }

}
