package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class formuInsert extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formu_insert);
        Button annadirEjercicio=findViewById(R.id.bAgregar);
        TextView texto=findViewById(R.id.eNombre);
        annadirEjercicio.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance("https://fitapp-1ca9d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("ejercicio");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
ejercicio eje= dataSnapshot1.getValue(ejercicio.class);
    Log.e("Datos",""+eje.getNombre());
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
            case R.id.bAgregar:
                nuevoEjercicio();
                return;
        }
    }
    public void nuevoEjercicio(){
        EditText nombre=findViewById(R.id.eNombre);
        EditText repeticiones=findViewById(R.id.eRepeticiones);
        EditText series=findViewById(R.id.eSeries);
        EditText video=findViewById(R.id.eVideo);
        ejercicio e=new ejercicio();
        e.setNombre(nombre.getText().toString());
        e.setRepeticiones(Integer.parseInt(repeticiones.getText().toString()));
        e.setSeries(Integer.parseInt(series.getText().toString()));
        e.setVideo(video.getText().toString());

        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance("https://fitapp-1ca9d-default-rtdb.europe-west1.firebasedatabase.app/").getReference("ejercicio");

        mDatabase.push().setValue(e);


        System.out.println("e introducido");
    }
}