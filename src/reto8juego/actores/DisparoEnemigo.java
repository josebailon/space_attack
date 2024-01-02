/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Disparo;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class DisparoEnemigo extends Disparo {

	/**
	 * 
	 * @param x
	 * @param y
	 * @param fuerza
	 */
	public DisparoEnemigo(double x, double y,double vX,double vY,int fuerza) {
		super(Recursos.getInstancia().getImg("disparoEnemigo"), x, y, vX, vY, fuerza);
	}

}
