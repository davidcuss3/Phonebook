import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class AddressBook {
    private Map<String, String> contacts;

    // Constructor
    public AddressBook() {
        contacts = new HashMap<>();
    }

    // Método para cargar contactos desde un archivo
    public void load(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            // Si el archivo no existe, solo muestra un mensaje y no intenta cargar
            System.out.println("El archivo no existe, se creará uno nuevo cuando guardes los contactos.");
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                contacts.put(parts[0], parts[1]);
            }
        }
        reader.close();
    }

    // Método para guardar los contactos en un archivo
    public void save(String filename) throws IOException {
        File file = new File(filename);
        // Crear el archivo si no existe
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue());
            writer.newLine();
        }
        writer.close();
    }

    // Método para mostrar la lista de contactos
    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos en la agenda.");
            return;
        }

        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Método para crear un nuevo contacto
    public void create(String number, String name) {
        contacts.put(number, name);
    }

    // Método para borrar un contacto por número
    public void delete(String number) {
        contacts.remove(number);
    }

    // Menú interactivo
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        String filename = "contacts.csv";

        // Intentar cargar los contactos desde el archivo
        try {
            addressBook.load(filename);
            System.out.println("Contactos cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo. Iniciando con una agenda vacía.");
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar contactos");
            System.out.println("2. Crear nuevo contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Guardar y salir");
            System.out.print("Selecciona una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Introduce el número: ");
                    String number = scanner.nextLine();
                    System.out.print("Introduce el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case 3:
                    System.out.print("Introduce el número del contacto a borrar: ");
                    String numberToDelete = scanner.nextLine();
                    addressBook.delete(numberToDelete);
                    break;
                case 4:
                    try {
                        addressBook.save(filename);
                        System.out.println("Cambios guardados. Saliendo...");
                    } catch (IOException e) {
                        System.out.println("Error al guardar los contactos.");
                    }
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
}
