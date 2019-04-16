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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author user
 */
public class AdivinadorTCP_Cliente {

    public static void main(String[] args) throws IOException {
        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;

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
            //Ciclo que se ejecuta 10 veces equivalente a los 10 intentos que tiene el cliente
            for (int i = 1; i <= 10; i++) {
                //Captura de mensaje del cliente y envio al servidor
                System.out.println("Ingresa numero:");
                linea = stdIn.readLine();
                salida.println(linea);

                //Recepcion de mensaje del servidor e impresion
                linea = entrada.readLine();
                System.out.println("Ans Server: " + linea);

                //Creacion de patron palabara Clave al inicio del String "Adivinaste"
                Pattern expresion = Pattern.compile("^Adivinaste.*");
                //Evalua el String linea 
                Matcher adivino = expresion.matcher(linea);

                //Si el patron se cumple quiere decir que el numero 
                //fue adivinado, se lanza break para detener ejecucion
                if (adivino.matches() == true) {
                    break;
                }

            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        salida.close();
        entrada.close();
        stdIn.close();
        socketCliente.close();
    }

}
