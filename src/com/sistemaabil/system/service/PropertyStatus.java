package com.sistemaabil.system.service;

/** Resultado de crear o actualizar una propiedad (PropiedadService). */
public enum PropertyStatus {
    PROPERTY_CREATED,
    PROPERTY_UPDATED,
    ERROR_PROPERTY_CREATE,
    ERROR_PROPERTY_UPDATE,
    EMPTY_FIELDS,
    INVALID_VALUES;
}
