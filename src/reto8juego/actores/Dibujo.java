/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public abstract class Dibujo {
	private boolean vivo=true;
	protected Double x = 0d;
	protected Double y = 0d;
	protected float opacidad=1f;
	
	abstract public void dibujar (Graphics2D g2d);
 	abstract public void nuevoFotograma(int frame, long delta,float deltaSegundo);
}
