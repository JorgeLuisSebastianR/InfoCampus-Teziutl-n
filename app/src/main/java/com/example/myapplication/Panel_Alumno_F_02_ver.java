package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Panel_Alumno_F_02_ver extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    MainAdapter_Alumno_Ver mainAdapterAlumnoVer;
    SearchView searchView;

    public Panel_Alumno_F_02_ver() {
        // Required empty public constructor
    }


    @Override
    public void onStop() {
        super.onStop();
        mainAdapterAlumnoVer.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapterAlumnoVer.startListening();
    }
    public static Panel_Alumno_F_02_ver newInstance(String param1, String param2) {
        Panel_Alumno_F_02_ver fragment = new Panel_Alumno_F_02_ver();
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

        View view = inflater.inflate(R.layout.fragment_panel_alumno_02_ver, container, false);

        recyclerView = view.findViewById(R.id.RV);
        searchView = view.findViewById(R.id.buscador);
        searchView.clearFocus();

        //traer la info
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        FirebaseRecyclerOptions<MainModel_Departamento> options = new FirebaseRecyclerOptions.Builder<MainModel_Departamento>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Usuario_1"), MainModel_Departamento.class)
                .build();

        mainAdapterAlumnoVer = new MainAdapter_Alumno_Ver(options);
        recyclerView.setAdapter(mainAdapterAlumnoVer);

        //traer la info
        //buscador
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                textSearch(query);
                return false;
            }
        });
        //buscador-Fin

        return view;
    }
    //buscar metdo donde hace la consulta
    private void textSearch(String query) {
        FirebaseRecyclerOptions<MainModel_Departamento> options = new FirebaseRecyclerOptions.Builder<MainModel_Departamento>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Usuario_1").orderByChild("nombre_Aviso").startAt(query).endAt(query+"~"), MainModel_Departamento.class)
                .build();
        mainAdapterAlumnoVer = new MainAdapter_Alumno_Ver(options);
        mainAdapterAlumnoVer.startListening();
        recyclerView.setAdapter(mainAdapterAlumnoVer);
    }
    //buscar-fin
}