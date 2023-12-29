/**
 * 
 */
package reto8juego.motor;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import reto8juego.actores.Dibujo;
import reto8juego.gui.Lienzo;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Motor extends Thread{
	public static final double fps=60d;
	public static final double duracionFotograma=1000/fps;
	private static Motor instancia;
	private boolean play=false;
	private long delta=0;
	private long ultimoTiempo=0;
	private int frame=0;
	private Lienzo lienzo;
	
	//capas
	private static LinkedBlockingDeque<Dibujo> capaFondo=new LinkedBlockingDeque<Dibujo>();
	private static LinkedBlockingDeque<Dibujo> capaNave=new LinkedBlockingDeque<Dibujo>();
	
	
	
	
	
	private Motor() {
		super();
		this.start();
	}
	
	public static Motor getInstancia() {
		if (instancia==null)
			instancia=new Motor();
		return instancia;
	}
	
	public void setLienzo(Lienzo lienzo) {
		this.lienzo=lienzo;
	}
	
	public synchronized void play() {
		ultimoTiempo=System.currentTimeMillis();
		play=true;
		notify();
	}
	
	public synchronized void pause() {
		play=false;
	}
	
	@Override
	public void run() {
		while (true) {
		//check pausa
			synchronized (this) {
			if (!play)
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		//calcular delta
			long ahora = System.currentTimeMillis();
			delta=ahora-ultimoTiempo;
			ultimoTiempo=ahora;
			
		//pasar frame
			frame++;
		//limpieza
		//animar
		animar();
		//detectar colisiones favorables
		//detectar colisiones desfavorables
		//Actualizar parametro GUI
		
		//pintar
		if (lienzo!=null)
			lienzo.repaint();
		
		
		//limitar frames
		long tActual = System.currentTimeMillis();
		long dormir = (long) (tActual-ultimoTiempo-duracionFotograma);
		if (dormir<0)
			try {
				sleep(-dormir);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		ultimoTiempo=ahora;
		}
		
	}

	/**
	 * 
	 */
	private void animar() {
		ExecutorService threadpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (Dibujo dibujo : capaFondo) {
			threadpool.execute(() -> dibujo.nuevoFotograma(frame,delta));
		}
		threadpool.shutdown();
		while (!threadpool.isTerminated());
	}
 

	/**
	 * @return
	 */
	public Iterator<Dibujo> getCapaFondo() {
		return capaFondo.iterator();
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
	
	
}

