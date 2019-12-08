package com.example.forpet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import java.lang.String;

public class MainActivity extends AppCompatActivity {


    // firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDBReference = null;
    DatabaseReference mDBReference2 = null;
    DatabaseReference mDBReference3 = null;
    DatabaseReference mDBReference5 = null;


    // notification
    NotificationManager nm;
    Notification.Builder builder; // shake
    Notification.Builder builder2; // water
    Notification.Builder builder3; // detect


    Intent push;
    PendingIntent fullScreenPendingIntent;


    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm");
    String formatDate = sdfNow.format(date);


    ImageButton goRecord;
    ImageButton goEtc;


    static boolean calledAlready = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        goRecord = (ImageButton) findViewById(R.id.imageButton);

        goRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent1 = new Intent(getApplicationContext(), ShowRecord.class);
                startActivity(intent1);

            }
        });


        goEtc = (ImageButton) findViewById(R.id.imageButton2);

        goEtc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent2 = new Intent(getApplicationContext(), ShowEtc.class);
                startActivity(intent2);

            }
        });


        if(!calledAlready){

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;

        }


        mDBReference = database.getReference("shake");
        mDBReference2 = database.getReference("water");
        mDBReference3 = database.getReference(); //////
        mDBReference5 = database.getReference("detect");


        builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("Test1");
        builder.setWhen(System.currentTimeMillis());

        builder.setContentTitle("진동 감지");
        builder.setContentText("기기에 진동이 감지되었습니다.");
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);


        builder2 = new Notification.Builder(this);
        builder2.setSmallIcon(R.mipmap.ic_launcher);
        builder2.setTicker("Test11");
        builder2.setWhen(System.currentTimeMillis());

        builder2.setContentTitle("물 감지");
        builder2.setContentText("기기 주변에 물이 감지되었습니다.");
        builder2.setAutoCancel(true);
        builder2.setPriority(Notification.PRIORITY_MAX);


        builder3 = new Notification.Builder(this);
        builder3.setSmallIcon(R.mipmap.ic_launcher);
        builder3.setTicker("Test111");
        builder3.setWhen(System.currentTimeMillis());

        builder3.setContentTitle("반려견 접근");
        builder3.setContentText("기기 주변에 반려견이 머물고 있습니다.");
        builder3.setAutoCancel(true);
        builder3.setPriority(Notification.PRIORITY_MAX);


        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(this, MainActivity.class);


        fullScreenPendingIntent = PendingIntent.getActivity(this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);


        builder.setFullScreenIntent(fullScreenPendingIntent, true);
        builder2.setFullScreenIntent(fullScreenPendingIntent, true);
        builder3.setFullScreenIntent(fullScreenPendingIntent, true);


        mDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue(Integer.class) == 1){

                    mDBReference.setValue(0);
                    nm.notify(123456, builder.build());

                    mDBReference3.child("record").push().setValue(formatDate+"                          물어뜯음"); ///

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());

            }

        });

        mDBReference2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue(Integer.class) == 1){

                    mDBReference2.setValue(0);
                    nm.notify(123456, builder2.build());

                    mDBReference3.child("record").push().setValue(formatDate+"                            배뇨"); ///

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());

            }
        });


        mDBReference5.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue(Integer.class) == 1){

                    mDBReference5.setValue(0);
                    nm.notify(123456, builder3.build());

                    mDBReference3.child("record").push().setValue(formatDate+"                            접근"); ///

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());

            }
        });

    }

}