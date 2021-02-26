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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private EditText email;
    private EditText pass;
    private FirebaseAuth mAuth;
    DatabaseReference mRootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.bLogin);
        login.setOnClickListener(this);
        Button register = findViewById(R.id.bRegister);
        register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }
    //Crea un nuevo usuario mediante el sistema de autentificacion de firebase, necesita un correo y una contraseña
   public void createAcount(){
       email = findViewById(R.id.eEmail);
       pass = findViewById(R.id.ePass);

       //Listener de firebase
       mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           // Sign in success, update UI with the signed-in user's information
                           Log.d(TAG, "createUserWithEmail:success");
                           FirebaseUser user = mAuth.getCurrentUser();
                           Toast.makeText(MainActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                           //Iniciamos sesion una vez que hemos creado la cuenta.
                           login();

                       } else {
                           // If sign in fails, display a message to the user.
                           Log.w(TAG, "createUserWithEmail:failure", task.getException());
                           Toast.makeText(MainActivity.this, "No se ha podido crear uauario", Toast.LENGTH_SHORT).show();

                       }

                       // ...
                   }
               });

   }
   //Comprueba si existe un usuario y en caso de que este registrado en la base de datos, permite el acceso a la aplicacion.
   public void login(){
       email = findViewById(R.id.eEmail);
       pass = findViewById(R.id.ePass);
       mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           // Sign in success, update UI with the signed-in user's information
                           Log.d(TAG, "signInWithEmail:success");
                           FirebaseUser user = mAuth.getCurrentUser();
                           lanzarIntent();
                       } else {
                           // If sign in fails, display a message to the user.
                           Toast.makeText(MainActivity.this, "No se ha encontrado al usuario, comprueba el correo y la contraseña"
                                   , Toast.LENGTH_SHORT).show();


                       }

                       // ...
                   }
               });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:

login();
                break;
            case R.id.bRegister:

                createAcount();


                break;
        }
    }

    public void lanzarIntent() {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("pass", pass.getText().toString());
        startActivity(intent);
        finish();
    }



}