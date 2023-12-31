/**
 * 
 */
package reto8juego.motor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class AnimacionFrenada extends Animacion {
	long duracion;
	float valorA;
	float valorB;
	float rango;
	Funcion callback;
	boolean terminado = false;
	Motor motor;
	
	public AnimacionFrenada(long duracion, float valorA, float valorB, Funcion callback) {
		motor=Motor.getInstancia();
		this.duracion = duracion;
		this.valorA = valorA;
		this.valorB = valorB;
		this.rango = valorB - valorA;
		this.valorFinal = new AtomicInteger(Float.floatToIntBits(valorA));
		this.callback=callback;
	}

	@Override
	public void run() {
		long inicio = motor.getTiempo();
		boolean terminar = false;
		while (!terminar) {
			long ahora = motor.getTiempo();
			float delta = (float) (ahora - inicio) / (float) duracion;
			if (delta > 1) {
				setValor(valorB);
				terminar = true;
			} else {
				setValor((float) (valorA + (-(Math.cos(Math.PI * delta) - 1) / 2) * rango));
				try {
					sleep(10);
				} catch (InterruptedException e) {

				}
			}
		}
		if (callback!=null)
			callback.apply();
	}

	public float getValor() {
		return Float.intBitsToFloat(valorFinal.get());
	}

	private void setValor(float v) {
		this.valorFinal.set(Float.floatToIntBits(v));
	}

 
}
