/**
 * 
 */
package reto8juego.motor;

/**
 * Interfaz para objetos que pueden colisionar con otros objetos
 * 
 * @author Jose Javier Bailon Ortiz
 */
public interface Colisionable {
	
	/**
	 * Matar el colisionable
	 */
	public void matar();
	
	/**
	 * Devuelve la fuerza con la que golpea a otro
	 * 
	 * @return La fuerza
	 */
	public int getFuerza();
	
	/**
	 * Devuelve el radio de colision
	 * 
	 * @return El radio
	 */
	public int getRadio();
	
	/**
	 * Posicion x
	 * @return La posicion x
	 */
	public double getX();
	
	/**
	 * Posicion y
	 * @return La posicion y
	 */
	public double getY();
	
	/**
	 * Comprueba si colisiona con otro colisionable
	 * 
	 * @param otro El otro colisionable con el que comprobar la colision
	 * 
	 * @return True si colisiona, False si no colisiona
	 */
	public boolean colisiona(Colisionable otro);
	
	/**
	 * Aplicar los efectos de una colision con una fuerza determinada
	 * 
	 * @param fuerza La fuerza del golpe recibido
	 */
	public void golpear(int fuerza);

}
