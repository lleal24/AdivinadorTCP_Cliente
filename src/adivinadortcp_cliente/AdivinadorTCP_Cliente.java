/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adivinadortcp_cliente;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author user
 */
public class AdivinadorTCP_Cliente {

    public static void main(String[] args) throws IOException {
        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;
        int contador = 1;

        try {
            //creacion del socket del lado cliente se define ip y socket de destino
            socketCliente = new Socket("localhost", 4444);
            //Obtenemos canal de entrada
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            //Obtenemos canal de salida
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

        } catch (IOException ex) {
            System.err.println("No puede establer conexión con el servidor");
            System.exit(-1);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String linea;

        try {
            for(int i=1; i<=10; i++){
                System.out.println("Ingresa numero Intento "+contador+"/10");
                linea = stdIn.readLine();
                salida.println(linea);
                linea = entrada.readLine();
                System.out.println("Ans Server: " + linea);
                contador++;
            }
            /*
            while (true) {
                // Leo la entrada del usuario
                linea = stdIn.readLine();
                // La envia al servidor
                salida.println(linea);
                // Envía a la salida estándar la respuesta del servidor
                linea = entrada.readLine();
                System.out.println("Respuesta servidor: " + linea);
                // Si es "Adios" es que finaliza la comunicación
                if (linea.equals("Adios")) {
                    break;
                }
            }
            */
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        salida.close();
        entrada.close();
        stdIn.close();
        socketCliente.close();
    }

}
