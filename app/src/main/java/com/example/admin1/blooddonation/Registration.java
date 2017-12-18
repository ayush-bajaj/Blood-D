package com.example.admin1.blooddonation;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    public class myLocation
    {
        public double lat;
        public double lng;

        public myLocation()
        {
            lat = 0;
            lng = 0;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public void setLocation(double lat, double lng)
        {
            setLat(lat);
            setLng(lng);
        }

    }

    public static class User
    {
        public String email;
        public String pass;
        public String contact;
        public String name;
        public int age;
        public String bloodg;
        public double wt;
        public myLocation loc;

        public User()
        {

        }

        public myLocation getLoc() {
            return loc;
        }

        public void setLoc(myLocation loc) {
            this.loc = loc;
        }

        public User(String email, String pass, String name, int age, String bloodg, double wt, String num,myLocation loc)
        {
            setEmail(email);
            setPass(pass);
            setContact(num);
            setName(name);
            setAge(age);
            setBloodg(bloodg);
            setWt(wt);
            setLoc(loc);
        }

        public String getContact() { return contact; }

        public void setContact(String contact) { this.contact = contact; }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setBloodg(String bloodg) {
            this.bloodg = bloodg;
        }

        public void setWt(double wt) {
            this.wt = wt;
        }

        public String getEmail() {
            return email;
        }

        public String getPass() {
            return pass;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getBloodg() {
            return bloodg;
        }

        public double getWt() {
            return wt;
        }
    };


    User userNew;
    public static User curUser = null;
    myLocation newlObj = new myLocation();
    private GoogleApiClient apiClient;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private LocationRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        apiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        request = new LocationRequest();
        request.setInterval(1 * 1000);
        request.setFastestInterval(1000 * 15);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        newlObj.setLng(location.getLongitude());
        newlObj.setLat(location.getLatitude());
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onResume() {
        if(apiClient.isConnected())
        {
            requestLocationUpdates();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient,this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        apiClient.disconnect();
        super.onStop();
    }


    public void Register(View view)
    {
        final String emailT = (((EditText) findViewById(R.id.email)).getText().toString()).toLowerCase();
        final String passT = ((EditText) findViewById(R.id.pass)).getText().toString();
        final String confirmT = ((EditText) findViewById(R.id.confirm)).getText().toString();
        final String nameT = ((EditText) findViewById(R.id.name)).getText().toString();
        final String ageT = ((EditText) findViewById(R.id.age)).getText().toString();
        final String bloodgT = ((Spinner) findViewById(R.id.bloodg)).getSelectedItem().toString();
        final String wtT = ((EditText) findViewById(R.id.wt)).getText().toString();
        final String contactT = ((EditText) findViewById(R.id.contact)).getText().toString();

        if(!(emailT.isEmpty()==false && emailT.contains("@") && emailT.contains(".")))
        {
            Context context = getApplicationContext();
            String text = "Enter email correctly.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        else if(passT.length()<=4)
        {
            //popup
            Context context = getApplicationContext();
            String text = "Password too short.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        else if(!passT.equals(confirmT))
        {
            Context context = getApplicationContext();
            String text = "Passwords don't match.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        else if(contactT.isEmpty())
        {
            Context context = getApplicationContext();
            String text = "Enter contact number.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }


        else if(nameT.isEmpty())
        {
            Context context = getApplicationContext();
            String text = "Enter name.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
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

            int hash = 0;
            int j= 10;

            for(int i=0;i<emailT.length();i++)
            {
                hash += emailT.charAt(i) * j;
                j *= 10;
            }

            hash %= 10000;
            final String TAG="";

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String s = "users/" + hash;
            final DatabaseReference myRef = database.getReference(s);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userNew = dataSnapshot.getValue(User.class);

                    if(userNew != null)
                    {

                        Context context = getApplicationContext();
                        String text = "This email already exists in the database.";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        return;
                    }

                    else
                    {

                        int age = Integer.parseInt(ageT);
                        double wt = Double.parseDouble(wtT);

                        User newUser = new User();
                        newUser.setEmail(emailT);
                        newUser.setPass(passT);
                        newUser.setName(nameT);
                        newUser.setAge(age);
                        newUser.setBloodg(bloodgT);
                        newUser.setWt(wt);
                        newUser.setContact(contactT);
                        newUser.setLoc(newlObj);

                        myRef.setValue(newUser);

                        Context context = getApplicationContext();
                        String text = "Registration is complete.";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        curUser = newUser;

                        Intent intent = new Intent(Registration.this, HomeScreen2.class);
                        startActivity(intent);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }


            });

        }

    }

}

