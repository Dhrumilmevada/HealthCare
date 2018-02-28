package com.example.dhrumil.healthcare.homePage;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dhrumil.healthcare.R;

/**
 * Created by Dhrumil on 2/25/2018.
 */

public class SliderViewPagerAdapter extends PagerAdapter {

    private Context context;
    private int image[];
    private LayoutInflater layoutInflater;
    public SliderViewPagerAdapter(Context context,int [] image) {
        this.context = context;
        this.image = image;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
