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
public class EscenaInicial extends Escena {

	/**
	 * Objeto que se encarga de dibujar el titulo del juego
	 */
	private TituloJuego textoTitulo;

	/**
	 * Objeto menu para seleccionar entre jugar partida, ver instrucciones o salir
	 */
	private Menu menu;
	/**
	 * Define si la escena responde al teclado
	 */
	private boolean controlActivo = true;

	/**
	 * @param control           Referencia al controlador
	 * @param callbackTerminado Callback a ejecutar al terminar la escena
	 */
	public EscenaInicial(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();
	}

	/**
	 * Inicia la escena limpiando las capas del motor y generando el titulo del
	 * juego y el menu
	 */
	@Override
	public void iniciar() {
		// vaciar las capas del motor
		motor.vaciarCapas();

		// crear titulo del juego
		textoTitulo = new TituloJuego();
		motor.agregarCapaGui(textoTitulo);

		// crear menu
		menu = new Menu(control);
		motor.agregarCapaGui(menu);
	}

	/**
	 * Termina la escena y llama al callback definido para el final de la escena
	 */
	@Override
	public void terminar() {
		callbackTerminado.apply();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Escucha el evento de teclado keyReleased si el controlActivo es true.
	 * Lo hace de la siguiente manera:
	 * <ul>
	 * <li>ENTER o ESPACIO: Termina la escena ordenando al menu efectuar su salida. El menu se encargara de lanzar la opcion seleccionada</li>
	 * <li>CURSOR ARRIBA: Cambia la opcion seleccionada del menu</li>
	 * <li>CURSOR ABAJO: Cambia la opcion seleccionada del menu</li>
	 * </ul>
	 * 
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (!controlActivo)
			return;
		int kc = e.getKeyCode();
		if (kc == KeyEvent.VK_ENTER || kc == KeyEvent.VK_SPACE) {
			controlActivo = false;
			menu.activarOpcionSeleccionada();
			textoTitulo.animacionSalida();
		} else if (kc == KeyEvent.VK_UP)
			menu.subirOpcionSeleccionada();
		else if (kc == KeyEvent.VK_DOWN)
			menu.bajarOpcionSeleccionada();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
