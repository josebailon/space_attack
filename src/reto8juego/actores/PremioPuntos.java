/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Premio;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class PremioPuntos extends Premio{

	int puntos=0;
	
	/**
	 * @param x
	 * @param y
	 * @param codigo
	 * @param nFotogramas
	 */
	public PremioPuntos(double x, double y, int puntos) {
		super(x, y, "moneda", 6);
		this.puntos=puntos;
	}

	@Override
	public void aplicarEfecto() {
		if (partida!=null)
			partida.agregarPuntos(puntos);
	}

}
