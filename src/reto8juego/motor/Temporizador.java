/**
 * 
 */
package reto8juego.motor;


/**
 * Hilo independiente que ejecuta una accion definida en un callback una vez
 * ha transcurrido el tiempo del motor especificado 
 * @author Jose Javier Bailon Ortiz
 */
public class Temporizador extends Thread {
	
	/**
	 * Tiempo a esperar antes de ejecutar la accion
	 */
	private long espera;
	
	/**
	 * Accion a ejecutar al pasar el tiempo
	 */
	private Funcion callback;
	
	/**
	 * Referencia al motor
	 */
	private Motor motor;

	
	/**
	 * Constructor
	 * 
	 * @param espera Tiempo a esperar
	 * @param callback Callback a ejetuar tras la espera
	 */
	public Temporizador(long espera, Funcion callback) {
		motor = Motor.getInstancia();
		this.espera = espera;
		this.callback = callback;
		this.start();
	}

	/**
	 * Comprueba cada 10 ms si el tiempo del motor ha pasado 
	 * hasta completar la espera
	 */
	@Override
	public void run() {
		//tiempo inicial en el motor
		long inicio = motor.getTiempo();
		boolean terminar = false;
		//Buble mientras el motor no haya avanzado el tiempo necesario
		while (!terminar) {
 			if (motor.getTiempo() > inicio + espera) {
				terminar = true;
			} else {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					terminar=true;
				}
			}
		}
		
		//ejecutar el callback
		if (callback != null)
			callback.apply();
	}
}
