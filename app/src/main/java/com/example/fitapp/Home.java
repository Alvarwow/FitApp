package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button annadirEjercicio=findViewById(R.id.bAnnadir);
        annadirEjercicio.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bAnnadir:
                nuevoEjercicio();
                return;
        }
    }


    public void nuevoEjercicio(){
        ejercicio e=new ejercicio();
        e.setNombre("Flexiones");
        e.setRepeticiones(25);
        e.setSeries(4);
        e.setVideo("hola");

        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance("https://fitapp-1ca9d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("ejercicio");

        mDatabase.push().setValue(e);


        System.out.println("e introducido");
    }
}