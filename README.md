# bank-test

Este repositorio contiene la solución del ejercicio técnico, incluyendo backend, frontend y base de datos, orquestados mediante Docker Compose.

---

## Generación del script de base de datos

Para generar el archivo **BaseDatos.sql** con la estructura de la base de datos (sin datos), se utilizó el siguiente comando:

```bash
pg_dump -h localhost -p 5433 -U bankuser -d bankdb --schema-only > BaseDatos.sql
```

El script **no incluye inserts** de los casos de uso, ya que estos se generan a través de las peticiones realizadas con Postman.

---

## Inicialización del proyecto

Para levantar el proyecto completo (frontend, backend y base de datos) ejecutar:

```bash
docker compose up --build
```

---

## Detener contenedores y limpiar la base de datos

Para detener los contenedores y eliminar los volúmenes asociados (incluyendo los datos de la base de datos):

```bash
docker compose down -v
```

---

## Herramientas utilizadas

- Docker Compose version v2.28.1  
- Postman v10.x.x  