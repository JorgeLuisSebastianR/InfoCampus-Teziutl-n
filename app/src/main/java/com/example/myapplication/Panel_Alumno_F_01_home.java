package com.example.myapplication;

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

public class Panel_Alumno_F_01_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView User, tipoUser, correo;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    public Panel_Alumno_F_01_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Panel_alumno_F_01_home.
     */
    // TODO: Rename and change types and number of parameters
    public static Panel_Alumno_F_01_home newInstance(String param1, String param2) {
        Panel_Alumno_F_01_home fragment = new Panel_Alumno_F_01_home();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panel_alumno_01_home, container, false);

        User = view.findViewById(R.id.txt_nombre_User);
        tipoUser = view.findViewById(R.id.txt_tipo);
        correo = view.findViewById(R.id.txt_correo);

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