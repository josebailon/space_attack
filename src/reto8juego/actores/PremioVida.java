/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class PremioVida extends Premio{

	
	/**
	 * @param x
	 * @param y
	 * @param codigo
	 * @param nFotogramas
	 */
	public PremioVida(double x, double y) {
		super(x, y, "vida", 6);
	}

	@Override
	public void aplicarEfecto() {
		if (partida!=null)
			partida.agregarVida();
	}

}
