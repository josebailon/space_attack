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
public class GeneradorMeteoritos extends Thread{
	Random r=new Random();
	boolean vivo=true;
	Partida partida;
	int nivel=1;
	Motor motor;
	long ahora;
	long proximo=0;
	public GeneradorMeteoritos(Partida partida) {
		this.partida = partida;
		motor=Motor.getInstancia();
		ahora=motor.getTiempo();
		
	}


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
	 * 
	 */
	private void generarMeterorito() {
		
		//calcular origen  y vector velocidad normalizado
		double xOrigen = r.nextDouble(0,motor.getAncho());
		double xDestino= r.nextDouble(0,motor.getAncho());
		double desfaseX=xDestino-xOrigen;
		double desfaseY=motor.getAlto()-100;
		double longitud =  Math.sqrt(desfaseX * desfaseX + desfaseY * desfaseY);

		// normalizar vector de velocidad
		double velX=desfaseX/longitud;
		double velY=desfaseY/longitud;

			Meteorito m = new Meteorito(xOrigen,-100,velX,velY,nivel);
			motor.agregarCapaMeteoritos(m);
	}


 

	public void terminar() {
		vivo=false;
	}

}
