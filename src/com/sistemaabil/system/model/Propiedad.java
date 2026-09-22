package com.sistemaabil.system.model;

public class Propiedad {

    private int idPropiedad;
    private String codigoInterno;
    private String direccion;
    private double precio;
    private String tipoPropiedad;
    private double area;
    private String estadoPropiedad;

    public Propiedad() {
    }

    public Propiedad(int idPropiedad, String codigoInterno, String direccion, double precio,
            String tipoPropiedad, double area, String estadoPropiedad) {
        this.idPropiedad = idPropiedad;
        this.codigoInterno = codigoInterno;
        this.direccion = direccion;
        this.precio = precio;
        this.tipoPropiedad = tipoPropiedad;
        this.area = area;
        this.estadoPropiedad = estadoPropiedad;
    }

    public int getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(String tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getEstadoPropiedad() {
        return estadoPropiedad;
    }

    public void setEstadoPropiedad(String estadoPropiedad) {
        this.estadoPropiedad = estadoPropiedad;
    }
}
