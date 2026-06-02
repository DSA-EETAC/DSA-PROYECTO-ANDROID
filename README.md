# Segundo Mínimo DSA - Ejercicio 3 (Nikita)

## ¿Qué está implementado?
* Se ha creado la nueva actividad `GruposActivity` en Android para listar los equipos disponibles.
* Se ha implementado el diseño visual con `activity_grupos.xml` y `item_grupo.xml`.
* Se ha configurado un `RecyclerView` con su `GruposAdapter` para mostrar la lista de forma dinámica.
* Se han añadido las peticiones de red en `ApiService.java` utilizando Retrofit (GET para listar, POST para unirse).
* En el backend (IntelliJ), se han implementado dos rutas dummy (`/grupos` y `/grupos/{id}/unirse`) que devuelven datos ficticios e imprimen el resultado por consola, tal como pedían los requisitos del examen.

## ¿Qué funciona?
* La navegación desde el menú principal hacia la vista de Grupos.
* La obtención de la lista de grupos mediante Retrofit desde el backend dummy y su correcta visualización.
* El botón de "Unirse", que recupera el nombre de usuario de la sesión actual (`LocalUserManager`) y envía correctamente la petición POST al servidor.
* El backend recibe las peticiones e imprime los logs por consola sin errores.

## ¿Qué queda pendiente?
* La integración real con la base de datos (actualmente funciona con datos dummy según las especificaciones de la prueba).