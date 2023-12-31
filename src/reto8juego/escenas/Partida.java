/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.GeneradorMeteoritos;
import reto8juego.actores.Nave;
import reto8juego.actores.TextoCentrado;
import reto8juego.motor.AnimacionFrenada;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Strings;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Partida extends Escena {
	TextoCentrado textoPausa;
	int puntos = 0;
	Nave nave;
	/**
	 * La partida responde o no al teclado
	 */
	boolean controlActivo = false;
	int nivel = 1;

	boolean arriba = false;
	boolean abajo = false;
	boolean izquierda = false;
	boolean derecha = false;
	GeneradorMeteoritos generadorMeteoritos = new GeneradorMeteoritos(this);

	/**
	 * @param control
	 * @param callbackTerminado
	 */
	public Partida(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();

	}

	/**
	 * 
	 */
	private void iniciarNivel() {
		nave.setAnimacionY(null);
		controlActivo = true;
		generadorMeteoritos.start();
	}

	@Override
	public void iniciar() {
		motor.vaciarCapas();
		controlActivo = false;
		nivel = 1;
		arriba = false;
		abajo = false;
		izquierda = false;
		derecha = false;
		nave = new Nave();
		motor.agregarCapaNave(nave);
		nave.setAnimacionY(new AnimacionFrenada(2000, 1124, 700, () -> {
			iniciarNivel();
		}));
		nave.getAnimacionY().start();
		motor.agregarCapaGui(new TextoCentrado(Strings.NIVEL+" "+nivel, true, 2000));

	}

	@Override
	public void terminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		if (controlActivo) {
			if (kc == e.VK_UP)
				arriba = true;
			if (kc == e.VK_DOWN)
				abajo = true;
			if (kc == e.VK_LEFT)
				izquierda = true;
			if (kc == e.VK_RIGHT)
				derecha = true;
			nave.teclas(arriba, abajo, izquierda, derecha);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int kc = e.getKeyCode();

		if (kc == e.VK_P) {
			if (!motor.togglePlay()) {
				textoPausa = new TextoCentrado("PAUSA", false,0);
				motor.agregarCapaGui(textoPausa);
				motor.repintar();
			} else {
				textoPausa.setVivo(false);
			}
		}

		if (controlActivo) {
			if (kc == e.VK_UP)
				arriba = false;
			if (kc == e.VK_DOWN)
				abajo = false;
			if (kc == e.VK_LEFT)
				izquierda = false;
			if (kc == e.VK_RIGHT)
				derecha = false;
			if (kc == e.VK_SPACE)
				nave.disparar();
			nave.teclas(arriba, abajo, izquierda, derecha);
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public int getNivel() {
		return nivel;
	}

}
