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
public class GeneradorEnemigos extends Thread{
	Random r=new Random();
	boolean vivo=true;
	Partida partida;
	int tipo;
	int nivel=1;
	Motor motor;
	int cantidad=0;
	long ahora;
	long proximo=0;
	public GeneradorEnemigos(Partida partida,int tipo, int nivel, int cantidad, int x, int y) {
		this.partida = partida;
		motor=Motor.getInstancia();
		ahora=motor.getTiempo();
		
	}


	@Override
	public void run() {
		calculaTiempoProximaGeneracion();
		while (vivo) {
			ahora=motor.getTiempo();
			nivel = partida.getNivel();
			if (proximo<ahora) {
				generarMeterorito();
				calculaTiempoProximaGeneracion();
			}
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


	/**
	 * 
	 */
	private void calculaTiempoProximaGeneracion() {
		ahora=motor.getTiempo();
 		int espera = Config.INTERVALO_METEORITOS/nivel;
 		proximo=ahora+espera;
		try {
			sleep(500);
		} catch (InterruptedException e) {
			vivo=false;
		}
 	}


	public void terminar() {
		vivo=false;
	}

}
