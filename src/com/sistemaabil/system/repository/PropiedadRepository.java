package com.sistemaabil.system.repository;

import com.sistemaabil.system.config.ConexionDB;
import com.sistemaabil.system.model.EstadoPropiedad;
import com.sistemaabil.system.model.Propiedad;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropiedadRepository implements PropiedadInterface {

    @Override
    public List<Propiedad> listarTodas() {
        return ejecutarConsultaSinParametros("sp_leer_propiedades");
    }

    @Override
    public List<Propiedad> listarPorEstado(EstadoPropiedad estado) {
        String procedimiento = switch (estado) {
            case DISPONIBLE -> "sp_supervisor_propiedades_disponibles";
            case VENDIDO -> "sp_supervisor_propiedades_vendidos";
            case ALQUILADO -> "sp_supervisor_propiedades_alquilados";
        };

        return ejecutarConsultaSinParametros(procedimiento);
    }

    @Override
    public List<Propiedad> buscarPorCodigoODireccion(String termino) {
        List<Propiedad> resultado = new ArrayList<>();
        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call sp_buscar_propiedades(?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, termino == null ? "" : termino);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultado.add(mapearFila(resultSet));
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error al buscar propiedades: " + sqlException.getMessage());
        }

        return resultado;
    }

    @Override
    public boolean crear(Propiedad propiedad) {
        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call sp_crear_propiedad(?, ?, ?, ?, ?, ?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setString(1, propiedad.getCodigoInterno());
            callableStatement.setString(2, propiedad.getDireccion());
            callableStatement.setDouble(3, propiedad.getPrecio());
            callableStatement.setString(4, propiedad.getTipoPropiedad());
            callableStatement.setDouble(5, propiedad.getArea());
            callableStatement.setString(6, etiquetaDe(propiedad.getEstadoPropiedad()));
            callableStatement.execute();
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Error al crear propiedad: " + sqlException.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Propiedad propiedad) {
        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call sp_actualizar_propiedad(?, ?, ?, ?, ?, ?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setInt(1, propiedad.getIdPropiedad());
            callableStatement.setString(2, propiedad.getDireccion());
            callableStatement.setDouble(3, propiedad.getPrecio());
            callableStatement.setString(4, propiedad.getTipoPropiedad());
            callableStatement.setDouble(5, propiedad.getArea());
            callableStatement.setString(6, etiquetaDe(propiedad.getEstadoPropiedad()));
            callableStatement.execute();
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Error al actualizar propiedad: " + sqlException.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idPropiedad) {
        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call sp_eliminar_propiedad(?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setInt(1, idPropiedad);
            callableStatement.execute();
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Error al eliminar propiedad: " + sqlException.getMessage());
            return false;
        }
    }

    private List<Propiedad> ejecutarConsultaSinParametros(String nombreProcedimiento) {
        List<Propiedad> resultado = new ArrayList<>();
        Connection connection = ConexionDB.getInstanciaConexionDB().getConnection();
        String sql = "{call " + nombreProcedimiento + "()}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultado.add(mapearFila(resultSet));
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error al listar propiedades: " + sqlException.getMessage());
        }

        return resultado;
    }

    private Propiedad mapearFila(ResultSet resultSet) throws SQLException {
        return new Propiedad(
                resultSet.getInt("id_propiedad"),
                resultSet.getString("codigo_interno"),
                resultSet.getString("direccion"),
                resultSet.getDouble("precio"),
                resultSet.getString("tipo_propiedad"),
                resultSet.getDouble("area"),
                EstadoPropiedad.fromEtiqueta(resultSet.getString("estado_propiedad"))
        );
    }

    private String etiquetaDe(EstadoPropiedad estado) {
        return estado == null ? null : estado.getEtiqueta();
    }
}
