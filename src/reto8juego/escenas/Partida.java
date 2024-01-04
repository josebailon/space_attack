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
import reto8juego.motor.Animacion;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;
import reto8juego.motor.Temporizador;
import reto8juego.recursos.Strings;

/**
 * <p>
 * Crea y controla los parametros de partida. Contiene los contadores de puntos
 * obtenidos, vidas extras restantes y crea y referencia la nave del jugador.
 * </p>
 * <p>
 * Registra el estado de las teclas de cursores y cada vez que se pulsa o se
 * suelta una tecla de cursor informa a la nave del estado de pulsacion de las
 * teclas. Cuando se pulsa la barra espaciadora ordena a la nave disparar.
 * Tambien actua cuando se pulsa le tecla P pausando el motor.
 * </p>
 * <p>
 * Se encarga de gestionar el flujo de avance por los niveles asi como de
 * generar las oleadas de naves enemigas y de generar los meteoritos.
 * </p>
 * <p>
 * Para gestionar el avance de los niveles sigue el siguiente proceso: Empieza
 * en el nivel 1. Al empezar un nivel recoge de la configuracion el listado de
 * oleadas del nivel. Va generando las oleadas segun las especificaciones y
 * cuando ha generado todas las oleadas pasa al siguiente nivel. Cuando todos
 * los niveles se terminan da por concluida la partida.
 * </p>
 * <p>
 * Para la generacion de las oleadas crea un GeneradorEnemigos por oleada y este
 * se encarga de ir creando las naves enemigas segun la especificacion de la
 * oleada.
 * </p>
 * <p>
 * Para la generacion de meteoritos crea en al inicio de la escena un
 * GeneradorMeteoritos el cual genera aleatoriamente meteoritos segun la
 * frecuencia definida en la configuracion
 * </p>
 * <p>
 * Cuando la nave es destruida genera una nueva a menos que no queden vidas
 * disponibles en cuyo caso da la partida por concluida.
 * </p>
 * <p>
 * Cuando la partida concluye, bien sea por perder todas las vidas o por pasar
 * el ultimo nivel se avisa al jugador y se ejecuta el callback de salida de la
 * escena que lleva al menu principal
 * </p>
 * 
 * @author Jose Javier Bailon Ortiz
 * @see Nave
 * @see GeneradorEnemigos
 * @see GeneradorMeteoritos
 * @see Niveles
 */
public class Partida extends Escena {

	/**
	 * Actor de texto de pausa
	 */
	private TextoPausa textoPausa;

	/**
	 * Puntos de la partida
	 */
	private int puntos = 0;

	/**
	 * Nave actual del jugador
	 */
	private Nave nave;

	/**
	 * Vidas restantes
	 */
	private int vidas = Config.VIDAS_INICIALES;

	/**
	 * La partida responde o no al teclado segun si esta como true o false
	 */
	private boolean controlActivo = false;

	/**
	 * Nivel actual
	 */
	private int nivel = 1;

	/**
	 * Tecla arriba pulsada
	 */
	private boolean arriba = false;

	/**
	 * Tecla abajo pulsada
	 */
	private boolean abajo = false;

	/**
	 * Tecla izquierda pulsada
	 */
	private boolean izquierda = false;

	/**
	 * Tecla derecha pulsada
	 */
	private boolean derecha = false;

	/**
	 * Generador de meteoritos
	 */
	private GeneradorMeteoritos generadorMeteoritos;

	/**
	 * Indice de la siguiente oleada del nivel
	 */
	private int siguienteOleada;

	/**
	 * Array de oleadas del nivel
	 */
	private int[][] oleadasNivel;

	/**
	 * Partida terminada (Para controlar la finalizacion de los pasos de nivel y
	 * dejar de crear oleadas mientras termina la escena)
	 */
	private boolean terminada = false;

	/**
	 * Constructor
	 * 
	 * @param control           Referencia al controlador
	 * @param callbackTerminado Callback a ejecutar tras terminar la partida
	 */
	public Partida(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();
	}

	/**
	 * Inicio de la partida
	 */
	@Override
	public void iniciar() {
		// vaciar capas del motor
		motor.vaciarCapas();

		// desactivar el teclado (una vez la nave este preparada se activara el teclado)
		controlActivo = false;

		// definir el nivel actual como 1
		nivel = 1;

		// agregar la nave
		addNave();

		// agregar elementos GUI
		addGUI();

		// Empezar el nuevo nivel
		nuevoNivel();

		// inicializar el generador de meteoritos
		generadorMeteoritos = new GeneradorMeteoritos(this);
		generadorMeteoritos.start();

	}

	/**
	 * Termina la partida esperando un tiempo para que el jugador vea el mensaje
	 * final
	 */
	@Override
	public void terminar() {
		controlActivo = false;
		terminada = true;
		generadorMeteoritos.terminar();
		new Temporizador(6000, () -> callbackTerminado.apply());
	}

	/**
	 * Empezar un nuevo nivel. Si la partida no ha terminado recupera los parametros
	 * de las oleadas de naves enemigas del nivel y las va creando. Si no hay
	 * oleadas para el nivel signifiaca que se ha llegado al final.
	 */
	private void nuevoNivel() {
		// si la partida ha terminado volver
		if (terminada)
			return;

		// recoger parametros de oleadas de naves enemigas del nivel
		oleadasNivel = Niveles.getNivel(nivel - 1);

		// si no hay oleadas se ha llegado al final y el jugador ha ganado
		if (oleadasNivel == null) {
			nivel--;
			ganarPartida();
			return;
		}

		// agrega el texto de aviso de nuevo nivel
		motor.agregarCapaGui(new TextoCentrado(Strings.NIVEL + " " + nivel, true, 2000, null));

		// comienza el lanzamiento de oleadas
		siguienteOleada = 0;
		lanzarSiguienteOleada();
	}

	/**
	 * Ejecutado cuando el jugador gana la partida. Anima la nave para que acienda
	 * hasta desaparecer y ordena terminar
	 * 
	 * @see Animacion
	 * @see TextoCentrado
	 * @see Nave
	 */
	private void ganarPartida() {
		nave.setEscudoActivo(true);
		nave.setAnimacionY(new Animacion(2000, (float) nave.getY(), -100, null));
		motor.agregarCapaGui(new TextoCentrado(Strings.MENSAJE_GANAR, true, 3000, null));
		terminar();
	}

	/**
	 * Ejecutado al morir la nave y no haber vidas disponibles. Muestra el mensaje y
	 * termina la partida
	 */
	private void perderPartida() {
		System.out.println("PERDER PARTIDA");
		motor.agregarCapaGui(new TextoCentrado(Strings.MENSAJE_PERDER, true, 2000, null));
		terminar();
	}

	/**
	 * Lanza la siguiente oleada de naves enemigas
	 */
	private void lanzarSiguienteOleada() {

		// si la partida esta terminada no hace nada
		if (terminada)
			return;

		/**
		 * Si no quedan oleadas en nivel pasa al siguietne nivel
		 */
		if (oleadasNivel != null && siguienteOleada == oleadasNivel.length) {
			nivel++;
			new Temporizador(5000, () -> nuevoNivel());
			return;
		}

		// parametros de la siguiente oleada
		int[] oleada = oleadasNivel[siguienteOleada];

		// tiempo, MOV, tipo enemigo, cantidad, frecuencia
		int tiempo = oleada[0];
		int x = oleada[1];
		int mov = oleada[2];
		int tipoEnem = oleada[3];
		int cantidad = oleada[4];
		int frecuencia = oleada[5];

		// crear la oleada tras el tiempo de espera especificado
		new Temporizador(tiempo, () -> crearOleada(x, mov, tipoEnem, cantidad, frecuencia));

		// incrementa el indicide que correspondera a la siguiente oleada
		siguienteOleada++;
	}

	/**
	 * Crea una oleada iniciando un nuevo GeneradorEnemigos en la posicion x
	 * especificada y lanza la siguiente oleada
	 * 
	 * @param x          posicion x del generador
	 * @param mov        Tipo de movimiento de las naves generadas
	 * @param tipoEnem   Tipo de enemigo
	 * @param cantidad   Cantidad a generar
	 * @param frecuencia intervalo entre cada nave generada
	 * @see GeneradorEnemigos
	 */
	private void crearOleada(int x, int mov, int tipoEnem, int cantidad, int frecuencia) {
		new GeneradorEnemigos(this, x, mov, tipoEnem, cantidad, frecuencia, nivel).start();
		lanzarSiguienteOleada();
	}

	/**
	 * Agrega la nave del jugador animando su entrada apareciendo por la parte
	 * inferior de la pantalla. Le activa el escudo de manera temporal
	 */
	private void addNave() {
		// resetear estado de las teclas
		arriba = false;
		abajo = false;
		izquierda = false;
		derecha = false;
		// desactivar teclas
		controlActivo = false;

		// crear nave
		nave = new Nave(this);
		motor.agregarCapaNave(nave);

		// anivar nave y reactivar las teclas tras la animacion de entrada
		nave.setAnimacionY(new Animacion(2000, 1124, 700, () -> {
			nave.setAnimacionY(null);
			controlActivo = true;
		}));
		new Temporizador(4000, () -> nave.setEscudoActivo(false));
	}

	/**
	 * Agrega a la capa GUI el visor de puntos y el visor de salud y nivel
	 */
	private void addGUI() {
		motor.agregarCapaGui(new VisorSaludNivel(this));
		motor.agregarCapaGui(new VisorPuntos(this));

	}

	/**
	 * Registra la tecla cursor correspondiente como pulsada
	 */
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

			// avisar del nuevo estado de las teclas a la nave
			nave.teclas(arriba, abajo, izquierda, derecha);
		}
	}

	/**
	 * Registra la tecla cursor correspondiente como no pulsada y avisa a la nave
	 * del nuevo estado de las teclas. Tambien actua activando/desactivando la pausa
	 * cuando se suelta la tecla p
	 */
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

	/**
	 * Devuelve el nivel actual
	 * 
	 * @return El nivel actual
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * Devuelve una referencia a la nave actual del jugador
	 * 
	 * @return La nave actual
	 */
	public Nave getNave() {
		return nave;
	}

	/**
	 * Resta una vida y agrega una nueva nave
	 */
	public void destruirNave() {
		vidas--;
		if (vidas > 0) {
			addNave();
		} else {
			perderPartida();
		}

	}

	/**
	 * Agrega puntos al marcador 
	 * 
	 * @param puntos Puntos a agregar
	 */
	public void agregarPuntos(int puntos) {
		this.puntos += puntos;
	}

	/**
	 * Agrega una vida extra
	 */
	public void agregarVida() {
		vidas++;

	}

	/**
	 * Devuelve los puntos actuales
	 * 
	 * @return Los puntos actuales
	 */
	public int getPuntos() {
		return puntos;
	}

	/**
	 * Devuelve las vidas actuales
	 * 
	 * @return Las vidas actuales
	 */
	public int getVidas() {
		return vidas;
	}

}
