package com.example.user.androidassign;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    int[] images = new int[9];              //Integer array to hold the ids for the pictures in drawable
    ImageView displayImg;
    ImageButton left;
    ImageButton right;
    Button slideshow;
    int count;                          //Counter to iterate through images in order
    String filePath = "/DCIM";           //Path of images if pre-loaded onto SD Card
    int savedCount;

    @Override               //OnCreate activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = 0;
        setContentView(R.layout.activity_image_view);
        imageLoader();                       //Initiate load sequence
        displayImg = (ImageView)findViewById(R.id.displayImg);
        left = (ImageButton)findViewById(R.id.imageButton);
        right = (ImageButton)findViewById(R.id.imageButton2);
        slideshow = (Button)findViewById(R.id.button2);
        //Display first image
        if(allOnSD){
            loadSpecificImage(0);
        }else {
            displayImg.setImageResource(images[0]);
        }
       // displayImg.setImageResource(images[0]);
        //On Click Listener for previous image button
        left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showPreviousImage();
            }
        });
        //On Click Listener for next image button
        right.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                showNextImage();
            }
        });
        //On Click Listener for slideshow button
        slideshow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                slideshowImages();
            }
        });
    }

//on paused method
    @Override
    public void onPause(){
        super.onPause();
        savedCount = count;
    }
//on resume method to resume from current image
    @Override
    public void onResume() {
        super.onResume();
        if(allOnSD){
            loadSpecificImage(savedCount);               //If image is on sd card, use that
        }else {
            displayImg.setImageResource(images[savedCount]);         //Otherwise use default image from drawable
        }
    }

    //Load saved instance state with counter of current image
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("count", count);
        savedInstanceState.putString("MyString", "Welcome back");
    }

    //Array of image names expected to find in SD card
    String[] imageNames = {"upper.jpg","ebe.jpg", "commerce.jpg", "science.jpg", "humanities.jpg", "change.jpg", "tugwell.jpg", "lecture.jpg", "graduate.jpg"};
    boolean allOnSD = true;

    //Finds if images exist in preset directory on SD card
    protected void imageLoader(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            System.out.println("SD card found, checking for preloaded images on SD");
            String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();

            String targetPath = externalStoragePath + filePath;
            System.out.println("PATH: "+targetPath);
            allOnSD = loadImagesFromSD(targetPath);
            if(!allOnSD){
                loadImagesFromDrawable();

            }
        }else {
            Toast.makeText(this, "No SD card found! Using images in drawable", Toast.LENGTH_LONG).show();
            System.out.println("Images not found on SD card, using images in drawable");
            allOnSD = false;
            loadImagesFromDrawable();
        }
    }
//Called if images are not on SD card
    protected void loadImagesFromDrawable(){
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
//Check that all images exist on the SD card, return false if they do not
    protected boolean loadImagesFromSD(String path){
        File imageFile;
        for (int i = 0; i < 8; i++){
            imageFile = new File(path);
            if(!imageFile.exists()){
                Toast.makeText(this,"Cannot find "+imageNames[i] +" image on SD card! Using default images from drawable",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        Toast.makeText(this,"Using SD card images",Toast.LENGTH_LONG).show();
        return true;
    }

//Load current image from SD card
    protected void loadSpecificImage(int id){
        String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = externalStoragePath + filePath;
        File imageFile;
        imageFile = new File(targetPath+"/"+imageNames[id]);
        //Already checked that this images is on SD card
        Bitmap d = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        displayImg.setImageBitmap(d);
    }

    protected void showNextImage(){
        if(count<8){
            count++;
        }else{
            count = 0;
        }
        if(allOnSD){
            loadSpecificImage(count);               //If image is on sd card, use that
        }else {
            displayImg.setImageResource(images[count]);         //Otherwise use default image from drawable
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
//enter slideshow
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
//Set slideshow to autochange every 2 seconds
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
//Once slideshow is exited, reset buttons
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
