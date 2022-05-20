package com.german.proyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    String nombreCoop;
    FirebaseFirestore fDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nombre = findViewById(R.id.txtProducto);
        añadir = findViewById(R.id.btn_añadirProducto);
        fDatabase = FirebaseFirestore.getInstance();
        nombreCoop = getIntent().getExtras().getString("nombre");
        titulo = findViewById(R.id.txtNombreCoop);
        titulo.setText(nombreCoop);
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreP = nombre.getText().toString();
                Log.d("asd", "click");
                if(!nombreP.isEmpty()){
                    Log.d("asd", "click");
                    Map<String, Object> productos = new HashMap<>();
                    productos.put("Nombre", nombreP);
                    writeProd(productos);

                }
            }
        });



    }

    public void writeProd(Map<String, Object> productos){
       fDatabase.collection("coops").document(titulo.getText().toString()).collection("productos").add(productos);

    }

}

