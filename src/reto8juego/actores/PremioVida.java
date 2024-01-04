/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;

/**
 * Agrega una vida extra a la partida
 * @author Jose Javier Bailon Ortiz
 */
public class PremioVida extends Premio{

	
	/**
	 * Constructor
	 * 
	 * @param x Posicion X
	 * @param y Posicion Y
	 */
	public PremioVida(double x, double y) {
		super(x, y, "vida", 6);
	}

	/**
	 * Agrega vida extra a la partida
	 */
	@Override
	public void aplicarEfecto() {
		if (partida!=null)
			partida.agregarVida();
	}

}
