package model;

import java.time.LocalDateTime;

/**
 * Clase que representa un incidente reportado en una Computadora.
 */
public class Incident {

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Agregue los atributos (relaciones) necesarios para satisfacer los
     * requerimientos.
     */
    private int incidentId;
    private String date; 
    private String detail;
    private boolean isResolved;
    private Computer computer; 

    public Incident(int incidentId, String detail, Computer computer) {
        this.incidentId = incidentId;
        this.date = LocalDateTime.now().toString(); 
        this.detail = detail;
        this.isResolved = false; 
        this.computer = computer;
    }
    
    // Getters y Setters

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public String getDate() {
        return date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    @Override
    public String toString() {
        String estado = isResolved ? "RESUELTO" : "PENDIENTE";
        return String.format("Incidente #%d (%s) - Fecha: %s - Equipo: %s - Detalle: %s",
                incidentId, estado, date, computer.getHostname(), detail);
    }
}