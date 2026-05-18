## Estructura del Proyecto

```
sportclub-automation/
├── .github/
│   └── workflows/
│       ├── ci.yml          # Pipeline de Integración Continua (Actividad 2)
│       └── cd.yml          # Pipeline de Deployment con rollback (Actividad 3)
├── src/
│   └── test/
│       └── java/cl/iplacex/sportclub/
│           ├── ServicioSocioTest.java      # Pruebas unitarias (JUnit 5)
│           └── SocioApiIT.java             # Pruebas de integración (REST Assured)
├── pom.xml                 # Configuración Maven con dependencias (Actividad 1)
└── README.md               # Este archivo
```

---

## Estrategia de Ramas — GitFlow

Se utiliza el flujo **GitFlow** con las siguientes ramas:

| Rama | Propósito |
|------|-----------|
| `main` | Código en producción, siempre estable |
| `develop` | Integración continua del desarrollo |
| `feature/*` | Desarrollo de nuevas funcionalidades |
| `release/*` | Preparación de una nueva versión |
| `hotfix/*` | Correcciones urgentes en producción |

**Flujo típico:**
```
feature/nueva-funcionalidad → develop → release/1.0.0 → main
```

---

## Estrategia de Pruebas

El proyecto implementa **tres niveles de pruebas** siguiendo la pirámide de testing:

### 1. Pruebas Unitarias (`*Test.java`)
- **Framework:** JUnit 5 + Mockito
- **Plugin Maven:** `maven-surefire-plugin`
- **Qué prueban:** Lógica de negocio aislada (servicios, validaciones, cálculos)
- **Ejemplo:** `ServicioSocioTest.java` — valida el registro de socios y cálculo de cuotas

### 2. Pruebas de Integración (`*IT.java`)
- **Framework:** REST Assured
- **Plugin Maven:** `maven-failsafe-plugin`
- **Qué prueban:** Comunicación entre componentes y APIs REST
- **Ejemplo:** `SocioApiIT.java` — verifica los endpoints de la API de socios

### 3. Pruebas de Aceptación (Acceptance Tests)
- **Perfil Maven:** `-Pacceptance-tests`
- **Qué prueban:** Flujos completos del negocio desde la perspectiva del usuario final
- **Se ejecutan en:** El pipeline CD, antes de cualquier despliegue

---

## Cómo Ejecutar las Pruebas

### Prerrequisitos
- Java 17+
- Maven 3.9+
- (Opcional) Docker para pruebas de integración con base de datos

### Comandos

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar SOLO pruebas unitarias
mvn test

# Ejecutar pruebas unitarias + integración
mvn verify

# Ejecutar solo pruebas de integración
mvn verify -DskipUnitTests

# Generar reporte de cobertura (se genera en target/site/jacoco/index.html)
mvn test jacoco:report
```

---

## Cómo Ejecutar los Pipelines

### Pipeline CI (Actividad 2)
Se activa **automáticamente** en cada push o Pull Request a `main`, `develop` o `feature/*`.

También se puede ejecutar manualmente:
1. Ir a **Actions** en GitHub
2. Seleccionar `CI Pipeline - Build & Test`
3. Click en **Run workflow**

**Etapas del CI:**
```
Build → Pruebas Unitarias → Pruebas de Integración → Notificación
```

### Pipeline CD — Blue-Green (Actividad 3)
Se activa **automáticamente** en push a `main`, o manualmente desde Actions.

**Etapas del CD:**
```
Acceptance Tests → Build Docker Image → Deploy Green → Smoke Tests → Switch Tráfico (Blue→Green)
                                                            ↓ (si falla)
                                                         Rollback a Blue
```

---

## Estrategia de Despliegue: Blue-Green

Se implementa **Blue-Green Deployment** para garantizar cero tiempo de inactividad:

1. **Blue** = versión estable actualmente en producción
2. **Green** = nueva versión que se despliega y valida
3. Si los **smoke tests** pasan → se redirige el 100% del tráfico a Green
4. Si los smoke tests **fallan** → **rollback automático** a Blue sin impacto en usuarios

**Ventajas:**
-  Cero downtime durante los despliegues
-  Rollback instantáneo ante fallas
-  Ambiente de prueba idéntico a producción

---

##  Cobertura de Código

El reporte de cobertura se genera automáticamente con **JaCoCo** en cada ejecución de `mvn test`.

Ubicación local: `target/site/jacoco/index.html`

En GitHub Actions, el reporte se descarga desde la sección **Artifacts** del workflow.

---

## Tecnologías Utilizadas

| Tecnología | Versión | Rol |
|------------|---------|-----|
| Java | 17 | Lenguaje de programación |
| Maven | 3.9+ | Gestión de dependencias y build |
| JUnit 5 | 5.10.2 | Framework de pruebas unitarias |
| Mockito | 5.11.0 | Mocking para pruebas unitarias |
| REST Assured | 5.4.0 | Pruebas de integración de API REST |
| Selenium | 4.20.0 | Pruebas de interfaz web |
| JaCoCo | 0.8.11 | Cobertura de código |
| GitHub Actions | — | CI/CD Pipeline |
| Docker | — | Contenedores para despliegue |
| PostgreSQL | 15 | Base de datos en pruebas de integración |

---
