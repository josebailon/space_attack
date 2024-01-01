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
public class PremioEscudo extends Premio{

	
	/**
	 * @param x
	 * @param y
	 * @param codigo
	 * @param nFotogramas
	 */
	public PremioEscudo(double x, double y) {
		super(x, y, "escudo", 6);
	}

	@Override
	public void aplicarEfecto() {
		if (partida==null)
			return;
			Nave nave = partida.getNave();
			nave.setEscudoActivo(true);
			new Temporizador(5000,()->nave.setEscudoActivo(false));
	}

}
