package com.german.proyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.german.proyectofinal.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddDataActivity extends AppCompatActivity {

    EditText kilos;
    EditText socio;
    TextView coop;
    TextView producto2;
    Button añadir;
    String fecha;
    String kilos2;
    String nSocio;
    String coop2;
    String producto;
    String nombreCoop;
    Button elegirF;
    Button volver;
    FirebaseFirestore fDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        kilos = findViewById(R.id.txtkilos);
        //socio = findViewById(R.id.txtnSocio);
        producto2 = findViewById(R.id.txtNombreProducto);
        añadir = findViewById(R.id.btn_añadirRegistro);
        elegirF = findViewById(R.id.btn_fecha);
        coop = findViewById(R.id.txtCoop);
        producto = getIntent().getExtras().getString("nombre");
        nombreCoop = getIntent().getExtras().getString("coop");
        producto2.setText(producto);
        Log.d("asd", nombreCoop);
        coop.setText(nombreCoop);
        //ABRIR CALENDARIO Y ELEGIR FECHA
        elegirF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(AddDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        fecha = i2 + "/" + i1 + "/" + i;
                        elegirF.setText(fecha);
                        Log.d("asd", fecha);
                    }
                }, year, mes, dia);
                dpd.show();
            }
        });

        //RECOGE LOS DATOS DE LOS EDITEXTS Y EJECUTA WRITEDATA
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kilos2 = kilos.getText().toString();
                coop2 = coop.getText().toString();
                producto = producto2.getText().toString();

                if(!kilos2.isEmpty() && !fecha.isEmpty()){
                    writeData(fecha, kilos2, producto, coop2);
                    finish();
                    Toast.makeText(AddDataActivity.this, "Registro Añadido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddDataActivity.this, "No se ha añadido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //VUELVE A LA ACTIVITY DE DATOS
        volver = findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDataActivity.this, DataCoopsActivity.class);
                intent.putExtra("nombre", nombreCoop);
                startActivity(intent);
                finish();
            }
        });


    }

    //METODO QUE CREA UN MAPA CON LOS VALORES DE LOS REGISTROS Y LO AÑADE LA BASE DE DATOS
    public void writeData(String fecha, String kilos, String producto, String coop){
        Map<String, String> data = new HashMap<>();
        data.put("fecha",fecha);
        data.put("kilos",kilos);
        //data.put("nSocio",nSocio);
        data.put("producto",producto);
        data.put("coop", coop);

        fDatabase = FirebaseFirestore.getInstance();
        fDatabase.collection("dataCoops").add(data);
    }
}