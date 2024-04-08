package Example_01_variant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorDeEco {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8189);
            Socket entrante = s.accept();
            try {
                InputStream secuenciaDeEntrada = entrante.getInputStream();
                OutputStream secuenciaDeSalida = entrante.getOutputStream();
                Scanner in = new Scanner(secuenciaDeEntrada);
                PrintWriter out = new PrintWriter(secuenciaDeSalida, true);
                out.println("¡Hola! Escriba ADIOS para salir.");
                boolean terminado = false;
                Scanner sc = new Scanner(System.in);
                while (!terminado && (in.hasNextLine() || sc.hasNextLine())) {
                    if (in.hasNextLine()) {
                        String linea = in.nextLine();
                        System.out.println("Mensaje recibido del cliente: " + linea); // Agregamos esta línea para imprimir el mensaje recibido del cliente en la consola de IntelliJ IDEA
                        out.println("Eco:" + linea);
                        if (linea.trim().equals("ADIOS"))
                            terminado = true;
                    }
                    if (sc.hasNextLine()) {
                        String lineaout = sc.nextLine();
                        System.out.println("Mensaje enviado al cliente: " + lineaout); // Agregamos esta línea para imprimir el mensaje enviado al cliente en la consola de IntelliJ IDEA
                        out.println("Eco:" + lineaout);
                    }
                }
            } finally {
                entrante.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
