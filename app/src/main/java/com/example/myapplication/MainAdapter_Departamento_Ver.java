package com.example.myapplication;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

//MainAdapter funcionamiento ///myViewHolder declar las variables
public class MainAdapter_Departamento_Ver extends FirebaseRecyclerAdapter<MainModel_Departamento, MainAdapter_Departamento_Ver.myViewHolder> {
    public MainAdapter_Departamento_Ver(@NonNull FirebaseRecyclerOptions<MainModel_Departamento> options) {super(options);}
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel_Departamento model) {

        //solo tare la infor
        holder.nombre_aviso.setText(model.getNombre_Aviso());
        holder.Descripcion.setText(model.getDescripcion());
        holder.fechaInicio.setText(model.getFechaInicio());
        holder.fechaFin.setText(model.getFechaFin());
        Glide.with(holder.url.getContext())
                .load(model.getUrl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.url);

        //fin de traer la info

        //editar
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.url.getContext())
                        .setContentHolder(new ViewHolder(R.layout.departamento_update_popup))// le decimos donde los mandamos
                        .setExpanded(true, 1900)//tiempo de respuesta
                        .create();

                View view = dialogPlus.getHolderView();


                //llamamos a consultra la informacion desde el archivo model
                EditText nombre_aviso = view.findViewById(R.id.txt_nameAviso);
                EditText fechaInicio = view.findViewById(R.id.txt_fechaInicio);
                EditText fechaFin = view.findViewById(R.id.txt_fechaFin);
                EditText Descripcion = view.findViewById(R.id.txt_descripcion);
                EditText url = view.findViewById(R.id.txt_url);

                //lo siguiente coidgo para enviara la informacion a FireBase
                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                nombre_aviso.setText(model.getNombre_Aviso());
                fechaInicio.setText(model.getFechaInicio());
                fechaFin.setText(model.getFechaFin());
                Descripcion.setText(model.getDescripcion());
                url.setText(model.getUrl());
                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("nombre_Aviso",nombre_aviso.getText().toString());
                        map.put("fechaInicio",fechaInicio.getText().toString());
                        map.put("fechaFin",fechaFin.getText().toString());
                        map.put("Descripcion",Descripcion.getText().toString());
                        map.put("url",url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Usuario_1")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nombre_aviso.getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();//una vez sea actualizado este accion cierra la centana emergente
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.nombre_aviso.getContext(), "Error en la actualizacion", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();//una vez muestre el error este accion cierra la centana emergente
                                    }
                                });


                    }
                });
            }
        });
        //editar-Fin

        //Eliminar
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre_aviso.getContext());//Se crea una alerta
                builder.setTitle("¿Desea Eiminarlo?");
                builder.setMessage("Los datos seran Eliminado y no hay formas de Restaurarlos");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Usuario_1")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.nombre_aviso.getContext(),"Elimindo",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.nombre_aviso.getContext(),"Cacelado",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        //Emilinar-Fin

    }


    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_departamento_aviso,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        //Declara las variables que de main_item.xml

        TextView nombre_aviso,fechaFin,fechaInicio,Descripcion;
        ImageView url;
        Button btnEdit, btnDelete;
        ImageView imagen_Aviso;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_aviso=(TextView)itemView.findViewById(R.id.nombreText);
            fechaInicio = (TextView)itemView.findViewById(R.id.fechainicio);
            fechaFin = (TextView)itemView.findViewById(R.id.fechafin);
            Descripcion = (TextView)itemView.findViewById(R.id.descipcion_text);
            url =(ImageView)itemView.findViewById(R.id.img1);

            btnEdit=(Button)itemView.findViewById(R.id.btn_editar);
            btnDelete=(Button) itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
