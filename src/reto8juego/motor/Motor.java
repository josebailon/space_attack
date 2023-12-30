/**
 * 
 */
package reto8juego.motor;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import reto8juego.actores.Dibujo;
import reto8juego.actores.TextoCentrado;
import reto8juego.config.Config;
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
	private float deltaSegundo=0;
	private long ultimoTiempo = 0;
	private int frame = 0;
	private Lienzo lienzo;

	// capas
	private LinkedBlockingDeque<Dibujo> capaFondo = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Dibujo> capaNave = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque<Dibujo> capaGui = new LinkedBlockingDeque<Dibujo>();
	private LinkedBlockingDeque[] capas = { capaFondo, capaNave, capaGui };
	private LinkedBlockingDeque[] capasLimpieza = { capaNave, capaGui };

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
	}

	public synchronized void play() {
		ultimoTiempo = System.currentTimeMillis();
		play = true;
		notify();
	}

	public synchronized void pause() {
		play = false;
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
			deltaSegundo=delta/1000f;
			ultimoTiempo = ahora;
			// pasar frame
			frame++;
			// limpieza
			// animar
			animar();
			// detectar colisiones favorables
			// detectar colisiones desfavorables
			// Actualizar parametro GUI

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
	private void animar() {
		ExecutorService threadpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (LinkedBlockingDeque<Dibujo> capa : capas) {
			for (Dibujo dibujo : capa) {
				threadpool.execute(() -> dibujo.nuevoFotograma(frame, delta,deltaSegundo));
			}

		}
		threadpool.shutdown();
		while (!threadpool.isTerminated())
			;
	}

	/**
	 * @return
	 */
	public Iterator<Dibujo> getCapaFondo() {
		return capaFondo.iterator();
	}

	/**
	 * @return
	 */
	public Iterator<Dibujo> getCapaNave() {
		return capaNave.iterator();
	}

	/**
	 * @return
	 */
	public Iterator<Dibujo> getCapaGui() {
		return capaGui.iterator();
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

	/**
	 * 
	 */
	public void vaciar() {
		for (LinkedBlockingDeque<Dibujo> capa : capasLimpieza) {
			capa.clear();
		}
	}

}
