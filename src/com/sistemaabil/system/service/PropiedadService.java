package com.sistemaabil.system.service;

import com.sistemaabil.system.model.EstadoPropiedad;
import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.repository.PropiedadInterface;
import com.sistemaabil.system.repository.PropiedadRepository;
import com.sistemaabil.system.utils.Sesion;
import java.util.List;

/**
 * Lógica de negocio de propiedades: validaciones, filtros y estado de edición.
 * Los controladores solo llaman a esta clase, nunca al repositorio.
 */
public class PropiedadService {

    private static final List<String> TIPOS = List.of("Casa", "Apartamento", "Terreno", "Local");

    private final PropiedadInterface propiedadRepository;

    public PropiedadService() {
        this(new PropiedadRepository());
    }

    public PropiedadService(PropiedadInterface propiedadRepository) {
        this.propiedadRepository = propiedadRepository;
    }

    public List<String> getTipos() {
        return TIPOS;
    }

    /** Lista las propiedades; con estado null devuelve todas. */
    public List<Propiedad> listar(EstadoPropiedad estado) {
        return estado == null
                ? propiedadRepository.listarTodas()
                : propiedadRepository.listarPorEstado(estado);
    }

    /** Busca por código interno o dirección. Un término vacío devuelve todas. */
    public List<Propiedad> buscar(String termino) {
        return propiedadRepository.buscarPorCodigoODireccion(termino == null ? "" : termino.trim());
    }

    public PropertyStatus crear(String codigo, String direccion, String tipo,
            Double area, Double precio, EstadoPropiedad estado) {
        if (hayCamposVacios(codigo, direccion, tipo, area, precio, estado)) {
            return PropertyStatus.EMPTY_FIELDS;
        }

        if (hayValoresInvalidos(area, precio)) {
            return PropertyStatus.INVALID_VALUES;
        }

        Propiedad nueva = new Propiedad(0, codigo.trim(), direccion.trim(), precio, tipo, area, estado);

        return propiedadRepository.crear(nueva)
                ? PropertyStatus.PROPERTY_CREATED
                : PropertyStatus.ERROR_PROPERTY_CREATE;
    }

    /** Actualiza una propiedad existente. El código interno no se modifica. */
    public PropertyStatus actualizar(Propiedad original, String direccion, String tipo,
            Double area, Double precio, EstadoPropiedad estado) {
        if (hayCamposVacios(original.getCodigoInterno(), direccion, tipo, area, precio, estado)) {
            return PropertyStatus.EMPTY_FIELDS;
        }

        if (hayValoresInvalidos(area, precio)) {
            return PropertyStatus.INVALID_VALUES;
        }

        Propiedad actualizada = new Propiedad(original.getIdPropiedad(), original.getCodigoInterno(),
                direccion.trim(), precio, tipo, area, estado);

        return propiedadRepository.actualizar(actualizada)
                ? PropertyStatus.PROPERTY_UPDATED
                : PropertyStatus.ERROR_PROPERTY_UPDATE;
    }

    public boolean eliminar(Propiedad propiedad) {
        return propiedadRepository.eliminar(propiedad.getIdPropiedad());
    }

    // ---- propiedad que se está editando (estado compartido entre pantallas) ----

    public void iniciarEdicion(Propiedad propiedad) {
        Sesion.setPropiedadEnEdicion(propiedad);
    }

    public Propiedad getPropiedadEnEdicion() {
        return Sesion.getPropiedadEnEdicion();
    }

    public void finalizarEdicion() {
        Sesion.setPropiedadEnEdicion(null);
    }

    // ---- validaciones ----

    private boolean hayCamposVacios(String codigo, String direccion, String tipo,
            Double area, Double precio, EstadoPropiedad estado) {
        return estaVacio(codigo) || estaVacio(direccion) || estaVacio(tipo)
                || area == null || precio == null || estado == null;
    }

    /** Área y precio deben ser números finitos mayores que cero. */
    private boolean hayValoresInvalidos(Double area, Double precio) {
        return !esPositivoYFinito(area) || !esPositivoYFinito(precio);
    }

    private boolean esPositivoYFinito(Double valor) {
        return !valor.isNaN() && !valor.isInfinite() && valor > 0;
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
