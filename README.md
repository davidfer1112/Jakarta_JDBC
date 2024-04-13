# Jakarta JDBC

Este repositorio contiene una implementación de ejemplo para interactuar con una base de datos utilizando JDBC en un proyecto Jakarta EE.

## Español

### Descripción

El proyecto demuestra cómo realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en una base de datos utilizando el patrón de diseño Repository en Java con JDBC. Se utiliza una base de datos en memoria (H2) para simplificar la configuración y ejecución del proyecto.

### Cómo funciona

El proyecto define una interfaz `Repository<T>` que especifica los métodos para las operaciones CRUD. La implementación específica para manejar productos se realiza en `ProductoRepositoryImpl`, que implementa `Repository<Producto>`.

#### Operaciones

- **Listar productos**: Recupera todos los productos de la base de datos.
- **Consultar por ID**: Busca un producto por su ID.
- **Guardar/Actualizar producto**: Inserta un nuevo producto o actualiza uno existente.
- **Eliminar producto**: Elimina un producto por su ID.

## Base de Datos

<div align="center">
  <img src="https://github.com/davidfer1112/Jakarta_JDBC/assets/90224781/ab6415e8-7393-4ea9-bc7e-915f1da3603a" height="400" alt="Modelo base de datos"  />
</div>






### Ejecución

Para ejecutar el proyecto, es necesario tener JDK 8 o superior y Maven configurado en tu entorno. Clona el repositorio y ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn clean install exec:java

