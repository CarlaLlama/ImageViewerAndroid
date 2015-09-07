package com.example.user.androidassign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {
    int[] images = new int[9];
    ImageView displayImg;
    ImageButton left;
    ImageButton right;
    Button slideshow;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        displayImg = (ImageView)findViewById(R.id.displayImg);
        left = (ImageButton)findViewById(R.id.imageButton);
        right = (ImageButton)findViewById(R.id.imageButton2);
        slideshow = (Button)findViewById(R.id.button2);
        imageLoader();
        displayImg.setImageResource(images[0]);

        left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showPreviousImage();
                displayImg.setImageResource(images[count]);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                showNextImage();
                displayImg.setImageResource(images[count]);
            }
        });
    }

    protected void imageLoader(){
        images[0] = R.drawable.upper;
        images[1] = R.drawable.ebe;
        images[2] = R.drawable.commerce;
        images[3] = R.drawable.science;
        images[4] = R.drawable.humanities;
        images[5] = R.drawable.change;
        images[6] = R.drawable.tugwell;
        images[7] = R.drawable.lecture;
        images[8] = R.drawable.graduate;
    }

    protected void showNextImage(){
        if(count<8){
            count++;
        }else{
            count = 0;
        }


    }

    protected void showPreviousImage(){
        if(count>0){
            count--;
        }else{
            count = 8;
        }
    }
}
