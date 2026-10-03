package com.sistemaabil.system.repository;

import com.sistemaabil.system.config.ConexionDB;
import com.sistemaabil.system.model.Rol;
import com.sistemaabil.system.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements UserInterface {

    @Override
    public boolean crearUsuario(Usuario usuario) {
        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call sp_crear_usuarios(?, ?, ?, ?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, usuario.getUsuario());
            callableStatement.setString(2, usuario.getClave());
            callableStatement.setString(3, usuario.getCorreo());
            callableStatement.setString(4, usuario.getRol().getEtiqueta());
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
                            Rol.fromEtiqueta(resultSet.getString("rol"))
                    );
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error al buscar usuario por correo: " + sqlException.getMessage());
        }

        return null;
    }
}
