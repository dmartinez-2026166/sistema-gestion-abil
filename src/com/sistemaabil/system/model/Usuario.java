package com.sistemaabil.system.model;

public class Usuario extends Model{

    private int idUsuario;
    private String usuario;
    private String clave;
    private String correo;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(int idUsuario, String usuario, String clave, String correo, Rol rol) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
        this.rol = rol;
    }

    @Override
    public int getId() {
        return idUsuario;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
