# Comandos del proyecto

## Compilación y ejecución
- Compilar con Maven:
  ```bash
  mvn clean install
  ```
    - Ejecutar la aplicación:
  ```bash
  ./gradlew bootRun
    ```
    - Comandos para usar durante el inicio:
  ```bash
    #Se deben usar ambos a la vez para lanzar toda la app de nuevo
  --borrar # borra la base de datos
  --poblar # puebla la base de datos

    ```
    - Lanzar tests unitarios:
  ```bash
  mvn clean install
    ```
    - Lanzar tests de integración (necesario Docker abierto):
  ```bash
  mvn clean install
    ```
    - Generar y abrir test de JaCoCo (en target/jacoco-report):
  ```bash
  mvn verify
    ```
- Lanzar SonarCube:
  ```bash
  mvn sonar:sonar -Dsonar.projectKey=BudgetPartner -Dsonar.projectName=BudgetPartner -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_a9b80443fc8786b4a1f034bca3bf530e41c38240 -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco-report/jacoco.xml -Dsonar.coverage.exclusions=**/com/budgetpartner/APP/dto/**,**/com/budgetpartner/APP/entity/** 
    ```
- Para generar JavaDoc:

  (ir a Tools -> JavaDoc y añadirlo)
  ```bash
  -encoding UTF-8 -docencoding UTF-8 -charset UTF-8
    ```
  