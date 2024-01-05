/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import reto8juego.config.Niveles;
import reto8juego.escenas.Partida;
import reto8juego.motor.Colisionable;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Disparable;
import reto8juego.motor.Disparo;
import reto8juego.motor.Movimiento;
import reto8juego.recursos.Recursos;

/**
 * Nave enemiga. Es Disparable y Colisionable.
 * 
 * Su movimiento es controlado por la lamda definida en el atributo Movimiento
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Enemigo extends Dibujo implements Disparable {

	/**
	 * Generador de numeros aleatorios
	 */
	private Random r = new Random();

	/**
	 * Imagen de la nave
	 */
	private BufferedImage img;

	/**
	 * Controlador del movimiento de la nave
	 */
	private Movimiento movimiento;

	/**
	 * Salud de la navi
	 */
	private AtomicInteger salud = new AtomicInteger(1);

	/**
	 * radio de la nave
	 */
	private int radio = 0;

	/**
	 * fuerza del golpe efectuado a la nave del jugador al colisionar
	 */
	private int fuerza = 1;

	/**
	 * Nivel de la nave
	 */
	private int nivel = 1;

	/**
	 * Cantidad maxima de disparos que efectua
	 */
	private int cantidadDisparos;

	/**
	 * Referencia a la partida
	 */
	private Partida partida;

	/**
	 * Constructor
	 * 
	 * @param partida Referencia a la partida
	 * @param x       Posicion inicial x de la nave
	 * @param mov     Tipo de movimiento
	 * @param tipo    Tipo de nave
	 * @param nivel   Nivel de la nave
	 */
	public Enemigo(Partida partida, double x, int mov, int tipo, int nivel) {
		super(x, -50);
		this.partida = partida;
		this.nivel = nivel;
		setTipo(tipo);
		setMovimiento(mov);
		salud.set(nivel);
		fuerza = 50 + 5 * this.nivel;
		cantidadDisparos = 1 + nivel % 3;
	}

	/**
	 * Dibuja la imagen de la nave
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x - mitadAncho, y - mitadAlto);
		g2d.drawImage(img, at, null);
		at.setToIdentity();

	}

	/**
	 * Calcula la posicion de la nave segun la lamda de movimiento. Mata la nave una
	 * vez sale por la parte inferior del lienzo. Si le quedan disparos disponibles
	 * intenta disparar.
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
		movimiento.apply(deltaSegundo);
		if (y > motor.getAlto() + 100)
			this.vivo = false;

		if (cantidadDisparos > 0)
			intentarDisparar();
	}

	/**
	 * Mata la nave generando un actor de explosion de nave y un actor de premio de
	 * clase PremioPuntos
	 */
	@Override
	public void matar() {
		salud.set(0);
		vivo = false;
		// generar explosion y morir
		motor.agregarCapaFx(new Explosion(x, y, Explosion.NAVE));
		motor.agregarCapaPremios(new PremioPuntos(x, y, nivel));
	}

	@Override
	public int getFuerza() {
		return fuerza;
	}

	/**
	 * El radio de colision es la mitad del alto de la nave
	 */
	@Override
	public int getRadio() {
		return mitadAlto;
	}

	/**
	 * Calcula la colision. Devuelve true si ha colisionado y false si no ha
	 * colisionado. La colision se calcula por la distancia con la otra nave(la del
	 * jugador). Tiene previamente una optimizacion para dar por no colisionado si
	 * la nave del jugador esta en algun eje a mas de la suma de los radios. Si la
	 * colision se produce registra el golpe propio y el golpe de la nave del
	 * jugador restando salud segun corresponda.
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
			// efectos propioes
			this.golpear(otro.getFuerza());
			// efectos ajenos
			otro.golpear(fuerza);
			colisionado = true;
		}
		// resultado del impacto
		return colisionado;
	}

	/**
	 * Decrementa la salud segun la fuerza del impacto y comprueba si la nave ha
	 * muerto
	 */
	@Override
	public void golpear(int fuerza) {
		salud.addAndGet(-fuerza);
		// comprobar si sigue vivo
		if (salud.get() <= 0)
			matar();

	}

	/**
	 * Comprueba si un disparo ha impactado en la nave siguiendo un sistema similar
	 * al de colision pero presuponiendo radio 0 al disparo.
	 */
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

	/**
	 * Define el tipo de nave. Dependiendo del tipo de nave elige una imagen
	 * diferente. En funcion de eso define los parametros de alto y ancho y
	 * parametros dependientes
	 * 
	 * @param tipo Tipo de nave
	 */
	private void setTipo(int tipo) {
		img = Recursos.getInstancia().getImg("enemigo" + tipo);
		ancho = img.getWidth();
		alto = img.getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
		this.radio = mitadAncho;
	}

	/**
	 * Define el movimiento que hace la nave creando una lambda que define el
	 * movimiento segun el tipo de movimiento.
	 * 
	 * Puede ser recto y la nave hace un movimiento recto vertical de arriba a abajo
	 * u ondulado en cuyo caso la posicion en x va definido por el seno de y/50*100
	 * creando un movimiento descendente vertical y ondulado.
	 * 
	 * @param mov
	 */
	private void setMovimiento(int mov) {
		switch (mov) {
		case Niveles.MOV_RECTO -> {
			movimiento = (d) -> {
				y += 200 * d;
			};
		}
		case Niveles.MOV_ONDULADO -> {
			int xorig = (int) x;
			movimiento = (d) -> {
				y += 200 * d;
				x = xorig + Math.sin(y / 50) * 100;
			};
		}

		}
	}

	/**
	 * Intenta generar un disparo. Lo hace en el 1% de fotogramas mientras le queden
	 * disparos restantes y la nave del jugador este en juego. Al generar el disparo
	 * lo genera dirigido hacia la posicion de la nave del jugador en el
	 * momento de crearse el disparo
	 */
	private void intentarDisparar() {
		Nave nave = partida.getNave();
		if (cantidadDisparos > 0 && nave != null && y > 0 && r.nextFloat() < 0.01) {
			
			//calcular vector direccion normalizado
			double dx = nave.getX() - x;
			double dy = nave.getY() - y;
			double longitud = Math.sqrt(dx * dx + dy * dy);
			dx = dx / longitud * 200;
			dy = dy / longitud * 200;
			
			//crear el disparo
			motor.agregarCapaDisparosEnemigos(new DisparoEnemigo(x, y, dx, dy, nivel * 10));
			cantidadDisparos--;
		}

	}

}
