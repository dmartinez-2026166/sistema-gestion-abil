package com.sistemaabil.system.repository;

import com.sistemaabil.system.model.Usuario;

public interface UserInterface {

    boolean crearUsuario(String usuario, String claveTextoPlano, String correo, String rol);

    Usuario buscarPorCorreo(String correo);

    Usuario autenticar(String correo, String claveTextoPlano);
}
