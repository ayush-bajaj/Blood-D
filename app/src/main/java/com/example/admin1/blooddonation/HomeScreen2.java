package com.example.admin1.blooddonation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeScreen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen2);
    }

    public void SignOut(View view)
    {
        Registration.curUser = null;
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void EditDetails(View view)
    {
        Intent intent = new Intent(this, EditAct.class);
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

    public void Donate(View view)
    {
        Intent intent = new Intent(this,DonateBlood.class);
        startActivity(intent);
    }

    public void Request(View view)
    {
        Intent intent = new Intent(this,RequestBlood.class);
        startActivity(intent);
    }
}
