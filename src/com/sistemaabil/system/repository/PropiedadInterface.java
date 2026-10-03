package com.sistemaabil.system.repository;

import com.sistemaabil.system.model.EstadoPropiedad;
import com.sistemaabil.system.model.Propiedad;
import java.util.List;

/**
 * Contrato de acceso a datos de propiedades. Solo persiste y consulta:
 * las reglas de negocio viven en PropiedadService.
 */
public interface PropiedadInterface {

    List<Propiedad> listarTodas();

    List<Propiedad> listarPorEstado(EstadoPropiedad estado);

    List<Propiedad> buscarPorCodigoODireccion(String termino);

    boolean crear(Propiedad propiedad);

    boolean actualizar(Propiedad propiedad);

    boolean eliminar(int idPropiedad);
}
