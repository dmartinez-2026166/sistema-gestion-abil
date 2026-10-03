package com.sistemaabil.system.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.sistemaabil.system.model.Rol;
import com.sistemaabil.system.model.Usuario;
import com.sistemaabil.system.repository.UserInterface;
import com.sistemaabil.system.repository.UserRepository;
import com.sistemaabil.system.utils.Sesion;

/**
 * Lógica de negocio de usuarios y sesión: validaciones de registro, hashing de claves,
 * autenticación y permisos del usuario en sesión. Los controladores solo llaman a esta clase.
 */
public class UsuarioService {

    private static final int COSTO_BCRYPT = 12;
    private static final int LONGITUD_MINIMA_CLAVE = 6;

    private final UserInterface userRepository;

    public UsuarioService() {
        this(new UserRepository());
    }

    public UsuarioService(UserInterface userRepository) {
        this.userRepository = userRepository;
    }

    /** Autentica al usuario y, si es válido, lo deja como usuario actual de la sesión. */
    public LoginStatus iniciarSesion(String correo, String clave) {
        if (estaVacio(correo) || estaVacio(clave)) {
            return LoginStatus.EMPTY_FIELDS;
        }

        Usuario usuario = userRepository.buscarPorCorreo(correo.trim());

        if (usuario == null || !claveCoincide(clave, usuario.getClave())) {
            return LoginStatus.INVALID_CREDENTIALS;
        }

        if (usuario.getRol() == null) {
            return LoginStatus.UNKNOWN_ROLE;
        }

        Sesion.setUsuarioActual(usuario);
        return LoginStatus.LOGIN_OK;
    }

    /** Valida los datos, hashea la clave y registra al usuario. */
    public UserStatus registrarUsuario(String usuario, String correo, String clave,
            String confirmarClave, Rol rol) {
        if (estaVacio(usuario) || estaVacio(correo) || estaVacio(clave) || rol == null) {
            return UserStatus.EMPTY_FIELDS;
        }

        if (!clave.equals(confirmarClave)) {
            return UserStatus.PASSWORD_MISMATCH;
        }

        if (clave.length() < LONGITUD_MINIMA_CLAVE) {
            return UserStatus.PASSWORD_TOO_SHORT;
        }

        String correoLimpio = correo.trim();

        if (userRepository.buscarPorCorreo(correoLimpio) != null) {
            return UserStatus.USER_EXISTS;
        }

        String claveHasheada = BCrypt.withDefaults().hashToString(COSTO_BCRYPT, clave.toCharArray());
        Usuario nuevo = new Usuario(0, usuario.trim(), claveHasheada, correoLimpio, rol);

        return userRepository.crearUsuario(nuevo) ? UserStatus.USER_CREATED : UserStatus.ERROR_USER_CREATE;
    }

    public Usuario getUsuarioActual() {
        return Sesion.getUsuarioActual();
    }

    public Rol getRolActual() {
        Usuario usuario = Sesion.getUsuarioActual();
        return usuario == null ? null : usuario.getRol();
    }

    public boolean puedeRegistrarPropiedades() {
        Rol rol = getRolActual();
        return rol != null && rol.puedeRegistrarPropiedades();
    }

    public void cerrarSesion() {
        Sesion.cerrarSesion();
    }

    private boolean claveCoincide(String claveTextoPlano, String claveHasheada) {
        return BCrypt.verifyer()
                .verify(claveTextoPlano.toCharArray(), claveHasheada)
                .verified;
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
