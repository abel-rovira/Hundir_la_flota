package com.example.hundirflotav1;

import com.example.hundirflotav1.base.Writable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EstadoJuego extends Writable implements Serializable {
    private static final long serialVersionUID = 1L;

    private char[][] tableroJugador;
    private char[][] tableroPC;
    private char[][] tableroDisparosJugador;
    private char[][] tableroDisparosPC;
    private int impactosJugador;
    private int impactosPC;
    private int totalCeldas;
    private List<String> historialDisparos;
    private List<String> historialDisparosCPU;
    private boolean juegoTerminado;
    private String ganador;
    private int[] barcos;
    private boolean[][] celdasOcupadas;
    private List<int[]> ultimoDisparoJugador;
    private List<int[]> ultimoDisparoCPU;
    private int barcoActualIndex;
    private boolean colocandoBarcos;

    public EstadoJuego() {
        this.historialDisparos = new ArrayList<>();
        this.historialDisparosCPU = new ArrayList<>();
        this.barcos = new int[]{5, 4, 3, 3, 2};
        this.celdasOcupadas = new boolean[10][10];
        this.ultimoDisparoJugador = new ArrayList<>();
        this.ultimoDisparoCPU = new ArrayList<>();
        this.barcoActualIndex = 0;
        this.colocandoBarcos = true;
    }

    public char[][] getTableroJugador() {
        return tableroJugador;
    }

    public void setTableroJugador(char[][] tableroJugador) {
        this.tableroJugador = tableroJugador;
    }

    public char[][] getTableroPC() {
        return tableroPC;
    }

    public void setTableroPC(char[][] tableroPC) {
        this.tableroPC = tableroPC;
    }

    public char[][] getTableroDisparosJugador() {
        return tableroDisparosJugador;
    }

    public void setTableroDisparosJugador(char[][] tableroDisparosJugador) {
        this.tableroDisparosJugador = tableroDisparosJugador;
    }

    public char[][] getTableroDisparosPC() {
        return tableroDisparosPC;
    }

    public void setTableroDisparosPC(char[][] tableroDisparosPC) {
        this.tableroDisparosPC = tableroDisparosPC;
    }

    public int getImpactosJugador() {
        return impactosJugador;
    }

    public void setImpactosJugador(int impactosJugador) {
        this.impactosJugador = impactosJugador;
    }

    public int getImpactosPC() {
        return impactosPC;
    }

    public void setImpactosPC(int impactosPC) {
        this.impactosPC = impactosPC;
    }

    public int getTotalCeldas() {
        return totalCeldas;
    }

    public void setTotalCeldas(int totalCeldas) {
        this.totalCeldas = totalCeldas;
    }

    public List<String> getHistorialDisparos() {
        return historialDisparos;
    }

    public void setHistorialDisparos(List<String> historialDisparos) {
        this.historialDisparos = historialDisparos;
    }

    public List<String> getHistorialDisparosCPU() {
        return historialDisparosCPU;
    }

    public void setHistorialDisparosCPU(List<String> historialDisparosCPU) {
        this.historialDisparosCPU = historialDisparosCPU;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public int[] getBarcos() {
        return barcos;
    }

    public void setBarcos(int[] barcos) {
        this.barcos = barcos;
    }

    public boolean[][] getCeldasOcupadas() {
        return celdasOcupadas;
    }

    public void setCeldasOcupadas(boolean[][] celdasOcupadas) {
        this.celdasOcupadas = celdasOcupadas;
    }

    public List<int[]> getUltimoDisparoJugador() {
        return ultimoDisparoJugador;
    }

    public void setUltimoDisparoJugador(List<int[]> ultimoDisparoJugador) {
        this.ultimoDisparoJugador = ultimoDisparoJugador;
    }

    public List<int[]> getUltimoDisparoCPU() {
        return ultimoDisparoCPU;
    }

    public void setUltimoDisparoCPU(List<int[]> ultimoDisparoCPU) {
        this.ultimoDisparoCPU = ultimoDisparoCPU;
    }

    public int getBarcoActualIndex() {
        return barcoActualIndex;
    }

    public void setBarcoActualIndex(int barcoActualIndex) {
        this.barcoActualIndex = barcoActualIndex;
    }

    public boolean isColocandoBarcos() {
        return colocandoBarcos;
    }

    public void setColocandoBarcos(boolean colocandoBarcos) {
        this.colocandoBarcos = colocandoBarcos;
    }
}
