/**
 * 
 */
package reto8juego.actores;

import java.util.Random;

import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.motor.Motor;

/**
 * Genera meteoritos en los intervalos definidos en la configuracion. Los genera
 * en una posicion aleatoria sobre la pantalla y los dirige a una posicion
 * aleatoria del borde inferior de la misma.
 * 
 * @author Jose Javier Bailon Ortiz
 * @see Config
 * @see Meteorito
 */
public class GeneradorMeteoritos extends Thread {

	/**
	 * Generador aleatorio
	 */
	private Random r = new Random();

	/**
	 * True si el generador vive. False implica el fin del hilo
	 */
	private boolean vivo = true;

	/**
	 * Referencia a la partida
	 */
	private Partida partida;

	/**
	 * Nivel actual de generacion de meteoritos
	 */
	private int nivel = 1;

	/**
	 * Referencia al motor
	 */
	private Motor motor;

	/**
	 * Constructor
	 * 
	 * @param partida Referencia a la partida
	 */
	public GeneradorMeteoritos(Partida partida) {
		this.partida = partida;
		motor = Motor.getInstancia();
	}

	/**
	 * Mientras vive genera un meteorito cada intervalo definido en la
	 * configuracion.
	 */
	@Override
	public void run() {
		while (vivo) {
			try {
				sleep(Config.INTERVALO_METEORITOS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			generarMeterorito();
		}

	}

	/**
	 * Crea un meteorito en una posicion aleatoria sobre la pantalla dandole un
	 * vector velocidad a una posicion aleatoria bajo la pantalla
	 */
	private void generarMeterorito() {

		// calcular origen y vector velocidad normalizado
		double xOrigen = r.nextDouble(0, motor.getAncho());
		double xDestino = r.nextDouble(0, motor.getAncho());
		double desfaseX = xDestino - xOrigen;
		double desfaseY = motor.getAlto() - 100;
		double longitud = Math.sqrt(desfaseX * desfaseX + desfaseY * desfaseY);

		// normalizar vector de velocidad
		double velX = desfaseX / longitud;
		double velY = desfaseY / longitud;

		//crear el meteorito
		Meteorito m = new Meteorito(xOrigen, -100, velX, velY, nivel);
		motor.agregarCapaMeteoritos(m);
	}

	/**
	 * Termina el generador de meteoritos
	 */
	public void terminar() {
		vivo = false;
	}

}
