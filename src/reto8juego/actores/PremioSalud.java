/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;

/**
 *  Recupera 20 de salud a la nave
 * @author Jose Javier Bailon Ortiz
 */
public class PremioSalud extends Premio{

	
	/**
	 * Constructor
	 * 
	 * @param x Posicion X
	 * @param y Posicion Y
	 */
	public PremioSalud(double x, double y) {
		super(x, y, "salud", 6);
	}

	/**
	 * Recupera 20 puntos de salud a la nave
	 */
	@Override
	public void aplicarEfecto() {
		if (partida!=null)
			partida.getNave().agregarSalud(20);
	}

}
