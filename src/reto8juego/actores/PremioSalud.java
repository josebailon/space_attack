/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class PremioSalud extends Premio{

	
	/**
	 * @param x
	 * @param y
	 * @param codigo
	 * @param nFotogramas
	 */
	public PremioSalud(double x, double y) {
		super(x, y, "salud", 6);
	}

	@Override
	public void aplicarEfecto() {
		if (partida!=null)
			partida.getNave().agregarSalud(20);
	}

}
