

import java.util.*;


public class Semaforo {
    private String colorActual;
    private int tiempoVerde;
    private int tiempoAmarillo;
    private int tiempoRojo;
    private int tiempoRestante; 

    public Semaforo(String colorActual, int tiempoVerde, int tiempoAmarillo, int tiempoRojo) {
        if (colorActual == null || colorActual.isBlank()) colorActual = "ROJO";
        this.colorActual = colorActual.toUpperCase(); 
        this.tiempoVerde = tiempoVerde;
        this.tiempoAmarillo = tiempoAmarillo;
        this.tiempoRojo = tiempoRojo;
        this.tiempoRestante = getDuracionColor(this.colorActual);
    }


    // Cambia el color al siguiente en el ciclo y reinicia el tiempoRestante
    private void cambiarColorAutomatico() {
        switch (colorActual) { // switch lo que hace es cambiar de color automaticamente
            case "VERDE": // en caso de que el color sea verde entonces pasa a ser amarillo en el cambio 
                colorActual = "AMARILLO";
                tiempoRestante = tiempoAmarillo;
                break;
            case "AMARILLO":
                colorActual = "ROJO";
                tiempoRestante = tiempoRojo;
                break;
            case "ROJO":
            default:
                colorActual = "VERDE";
                tiempoRestante = tiempoVerde;
                break;
        }
    }

    // Método para avanzar el semáforo delta t unidades de tiempo
    public void actualizar(int deltaT) { //delta t es el cambio en el tiempo 
        if (deltaT <= 0) return;
        tiempoRestante -= deltaT;
        while (tiempoRestante <= 0) {
            cambiarColorAutomatico();
            // Si deltaT fue mayor que el tiempo restante, seguir descontando
            // el sobrante del siguiente color
            // Ejemplo: tiempoRestante = -2, entonces restar 2 al siguiente color
            tiempoRestante += getDuracionColor(colorActual);
        }
        // Asegurar que tiempoRestante nunca sea negativo
        tiempoRestante = Math.max(tiempoRestante, 0);
    }

    // Devuelve la duración del color actual
    private int getDuracionColor(String color) {
        switch (color) {
            case "VERDE": return tiempoVerde;
            case "AMARILLO": return tiempoAmarillo;
            case "ROJO": return tiempoRojo;
            default: return tiempoRojo;
        }
    }

    public String getColor() {
        return colorActual;
    }

    public void setDuraciones(int tiempoVerde, int tiempoAmarillo, int tiempoRojo) {
        if (tiempoVerde < 0 || tiempoAmarillo < 0 || tiempoRojo < 0)
            throw new IllegalArgumentException("Los tiempos no pueden ser negativos");
        this.tiempoVerde = tiempoVerde;
        this.tiempoAmarillo = tiempoAmarillo;
        this.tiempoRojo = tiempoRojo;
        // Si el color actual cambió de duración, actualizar tiempoRestante si es mayor a la nueva duración
        int dur = getDuracionColor(colorActual);
        if (tiempoRestante > dur) tiempoRestante = dur;
    }

    public int getTiempoVerde() {
        return tiempoVerde;
    }

    public int getTiempoAmarillo() {
        return tiempoAmarillo;
    }

    public int getTiempoRojo() {
        return tiempoRojo;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    // Método para verificar si el semáforo está en una fase específica
    public boolean estaEnFase(String fase) {
        return colorActual.equals(fase.toUpperCase());
    }

    // Método para obtener el progreso de la fase actual (0.0 a 1.0)
    public double getProgresoFase() {
        int duracionTotal = getDuracionColor(colorActual);
        if (duracionTotal == 0) return 1.0;
        return 1.0 - (double) tiempoRestante / duracionTotal;
    }

    public String toString() {
        return "Semaforo{" +
                "colorActual='" + colorActual + '\'' +
                ", tiempoRestante=" + tiempoRestante +
                ", tiempoVerde=" + tiempoVerde +
                ", tiempoAmarillo=" + tiempoAmarillo +
                ", tiempoRojo=" + tiempoRojo +
                '}';
    }
}
