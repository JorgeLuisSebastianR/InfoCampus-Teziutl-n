package com.example.myapplication;
import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
public class MainAdapter_Admin_Ver extends FirestoreRecyclerAdapter<MainModel_Admin, MainAdapter_Admin_Ver.myViewHolder> {



    public MainAdapter_Admin_Ver(@NonNull FirestoreRecyclerOptions<MainModel_Admin> options) {
        super(options);
    }

    protected void onBindViewHolder(@NonNull MainAdapter_Admin_Ver.myViewHolder holder, int position, @NonNull MainModel_Admin model) {
        holder.nombreText.setText(model.getNombre());
        holder.password.setText(model.getPassword());
        holder.Correotext.setText(model.getCorreo());
        holder.tipoUsertext.setText(model.getTipoUser());


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.nombreText.getContext())
                        .setContentHolder(new ViewHolder(R.layout.admin_update_popup))
                        .setExpanded(true, 1200)
                        .create();



                View view = dialogPlus.getHolderView();

                EditText nombreText = view.findViewById(R.id.txt_newUser);
                EditText password = view.findViewById(R.id.txt_pass);
                EditText Correotext = view.findViewById(R.id.txt_correo);
                EditText tipoUsertext = view.findViewById(R.id.txt_tipoUser);
                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                nombreText.setText(model.getNombre());
                password.setText(model.getPassword());
                Correotext.setText(model.getCorreo());
                tipoUsertext.setText(model.getTipoUser());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            String userEmail = user.getEmail();
                            String newEmail = Correotext.getText().toString();

                            if (!userEmail.equals(newEmail)) {
                                // Si el correo electrónico cambió, actualiza el correo en Firebase Authentication
                                user.updateEmail(newEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Actualiza los datos en Firestore
                                                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("user")
                                                            .document(getSnapshots().getSnapshot(position).getId());

                                                    Map<String, Object> map = new HashMap<>();
                                                    map.put("nombre", nombreText.getText().toString());
                                                    map.put("password", password.getText().toString());
                                                    map.put("correo", Correotext.getText().toString());
                                                    map.put("tipoUser", tipoUsertext.getText().toString());

                                                    docRef.update(map)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(holder.nombreText.getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                                                                    dialogPlus.dismiss();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(Exception e) {
                                                                    Toast.makeText(holder.nombreText.getContext(), "Error en la actualización", Toast.LENGTH_SHORT).show();
                                                                    dialogPlus.dismiss();
                                                                }
                                                            });
                                                } else {
                                                    // Si falla la actualización del correo en Authentication
                                                    Toast.makeText(holder.nombreText.getContext(), "Error al actualizar el correo electrónico: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // Si el correo no cambió, solo actualiza los datos en Firestore
                                DocumentReference docRef = FirebaseFirestore.getInstance().collection("user")
                                        .document(getSnapshots().getSnapshot(position).getId());

                                Map<String, Object> map = new HashMap<>();
                                map.put("nombre", nombreText.getText().toString());
                                map.put("password", password.getText().toString());
                                map.put("correo", Correotext.getText().toString());
                                map.put("tipoUser", tipoUsertext.getText().toString());

                                docRef.update(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(holder.nombreText.getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(Exception e) {
                                                Toast.makeText(holder.nombreText.getContext(), "Error en la actualización", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(holder.nombreText.getContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombreText.getContext());
                builder.setTitle("¿Desea eliminar?");
                builder.setMessage("Los datos serán eliminados y no podrán ser restaurados.");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            // Eliminar el usuario de Firebase Authentication
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Eliminar el documento del usuario de Firestore
                                        DocumentReference docRef = FirebaseFirestore.getInstance().collection("user")
                                                .document(getSnapshots().getSnapshot(position).getId());

                                        docRef.delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(holder.nombreText.getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(Exception e) {
                                                        Toast.makeText(holder.nombreText.getContext(), "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(holder.nombreText.getContext(), "Error al eliminar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // El usuario no está autenticado
                            Toast.makeText(holder.nombreText.getContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.nombreText.getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });


    }

    public MainAdapter_Admin_Ver.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_user, parent, false);

        return new MainAdapter_Admin_Ver.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView nombreText, Correotext, tipoUsertext, password;
        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreText = (TextView) itemView.findViewById(R.id.nombre_Text);
            password = (TextView) itemView.findViewById(R.id.Pass_User_text);
            Correotext = (TextView) itemView.findViewById(R.id.Correo_text);
            tipoUsertext = (TextView) itemView.findViewById(R.id.tipoUser_text);

            btnEdit = (Button) itemView.findViewById(R.id.btn_editar);
            btnDelete = (Button) itemView.findViewById(R.id.btn_eliminar);


        }
    }

}