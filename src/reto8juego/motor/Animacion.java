
package reto8juego.motor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Animacion autonoma entre dos valores. Un hilo independiente
 *  genera la transicion entre dos valores en un tiempo determinado
 *  usando una funcion de suavizado por coseno. El valor actual es 
 *  usado por otros objetos para animar alguno de sus atributos.
 *  
 * @author Jose Javier Bailon Ortiz
 */
public class Animacion extends Thread {
	
	/**
	 * Duracion de la transicion
	 */
	private long duracion;
	
	/**
	 * Valor inicial
	 */
	private float valorA;
	
	/**
	 * Valor final
	 */
	private float valorB;
	
	/**
	 * valor inicial - valor final
	 */
	private float rango;
	
	/**
	 * Valor actual
	 */
	private float valorActual;
	
	/**
	 * Funcion a ejecutar al terminar la transicion
	 */
	private Funcion callback;
	
	/**
	 * Referencia al motor
	 */
	private Motor motor;
	
	
	/**
	 * Constructor
	 * @param duracion Duracion de la transicion
	 * @param valorA Valor inicial
	 * @param valorB Valor final
	 * @param callback Funcion a ejecutar tras terminar la transicion
	 */
	public Animacion(long duracion, float valorA, float valorB, Funcion callback) {
		motor=Motor.getInstancia();
		this.duracion = duracion;
		this.valorA = valorA;
		this.valorB = valorB;
		this.rango = valorB - valorA;
		this.valorActual =  valorA;
		this.callback=callback;
		this.start();
	}

	/**
	 * Durante la carrera se va calculando el tiempo que ha pasado.
	 * La proporcion entre el tiempo pasado y la duracion de la transicion
	 * se usa como factor delta de avance de la misma(0.0->1.0). Ese factor delta se usa en la formula
	 * de trancion para calcular el valor actual. Se usa la formula
	 * de transicion suavizada por coseno: -(cos(PI*delta)-1)/2
	 */
	@Override
	public void run() {
		long inicio = motor.getTiempo();
		boolean terminar = false;
		while (!terminar) {
			//calcular delta
			long ahora = motor.getTiempo();
			float delta = (float) (ahora - inicio) / (float) duracion;
			//si delta ha llegado a 1 se termina
			if (delta > 1) {
				valorActual=valorB;
				terminar = true;
			} else {
				//aplicar la formula de transicion suavizada por coseno
				valorActual=(float) (valorA + (-(Math.cos(Math.PI * delta) - 1) / 2) * rango);
				try {
					sleep(10);
				} catch (InterruptedException e) {

				}
			}
		}
		//si hay callback ejecutarlo
		if (callback!=null)
			callback.apply();
	}

	
	/**
	 * Devuelve el valor actual
	 * 
	 * @return El valor
	 */
	public float getValor() {
		return valorActual;
	}
  
}
