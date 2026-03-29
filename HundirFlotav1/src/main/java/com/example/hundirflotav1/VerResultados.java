package com.example.hundirflotav1;

import java.io.Serializable;

public class VerResultados implements Serializable {
    private static final long serialVersionUID = 1L;

    private int vidasJugador;
    private int vidasCPU;
    private String ganador;

    public VerResultados(int vidasJugador, int vidasCPU, String ganador) {
        this.vidasJugador = vidasJugador;
        this.vidasCPU = vidasCPU;
        this.ganador = ganador;
    }

    public int getVidasJugador() {
        return vidasJugador;
    }

    public void setVidasJugador(int vidasJugador) {
        this.vidasJugador = vidasJugador;
    }

    public int getVidasCPU() {
        return vidasCPU;
    }

    public void setVidasCPU(int vidasCPU) {
        this.vidasCPU = vidasCPU;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }
}