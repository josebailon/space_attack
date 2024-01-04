/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import reto8juego.motor.Colisionable;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Disparable;
import reto8juego.motor.Disparo;
import reto8juego.motor.Premio;
import reto8juego.recursos.Recursos;

/**
 * Actor meteorito. Se mueve segun su vector de velocidad y gira sobre si mismo.
 * Cuando se destruye por haber sido disparado o por colisionar con la nave del
 * jugador genera un premio.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Meteorito extends Dibujo implements Disparable, Colisionable {

	/**
	 * Generador aleatorio
	 */
	private Random r;

	/**
	 * Imagen del meteorito
	 */
	private BufferedImage img;

	/**
	 * Salud del meteorito
	 */
	private AtomicInteger salud = new AtomicInteger(1);

	/**
	 * Nivel del meteorito
	 */
	private int nivel = 1;

	/**
	 * Fuerza ejercida por el meteorito en una colision
	 */
	private int fuerza = 1;

	/**
	 * radio del meteorito para las colisiones
	 */
	private int radio = 0;

	/**
	 * Constructor
	 * 
	 * @param x     Posicion X
	 * @param y     Posicon Y
	 * @param vx    Velocidad X
	 * @param vy    Velocidad Y
	 * @param nivel Nivel
	 */
	public Meteorito(double x, double y, double vx, double vy, int nivel) {
		super(x, y);
		r = new Random();

		// cargar la imagen(una aleatoria entre las 3 de meteorito)
		this.img = Recursos.getInstancia().getImg("meteorito" + r.nextInt(3));

		// definir una velocidad angular aleatorioa
		this.vAngulo = r.nextFloat(-3, 3);

		// calcular dimensiones
		ancho = img.getWidth();
		alto = img.getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;

		// definir resto de parametros
		this.vX = vx;
		this.vY = vy;
		this.nivel = nivel;
		this.salud.set(nivel % 5);
		this.fuerza = 10 + this.nivel;
		this.radio = mitadAlto;
	}

	/**
	 * Dibuja el meteorito. Hace una traslacion hasta la posicion, rotacion y traslacion segun el ancho para
	 * hacer que el meteorito rote sobre si mismo mientras se desplaza
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angulo);
		at.translate(-mitadAncho, -mitadAlto);
		g2d.drawImage(img, at, null);
		at.setToIdentity();
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		x += deltaSegundo * vX * nivel * 100;
		y += deltaSegundo * vY * nivel * 100;
		angulo += deltaSegundo * vAngulo;
		if (y > motor.getAlto() + 100)
			this.vivo = false;
	}

	@Override
	public boolean impactoDisparo(Disparo disparo) {
		boolean impactado = false;
		// componentes de vector de posicion relativa
		double xt = Math.abs(disparo.getX()) - x;
		double yt = Math.abs(disparo.getY()) - y;
		// comprobacion de que el disparo esta en la caja contenedora
		if (xt > radio || yt > radio)
			return false;

		// comprobacion por distancia
		double distancia = Math.sqrt(xt * xt + yt * yt);

		// calcular efectos de impacto
		if (distancia < mitadAncho) {
			golpear(disparo.getFuerza());
			impactado = true;
		}

		// resultado del impacto
		return impactado;
	}

	@Override
	public int getFuerza() {
		return fuerza;
	}

	@Override
	public int getRadio() {
		return mitadAlto;
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
			// efectos propioes
			this.golpear(otro.getFuerza());
			// efectos ajenos
			otro.golpear(fuerza);
			colisionado = true;
		}
		// resultado del impacto
		return colisionado;
	}

	@Override
	public void matar() {
		salud.set(0);
		// generar explosion y morir
		motor.agregarCapaFx(new Explosion(x, y, Explosion.METEORITO));
		vivo = false;
		crearPremio();
	}

	/**
	 * 
	 */
	private void crearPremio() {
		Premio premio;
		int azar = r.nextInt(1000);
		if (azar > 500)
			premio = new PremioPuntos(x, y, nivel);
		else if (azar > 200)
			premio = new PremioSalud(x, y);
		else if (azar > 80)
			premio = new PremioEscudo(x, y);
		else if (azar > 30)
			premio = new PremioArma(x, y);
		else
			premio = new PremioVida(x, y);
		motor.agregarCapaPremios(premio);
	}

	@Override
	public void golpear(int fuerza) {
		salud.addAndGet(-fuerza);
		// comprobar si sigue vivo
		if (salud.get() <= 0)
			matar();
	}

}
