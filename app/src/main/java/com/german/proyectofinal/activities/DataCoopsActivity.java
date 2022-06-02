package com.german.proyectofinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.german.proyectofinal.activities.CoopsActivity;
import com.german.proyectofinal.R;
import com.german.proyectofinal.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DataCoopsActivity extends AppCompatActivity {
    String nombreCoop;
    Button calendario;
    Button añadir;
    Button borrar;
    FirebaseFirestore fDatabase;
    TextView kilosTotal;
    String fecha;
    TextView kiloshoy;
    TextView fecha2;
    String txtkiloshoy;
    double kiloshoy2;
    String nombreCoop2;
    TextView titulo;
    Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coop);
        //REFERENCIO LAS VARIABLES
        fDatabase = FirebaseFirestore.getInstance();
        fecha2 = findViewById(R.id.txtHoy);
        nombreCoop2 = getIntent().getExtras().getString("coop");
        nombreCoop = getIntent().getExtras().getString("producto");
        añadir = findViewById(R.id.btnAñadirRegistro);
        kiloshoy = findViewById(R.id.txtKilosHoy);
        volver = findViewById(R.id.btn_atras);
        calendario = findViewById(R.id.btnCaledario);
        kilosTotal = findViewById(R.id.txtTotal);
        titulo = findViewById(R.id.txtNombreC);
        borrar = findViewById(R.id.btn_borrarP);
        titulo.setText(nombreCoop);


        //SE ABRE EL CALENDARIO Y CON ESA FECHA SE RECOGEN LOS DATOS Y SE MUESTRAN
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(DataCoopsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        fecha = i2 + "/" + i1 + "/" + i;
                        calendario.setText(fecha);
                        fecha2.setText(fecha);
                        fDatabase = FirebaseFirestore.getInstance();
                        fDatabase.collection("dataCoops").whereEqualTo("fecha", fecha).whereEqualTo("coop", nombreCoop2).whereEqualTo("producto", nombreCoop).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                kiloshoy.setText("Sin datos");
                                kiloshoy2=0;
                                for(QueryDocumentSnapshot document : task.getResult()){

                                    Map data = new HashMap();
                                    data = document.getData();
                                    Log.d("datos", data.toString());
                                    kiloshoy2 += Double.parseDouble(data.get("kilos").toString());
                                    Log.d("kilos", " "+kiloshoy2);
                                    txtkiloshoy = kiloshoy2 + " kilos";
                                    kiloshoy.setText(txtkiloshoy);
                                    Log.d("asd", txtkiloshoy);
                                }
                            }
                        });


                    }
                }, year, mes, dia);
                dpd.show();
            }
        });

        //TE DEVUELVE A LA ACTIVITY DE COOPS
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(DataCoopsActivity.this, CoopsActivity.class );
                intent.putExtra("nombre", nombreCoop2);
                startActivity(intent);
                finish();
            }
        });

        //TE ENVIA A LA ACTIVITY PARA AÑADIR DATOS
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataCoopsActivity.this, AddDataActivity.class);
                intent.putExtra("nombre", nombreCoop);
                intent.putExtra("coop", nombreCoop2);
                startActivity(intent);
                finish();
            }
        });


        //ELIMINA EL PRODUCTO Y TODOO LO RELACIONADO CON EL
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fDatabase.collection("coops").document(nombreCoop2).collection("productos").document(nombreCoop).delete();
                fDatabase.collection("dataCoops").whereEqualTo("producto", nombreCoop).whereEqualTo("coop", nombreCoop2).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                            fDatabase.collection("dataCoops").document(document.getId()).delete();
                        }
                    }
                });
                Intent intent  = new Intent(DataCoopsActivity.this, CoopsActivity.class );
                intent.putExtra("nombre", nombreCoop2);
                startActivity(intent);
                finish();
                Toast.makeText(DataCoopsActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
            }
        });


        //MUESTRA LOS KILOS TOTALES
        fDatabase.collection("dataCoops").whereEqualTo("coop", nombreCoop2).whereEqualTo("producto", nombreCoop).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                double kilostotal = 0;
                for(QueryDocumentSnapshot document : task.getResult()){
                    Map data = new HashMap();
                    data = document.getData();
                    kilostotal += Double.parseDouble(data.get("kilos").toString());
                    kilosTotal.setText(""+kilostotal+ " kilos");
                }

            }
        });

    }
}