package com.sistemaabil.system.model;

/**
 * Roles del sistema. La etiqueta es el texto tal como se guarda en la
 * columna Usuario.rol de la base de datos y como se muestra en pantalla.
 */
public enum Rol {
    ADMINISTRADOR("Administrador"),
    SUPERVISOR("Supervisor"),
    AGENTE_INMOBILIARIO("Agente Inmobiliario"),
    CLIENTE("Cliente");

    private final String etiqueta;

    Rol(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /** Regla de negocio: solo estos roles pueden registrar propiedades nuevas. */
    public boolean puedeRegistrarPropiedades() {
        return this == ADMINISTRADOR || this == AGENTE_INMOBILIARIO;
    }

    /** Convierte el texto de la base de datos a Rol; devuelve null si no coincide con ninguno. */
    public static Rol fromEtiqueta(String etiqueta) {
        for (Rol rol : values()) {
            if (rol.etiqueta.equals(etiqueta)) {
                return rol;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
