package com.example.hundirflotav1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    private GridPane tableroUno;
    @FXML
    private GridPane tableroDos1;
    @FXML
    private Button botonRotar;


    @FXML
    private MenuItem cargarPartida;
    @FXML
    private MenuItem nuevaPartida;
    @FXML
    private MenuItem guardarPartida;

    @FXML
    private MenuItem tablaResultados;


    @FXML
    private MenuItem salir;

    @FXML
    private ListView<String> historial;

    @FXML
    private Button botonRetroceder;

    @FXML
    private Label labelError;

    private JuegoController juegoController;
    private ResultadosController resultadosController;

    @FXML
    public void initialize() {

        juegoController = new JuegoController(tableroUno, tableroDos1);
        juegoController.setHistorialDisparos(historial);
        resultadosController = new ResultadosController();

        botonRotar.setOnAction(e -> juegoController.cambiarOrientacion());

        nuevaPartida.setOnAction(e -> juegoController.nuevaPartida());
        cargarPartida.setOnAction(e -> juegoController.cargarPartida());
        guardarPartida.setOnAction(e -> juegoController.guardarPartida());
        tablaResultados.setOnAction(e -> verResultados());
        salir.setOnAction(e -> juegoController.salir());

        juegoController.setLabelError(labelError);

        botonRetroceder.setOnAction(e -> juegoController.deshacerTurno());
    }

    private void verResultados() {
        resultadosController.mostrarResultados();
    }
}
