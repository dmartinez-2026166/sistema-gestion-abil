package com.sistemaabil.system.repository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.sistemaabil.system.config.ConexionDB;
import com.sistemaabil.system.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserInterface {

    @Override
    public boolean crearUsuario(String usuario, String claveTextoPlano, String correo, String rol) {
        if (usuario == null || usuario.isBlank()
                || claveTextoPlano == null || claveTextoPlano.isBlank()
                || correo == null || correo.isBlank()
                || rol == null || rol.isBlank()) {
            return false;
        }

        if (buscarPorCorreo(correo) != null) {
            return false;
        }

        String claveHasheada = BCrypt.withDefaults().hashToString(12, claveTextoPlano.toCharArray());

        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call sp_crear_usuarios(?, ?, ?, ?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, usuario);
            callableStatement.setString(2, claveHasheada);
            callableStatement.setString(3, correo);
            callableStatement.setString(4, rol);
            callableStatement.execute();
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Error al crear usuario: " + sqlException.getMessage());
            return false;
        }
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call sp_buscar_usuario_por_correo(?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, correo);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Usuario(
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("usuario"),
                            resultSet.getString("clave"),
                            resultSet.getString("correo"),
                            resultSet.getString("rol")
                    );
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error al buscar usuario por correo: " + sqlException.getMessage());
        }

        return null;
    }

    @Override
    public Usuario autenticar(String correo, String claveTextoPlano) {
        Usuario usuario = buscarPorCorreo(correo);

        if (usuario == null) {
            return null;
        }

        boolean claveValida = BCrypt.verifyer()
                .verify(claveTextoPlano.toCharArray(), usuario.getClave())
                .verified;

        return claveValida ? usuario : null;
    }
}
