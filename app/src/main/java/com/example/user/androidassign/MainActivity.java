package com.example.user.androidassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
           getSupportActionBar().hide();
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        enter = (Button)findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                enterClick();
            }
        });
    }

    public void enterClick(){
        Intent inte = new Intent(MainActivity.this,ImageViewActivity.class);
        startActivity(inte);
        finish();
    }
}
