package com.german.proyectofinal.fragments;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.german.proyectofinal.Cooperativa;
import com.german.proyectofinal.adapters.CoopsAdapter;
import com.german.proyectofinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Cooperativa> list_coops;
    private RecyclerView recyclerView;
    private CoopsAdapter adapter;
    private Map data;
    private String nombre;
    private String desc;
    private FirebaseFirestore fDatabase;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        /*botonEsparragos = v.findViewById(R.id.imageButton);
        botonPatatas = v.findViewById(R.id.imageButton2);
        botonAceitunas = v.findViewById(R.id.imageButton3);

        botonEsparragos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EsparragosActivity.class);
                startActivity(intent);
            }
        });

        botonPatatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PatatasActivity.class);
                startActivity(intent);
            }
        });

        botonAceitunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AceitunasActivity.class);
                startActivity(intent);
            }
        });
*/


        fDatabase = FirebaseFirestore.getInstance();
        data = new HashMap<String, String>();
        list_coops = new ArrayList<>();
        recyclerView= (RecyclerView) v.findViewById(R.id.list_coops);

        //CollectionReference coops = fDatabase.collection("coops");
        fDatabase.collection("coops")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                data = document.getData();
                                nombre=data.get("Nombre").toString();
                                desc = data.get("Desc").toString();
                                list_coops.add(new Cooperativa(nombre, desc,""));
                                adapter = new CoopsAdapter(list_coops, v.getContext());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                            }
                        } else {

                        }
                    }
                });


                LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
                recyclerView.setLayoutManager(llm);

                DividerItemDecoration did = new DividerItemDecoration(v.getContext(), llm.getOrientation());
                recyclerView.addItemDecoration(did);




        return v;
    }


}