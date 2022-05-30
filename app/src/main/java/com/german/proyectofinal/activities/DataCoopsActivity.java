package com.german.proyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.german.proyectofinal.R;

import java.util.Calendar;

public class DataCoopsActivity extends AppCompatActivity {
    String nombreCoop;
    Button calendario;
    Button añadir;
    String fecha;
    String nombreCoop2;
    TextView titulo;
    Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coop);
        nombreCoop2 = getIntent().getExtras().getString("nombre");
        nombreCoop = getIntent().getExtras().getString("producto");
        añadir = findViewById(R.id.btnAñadirRegistro);
        calendario = findViewById(R.id.btnCaledario);
        titulo = findViewById(R.id.txtNombreC);
        titulo.setText(nombreCoop);
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
                        Log.d("asd", fecha);
                    }
                }, year, mes, dia);
                dpd.show();
            }
        });

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataCoopsActivity.this, AddDataActivity.class);
                intent.putExtra("nombre", nombreCoop2);
                //intent.putExtra("coop", nombreCoop2);
                startActivity(intent);
                finish();
            }
        });

        volver = findViewById(R.id.btn_atras);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataCoopsActivity.this, CoopsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}