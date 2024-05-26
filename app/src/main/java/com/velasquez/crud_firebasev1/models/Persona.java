package com.velasquez.crud_firebasev1.models;

public class Persona {
    private String uid;
    private String nombre;
    private String apellido;
    private String correo;


    public Persona() {
    }

    public Persona(String uid, String nombre, String apellido, String correo) {
        this.uid = uid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

//Get and setters:


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    //toString


    @Override
    public String toString() {
        return "nombre='" + nombre;
    }
}//Fin Persona
