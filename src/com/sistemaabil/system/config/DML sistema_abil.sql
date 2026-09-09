#--------------USUARIOS--------------------
call sp_crear_usuarios( "dmartinez-2026166", "123987", "dmartinez-2026214@gmail.com", "Administrador");
call sp_crear_usuarios( "hdiaz-2026214", "543789", "hdiaz-2026214@gmail.com", "Supervisor");
call sp_crear_usuarios( "cluis-2026534", "982365", "cluis2026534@gmail.com", "Cliente");
call sp_crear_usuarios( "ccruz-2026541", "123678", "ccruz-2026541@gmail.com.", "Agente Inmobiliario");

#-------------ADMINISTRADOR-----------------
call sp_leer_usuarios(1);
call sp_leer_usuarios(2);
call sp_leer_usuarios(3);
call sp_leer_usuarios(4);

select * from Usuario;

#-------------AGENTE INMOBILIARIO-------------------
call sp_crear_propiedad("Zona 16, 5a Calle, 18-25, Ciudad de Guatemala", 120000.00, "edificio", 250.00, "Disponible");
call sp_crear_propiedad("Zona 7, 23 Calle 8-17, Ciudad de Guatemala", 850.00, "Casa", 240.00, 'Alquilado');
call sp_crear_propiedad("Zona 1, 5a Avenida 12-45, Ciudad de Guatemala", 950000.00, "oficina", 180.00, "Disponible");
call sp_crear_propiedad("Zona 21, 6a Avenida 20-15, Ciudad de Guatemala", 725000.00, "Local comercial", 95.00, 'Vendido');
call sp_crear_propiedad("Zona 12, 4a Calle 14-63, Ciudad de Guatemala", 8500000.00, "Parque Industrial", 4000.00, "Disponible");

call sp_leer_propiedades();

call sp_actualizar_propiedad(4, "Zona 10, 15 Calle 3-76, Ciudad de Guatemala", 3500000.00, "Edificio", 850.00, 'Disponible');

call sp_eliminar_propiedad(1);

#------------SUPERVISOR----------------------------
call sp_supervisor_leer_propiedades();

call sp_supervisor_propiedades_disponibles();
call sp_supervisor_propiedades_vendidos();
call sp_supervisor_propiedades_alquilados();

call sp_editar_propiedad(6, "Zona 18, 14 Calle 3-47, Ciudad de Guatemala", 500000.00,"Terreno", 550.00,"Vendido");

#-----------CLIENTE------------------------
call sp_cliente_ver_propiedades();