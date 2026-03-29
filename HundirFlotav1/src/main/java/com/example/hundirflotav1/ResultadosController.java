package com.example.hundirflotav1;

import com.example.hundirflotav1.base.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultadosController {

    private static ObservableList<VerResultados> listaResultados = FXCollections.observableArrayList();

    static {
        cargarResultados();
    }

    public static void agregarResultado(int vidasJugador, int vidasCPU, String ganador) {
        VerResultados resultado = new VerResultados(vidasJugador, vidasCPU, ganador);
        listaResultados.add(resultado);
        guardarResultados();
    }

    private static void guardarResultados() {
        List<VerResultados> lista = new ArrayList<>(listaResultados);
        FileManager.saveFileList("resultados.dat", lista);
    }

    private static void cargarResultados() {
        List<?> cargados = FileManager.loadFileList("resultados.dat");
        if (cargados != null) {
            listaResultados.clear();
            for (Object obj : cargados) {
                listaResultados.add((VerResultados) obj);
            }
        }
    }

    public void mostrarResultados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tablaresultados.fxml"));
            Scene scene = new Scene(loader.load());
            VerResultadosController controller = loader.getController();
            controller.setListaResultados(listaResultados);
            Stage ventana = new Stage();
            ventana.setTitle("Resultados");
            ventana.setScene(scene);
            ventana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}