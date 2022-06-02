package com.german.proyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.german.proyectofinal.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    EditText coop;
    EditText nombre;
    Button añadir;
    String nombreP;
    TextView titulo;
    Button atras;
    String nombreCoop;
    FirebaseFirestore fDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nombre = findViewById(R.id.txtProducto);
        añadir = findViewById(R.id.btn_añadirProducto);
        atras = findViewById(R.id.btn_volver);
        fDatabase = FirebaseFirestore.getInstance();
        nombreCoop = getIntent().getExtras().getString("nombre");
        Log.d("asd", nombreCoop);
        titulo = findViewById(R.id.txtNombreCoop);
        titulo.setText(nombreCoop);
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreP = nombre.getText().toString();
                Log.d("asd", "click");
                if(!nombreP.isEmpty()){
                    nombreCoop = getIntent().getExtras().getString("nombre");
                    Log.d("asd", "click");
                    Map<String, Object> productos = new HashMap<>();
                    productos.put("Nombre", nombreP);
                    productos.put("coop", nombreCoop);
                    writeProd(productos);

                    Intent intent = new Intent(AddProductActivity.this, CoopsActivity.class);
                    intent.putExtra("nombre", nombreCoop);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(AddProductActivity.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProductActivity.this, CoopsActivity.class);
                intent.putExtra("nombre", nombreCoop);
                startActivity(intent);
                finish();
            }
        });


    }

    public void writeProd(Map<String, Object> productos){
       fDatabase.collection("coops").document(titulo.getText().toString()).collection("productos").document(nombreP).set(productos);

    }

}

