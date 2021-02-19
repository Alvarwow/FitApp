package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private EditText email;
    private EditText pass;
   private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.bLogin);
        login.setOnClickListener(this);
        Button register = findViewById(R.id.bRegister);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:

                comprobarUsuario();


                break;
            case R.id.bRegister:

                registrarse();


                break;
        }
    }

    public void lanzarIntent() {
        Intent intent = new Intent(this,Home.class);
        intent.putExtra("email",email.getText().toString());
        intent.putExtra("pass",pass.getText().toString());
        startActivity(intent);
        finish();
    }

    public void comprobarUsuario(){

        email = findViewById(R.id.eEmail);
        pass = findViewById(R.id.ePass);
        db.collection("usuarios")
                .whereEqualTo("email", email.getText().toString()).whereEqualTo("pass",pass.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean usuarioOk=false;
                        boolean passOk=false;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.getId().equals("email")&&document.getData().equals(email.getText().toString())){
                                    usuarioOk=true;
                                }
                                if(document.getId().equals("pass")&&document.getData().equals(pass.getText().toString())){
                                    passOk=true;
                                }
                            }

if(usuarioOk&&passOk){
   lanzarIntent();
}else{
    Log.d(TAG, "No es correcto");
}
                        }
                    }
                });

    }
    public void registrarse() {
        email = findViewById(R.id.eEmail);
        pass = findViewById(R.id.ePass);
        if (!email.getText().toString().equals("") && !pass.getText().toString().equals("")) {



            // Create a new user with a first and last name
            Map<String, Object> usuario = new HashMap<>();

            usuario.put("email", email.getText().toString());
            usuario.put("pass", pass.getText().toString());


// Add a new document with a generated ID
            db.collection("usuarios")
                    .add(usuario)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Usuario insertado correctamente");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error");
                        }
                    });
        } else {
            Toast.makeText(this, "Debes introducir email y contraseña", Toast.LENGTH_SHORT).show();
        }
    }
}