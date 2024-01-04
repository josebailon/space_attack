/**
 * 
 */
package reto8juego.actores;

import reto8juego.escenas.Partida;
import reto8juego.motor.Motor;

/**
 * Genera enemigos segun los parametros de la  configuracion de la oleada.
 * 
 * @author Jose Javier Bailon Ortiz
 * @see Enemigo
 */
public class GeneradorEnemigos extends Thread {
	
	/**
	 * Referencia a la partida
	 */
	private Partida partida;
	
	/**
	 * Posicion x de generacion de enemigos(todos los enemigos se generan automaticamente en la Y=-50)
	 */
	private int x;
	
	/**
	 * Tipo de movimiento
	 */
	private int mov;
	
	/**
	 * Tipo de nave
	 */
	private int tipo;
	
	/**
	 * Cantidad  a generar
	 */
	private int cantidad;
	
	/**
	 * Intervalo entre la creacion de dos naves
	 */
	private int frecuencia;
	
	/**
	 * Nivel de nave a generar
	 */
	private int nivel;
	
	/**
	 * Referencia al motor
	 */
	private Motor motor;

	/**
	 * Constructor 
	 * 
	 * @param partida 	Referencia a la partida
	 * @param x 		Posicion x de generacion de enemigos(todos los enemigos se generan automaticamente en la Y=-50
	 * @param mov 		Tipo de movimiento
	 * @param tipo 		Tipo de nave
	 * @param cantidad 	Cantidad  a generar
	 * @param frecuencia  Intervalo en ms entre la creacion de dos naves
	 * @param nivel 	Nivel de las naves creadas
	 */
	public GeneradorEnemigos(Partida partida, int x, int mov, int tipo, int cantidad, int frecuencia, int nivel) {
		this.partida = partida;
		this.x = x;
		this.mov = mov;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.frecuencia = frecuencia;
		this.nivel = nivel;
		motor = Motor.getInstancia();
	}

	/**
	 * Crear naves y hacer las esperas entre naves
	 */
	@Override
	public void run() {
		for (int i = 0; i < cantidad; i++) {
			Enemigo enemigo = new Enemigo(partida,x, mov, tipo, nivel);
			motor.agregarCapaEnemigos(enemigo);
			try {
				sleep(frecuencia);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
