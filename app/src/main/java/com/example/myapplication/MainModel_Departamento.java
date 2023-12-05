package com.example.myapplication;

public class MainModel_Departamento {

    String nombre_Aviso;
    String fechaFin;
    String fechaInicio;
    String Descripcion;
    String url;

    public MainModel_Departamento(){}

    public MainModel_Departamento(String nombre_Aviso, String fechaFin, String fechaInicio, String descripcion, String url) {
        this.nombre_Aviso = nombre_Aviso;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.Descripcion = descripcion;
        this.url = url;
    }

    public String getNombre_Aviso() {
        return nombre_Aviso;
    }

    public void setNombre_Aviso(String nombre_Aviso) {
        this.nombre_Aviso = nombre_Aviso;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
