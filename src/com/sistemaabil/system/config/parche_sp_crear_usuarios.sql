-- Ejecutar esto en la base sistema_gestion_abil_in4av para corregir
-- sp_crear_usuarios sin tener que recrear toda la base (borra y vuelve a
-- crear solo este procedimiento).

use sistema_gestion_abil_in4av;

drop procedure if exists sp_crear_usuarios;

delimiter $$
create procedure sp_crear_usuarios(in usuario_p varchar(50),
								   in clave_p varchar(60),
                                   in correo_p varchar(100),
                                   in rol_p varchar(30))
begin
	insert into Usuario (usuario, clave, correo, rol)
		values(usuario_p, clave_p, correo_p, rol_p);
end$$
delimiter ;
