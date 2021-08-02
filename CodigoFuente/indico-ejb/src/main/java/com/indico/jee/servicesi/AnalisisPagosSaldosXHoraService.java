package com.indico.jee.servicesi;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import com.indico.jee.modelo.AnalisisPagosSaldosXHora;
import com.indico.jee.util.ValorGraficable;

@Local
public interface AnalisisPagosSaldosXHoraService {
	
	Date getLastDate(); 
	
	List<AnalisisPagosSaldosXHora> finAll();
	
	BigInteger countAll();

	List<ValorGraficable> getAllDiario(Date desde, Date hasta, int posinicial, int cantregistros);
	
	List<AnalisisPagosSaldosXHora> getDetalleExportarPeriodo 		(Date desde     	, Date hasta  	, int tipo); 

	List<ValorGraficable> getConjuntoDiario    				(Date desde     	, Date hasta	, int posinicial, int cantregistros); 

	List<ValorGraficable> getAllMensual   					(Date desde			, Date hasta	, int posinicial, int cantregistros); 

	List<ValorGraficable> getConjuntoMensual   				(Date desde			, Date hasta	, int posinicial, int cantregistros); 
	
	List<ValorGraficable> getAllTrimestral					(Date desde			, Date hasta	, int posinicial, int cantregistros); 
	
	List<ValorGraficable> getConjuntoTrimestral				(Date desde			, Date hasta	, int posinicial, int cantregistros); 
	
	List<ValorGraficable> getAllSemestral 					(Date desde			, Date hasta	, int posinicial, int cantregistros); 

	List<ValorGraficable> getConjuntoSemestral 				(Date desde			, Date hasta	, int posinicial, int cantregistros); 

	List<ValorGraficable> getAllAnual     					(Date desde			, Date hasta	,int posinicial, int cantregistros); 

	List<ValorGraficable> getConjuntoAnual     				(Date desde			, Date hasta  	, int posinicial, int cantregistros); 
	
	
	
	List<ValorGraficable> getConjuntoDiarioCantidadSG    				(); 
	
	List<ValorGraficable> getConjuntoMensualCantidadSG   				();
	
	List<ValorGraficable> getConjuntoTrimestralCantidadSG				();
	
	List<ValorGraficable> getConjuntoSemestralCantidadSG 				();
	
	List<ValorGraficable> getConjuntoAnualCantidadSG     				(); 

	List<ValorGraficable> getConjuntoDiarioValorSG    				(); 
	
	List<ValorGraficable> getConjuntoMensualValorSG   				();
	
	List<ValorGraficable> getConjuntoTrimestralValorSG				();
	
	List<ValorGraficable> getConjuntoSemestralValorSG 				();
	
	List<ValorGraficable> getConjuntoAnualValorSG     				(); 
	
	
	/**
	 * Metodo que consulta la informacion agrupada por fecha y 
	 * retorna 2 registros con las 2 ultimas fechas.
	 * 
	 * @return Las 2 ultimas fechas registradas en la entidad AnalisisPagosSaldosXHora.
	 * @author fadosolutions
	 * @since 2018-12-04
	 * @version 1.0
	 */
	List<ValorGraficable> lastMovementsDaily				();

	/**
	 * ROTACION
	 * */
	
	List<ValorGraficable> getConjuntoDiarioRotacion    		(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);

	List<ValorGraficable> getConjuntoMensualRotacion    	(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoTrimestralRotacion    	(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoSemestralRotacion    	(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoAnualRotacion    		(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoDiarioRotacionSG    	();

	List<ValorGraficable> getConjuntoMensualRotacionSG    	();
	
	List<ValorGraficable> getConjuntoTrimestralRotacionSG   ();
	
	List<ValorGraficable> getConjuntoSemestralRotacionSG    ();
	
	List<ValorGraficable> getConjuntoAnualRotacionSG    	();

	
	/**
	 * PIB
	 * */
	
	List<ValorGraficable> getConjuntoDiarioPIB    			(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoMensualPIB    			(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoTrimestralPIB    		(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoSemestralPIB    		(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);
	
	List<ValorGraficable> getConjuntoAnualPIB    			(Date desde     	, Date hasta  	, int posinicial, int cantregistros , int tipo);

	List<ValorGraficable> getConjuntoDiarioPIBSG   			();
	
	List<ValorGraficable> getConjuntoMensualPIBSG  			();
	
	List<ValorGraficable> getConjuntoTrimestralPIBSG   		();
	
	List<ValorGraficable> getConjuntoSemestralPIBSG    		();
	
	List<ValorGraficable> getConjuntoAnualPIBSG    			();

	/**
	 * Exportar Datos
	 * */
	List<AnalisisPagosSaldosXHora> getDetalleExportarDesdeHasta 		(Date desde     	, Date hasta  	, int tipo); 

	List<ValorGraficable> getDetalleExportarRotaDesdeHastaVG(Date desde, Date hasta);
	
	List<ValorGraficable> getDetalleExportarPibDesdeHastaVG(Date desde, Date hasta);
}	
