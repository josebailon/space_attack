/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.GeneradorMeteoritos;
import reto8juego.actores.Nave;
import reto8juego.actores.TextoCentrado;
import reto8juego.actores.TextoPausa;
import reto8juego.actores.VisorPuntos;
import reto8juego.actores.VisorSalud;
import reto8juego.motor.AnimacionFrenada;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;
import reto8juego.motor.Temporizador;
import reto8juego.recursos.Strings;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Partida extends Escena {
	TextoPausa textoPausa;
 	int puntos = 0;
	private Nave nave;
	private int vidas=3;
	/**
	 * La partida responde o no al teclado
	 */
	boolean controlActivo = false;
	int nivel = 1;
	boolean arriba = false;
	boolean abajo = false;
	boolean izquierda = false;
	boolean derecha = false;
	GeneradorMeteoritos generadorMeteoritos;
	int oleadasRestantes=1;
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
		generadorMeteoritos = new GeneradorMeteoritos(this);
		generadorMeteoritos.start();
		new Temporizador(3, ()->siguienteOleada());
	}

	@Override
	public void iniciar() {
		motor.vaciarCapas();
		controlActivo = false;
		nivel = 1;
		
		addNave();
		motor.agregarCapaGui(new TextoCentrado(Strings.NIVEL+" "+nivel, true, 2000,null));
		iniciarNivel();
		addGUI();
	
	}

	/**
	 * @return
	 */
	private Object siguienteOleada() {
		// TODO Auto-generated method stub
		return null;
	}

	private void resetControl() {
		arriba = false;
		abajo = false;
		izquierda = false;
		derecha = false;
	}
	
	/**
	 * 
	 */
	private void addNave() {
		resetControl();
		controlActivo=false;
		nave = new Nave(this);
		motor.agregarCapaNave(nave);
		nave.setAnimacionY(new AnimacionFrenada(2000, 1124, 700, () -> {
			nave.setAnimacionY(null);
			controlActivo = true;
		}));
		new Temporizador(3000,()->nave.setEscudoActivo(false));
	}
 
	/**
	 * 
	 */
	private void addGUI() {
		motor.agregarCapaGui(new VisorSalud(this));
		motor.agregarCapaGui(new VisorPuntos(this));
		
	}

	@Override
	public void terminar() {
		generadorMeteoritos.terminar();
		motor.agregarCapaGui(new TextoCentrado(Strings.GAME_OVER, true, 2000,()->control.pantallaInicial()));

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
				textoPausa = new TextoPausa();
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

	public Nave getNave() {
		return nave;
	}

	/**
	 * 
	 */
	public void naveDestruida() {
		vidas--;
		if (vidas>0) {
			addNave();
		}
		else {
			terminar();
		}
		
	}

	/**
	 * @param puntos
	 */
	public void agregarPuntos(int puntos) {
		this.puntos+=puntos;
		
	}

	/**
	 * @return
	 */
	public int getPuntos() {
		return puntos;
	}

	/**
	 * @return
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * 
	 */
	public void agregarVida() {
		vidas++;
		
	}

	
}
