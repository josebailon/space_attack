/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.Menu;
import reto8juego.actores.TituloJuego;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;

/**
 * La escena esta compuesta del titulo del juego y un menu
 *  
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class EscenaInicial extends Escena{
	
	/**
	 * Objeto que se encarga de dibujar el titulo del juego
	 */
	TituloJuego textoTitulo;
	
	/**
	 * Objeto menu para seleccionar entre jugar partida, ver instrucciones o salir
	 */
	Menu menu;
	/**
	 * Define si la escena responde al teclado
	 */
	boolean controlActivo=true;
	
	
	/**
	 * @param control Referencia al controlador
	 * @param callbackTerminado  Callback a ejecutar al terminar la escena
	 */
	public EscenaInicial(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();
 	}

	
	
	@Override
	public void iniciar() {
		//vaciar las capas del motor
		motor.vaciarCapas();
		//crear titulo del juego
		textoTitulo= new TituloJuego();
		motor.agregarCapaGui(textoTitulo);
		
		menu = new Menu(control);
		motor.agregarCapaGui(menu);
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
