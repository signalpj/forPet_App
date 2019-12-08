package com.example.forpet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ShowEtc extends AppCompatActivity {


    ImageButton b1;


    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.etc);


        b1 = (ImageButton)findViewById(R.id.imageButton4);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();

            }

        });

    }

}