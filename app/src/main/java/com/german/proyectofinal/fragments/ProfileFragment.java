package com.german.proyectofinal.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.german.proyectofinal.activities.LoginActivity;
import com.german.proyectofinal.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

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
        upload = v.findViewById(R.id.imgPerfil);
        String email = mAuth.getCurrentUser().getEmail();
        fDatabase = FirebaseFirestore.getInstance();
        btn_logout = v.findViewById(R.id.btn_logout);
        mProgress = new ProgressDialog(getActivity());

        fDatabase.collection("profile").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map data = new HashMap();
                data = documentSnapshot.getData();
                String foto = data.get("foto").toString();
                String descripcion = data.get("desc").toString();
                Uri foto2 = Uri.parse(foto);

                Glide.with(getActivity())
                        .load(foto2)
                        .fitCenter()
                        .centerCrop()
                        .into(upload);
            }
        });

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
                    fDatabase.collection("profiles").document(email).set(perfil);
                }
            });

}