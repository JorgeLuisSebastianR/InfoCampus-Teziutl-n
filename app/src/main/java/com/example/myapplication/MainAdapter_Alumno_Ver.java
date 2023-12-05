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
public class MainAdapter_Alumno_Ver extends FirebaseRecyclerAdapter<MainModel_Departamento, MainAdapter_Alumno_Ver.myViewHolder> {
    public MainAdapter_Alumno_Ver(@NonNull FirebaseRecyclerOptions<MainModel_Departamento> options) {super(options);}
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

    }


    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno_aviso,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        //Declara las variables que de main_item.xml

        TextView nombre_aviso,fechaFin,fechaInicio,Descripcion;
        ImageView url;
        Button btnEdit, btnDelete;
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
