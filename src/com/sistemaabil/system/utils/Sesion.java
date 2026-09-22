package com.sistemaabil.system.utils;

import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.model.Usuario;

/**
 * Guarda datos simples que varias vistas necesitan compartir durante la
 * sesion: quien inicio sesion, y (si aplica) que propiedad se esta editando.
 * No es una base de datos ni nada persistente, solo vive mientras la
 * aplicacion esta abierta.
 */
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
