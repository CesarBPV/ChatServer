/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Observable;

/**
 *
 * @author César
 */
public class MensajesChat extends Observable {
    
    private String mensaje;
    
    public MensajesChat(){
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String mensaje, String username){
        this.mensaje = username+": "+mensaje;
        // Indica que el mensaje ha cambiado
        this.setChanged();
        // Notifica a los observadores que el mensaje ha cambiado y se lo pasa
        // (Internamente notifyObservers llama al metodo update del observador)
        this.notifyObservers(this.getMensaje());
    }
    public void NewUserNotification(String username){
        this.mensaje="!"+username+" se acaba de conectar!";
        this.setChanged();
        this.notifyObservers(this.getMensaje());
    }

}
