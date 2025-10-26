package ui;

import controller.SchoolController;
import model.Incident;

import java.util.List;
import java.util.Scanner;

public class SchoolApp {

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Agregue los atributos (relaciones) necesarios para conectar esta clase con el
     * modelo.
     */
    private SchoolController controller;
    private Scanner input;

    public static void main(String[] args) {

        SchoolApp ui = new SchoolApp();
        ui.menu();

    }

    // Constructor
    public SchoolApp() {
        input = new Scanner(System.in);
        controller = new SchoolController();
    }

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * El siguiente metodo esta incompleto.
     * Agregue la logica necesaria (instrucciones) para satisfacer los
     * requerimientos
     */
    public void menu() {

        System.out.println("Bienvenido a Computaricemos");

        int option = 0;
        do {
            System.out.println("\nMenu Principal");
            System.out.println("/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
            System.out.println("Digite alguna de las siguientes opciones");
            System.out.println("1) Registrar computador");
            System.out.println("2) Registrar incidente en computador");
            System.out.println("3) Consultar incidentes del piso con más problemas");
            System.out.println("4) Marcar incidente como resuelto"); 
            System.out.println("5) Listar todos los incidentes PENDIENTES"); 
            System.out.println("0) Salir del sistema");
            
            if (input.hasNextInt()) {
                option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 1:
                        registrarComputador();
                        break;
                    case 2:
                        registrarIncidenteEnComputador();
                        break;
                    case 3:
                        consultarComputadorConMasIncidentes();
                        break;
                    case 4:
                        marcarIncidenteResuelto();
                        break;
                    case 5:
                        listarIncidentesPendientes();
                        break;
                    case 0:
                        System.out.println("\nGracias por usar nuestros servicios. Adios!");
                        break;
                    default:
                        System.out.println("\nOpcion invalida. Intente nuevamente.");
                        break;
                }
            } else {
                System.out.println("\nEntrada no válida. Por favor, digite un número.");
                input.nextLine();
                option = -1;
            }

        } while (option != 0);

    }

    /*
     * ATENCION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Los siguientes metodos estan incompletos.
     * Agregue la logica necesaria (instrucciones) para satisfacer los
     * requerimientos
     */

    public void registrarComputador() {
        System.out.println("Registro de Computador");
        System.out.print("Ingrese Hostname (Serial): ");
        String hostname = input.nextLine();

        System.out.print("Ingrese Piso (1-5): ");
        if (!input.hasNextInt()) {
            System.out.println("ERROR: El piso debe ser un número entero.");
            input.nextLine();
            return;
        }
        int floor = input.nextInt();
        
        System.out.print("Ingrese Fila: ");
        if (!input.hasNextInt()) {
            System.out.println("ERROR: La fila debe ser un número entero.");
            input.nextLine();
            return;
        }
        int row = input.nextInt();
        
        System.out.print("Ingrese Columna: ");
        if (!input.hasNextInt()) {
            System.out.println("ERROR: La columna debe ser un número entero.");
            input.nextLine();
            return;
        }
        int column = input.nextInt();
        input.nextLine();

        if (controller.agregarComputador(hostname, floor, row, column)) {
            System.out.println("Computador " + hostname + " registrado correctamente.");
        } else {
            System.out.println("ERROR: Ya existe un computador con el hostname " + hostname + ".");
        }
    }

    public void registrarIncidenteEnComputador() {
        System.out.println("Registro de Incidente");
        System.out.print("Ingrese Hostname del computador afectado: ");
        String hostname = input.nextLine();

        System.out.print("Ingrese la descripción del incidente: ");
        String detail = input.nextLine();

        Incident incident = controller.agregarIncidenteEnComputador(hostname, detail);

        if (incident != null) {
            System.out.println("Incidente #" + incident.getIncidentId() + " reportado para " + hostname + ".");
        } else {
            System.out.println("ERROR: El computador con Hostname " + hostname + " no fue encontrado.");
        }
    }

    public void consultarComputadorConMasIncidentes() {
        int worstFloor = controller.getWorstFloor();
        
        if (worstFloor == -1) {
            System.out.println("\nActualmente, no hay incidentes pendientes registrados.");
            return;
        }

        List<Incident> worstIncidents = controller.getIncidentsForWorstFloor();
        
        System.out.println("\n--- Incidentes Pendientes del Piso con Más Problemas (Piso " + worstFloor + ") ---");
        
        for (Incident incident : worstIncidents) {
            System.out.println(incident.toString());
        }
    }

    // Funcionalidad adicional para la vista
    public void marcarIncidenteResuelto() {
        System.out.println("Marcar Incidente como Resuelto");
        System.out.print("Ingrese el ID del incidente a resolver: ");
        
        if (input.hasNextInt()) {
            int incidentId = input.nextInt();
            input.nextLine();
            
            if (controller.resolveIncident(incidentId)) {
                System.out.println("Incidente #" + incidentId + " ha sido marcado como resuelto.");
            } else {
                System.out.println("ERROR: Incidente #" + incidentId + " no encontrado o ya estaba resuelto.");
            }
        } else {
            System.out.println("ID de incidente no válido.");
            input.nextLine();
        }
    }
    
    // Funcionalidad adicional para la vista
    public void listarIncidentesPendientes() {
        System.out.println("Incidentes PENDIENTES");
        
        List<Incident> allIncidents = controller.getIncidentList();
        boolean foundPending = false;
        
        for (Incident i : allIncidents) {
            if (!i.isResolved()) {
                System.out.println(i.toString());
                foundPending = true;
            }
        }
        
        if (!foundPending) {
             System.out.println("No hay incidentes pendientes. Todo en orden.");
        }
    }
}