/**
 * 
 */
package reto8juego;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import reto8juego.escenas.EscenaInicial;
import reto8juego.escenas.Instrucciones;
import reto8juego.escenas.Partida;
import reto8juego.gui.Ventana;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

/**
 * Crea el motor, la ventana y enlaza el motor con el lienzo de dibujado que hay en la ventana.
 * 
 * Sirve de control de flujo entre escenas. 
 * 
 * Menu inicial <--> Insturcciones
 * Menu inicial <--> Partida
 * 
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Controlador implements KeyListener {
	
	/**
	 * Ventana grafica
	 */
	private Ventana ventana;
	
	/**
	 * Motor del juego
	 */
	private Motor motor;
	
	
	/**
	 * Constructor. Lanza el cargado de recursos(imagenes y fuentes)
	 * Crea el motor y la ventana y enlaza el motor con el lienzo de dibujado que hay 
	 * en la ventana.
	 */
	public Controlador() {
		//cargar recursos
		if (!Recursos.getInstancia().cargarRecursos())
			salir();
		//crear motor
		motor = Motor.getInstancia();
		//crear ventana
		ventana = new Ventana(this);
		//asignar lienzo de dibujado al motor
		motor.setLienzo(ventana.getLienzo());
		//iniciar actividad del motor
		motor.play();
		//ir a la pantalla inicial
		pantallaInicial();
	}
	
	
	/**
	 * Carga en el motor la escena inicial del menu
	 */
	public void pantallaInicial() {
		motor.setEscena(new EscenaInicial(this,null));
	}
 
	
	/**
	 * Inicia la partida marcando la vuelta a la pantalla inicial como punto de 
	 * salida de la escena
	 */
	public void iniciarPartida() {
		motor.setEscena(new Partida(this,()->pantallaInicial()));
	}

	
	
	/**
	 * Inicia la escena de insturcciones marcando la vuelta a la pantalla inicial como
	 * punto de salida de la escena de instrucciones
	 */
	public void verInstrucciones() {
		motor.setEscena(new Instrucciones(this,()->pantallaInicial()));
	}

	
	/**
	 * Enlace con la escena actual de los eventos keyPressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		motor.getEscena().keyPressed(e);
	}

	
	/**
	 * Enlace con la escena actual de los eventos keyReleased
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		motor.getEscena().keyReleased(e);
	}
	
	
	/**
	 * Enlace con la escena actual de los eventos keyTyped
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		motor.getEscena().keyTyped(e);
	}


	/**
	 * salir de la aplicacion
	 */
	public void salir() {
		System.exit(0);
	}

}
