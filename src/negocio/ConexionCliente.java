/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author César
 */
public class ConexionCliente extends Thread implements Observer {

    private Socket socket;
    private MensajesChat mensajes;
    private DataInputStream entradaDatos;
    private DataOutputStream salidaDatos;

    public ConexionCliente(Socket socket, MensajesChat mensajes) {
        this.socket = socket;
        this.mensajes = mensajes;

        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        String mensajeRecibido;
        boolean conectado = true;
        String username = "";
        String contraseña = "";
        // Se apunta a la lista de observadores de mensajes
        mensajes.addObserver(this);

        while (conectado) {
            try {
                // Lee un mensaje enviado por el cliente
                mensajeRecibido = entradaDatos.readUTF();
                if (mensajeRecibido.equals("validar")) {
                    username = entradaDatos.readUTF();
                    contraseña = entradaDatos.readUTF();
                } else {
                    // Pone el mensaje recibido en mensajes para que se notifique 
                    // a sus observadores que hay un nuevo mensaje.
                    mensajes.setMensaje(mensajeRecibido);
                }
            } catch (IOException ex) {
                System.err.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.");
                conectado = false;
                // Si se ha producido un error al recibir datos del cliente se cierra la conexion con el.
                try {
                    entradaDatos.close();
                    salidaDatos.close();
                } catch (IOException ex2) {
                    System.err.println("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            // Envia el mensaje al cliente
            salidaDatos.writeUTF(arg.toString());
        } catch (IOException ex) {
            System.err.println("Error al enviar mensaje al cliente (" + ex.getMessage() + ").");
        }
    }

}
