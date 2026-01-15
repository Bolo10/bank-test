# bank-test

- Utilice el siquiente comando para generar BaseDatos.sql

 pg_dump -h localhost -p 5433 -U bankuser -d bankdb  --inserts > BaseDatos.sql   

 incluye los inserts de los casos de uso

 Comando para inicializar el proyecto

docker compose up --build

comando para bajar los contenedores y tambien vaciar la BD

docker compose down -v

Docker compose que utilice 
Docker Compose version v2.28.1