/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.IconoConTexto;
import reto8juego.config.Config;
import reto8juego.motor.AnimacionFrenada;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Strings;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Instrucciones extends Escena{
	
	
	private final String[] textos= {Strings.INST_TXT0,Strings.INST_TXT1,Strings.INST_TXT2,Strings.INST_TXT3,Strings.INST_TXT4,Strings.INST_TXT5,Strings.INST_TXT6,Strings.INST_TXT7};
	private final String[] iconos= {Strings.INST_ICO0,Strings.INST_ICO1,Strings.INST_ICO2,Strings.INST_ICO3,Strings.INST_ICO4,Strings.INST_ICO5,Strings.INST_ICO6,Strings.INST_ICO7,};
	private IconoConTexto[] lineas = new IconoConTexto[9];
	private boolean animando=true;
	/**
	 * Define si la escena responde al teclado
	 */
	boolean controlActivo=true;
	
	/**
	 * @param control
	 * @param callbackTerminado 
	 */
	public Instrucciones(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();
 	}

	@Override
	public void iniciar() {
		int xinicio=Config.ANCHO+200;
		int x=130;
		int y=120;
		motor.vaciarCapas();
		for (int i=0;i<textos.length;i++) {
		IconoConTexto texto=new IconoConTexto(textos[i],iconos[i] , xinicio, y,false);
		AnimacionFrenada animacion=new AnimacionFrenada(1000, xinicio, x, null);
		try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		texto.setAnimacionX(animacion);
		motor.agregarCapaGui(texto);
		y+=100;
		lineas[i]=texto;
		}
		IconoConTexto textoVolver=new IconoConTexto(Strings.VOLVER,null , Config.CENTRO_ANCHO, y,true);
		AnimacionFrenada animacion=new AnimacionFrenada(1000, Config.ALTO+100, y, ()->animando=false);
		textoVolver.setAnimacionY(animacion);
		motor.agregarCapaGui(textoVolver);
		lineas[8]=textoVolver;
	}

	@Override
	public void terminar() {
		animando=true;
		int x=130;
		int xfin=Config.ANCHO+200;
		for (int i=0;i<textos.length;i++) {
			AnimacionFrenada animacion=new AnimacionFrenada(1000, x,xfin, null);
			lineas[i].setAnimacionX(animacion);
	
		}
		AnimacionFrenada animacion=new AnimacionFrenada(1000,(float) lineas[8].getY(),Config.ALTO+100, ()->callbackTerminado.apply());
		lineas[8].setAnimacionY(animacion);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!animando)
		terminar();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
