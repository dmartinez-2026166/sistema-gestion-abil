package com.sistemaabil.system.repository;

import com.sistemaabil.system.model.Usuario;

public interface UserInterface {

    /**
     * Crea un usuario nuevo. La clave que llega aqui debe venir en texto
     * plano; el repositorio es responsable de aplicar el hash antes de
     * guardarla.
     */
    boolean crearUsuario(String usuario, String claveTextoPlano, String correo, String rol);

    /**
     * Busca un usuario por correo. Devuelve null si no existe.
     */
    Usuario buscarPorCorreo(String correo);

    /**
     * Verifica que la clave en texto plano coincida con el hash guardado
     * para ese correo. Devuelve el Usuario si las credenciales son
     * correctas, o null si el correo no existe o la clave no coincide.
     */
    Usuario autenticar(String correo, String claveTextoPlano);
}
