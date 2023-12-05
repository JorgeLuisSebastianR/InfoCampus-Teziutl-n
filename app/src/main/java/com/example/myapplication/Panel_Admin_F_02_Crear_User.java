package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Panel_Admin_F_02_Crear_User extends Fragment {

    String[] items = {"admin ","alumno ","departamento "};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> arrayAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private EditText T_nameUsuario,T_correo,T_pass,T_tipo;
    private Button btn_Crear;


    FirebaseAuth mauth;
    FirebaseFirestore mFirestore;
    public Panel_Admin_F_02_Crear_User() {
        // Required empty public constructor
    }

    public static Panel_Admin_F_02_Crear_User newInstance(String param1, String param2) {
        Panel_Admin_F_02_Crear_User fragment = new Panel_Admin_F_02_Crear_User();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirestore = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        if (getArguments() != null) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        autoCompleteTxt.setText(""); // Borrar el texto seleccionado
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel_admin_02_crear_user, container, false);
        mFirestore = FirebaseFirestore.getInstance();

        mauth = FirebaseAuth.getInstance();
        autoCompleteTxt = view.findViewById(R.id.auto_complete_txt);
        arrayAdapter = new ArrayAdapter<String>(requireContext(), R.layout.list_item_admin_crear, items);
        autoCompleteTxt.setAdapter(arrayAdapter);


        T_tipo = view.findViewById(R.id.T_tipo);
        T_nameUsuario = view.findViewById(R.id.T_nameUsuario);
        T_correo = view.findViewById(R.id.T_correo);
        T_pass = view.findViewById(R.id.T_pass);
        btn_Crear = view.findViewById(R.id.btn_Crear_User);

        T_tipo.setVisibility(View.GONE);



        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí puedes manejar la selección del item si es necesario
                String selectedItem = (String) parent.getItemAtPosition(position);
                T_tipo.setText(selectedItem);
            }
        });




        btn_Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUser = T_nameUsuario.getText().toString();
                String emailUser = T_correo.getText().toString();
                String passUser = T_pass.getText().toString();
                String tipoUser = T_tipo.getText().toString();

                if (nombreUser.isEmpty() && emailUser.isEmpty() && passUser.isEmpty() && tipoUser.isEmpty()){
                    Toast.makeText(getContext(), "Complete los datos", Toast.LENGTH_SHORT).show();
                }else {
                    resgistarUser(nombreUser,emailUser,passUser,tipoUser);
                }

            }
        });
        return view;
    }
    private void resgistarUser(String nombreUser, String emailUser, String passUser, String tipoUser) {
        mauth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mauth.getCurrentUser().getUid();
                Map<String,Object> map = new HashMap<>();
                map.put("id",id);
                map.put("nombre",nombreUser);
                map.put("correo",emailUser);
                map.put("password",passUser);
                map.put("tipoUser",tipoUser);
                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        clearFields();
                        Toast.makeText(getContext(),"Usurio guardado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error al agregar usuario",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        T_nameUsuario.setText("");
        T_correo.setText("");
        T_pass.setText("");
        T_tipo.setText("");
    }
}