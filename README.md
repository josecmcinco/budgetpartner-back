# 💰 BudgetPartner - Spring Boot Backend

**BudgetPartner** es el backend de una aplicación de gestión de gastos personales y grupales. Esta API RESTful, desarrollada con **Spring Boot**, proporciona toda la lógica de negocio, autenticación y persistencia de datos necesaria para la app móvil (desarrollada en [Expo](https://expo.dev)).

---

## ⚙️ Requisitos previos

- Java 17 o superior  
- Maven 3.8+  
- PostgreSQL (u otro motor de base de datos compatible con JPA)  
- IDE compatible con Spring (IntelliJ, VS Code, Eclipse)  
- Docker (opcional, para desarrollo o despliegue de pruebas)
- Ollama (opciona, para implementar Inteligencia Artificial en local)

---

## 🚀 Comenzar

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/budgetpartner-backend.git
cd budgetpartner-backend
````
### 2. Configurar variables de entorno

```bash
# Copia el archivo de ejemplo
cp .env.example .env
# Edita el archivo .env con tus credenciales de base de datos y otras configuraciones
````

### 3. (Opcional) Descargar agentes IA con Ollama
```bash
# Descarga un modelo, por ejemplo 'llama2'
ollama pull llama2

#Ver modelos descargados
ollama list
````
La aplicación da soporte a los siguientes modelos:
 - gemma3:12b
 - "qwen3:4b"

```bash
# Copia el archivo de ejemplo
cp .env.example .env
# Edita el archivo .env con tus credenciales de base de datos y otras configuraciones
````

### 4. Ejecutar localmente

#A. Usando maven: 
```bash
./mvnw spring-boot:run
```

#A. Desde tu IDE: 
- Abre el proyecto.
- Ejecuta la clase BudgetPartnerApplication.java.


### 5. Estructura del proyecto

- `/admin`: Funciones administrativas del sistema.
- `/config`: Configuración general de la aplicación.
- `/controller`: Endpoints y rutas REST.
- `/dto`: Objetos de transferencia de datos.
- `/entity`: Entidades JPA (modelo de datos).
- `/enums`: Enumeraciones del dominio.
- `/exceptions`: Manejo de errores y excepciones.
- `/mapper`: Conversión entre entidades y DTOs.
- `/repository`: Acceso a base de datos.
- `/security`: Autenticación y autorización.
- `/service`: Lógica de negocio.

### 6. Pruebas
./mvnw test

### 7. Recursos útiles
(PENDIENTE)
