package com.example.dhrumil.healthcare.homePage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dhrumil.healthcare.R;
import com.example.dhrumil.healthcare.dataBase.Database;
import com.example.dhrumil.healthcare.diet.DietPlan;
import com.example.dhrumil.healthcare.fitness.FitnessRoutine;
import com.example.dhrumil.healthcare.hospital.HospitalList;
import com.example.dhrumil.healthcare.laboratory.LaboratoryList;
import com.example.dhrumil.healthcare.login.LoginActivity;
import com.example.dhrumil.healthcare.yoga.YogaRoutine;

/**
 * Created by Dhrumil on 2/25/2018.
 */

public class SliderViewPagerAdapter extends PagerAdapter {
    private Database db;
    private Context context;
    private int image[];
    private LayoutInflater layoutInflater;

    public SliderViewPagerAdapter(Context context,int [] image) {
        this.context = context;
        this.image = image;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new Database(context);
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item = layoutInflater.inflate(R.layout.image_slide_view,container,false);
        ImageView imageview = (ImageView) item.findViewById(R.id.imageview_img_slide);
        imageview.setImageResource(image[position]);
        container.addView(item,0);
        onClickViewPager(imageview,position,context);
        return item;
    }

    public void onClickViewPager(ImageView imageview,final int pos,final Context con)
    {
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                switch(pos)
                {
                    case 0:
                        i = new Intent(con, HospitalList.class);
                        i.putExtra(LoginActivity.USER_TYPE,db.getUsertype());
                        con.startActivity(i);
                        break;
                    case 1:
                        i = new Intent(con, LaboratoryList.class);
                        i.putExtra(LoginActivity.USER_TYPE,db.getUsertype());
                        con.startActivity(i);
                        break;
                    case 2:
                        i = new Intent(con, DietPlan.class);
                        i.putExtra(LoginActivity.USER_TYPE,db.getUsertype());
                        con.startActivity(i);
                        break;
                    case 3:
                        i = new Intent(con, FitnessRoutine.class);
                        i.putExtra(LoginActivity.USER_TYPE,db.getUsertype());
                        con.startActivity(i);
                        break;
                    case 4:
                        i = new Intent(con, YogaRoutine.class);
                        i.putExtra(LoginActivity.USER_TYPE,db.getUsertype());
                        con.startActivity(i);
                        break;
                }
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
