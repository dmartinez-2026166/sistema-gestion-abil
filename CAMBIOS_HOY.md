# Estado del proyecto para la entrega

## Antes de compilar (en cada máquina del equipo)
1. Agregar al Classpath: `bcrypt-0.10.2.jar`, `bytes-1.6.1.jar` y el conector
   JDBC de MySQL.
2. `Enviroment.java` está en `.gitignore` — cada quien pone ahí su propio
   usuario/clave/nombre de base de datos de MySQL local.
3. Correr `config/ddl.sql` completo en MySQL (crea la base desde cero con
   todos los procedimientos ya corregidos).

## Roles y qué ve cada uno al iniciar sesión
Según el documento de propuesta (Administrador, Agente Inmobiliario,
Supervisor, Cliente):

- **Administrador** → Panel de Control (`PanelControl.fxml` /
  `PanelControlController`): tabla completa del inventario, filtros por
  estado (Propiedades / Disponibles / Vendidas / Alquiladas), buscador por
  código o dirección, y botones **Nueva propiedad**, **VER**, **EDITAR**,
  **ELIMINAR** sobre la propiedad seleccionada. Es el único rol con acceso
  a esta vista, tal como se pidió.
- **Cliente** → `BusquedaPropiedadesView` / `BusquedaPropiedadesController`:
  solo un buscador (código o dirección) y una tabla de resultados. Sin
  ningún otro botón, como se pidió.
- **Agente Inmobiliario** → misma vista que el Cliente, pero con un botón
  extra **NUEVA PROPIEDAD** (porque según el documento su función es
  registrar y actualizar propiedades). *Supuesto: no estaba confirmado con
  el equipo, revisen si quieren agregarle también "editar" desde ahí.*
- **Supervisor** → misma vista que el Cliente (solo consulta), porque su
  función según el documento es "consultar y supervisar" sin modificar
  nada. *Mismo supuesto que arriba: no estaba confirmado.*

## Flujo completo que ya funciona de punta a punta
1. Login con correo/clave contra la base de datos (BCrypt).
2. Registro de cuenta nueva (`RegisterUserView`), con selección de rol.
3. Según el rol, entra a la vista que le corresponde (ver arriba).
4. Desde el Panel de Control (Administrador) o desde el botón "Nueva
   Propiedad" (Agente): formulario `RegisterView` / `RegisterController`
   para **crear** una propiedad, y el mismo formulario sirve para
   **editar** una ya existente (llega pre-llenado si se entra desde
   "EDITAR").
5. Eliminar propiedad con confirmación (solo Administrador).
6. Cerrar sesión desde cualquiera de las dos vistas post-login, vuelve al
   login y limpia la sesión.

## Base de datos — todo lo corregido en `ddl.sql`
- `clave` de `Usuario` ampliada a `VARCHAR(60)` (hash de BCrypt).
- `codigo_interno` agregado a `Propiedad`.
- `sp_crear_usuarios`: parámetro `clave_p` ampliado a `VARCHAR(60)`, y se
  quitó la referencia inválida a `id_usuario` en el `insert` (era
  auto_increment, no se pasa como valor).
- Nuevos SP: `sp_buscar_usuario_por_correo`, `sp_buscar_propiedades`.
- Los SP de propiedades (`sp_crear_propiedad`, `sp_actualizar_propiedad`,
  `sp_eliminar_propiedad`, `sp_leer_propiedades`,
  `sp_supervisor_propiedades_*`) ya estaban en el `ddl.sql` original y
  ahora sí se usan desde `PropiedadRepository`.

## Código eliminado por quedar redundante
- `PropiedadesView.fxml` y `PropiedadesController.java` — su tabla no
  coincidía con el controller (bug que habíamos detectado antes) y su
  función quedó cubierta por `PanelControl`. Se borraron para no dejar
  código muerto.

## Lo que queda pendiente / a criterio del equipo
- Confirmar si Agente Inmobiliario y Supervisor deben ver algo distinto a
  lo que se les asignó hoy (fue una decisión razonable pero no confirmada
  con el equipo completo).
- `sp_editar_propiedad` quedó duplicado con `sp_actualizar_propiedad` (son
  idénticos) — no se tocó porque no rompe nada, pero se puede limpiar.
- No hay pantalla dedicada para que el Administrador gestione usuarios
  (crear/editar/eliminar cuentas) — solo el registro de cuenta nueva desde
  el login.
