package com.example.admin1.blooddonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    public void onBackPressed() {

    }

    public void Register(View view)
    {
        Intent intent;
        intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void SignIn(View view)
    {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void AboutUs(View view)
    {
        Intent intent = new Intent(this,AboutUs.class);
        startActivity(intent);
    }

    public void Requirements(View view)
    {
        Intent intent = new Intent(this,Requirements.class);
        startActivity(intent);
    }
}