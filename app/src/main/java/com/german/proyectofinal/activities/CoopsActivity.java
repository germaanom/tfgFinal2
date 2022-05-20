package com.german.proyectofinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.german.proyectofinal.Producto;
import com.german.proyectofinal.R;
import com.german.proyectofinal.adapters.ProductoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoopsActivity extends AppCompatActivity {

    //Variables
    FirebaseFirestore fDatabase;
    Map data;
    ArrayList<Producto> list_products;
    RecyclerView recyclerView;
    String nombre;
    String coop;
    String nombreCoop;
    ProductoAdapter adapter;
    Button btnAñadir;
    TextView titulo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coops);

        //Referencio las variables
        btnAñadir = findViewById(R.id.btnAñadirProducto);
        nombreCoop = getIntent().getExtras().getString("nombre");
        titulo = findViewById(R.id.txtNombrecoop);
        fDatabase = FirebaseFirestore.getInstance();
        data = new HashMap<String, String>();
        list_products = new ArrayList<Producto>();
        recyclerView = (RecyclerView) findViewById(R.id.list_productos);
        Context context = this;
        titulo.setText(nombreCoop);
        //Consulto a la coleccion coops y muestro en el recyclerview cada cardview con los datos de la db
        fDatabase.collection("coops").document(nombreCoop).collection("productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data = document.getData();
                                nombre = data.get("Nombre").toString();
                                list_products.add(new Producto("test", nombre));
                                adapter = new ProductoAdapter(list_products, context);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                            }

                        }
                    }
                });
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration did = new DividerItemDecoration(context, llm.getOrientation());
        recyclerView.addItemDecoration(did);



        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = (new Intent(CoopsActivity.this, AddProductActivity.class));
                intent.putExtra("nombre", nombreCoop);
                startActivity(intent);
                }
            });
        }
    }