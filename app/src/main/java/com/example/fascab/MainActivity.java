package com.example.fascab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.view.View ;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageContainer = findViewById(R.id.imageView) ;
        imageContainer.setClipToOutline(true) ;
    }

    /**
     * Method to take user to main home page for taxi after validating input
     *
     */
    public void goHome(View view){

        EditText username = findViewById(R.id.username_entry);
        EditText password = findViewById(R.id.password_enrty);

        /** Hardcode check if user is correct **/
        if(username.getText().toString().equals("user") && password.getText().toString().equals("password")){

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(getApplicationContext(),"Username or password incorrect",Toast.LENGTH_SHORT).show();
        }

    }
}
