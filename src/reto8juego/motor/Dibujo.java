/**
 * 
 */
package reto8juego.motor;

import java.awt.Graphics2D;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public abstract class Dibujo {
	protected boolean vivo=true;
	protected double x=0;
	protected double y=0;
	 /** Velocidad x
	 */
	protected double vX=0;
	
	/**
	 * Velocidad y
	 */
	protected double vY=0;
	
	protected float angulo=0;
	protected float vAngulo=0;
	
	protected int ancho=1;
	protected int alto=1;
	protected int mitadAncho=1;
	protected int mitadAlto=1;
	protected float opacidad=254f;
	protected Animacion animacionX;
	protected Animacion animacionY;
	protected Animacion animacionOpacidad;
	protected Motor motor;
	public Dibujo() {
		this.x = 0d;
		this.y = 0d;
		motor = Motor.getInstancia();
	}
	
	public Dibujo(double x, double y) {
		this.x = x;
		this.y = y;
		motor = Motor.getInstancia();
	}
	abstract public void dibujar (Graphics2D g2d);
	
	
 	public void nuevoFotograma(int frame, long delta,float deltaSegundo) {
		if (animacionY!=null)
			y=(double) animacionY.getValor();
		if (animacionX!=null)
			x=(double) animacionX.getValor();
		if (animacionOpacidad!=null)
			opacidad=(float) animacionOpacidad.getValor();
 	}
 	
 	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public float getOpacidad() {
		return opacidad;
	}
	public void setOpacidad(float opacidad) {
		this.opacidad = opacidad;
	}
	public Animacion getAnimacionX() {
		return animacionX;
	}
	public void setAnimacionX(Animacion animacionX) {
		this.animacionX = animacionX;
	}
	public Animacion getAnimacionY() {
		return animacionY;
	}
	public void setAnimacionY(Animacion animacionY) {
		this.animacionY = animacionY;
	}

	public Animacion getAnimacionOpacidad() {
		return animacionOpacidad;
	}

	public void setAnimacionOpacidad(Animacion animacionOpacidad) {
		this.animacionOpacidad = animacionOpacidad;
	}

	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
 	
}

