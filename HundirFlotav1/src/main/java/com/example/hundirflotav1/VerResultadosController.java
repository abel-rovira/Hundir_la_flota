package com.example.hundirflotav1;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VerResultadosController {

    @FXML
    private TableView<VerResultados> tableResultados;

    @FXML
    private TableColumn<VerResultados, Integer> colVidaJugador;
    @FXML
    private TableColumn<VerResultados, Integer> colVidaCPU;
    @FXML
    private TableColumn<VerResultados, String> colGanador;

    @FXML
    public void initialize() {
        colVidaJugador.setCellValueFactory(new PropertyValueFactory<>("vidasJugador"));
        colVidaCPU.setCellValueFactory(new PropertyValueFactory<>("vidasCPU"));
        colGanador.setCellValueFactory(new PropertyValueFactory<>("ganador"));
    }

    public void setListaResultados(ObservableList<VerResultados> resultados) {
        tableResultados.setItems(resultados);
    }

}
