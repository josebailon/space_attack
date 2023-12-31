/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.Fondo;
import reto8juego.actores.TextoCentrado;
import reto8juego.actores.TituloJuego;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Strings;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Inicio extends Escena{
	
	TextoCentrado textoCentrado;
	TituloJuego textoTitulo;
	
	/**
	 * Define si la escena responde al teclado
	 */
	boolean controlActivo=true;
	
	/**
	 * @param control
	 * @param callbackTerminado 
	 */
	public Inicio(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();
 	}

	@Override
	public void iniciar() {
		motor.vaciarCapas();
		motor.agregarCapaFondo(new Fondo());
		textoCentrado = new TextoCentrado(Strings.TEXTO_INICIA_PARTIDA,true,0);
		motor.agregarCapaGui(textoCentrado);
		textoTitulo= new TituloJuego();
		motor.agregarCapaGui(textoTitulo);
		motor.play();
	}

	@Override
	public void terminar() {
		callbackTerminado.apply();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!controlActivo)
			return;
		int kc=e.getKeyCode();
		if (kc==e.VK_SPACE) {
			controlActivo=false;
			textoCentrado.animacionSalida(() -> terminar());
			textoTitulo.animacionSalida(null);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
