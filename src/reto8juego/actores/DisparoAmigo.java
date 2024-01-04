/**
 * 
 */
package reto8juego.actores;

import reto8juego.motor.Disparo;
import reto8juego.recursos.Recursos;

/**
 * Disparo efectuado por la nave del jugador.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class DisparoAmigo extends Disparo {

	/**
	 * @param x Posicion x
	 * @param y Posicion y
	 * @param fuerza Fuerza de impacto
	 */
	public DisparoAmigo(double x, double y,int fuerza) {		
		super(Recursos.getInstancia().getImg("disparoAmigo"+((fuerza-1)%6)), x, y, 0, -1000d, fuerza);
	}

}
