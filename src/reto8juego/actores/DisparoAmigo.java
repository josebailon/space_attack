/**
 * 
 */
package reto8juego.actores;

import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class DisparoAmigo extends Disparo {

	/**
	 * 
	 * @param x
	 * @param y
	 * @param fuerza
	 */
	public DisparoAmigo(double x, double y,int fuerza) {
		super(Recursos.getInstancia().getImg("disparoAmigo"), x, y, 0, -1000d, fuerza);
	}

}
