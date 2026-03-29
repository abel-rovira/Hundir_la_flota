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
    private int barcoActualCont;
    private int[] tamanosBarcos;

    private ListView<String> historialDisparos;
    private boolean resultadoGuardado = false;

    public JuegoController(GridPane tableroJugador, GridPane tableroEnemigo) {
        this.tableroJugadorGrid = tableroJugador;
        this.tableroEnemigoGrid = tableroEnemigo;

        this.botonesJugador = new Button[10][10];
        this.botonesEnemigo = new Button[10][10];

        this.listaRegistro = FXCollections.observableArrayList();

        this.orientacionActual = 0;
        this.colocandoBarcos = true;
        this.tamanosBarcos = new int[]{5, 4, 3, 3, 2};
        this.barcoActualCont = 0;

        inicializarInterfaz();
        nuevaPartida();
    }

    public void setHistorialDisparos(ListView<String> historial) {
        this.historialDisparos = historial;

        if (this.historialDisparos != null) {
            this.historialDisparos.setItems(listaRegistro);
        }
    }

    private void inicializarInterfaz() {

        etiquetaEstado = new Label("Coloca tus barcos");
        etiquetaVidas = new Label("Vidas Jugador: 0 | Vidas CPU: 0");
        etiquetaError = new Label();

        dibujarTableroJugador();
        dibujarTableroEnemigo();
    }

    public void setLabelError(Label label) {
        this.etiquetaError = label;
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

    public void cambiarOrientacion() {
        orientacionActual = (orientacionActual == 0) ? 1 : 0;

        if (orientacionActual == 0) {
            listaRegistro.add(0, "Barcos en horizontal");
        } else {
            listaRegistro.add(0, "Barcos en vertical");
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
            if (resultado.contains("sale") || resultado.contains("Hay otro")) {
                if (etiquetaError != null) {
                    etiquetaError.setText(resultado);
                }
            } else {
                if (etiquetaError != null) {
                    etiquetaError.setText("");
                }
                listaRegistro.add(0, resultado);
                barcoActualCont++;
                if (barcoActualCont >= tamanosBarcos.length) {
                    colocandoBarcos = false;
                }
            }

            actualizarTableroJugador();
        }
    }


    private void alHacerClickEnTableroEnemigo(int fila, int columna) {
        if (!colocandoBarcos && !logicaJuego.getEstado().isJuegoTerminado()) {
            String r1 = logicaJuego.disparoJugador(fila, columna);
            if (r1.equals("Ya disparaste ahi")){
                return;
            }
            listaRegistro.add(0, r1);

            if (!logicaJuego.getEstado().isJuegoTerminado()) {
                String r2 = logicaJuego.disparoCPU();
                listaRegistro.add(0, r2);
            }

            actualizarTableroJugador();
            actualizarTableroEnemigo();

            if (logicaJuego.getEstado().isJuegoTerminado() && !resultadoGuardado) {
                int vidasJ = logicaJuego.calcularVidasJugador();
                int vidasCPU = logicaJuego.calcularVidasCPU();
                String ganador = logicaJuego.getEstado().getGanador();
                ResultadosController.agregarResultado(vidasJ, vidasCPU, ganador);

                resultadoGuardado = true;
            }
        }
    }

    private void actualizarTableroJugador() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                char v = logicaJuego.getEstado().getTableroJugador()[i][j];
                Button b = botonesJugador[i][j];

                if (v == 'B'){
                    b.setStyle("-fx-background-color: green;");
                }
                else if (v == 'X'){
                    b.setStyle("-fx-background-color: red;");
                }
                else if (v == '-'){
                    b.setStyle("-fx-background-color: gray;");
                }
                else {
                    b.setStyle("-fx-background-color: lightblue;");
                }
            }
        }
    }

    private void actualizarTableroEnemigo() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                char v = logicaJuego.getEstado().getTableroDisparosJugador()[i][j];
                Button b = botonesEnemigo[i][j];

                if (v == 'X'){
                    b.setStyle("-fx-background-color: red;");
                }
                else if (v == '-'){
                    b.setStyle("-fx-background-color: gray;");
                }
                else {
                    b.setStyle("-fx-background-color: lightcoral;");
                }
            }
        }
    }

    public void nuevaPartida() {
        logicaJuego = new LogicaJuego();
        colocandoBarcos = true;
        barcoActualCont = 0;
        listaRegistro.clear();
        resultadoGuardado = false;
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
        if (logicaJuego.getEstado().getUltimoDisparoJugador().isEmpty() || logicaJuego.getEstado().getUltimoDisparoCPU().isEmpty()) {
            return;
        }

        logicaJuego.deshacerUltimoTurno();

        if (!listaRegistro.isEmpty()){
            listaRegistro.remove(0);
        }
        if (!listaRegistro.isEmpty()){
            listaRegistro.remove(0);
        }

        actualizarTableroJugador();
        actualizarTableroEnemigo();
    }

    public void salir() {
        Platform.exit();
    }
}
