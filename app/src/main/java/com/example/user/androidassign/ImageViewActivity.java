package com.example.user.androidassign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ImageViewActivity extends FragmentActivity {
    int[] images = new int[9];
    ImageView displayImg;
    ImageButton left;
    ImageButton right;
    Button slideshow;
    int count = 0;
    String filePath = "/DCIM/AssignmentApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_view);
        imageLoader();
        displayImg = (ImageView)findViewById(R.id.displayImg);
        left = (ImageButton)findViewById(R.id.imageButton);
        right = (ImageButton)findViewById(R.id.imageButton2);
        slideshow = (Button)findViewById(R.id.button2);

        displayImg.setImageResource(images[0]);

        left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showPreviousImage();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                showNextImage();
            }
        });

        slideshow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                slideshowImages();
            }
        });
    }

//on paused method
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("count", count);
        savedInstanceState.putString("MyString", "Welcome back");
    }

    String[] imageNames = {"upper.png","ebe.jpg", "commerce.jpg", "science.jpg", "humanities.jpg", "change.jpg", "tugwell.jpg", "lecture.jpg", "graduate.jpg"};
    boolean allOnSD = true;

    protected void imageLoader(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            System.out.println("SD card found, checking for preloaded images on SD");
            String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String targetPath = externalStoragePath + filePath;
            loadImagesFromSD(targetPath);
        }else {
            Toast.makeText(this, "No SD card found! Using images in drawable", Toast.LENGTH_LONG).show();
            System.out.println("Images not found on SD card, using images in drawable");
            allOnSD = false;
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
    }

    protected void loadImagesFromSD(String path){
        File imageFile;
        for (int i = 0; i < 9; i++){
            imageFile = new File(path+"/"+imageNames[i]);
            if(!imageFile.exists()){
                Toast.makeText(this,"Cannot find image! Using default images from drawable",Toast.LENGTH_LONG).show();
                allOnSD = false;
                break;
            }
        }
        Toast.makeText(this,"Using SD card images",Toast.LENGTH_LONG).show();
    }

    //TEST!!!
    protected void loadSpecificImage(int id){
        String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = externalStoragePath + filePath;
        File imageFile;
        imageFile = new File(targetPath+imageNames[id]);
        //Already checked that image exists
        Bitmap d = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        Drawable.createFromPath(imageFile.getAbsolutePath());
        displayImg.setImageBitmap(d);
    }

    protected void showNextImage(){
        if(count<8){
            count++;
        }else{
            count = 0;
        }
        if(allOnSD){
            loadSpecificImage(count);
        }else {
            displayImg.setImageResource(images[count]);
        }
    }

    protected void showPreviousImage(){
        if(count>0){
            count--;
        }else{
            count = 8;
        }
        if(allOnSD){
            loadSpecificImage(count);
        }else {
            displayImg.setImageResource(images[count]);
        }
    }

    private Handler handler = new Handler();

    protected void slideshowImages(){
        runnable.run();

        slideshow.setText("EXIT SLIDESHOW");
        left.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);

        slideshow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                exitSlideshow();
            }
        });
    }

    private Runnable runnable = new Runnable(){
        public void run(){
            if(count<8){
                count++;
            }else {
                count = 0;
            }
            handler.postDelayed(runnable, 2000);
            if(allOnSD){
                loadSpecificImage(count);
            }else {
                displayImg.setImageResource(images[count]);
            }
        }
    };

    protected void exitSlideshow(){
        slideshow.setText("ENTER SLIDESHOW");
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        slideshow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                slideshowImages();
            }
        });
    }
}
