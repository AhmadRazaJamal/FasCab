package com.example.fascab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.content.Intent;
import android.view.View ;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageContainer = findViewById(R.id.imageView) ;
        imageContainer.setClipToOutline(true) ;
    }

    public void goHome(View view){

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }
}
