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
    private ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
    private ArrayList<String> referenciaEjercicio = new ArrayList<String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button annadirEjercicio = findViewById(R.id.bAnnadir);
        listView = (ListView) findViewById(R.id.lEjercicios);
        annadirEjercicio.setOnClickListener(this);

        //Instancia de las referencias a la base de datos
        mDatabase = FirebaseDatabase.getInstance("https://fitapp-1ca9d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("ejercicio");
        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*Recorre todo el dataSnapshot recogiendo todos los objetos de la base de datos y mos
                trandolos en un list view presonalizado*/
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Ejercicio ejercicio = dataSnapshot1.getValue(Ejercicio.class);
                    ejercicios.add(ejercicio);
                    referenciaEjercicio.add(ejercicio.getNombre());

                }

            }

            //Caso en el que no pueda obtener los datos de la base de datos.
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Datos", "Failed to read value.", error.toException());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAnnadir:
                //Formulario de insercion de nuevo ejercicio.
                Intent intent = new Intent(this, formuInsert.class);

                startActivity(intent);
                finish();
                return;
        }
    }


}