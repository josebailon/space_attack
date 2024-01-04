/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Disparo;
import reto8juego.recursos.Recursos;

/**
 * Disparo efectuado por las naves enemigas
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class DisparoEnemigo extends Disparo {

	/**
	 * Constructor 
	 * @param x Posicion X 
	 * @param y Posicion Y
	 * @param vX Velocidad X
	 * @param vY Velocidad Y
	 * @param fuerza Fuerza del disparo
	 */
	public DisparoEnemigo(double x, double y,double vX,double vY,int fuerza) {
		super(Recursos.getInstancia().getImg("disparoEnemigo"), x, y, vX, vY, fuerza);
	}

}
