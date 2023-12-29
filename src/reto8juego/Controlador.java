/**
 * 
 */
package reto8juego;

import reto8juego.actores.Fondo;
import reto8juego.gui.Ventana;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Controlador {
	private final int INICIO =0;
	private final int JUGANDO =1;
	private final int PERDIDO =1;
	
	Ventana v;
	Motor m;
	private int estado = INICIO;
	
	
	
	public Controlador() {
		//cargar recursos
		Recursos.getInstancia().cargarRecursos();
		//crear motor
		m = Motor.getInstancia();
		//crear ventana
		v = new Ventana(this,m);
		m.agregarCapaFondo(new Fondo());
		m.play();
	}
	/**
	 * @param keyCode
	 */
	public void pulsa(int keyCode) {
				
	}
	/**
	 * @param keyCode
	 */
	public void suelta(int keyCode) {
		
	}

}
