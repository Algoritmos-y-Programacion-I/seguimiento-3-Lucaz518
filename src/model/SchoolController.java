package controller;

import model.Computer;
import model.Incident;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase que gestiona la lógica de negocio y los contenedores de datos.
 */
public class SchoolController {

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Agregue los atributos (relaciones) necesarios para satisfacer los
     * requerimientos.
     */
    private List<Computer> computerList;
    private List<Incident> incidentList;
    private int nextIncidentId = 1;

    public SchoolController() {
        this.computerList = new ArrayList<>();
        this.incidentList = new ArrayList<>();
        initializeComputers(); 
    }

    private void initializeComputers() {
        int count = 0;
        for (int floor = 1; floor <= 5; floor++) {
            for (int row = 1; row <= 2; row++) {
                for (int column = 1; column <= 5; column++) {
                    count++;
                    String hostname = String.format("PC-%d-P%dF%dC%d", count, floor, row, column);
                    computerList.add(new Computer(hostname, floor, row, column));
                }
            }
        }
    }

    public Computer getComputerByHostname(String hostname) {
        return computerList.stream()
                .filter(c -> c.getHostname().equalsIgnoreCase(hostname))
                .findFirst()
                .orElse(null);
    }

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Los siguientes metodos estan incompletos.
     * Añada los metodos que considere hagan falta para satisfacer los
     * requerimientos.
     */

    public boolean agregarComputador(String hostname, int floor, int row, int column) {
        if (getComputerByHostname(hostname) == null) {
            computerList.add(new Computer(hostname, floor, row, column));
            return true;
        }
        return false;
    }

    public Incident agregarIncidenteEnComputador(String hostname, String detail) {
        Computer computer = getComputerByHostname(hostname);
        
        if (computer != null) {
            Incident newIncident = new Incident(nextIncidentId++, detail, computer);
            incidentList.add(newIncident);
            return newIncident;
        }
        return null;
    }

    public List<Computer> getComputerList() {
        return computerList;
    }
    
    // Método para la Opción 3 (Consulta de piso con más problemas)
    public int getWorstFloor() {
        Map<Integer, Integer> floorIncidentCounts = new HashMap<>();

        for (Incident i : incidentList) {
            if (!i.isResolved()) {
                int floor = i.getComputer().getFloor();
                floorIncidentCounts.put(floor, floorIncidentCounts.getOrDefault(floor, 0) + 1);
            }
        }

        int maxIncidents = -1;
        int worstFloor = -1;

        for (Map.Entry<Integer, Integer> entry : floorIncidentCounts.entrySet()) {
            if (entry.getValue() > maxIncidents) {
                maxIncidents = entry.getValue();
                worstFloor = entry.getKey();
            }
        }
        return worstFloor;
    }

    public List<Incident> getIncidentsForWorstFloor() {
        int worstFloor = getWorstFloor();
        if (worstFloor == -1) {
            return new ArrayList<>();
        }

        return incidentList.stream()
                .filter(i -> i.getComputer().getFloor() == worstFloor && !i.isResolved())
                .collect(Collectors.toList());
    }
     
    // Método auxiliar para la vista
    public List<Incident> getIncidentList() {
        return incidentList;
    }
    
    public boolean resolveIncident(int incidentId) {
        for (Incident i : incidentList) {
            if (i.getIncidentId() == incidentId && !i.isResolved()) {
                i.setResolved(true);
                return true;
            }
        }
        return false;
    }
}