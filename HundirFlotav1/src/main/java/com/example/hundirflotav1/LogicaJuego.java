package com.example.hundirflotav1;

import java.util.ArrayList;

public class LogicaJuego {

    private EstadoJuego estado;

    public LogicaJuego() {
        this.estado = new EstadoJuego();
        inicializarJuego();
    }

    public LogicaJuego(EstadoJuego estadoCargado) {
        this.estado = estadoCargado;
    }

    public EstadoJuego getEstado() {
        return estado;
    }


    public void inicializarJuego() {
        char[][] tableroJugador = new char[10][10];
        char[][] tableroPC = new char[10][10];
        char[][] tableroDisparosJugador = new char[10][10];
        char[][] tableroDisparosPC = new char[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tableroJugador[i][j] = '~';
                tableroPC[i][j] = '~';
                tableroDisparosJugador[i][j] = '~';
                tableroDisparosPC[i][j] = '~';
            }
        }

        estado.setTableroJugador(tableroJugador);
        estado.setTableroPC(tableroPC);
        estado.setTableroDisparosJugador(tableroDisparosJugador);
        estado.setTableroDisparosPC(tableroDisparosPC);
        estado.setImpactosJugador(0);
        estado.setImpactosPC(0);
        estado.setJuegoTerminado(false);
        estado.setGanador(null);

        int total = 0;
        for (int v : estado.getBarcos()) {
            total += v;
        }
        estado.setTotalCeldas(total);
        estado.setCeldasOcupadas(new boolean[10][10]);
        estado.setColocandoBarcos(true);
        estado.setBarcoActualIndex(0);
        estado.setHistorialDisparos(new ArrayList<>());
        estado.setHistorialDisparosCPU(new ArrayList<>());
        estado.setUltimoDisparoJugador(new ArrayList<>());
        estado.setUltimoDisparoCPU(new ArrayList<>());

        colocarBarcosPC();
    }

    private void colocarBarcosPC() {
        for (int longitud : estado.getBarcos()) {
            boolean colocado = false;
            while (!colocado) {
                int fila = (int)(Math.random() * 10);
                int columna = (int)(Math.random() * 10);
                int orientacion = (int)(Math.random() * 2);
                colocado = colocarBarco(estado.getTableroPC(), longitud, fila, columna, orientacion);
            }
        }
    }

    private boolean colocarBarco(char[][] tablero, int longitud, int fila, int columna, int orientacion) {
        if (orientacion == 0) {
            if (columna + longitud > 10) return false;
            for (int j = columna; j < columna + longitud; j++) {
                if (tablero[fila][j] != '~') return false;
            }
            for (int j = columna; j < columna + longitud; j++) {
                tablero[fila][j] = 'B';
            }
        } else {
            if (fila + longitud > 10) return false;
            for (int i = fila; i < fila + longitud; i++) {
                if (tablero[i][columna] != '~') return false;
            }
            for (int i = fila; i < fila + longitud; i++) {
                tablero[i][columna] = 'B';
            }
        }
        return true;
    }

    public String colocarBarcoJugador(int fila, int columna, int orientacion) {
        int indice = estado.getBarcoActualIndex();
        if (indice >= estado.getBarcos().length) {
            return "Ya colocaste todos los barcos";
        }

        int longitud = estado.getBarcos()[indice];

        if (orientacion == 0) {
            if (columna + longitud > 10) {
                return "El barco se sale del tablero";
            }
            for (int j = columna; j < columna + longitud; j++) {
                if (estado.getTableroJugador()[fila][j] != '~') {
                    return "Hay otro barco en esa posicion";
                }
            }
        } else {
            if (fila + longitud > 10) {
                return "El barco se sale del tablero";
            }
            for (int i = fila; i < fila + longitud; i++) {
                if (estado.getTableroJugador()[i][columna] != '~') {
                    return "Hay otro barco en esa posicion";
                }
            }
        }

        if (orientacion == 0) {
            for (int j = columna; j < columna + longitud; j++) {
                estado.getTableroJugador()[fila][j] = 'B';
                estado.getCeldasOcupadas()[fila][j] = true;
            }
        } else {
            for (int i = fila; i < fila + longitud; i++) {
                estado.getTableroJugador()[i][columna] = 'B';
                estado.getCeldasOcupadas()[i][columna] = true;
            }
        }

        estado.setBarcoActualIndex(indice + 1);

        if (estado.getBarcoActualIndex() >= estado.getBarcos().length) {
            estado.setColocandoBarcos(false);
            return "Barcos colocados. Empieza el juego";
        }

        return "Barco colocado. Siguiente barco de " + estado.getBarcos()[estado.getBarcoActualIndex()] + " casillas";
    }

    public String disparoJugador(int fila, int columna) {
        if (estado.isJuegoTerminado()) return "El juego ya termino";
        if (estado.isColocandoBarcos()) return "Primero coloca todos tus barcos";
        if (estado.getTableroDisparosJugador()[fila][columna] == 'X' ||
                estado.getTableroDisparosJugador()[fila][columna] == '-') {
            return "Ya disparaste ahi";
        }

        String coordenada = "" + (char)('A' + fila) + (columna + 1);
        String registro;

        if (estado.getTableroPC()[fila][columna] == 'B') {
            estado.getTableroDisparosJugador()[fila][columna] = 'X';
            estado.getTableroPC()[fila][columna] = 'X';
            estado.setImpactosJugador(estado.getImpactosJugador() + 1);

            registro = "JUGADOR " + coordenada + " IMPACTO";
            estado.getUltimoDisparoJugador().add(new int[]{fila, columna, 1});
        } else {
            estado.getTableroDisparosJugador()[fila][columna] = '-';
            estado.getTableroPC()[fila][columna] = '-';

            registro = "JUGADOR " + coordenada + " AGUA";
            estado.getUltimoDisparoJugador().add(new int[]{fila, columna, 0});
        }

        estado.getHistorialDisparos().add(registro);

        if (estado.getImpactosJugador() >= estado.getTotalCeldas()) {
            estado.setJuegoTerminado(true);
            estado.setGanador("Jugador");
            return "GANASTE. Hundiste todos los barcos";
        }

        return registro;
    }

    public String disparoCPU() {
        if (estado.isJuegoTerminado()) return "El juego ya termino";

        int fila, columna;
        boolean valido = false;

        while (!valido) {
            fila = (int)(Math.random() * 10);
            columna = (int)(Math.random() * 10);

            if (estado.getTableroDisparosPC()[fila][columna] != 'X' &&
                    estado.getTableroDisparosPC()[fila][columna] != '-') {
                valido = true;
                String registro = "CPU disparo a " + (char)('A' + fila) + columna + ": ";

                if (estado.getTableroJugador()[fila][columna] == 'B') {
                    estado.getTableroDisparosPC()[fila][columna] = 'X';
                    estado.getTableroJugador()[fila][columna] = 'X';
                    estado.setImpactosPC(estado.getImpactosPC() + 1);
                    registro = registro + "IMPACTO";
                    estado.getUltimoDisparoCPU().add(new int[]{fila, columna, 1});
                } else {
                    estado.getTableroDisparosPC()[fila][columna] = '-';
                    estado.getTableroJugador()[fila][columna] = '-';
                    registro = registro + "AGUA";
                    estado.getUltimoDisparoCPU().add(new int[]{fila, columna, 0});
                }

                estado.getHistorialDisparosCPU().add(registro);

                if (estado.getImpactosPC() >= estado.getTotalCeldas()) {
                    estado.setJuegoTerminado(true);
                    estado.setGanador("CPU");
                    return "PERDISTE. La CPU hundio todos tus barcos";
                }

                return registro;
            }
        }
        return "";
    }

    public void deshacerUltimoTurno() {
        if (!estado.getUltimoDisparoJugador().isEmpty() && !estado.getUltimoDisparoCPU().isEmpty()) {

            int[] ultimoJugador = estado.getUltimoDisparoJugador().remove(estado.getUltimoDisparoJugador().size() - 1);
            int filaJ = ultimoJugador[0];
            int colJ = ultimoJugador[1];
            int tipoJ = ultimoJugador[2];

            if (tipoJ == 1) {
                estado.getTableroDisparosJugador()[filaJ][colJ] = '~';
                estado.getTableroPC()[filaJ][colJ] = 'B';
                estado.setImpactosJugador(estado.getImpactosJugador() - 1);
            } else {
                estado.getTableroDisparosJugador()[filaJ][colJ] = '~';
                estado.getTableroPC()[filaJ][colJ] = 'B';
            }

            if (!estado.getHistorialDisparos().isEmpty()) {
                estado.getHistorialDisparos().remove(estado.getHistorialDisparos().size() - 1);
            }

            int[] ultimoCPU = estado.getUltimoDisparoCPU().remove(estado.getUltimoDisparoCPU().size() - 1);
            int filaC = ultimoCPU[0];
            int colC = ultimoCPU[1];
            int tipoC = ultimoCPU[2];

            if (tipoC == 1) {
                estado.getTableroDisparosPC()[filaC][colC] = '~';
                estado.getTableroJugador()[filaC][colC] = 'B';
                estado.setImpactosPC(estado.getImpactosPC() - 1);
            } else {
                estado.getTableroDisparosPC()[filaC][colC] = '~';
                estado.getTableroJugador()[filaC][colC] = 'B';
            }

            if (!estado.getHistorialDisparosCPU().isEmpty()) {
                estado.getHistorialDisparosCPU().remove(estado.getHistorialDisparosCPU().size() - 1);
            }
        }
    }

    public int calcularVidasJugador() {
        int vidas = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (estado.getTableroJugador()[i][j] == 'B') {
                    vidas++;
                }
            }
        }
        return vidas;
    }

    public int calcularVidasCPU() {
        int vidas = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (estado.getTableroPC()[i][j] == 'B') {
                    vidas++;
                }
            }
        }
        return vidas;
    }
}