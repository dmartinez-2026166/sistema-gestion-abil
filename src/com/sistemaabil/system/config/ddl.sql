drop database if exists sistema_gestion_abil_in4av;
create database sistema_gestion_abil_in4av;
use sistema_gestion_abil_in4av;

create table Usuario (
id_usuario int auto_increment primary key,
usuario varchar(50) not null,
clave varchar(36) not null,
correo varchar(100) not null,
rol varchar(30) not null
);

create table Administrador(
id_usuario int,
foreign key(id_usuario)
	references Usuario(id_usuario)
);

create table Supervisor(
id_usuario int primary key,
foreign key (id_usuario)
	references Usuario(id_usuario)
);

create table Cliente(
id_usuario  int primary key,
foreign key (id_usuario)
	references Usuario(id_usuario)
);

create table Agente_inmobiliario(
id_usuario int primary key,
foreign key (id_usuario)
	references Usuario(id_usuario)
);

create table Propiedad(
id_propiedad int auto_increment primary key,
direccion varchar(150),
precio decimal(10,2),
tipo_propiedad varchar(50),
area decimal(10,2),
estado_propiedad varchar(100)
);

#-------------USUARIOS--------------------------------------------------
delimiter $$
create procedure sp_crear_usuarios(in usuario_p varchar(50),
								   in clave_p varchar(36),
                                   in correo_p varchar(100),
                                   in rol_p varchar(30))
begin
	insert into Usuario (id_usuario, usuario, clave, correo, rol)
		values(id_usuario, usuario_p, clave_p, correo_p, rol_p);
        
end$$
delimiter ;

#-------------------ADMINISTRADOR-------------------------------------

delimiter $$
create procedure sp_leer_usuarios(in id_usuario_p int)

begin
	select * from Usuario
    where id_usuario = id_usuario_p;
end$$
delimiter ;

#----------------------AGENTE INMOBILIARIO--------------------------------------

delimiter $$
create procedure sp_crear_propiedad(in direccion_p varchar(150),
									in precio_p decimal(10,2),
									in tipo_p varchar(50),
									in area_p decimal(10,2),
									in estado_p varchar(100))

begin
	insert into Propiedad(direccion, precio, tipo_propiedad, area, estado_propiedad)
		values(direccion_p, precio_p, tipo_p, area_p, estado_p);

end$$
delimiter ;

delimiter $$
create procedure sp_leer_propiedades()
begin

    select * from Propiedad;

end$$
delimiter ;

delimiter $$
create procedure sp_actualizar_propiedad(in id_propiedad_p int,
										 in direccion_p varchar(150),
										 in precio_p decimal(10,2),
										 in tipo_p varchar(50),
										 in area_p decimal(10,2),
										 in estado_p varchar(100))
begin
    update Propiedad
	set	direccion = direccion_p,
        precio = precio_p,
        tipo_propiedad = tipo_p,
        area = area_p,
        estado_propiedad = estado_p
    where id_propiedad = id_propiedad_p;

end$$
delimiter ;

delimiter $$
create procedure sp_eliminar_propiedad(in id_propiedad_p int)

begin

    delete from Propiedad
    where id_propiedad = id_propiedad_p;

end$$
delimiter ;

#--------------------------SUPERVISOR-----------------------------------
delimiter $$
create procedure sp_supervisor_leer_propiedades()
begin
    select*from Propiedad;

end$$
delimiter ;

delimiter $$
create procedure sp_supervisor_propiedades_disponibles()

begin
	select*from Propiedad
    where estado_propiedad = 'Disponible';

end$$
delimiter ;

delimiter $$
create procedure sp_supervisor_propiedades_vendidos()

begin
	select*from Propiedad
    where estado_propiedad = 'Vendido';

end$$
delimiter ;

delimiter $$
create procedure sp_supervisor_propiedades_alquilados()

begin
	select*from Propiedad
    where estado_propiedad = 'Alquilado';

end$$
delimiter ;

delimiter $$
create procedure sp_editar_propiedad(in id_propiedad_p int,
									 in direccion_p varchar(150),
									 in precio_p decimal(10,2),
									 in tipo_p varchar(50),
									 in area_p decimal(10,2),
									 in estado_p varchar(100))
begin
    update Propiedad
	set	direccion = direccion_p,
        precio = precio_p,
        tipo_propiedad = tipo_p,
        area = area_p,
        estado_propiedad = estado_p
    where id_propiedad = id_propiedad_p;

end$$
delimiter ;

#-------------------CLIENTE-----------------------------

delimiter $$
create procedure sp_cliente_ver_propiedades()
begin
	select id_propiedad, direccion, precio, tipo_propiedad, area,
        estado_propiedad
    from Propiedad
    where estado_propiedad = 'Disponible';

end$$
delimiter ;