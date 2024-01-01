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
	float valorFinal;
	
	public float getValor() {
		return valorFinal;
	}

	private void setValor(float v) {
		this.valorFinal=v;
	}
}
