/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.Menu;
import reto8juego.actores.TextoCentrado;
import reto8juego.actores.TituloJuego;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Strings;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Instrucciones extends Escena{
	
	TituloJuego textoTitulo;
	Menu menu;
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
		motor.vaciarCapas();
		textoTitulo= new TituloJuego();
		motor.agregarCapaGui(textoTitulo);
		menu = new Menu(control);
		motor.agregarCapaGui(menu);
		motor.play();
		System.out.println("instrucciones");
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
		if (kc==e.VK_ENTER||kc==e.VK_SPACE) {
			controlActivo=false;
			menu.animacionSalida();
			textoTitulo.animacionSalida();
		}else if (kc==e.VK_UP)
			menu.subirOpcionSeleccionada();
		else if (kc==e.VK_DOWN)
			menu.bajarOpcionSeleccionada();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
