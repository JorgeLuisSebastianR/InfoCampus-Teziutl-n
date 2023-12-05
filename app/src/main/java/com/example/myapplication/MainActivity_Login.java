package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity_Login extends AppCompatActivity {

    private TextInputEditText editUser, editPass;
    private Button btn_logeo;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main_login);
        editUser = findViewById(R.id.editUser); // Asigna el EditText del usuario
        editPass = findViewById(R.id.editPass); // Asigna el EditText de la contraseña
        btn_logeo = findViewById(R.id.btn_logeo); // Asigna el botón de inicio de sesión

        // Encontrar la referencia del TextView para la contraseña
        TextView textViewPassword = findViewById(R.id.editPass);

        // Establecer el tipo de entrada como contraseña
        textViewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        TextView btnOlvidoPass = findViewById(R.id.btn_ovidoPass);
        btnOlvidoPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity_Login.this, o_pass.class));
            }
        });
        btn_logeo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String emailUser = editUser.getText().toString();
                String passUser = editPass.getText().toString();

                if (emailUser.isEmpty() && passUser.isEmpty()){
                    Toast.makeText(MainActivity_Login.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(emailUser,passUser);
                }
            }
        });


    }

    private void loginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                DocumentReference docRef = FirebaseFirestore.getInstance().collection("user").document(user.getUid());
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            String tipoUsuarioFirebase = documentSnapshot.getString("tipoUser");
                                            // Verificar el tipo de usuario desde Firebase
                                            if (tipoUsuarioFirebase != null) {
                                                switch (tipoUsuarioFirebase) {
                                                    case "admin ":
                                                        // Redirigir al panel de administrador
                                                        startActivity(new Intent(MainActivity_Login.this, Panel_Admin_00_Main.class));
                                                        break;
                                                    case "alumno ":
                                                        // Redirigir al panel de alumno
                                                        startActivity(new Intent(MainActivity_Login.this, Panel_Alumno_00_Main.class));
                                                        break;
                                                    case "departamento ":
                                                        // Redirigir al panel de departamento
                                                        startActivity(new Intent(MainActivity_Login.this, Panel_Departamento_00_Main.class));
                                                        break;
                                                    default:
                                                        // Tipo de usuario no reconocido
                                                        Toast.makeText(MainActivity_Login.this, "Tipo de usuario no reconocido", Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                                finish(); // Finalizar la actividad actual si es necesario
                                            } else {
                                                // Tipo de usuario no encontrado en Firestore
                                                Toast.makeText(MainActivity_Login.this, "Tipo de usuario no encontrado", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            // Error al iniciar sesión con Firebase Auth
                            Toast.makeText(MainActivity_Login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el fallo de inicio de sesión
                        Toast.makeText(MainActivity_Login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("user").document(userId);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String userType = documentSnapshot.getString("tipoUser");
                        if (userType != null) {
                            switch (userType) {
                                case "admin ":
                                    startActivity(new Intent(MainActivity_Login.this, Panel_Admin_00_Main.class));
                                    break;
                                case "alumno ":
                                    startActivity(new Intent(MainActivity_Login.this, Panel_Alumno_00_Main.class));
                                    break;
                                case "departamento ":
                                    startActivity(new Intent(MainActivity_Login.this, Panel_Departamento_00_Main.class));
                                    break;
                                default:
                                    // Tipo de usuario no reconocido
                                    // Puedes redirigir a una actividad por defecto o mostrar un mensaje de error
                                    break;
                            }
                            finish();
                        }
                    }
                }
            });
        }
    }

}