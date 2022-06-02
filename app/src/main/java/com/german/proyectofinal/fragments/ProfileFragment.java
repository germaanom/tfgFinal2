package com.german.proyectofinal.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.german.proyectofinal.Producto;
import com.german.proyectofinal.SpacingItemDecorator;
import com.german.proyectofinal.activities.LoginActivity;
import com.german.proyectofinal.R;
import com.german.proyectofinal.adapters.ProductoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;


import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btn_logout;
    FirebaseAuth mAuth;
    FirebaseFirestore fDatabase;
    ImageButton upload;
    TextView emailP;
    TextView user;
    Button btn_desc;
    EditText desc;
    ProgressDialog mProgress;
    private static final int GALLERY_INTENT = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        emailP = v.findViewById(R.id.txtEmailP);
        upload = v.findViewById(R.id.imgPerfil);
        //..String email = mAuth.getCurrentUser().getEmail();
        fDatabase = FirebaseFirestore.getInstance();
        user = v.findViewById(R.id.txtuser);
        btn_logout = v.findViewById(R.id.btn_logout);
        mProgress = new ProgressDialog(getActivity());
        String email = mAuth.getCurrentUser().getEmail();
        btn_desc = v.findViewById(R.id.editDesc);
        desc = v.findViewById(R.id.txtDesc);

        Log.d("asd", email);
        emailP.setText(email);
        /*fDatabase.collection("profile").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map data = new HashMap();
                data = documentSnapshot.getData();
                String foto = data.get("foto").toString();
                //String descripcion = data.get("desc").toString();
                Uri foto2 = Uri.parse(foto);

                Glide.with(getActivity()).load(foto2).into(upload);
            }
        });*/
        if(!mAuth.getCurrentUser().getEmail().isEmpty()){ ;
            fDatabase.collection("profile").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Map data = new HashMap();
                    data = task.getResult().getData();
                    if(data != null) {
                        Log.d("asd", data.toString());
                        String desc2 = data.get("desc").toString();
                        Log.d("as", desc2);
                        desc.setText(desc2, TextView.BufferType.EDITABLE);
                    }
                }
            });
        }


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLaunch.launch(intent);


            }
        });

        fDatabase.collection("usuarios").whereEqualTo("Correo", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    Map data = new HashMap();
                    data = document.getData();
                    user.setText(data.get("Nombre").toString());
                }
            }
        });


        btn_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descTxt = desc.getText().toString();
                Map desc1 = new HashMap();
                desc1.put("desc", descTxt);
                fDatabase.collection("profile").document(email).update(desc1);
                Log.d("asd", email);

                Toast.makeText(getActivity(), "Descripcion actualizada", Toast.LENGTH_SHORT).show();
            }


        });
        return v;
    }


    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    mProgress.setTitle("Subiendo...");
                    mProgress.setMessage("Subiendo foto a firebase");
                    mProgress.setCancelable(false);
                    mProgress.show();

                    Uri uri = result.getData().getData();
                    Map perfil = new HashMap();
                    String email = mAuth.getCurrentUser().getEmail();
                    perfil.put("email", email);
                    perfil.put("foto", uri);
                    fDatabase.collection("profile").document(email).set(perfil);
                    mProgress.cancel();
                }
            });

}