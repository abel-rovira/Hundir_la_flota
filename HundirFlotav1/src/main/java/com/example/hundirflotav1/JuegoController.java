package com.example.hundirflotav1;

import com.example.hundirflotav1.base.FileManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class JuegoController {

    private GridPane tableroJugadorGrid;
    private GridPane tableroEnemigoGrid;
    private LogicaJuego logicaJuego;

    private Button[][] botonesJugador;
    private Button[][] botonesEnemigo;

    private ObservableList<String> listaRegistro;

    private Label etiquetaEstado;
    private Label etiquetaVidas;
    private Label etiquetaError;

    private int orientacionActual;
    private boolean colocandoBarcos;
    private int barcoActualIndex;
    private int[] tamanosBarcos;

    private ListView<String> historialDisparos; // ✅ USAMOS EL DEL FXML

    public JuegoController(GridPane tableroJugador, GridPane tableroEnemigo) {
        this.tableroJugadorGrid = tableroJugador;
        this.tableroEnemigoGrid = tableroEnemigo;

        this.botonesJugador = new Button[10][10];
        this.botonesEnemigo = new Button[10][10];

        this.listaRegistro = FXCollections.observableArrayList();

        this.orientacionActual = 0;
        this.colocandoBarcos = true;
        this.tamanosBarcos = new int[]{5, 4, 3, 3, 2};
        this.barcoActualIndex = 0;

        inicializarInterfaz();
        nuevaPartida();
    }

    // 🔥 MÉTODO PARA CONECTAR EL LISTVIEW DEL FXML
    public void setHistorialDisparos(ListView<String> historial) {
        this.historialDisparos = historial;
        this.historialDisparos.setItems(listaRegistro);
    }

    private void inicializarInterfaz() {

        etiquetaEstado = new Label("Coloca tus barcos");
        etiquetaVidas = new Label("Vidas Jugador: 0 | Vidas CPU: 0");
        etiquetaError = new Label();

        dibujarTableroJugador();
        dibujarTableroEnemigo();
    }

    private void dibujarTableroJugador() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {

                Button celda = new Button();
                celda.setMinSize(20, 20);

                int f = fila;
                int c = columna;

                celda.setOnAction(e -> alHacerClickEnTableroJugador(f, c));

                botonesJugador[fila][columna] = celda;
                tableroJugadorGrid.add(celda, columna, fila);
            }
        }
    }

    private void dibujarTableroEnemigo() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {

                Button celda = new Button();
                celda.setMinSize(20, 20);

                int f = fila;
                int c = columna;

                celda.setOnAction(e -> alHacerClickEnTableroEnemigo(f, c));

                botonesEnemigo[fila][columna] = celda;
                tableroEnemigoGrid.add(celda, columna, fila);
            }
        }
    }

    private void alHacerClickEnTableroJugador(int fila, int columna) {

        if (colocandoBarcos) {

            String resultado = logicaJuego.colocarBarcoJugador(fila, columna, orientacionActual);

            listaRegistro.add(0, resultado);

            if (!resultado.contains("Error")) {
                barcoActualIndex++;

                if (barcoActualIndex >= tamanosBarcos.length) {
                    colocandoBarcos = false;
                }
            }

            actualizarTableroJugador();
        }
    }

    private void alHacerClickEnTableroEnemigo(int fila, int columna) {

        if (!colocandoBarcos && !logicaJuego.getEstado().isJuegoTerminado()) {

            String r1 = logicaJuego.disparoJugador(fila, columna);
            listaRegistro.add(0, r1);

            if (!logicaJuego.getEstado().isJuegoTerminado()) {
                String r2 = logicaJuego.disparoCPU();
                listaRegistro.add(0, r2);
            }

            actualizarTableroJugador();
            actualizarTableroEnemigo();
        }
    }

    private void actualizarTableroJugador() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                char v = logicaJuego.getEstado().getTableroJugador()[i][j];
                Button b = botonesJugador[i][j];

                if (v == 'B') b.setStyle("-fx-background-color: green;");
                else if (v == 'X') b.setStyle("-fx-background-color: red;");
                else if (v == '-') b.setStyle("-fx-background-color: gray;");
                else b.setStyle("-fx-background-color: lightblue;");
            }
        }
    }

    private void actualizarTableroEnemigo() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                char v = logicaJuego.getEstado().getTableroDisparosJugador()[i][j];
                Button b = botonesEnemigo[i][j];

                if (v == 'X') b.setStyle("-fx-background-color: red;");
                else if (v == '-') b.setStyle("-fx-background-color: gray;");
                else b.setStyle("-fx-background-color: lightcoral;");
            }
        }
    }

    public void nuevaPartida() {
        logicaJuego = new LogicaJuego();
        colocandoBarcos = true;
        barcoActualIndex = 0;
        listaRegistro.clear();
        actualizarTableroJugador();
        actualizarTableroEnemigo();
    }

    public void cargarPartida() {
        EstadoJuego estado = FileManager.loadFile("partida_guardada.dat");

        if (estado != null) {
            logicaJuego = new LogicaJuego(estado);
            colocandoBarcos = false;
            actualizarTableroJugador();
            actualizarTableroEnemigo();
        }
    }

    public void guardarPartida() {
        FileManager.saveFile("partida_guardada.dat", logicaJuego.getEstado());
    }

    public void deshacerTurno() {
        logicaJuego.deshacerUltimoTurno();
        actualizarTableroJugador();
        actualizarTableroEnemigo();
    }

    public void salir() {
        Platform.exit();
    }
}