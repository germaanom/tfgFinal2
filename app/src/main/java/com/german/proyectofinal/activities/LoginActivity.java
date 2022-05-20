package com.german.proyectofinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.german.proyectofinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    //DECLARACION DE VARIABLES
    Button btn_login, btn_register;
    EditText email, password;
    FirebaseAuth mAuth;
    FirebaseFirestore fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INSTANCION LAS VARIABLES
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtNombreCoop);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_crearCoop);

        //EVENTO ONCLICK PARA LOGEARME
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                if(emailUser.isEmpty() && passUser.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(emailUser, passUser);
                }
            }
        });
        //EVENTO ONCLICK PARA IR A LA ACTIVITY DE REGISTER
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
        //FUNCION QUE TE LOGEA EN LA APLICACIÓN
    private void loginUser(String emailUser, String passUser){
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this, NavActivity.class));
                    Toast.makeText(LoginActivity.this, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //COMPRUEBA SI ESTAS LOGEADO
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, NavActivity.class));
        }
    }


}