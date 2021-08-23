package com.indico.jee.servicesi;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import com.indico.jee.modelo.TransfElectronicaAltoValor;
import com.indico.jee.util.CampoSelect;
import com.indico.jee.util.ValorGraficable;

@Local
public interface TransfElectronicaAltoValorService {

	Date getLastDate(); 

	List<TransfElectronicaAltoValor> finAll();
	
	BigInteger countAll();
	
	List<CampoSelect>     getSubgrupos         ();
	
	List<CampoSelect> getGrupos();
	
	List<ValorGraficable> getAllDiario(Date desde, Date hasta, int posinicial, int cantregistros);
	
	List<TransfElectronicaAltoValor> getDetalleExportar 	(Date desde     	, Date hasta ); 

	List<TransfElectronicaAltoValor> getDetalleExportarSG 	(Date desde     	, Date hasta , String subgrupo); 

	List<ValorGraficable> getConjuntoDiario    				(Date desde     	, Date hasta	,String subgrupo, int posinicial, int cantregistros); 

	List<ValorGraficable> getAllMensual   					(Date desde			, Date hasta	, int posinicial, int cantregistros); 

	List<ValorGraficable> getConjuntoMensual   				(Date desde			, Date hasta	,String subgrupo, int posinicial, int cantregistros); 
	
	List<ValorGraficable> getAllTrimestral					(Date desde			, Date hasta	, int posinicial, int cantregistros); 
	
	List<ValorGraficable> getConjuntoTrimestral				(Date desde			, Date hasta	,String subgrupo, int posinicial, int cantregistros); 
	
	List<ValorGraficable> getAllSemestral 					(Date desde			, Date hasta	, int posinicial, int cantregistros); 

	List<ValorGraficable> getConjuntoSemestral 				(Date desde			, Date hasta	,String subgrupo, int posinicial, int cantregistros); 

	List<ValorGraficable> getAllAnual     					(Date desde			, Date hasta,int posinicial, int cantregistros); 

	List<ValorGraficable> getConjuntoAnual     				(Date desde			, Date hasta,String subgrupo, int posinicial, int cantregistros); 
	
	List<ValorGraficable> lastMovementsDaily				();
	
	List<ValorGraficable> getAllDiarioComplete();
	
	List<ValorGraficable> getAllConjuntoDiarioBySG(String subgrupo);
	
	List<ValorGraficable> getAllMensualComplete();
	
	List<ValorGraficable> getConjuntoMensualCompleteSG(String concepto);
	
	List<ValorGraficable> getConjuntoTrimestralSG(String concepto);
	
	List<ValorGraficable> getConjuntoSemestralSG(String concepto);
	
	List<ValorGraficable> getConjuntoAnualSG(String concepto);
	
	List<ValorGraficable> getAllTrimestralComplete();
	
	List<ValorGraficable> getAllSemestralComplete() ;
	
	List<ValorGraficable> getAllAnualComplete();
	
	/**
	 * By Group 
	 */

	List<ValorGraficable> getConjuntoAnualByGR(String concepto);
	
	List<ValorGraficable> getConjuntoSemestralByGR(String concepto);
	
	List<ValorGraficable> getConjuntoTrimestralByGR(String concepto);
	
	List<ValorGraficable> getConjuntoMensualCompleteByGR(String concepto);
	
	List<ValorGraficable> getAllConjuntoDiarioByGR(String concepto);
	
	
}
