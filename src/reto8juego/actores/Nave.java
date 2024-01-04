/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.motor.Colisionable;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Disparable;
import reto8juego.motor.Disparo;
import reto8juego.recursos.Recursos;

/**
 * 
 * <p>
 * Nave del jugador. Se mueve segun el estado de teclas recibido. Contiene una
 * salud, una fuerza de disparo y un escudo que puede estar activo o no
 * </p>
 * <p>
 * Una secuencia de fotogramas que se anima segun se este yendo a la izquierda,
 * derecha o sin movimiento lateral define su aspecto.
 * </p>
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Nave extends Dibujo implements Colisionable, Disparable {
	/**
	 * Fotograma de nave central
	 */
	private final int FOTOGRAMA_CENTRO = 7;

	/**
	 * Fotograma extremo de nave virando a la izquierda
	 */
	private final int FOTOGRAMA_IZQUIERDA = 0;

	/**
	 * Fotograma extremo de nave virando a la derecha
	 */
	private final int FOTOGRAMA_DERECHA = 14;

	/**
	 * Lista de fotogramas de la nave
	 */
	private BufferedImage[] fotogramas = new BufferedImage[15];

	/**
	 * Imagen del escudo
	 */
	private BufferedImage imgEscudo;

	/**
	 * Referencia a la partida
	 */
	private Partida partida;

	/**
	 * Velocidad en X
	 */
	private AtomicInteger velX = new AtomicInteger();

	/**
	 * Velocidad en Y
	 */
	private AtomicInteger velY = new AtomicInteger();

	/**
	 * Magnitud de la velocidad
	 */
	private AtomicInteger velocidad = new AtomicInteger(400);

	/**
	 * Fotograma destino de la animacion
	 */
	private AtomicInteger fotogramaDestino = new AtomicInteger(FOTOGRAMA_CENTRO);

	/**
	 * Fotograma actual de la animacion
	 */
	private AtomicInteger fotogramaActual = new AtomicInteger(FOTOGRAMA_CENTRO);

	/**
	 * Fuerza del disparo
	 */
	private int fuerzaDisparo = 1;

	/**
	 * Salud de la nave
	 */
	private AtomicInteger salud = new AtomicInteger(Config.SALUD_MAXIMA);

	/**
	 * Nivel de escudo. Mientras sea positivo sera invulnerable
	 */
	private AtomicInteger nivelEscudo = new AtomicInteger(1);

	/**
	 * Constructor
	 * 
	 * @param partida Referncia a la partida
	 */
	public Nave(Partida partida) {
		// colocar la nave en el centro horizontal por debajo de la pantalla (la
		// animacion de entrada la pondra en su sitio)
		super(400d, 1124d);

		// recoger imagenes
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fotogramas.length; i++) {
			fotogramas[i] = r.getImg("nave" + i);
		}
		imgEscudo = r.getImg("escudo");

		// definir dimensiones de la nave
		ancho = fotogramas[0].getWidth();
		alto = fotogramas[0].getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;

		// referencia a la partida
		this.partida = partida;
	}

	/**
	 * Cuando la nave se dibuja se muestra el fotograma actual de la animacion de la
	 * nave y se superpone la imagen del escudo si procede
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		// imagen de la nave
		AffineTransform at = new AffineTransform();
		at.translate(x - mitadAncho, y - mitadAlto);
		g2d.drawImage(fotogramas[fotogramaActual.get()], at, null);
		at.setToIdentity();

		// imagen del escudo si esta activo
		if (nivelEscudo.get() > 0) {
			at.translate(x - mitadAncho, y - mitadAncho);
			g2d.drawImage(imgEscudo, at, null);
			at.setToIdentity();
		}
	}

	/**
	 * Calcula la nueva posicion segun los vectores velocidad. Tambien refresca el
	 * fotograma de la animacion de la nave y limita la posicion para que no salga
	 * de la pantalla
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);

		// no actuar si hay animaciones automaticas en marcha
		if (animacionY != null || animacionX != null)
			return;

		// calcular fotograma de la animacion de la nave
		int fotogramaA = fotogramaActual.get();
		if (fotogramaA < fotogramaDestino.get())
			fotogramaActual.set(++fotogramaA);
		else if (fotogramaA > fotogramaDestino.get())
			fotogramaActual.set(--fotogramaA);

		// calculo de la nueva posicion
		x += velocidad.get() * velX.get() * deltaSegundo;
		y += velocidad.get() * velY.get() * deltaSegundo;

		// limitacion a la pantalla
		if (x < 0)
			x = 0;
		else if (x > motor.getAncho())
			x = motor.getAncho();
		if (y < 0)
			y = 0;
		else if (y > motor.getAlto())
			y = motor.getAlto();
	}

	/**
	 * Responder a las teclas modificando el vector velocidad y el fotograma destino
	 * de la animacion
	 * 
	 * @param arriba    Tecla arriba pulsada
	 * @param abajo     Tecla abajo pulsada
	 * @param izquierda Tecla izquierda pulsada
	 * @param derecha   Tecla derecha pulsada
	 */
	public void teclas(boolean arriba, boolean abajo, boolean izquierda, boolean derecha) {

		// velocidad en Y
		if (arriba && !abajo)
			velY.set(-1);
		else if (!arriba && abajo)
			velY.set(1);
		else
			velY.set(0);

		// velocidad en X y fotograma destino de la animacion
		if (derecha && !izquierda) {
			velX.set(1);
			fotogramaDestino.set(FOTOGRAMA_DERECHA);
		} else if (!derecha && izquierda) {
			velX.set(-1);
			fotogramaDestino.set(FOTOGRAMA_IZQUIERDA);
		} else {
			velX.set(0);
			fotogramaDestino.set(FOTOGRAMA_CENTRO);
		}
	}

	/**
	 * Crea un disparo
	 */
	public void disparar() {
		motor.agregarCapaDisparosAmigos(new DisparoAmigo(x, y, fuerzaDisparo));
	}

	/**
	 * Mata la nave, genera una explosion y avisa a la partida de que se ha
	 * destruido la nave
	 */
	@Override
	public void matar() {
		salud.set(0);
		vivo = false;
		// generar explosion y morir
		motor.agregarCapaFx(new Explosion(x, y, Explosion.NAVE));

		// avisar a partida
		partida.naveDestruida();
	}

	@Override
	public int getFuerza() {
		return salud.get();
	}

	@Override
	public int getRadio() {
		return mitadAlto;
	}

	/**
	 * No implementada ya que la colision con la nave la calculan los otros objetos
	 * segun el funcionamiento del motor
	 */
	@Override
	public boolean colisiona(Colisionable otro) {
		return false;
	}

	/**
	 * Golpea la nave con una fuerza. Comprueba la salud y si es menor o igual a 0 mata la nave
	 * @see Nave#matar()
	 */
	@Override
	public void golpear(int fuerza) {
		if (nivelEscudo.get() > 0)
			return;
		salud.addAndGet(-fuerza);
		if (salud.get() <= 0)
			matar();
	}

	
	/**
	 * Define el nivel el escudo
	 * 
	 * @param activo True para subir un nivel de escudo , false para bajar un nivel
	 *               de escudo
	 */
	public void setEscudoActivo(boolean activo) {
		if (activo)
			nivelEscudo.incrementAndGet();
		else
			nivelEscudo.decrementAndGet();
	}

	/**
	 * Devuelve la salud actual de la nave
	 * @return La salud
	 */
	public float getSalud() {
		return salud.get();
	}

	/**
	 * Incrementa la salud de la nave
	 * @param i Incremento a realizar
	 */
	public void agregarSalud(int i) {
		if (salud.addAndGet(i) > Config.SALUD_MAXIMA)
			salud.set(Config.SALUD_MAXIMA);
	}

	
	/**
	 * Aumenta la fuerza del disparo
	 */
	public void aumentarFuerzaDisparo() {
		fuerzaDisparo++;
	}

	/**
	 * Calcula el impacto de un disparo con la nave
	 */
	@Override
	public boolean impactoDisparo(Disparo disparo) {
		boolean impactado = false;
		// componentes de vector de posicion relativa
		double xt = Math.abs(disparo.getX()) - x;
		double yt = Math.abs(disparo.getY()) - y;
		// comprobacion de que el disparo esta en la caja contenedora
		if (xt > mitadAlto || yt > mitadAlto)
			return false;

		// comprobacion por distancia
		double distancia = Math.sqrt(xt * xt + yt * yt);

		// calcular efectos de impacto
		if (distancia < mitadAlto) {
			golpear(disparo.getFuerza());
			impactado = true;
		}

		// resultado del impacto
		return impactado;
	}

}
