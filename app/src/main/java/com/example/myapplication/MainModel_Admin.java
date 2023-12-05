package com.example.myapplication;

public class MainModel_Admin {

String nombre;
String password;
String tipoUser;
String correo;

    public MainModel_Admin(){}
    public MainModel_Admin(String nombre, String password, String tipoUser, String correo) {
        this.nombre = nombre;
        this.password = password;
        this.tipoUser = tipoUser;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
