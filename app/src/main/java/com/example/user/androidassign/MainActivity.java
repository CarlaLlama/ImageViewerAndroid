package com.example.user.androidassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button enter;
    @Override
    //onCreate activity for when the app starts
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
           getSupportActionBar().setTitle("Welcome Image");
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);             //Fill window with current class

        enter = (Button)findViewById(R.id.enter);
        //On click listener for Enter button
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                enterClick();
            }
        });
    }

    public void enterClick(){
        //Create new intent to initiate next window
        Intent intent = new Intent(MainActivity.this,ImageViewActivity.class);
        startActivity(intent);
        finish();
    }
}
