/**
 * 
 */
package reto8juego.motor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

import reto8juego.actores.Enemigo;
import reto8juego.actores.Fondo;
import reto8juego.actores.Meteorito;
import reto8juego.actores.Nave;
import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.gui.Lienzo;

/**
 * <p>
 * Motor de comportamiento del juego. Trabaja en conjuncion a la escena actual
 * establecida. Lanzando el limpiado, la animacion y la deteccion de colisiones
 * cuando toque segun el bucle de fotograma. Organiza los elementos en capas
 * cada una dedicada a un proposito, capa de fondo, de disparos, de naves, de
 * premios y de GUI.
 * </p>
 * <p>
 * Se trata de un hilo que lleva un seguimiento del tiempo en funcion del cual controla
 * un bucle de fotograma en el que se encarga de ir actualizando el estado de los diferentes elementos
 * que hay en las capas.
 * </p>
 * 
 * <p>
 * Cada ciclo del bucle de fotograma se compone de las siugientes fases:
 * <ul>
 * <li>Comprobar si el motor esta en funcionamiento. Si esta en pausa se queda esperando</li>
 * <li>Calcular el delta(Tiempo transcurrido tras el anterior fotograma)</li>
 * <li>Limpiar las capas eliminando los dibujos establecidods como no vivos</li>
 * <li>Animar los dibujos</li>
 * <li>Detectar las colisiones favorables(premios, disparos amigos)</li>
 * <li>Detectar las colisiones desfaborables(colisiones de la nave con meteoritos, enemigos y disparos enemigos)</li>
 * <li>Ordenar el dibujado de elementos en el lienzo</li>
 * <li>Esperar para la limitacion de fotogramas en caso de ser necesario</li>
 * </ul>
 * </p>
 * 
 * 
 * @author Jose Javier Bailon Ortiz
 */
 

public class Motor extends Thread {

	/**
	 * Instancia singleton del motor
	 */
	private static Motor instancia;

	/**
	 * Limite de frames por segundo del motor
	 */
	private static final double fps = Config.FPS;

	/**
	 * Estimacion de la duracion que debe tener un fotograma. Usado en la limitacion
	 * de FPS
	 */
	private static final double duracionFotograma = 1000 / fps;

	/**
	 * Tiempo del reloj del motor.
	 */
	private AtomicLong tiempo = new AtomicLong(0);

	/**
	 * Ultimo tiempo real que se recogio del sistema. Usado para calcular tiempo
	 * delta.
	 */
	private long ultimoTiempo = 0;

	/**
	 * Tiempo delta del fotograma (Tiempo transcurrido entre el anterior fotograma y
	 * el actual)
	 */
	private long delta = 0;

	/**
	 * True si el motor esta funcionando
	 */
	private boolean play = false;

	/**
	 * Proporcion entre un segundo y delta. Especifica la proporcion de 1 segundo
	 * que supone delta. Usado por las distintas animaciones para conservar la
	 * consistencia temporal independientemente de los FPS reales que se esten
	 * produciendo
	 */
	private float deltaPorSegundo = 0;

	/**
	 * Fotograma actual
	 */
	private int frame = 0;

	/**
	 * Referencia al lienzo de dibujo
	 */
	private Lienzo lienzo;

	/**
	 * Ancho del lienzo
	 */
	private int ancho = 0;

	/**
	 * Alto del lienzo
	 */
	private int alto = 0;

	// CAPAS
	/**
	 * Capa de elementos de fondo
	 */
	private LinkedBlockingDeque<Dibujo> capaFondo = new LinkedBlockingDeque<Dibujo>();

	/**
	 * Capa de disparos amigos
	 */
	private LinkedBlockingDeque<Disparo> capaDisparosAmigos = new LinkedBlockingDeque<Disparo>();

	/**
	 * Capa de disparos enemigos
	 */
	private LinkedBlockingDeque<Disparo> capaDisparosEnemigos = new LinkedBlockingDeque<Disparo>();

	/**
	 * Capa de elementos de la nave
	 */
	private LinkedBlockingDeque<Dibujo> capaNave = new LinkedBlockingDeque<Dibujo>();

	/**
	 * Capa de meteoritos
	 */
	private LinkedBlockingDeque<Dibujo> capaMeteoritos = new LinkedBlockingDeque<Dibujo>();

	/**
	 * Capa de naves enemigas
	 */
	private LinkedBlockingDeque<Dibujo> capaEnemigos = new LinkedBlockingDeque<Dibujo>();

	/**
	 * Capa de premios
	 */
	private LinkedBlockingDeque<Dibujo> capaPremios = new LinkedBlockingDeque<Dibujo>();

	/**
	 * Capa de FX, explosiones
	 */
	private LinkedBlockingDeque<Dibujo> capaFx = new LinkedBlockingDeque<Dibujo>();

	/**
	 * Capa de elementos GUI informativos
	 */
	private LinkedBlockingDeque<Dibujo> capaGui = new LinkedBlockingDeque<Dibujo>();

	/**
	 * Listado total de capas
	 */
	private LinkedBlockingDeque[] capas = { capaFondo, capaDisparosAmigos, capaDisparosEnemigos, capaNave,
			capaMeteoritos, capaEnemigos, capaPremios, capaFx, capaGui };

	/**
	 * Listado de capas a limpiar cuando se ordene al motor vaciar las capas
	 */
	private LinkedBlockingDeque[] capasLimpieza = { capaDisparosAmigos, capaDisparosEnemigos, capaNave, capaMeteoritos,
			capaEnemigos, capaPremios, capaFx, capaGui };

	/**
	 * Referencia a la escena actual
	 */
	private Escena escenaActual;

	private Motor() {
		this.start();
	}

	public static Motor getInstancia() {
		if (instancia == null)
			instancia = new Motor();
		return instancia;
	}

	@Override
	public void run() {
		while (true) {
			// check pausa
			synchronized (this) {
				if (!play)
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}

			// calcular delta
			long ahora = System.currentTimeMillis();
			delta = ahora - ultimoTiempo;
			tiempo.addAndGet(delta);
			deltaPorSegundo = delta / 1000f;
			ultimoTiempo = ahora;
			// pasar frame
			frame++;
			// limpieza
			limpiar();
			// animar
			animar();
			// detectar colisiones favorables
			colisionesFavorables();
			// detectar colisiones desfavorables
			colisionesDesfavorables();

			// pintar
			if (lienzo != null)
				lienzo.repaint();

			// limitar frames
			long tActual = System.currentTimeMillis();
			long dormir = (long) (tActual - ultimoTiempo - duracionFotograma);
			if (dormir < 0)
				try {
					sleep(-dormir);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			ultimoTiempo = ahora;
		}
	}

	public synchronized void play() {
		ultimoTiempo = System.currentTimeMillis();
		play = true;
		notify();
	}

	public synchronized void pause() {
		play = false;
	}

	/**
	 * Cambia el estado entre play y pause
	 * 
	 * @return El estado definido
	 */
	public boolean togglePlay() {
		if (play)
			pause();
		else
			play();

		return play;
	}

	public void setLienzo(Lienzo lienzo) {
		this.lienzo = lienzo;
		this.ancho = lienzo.getWidth();
		this.alto = lienzo.getHeight();
		for (LinkedBlockingDeque capa : capas) {
			lienzo.addCapa(capa);
		}
		// poner fondo
		agregarCapaFondo(new Fondo());
	}

	public void setEscena(Escena escena) {
		this.escenaActual = escena;
	}

	public Escena getEscena() {
		return this.escenaActual;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	/**
	 * @return
	 */
	public long getTiempo() {
		return tiempo.get();
	}

	/**
	 * Ordena al motor a repintar la pantalla
	 */
	public void repintar() {
		lienzo.repaint();

	}

	/**
	 * 
	 */
	public void agregarCapaFondo(Dibujo dibujo) {
		capaFondo.add(dibujo);
	}

	public void agregarCapaNave(Dibujo dibujo) {
		capaNave.add(dibujo);
	}

	public void agregarCapaGui(Dibujo dibujo) {
		capaGui.add(dibujo);
	}

	public void agregarCapaDisparosAmigos(Disparo disparo) {
		capaDisparosAmigos.add(disparo);
	}

	/**
	 * @param m
	 */
	public void agregarCapaMeteoritos(Dibujo dibujo) {
		capaMeteoritos.add(dibujo);

	}

	/**
	 * @param explosionMeteorito
	 */
	public void agregarCapaPremios(Dibujo dibujo) {
		capaPremios.add(dibujo);

	}

	/**
	 * @param explosionMeteorito
	 */
	public void agregarCapaFx(Dibujo dibujo) {
		capaFx.add(dibujo);

	}

	/**
	 * @param enemigo
	 */
	public void agregarCapaEnemigos(Enemigo enemigo) {
		capaEnemigos.add(enemigo);

	}

	/**
	 * @param disparoEnemigo
	 */
	public void agregarCapaDisparosEnemigos(Disparo disparo) {
		capaDisparosEnemigos.add(disparo);
	}

	/**
	 * 
	 */
	public void vaciarCapas() {
		for (LinkedBlockingDeque<Dibujo> capa : capasLimpieza) {
			capa.clear();
		}
	}

	/**
	 * 
	 */
	private void limpiar() {
		ExecutorService threadpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (LinkedBlockingDeque<Dibujo> capa : capas) {
			threadpool.execute(() -> {
				for (Dibujo dibujo : capa) {
					if (!dibujo.isVivo())
						capa.remove(dibujo);
				}
			});
		}
		threadpool.close();

	}

	/**
	 * 
	 */
	private void animar() {
		ExecutorService threadpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (LinkedBlockingDeque<Dibujo> capa : capas) {
			for (Dibujo dibujo : capa) {
				threadpool.execute(() -> dibujo.nuevoFotograma(frame, delta, deltaPorSegundo));
			}

		}
		threadpool.close();
	}

	/**
	 * 
	 */
	private void colisionesFavorables() {
		ExecutorService threadpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		// recorrer objetivos para cada disparo:
		for (Disparo disp : capaDisparosAmigos) {
			// recorrer comprobar meteoritos
			for (Dibujo meteo : capaMeteoritos) {
				if (disp.isVivo())
					threadpool.execute(() -> {
						if (disp.isVivo() && ((Disparable) meteo).impactoDisparo(disp))
							disp.impactado();
					});
			}

			// recorrer comprobar enemigos
			for (Dibujo enemigo : capaEnemigos) {
				if (disp.isVivo())
					threadpool.execute(() -> {
						if (disp.isVivo() && ((Disparable) enemigo).impactoDisparo(disp))
							disp.impactado();
					});
			}
		}

		// recorrer colisiones con premios
		// recorrer meteoritos:
		if ((escenaActual instanceof Partida)) {
			Nave nave = ((Partida) escenaActual).getNave();
			for (Dibujo premio : capaPremios) {
				if (nave.isVivo())
					threadpool.execute(() -> ((Premio) premio).colisiona(nave));
			}
		}
		threadpool.close();
	}

	/**
	 * 
	 */
	private void colisionesDesfavorables() {
		if (!(escenaActual instanceof Partida))
			return;
		Nave nave = ((Partida) escenaActual).getNave();
		if (!nave.isVivo())
			return;

		ExecutorService threadpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		// colisiones meteoritos:
		for (Dibujo meteo : capaMeteoritos) {
			if (nave.isVivo())
				threadpool.execute(() -> ((Meteorito) meteo).colisiona(nave));
		}

		// colisiones enemigos:
		for (Dibujo enemigo : capaEnemigos) {
			if (nave.isVivo())
				threadpool.execute(() -> ((Enemigo) enemigo).colisiona(nave));
		}

		// disparos enemigos:
		for (Disparo disp : capaDisparosEnemigos) {
			// recorrer comprobar cada disparo enemigo con la nave
			if (nave.isVivo())
				threadpool.execute(() -> {
					if (disp.isVivo() && ((Disparable) nave).impactoDisparo(disp))
						disp.impactado();
				});
		}

		threadpool.close();
	}

}
