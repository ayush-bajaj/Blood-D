package com.example.admin1.blooddonation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class RequestBlood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        ((EditText) findViewById(R.id.bg)).setText(Registration.curUser.bloodg);
        final String s = "donated/" + Registration.curUser.bloodg;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(s);
        final String TAG = "";

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            Map mapNew = new HashMap();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                if (map == null) {
                    ((TextView) findViewById(R.id.colorc)).setText("Your blood group is not available");
                    ((TextView) findViewById(R.id.colorc)).setTextColor(Color.RED);
                    return;
                }
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> me = (Map.Entry<String, Object>) it.next();
                    mapNew.put(me.getKey(), me.getValue());
                }
                it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> me = (Map.Entry<String, Object>) it.next();
                    Map<String, String> ud = (Map<String, String>) me.getValue();
                    if (ud.get("email").equals(Registration.curUser.email)) {
                        mapNew.remove(me.getKey());
                    }
                }


                if (mapNew.size() == 0) {
                    ((TextView) findViewById(R.id.colorc)).setText("Your blood group is not available");
                    ((TextView) findViewById(R.id.colorc)).setTextColor(Color.RED);
                } else {
                    ((Button) findViewById(R.id.reqbut)).setEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());

            }
        });

    }

    public int hashfn(String emailT) {
        int hash = 0;
        int j = 10;

        for (int i = 0; i < emailT.length(); i++) {
            hash += emailT.charAt(i) * j;
            j *= 10;
        }

        hash %= 10000;

        return hash;
    }

    public void Request(View view) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String s = "donated/" + Registration.curUser.bloodg;
        final DatabaseReference myRef = database.getReference(s);
        final String TAG = "";

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (Map<String, Object>) dataSnapshot.getValue();
                Iterator it = td.entrySet().iterator();
                Map.Entry<String, Object> me = (Map.Entry<String, Object>) it.next();
                while (Integer.parseInt(me.getKey()) == hashfn(Registration.curUser.email)) {
                    me = (Map.Entry<String, Object>) it.next();
                }
                final Map<String, String> td2 = (Map<String, String>) me.getValue();
                String s = "Thankyou for using our services, The matched user details are shown below. Press Proceed to Confirm or Cancel to go back to home screen: \n";

                s = s + "Name: " + td2.get("name") + "\n";
                s = s + "Email: " + td2.get("email") + "\n";
                s = s + "Contact Number: " + td2.get("contact");

                String emailT = td2.get("email");

                int hash = 0;
                int j = 10;

                for (int i = 0; i < emailT.length(); i++) {
                    hash += emailT.charAt(i) * j;
                    j *= 10;
                }

                hash %= 10000;
                final int hash2 = hash;

                AlertDialog.Builder builder = new AlertDialog.Builder(RequestBlood.this);
                builder.setCancelable(false);
                builder.setMessage(s);
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder builderNew = new AlertDialog.Builder(RequestBlood.this);
                        builderNew.setCancelable(false);
                        builderNew.setMessage("Call or message this user.");
                        builderNew.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                String newS = "tel:" + td2.get("contact");
                                intent.setData(Uri.parse(newS));
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                startActivity(intent);
                            }
                        });
                        builderNew.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                sendIntent.putExtra("sms_body", "Hey! I got your information through the Indian Blood Donation App! I heard you were willing to donate?");
                                sendIntent.putExtra("address"  , td2.get("contact"));
                                sendIntent.setType("vnd.android-dir/mms-sms");
                                startActivity(sendIntent);

                            }
                        });
                        AlertDialog dialogNew = builderNew.create();
                        dialogNew.show();

                        String q = "donated/" + Registration.curUser.bloodg + "/" + hash2;
                        DatabaseReference anotherRef = database.getReference(q);
                        anotherRef.removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(RequestBlood.this,HomeScreen2.class);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
