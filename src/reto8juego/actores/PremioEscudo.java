/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;
import reto8juego.motor.Temporizador;

/**
 * Premio que activa el escudo de la nave por 5 segundos
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class PremioEscudo extends Premio{

	
	/**
	 * Constructor
	 * @param x Posicion X
	 * @param y Posicion Y
	 */
	public PremioEscudo(double x, double y) {
		super(x, y, "escudo", 6);
	}

	/**
	 * Activa el escudo de la nave por 5 segundos
	 */
	@Override
	public void aplicarEfecto() {
		if (partida==null)
			return;
			Nave nave = partida.getNave();
			nave.setEscudoActivo(true);
			new Temporizador(5000,()->nave.setEscudoActivo(false));
	}

}
