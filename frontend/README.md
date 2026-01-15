# Frontend - Bank

El proyecto frontend está desarrollado con **Angular**, siguiendo una organización modular que separa responsabilidades por dominio para una mejor mantenibilidad y escalabilidad de la aplicación.

---

## Ejecución del proyecto

### Esta ya es ejecutada en el docker-compose.yml pero para ejecutar en modo local

### Ejecutar el frontend en modo local

Para iniciar la aplicación en entorno de desarrollo:

```bash
npm run start
```

La aplicación estará disponible en el navegador según la configuración por defecto de Angular.

---

## Ejecución de pruebas

### Ejecutar todos los tests

Para ejecutar la totalidad de las pruebas unitarias del frontend:

```bash
npm run test
```

---

### Ejecutar un test específico

Para ejecutar un archivo de prueba de manera individual (por ejemplo un servicio):

```bash
npm test -- cliente.service.spec.ts
```

---

## Estructura de carpetas

```text
frontend/
├── src/
│   ├── app/
│   │   ├── core/
│   │   │   ├── api/
│   │   │   └── models/
│   │   ├── features/
│   │   ├── layout/
│   │   ├── shared/
│   │   ├── app.component.css
│   │   ├── app.component.html
│   │   ├── app.component.spec.ts
│   │   ├── app.component.ts
│   │   ├── app.config.ts
│   │   └── app.routes.ts
│   ├── test/

```
