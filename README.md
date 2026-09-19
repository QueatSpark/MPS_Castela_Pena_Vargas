# MPS_Castela_Pena_Vargas

## Descripción

Repositorio del equipo **Castela - Peña - Vargas** para la materia de
**Modelos de Prueba de Software** de la carrera de Ingeniería en Informática,
secuencia **5CV50**, ciclo **27-1**, en **UPIICSA - IPN**.

Este repositorio contiene las prácticas, tareas y proyectos desarrollados
a lo largo del curso. Cada proyecto se encuentra en su propia carpeta para
mantener el código y la documentación organizados.

## Integrantes

| Nombre | Usuario GitHub |
|---|---|
| Queat Castela | [@QueatSpark](https://github.com/QueatSpark) |
| Valentina Peña | [@val0703](https://github.com/val0703) |
| Angel Vargas | [@AgelIvanV-D](https://github.com/AgelIvanV-D) |

## Estructura del Repo

MPS_Castela_Pena_Vargas/
├── docs/ # Documentación y reportes en PDF
│ ├── Calculadora V. 1.0.pdf
│ └── Calculadora V. 2.0.pdf
├── calculadora/ # Proyecto: Calculadora MVC
│ ├── pom.xml
│ └── src/main/java/org/Calculadora/
│ ├── App.java
│ ├── controller/
│ ├── model/
│ └── view/
└── README.md


## Proyecto: Calculadora

Calculadora de operaciones básicas con interfaz gráfica en **Java Swing**,
organizada bajo el patrón de diseño **Modelo-Vista-Controlador (MVC)**.

### Características

- Operaciones: suma, resta, multiplicación y división.
- Botones: `AC`, `C`, `±`, `%`, dígitos, `.`, operadores y `=`.
- Estilo visual tipo calculadora clásica (fondo oscuro, botones negros,
  texto azul cielo).
- Manejo de errores: división entre cero y entradas inválidas.
- Botones de memoria (`+M`, `-M`, `MRC`) deshabilitados, reservados para
  futuras versiones.

### Tecnologías

- **Java** (versión 17 o superior)
- **Maven** para la gestión del proyecto
- **Swing** para la interfaz gráfica

### Arquitectura MVC

| Capa | Clase | Responsabilidad |
|---|---|---|
| Modelo | `OperacionesBasicas` | Lógica matemática pura |
| Vista | `CalculadoraView` | Interfaz Swing, sin lógica |
| Controlador | `CalculadoraController` | Conecta vista y modelo, maneja eventos |

### Cómo ejecutar

#### Con Maven (recomendado)

```bash
cd calculadora
mvn compile exec:java -Dexec.mainClass="org.Calculadora.App"
