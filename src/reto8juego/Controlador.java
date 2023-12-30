/**
 * 
 */
package reto8juego;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import reto8juego.actores.Fondo;
import reto8juego.actores.Nave;
import reto8juego.actores.TextoCentrado;
import reto8juego.config.Strings;
import reto8juego.gui.Ventana;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Controlador implements KeyListener {
	private final int INICIO =0;
	private final int JUGANDO =1;
	private final int PERDIDO =1;
	
	Ventana ventana;
	Motor motor;
	private int estado = INICIO;
	public static Controlador CONTROL;
	
	TextoCentrado t;
	
	public Controlador() {
		CONTROL=this;
		//cargar recursos
		Recursos.getInstancia().cargarRecursos();
		//crear motor
		motor = Motor.getInstancia();
		//crear ventana
		ventana = new Ventana(this,motor);
		pantallaInicial();
	}
	
	
	
	
	/**
	 * 
	 */
	private void pantallaInicial() {
		estado=INICIO;
		motor.vaciar();
		motor.agregarCapaFondo(new Fondo());
		t = new TextoCentrado(Strings.TEXTO_INICIA_PARTIDA);
		motor.agregarCapaGui(t);
		motor.play();
	}


 
	/**
	 * 
	 */
	public void iniciarPartida() {
		estado=JUGANDO;
 		motor.vaciar();
 		motor.agregarCapaNave(new Nave());
	
	}






	@Override
	public void keyPressed(KeyEvent e) {
		int kc=e.getKeyCode();
		
	}




	@Override
	public void keyReleased(KeyEvent e) {
		int kc=e.getKeyCode();
		if (estado==INICIO && kc==e.VK_SPACE)
			t.animacionSalida(() -> iniciarPartida());
		
	}
	




	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
