package com.example.myapplication;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Panel_Departamento_F_02_Crear extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private int dia, mes, ano,dia2, mes2, ano2;

    EditText nameAviso,fechaInicio,fechaFin,descripcion,url;
    Button btn_Crear,btn_Subir_Foto,btn_Eliminar_Foto;
    ImageView imagen_f1,imagen_f2,imagenFoto;

    FirebaseFirestore mfirestore;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    String storage_path = "pet/*";
    private Uri imagen_URL;
    private String imageUrl;
    String photo = "photo";
    String idd;

    ProgressDialog progressDialog;

    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_IMAGE = 300;



    public Panel_Departamento_F_02_Crear() {}

    public static Panel_Departamento_F_02_Crear newInstance(String param1, String param2) {
        Panel_Departamento_F_02_Crear fragment = new Panel_Departamento_F_02_Crear();
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
        View view = inflater.inflate(R.layout.fragment_panel_departamento_02_crear, container, false);

        imagen_f1 = view.findViewById(R.id.image_Fecha1);
        imagen_f2 = view.findViewById(R.id.image_Fecha2);
        imagenFoto = view.findViewById(R.id.imagen_Aviso);

        imagen_f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == imagen_f1) {
                    final Calendar calendar = Calendar.getInstance();
                    dia = calendar.get(Calendar.DAY_OF_MONTH);
                    mes = calendar.get(Calendar.MONTH);
                    ano = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            fechaInicio.setText(fechaSeleccionada);
                        }
                    }, ano, mes, dia);
                    datePickerDialog.show();
                }
            }
        });
        imagen_f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == imagen_f2) {
                    final Calendar calendar = Calendar.getInstance();
                    dia2 = calendar.get(Calendar.DAY_OF_MONTH);
                    mes2 = calendar.get(Calendar.MONTH);
                    ano2 = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            fechaFin.setText(fechaSeleccionada);
                        }
                    }, ano2, mes2, dia2);
                    datePickerDialog.show();
                }
            }
        });

        //imagen

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Subiendo foto...");
        mfirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        storageReference = FirebaseStorage.getInstance().getReference();
        btn_Subir_Foto = view.findViewById(R.id.btn_S_Imagen);

        btn_Subir_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });


        //fin imagen


        nameAviso = view.findViewById(R.id.T_nameAviso);
        fechaInicio = view.findViewById(R.id.T_fechaInicio);
        fechaFin = view.findViewById(R.id.T_fechaFin);
        descripcion = view.findViewById(R.id.T_descripcion);
        url = view.findViewById(R.id.T_url);



        btn_Crear = view.findViewById(R.id.btn_crear_avisos);

        btn_Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
                clearAll();
            }
        });
        return view;
    }

    private void uploadPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        // Verificar si hay una actividad que puede manejar la selección de imágenes
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivityForResult(intent, COD_SEL_IMAGE);
        } else {
            // Mostrar un mensaje de error o tomar una acción alternativa
            Toast.makeText(requireContext(), "No hay aplicación para seleccionar imágenes", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == COD_SEL_IMAGE) {
                // Procesar la imagen seleccionada aquí
                imagen_URL = data.getData();
                Log.d("TAG", "URI de imagen seleccionada: " + imagen_URL);


                String idd = "mi_documento_123";
                subirFoto(imagen_URL);
            }
        }
    }


    private void subirFoto(Uri imagenUrl) {
        progressDialog.setMessage("Actualizando Foto");
        progressDialog.show();

        // Generar ID aleatorio
        String idd = UUID.randomUUID().toString();

        String rute_store_photo = storage_path + ""+ photo +"" + mAuth.getUid() +""+idd;
        StorageReference reference = storageReference.child(rute_store_photo);

        reference.putFile(imagenUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUrl = uri.toString();
                        imageUrl = downloadUrl;

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("photo", downloadUrl);


                        mfirestore.collection("pet").document(idd).set(map);

                        Toast.makeText(getContext(), "foto Actualizada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        insertDataWithImageUrl();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al subir foto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertDataWithImageUrl() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Map<String,Object> map = new HashMap<>();
        // Usar el link de la imagen
        map.put("url", imageUrl);


        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.mapache)
                .error(R.drawable.mapache)
                .into(imagenFoto);

    }


    private void insertdata() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        Map<String,Object> map = new HashMap<>();
        map.put("nombre_Aviso", nameAviso.getText().toString());
        map.put("fechaInicio", fechaInicio.getText().toString());
        map.put("fechaFin", fechaFin.getText().toString());
        map.put("Descripcion", descripcion.getText().toString());
        map.put("url", imageUrl);

        FirebaseDatabase.getInstance().getReference().child("Usuario_1").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Aviso creado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private  void clearAll(){
        nameAviso.setText("");
        fechaInicio.setText("");
        fechaFin.setText("");
        descripcion.setText("");
        url.setText("");
    }

}