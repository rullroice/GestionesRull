# **Proyecto Final: Sistema de Gestión de Inventario**

* **Autor:** Raúl Ibarra
* **Fecha:** 25 de junio de 2025

---

## **1. Resumen Técnico del Proyecto**

El presente proyecto consiste en una aplicación de consola desarrollada en Java estándar utilizando Apache Maven como sistema de gestión y construcción. El sistema implementa una solución funcional para la gestión de un inventario básico, cuyo propósito principal es servir como un caso de estudio práctico para la aplicación de patrones de diseño "Gang of Four" (GoF).

El objetivo fundamental es la aplicación pragmática de dichos patrones para construir un sistema que exhiba características deseables de una arquitectura de software robusta: **bajo acoplamiento**, **alta cohesión** y **extensibilidad**. El diseño se adhiere a principios de diseño SOLID, como el de Apertura/Cierre y el de Inversión de Dependencias, para resolver problemas concretos de creación, estructura y comportamiento de objetos.

**Funcionalidades Principales:**
- Modelado de una estructura de inventario jerárquica (ítems y categorías).
- Creación desacoplada de transacciones de inventario (entradas y salidas de stock).
- Implementación de un mecanismo de notificación reactivo para alertas de bajo stock.
- Gestión de un estado de configuración global y consistente para la aplicación.

## **2. Análisis y Justificación de Patrones de Diseño**

Se implementaron cuatro patrones de diseño GoF, seleccionados estratégicamente para abordar desafíos específicos del dominio del problema.

### **2.1. Patrón Creacional: Singleton**

* **Clase Implementada:** `ConfiguracionSistema`
* **Problema a Resolver (Justificación Técnica):** En cualquier aplicación, es imperativo gestionar un estado de configuración que debe ser consistente y globalmente accesible (e.g., umbrales, nombres de servidor, rutas de archivos). Instanciar múltiples objetos de configuración conduciría a un estado inconsistente y a un uso ineficiente de la memoria. El patrón Singleton resuelve este problema garantizando la existencia de **una y solo una instancia** de una clase, proporcionando un punto de acceso global y controlado a dicho objeto. Esto asegura un único punto de control para el estado compartido, aunque se debe usar con prudencia para no introducir un estado global excesivo que dificulte las pruebas unitarias.
* **Implementación (¿Cómo?):** Se sigue la implementación clásica: un constructor privado para prevenir la instanciación externa, una variable `static private` para contener la única instancia, y un método `public static synchronized getInstance()` que gestiona el acceso y la creación "lazy" (diferida) de la instancia, asegurando además la seguridad en entornos multi-hilo (thread-safety).
* **Ubicación y Uso:** La clase reside en `patrones.creacionales.singleton`. Es invocada por `Main` para obtener datos de configuración y por `ObservadorAlerta` para consultar el `umbralStockBajo`.

### **2.2. Patrón Creacional: Factory Method (versión simplificada)**

* **Clases Involucradas:** `FabricaMovimiento`, `Movimiento` (Interfaz), `MovimientoEntrada` y `MovimientoSalida` (Productos Concretos).
* **Problema a Resolver (Justificación Técnica):** El código cliente necesita crear objetos que pertenecen a una misma familia (`Movimiento`), pero no debe conocer los detalles de las clases concretas que se instancian. Acoplar al cliente con `new MovimientoEntrada()` o `new MovimientoSalida()` violaría el **Principio de Apertura/Cierre (Open/Closed Principle)**. Si se quisiera añadir un `MovimientoDeDevolucion`, habría que modificar el cliente. El patrón Factory Method delega la responsabilidad de la instanciación a una clase "fábrica", desacoplando al cliente de los productos concretos y adhiriéndose al principio de "programar para una interfaz, no para una implementación".
* **Implementación (¿Cómo?):** Una clase `FabricaMovimiento` expone un método `crearMovimiento(String tipo, int cantidad)`. Este método contiene la lógica condicional que determina qué clase concreta instanciar, devolviendo un objeto de tipo `Movimiento`, la interfaz común de todos los productos.
* **Ubicación y Uso:** La fábrica se encuentra en `patrones.creacionales.factorymethod` y los productos en `modelo.movimiento`. La clase `Main` utiliza esta fábrica para obtener instancias de `Movimiento` sin conocer su tipo concreto.

### **2.3. Patrón Estructural: Composite**

* **Clases Involucradas:** `ComponenteInsumo` (Interfaz Component), `Insumo` (Leaf), `GrupoInsumo` (Composite).
* **Problema a Resolver (Justificación Técnica):** Se requiere modelar una jerarquía de "parte-todo", como es un inventario con categorías y productos. El patrón Composite permite componer objetos en **estructuras de árbol** y trabajar con estos objetos (tanto individuales como compuestos) de manera uniforme. La principal ventaja es la **transparencia**: el código cliente puede tratar a un `Insumo` simple y a un `GrupoInsumo` complejo de la misma manera a través de la interfaz `ComponenteInsumo`. Esto simplifica enormemente el código cliente y facilita la implementación de operaciones recursivas (e.g., `mostrarDetalles`, `calcularValorTotal`).
* **Implementación (¿Cómo?):** La interfaz `ComponenteInsumo` define la operación común. La clase `Insumo` (hoja) implementa la operación directamente. La clase `GrupoInsumo` (compuesto) implementa la operación y, además, mantiene una colección de `ComponenteInsumo`, delegando la llamada a cada uno de sus "hijos".
* **Ubicación y Uso:** Las clases están en `modelo.insumo`. En `Main` se instancia y se ensambla la estructura jerárquica del inventario.

### **2.4. Patrón de Comportamiento: Observer**

* **Clases Involucradas:** `Sujeto` (Interfaz), `Observador` (Interfaz), `Insumo` (Sujeto Concreto), `ObservadorAlerta` (Observador Concreto).
* **Problema a Resolver (Justificación Técnica):** Es necesario establecer una relación de dependencia de uno-a-muchos entre objetos, de forma que cuando el estado de un objeto (el `Insumo`) cambia, todos sus dependientes (los `Observador`es) sean notificados y actualizados automáticamente. Este patrón es fundamental para lograr un **bajo acoplamiento (loose coupling)**. El `Sujeto` no conoce nada sobre sus observadores, excepto que implementan la interfaz `Observador`. Esto se alinea con el **Principio de Inversión de Dependencias (Dependency Inversion Principle)**, ya que los módulos de alto y bajo nivel dependen de abstracciones. Permite que el `Insumo` (lógica de negocio central) y el `ObservadorAlerta` (una preocupación secundaria) evolucionen de forma independiente.
* **Implementación (¿Cómo?):** `Insumo` implementa `Sujeto`, gestionando una lista de `Observador`es. Su método `setStock()` invoca a `notificarObservadores()` tras un cambio de estado. `ObservadorAlerta` implementa `Observador` y define la lógica de reacción en su método `actualizar()`.
* **Ubicación y Uso:** Las interfaces y el observador están en `patrones.comportamentales.observer`. `Insumo` actúa como el sujeto. En `Main` se realiza la suscripción del observador al sujeto.

## **3. Arquitectura de Paquetes y Código Fuente**

La estructura de paquetes sigue las convenciones de Maven y separa el modelo de dominio (`modelo`) de las implementaciones de los patrones (`patrones`), promoviendo una clara separación de conceptos y facilitando la navegabilidad del proyecto.

![image](https://github.com/user-attachments/assets/e49fcaae-884a-484d-818b-89a7bf3fc13c)

## **4. Guía de Compilación y Ejecución**

#### **Requisitos Previos**
* **JDK (Java Development Kit)**, versión 11 o superior.
* **Apache Maven**, versión 3.6 o superior.
* Variables de entorno `JAVA_HOME` y `Path` (apuntando al `bin` de Maven) configuradas correctamente.

#### **Compilación**
El proyecto se gestiona con Maven, que automatiza el proceso de compilación y empaquetado.

1.  **Abrir una terminal** en el sistema.
2.  **Navegar a la carpeta raíz del proyecto** (la que contiene el archivo `pom.xml`).
    ```sh
    cd "C:\GestionesRull\sistema de gestion\demo"
    ```
3.  **Ejecutar el ciclo de vida `package` de Maven.** Este comando limpiará compilaciones anteriores, compilará el código fuente y lo empaquetará en un archivo JAR.
    ```sh
    mvn clean package
    ```
4.  El proceso finalizará exitosamente con el mensaje `[INFO] BUILD SUCCESS`. Los archivos compilados (`.class`) se encontrarán en el directorio `target/classes`.

