/**
 * 
 */
package reto8juego;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import reto8juego.actores.Fondo;
import reto8juego.actores.Nave;
import reto8juego.actores.TextoCentrado;
import reto8juego.escenas.Inicio;
import reto8juego.escenas.Partida;
import reto8juego.gui.Ventana;
import reto8juego.motor.Escena;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;
import reto8juego.recursos.Strings;

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
		estado=INICIO;
		motor.setEscena(new Inicio(this,() -> iniciarPartida()));
	}

 
	/**
	 * 
	 */
	private void iniciarPartida() {
		estado=JUGANDO;
		motor.setEscena(new Partida(this,null));
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

}
