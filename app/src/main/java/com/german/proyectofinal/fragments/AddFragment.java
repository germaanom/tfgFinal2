package com.german.proyectofinal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.german.proyectofinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
        /*productos = new ArrayList<>();
        productosSeleccionados = new ArrayList<>();
        listaProductos = v.findViewById(R.id.listaProductos);
        productos2 = new HashMap<String, String>();
        fDatabase.collection("productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data = document.getData();
                                Log.d("asd", ""+data);
                                productos2 = (Map<String, String>) data.get("Productos");
                                Iterator i = productos2.keySet().iterator();
                                while(i.hasNext()){
                                    productos.add(productos2.get(i.next()).toString());
                                }
                                //producto = data.get("Nombre").toString();
                               // productos.add(producto);
                                //Log.d("asd", producto);

                            }
                        }
                    }
                });

        ArrayAdapter adp = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, productos);
        listaProductos.setAdapter(adp);

        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String productoSeleecionado = (String) listaProductos.getAdapter().getItem(i);
                productosSeleccionados.add(productoSeleecionado);
                for(String products:productosSeleccionados){
                    Log.d("asdf", products);
                }
            }
        });*/
        nombre = (EditText) v.findViewById(R.id.txtNombreCoop);
        desc = (EditText) v.findViewById(R.id.txtUsuario);
        boton = v.findViewById(R.id.btn_crearCoop);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeCoop(nombre.getText().toString(), desc.getText().toString());
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