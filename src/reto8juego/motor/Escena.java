/**
 * 
 */
package reto8juego.motor;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
abstract public class Escena {
	
	
	protected Controlador control;
	protected Motor motor;
	protected Funcion callbackTerminado;
	public Escena(Controlador control,Funcion callbackTerminado) {
		this.control = control;
		this.callbackTerminado=callbackTerminado;
		this.motor=Motor.getInstancia();
	}
	
	public void setCallback(Funcion callback) {
		callbackTerminado=callback;
	}
	abstract public void iniciar();
	abstract public void terminar();
 	abstract public void keyPressed(KeyEvent e);
	abstract public void keyReleased(KeyEvent e);	
	abstract public void keyTyped(KeyEvent e);

 
}
