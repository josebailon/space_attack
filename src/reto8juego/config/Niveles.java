/**
 * 
 */
package reto8juego.config;

/**
 * Configuracion de las oleadas de enemigos de los niveles. 
 * 
 * Contiene una matriz por nivel en el que cada fila son los datos de creacion 
 * de una oleada tal que:
 * 
 * <ul>
 * <li>0-Espera inicial en ms</li>
 * <li>1-Posicion x de creacion</li>
 * <li>2-Tipo de movimiento</li>
 * <li>3-Cantidad de enemigos a generar</li>
 * <li>4-Intervalo de creacion entre enemigos en ms</li>
 * </ul>
 * 
 * El nue
 * @author Jose Javier Bailon Ortiz
 */
public class Niveles {
	
	/**
	 * Movimiento recto de las naves enemigas
	 */
	public static final int MOV_RECTO=0;
	
	/**
	 * Movimiento ondulado de las naves enemigas
	 */
	public static final int MOV_ONDULADO=1;
	
	
	/**
	 * Tipo de enemigo 0
	 */
	public static final int ENEMIGO_0=0;
	
	/**
	 * Tipo de enemigo 1
	 */
	public static final int ENEMIGO_1=1;
	
	/**
	 * Tipo de enemigo 2
	 */
	public static final int ENEMIGO_2=2;
	
	
	
	
	/**
	 * Listado de los niveles.
	 * 
	 * Cada nivel esta compuesto por un array de oleadas.
	 * Cada oleada es un array de valores tal que:
	 * <ul>
	 * <li>0-Espera inicial en ms</li>
	 * <li>1-Posicion x de creacion</li>
	 * <li>2-Tipo de movimiento</li>
	 * <li>3-Cantidad de enemigos a generar</li>
	 * <li>4-Intervalo de creacion entre enemigos en ms</li>
	 * </ul>
	 * 
	 */
	private static int[][][]niveles={
			//nivel 1
			{
				//tiempo, x MOV, tipo enemigo, cantidad, frecuencia
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,200,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{8000,600,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{8000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,4,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,4,1000},
				{4000,200,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{8000,600,MOV_ONDULADO,ENEMIGO_2,3,1000}
			},
			
			//nivel 2
			{
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,600,MOV_RECTO,ENEMIGO_1,3,1000},
				{4000,200,MOV_RECTO,ENEMIGO_1,4,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,300,MOV_ONDULADO,ENEMIGO_2,4,1000},
				{5000,500,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{5000,200,MOV_ONDULADO,ENEMIGO_1,4,1000},
				{5000,600,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000}
			},
			//nivel 3
			{
				{4000,300,MOV_ONDULADO,ENEMIGO_2,4,1000},
				{5000,500,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{4000,600,MOV_RECTO,ENEMIGO_1,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,200,MOV_RECTO,ENEMIGO_1,4,1000},
				{5000,200,MOV_ONDULADO,ENEMIGO_1,4,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{5000,600,MOV_ONDULADO,ENEMIGO_2,3,1000},
			},
			//nivel 4
			{
				{4000,600,MOV_RECTO,ENEMIGO_1,5,1500},
				{8000,200,MOV_ONDULADO,ENEMIGO_1,4,2000},
				{1000,600,MOV_ONDULADO,ENEMIGO_0,1,1000},
				{1000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,2,1000},
				{1000,200,MOV_ONDULADO,ENEMIGO_0,1,1000},
				{5000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,4,2000},
				{1000,600,MOV_ONDULADO,ENEMIGO_0,1,1000},
				{1000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,2,1000},
				{1000,200,MOV_ONDULADO,ENEMIGO_0,1,1000},
			},
			
			
			//nivel 5
			{
				{1000,200,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,300,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,400,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,500,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,600,MOV_RECTO,ENEMIGO_0,1,1000},
				{4000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{4000,300,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{5000,500,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{5000,200,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{5000,600,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000}
			},
			//nivel 6
			{
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,200,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{8000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,4,1000},
				{8000,600,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{8000,600,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,4,1000},
				{4000,200,MOV_ONDULADO,ENEMIGO_2,3,1000},
			},
			
			//nivel 7
			{
				{4000,600,MOV_RECTO,ENEMIGO_1,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,200,MOV_RECTO,ENEMIGO_1,4,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,300,MOV_ONDULADO,ENEMIGO_2,4,1000},
				{5000,200,MOV_ONDULADO,ENEMIGO_1,4,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{5000,500,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{5000,600,MOV_ONDULADO,ENEMIGO_2,3,1000},
			},
			//nivel 8
			{
				{4000,300,MOV_ONDULADO,ENEMIGO_2,4,1000},
				{5000,500,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{4000,600,MOV_RECTO,ENEMIGO_1,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,200,MOV_RECTO,ENEMIGO_1,4,1000},
				{5000,200,MOV_ONDULADO,ENEMIGO_1,4,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{5000,600,MOV_ONDULADO,ENEMIGO_2,3,1000},
			},
			//nivel 9
			{
				{4000,600,MOV_RECTO,ENEMIGO_1,5,1500},
				{1000,600,MOV_ONDULADO,ENEMIGO_0,1,1000},
				{8000,200,MOV_ONDULADO,ENEMIGO_1,4,2000},
				{1000,200,MOV_ONDULADO,ENEMIGO_0,1,1000},
				{1000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,2,1000},
				{5000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,4,2000},
				{1000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,2,1000},
				{1000,600,MOV_ONDULADO,ENEMIGO_0,1,1000},
				{1000,200,MOV_ONDULADO,ENEMIGO_0,1,1000},
			},
			
			
			//nivel 10
			{
				{1000,300,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,200,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,500,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,400,MOV_RECTO,ENEMIGO_0,1,1000},
				{1000,600,MOV_RECTO,ENEMIGO_0,1,1000},
				{4000,Config.CENTRO_ANCHO,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{4000,Config.CENTRO_ANCHO,MOV_RECTO,ENEMIGO_0,3,1000},
				{5000,500,MOV_ONDULADO,ENEMIGO_1,3,1000},
				{4000,300,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{5000,600,MOV_ONDULADO,ENEMIGO_2,3,1000},
				{5000,200,MOV_ONDULADO,ENEMIGO_1,3,1000},
			},
	};

	/**
	 * Devuelve una matriz de parametros de las oleadas de un nivel.
	 * 
	 * @param n Indice del nivel a recuperar
	 * 
	 * @return La matriz con los datos de las oleadas o null si no existe
	 */
	public static int[][] getNivel(int n) {
		if (n>=niveles.length)
			return null;
		return niveles[n];
	}
}
