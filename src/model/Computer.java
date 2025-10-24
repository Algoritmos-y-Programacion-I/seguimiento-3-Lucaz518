package model;

/**
 * Clase que representa una computadora en el edificio.
 */
public class Computer {

    private String hostname;
    private int floor;
    private int row;
    private int column;

    public Computer(String hostname, int floor, int row, int column) {
        this.hostname = hostname;
        this floor = floor;
        this.row = row;
        this.column = column;
    }

    public Computer() {
    }

    /* Getters y Setters */

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    @Override
    public String toString() {
        return "Computer [Hostname=" + hostname + ", Ubicacion=Piso " + floor + ", Fila " + row + ", Columna " + column + "]";
    }
}