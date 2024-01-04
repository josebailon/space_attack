/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;

/**
 * Premio de incremento de la fuerza de disparo de la nave
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class PremioArma extends Premio{

	
	/**
	 * Constructor 
	 * @param x Posicion x
	 * @param y Posicion y
	 */
	public PremioArma(double x, double y) {
		super(x, y, "arma", 6);
	}

	
	/**
	 * Aumenta la fuerza de disparo de la nave
	 */
	@Override
	public void aplicarEfecto() {
		if (partida==null)
			return;
			Nave nave = partida.getNave();
			nave.aumentarFuerzaDisparo();
	}

}
