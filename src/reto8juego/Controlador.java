/**
 * 
 */
package reto8juego;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import reto8juego.actores.TextoCentrado;
import reto8juego.escenas.EscenaInicial;
import reto8juego.escenas.Instrucciones;
import reto8juego.escenas.Partida;
import reto8juego.gui.Ventana;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Controlador implements KeyListener {
	private final int INICIO =0;
	private final int JUGANDO =1;
	private final int PERDIDO =1;
	
	Ventana ventana;
	Motor motor;
	private int estado = INICIO;

	TextoCentrado textoCentrado;
	
	public Controlador() {
		//cargar recursos
		Recursos.getInstancia().cargarRecursos();
		//crear motor
		motor = Motor.getInstancia();
		//crear ventana
		ventana = new Ventana(this);
		motor.setLienzo(ventana.getLienzo());
		pantallaInicial();
	}
	
	
	/**
	 * 
	 */
	public void pantallaInicial() {
		motor.setEscena(new EscenaInicial(this,() -> iniciarPartida()));
	}
 
	/**
	 * 
	 */
	public void iniciarPartida() {
		motor.setEscena(new Partida(this,()->pantallaInicial()));
	}

	

	@Override
	public void keyPressed(KeyEvent e) {
		motor.getEscena().keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		motor.getEscena().keyReleased(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		motor.getEscena().keyTyped(e);
	}


	/**
	 * @return
	 */
	public void salir() {
		System.exit(0);
	}


	/**
	 * @return
	 */
	public void verInstrucciones() {
		motor.setEscena(new Instrucciones(this,()->pantallaInicial()));
	}

}
