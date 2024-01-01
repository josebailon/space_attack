/**
 * 
 */
package reto8juego.motor;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public interface Disparable extends Colisionable{
	
	/**
	 * Devuelve si un disparo en esa posicion lo impacta
	 * @param x
	 * @param y
	 * @param fuerza
	 * @return
	 */
	public boolean impactoDisparo(Disparo disparo);
}
