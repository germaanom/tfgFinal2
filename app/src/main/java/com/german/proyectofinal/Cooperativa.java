package com.german.proyectofinal;

public class Cooperativa {
    public String nombre;
    public String descripcion;
    public String contraseña;

    public Cooperativa(String nombre, String desc, String pass){
        this.nombre=nombre;
        this.descripcion=desc;
        this.contraseña=pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
