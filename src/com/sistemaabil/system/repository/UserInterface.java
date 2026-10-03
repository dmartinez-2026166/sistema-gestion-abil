package com.sistemaabil.system.repository;

import com.sistemaabil.system.model.Usuario;

/**
 * Contrato de acceso a datos de usuarios. Solo persiste y consulta:
 * las reglas de negocio (validaciones, hashing, autenticación) viven en UsuarioService.
 */
public interface UserInterface {

    /** Inserta el usuario. Su clave ya debe venir hasheada. */
    boolean crearUsuario(Usuario usuario);

    /** Devuelve el usuario con ese correo o null si no existe. */
    Usuario buscarPorCorreo(String correo);
}
