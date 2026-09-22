use sistema_gestion_abil_in4av;

call sp_crear_propiedad("PA-001", "Zona 16, 5a Calle, 18-25, Ciudad de Guatemala", 120000.00, "edificio", 250.00, "Disponible");
call sp_crear_propiedad("PA-002", "Zona 7, 23 Calle 8-17, Ciudad de Guatemala", 850.00, "Casa", 240.00, 'Alquilado');
call sp_crear_propiedad("PA-003", "Zona 1, 5a Avenida 12-45, Ciudad de Guatemala", 950000.00, "oficina", 180.00, "Disponible");
call sp_crear_propiedad("PA-004", "Zona 21, 6a Avenida 20-15, Ciudad de Guatemala", 725000.00, "Local comercial", 95.00, 'Vendido');
call sp_crear_propiedad("PA-005", "Zona 12, 4a Calle 14-63, Ciudad de Guatemala", 8500000.00, "Parque Industrial", 4000.00, "Disponible");

select * from Propiedad;
