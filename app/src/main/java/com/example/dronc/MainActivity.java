package com.example.dronc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //initialize Buttons
        Button startButton = findViewById(R.id.button_start);
        Button addStatementButton = findViewById(R.id.button_addStatement);
        Button shareButton = findViewById(R.id.button_share);

        //OnClick for Start
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGroup = new Intent(MainActivity.this,GroupActivity.class);
                startActivity(openGroup);
            }
        });

        //TODO: Add Statement OnClick + Activity

        //TODO: Share onClick + Activity
    }
}
