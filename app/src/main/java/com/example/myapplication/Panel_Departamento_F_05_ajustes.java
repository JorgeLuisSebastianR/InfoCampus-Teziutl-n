package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Panel_Departamento_F_05_ajustes extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_Salir;
    FirebaseAuth mAuth;

    public Panel_Departamento_F_05_ajustes() {}

    public static Panel_Departamento_F_05_ajustes newInstance(String param1, String param2) {
        Panel_Departamento_F_05_ajustes fragment = new Panel_Departamento_F_05_ajustes();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel_departamento_05_ajustes, container, false);
        mAuth = FirebaseAuth.getInstance();
        btn_Salir  = view.findViewById(R.id.btn_salir);
        btn_Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity().startActivity(new Intent(getActivity(), MainActivity_Login.class));
                getActivity().finish();
            }
        });
        return view;
    }
}