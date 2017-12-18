package com.example.admin1.blooddonation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class DonateBlood extends AppCompatActivity {

    private static final int uniqueID = 123213;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        ((EditText) findViewById(R.id.age)).setText(Integer.toString(Registration.curUser.age));
        ((EditText) findViewById(R.id.wt)).setText(Double.toString(Registration.curUser.wt));

        if(Registration.curUser.age>=16 && Registration.curUser.age<=70 && Registration.curUser.wt>45)
        {

        }

        else
        {
            ((TextView)findViewById(R.id.ttc)).setText("You are not eligible to donate");
            ((TextView)findViewById(R.id.ttc)).setTextColor(Color.RED);
            ((CheckBox)findViewById(R.id.check)).setEnabled(false);
        }
    }

    public void Check(View view)
    {
        if(((Button) findViewById(R.id.Donbutt)).isEnabled()==false)
        {
            ((Button) findViewById(R.id.Donbutt)).setEnabled(true);
        }
        else
        {
            ((Button) findViewById(R.id.Donbutt)).setEnabled(false);
        }
    }

    public void Donate(View view)
    {
        Context context = getApplicationContext();
        String text = "Thankyou for your contribution.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();

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
        String s = "donated/"+Registration.curUser.bloodg+"/"+hash;
        DatabaseReference myRef = database.getReference(s);
        myRef.setValue(Registration.curUser);

        Intent intent = new Intent(this, HomeScreen2.class);
        startActivity(intent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setTicker("Thankyou for participating!");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("Indian Blood Donation");
        builder.setContentText("Thankyou for contributing towards the betterment of society. You will be called or informed when someone needs your blood.");

        Intent changeSc = new Intent(this,HomeScreen2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, changeSc, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, builder.build());

    }
}


