package com.example.erickcruz.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button administratorBtn, employerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initUI();
        this.setControlEvents();
    }

    private void initUI() {
        administratorBtn = (Button)findViewById(R.id.employerBtn);
        employerBtn = (Button)findViewById(R.id.userBtn);
    }

    private  void setControlEvents() {
        administratorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalApplication.typeStr = "Administrator";
                gotoLoginScreen();
            }
        });

        employerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalApplication.typeStr = "Employer";
                gotoLoginScreen();
            }
        });
    }

    private void gotoLoginScreen(){
        Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(mainIntent);
    }
}
