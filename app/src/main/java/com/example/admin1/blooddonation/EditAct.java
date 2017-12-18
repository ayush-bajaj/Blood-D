package com.example.admin1.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    public void changepass(View view)
    {
        String passTNew = ((EditText)findViewById(R.id.passNew)).getText().toString();
        String confirmTNew = ((EditText)findViewById(R.id.confirmNew)).getText().toString();
        String oldPass = ((EditText)findViewById(R.id.passOld)).getText().toString();

        if(!oldPass.equals(Registration.curUser.pass))
        {
            Toast.makeText(getApplicationContext(),"The password entered is incorrect.",Toast.LENGTH_LONG).show();
        }

        if(passTNew.length()<=4)
        {
            Toast.makeText(getApplicationContext(),"Password too short.",Toast.LENGTH_LONG).show();
        }

        else if(!passTNew.equals(confirmTNew))
        {
            Toast.makeText(getApplicationContext(),"Passwords don't match.",Toast.LENGTH_LONG).show();
        }

        else
        {
            String emailTNew = Registration.curUser.email;

            int hash = 0;
            int j= 10;

            for(int i=0;i<emailTNew.length();i++)
            {
                hash += emailTNew.charAt(i) * j;
                j *= 10;
            }

            hash %= 10000;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String s = "users/"+hash;
            DatabaseReference myRef = database.getReference(s);

            Registration.curUser.pass = passTNew;

            myRef.setValue(Registration.curUser);

            Toast.makeText(getApplicationContext(),"Password changed successfully.",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this,HomeScreen2.class);
            startActivity(intent);


        }
    }

    public void editDetails(View view)
    {
        String contactT = ((EditText)findViewById(R.id.contactNew)).getText().toString();
        String ageT = ((EditText)findViewById(R.id.ageNew)).getText().toString();
        String wtT = ((EditText)findViewById(R.id.wtNew)).getText().toString();

        if(contactT.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Enter your mobile number.",Toast.LENGTH_LONG).show();
        }

        else if(ageT.isEmpty())
        {
            Context context = getApplicationContext();
            String text = "Enter age.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        else if(wtT.isEmpty())
        {
            Context context = getApplicationContext();
            String text = "Enter your weight.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        else
        {
            String emailT = Registration.curUser.email;

            int hash = 0;
            int j= 10;

            for(int i=0;i<emailT.length();i++)
            {
                hash += emailT.charAt(i) * j;
                j *= 10;
            }

            hash %= 10000;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String s = "users/"+hash;
            DatabaseReference myRef = database.getReference(s);

            Registration.curUser.contact = contactT;
            Registration.curUser.age = Integer.parseInt(ageT);
            Registration.curUser.wt = Double.parseDouble(wtT);

            myRef.setValue(Registration.curUser);
            Toast.makeText(getApplicationContext(),"Details changed Successfully.",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,HomeScreen2.class);
            startActivity(intent);

        }
    }
}
