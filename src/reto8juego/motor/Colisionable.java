/**
 * 
 */
package reto8juego.motor;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public interface Colisionable {
	public void matar();
	public int getFuerza();
	public int getRadio();
	public double getX();
	public double getY();
	public boolean colisiona(Colisionable otro);
	public void golpear(int fuerza);

}
