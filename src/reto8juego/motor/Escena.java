/**
 * 
 */
package reto8juego.motor;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;

/**
 * Escena para cargar en el motor. Debe contener la logica requerida para la
 * escena, encargarse de solicitar la limpieza necesaria al motor y de crear
 * los elementos que necesita mostrar.
 * 
 * @author Jose Javier Bailon Ortiz
 */
abstract public class Escena {

	/**
	 * Referencia al controlador
	 */
	protected Controlador control;
	
	/**
	 * Referencia al motor
	 */
	protected Motor motor;
	
	/**
	 * Funcion callback a ejecutar cuando termine la escena
	 */
	protected Funcion callbackTerminado;

	
	/**
	 * Constructor
	 * @param control Referencia al control
	 * @param callbackTerminado Callback a ejectuar al terminar
	 */
	public Escena(Controlador control, Funcion callbackTerminado) {
		this.control = control;
		this.callbackTerminado = callbackTerminado;
		this.motor = Motor.getInstancia();
	}

	/**
	 * Establece un callback a ejectuar al finalizar la escena
	 *  
	 * @param callback El callback
	 */
	public void setCallback(Funcion callback) {
		callbackTerminado = callback;
	}

	
	/**
	 * Iniciar la escena
	 */
	abstract public void iniciar();

	
	/**
	 * Terminar la escena
	 */
	abstract public void terminar();

	/**
	 * Escucha a eventos keyPressed
	 * @param e El evento
	 */
	abstract public void keyPressed(KeyEvent e);

	/**
	 * Escucha a eventos keyReleased
	 * @param e El evento
	 */
	abstract public void keyReleased(KeyEvent e);

	
	/**
	 * Escucha a eventos keyTyped
	 * @param e El evento
	 */
	abstract public void keyTyped(KeyEvent e);

}
