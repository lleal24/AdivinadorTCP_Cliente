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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lorena Leal ID: 208313
 *@asignatura Interconetividad
 */
public class AdivinadorTCP_Cliente {

    public static void main(String[] args) throws IOException {
        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;
        //Descripcion del juego
        System.out.println("----------------------------Adivinar el numero----------------------------------" + "\n"
                + "El juego consiste en que el servidor"
                + " generara un numero aleatorio entre 0 y 100." + "\n"
                + "El objetivo es adivinar el numero teniendo en cuenta las pistas | tienes maximo 10 intentos" + "\n"
                + "Buena Suerte.... ☻");

        try {
            //creacion del socket del lado cliente se define ip y socket de destino
            socketCliente = new Socket("localhost", 4444);//ingresar aqui la ip del servidor
            //Obtenemos canal de entrada
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            //Obtenemos canal de salida
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

        } catch (IOException ex) {
            //Mensaje que se mostrara en caso de no poder ejecutar correctamente el bloque try
            System.err.println("No puede establer conexión con el servidor");
            //Salida del programa
            System.exit(-1);
        }
        //@stdInd, variable tipo Buffer que almacenara el mensaje que el cliente desee enviar
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        //@linea, variable tipo string que almacenara el mensaje enviado por el servidor
        String linea;

        try {
            //Ciclo que se ejecuta 10 veces equivalente a los 10 intentos que tiene el cliente
            for (int i = 1; i <= 10; i++) {
                //Mensaje en patalla del cliente solicitando un numero

                System.out.println("Ingresa numero:");
                //Se guarda el valor que el cliente ingrese
                linea = stdIn.readLine();
                //envio del mensaje cliente/servidor/conversion a decimal
                salida.println(bin(linea));

                //Recepcion de mensaje del servidor
                linea = entrada.readLine();
                //impresion del mensaje
                System.out.println("Ans Server: " + linea);

                //USO DE EXPRESIONES REGULARES
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

        //Cierre del socket
        salida.close();
        entrada.close();
        stdIn.close();
        socketCliente.close();
    }

    private static String bin(String a) {
        String binario = Integer.toBinaryString(Integer.valueOf(a));
        System.out.println("Numero binario enviado: " + binario);
        return binario;
    }
}
