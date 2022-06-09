package com.german.proyectofinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    //VARIABLES
    Button btn_register, btn_volver;
    EditText email;
    EditText password;
    EditText usuario;
    FirebaseFirestore fDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    //INSTANCIO LAS VARIABLES
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtNombreCoop);
        usuario = (EditText) findViewById(R.id.txtUsuario);
        btn_register = findViewById(R.id.btn_login);
        btn_volver = findViewById(R.id.btn_atras);
        //EVENTO ONCLICK PARA VOLVER A INICIAR SESION
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        //EVENTO ONCLICK PARA REGISTRAR
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2 = email.getText().toString().trim();
                String password2 = password.getText().toString().trim();
                String usuario2 = usuario.getText().toString().trim();

                if(email2.isEmpty() || password2.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                addUser(usuario2, email2);
                                finish();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                Toast.makeText(RegisterActivity.this, " Registro correcto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }


    public void addUser(String nombre, String correo){
        fDatabase = FirebaseFirestore.getInstance();
        Map<String, String> user = new HashMap<>();
        user.put("Nombre",nombre);
        user.put("Correo",correo);

        fDatabase.collection("usuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Map perfil = new HashMap();
                        perfil.put("desc", "Añade tu descripcion");
                        fDatabase.collection("profile").document(correo).set(perfil);
                    }
                });
    }

}