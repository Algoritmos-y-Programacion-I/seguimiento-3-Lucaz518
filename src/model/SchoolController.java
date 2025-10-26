package controller;

import model.Computer;
import model.Incident;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * lógica de negocio y los contenedores de datos.
 */
public class SchoolController {

    private List<Computer> computerList;
    private List<Incident> incidentList;
    private int nextIncidentId = 1;

    public SchoolController() {
        this.computerList = new ArrayList<>();
        this.incidentList = new ArrayList<>();
        initializeComputers(); 
    }

    /**
     * Inicializa los computadores
     */
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

    /**
     * buscar el comp por nombre 
     */
    public Computer getComputerByHostname(String hostname) {
        return computerList.stream()
                .filter(c -> c.getHostname().equalsIgnoreCase(hostname))
                .findFirst()
                .orElse(null);
    }

    /**
     * Agrega un nuevo computador
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

    /**
     * Encuentra el número de piso con la mayor cantidad de incidentes PENDIENTES.
     */
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
    
    public List<Computer> getComputerList() {
        return computerList;
    }

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