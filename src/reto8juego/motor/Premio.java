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
 * 
 * @author Jose Javier Bailon Ortiz
 */
abstract public class Premio extends Dibujo implements Colisionable {
	protected Partida partida;
	BufferedImage[] fotogramas;
	int fotogramaActual = 0;
	int velocidad = Config.VELOCIDAD_PREMIOS*2;
	int radio;
	public Premio(double x, double y, String codigo, int nFotogramas) {
		super(x, y);
		fotogramas = new BufferedImage[nFotogramas];
		Random r = new Random();
		for (int i = 0; i < fotogramas.length; i++) {
			this.fotogramas[i] = Recursos.getInstancia().getImg(codigo + i);
		}
		ancho = fotogramas[0].getWidth();
		alto = fotogramas[0].getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
		radio = mitadAlto;
		if (motor.getEscena() instanceof Partida)
			partida=(Partida)motor.getEscena();
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();

		at.translate(x, y);
		at.rotate(angulo);
		at.translate(-mitadAncho, -mitadAlto);
		g2d.drawImage(fotogramas[fotogramaActual], at, null);
		at.setToIdentity();
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		if (frame%2==0)
			fotogramaActual = (fotogramaActual + 1)%fotogramas.length;
		y += deltaSegundo * velocidad;
		if (x < -100 || x > motor.getAncho() + 100 || y > motor.getAlto() + 100)
			this.vivo = false;
	}

	@Override
	public int getFuerza() {
		return 0;
	}

	@Override
	public int getRadio() {
		return radio;
	}

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

	abstract public void aplicarEfecto();

	@Override
	public void matar() {
	}

	@Override
	public void golpear(int fuerza) {
	}

}
