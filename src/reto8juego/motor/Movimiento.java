/**
 * 
 */
package reto8juego.motor;

/**
 * Interfaz funcional para movimientos.  Usada para especificar
 * con lambdas los movimientos de enemigos
 * @author Jose Javier Bailon Ortiz
 */
public interface Movimiento {
	
	/**
	 * Especifica una accion de movimiento a realizar segun deltaSegundo
	 * 
	 * @param deltaSegundo Proporcion de 0 a 1 del avance de un segundo
	 */
	public void apply(float deltaSegundo);
}
