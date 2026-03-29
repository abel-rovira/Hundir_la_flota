# Hundir la Flota — JavaFX

Proyecto de escritorio desarrollado en **JavaFX** que implementa el clásico juego de Hundir la Flota (Batalla Naval) con interfaz gráfica, persistencia de partidas y tabla de resultados históricos.

---

## Descripción

El jugador humano se enfrenta a la CPU en un tablero de 10×10. Primero coloca manualmente su flota y después turna disparos con la máquina hasta hundir todos los barcos del adversario. El estado de la partida puede guardarse y recuperarse en cualquier momento.

---

## Estructura del proyecto

```
src/
└── com/example/hundirflotav1/
    ├── base/
    │   ├── FileManager.java         # Gestor de archivos binarios (serialización)
    │   └── Writable.java            # Clase base serializable
    ├── EstadoJuego.java             # Modelo: estado completo de la partida
    ├── LogicaJuego.java             # Lógica del juego (sin dependencias de UI)
    ├── JuegoController.java         # Controlador visual del tablero
    ├── HelloController.java         # Controlador FXML principal
    ├── HelloApplication.java        # Punto de entrada JavaFX
    ├── Launcher.java                # Main class para empaquetado
    ├── ResultadosController.java    # Gestión de resultados históricos
    ├── VerResultados.java           # Modelo de un resultado de partida
    └── VerResultadosController.java # Controlador FXML de la tabla de resultados
resources/
└── com/example/hundirflotav1/
    ├── hundirlaflota.fxml           # Vista principal
    └── tablaresultados.fxml         # Vista de resultados
```

---

## Capturas de pantalla

### TABLERO
<p align="center">
  <img src="https://github.com/user-attachments/assets/d015355e-b9ee-4878-a48c-5892fba2165c"  width="660" height="583" alt="Tablero principal" width="600"/>
</p>

### TABLERO DE RESULTADOS
<p align="center">
  <img width="662" height="583" alt="Tabla de resultados" src="https://github.com/user-attachments/assets/a2a5ff79-aed8-4122-b021-df3eb72245cf" />   
</p>

---

## Funcionalidades

### Menú principal
| Opción | Descripción |
|--------|-------------|
| **Partida → Nueva partida** | Inicia una nueva partida, reiniciando la actual si la hubiera |
| **Partida → Cargar partida** | Recupera el estado guardado desde `partida_guardada.dat` |
| **Partida → Guardar partida** | Guarda el estado actual en `partida_guardada.dat` |
| **Ver resultados** | Abre una ventana independiente con el historial de partidas |
| **Salir** | Cierra la aplicación |

### Tableros
- **Tablero del jugador** (izquierda): muestra los barcos propios y los disparos recibidos.
- **Tablero enemigo** (derecha): muestra el resultado de los disparos del jugador sobre la CPU.

### Colocación de barcos
- El jugador coloca sus barcos **manualmente** haciendo clic en el tablero propio.
- El botón **"Rotar barco"** alterna entre orientación horizontal y vertical.
- Si la colocación no es válida (fuera de límites o solapamiento), se muestra un **mensaje en rojo** indicando el motivo.
- La CPU coloca sus barcos automáticamente al inicio.

### Código de colores del tablero
| Color | Significado |
|-------|-------------|
| Azul claro | Agua (celda vacía) |
| Verde | Barco propio intacto |
| Rojo | Impacto |
| Gris | Agua disparada (fallo) |

### Registro de disparos
- Lista visible con todos los disparos realizados por ambos jugadores durante la partida.
- Formato: `Jugador: B3: IMPACTO` / `CPU: F7: AGUA`

### Deshacer turno
- El botón **"Volver hacia atrás"** deshace simultáneamente el último disparo del jugador y el de la CPU, restaurando ambas cuadrículas y el registro.

### Tabla de resultados
Se muestra en ventana independiente con las columnas:
- **Vidas del jugador** — celdas de barco intactas al terminar
- **Vidas de la CPU** — celdas de barco intactas al terminar
- **Ganador** — nombre del jugador que ganó (`Jugador` o `CPU`)

Los resultados se persisten automáticamente en `resultados.dat`.

---

## Arquitectura

El proyecto sigue una separación estricta entre lógica y presentación:

```
HelloController      →  solo gestiona eventos de UI, sin lógica de juego
    │
    ├── JuegoController   →  gestión visual de tableros e interacción
    │       │
    │       └── LogicaJuego   →  toda la lógica del juego
    │               │
    │               └── EstadoJuego   →  estado serializable completo
    │
    └── ResultadosController  →  gestión y persistencia de resultados
```

> **Importante:** `HelloController` y `JuegoController` no contienen lógica de juego. Toda la lógica reside en `LogicaJuego` y `EstadoJuego`.

---

## Persistencia

Se utiliza la clase `FileManager` (del proyecto base Polideportivo) para serialización binaria:

| Archivo | Contenido |
|---------|-----------|
| `partida_guardada.dat` | Estado completo de la partida actual (`EstadoJuego`) |
| `resultados.dat` | Lista histórica de resultados (`List<VerResultados>`) |

Todos los objetos persistidos implementan `Serializable`.

---

## Requisitos y ejecución

### Requisitos
- **Java** 17 o superior
- **JavaFX** 17 o superior
- **Maven** (recomendado) o configuración manual del módulo JavaFX

### Ejecución con Maven
```bash
mvn clean javafx:run
```

### Ejecución manual
```bash
# Compilar
javac --module-path /ruta/javafx/lib --add-modules javafx.controls,javafx.fxml -d out src/**/*.java

# Ejecutar
java --module-path /ruta/javafx/lib --add-modules javafx.controls,javafx.fxml -cp out com.example.hundirflotav1.Launcher
```

> Usa `Launcher` como clase principal para evitar problemas de módulos con JavaFX al empaquetar en JAR.

---

## Cómo jugar

1. Al iniciar, selecciona **Partida → Nueva partida**.
2. **Coloca tus barcos** haciendo clic en el tablero izquierdo. Usa "Rotar barco" para cambiar la orientación.
   - Los barcos a colocar son: 1×5, 1×4, 2×3, 1×2 celdas.
3. Una vez colocados todos los barcos, haz clic en el **tablero enemigo** (derecha) para disparar.
4. La CPU disparará automáticamente tras cada turno tuyo.
5. Gana quien hunda todos los barcos del rival primero.
6. Puedes **guardar** en cualquier momento y **recuperar** la partida más tarde.
