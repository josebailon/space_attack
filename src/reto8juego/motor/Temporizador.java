/**
 * 
 */
package reto8juego.motor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Temporizador extends Animacion {
	long duracion;
	float valorA;
	float valorB;
	float rango;
	Funcion callback;
	boolean terminado = false;
	Motor motor;

	public Temporizador(long duracion, Funcion callback) {
		motor = Motor.getInstancia();
		this.duracion = duracion;
		this.callback = callback;
		this.start();
	}

	@Override
	public void run() {
		long inicio = motor.getTiempo();
		boolean terminar = false;
		while (!terminar) {
			long ahora = motor.getTiempo();
			float delta = (float) (ahora - inicio) / (float) duracion;
			if (motor.getTiempo() > inicio + duracion) {
				terminar = true;
			} else {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					terminar=true;
				}
			}
		}
		if (callback != null)
			callback.apply();
	}
}
