package com.german.proyectofinal;

public class Producto {
    String coop;
    String nombre;

    public Producto(String coop, String nombre) {
        this.coop = coop;
        this.nombre = nombre;
    }

    public String getCoop() {
        return coop;
    }

    public void setCoop(String coop) {
        this.coop = coop;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
