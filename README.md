
# 📊 Proyecto Java - Procesamiento de CSV con Strategy y OpenCSV

Este proyecto muestra cómo estructurar una aplicación Java modular aplicando el patrón de diseño **Strategy** para procesar un archivo CSV en diferentes etapas evolutivas. Cada estrategia representa un enfoque distinto para la lectura y carga de datos de empleados a modo de ejemplo.

---

## 🧱 Estructura del Proyecto

```bash
src/
 └── main/
      ├── java/utnfc/isi/back/procesocsv
      │    ├── App.java
      │    └── strategy/
      │         ├── StepStrategy.java
      │         ├── Step01Manual.java
      │         ├── Step02OpenCSVList.java
      │         └── Step03OpenCSVMap.java
      └── data/
           └── empleados.csv
pom.xml

```

---

## 🧠 Concepto Central

Se utiliza el patrón **Strategy** para encapsular distintas formas de procesar archivos CSV. Esto permite invocar cualquier "estrategia" de procesamiento usando un solo punto de entrada (`App.java`) y cambiar dinámicamente qué código se ejecuta según el parámetro que se pase desde línea de comandos.

---

## 🧩 Paso 1: Implementar el patrón Strategy

1. Crear la interfaz `StepStrategy` con un único método:

    ```java
    // Interfaz a cumplir por cada estrategia
    public interface StepStrategy {

        // Método que implementa la estrategia
        void ejecutar();
    }
    ```

2. Implementar clases concretas en el paquete `strategy` que implementen esa interfaz.

3. En `App.java`, crear un mapa con las estrategias disponibles y usar un argumento para invocar una u otra:

    ```java
    public class App {
        public static void main(String[] args) {
            ...
            // Obtención de la estrategia elegida desde el parámetros del método main
            //  captura de argumento de línea de comandos.
            String paso = args.length > 0 ? args[0].toLowerCase() : "";

            // Mapa de estrategias asociadas a un nombre clave
            Map<String, StepStrategy> estrategias = Map.of(
                "paso01", new Step01Manual(),
                "paso02", new Step02OpenCSVList(),
                "paso03", new Step03OpenCSVMap()
            );

            // Obtención de la Estrategia
            StepStrategy estrategia = estrategias.get(paso);

            // Chequeo de existencia de la misma y etablecimiento de estrategia por defecto
            if (estrategia == null) {
                System.out.println("Paso no reconocido. Se utiliza 'paso03'");
                estrategia = estrategias.get("paso03");
            }
            
            // Ejecución de la estrategia elegida
            estrategia.ejecutar();

            ...
        }
    }
    ```

    > En esta versión simplificada del método main, podemos observar el uso del parámetro `args` del método `main` para obtener el paso seleccionado por el usuario. En caso de no encontrar el paso usa `paso03` por defecto.

---

## 📄 Paso 2: Leer CSV con `Scanner` y `split`

En `Step01Manual.java`:

- Abrimos el archivo `empleados.csv`.
- Usamos `Scanner` para leer línea por línea.
- Aplicamos `split(",")` para separar campos.
- Creamos objetos y mostramos los primeros registros.

```java
// Apertura del archivo para lectura
try (Scanner sc = new Scanner(new File("src/main/data/empleados.csv"))) {

    sc.nextLine(); // Saltear encabezado

    // Lectura línea por línea
    while (sc.hasNextLine()) {
        // División de las columnas separadas por coma
        String[] campos = sc.nextLine().split(",");

        // parsear datos...
    }
}

```

> En esta primera verisón estamos haciendo esencialmente lo que hacíamos en AED para trabajar un archivo csv traducido a java pero trabajando a mano la lectura con `Scanner` y utilizando `split` para dividir el vector.

---

## 📚 Paso 3: Leer CSV como `List<String[]>` con OpenCSV

Ahora la idea es comenzar a trabajar como se hace normalmente en la realidad que es aprovechar librerías y código ya escrito en lugar de reinventar la rueda.

Vamos a utilizar la librería OpenCSV que es una de las opciones para procesar archivos csv con características como minimalista y Open Source. [OpenCSV en Maven Central](https://mvnrepository.com/artifact/com.opencsv/opencsv/5.11)

1. Agregar al `pom.xml`:

    ```xml
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.11</version>
    </dependency>
    ```

2. En `Step02OpenCSVList.java`:

    En el primer intento utilizamos la librería sin usar prácticamente nada de su pontencial, ya que estamos haciendo prácticamente lo mismo que si lo hiciéramos a mano, pero, evitando el uso de `Scanner` para ir línea por línea y la necesidad de `split` para dividir las porciones separadas por coma.

    ```java
    // Construcción de un lector de CSV de la librería
    CSVReader reader = new CSVReader(new FileReader("src/main/data/empleados.csv"));

    reader.readNext(); // saltear encabezado

    // Carga de todas las líneas del csv en una lista de vectores de cadenas.
    List<String[]> filas = reader.readAll();

    // Recorrido de las listas en memoria
    for (String[] campos : filas) {
        // parsear campos[0], campos[1], etc.
    }

    ```

---

## 🗺️ Paso 4: Leer CSV como `Map<String, String>` con encabezado

Ahora sí empezamos a aprovechar el potencial del la librería aplicando la alternativa de obtener un map por cada fila de elementos clave:valor para cada campo, esto hace mucho más legible el código.
En este caso es necesario el encabezado del archivo donde figuran los nobmres de las columnas (cabe mencionar que en las versiones anteriores en el código de proyecto se saltea la línea de comentarios para evitar errores).

En `Step03OpenCSVMap.java`, usamos `CSVReaderHeaderAware`:

```java

// Similar al caso anterior pero con una especialización más sofisticada que es capaz de 
//  comprendenr los encabezados de las columnas
CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader("src/main/data/empleados.csv"));
Map<String, String> fila;

// Recorrido de las líneas del archivo tomando cada Mapa de columna => valor
while ((fila = reader.readMap()) != null) {
    // Obteneción de los valores del CSV a partir del nombre de la columna
    String nombre = fila.get("nombre");
    // ...
    
}
```

> Esto mejora la legibilidad y elimina dependencia del orden de columnas.

---

## ▶️ 🛠️ Configuración y Ejecución con Maven

### 1️⃣ Configurar el plugin de ejecución en pom.xml

Para simplificar el uso de mvn exec:java, configuramos el plugin exec-maven-plugin. Asegurate de tener esta sección dentro de `<build>` en tu pom.xml:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <mainClass>utnfc.isi.back.procesocsv.App</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

> Esto configura App.java como clase principal para ejecutar el proyecto.

### 2️⃣ Ejecutar un paso desde línea de comandos

Una vez configurado el plugin, podés ejecutar cualquiera de los pasos disponibles simplemente indicando su nombre como argumento:

```bash
mvn compile exec:java -Dexec.args="paso02"
```

> Esto ejecutará el paso 2. Cambiar el argumento por `paso01` o `paso03` según se desee.

---

## 🐾 Próximos pasos

- Usted debe tomar cualquiera de los pasos como punto de partida para la resolución de la actividad de clase propuesta.
- En dicha actividad, deberá construir las clases para EmpleadosPermanentes y EmpleadosContratados.
- Procesar el archivo CSV a una lista de objetos.
- Finalmente obtener los resultados solicitados para la lista resultante.

---

## 🧪 CSV de ejemplo

El archivo `src/main/data/empleados.csv` incluye más de 1000 empleados con los siguientes campos:

```text
legajo,nombre,tipo,categoria,fecha,montoBase
1001,Juan Pérez,PERMANENTE,A,2010-04-12,85000
...
```

---

## 🚀 Créditos

Este proyecto fue preparado como base didáctica para la asignatura Backend de Aplicaciones de la carrera Ingeniería en Sistemas de Información (UTN-FRC).
