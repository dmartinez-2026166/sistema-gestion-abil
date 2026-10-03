use sistema_gestion_abil_in4av;

-- por si tu tabla Propiedad todavia no tiene esta columna (no hace nada si ya existe)
alter table Propiedad add column if not exists codigo_interno varchar(20) not null unique;

drop procedure if exists sp_buscar_propiedades;

delimiter $$
create procedure sp_buscar_propiedades(in termino_p varchar(150))
begin
	select id_propiedad, codigo_interno, direccion, precio, tipo_propiedad, area, estado_propiedad
    from Propiedad
    where codigo_interno like concat('%', termino_p, '%')
       or direccion like concat('%', termino_p, '%');
end$$
delimiter ;
