package com.example.forpet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.String;


public class ShowRecord extends AppCompatActivity {


    // firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDBReference4 = null;


    ImageButton b1;


    private ListView listView;
    private ArrayAdapter<String> adapter;


    List<Object> Array = new ArrayList<Object>();


    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);


        mDBReference4 = database.getReference("record");


        listView = (ListView)findViewById(R.id.listView1);
        adapter =  new ArrayAdapter<String>(this, R.layout.activity_listitem, new ArrayList<String>());
        listView.setAdapter(adapter);


        mDBReference4.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapter.clear();

                for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()){

                    String str = fileSnapshot.getValue().toString();

                    Array.add(str);
                    adapter.add(str);

                }

                adapter.notifyDataSetChanged();

                listView.setSelection(adapter.getCount()-1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w("Database", "Failed to read value.", databaseError.toException());

            }

        });


        b1 = (ImageButton)findViewById(R.id.imageButton3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}