package reto8juego.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import javax.swing.JPanel;

import reto8juego.motor.Dibujo;

/**
 * JPanel usado para dibujar los graficos del juego.
 * El motor le pasa las capas de Dibujos que debe mostrar en forma de listas.
 * 
 * En su metodo paintComponent recorre esas capas y dibuja los elementos que hay en ellas.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Lienzo extends JPanel implements ActionListener {
	

	private static final long serialVersionUID = 1L;

	/**
	 * Lista de capas a dibujar
	 */
	ArrayList<LinkedBlockingDeque<Dibujo>> capas = new ArrayList();


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		for (LinkedBlockingDeque<Dibujo> capa : capas) {
			Iterator<Dibujo> it =capa.iterator();
			while (it.hasNext()) {
				it.next().dibujar(g2);
			}
		}

	}

 	/**
 	 * Agrega  una capa al listado de capas a dibujar
 	 * 
	 * @param capa a agregar
	 */
	public void addCapa(LinkedBlockingDeque<Dibujo> capa) {
		this.capas.add(capa);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	
}
