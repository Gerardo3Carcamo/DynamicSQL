# DynamicSQL

Este proyecto contiene una librería Java para facilitar la conexión a bases de datos y la ejecución de comandos SQL de forma dinámica. La librería está compuesta por dos clases principales:

1. **ConnectionDatabases**: Proporciona métodos para conectarse a bases de datos como Oracle, MySQL, MariaDB y SQL Server.
2. **SQLSentences**: Permite realizar operaciones de CRUD (Crear, Leer, Actualizar, Eliminar) de manera dinámica sobre las tablas de la base de datos utilizando sentencias SQL generadas de manera flexible.

## Características

- **Conexiones a múltiples bases de datos**:
  - Oracle
  - MySQL
  - MariaDB
  - SQL Server

- **Sentencias SQL Dinámicas**: 
  - Soporte para operaciones CRUD.
  - Inserción dinámica de datos.
  - Actualización y eliminación flexible con condiciones.
 
## Uso

### Conexiones a Bases de Datos

La clase `ConnectionDatabases` te permite establecer conexiones a diferentes bases de datos. Aquí hay un ejemplo de cómo conectar a una base de datos MySQL:

```java
ConnectionDatabases connection = new ConnectionDatabases();
Connection cn = connection.getMySQLConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "usuario", "contraseña");
```
#### Métodos disponibles para obtener la conexión a las diferentes bases de datos:
```java
Connection cn = connection.getOracleConnection("jdbc:oracle:thin:@localhost:1521:orcl", "usuario", "contraseña");
```
```java
Connection cn = connection.getMySQLConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "usuario", "contraseña");
```
```java
Connection cn = connection.getMariaDbConnection("jdbc:mariadb://localhost:3306/mi_base_de_datos", "usuario", "contraseña");
```
```java
Connection cn = connection.getSqlServerConnection("jdbc:sqlserver://localhost:1433;databaseName=mi_base_de_datos", "usuario", "contraseña");
```

### Ejecución de sentencias SQL
La clase `SQLSentences` permite realizar operaciones de CRUD en la base de datos utilizando clases genéricas. A continuación, un ejemplo de cómo obtener una lista de objetos de la base de datos.

```java
SQLSentences<MiModelo> sqlService = new SQLSentences<>(MiModelo.class);
List<MiModelo> resultados = sqlService.DynamicGetListMethod("SELECT * FROM MiTabla", cn);
```
#### Métodos disponibles
`DynamicGetListMethod(String query, Connection connection)`
```java
String query = "SELECT * FROM empleados WHERE salario > ?";
Object[] params = {3000};
List<Empleado> empleados = sqlService.DynamicGetListMethod(query, cn, params);
```
`DynamicInsertMethod(String table, String optionalColumns, Object[] objects, Connection cn)`
```java
SQLSentences<Empleado> sqlService = new SQLSentences<>(Empleado.class);
Object[] datos = {"Juan", "Pérez", 3500};
boolean insertado = sqlService.DynamicInsertMethod("empleados", "nombre, apellido, salario", datos, cn);
if (insertado) {
    System.out.println("Empleado insertado correctamente.");
}
```
`DynamicUpdateMethod(String table, String[] columns, Object[] objectsUpdate, Object[] objectsConditionals, Connection cn`
```java
String[] columnas = {"salario"};
Object[] valores = {4000};
Object[] condiciones = {"id = 5"};
boolean actualizado = sqlService.DynamicUpdateMethod("empleados", columnas, valores, condiciones, cn);
if (actualizado) {
    System.out.println("Empleado actualizado correctamente.");
}
```
`DynamicDeleteMethod(String table, String[] columns, Object[] objectsConditionals, Connection cn)`
```java
String[] columnas = {"id"};
Object[] condiciones = {5};
boolean eliminado = sqlService.DynamicDeleteMethod("empleados", columnas, condiciones, cn);
if (eliminado) {
    System.out.println("Empleado eliminado correctamente.");
}
```
## Requisitos
- Java 8 o superior.
- Dependencias JDBC para las bases de datos que desees utilizar (Oracle, MySQL, MariaDB, SQL Server).
