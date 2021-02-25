package com.cod.tablayout_demo.entities;

public class Comanda {

    private Integer id;
    private String propietario;
    private String personas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getPersonas() {
        return personas;
    }

    public void setPersonas(String personas) {
        this.personas = personas;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", propietario='" + propietario + '\'' +
                ", personas='" + personas + '\'' +
                '}';
    }
}
