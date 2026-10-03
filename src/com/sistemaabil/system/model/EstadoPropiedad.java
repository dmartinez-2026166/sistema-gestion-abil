package com.sistemaabil.system.model;

public enum EstadoPropiedad {
    DISPONIBLE("Disponible"),
    VENDIDO("Vendido"),
    ALQUILADO("Alquilado");

    private final String etiqueta;

    EstadoPropiedad(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /** Convierte el texto de la base de datos a EstadoPropiedad; devuelve null si no coincide. */
    public static EstadoPropiedad fromEtiqueta(String etiqueta) {
        for (EstadoPropiedad estado : values()) {
            if (estado.etiqueta.equals(etiqueta)) {
                return estado;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
