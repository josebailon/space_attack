/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.GeneradorEnemigos;
import reto8juego.actores.GeneradorMeteoritos;
import reto8juego.actores.Nave;
import reto8juego.actores.TextoCentrado;
import reto8juego.actores.TextoPausa;
import reto8juego.actores.VisorPuntos;
import reto8juego.actores.VisorSaludNivel;
import reto8juego.config.Config;
import reto8juego.config.Niveles;
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
	private TextoPausa textoPausa;
 	private int puntos = 0;
	private Nave nave;
	private int vidas=Config.VIDAS_INICIALES;
	/**
	 * La partida responde o no al teclado
	 */
	private boolean controlActivo = false;
	private int nivel = 1;
	private boolean arriba = false;
	private boolean abajo = false;
	private boolean izquierda = false;
	private boolean derecha = false;
	private GeneradorMeteoritos generadorMeteoritos;
	private int siguienteOleada;
	private int[][] oleadasNivel;
	private GeneradorEnemigos generadorEnemigos;
	private boolean terminada =false;
	
	
	/**
	 * @param control
	 * @param callbackTerminado
	 */
	public Partida(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();
	}

	@Override
	public void iniciar() {
		motor.vaciarCapas();
		controlActivo = false;
		nivel = 1;
		
		addNave();
		addGUI();
		nuevoNivel();
		generadorMeteoritos = new GeneradorMeteoritos(this);
		generadorMeteoritos.start();
	
	}

	@Override
	public void terminar() {
		controlActivo=false;
		terminada=true;
		generadorMeteoritos.terminar();
		new Temporizador(6000, ()->callbackTerminado.apply());
	}

	/**
	 * 
	 */
	private void nuevoNivel() {
		if (terminada)
			return;
		oleadasNivel=Niveles.getNivel(nivel-1);
		if (oleadasNivel==null) {
			ganarPartida();
			return;
		}
		motor.agregarCapaGui(new TextoCentrado(Strings.NIVEL+" "+nivel, true, 2000,null));
		siguienteOleada=0;
		lanzarSiguienteOleada();

	}

	/**
	 * 
	 */
	private void ganarPartida() {
		nave.setEscudoActivo(true);
		nave.setAnimacionY(new AnimacionFrenada(2000, (float)nave.getY(), -100, null));
		motor.agregarCapaGui(new TextoCentrado(Strings.MENSAJE_GANAR, true, 3000,null));
		terminar();
	}

	private void perderPartida() {
		System.out.println("PERDER PARTIDA");
		motor.agregarCapaGui(new TextoCentrado(Strings.MENSAJE_PERDER, true, 2000,null));
		terminar();
	}
	
	/**
	 * @return
	 */
	private void lanzarSiguienteOleada() {
		if (terminada)
			return;
		if (oleadasNivel!=null &&siguienteOleada==oleadasNivel.length) {
			nivel++;
			new Temporizador(5000, ()-> nuevoNivel());
			return;
		}
			
		int[]  oleada = oleadasNivel[siguienteOleada];
		//tiempo, MOV, tipo enemigo, cantidad, frecuencia
		int tiempo=oleada[0];
		int x = oleada[1];
		int mov=oleada[2];
		int tipoEnem=oleada[3];
		int cantidad=oleada[4];
		int frecuencia=oleada[5];
		new Temporizador(tiempo, ()->crearOleada(x, mov,tipoEnem,cantidad,frecuencia));
		siguienteOleada++;
	}

	/**
	 * @param mov
	 * @param tipoEnem
	 * @param cantidad
	 * @param frecuencia
	 * @return
	 */
	private void crearOleada(int x, int mov, int tipoEnem, int cantidad, int frecuencia) {
		generadorEnemigos=new  GeneradorEnemigos(this, x, mov, tipoEnem, cantidad, frecuencia, nivel);
		generadorEnemigos.start();
		lanzarSiguienteOleada();
	}

 
	
	/**
	 * 
	 */
	private void addNave() {
		arriba = false;
		abajo = false;
		izquierda = false;
		derecha = false;
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
		motor.agregarCapaGui(new VisorSaludNivel(this));
		motor.agregarCapaGui(new VisorPuntos(this));
		
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
	public void destruirNave() {
		vidas--;
		if (vidas>0) {
			addNave();
		}
		else {
			perderPartida();
		}
		
	}

	/**
	 * @param puntos
	 */
	public void agregarPuntos(int puntos) {
		this.puntos+=puntos;
		
	}

	/**
	 * 
	 */
	public void agregarVida() {
		vidas++;
		
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
 

	
}
