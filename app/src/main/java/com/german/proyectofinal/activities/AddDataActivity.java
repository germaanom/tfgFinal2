package com.german.proyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
    Button añadir;
    String fecha;
    String kilos2;
    String nSocio;
    String nombreCoop;
    Button elegirF;
    FirebaseFirestore fDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        kilos = findViewById(R.id.txtkilos);
        socio = findViewById(R.id.txtnSocio);
        coop = findViewById(R.id.txtNombreCoop);
        añadir = findViewById(R.id.btn_añadirRegistro);
        elegirF = findViewById(R.id.btn_fecha);
        nombreCoop = getIntent().getExtras().getString("nombre");

        coop.setText(nombreCoop);
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
                        Log.d("asd", fecha);
                    }
                }, year, mes, dia);
                dpd.show();
            }
        });

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kilos2 = kilos.getText().toString();
                nSocio = socio.getText().toString();
                nombreCoop = coop.getText().toString();

                if(kilos2 != null && nSocio != null && fecha != null){
                    writeData(fecha, kilos2, nSocio, nombreCoop);
                }else{
                    Toast.makeText(AddDataActivity.this, "No se ha añadido", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void writeData(String fecha, String kilos, String nSocio, String nombreCoop){
        Map<String, String> data = new HashMap<>();
        data.put("fecha",fecha);
        data.put("kilos",kilos);
        data.put("nSocio",nSocio);
        data.put("nombreCoop",nombreCoop);

        fDatabase = FirebaseFirestore.getInstance();
        fDatabase.collection("dataCoops").add(data);
    }
}