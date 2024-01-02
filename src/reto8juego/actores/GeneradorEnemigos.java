/**
 * 
 */
package reto8juego.actores;

import java.util.Random;

import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.motor.Motor;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class GeneradorEnemigos extends Thread {
	Random r = new Random();
	Partida partida;
	int x;
	int mov;
	int tipo;
	int cantidad;
	int frecuencia;
	int nivel;
	Motor motor;

	/**
	 * 
	 * @param partida
	 * @param x
	 * @param mov
	 * @param tipo
	 * @param cantidad
	 * @param frecuencia
	 * @param nivel
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

	@Override
	public void run() {
		for (int i = 0; i < cantidad; i++) {
			Enemigo enemigo = new Enemigo(partida,x, mov, tipo, nivel);
			motor.agregarCapaEnemigos(enemigo);
			try {
				sleep(frecuencia);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
