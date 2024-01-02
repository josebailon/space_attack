/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;
import reto8juego.motor.Temporizador;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class PremioArma extends Premio{

	
	/**
	 * @param x
	 * @param y
	 * @param codigo
	 * @param nFotogramas
	 */
	public PremioArma(double x, double y) {
		super(x, y, "arma", 6);
	}

	@Override
	public void aplicarEfecto() {
		if (partida==null)
			return;
			Nave nave = partida.getNave();
			nave.aumentarFuerzaDisparo();
	}

}
