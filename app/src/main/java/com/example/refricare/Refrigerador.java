package com.example.refricare;

import java.io.Serializable;

public class Refrigerador implements Serializable {

    String marca;
    String modelo;
    int tipo;
    double latitud;
    double longitud;

    public Refrigerador( String marca, String modelo, int tipo, double latitud, double longitud){
        this.marca= marca;
        this.modelo= marca;
        this.tipo= tipo;
        this.latitud= latitud;
        this.longitud= longitud;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
