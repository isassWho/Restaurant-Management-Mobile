package com.cod.tablayout_demo.entities;

public class WaitingList {

    private Integer id;
    private String nombre;
    private String profesion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Override
    public String toString() {
        return "WaitingList{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", profesion='" + profesion + '\'' +
                '}';
    }
}