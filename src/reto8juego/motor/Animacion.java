/**
 * 
 */
package reto8juego.motor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Animacion extends Thread {
	AtomicInteger valorFinal;
	
	public float getValor() {
		return Float.intBitsToFloat(valorFinal.get());
	}

	private void setValor(float v) {
		this.valorFinal.set(Float.floatToIntBits(v));
	}
}
