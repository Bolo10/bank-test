# Backend

El proyecto backend fue generado utilizando **Spring Boot Initializer**, lo que permitió contar con una estructura base estandarizada y alineada a las buenas prácticas del ecosistema Spring.

La aplicación sigue una **arquitectura en capas**, separando claramente las responsabilidades en:

- Controller
- Service
- Repository
- Entity
- DTO
- Exception handling

Esta separación facilita el mantenimiento, la escalabilidad y la implementación de pruebas unitarias.

---

## Ejecución de pruebas

### Ejecutar todos los tests

Para ejecutar la totalidad de las pruebas unitarias del proyecto:

```bash
./mvnw test
```

### Ejecutar un test específico

Para ejecutar una clase de test de manera individual (por ejemplo `CuentaControllerTest`):

```bash
./mvnw -Dtest=CuentaControllerTest test
```
