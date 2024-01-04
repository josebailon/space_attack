/**
 * 
 */
package reto8juego.motor;

import java.awt.Graphics2D;

/**
 * <p>
 * Clase base para los dibujos. Contiene los parametros esenciales de: si esta
 * vivo, posicion x, posicion y, velocidad en x velocidad en y angulo, velocidad
 * angular, ancho, alto opacidad, Animacion autonoma a usar para x, Animacoin
 * autonoma a usar para y, animacion autonoma a usar para transparencia y una
 * referencia al motor.
 * </p>
 * <p>
 * Su metodo dibujar sera ejecutado por el lienzo durante el dibujado. El metodo
 * nuevoFotograma sera ejecutado por el motor durante la fase de animacion
 * </p>
 * 
 * <p>
 * Esta clase base de Dibujo tiene ya implementado un comportamiento que aplica
 * a transparencia, x e y la animacion autonoma en caso de estar definida. Las
 * clases extendidas es posible que necesiten un comportamiento de animacion mas
 * especifico lo cual se hara sobreescribiendo el metodo nuevoFotograma.
 * </p>
 * 
 * <p>
 * El metodo dibujar debe implementandose ejecutando el dibujado del objeto.
 * </p>
 * 
 * @author Jose Javier Bailon Ortiz
 */
public abstract class Dibujo {

	/**
	 * Define si esta vivo o no. Los dibujos con vivo=false seran eliminados del
	 * motor en la fase de limpieza del bucle de fotograma
	 */
	protected boolean vivo = true;

	/**
	 * Posicion en x
	 */
	protected double x = 0;

	/**
	 * Posicion en Y
	 */
	protected double y = 0;

	/**
	 * Velocidad x
	 */
	protected double vX = 0;

	/**
	 * Velocidad y
	 */
	protected double vY = 0;

	/**
	 * Angulo de rotacion
	 */
	protected float angulo = 0;

	/**
	 * Velocidad angular
	 */
	protected float vAngulo = 0;

	/**
	 * Ancho
	 */
	protected int ancho = 2;

	/**
	 * Alto
	 */
	protected int alto = 2;

	/**
	 * Mitad del ancho
	 */
	protected int mitadAncho = 1;

	/**
	 * Mitad del alto
	 */
	protected int mitadAlto = 1;

	/**
	 * Opacidad
	 */
	protected float opacidad = 254f;

	/**
	 * Animacion autonoma a aplicar en x
	 */
	protected Animacion animacionX;

	/**
	 * Animacion autonoma a aplicar en y
	 */
	protected Animacion animacionY;

	/**
	 * Animacion autonoma a aplicar en opacidad
	 */
	protected Animacion animacionOpacidad;

	/**
	 * Referencia al motor
	 */
	protected Motor motor;

	/**
	 * Constructor
	 */
	public Dibujo() {
		this.x = 0d;
		this.y = 0d;
		motor = Motor.getInstancia();
	}

	/**
	 * Constructor parametrizado
	 * 
	 * @param x Posicion x
	 * @param y Posicion y
	 */
	public Dibujo(double x, double y) {
		this.x = x;
		this.y = y;
		motor = Motor.getInstancia();
	}

	/**
	 * Comandos para el dibujado. Este metodo lo ejecuta Lienzo durante su
	 * paintComponent
	 * 
	 * @param g2d Graphics2D del lienzo
	 */
	abstract public void dibujar(Graphics2D g2d);

	/**
	 * <p>
	 * Calcular el nuevo estado del objeto para el nuevo fotograma. Comumente sera
	 * la posicion y/o orientacion pero cualquier parametro que se necesite animar a
	 * lo largo del tiempo debe contemplarse en este metodo si la clase es
	 * extendida.
	 * </p>
	 * 
	 * <p>
	 * En la implementacion base se recalcula x, y, opacidad en funcion de las
	 * animaciones autonomas si estan definidas
	 * </p>
	 * 
	 * @param frame           Frame actual del motor
	 * @param delta           Delta en ms
	 * @param deltaPorSegundo Proporcion 0.0-1.0 de un segundo que supone el delta
	 */
	public void nuevoFotograma(int frame, long delta, float deltaPorSegundo) {
		if (animacionY != null)
			y = (double) animacionY.getValor();
		if (animacionX != null)
			x = (double) animacionX.getValor();
		if (animacionOpacidad != null)
			opacidad = (float) animacionOpacidad.getValor();
	}

	/**
	 * Devuelve x
	 * @return Valor x
	 */
	public double getX() {
		return x;
	}

	
	/**
	 * Define x
	 * @param x Valor para x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Devuelve y
	 * @return El valor y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Defie y
	 * @param y El valor para y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Devuelve la opacidad
	 * @return El valor de la opacidad
	 */
	public float getOpacidad() {
		return opacidad;
	}

	/**
	 * Define la opacidad
	 * @param opacidad El valor para la opacidad
	 */
	public void setOpacidad(float opacidad) {
		this.opacidad = opacidad;
	}

	/**
	 * Devuelve la animacion establecida actualmente para x
	 * 
	 * @return La animacion
	 */
	public Animacion getAnimacionX() {
		return animacionX;
	}

	/**
	 * Define la animacion para x
	 * 
	 * @param animacionX La animacion
	 */
	public void setAnimacionX(Animacion animacionX) {
		this.animacionX = animacionX;
	}

	
	/**
	 * Devuelve la animacion establecida actualmente para y
	 * 
	 * @return La animacion
	 */
	public Animacion getAnimacionY() {
		return animacionY;
	}

	/**
	 * Define la animacion para y
	 * 
	 * @param animacionY La animacion
	 */
	public void setAnimacionY(Animacion animacionY) {
		this.animacionY = animacionY;
	}

	/**
	 * Devuelve la animacion establecida actualmente para la opacidad
	 * 
	 * @return La animacion
	 */
	public Animacion getAnimacionOpacidad() {
		return animacionOpacidad;
	}

	/**
	 * Define la animacion para la opacidad
	 * 
	 * @param animacionOpacidad La animacion
	 */
	public void setAnimacionOpacidad(Animacion animacionOpacidad) {
		this.animacionOpacidad = animacionOpacidad;
	}

	/**
	 * Devuelve si esta vivo
	 * 
	 * @return True si esta vivo, False si no lo esta
	 */
	public boolean isVivo() {
		return vivo;
	}

	/**
	 * Define el estado como vivo o muerto
	 * 
	 * @param vivo True para vivo False para muerto
	 */
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

}
