package com.sistemaabil.system.service;

/** Resultado de iniciar sesión (UsuarioService.iniciarSesion). */
public enum LoginStatus {
    LOGIN_OK,
    EMPTY_FIELDS,
    INVALID_CREDENTIALS,
    UNKNOWN_ROLE;
}
