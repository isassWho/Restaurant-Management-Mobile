package com.cod.tablayout_demo.entities;

public class Mesa {

    private Integer id;
    private String nombre;
    private String personas;

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

    public String getPersonas() {
        return personas;
    }

    public void setPersonas(String personas) {
        this.personas = personas;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", personas='" + personas + '\'' +
                '}';
    }
}
