package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Panel_Departamento_F_01_home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    MainAdapter_Departamento_Ver mainAdapterDepartamentoVer;

    TextView User, tipoUser, correo;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    public Panel_Departamento_F_01_home() {
        // Required empty public constructor
    }


    public static Panel_Departamento_F_01_home newInstance(String param1, String param2) {
        Panel_Departamento_F_01_home fragment = new Panel_Departamento_F_01_home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_panel_departamento_01_home, container, false);


        User = view.findViewById(R.id.txt_nombre_User);
        tipoUser = view.findViewById(R.id.txt_tipo);
        correo = view.findViewById(R.id.txt_correo);


        //traer la info






        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DocumentReference userDocRef = db.collection("user").document(userId);

            userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String nombreUsuario = documentSnapshot.getString("nombre");
                        String tipoUsuario = documentSnapshot.getString("tipoUser");
                        String correoUser = documentSnapshot.getString("correo");

                        User.setText(nombreUsuario);
                        tipoUser.setText(tipoUsuario);
                        correo.setText(correoUser);
                    } else {
                        User.setText("");
                        tipoUser.setText("");
                        correo.setText("Sin Correo");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    User.setText("Error al obtener usuario: " + e.getMessage());
                    tipoUser.setText("Error al obtener tipo de usuario: " + e.getMessage());
                    correo.setText("Error al obtener Correo: " + e.getMessage());
                }
            });
        }

        return view;

    }
}