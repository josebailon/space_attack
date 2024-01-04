/**
 * 
 */
package reto8juego.motor;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.recursos.Recursos;

/**
 * Premios que se pueden recoger. Cuando el jugador colisione con un objeto de
 * este tipo debe aplicarse el premio usando el metodo aplicarEfecto. Su
 * animacion es quedar a la deriva en pantalla descendiendo segun la velocidad
 * indicada en la configuraion
 * 
 * @author Jose Javier Bailon Ortiz
 */
abstract public class Premio extends Dibujo implements Colisionable {

	/**
	 * Referencia a la partida
	 */
	protected Partida partida;

	/**
	 * Fotogramas que componen la animacion del premio
	 */
	BufferedImage[] fotogramas;

	/**
	 * Indice del fotograma actual
	 */
	int fotogramaActual = 0;

	int velocidad = Config.VELOCIDAD_PREMIOS;

	/**
	 * Radio de colision del premio
	 */
	int radio;

	/**
	 * Constructor
	 * 
	 * @param x           Posicion x
	 * @param y           Posicion y
	 * @param codigo      Codigo para acceder a los fotogramas en el gestor de
	 *                    Recursos
	 * @param nFotogramas Cantidad de fotogramas que componen su animacion
	 * 
	 * @see Recursos
	 */
	public Premio(double x, double y, String codigo, int nFotogramas) {
		super(x, y);

		// recoger fotogramas
		fotogramas = new BufferedImage[nFotogramas];
		Random r = new Random();
		for (int i = 0; i < fotogramas.length; i++) {
			this.fotogramas[i] = Recursos.getInstancia().getImg(codigo + i);
		}
		// configuracion de tamano
		ancho = fotogramas[0].getWidth();
		alto = fotogramas[0].getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
		radio = mitadAlto;

		// recoger referncia a la partida
		if (motor.getEscena() instanceof Partida)
			partida = (Partida) motor.getEscena();
	}

	/**
	 * Dibuja el fotograma actual
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angulo);
		at.translate(-mitadAncho, -mitadAlto);
		g2d.drawImage(fotogramas[fotogramaActual], at, null);
		at.setToIdentity();
	}

	/**
	 * Calcula la nueva posicion, el nuevo fotograma de la animacion a dibujar y lo
	 * mata si sale de la pantalla
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		if (frame % 2 == 0)
			fotogramaActual = (fotogramaActual + 1) % fotogramas.length;
		y += deltaSegundo * velocidad;
		if (x < -100 || x > motor.getAncho() + 100 || y > motor.getAlto() + 100)
			this.vivo = false;
	}

	/**
	 * Devuele la fuerza de golpeo
	 */
	@Override
	public int getFuerza() {
		return 0;
	}

	/**
	 * Devuelve el radio de colision
	 */
	@Override
	public int getRadio() {
		return radio;
	}

	/**
	 * Comprueba si ha colisionado con otro. En caso de haber colisionado aplica el efecto
	 */
	@Override
	public boolean colisiona(Colisionable otro) {
		boolean colisionado = false;
		// componentes de vector de posicion relativa
		double xt = Math.abs(otro.getX()) - x;
		double yt = Math.abs(otro.getY()) - y;
		// comprobacion de que se solapan las cajas contenedoras
		if (xt - otro.getRadio() > radio || yt - otro.getRadio() > radio)
			return false;

		// comprobacion por distancia
		double distancia = Math.sqrt(xt * xt + yt * yt);

		// calcular efectos de impacto
		if (distancia - otro.getRadio() < radio) {
			// matar premio
			vivo = false;
			// efectos ajenos
			aplicarEfecto();
			colisionado = true;
		}
		// resultado del impacto
		return colisionado;
	}

	/**
	 * Debe implementar el efecto ocurrido tras recoger el premio
	 */
	abstract public void aplicarEfecto();

	@Override
	public void matar() {
	}

	@Override
	public void golpear(int fuerza) {
	}

}
