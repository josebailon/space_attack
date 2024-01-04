/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;

/**
 * Otorga puntos para la partida
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class PremioPuntos extends Premio{

	/**
	 * Puntos a otorgar
	 */
	private int puntos=0;
	
	/**
	 * Constructor
	 * @param x Posicion X
	 * @param y Posicion Y
	 * @param puntos Puntos a otorgar
	 */
	public PremioPuntos(double x, double y, int puntos) {
		super(x, y, "moneda", 6);
		this.puntos=puntos;
	}

	/**
	 * Otorga puntos a la partida
	 */
	@Override
	public void aplicarEfecto() {
		if (partida!=null)
			partida.agregarPuntos(puntos);
	}

}
