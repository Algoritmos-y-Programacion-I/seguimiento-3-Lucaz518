package ui;

import controller.SchoolController;
import java.util.List;
import java.util.Scanner;
import model.Incident;

public class SchoolApp {

    private SchoolController controller;
    private Scanner input;

    public static void main(String[] args) {
        SchoolApp ui = new SchoolApp();
        ui.menu();
    }

    public SchoolApp() {
        input = new Scanner(System.in);
        controller = new SchoolController(); 
    }

    public void menu() {
        System.out.println("Bienvenido a Computaricemos");

        int option = 0;
        do {
            System.out.println("MENU PRINCIPAL");
            System.out.println("--------------------------------------------------------");
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
                        System.out.println("gracias por usar nuestros servicios, Adios!");
                        break;
                    default:
                        System.out.println("Opcion invalida,intenta nuevamente.");
                        break;
                }
            } else {
                System.out.println("Entrada no valida. Por favor dijite un numero.");
                input.nextLine();
                option = -1;
            }

        } while (option != 0);

    }

    public void registrarComputador() {
        System.out.println("/-/-/-/-/-/-/Registro de Computador/-/-/-/-/-/-/");
        System.out.print("Ingrese el Nombre de Host: ");
        String hostname = input.nextLine();

        System.out.print("Ingrese el piso,(1-5): ");
        if (!input.hasNextInt()) {
            System.out.println("ERROR!! elpiso debe ser un numero entero (1-5)");
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
            System.out.println("CUIDADO! ya esxiste un computador con el Hostname: " + hostname + ".");
        }
    }

    public void registrarIncidenteEnComputador() {
        System.out.println("/-/-/-/-/Registro de Incidente/-/-/-/-/");
        System.out.print("Ingresa el HostName del computador afectado: ");
        String hostname = input.nextLine();

        System.out.print("Ingreda la descripcion del Incidente: ");
        String detail = input.nextLine();

        Incident incident = controller.agregarIncidenteEnComputador(hostname, detail);

        if (incident != null) {
            System.out.println("El incidente #" + incident.getIncidentId() + " Esta reportado para: " + hostname + ".");
        } else {
            System.out.println("CUIDADO! el computador con el hostname: " + hostname + " No a sido encontrado.");
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

    public void marcarIncidenteResuelto() {
        System.out.println("\n--- Marcar Incidente como Resuelto ---");
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
    
    public void listarIncidentesPendientes() {
        System.out.println("\n--- Incidentes PENDIENTES ---");
        
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