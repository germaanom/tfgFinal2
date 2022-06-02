package com.german.proyectofinal.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.german.proyectofinal.R;
import com.german.proyectofinal.activities.CoopsActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private FirebaseFirestore fDatabase;
    private ArrayList<String> productos;
    private Map productos2;
    private EditText nombre;
    private String producto;
    private Map data;
    private Spinner listaProductos;
    private EditText desc;
    private Button boton;

    public AddFragment() {
        // Required empty public constructor
    }


    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        fDatabase = FirebaseFirestore.getInstance();

        nombre = (EditText) v.findViewById(R.id.txtNombreCoop);
        desc = (EditText) v.findViewById(R.id.txtUsuario);
        boton = v.findViewById(R.id.btn_atras);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nombre.getText().toString().isEmpty() && !desc.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Cooperativa Creada", Toast.LENGTH_SHORT).show();
                    writeCoop(nombre.getText().toString(), desc.getText().toString());
                }else{
                    Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });



        return v;
    }


    public void writeCoop(String nombre, String desc){
        Map<String, String> coop = new HashMap<>();
        coop.put("Nombre",nombre);
        coop.put("Desc",desc);

        fDatabase = FirebaseFirestore.getInstance();
        fDatabase.collection("coops").document(nombre).set(coop);


    }


}
