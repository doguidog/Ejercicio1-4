package com.example.ejercicio1_4.Clases;

import java.sql.Blob;

public class Usuario {

    private int id;
    private String nombre;
    private String descripcion;
    private Blob foto;

    public Usuario() {

    }

    public Usuario(int id, String nombre, String descripcion, Blob foto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }
}
