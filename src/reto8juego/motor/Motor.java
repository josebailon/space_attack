/**
 * 
 */
package reto8juego.motor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

import reto8juego.actores.DisparoEnemigo;
import reto8juego.actores.Enemigo;
import reto8juego.actores.Fondo;
import reto8juego.actores.Meteorito;
import reto8juego.actores.Nave;
import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.gui.Lienzo;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Motor extends Thread {
	public static final double fps = Config.FPS;
	public static final double duracionFotograma = 1000 / fps;
	private static Motor instancia;
	private boolean play = false;
	private long delta = 0;
	private float deltaSegundo = 0;
	private long ultimoTiempo = 0;
	public AtomicLong tiempo = new AtomicLong(0);
	private int frame = 0;
	private Lienzo lienzo;
	private int ancho = 0;
	private int alto = 0;

	// capas
	private LinkedBlockingDeque<Dibujo> capaFondo = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Disparo> capaDisparosAmigos = new LinkedBlockingDeque<Disparo>();
	private LinkedBlockingDeque<Disparo> capaDisparosEnemigos = new LinkedBlockingDeque<Disparo>();
	private LinkedBlockingDeque<Dibujo> capaNave = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Dibujo> capaMeteoritos = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Dibujo> capaEnemigos = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Dibujo> capaPremios= new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Dibujo> capaFx = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Dibujo> capaGui = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque[] capas = { capaFondo, capaDisparosAmigos, capaDisparosEnemigos, capaNave, capaMeteoritos, capaEnemigos, capaPremios, capaFx, capaGui };
	private LinkedBlockingDeque[] capasLimpieza = { capaDisparosAmigos, capaDisparosEnemigos, capaNave, capaMeteoritos, capaEnemigos, capaPremios, capaFx, capaGui };

	private Escena escenaActual;

	private Motor() {
		super();
		this.start();
	}

	public static Motor getInstancia() {
		if (instancia == null)
			instancia = new Motor();
		return instancia;
	}

	public void setLienzo(Lienzo lienzo) {
		this.lienzo = lienzo;
		this.ancho = lienzo.getWidth();
		this.alto = lienzo.getHeight();
		for (LinkedBlockingDeque capa : capas) {
			lienzo.addCapa(capa);
		}
		//poner fondo
		agregarCapaFondo(new Fondo());
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
			deltaSegundo = delta / 1000f;
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

	/**
	 * 
	 */
	private void colisionesDesfavorables() {
		if (!(escenaActual instanceof Partida))
			return;
		Nave nave =  ((Partida) escenaActual).getNave();
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
	 
		//disparos enemigos:
			for (Disparo disp : capaDisparosEnemigos) {
				// recorrer  comprobar cada disparo enemigo con la nave
					if (nave.isVivo())
						threadpool.execute(() -> {
							if (disp.isVivo()&&((Disparable) nave).impactoDisparo(disp))
								disp.impactado();
						});
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
			// recorrer  comprobar meteoritos
			for (Dibujo meteo : capaMeteoritos) {
				if (disp.isVivo())
					threadpool.execute(() -> {
						if (disp.isVivo()&&((Disparable) meteo).impactoDisparo(disp))
							disp.impactado();
					});
			}
			
			// recorrer  comprobar enemigos 
			for (Dibujo enemigo: capaEnemigos) {
				if (disp.isVivo())
					threadpool.execute(() -> {
						if (disp.isVivo()&&((Disparable) enemigo).impactoDisparo(disp))
							disp.impactado();
					});
			}
		}
		
		//recorrer colisiones con premios
		// recorrer meteoritos:
		if ((escenaActual instanceof Partida)) {
			Nave nave =  ((Partida) escenaActual).getNave();
			for (Dibujo premio: capaPremios) {
				if (nave.isVivo())
				threadpool.execute(() -> ((Premio) premio).colisiona(nave));
			}
		}
		threadpool.close();
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
				threadpool.execute(() -> dibujo.nuevoFotograma(frame, delta, deltaSegundo));
			}

		}
		threadpool.close();
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
	public void repintar() {
		lienzo.repaint();

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

	
	}

 
