package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
public class Panel_Admin_F_03_Ver extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private MainAdapter_Admin_Ver mainAdapterAdminVer;
    public Panel_Admin_F_03_Ver() {}
    public static Panel_Admin_F_03_Ver newInstance() {return new Panel_Admin_F_03_Ver();}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel_departamento_03_ver, container, false);

        recyclerView = view.findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        searchView = view.findViewById(R.id.buscador);
        searchView.clearFocus();

        setupRecyclerView();
        setupSearchView();

        return view;
    }

    private void setupRecyclerView() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("user");

        FirestoreRecyclerOptions<MainModel_Admin> options = new FirestoreRecyclerOptions.Builder<MainModel_Admin>()
                .setQuery(query, MainModel_Admin.class)
                .build();

        mainAdapterAdminVer = new MainAdapter_Admin_Ver(options);
        recyclerView.setAdapter(mainAdapterAdminVer);
    }

    private void setupSearchView() {
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
    }

    private void textSearch(String query) {
        Query firestoreQuery = FirebaseFirestore.getInstance().collection("user")
                .orderBy("nombre").startAt(query).endAt(query + "~");
        FirestoreRecyclerOptions<MainModel_Admin> options = new FirestoreRecyclerOptions.Builder<MainModel_Admin>()
                .setQuery(firestoreQuery, MainModel_Admin.class)
                .build();
        mainAdapterAdminVer = new MainAdapter_Admin_Ver(options);
        mainAdapterAdminVer.startListening();
        recyclerView.setAdapter(mainAdapterAdminVer);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapterAdminVer.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapterAdminVer.stopListening();
    }
}
