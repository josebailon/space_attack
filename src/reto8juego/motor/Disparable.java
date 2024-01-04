/**
 * 
 */
package reto8juego.motor;

/**
 * Interfaz para objetos que pueden recibir disparos. Esta interfaz
 * extiende a colisionable dada la relacion entre ambas. Tras comprobarse
 * si ha impactado se pueden aplicar los efectos de la colision del disparo
 * via el metodo golpear de la interfaz colisionable.
 * La comprobacin del impacto de un disparo deberia hacerse suponiendo al disparo
 * como radio 0 simplificando el calculo de la posible colision
 * 
 * @author Jose Javier Bailon Ortiz
 */
public interface Disparable extends Colisionable{
	
	/**
	 * Comprueba si un disparo impacta.
	 * 
	 * @param disparo El disparo a comprobar
	 * 
	 * @return True si ha impactado. False si no lo ha impactado
	 */
	public boolean impactoDisparo(Disparo disparo);
}
