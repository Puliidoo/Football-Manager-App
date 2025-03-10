package FootballManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<String> fichajes;
    private static final String fileName = "src/FootballManager/mercat_fitxages.txt";
    private static final Path filePath = Paths.get(fileName);
    private static List<Entrenador> entrenadores = new ArrayList<>();
    private static List<Jugador> jugadores = new ArrayList<>();
    private static List<Persona> personas = new ArrayList<>();
    private static ArrayList<Equip> equips = new ArrayList<Equip>();

    public static void main(String[] args) throws IOException {
        DarAltaEquipos gestorEquips = new DarAltaEquipos();
        BaixaEquips eliminador = new BaixaEquips(gestorEquips.getEquips());
        AltaJugadorEntrenador jugadorEntrenador = new AltaJugadorEntrenador();

        Entrenador entrenador = new Entrenador();
        Jugador jugador = new Jugador();
        President president = new President();
        Lliga lliga = new Lliga();
        MercatFitxages mercat = new MercatFitxages();

        String nom = null;


        fichajes = cargarFichajes();
        menuPrincipal(gestorEquips, eliminador, jugadorEntrenador, nom);
    }


    public static void menuPrincipal(DarAltaEquipos gestorEquips, BaixaEquips eliminador, AltaJugadorEntrenador jugadorEntrenador, String nom) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = false;

        do {
            System.out.println("<---------------MENU--------------->");
            System.out.println("Welcome to Politecnics Football Manager");
            System.out.println("1- Veure classificacio lliga actual 🏆");
            System.out.println("2- Gestionar equip ⚽");
            System.out.println("3- Donar d'alta equip");
            System.out.println("4- Donar d'alta jugador/a o entrenador/a");
            System.out.println("5- Consultar dades equip");
            System.out.println("6- Consultar dades jugador/a equip");
            System.out.println("7- Disputar nova lliga");
            System.out.println("8- Realitzar sessió entrenament (mercat/fitxages)");
            System.out.println("9- Transferir jugador/a");
            System.out.println("10- Desar dades equips");
            System.out.println("0- Sortir");
            System.out.println("<---------------MENU---------------> \n");
            System.out.println("Introdueix la teva opcio: ");

            try {
                int opcio = sc.nextInt(); // Intentar leer un entero

                if (opcio < 0 || opcio > 10) {
                    System.out.println("Opcio no valida. Torna a intentar");
                } else {
                    switch (opcio) {
                        case 1:
                            break;
                        case 2:
                            mostrarmenusecundario(eliminador);
                            break;
                        case 3:
                            darAltaEquipos(nom);
                            break;
                        case 4:
                            menuPersona();
                            break;
                        case 5:
                            consultarDadesEquip(nom);
                            break;
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 0:
                            continuar = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error. Debes ingresar un número entero...");
                sc.next();
            }
        } while (!continuar);
    }

    // 3.- DONAR ALTA EQUIP

    public static void darAltaEquipos(String nom) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = false;
        boolean continuar2 = false;
        int any_fundacio = 0;
        int numeroEquipos = 0;

        do {
            System.out.println("Quants equips vols crear? \n 0.- Para salir: ");
            numeroEquipos = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            if (numeroEquipos < 0) {
                System.out.println("El numero no puede ser negativo ni 0");
            } else if (numeroEquipos == 0) {
                continuar = true;
            } else {
                for (int i = 0; i < numeroEquipos; i++) {
                    System.out.print("Nom del equip " + (i + 1) + ": ");
                    nom = scanner.nextLine().toLowerCase();

                    // Verificar si el equipo ya existe
                    boolean existe = false;
                    for (Equip equip : equips) {
                        if (equip.getNom().equals(nom)) {
                            existe = true;
                            break;
                        }
                    }

                    if (existe) {
                        System.out.println("El nombre de este equipo ya existe. No se ha creado el equipo.");
                        continue; // Saltar al siguiente ciclo del for
                    }

                    do {
                        System.out.println("Introduce el año de fundación: ");
                        any_fundacio = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer
                        if (any_fundacio < 1857 || any_fundacio > 2025) {
                            System.out.println("El equipo no puede ser tan antiguo, o al menos no tanto como el equipo más antiguo del mundo...");
                            continuar2 = false;
                        } else {
                            continuar2 = true;
                        }
                    } while (!continuar2);

                    System.out.println("Introduce la ciudad del equipo: ");
                    String ciudad = scanner.nextLine().toLowerCase();

                    // Agregar el nuevo equipo a la lista
                    equips.add(new Equip(nom, any_fundacio, ciudad));
                    System.out.println("Equip creat amb èxit!");
                }
                continuar = true;
            }
        } while (!continuar);
    }

    // 4.- DONAR ALTA JUGADOR O ENTRENADOR - ÁNGEL PÉREZ MORENO

    private static void menuPersona() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = false;
        String persona = null;
        String nom = null;
        String cognom = null;
        String fecha = null;
        int motivacio = 5;
        int dorsal = 0;
        double sou_anual = 0;
        String posicio = null;
        boolean seleccionador = false;
        String seleccionador2 = null;
        int tornejos = 0;
        final int min = 30;
        final int max = 100;

        do {
            System.out.println("Quieres entrenador o jugador?: ");
            persona = scanner.nextLine().toLowerCase();
            if (persona.equals("entrenador")) {
                nomPersona(nom, cognom, fecha);
                motivacio(motivacio);
                seleccionadorEntrenador(seleccionador, seleccionador2);
                tornejosEntrenador(tornejos);
                souAnual(sou_anual);
                entrenadores.add(new Entrenador(nom, cognom, fecha, motivacio, seleccionador, tornejos, sou_anual));
                continuar = true;
            } else if (persona.equals("jugador")) {
                nomPersona(nom, cognom, fecha);
                motivacio(motivacio);
                souAnual(sou_anual);
                dorsal(dorsal);
                posicioJugador(posicio);
                int qualitat = numeroRandom(min, max);
                jugadores.add(new Jugador(nom, cognom, fecha, motivacio, sou_anual, dorsal, posicio, qualitat));
                continuar = true;
            } else {
                System.out.println("Opcio no valida. Torna a intentar...");
            }
        } while (!continuar);
    }

    private static void tornejosEntrenador(int tornejos) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = false;
        do {
            System.out.println("Tornejos Guanyats: ");
            tornejos = scanner.nextInt();
            if (tornejos < 0) {
                System.out.println("Numero incorrecto, no puede ser negativo.");
                continuar = false;
            }
            continuar = true;
        } while (!continuar);
    }

    private static void seleccionadorEntrenador(boolean seleccionador, String seleccionador2) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = false;
        do {
            System.out.println("Es seleccionador? (si o no): ");
            seleccionador2 = scanner.nextLine().toLowerCase();
            if (seleccionador2.equals("si")) {
                seleccionador = true;
                continuar = true;
            } else if (seleccionador2.equals("no")) {
                continuar = true;
            } else {
                continuar = false;
            }
        } while (!continuar);
    }

    private static int numeroRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private static void posicioJugador(String posicio) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = false;
        do {
            System.out.println(" - Porter \n - Defensa \n - Migcampista \n - Davanter");
            System.out.println("Posicio del jugador: ");
            posicio = scanner.nextLine().toLowerCase();
            if (posicio.equals("porter") || posicio.equals("defensa") || posicio.equals("migcampista") || posicio.equals("davanter")) {
                continuar = true;
            } else {
                System.out.println("Introduce otra posicion");
            }
        } while (!continuar);
    }

    private static void souAnual(double sou_anual) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sou Anual: ");
        sou_anual = scanner.nextDouble();
    }

    private static void nomPersona(String nom, String cognom, String fecha) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nom: ");
        nom = scanner.nextLine();
        System.out.println("Cognom: ");
        cognom = scanner.nextLine();
        System.out.println("Data de Naixement: ");
        fecha = scanner.nextLine();


    }

    private static void motivacio(int motivacio) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = false;

        do {
            System.out.println("Nivell de motivació (1-10): ");
            motivacio = scanner.nextInt();
            if (motivacio < 1 || motivacio > 10) {
                System.out.println("Motivacio no valida. Torna a intentar");
            } else {
                continuar = true;
            }
        } while (!continuar);
    }

    private static void dorsal(int dorsal) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar2 = false;
        do {
            System.out.println("Dorsal del Jugador: ");
            dorsal = scanner.nextInt();
            if (dorsal > 99 || dorsal <= 0) {
                System.out.println("No puede ser más grande de 99, más pequeño o igual a 0");
            } else {
                continuar2 = true;
            }
        } while (!continuar2);
    }

    // 5.- CONSULTAR DADES EQUIP

    private static void consultarDadesEquip(String nom) {
        if (equips.isEmpty()) {
            System.out.println("No hay equipos creados.");
        } else {
            for (Equip equip : equips) {
                System.out.println(equip);
            }
        }
    }

    // DAVID

    private static void mostrarmenusecundario(BaixaEquips eliminador) {
        boolean continuar2 = false;
        int opcio2 = 0;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1- Donar de Baixa equip");
            System.out.println("2- Modificar President/a");
            System.out.println("3- Destruir Entrenador");
            System.out.println("4- Fitxar jugador/a o entrenador/a");
            System.out.println("0- Sortir \n");
            System.out.println("Introdueix la teva opcio: ");
            opcio2 = scanner.nextInt();
            if (opcio2 > 4 || opcio2 < 0) {
                System.out.println("Opcio no valida. Torna a intentar");
                continuar2 = false;
            } else {
                switch (opcio2) {
                    case 1:
                        eliminador.eliminarEquip();
                        eliminador.mostrarEquips();
                        break;
                    case 2:

                    case 3:
                    case 4:
                    case 0:
                        continuar2 = true;
                }
            }
        } while (!continuar2);

    }

    private static List<String> cargarFichajes() throws IOException {
        if (Files.exists(filePath)) {
            return (Files.readAllLines(filePath));
        } else {
            return (new ArrayList<>());
        }
    }
}