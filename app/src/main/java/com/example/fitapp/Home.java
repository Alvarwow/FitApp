package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private ArrayList<Ejercicio> ejercicios=new ArrayList<Ejercicio>();
    private ArrayList<String>referenciaEjercicio=new ArrayList<String>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button annadirEjercicio=findViewById(R.id.bAnnadir);
        listView = (ListView) findViewById(R.id.lEjercicios);
        annadirEjercicio.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance("https://fitapp-1ca9d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("ejercicio");
        mDatabase.addValueEventListener(new ValueEventListener() {
int i=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Ejercicio ejercicio= dataSnapshot1.getValue(Ejercicio.class);
                    ejercicios.add(ejercicio);
                    referenciaEjercicio.add(ejercicio.getNombre());
                    System.out.println(ejercicios);
                    i++;
                }

    }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Datos", "Failed to read value.", error.toException());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bAnnadir:
                Intent intent = new Intent(this, formuInsert.class);

                startActivity(intent);
                finish();
                return;
        }
    }



}