package com.example.dhrumil.healthcare.hospital;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import  android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.dhrumil.healthcare.R;

/**
 * Created by Dhrumil on 4/5/2018.
 */

public class RatingDialog extends AppCompatDialogFragment implements View.OnClickListener{
    private ImageView img_view_rate_1_rate_dialog;
    private ImageView img_view_rate_2_rate_dialog;
    private ImageView img_view_rate_3_rate_dialog;
    private ImageView img_view_rate_4_rate_dialog;
    private ImageView img_view_rate_5_rate_dialog;
    private int rating;
    private Drawable img,img2;
    private RatingDialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rating_dialog,null);
        img_view_rate_1_rate_dialog = view.findViewById(R.id.img_view_rate_1_rate_dialog);
        img_view_rate_2_rate_dialog = view.findViewById(R.id.img_view_rate_2_rate_dialog);
        img_view_rate_3_rate_dialog = view.findViewById(R.id.img_view_rate_3_rate_dialog);
        img_view_rate_4_rate_dialog = view.findViewById(R.id.img_view_rate_4_rate_dialog);
        img_view_rate_5_rate_dialog = view.findViewById(R.id.img_view_rate_5_rate_dialog);
        img = ContextCompat.getDrawable(getContext(),R.drawable.ic_star_yellow);
        img2 = ContextCompat.getDrawable(getContext(),R.drawable.ic_star_border_black);

        img_view_rate_1_rate_dialog.setOnClickListener(this);
        img_view_rate_2_rate_dialog.setOnClickListener(this);
        img_view_rate_3_rate_dialog.setOnClickListener(this);
        img_view_rate_4_rate_dialog.setOnClickListener(this);
        img_view_rate_5_rate_dialog.setOnClickListener(this);

        builder.setView(view)
                .setTitle("Rate Hospital")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.getRating(rating);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
     }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogListener = (RatingDialogListener) context;
    }

    public interface RatingDialogListener{
        void getRating(int rate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_view_rate_1_rate_dialog:
                img_view_rate_1_rate_dialog.setImageDrawable(img);
                img_view_rate_2_rate_dialog.setImageDrawable(img2);
                img_view_rate_3_rate_dialog.setImageDrawable(img2);
                img_view_rate_4_rate_dialog.setImageDrawable(img2);
                img_view_rate_5_rate_dialog.setImageDrawable(img2);
                rating = 1;
                break;
            case R.id.img_view_rate_2_rate_dialog:
                rating = 2;
                img_view_rate_1_rate_dialog.setImageDrawable(img);
                img_view_rate_2_rate_dialog.setImageDrawable(img);
                img_view_rate_3_rate_dialog.setImageDrawable(img2);
                img_view_rate_4_rate_dialog.setImageDrawable(img2);
                img_view_rate_5_rate_dialog.setImageDrawable(img2);
                break;
            case R.id.img_view_rate_3_rate_dialog:
                rating = 3;
                img_view_rate_1_rate_dialog.setImageDrawable(img);
                img_view_rate_2_rate_dialog.setImageDrawable(img);
                img_view_rate_3_rate_dialog.setImageDrawable(img);
                img_view_rate_4_rate_dialog.setImageDrawable(img2);
                img_view_rate_5_rate_dialog.setImageDrawable(img2);
                break;
            case R.id.img_view_rate_4_rate_dialog:
                rating = 4;
                img_view_rate_1_rate_dialog.setImageDrawable(img);
                img_view_rate_2_rate_dialog.setImageDrawable(img);
                img_view_rate_3_rate_dialog.setImageDrawable(img);
                img_view_rate_4_rate_dialog.setImageDrawable(img);
                img_view_rate_5_rate_dialog.setImageDrawable(img2);
                break;
            case R.id.img_view_rate_5_rate_dialog:
                rating  = 5;
                img_view_rate_1_rate_dialog.setImageDrawable(img);
                img_view_rate_2_rate_dialog.setImageDrawable(img);
                img_view_rate_3_rate_dialog.setImageDrawable(img);
                img_view_rate_4_rate_dialog.setImageDrawable(img);
                img_view_rate_5_rate_dialog.setImageDrawable(img);
                break;
        }
    }
}
