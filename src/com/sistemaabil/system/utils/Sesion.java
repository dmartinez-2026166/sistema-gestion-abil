package com.sistemaabil.system.utils;

import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.model.Usuario;

public class Sesion {

    private static Usuario usuarioActual;
    private static Propiedad propiedadEnEdicion;

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Propiedad getPropiedadEnEdicion() {
        return propiedadEnEdicion;
    }

    public static void setPropiedadEnEdicion(Propiedad propiedad) {
        propiedadEnEdicion = propiedad;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
        propiedadEnEdicion = null;
    }
}
