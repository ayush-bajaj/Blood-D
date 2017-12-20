package com.example.admin1.blooddonation;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public int hashfn(String email)
    {
        int hash = 0;
        int j= 10;

        for(int i=0;i<email.length();i++)
        {
            hash += email.charAt(i) * j;
            j *= 10;
        }

        hash %= 10000;
        return hash;
    }

    /*public void Forgot(View view)
    {
        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);
    }*/

    public void Login(View view)
    {
        String email = (((EditText) findViewById(R.id.email)).getText().toString()).toLowerCase();
        final String passT = ((EditText) findViewById(R.id.pass)).getText().toString();

        if(!(email.isEmpty()==false && email.contains("@") && email.contains(".")))
        {
            Context context = getApplicationContext();
            String text = "Username/Password is incorrect.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        else if(passT.length()<=4)
        {
            //popup
            Context context = getApplicationContext();
            String text = "Username/Password is incorrect.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        int hash = 0;
        int j= 10;

        for(int i=0;i<email.length();i++)
        {
            hash += email.charAt(i) * j;
            j *= 10;
        }

        hash %= 10000;

        final String TAG = "";

        FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
        final String s = "users/" + hash;
        final DatabaseReference myRef = myDatabase.getReference(s);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user1 = dataSnapshot.getValue(User.class);
                String fetchpass = "";

                if(user1 != null) {
                     fetchpass = user1.getPass();
                }

                if(user1 == null || !fetchpass.equals(passT))
                {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    String text = "Username/Password is incorrect.";
                    Toast toast = Toast.makeText(context,text, duration);
                    toast.show();

                    return;
                }
                else
                {
                    Registration.curUser = user1;
                    Intent intent = new Intent(SignIn.this,HomeScreen2.class);
                    startActivity(intent);
                }

                Log.d(TAG,"Object is:"+user1.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"Failed to read value.", databaseError.toException());
                return;
            }
        });

    }
}
