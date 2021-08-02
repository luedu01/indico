package com.indico.jee.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
 
@Entity
@NamedQueries( {
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getAnios", query = "		" +
			  	" SELECT DISTINCT new com.indico.jee.modelo.Anios (o.idAnio) FROM AnalisisPagosSaldosXHora o ORDER BY o.idAnio DESC	" 
  ),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getLastDate", query = "		" +
			  	" SELECT max (o.pk.fechaValor) FROM AnalisisPagosSaldosXHora o 	" 
  ),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.findAll", query = "		" +
	  	" SELECT o FROM AnalisisPagosSaldosXHora o 						" +
	  	" ORDER BY o.pk.fechaValor DESC , o.pk.horaLiquidacion ASC		"
	),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.countAll", query = "	"+
	  	" select count(o) from AnalisisPagosSaldosXHora o				"
  ),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getDetalleExportar", query = "" +
		  	" SELECT o	FROM AnalisisPagosSaldosXHora o 					" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  			" +
		  	" ORDER BY o.pk.fechaValor, o.pk.horaLiquidacion	ASC			" 
		), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.lastMovementsDaily", query = "								" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 								," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)		" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" GROUP BY o.pk.fechaValor																		" +
		  	" ORDER BY o.pk.fechaValor	DESC																" 
	  		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getAllDiario", query = "									 	 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 								," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 o.numTotalMovimientos							," +
		  	"												 o.valorTotalMovimientos				" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor IN ( :desde ,  :hasta )  												" +
		  	" ORDER BY o.pk.fechaValor ASC ,	o.pk.horaLiquidacion	ASC										" 
		), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoDiario", query = "							     " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 								," +
		  	"												 SUM(o.pk.horaLiquidacion)						," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)		" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 															 	" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta 							 					" +
		  	" GROUP BY o.pk.fechaValor,	o.pk.horaLiquidacion												" +
		  	" ORDER BY o.pk.fechaValor ASC ,	o.pk.horaLiquidacion	ASC										" 
		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoDiarioValorSG", query = "						     " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 								," +
		  	"												 SUM(o.pk.horaLiquidacion)						," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)		" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 															 	" +
		  	" GROUP BY o.pk.fechaValor,	o.pk.horaLiquidacion												" +
		  	" ORDER BY o.pk.fechaValor ASC ,	o.pk.horaLiquidacion	ASC										" 
		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoDiarioSG", query = "							     " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 								," +
		  	"												 SUM(o.pk.horaLiquidacion)						," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)		" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 															 	" +
		  	" GROUP BY o.pk.fechaValor,	o.pk.horaLiquidacion												" +
		  	" ORDER BY o.pk.fechaValor ASC ,	o.pk.horaLiquidacion	ASC										" 
		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getAllMensual", query = "									 	 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idMes										," +
		  	"												 SUM(o.pk.horaLiquidacion)						," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)		," +
		  	"												 0												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" ORDER BY o.idAnio ASC ,o.idMes	 ASC																" 
		  ) ,
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoMensual", query = "									 	 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idMes										," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					," +
		  	"												 0												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" GROUP BY o.idAnio, o.idMes , o.pk.horaLiquidacion												" +
		  	" ORDER BY o.idAnio ASC , o.idMes ASC , o.pk.horaLiquidacion	ASC								"
		  ), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoMensualSG", query = "								 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idMes										," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					," +
		  	"												 0												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" GROUP BY o.idAnio, o.idMes , o.pk.horaLiquidacion												" +
		  	" ORDER BY o.idAnio ASC , o.idMes ASC , o.pk.horaLiquidacion	ASC								" 
		  ), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getAllTrimestral", query = "									" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idTrimestre									," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					," +
		  	"												 1												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" ORDER BY o.pk.fechaValor ASC,	o.pk.horaLiquidacion	ASC										" 
		), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoTrimestral", query = "							 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idTrimestre									," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					," +
		  	"												 1												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" GROUP BY o.idAnio,	o.idTrimestre	,o.pk.horaLiquidacion									" +
		  	" ORDER BY o.idAnio ASC,	o.idTrimestre	ASC ,o.pk.horaLiquidacion		ASC					" 
		), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoTrimestralSG", query = "							" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idTrimestre									," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					," +
		  	"												 1												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" GROUP BY o.idAnio,	o.idTrimestre	,o.pk.horaLiquidacion									" +
		  	" ORDER BY o.idAnio ASC,	o.idTrimestre	ASC ,o.pk.horaLiquidacion		ASC					" 
		), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getAllSemestral", query = "									" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idSemestre									," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 o.numTotalMovimientos							," +
		  	"												 (o.valorTotalMovimientos)						," +
		  	"												 2												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" ORDER BY o.pk.fechaValor ASC,	o.pk.horaLiquidacion	ASC										" 
		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoSemestral", query = "								 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idSemestre									," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					," +
		  	"												 2												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" GROUP BY o.idAnio,	o.idSemestre ,o.pk.horaLiquidacion										" +
		  	" ORDER BY o.idAnio ASC,	o.idSemestre ASC,o.pk.horaLiquidacion	ASC							" 
		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoSemestralSG", query = "							" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 o.idSemestre									," +
		  	"												 o.pk.horaLiquidacion							," +
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					," +
		  	"												 2												" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" GROUP BY o.idAnio,	o.idSemestre ,o.pk.horaLiquidacion										" +
		  	" ORDER BY o.idAnio ASC,	o.idSemestre ASC,o.pk.horaLiquidacion	ASC							" 
		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getAllAnual", query = "									 	 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
		  	"												o.pk.horaLiquidacion 							,"+
		  	"												SUM(o.numTotalMovimientos)						," +
		  	"												SUM(o.valorTotalMovimientos)					" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" GROUP BY o.idAnio,o.pk.horaLiquidacion														" +
		  	" ORDER BY o.idAnio ASC,o.pk.horaLiquidacion	ASC												" 
		),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoAnual", query = "								 	 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
		  	"												 o.pk.horaLiquidacion							,"+
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" WHERE o.pk.fechaValor  BETWEEN :desde AND :hasta  											" +
		  	" GROUP BY o.idAnio	 , o.pk.horaLiquidacion														" +
		  	" ORDER BY o.idAnio	ASC , o.pk.horaLiquidacion	ASC													"  
		), 
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoAnualSG", query = "								 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
		  	"												 o.pk.horaLiquidacion							,"+
		  	"												 SUM(o.numTotalMovimientos)						," +
		  	"												 SUM(o.valorTotalMovimientos)					" +
		  	"												) 												" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 		" +
		  	" GROUP BY o.idAnio	 , o.pk.horaLiquidacion														" +
		  	" ORDER BY o.idAnio	ASC , o.pk.horaLiquidacion	ASC												"  
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoDiarioRotacion", query = "					 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 						," +
			  	"												 SUM(o.numTotalMovimientos)				," +
			  	"												 SUM(CAST(o.valorSaldo AS float))		 " +
			  	"												) 										 " +  
			  	" FROM AnalisisPagosSaldosXHora o 														 " +
			  	" WHERE o.pk.fechaValor BETWEEN :desde AND :hasta AND o.pk.horaLiquidacion=:hora		 " +
			  	" GROUP BY o.pk.fechaValor 																 " +
			  	" ORDER BY o.pk.fechaValor ASC															 " 
			), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoDiarioRotacionSG", query = "																	 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 																				," +
		  	"												SUM(o.numTotalMovimientos)																		," +
			"												CASE WHEN SUM(																					 " +
			"												  		CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float) ELSE 0D END   	 				 " +
			"													)=0 THEN 0D   														 	    				 " +
			"												ELSE (																							 " +
			"														SUM(CAST(o.valorTotalMovimientos AS float)) /												 " +
			"														SUM(CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END)	 			" +	
			"												) END)	 																						" +
		  	" FROM AnalisisPagosSaldosXHora o 														 								 						" +
		  	" GROUP BY o.pk.fechaValor 																 								 						" +
		  	" ORDER BY o.pk.fechaValor ASC																							 						" 
		),
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getDetalleExportarRotaDesdeHastaVG", query = "                                                                 " +
          " SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor                                                                                ," +
          "                                                CASE WHEN SUM(                                                                                     " +
          "                                                          CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)    ELSE 0D END                         " +
          "                                                    )=0 THEN 0D                                                                                     " +
          "                                                ELSE (                                                                                             " +
          "                                                        SUM(COALESCE(o.valorTotalMovimientos,0D)) /                                                 " +
          "                                                        SUM(CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)    ELSE 0D END)                 " +   
          "                                                ) END)                                                                                             " +
            " FROM AnalisisPagosSaldosXHora o                                                                                                                   " +
            " WHERE o.pk.fechaValor BETWEEN :desde AND :hasta                                                                                                  " +
            " GROUP BY o.pk.fechaValor                                                                           " +
            " ORDER BY o.pk.fechaValor ASC                                                                                                 "
      ),
  @NamedQuery(name = "AnalisisPagosSaldosXHora.getDetalleExportarPibDesdeHastaVG", query = "             " +
            " SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor                         ," +       
            "                                                 SUM(COALESCE(o.valorTotalMovimientos,0D)) " +
            "                                                )                                          " +
            " FROM AnalisisPagosSaldosXHora o                                                          	" +
            " WHERE o.pk.fechaValor BETWEEN :desde AND :hasta                                         	" +
            " GROUP BY o.pk.fechaValor                                        	" +
            " ORDER BY o.pk.fechaValor ASC                                     	"
      ),
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoDiarioRotacionPIBSG", query = "			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaValor 						," +
		  	"												 SUM(o.numTotalMovimientos)				," +
		  	"												 SUM(COALESCE(o.valorTotalMovimientos,0D)) " +
		  	"												) 										 " +  
		  	" FROM AnalisisPagosSaldosXHora o 														 " +
		  	" GROUP BY o.pk.fechaValor 																 " +
		  	" ORDER BY o.pk.fechaValor ASC															 " 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoMensualRotacion", query = "				     " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idMes									," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(CAST(o.valorSaldo AS float))			," +
		  	"												 0											 " +
		  	"												) 											 " +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	 " +
		  	" WHERE o.pk.fechaValor BETWEEN :desde AND :hasta AND o.pk.horaLiquidacion=:hora			 " +
		  	" GROUP BY o.idAnio , o.idMes																 " +
		  	" ORDER BY o.idAnio ASC , o.idMes ASC														 " 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoMensualRotacionSG", query = "				 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idMes									," +
		  	"												SUM(o.numTotalMovimientos)					," +
			"												CASE WHEN SUM(														 	 		   				 " +
			"												  		CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END   	 				 " +
			"													)=0 THEN 0D   														 	    				 " +
			"												ELSE (																							 " +
			"														SUM(CAST(o.valorTotalMovimientos AS float)) /										 " +
			"														SUM(CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END)	 			" +	
			"												) END	 																						" +
			"												, 0)	 																	" + 
		  	" FROM AnalisisPagosSaldosXHora o 														 	 								" +
		  	" GROUP BY o.idAnio , o.idMes																 								" +
		  	" ORDER BY o.idAnio ASC , o.idMes ASC															 							" 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoMensualRotacionPIBSG", query = "							" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idMes									," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(COALESCE(o.valorTotalMovimientos,0D)) 							," +
		  	"												 0											 " +
		  	"												) 											 " +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	 " +
		  	" GROUP BY o.idAnio , o.idMes																 " +
		  	" ORDER BY o.idAnio ASC , o.idMes ASC															 " 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoTrimestralRotacion", query = "				 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idTrimestre								," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(CAST(o.valorSaldo AS float))							," +
		  	"												 1											" +
		  	"												) 											" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	" +
		  	" WHERE o.pk.fechaValor BETWEEN :desde AND :hasta  AND o.pk.horaLiquidacion=:hora			" +
		  	" GROUP BY o.idAnio,	o.idTrimestre														" +
		  	" ORDER BY o.idAnio ASC,	o.idTrimestre	ASC												" 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoTrimestralRotacionSG", query = "				 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idTrimestre								," +
		  	"												SUM(o.numTotalMovimientos)														   				," +
			"												CASE WHEN SUM(														 	 		   				 " +
			"												  		CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END   	 				 " +
			"													)=0 THEN 0D   														 	    				 " +
			"												ELSE (																							 " +
			"														SUM(CAST(o.valorTotalMovimientos AS float)) /										 	" +
			"														SUM(CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END)	 			" +	
			"												) END	 																						" +
			"												, 1)	 																						" +
		  	" FROM AnalisisPagosSaldosXHora o 														 	" +
		  	" GROUP BY o.idAnio,	o.idTrimestre														" +
		  	" ORDER BY o.idAnio ASC,	o.idTrimestre	ASC												" 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoTrimestralRotacionPIBSG", query = "				 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idTrimestre								," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(CAST(o.valorTotalMovimientos AS float)) 	," +
		  	"												 1											" +
		  	"												) 											" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	" +
		  	" GROUP BY o.idAnio,	o.idTrimestre														" +
		  	" ORDER BY o.idAnio ASC,	o.idTrimestre	ASC												" 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoSemestralRotacion", query = "							" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idSemestre								," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(CAST(o.valorSaldo AS float))							," +
		  	"												 2											" +
		  	"												) 											" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	" +
		  	" WHERE o.pk.fechaValor BETWEEN :desde AND :hasta AND o.pk.horaLiquidacion=:hora			" +
		  	" GROUP BY o.idAnio,	o.idSemestre 														" +
		  	" ORDER BY o.idAnio ASC,	o.idSemestre 	ASC												" 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoSemestralRotacionSG", query = "							" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idSemestre								," +
		  	"												SUM(o.numTotalMovimientos)														   				," +
			"												CASE WHEN SUM(														 	 		   				 " +
			"												  		CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END   	 				 " +
			"													)=0 THEN 0D   														 	    				 " +
			"												ELSE (																							 " +
			"														SUM(CAST(o.valorTotalMovimientos AS float)) /										 " +
			"														SUM(CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END)	 			" +	
			"												) END	 																						" +
			"												, 2)	 																	" +
		  	" FROM AnalisisPagosSaldosXHora o 														 	" +
		  	" GROUP BY o.idAnio,	o.idSemestre 														" +
		  	" ORDER BY o.idAnio ASC,	o.idSemestre 	ASC												" 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoSemestralRotacionPIBSG", query = "							" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 o.idSemestre								," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(CAST(o.valorTotalMovimientos AS float)) 	, " +
		  	"												 2											" +
		  	"												) 											" +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	" +
		  	" GROUP BY o.idAnio,	o.idSemestre 														" +
		  	" ORDER BY o.idAnio ASC,	o.idSemestre 	ASC												" 
		), 
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoAnualRotacion", query = "							" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio 									," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(CAST(o.valorSaldo AS float))			 " +
		  	"												) 											 " +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	 " +
		  	" WHERE o.pk.fechaValor BETWEEN :desde AND :hasta AND o.pk.horaLiquidacion=:hora			 " +
		  	" GROUP BY o.idAnio																			 " +
		  	" ORDER BY o.idAnio	ASC																	     " 
		),
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoAnualRotacionSG", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio 									," +
		  	"												SUM(o.numTotalMovimientos)														   				," +
			"												CASE WHEN SUM(														 	 		   				 " +
			"												  		CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END   	 				 " +
			"													)=0 THEN 0D   														 	    				 " +
			"												ELSE (																							 " +
			"														SUM(CAST(o.valorTotalMovimientos AS float)) /											 " +
			"														SUM(CASE WHEN o.pk.horaLiquidacion=:hora THEN CAST(o.valorSaldo AS float)	ELSE 0D END)	 			 " +	
			"												) END)	 																						 " +
		  	" FROM AnalisisPagosSaldosXHora o 														 	 " +
		  	" GROUP BY o.idAnio																			 " +
		  	" ORDER BY o.idAnio	ASC																	 	 " 
		),
	@NamedQuery(name = "AnalisisPagosSaldosXHora.getConjuntoAnualRotacionPIBSG", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio 									," +
		  	"												 SUM(o.numTotalMovimientos)					," +
		  	"												 SUM(CAST(o.valorTotalMovimientos AS float))	 " +
		  	"												) 											 " +  
		  	" FROM AnalisisPagosSaldosXHora o 														 	 " +
		  	" GROUP BY o.idAnio																			 " +
		  	" ORDER BY o.idAnio	ASC																	 	 " 
		)
})
@Table(name = "C_CUDAnalisisPagosSaldosXHora")
public class AnalisisPagosSaldosXHora implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AnalisisPagosSaldosXHoraPK pk;
	
	@Column(name = "Id_Anio")
    private String idAnio;
    
	@Column(name = "Id_Semestre")
    private BigDecimal idSemestre;
    
	@Column(name = "Id_Trimestre")
    private BigDecimal idTrimestre;
    
	@Column(name = "Id_Mes")
    private BigDecimal idMes;

    @Column(name = "Num_Movimientos")
    private BigDecimal numTotalMovimientos;
    
    @Column(name = "Cv_ValorMovimientos")
    private BigDecimal valorTotalMovimientos;

    @Column(name = "Cv_ValorSaldo")
    private BigDecimal valorSaldo;

    @Transient
    private BigDecimal pib;
    
    @Transient
    private BigDecimal calrotacion;

    @Transient
    private BigDecimal calpib;

        
	public String getIdAnio() {
		return idAnio;
	}

	public void setIdAnio(String idAnio) {
		this.idAnio = idAnio;
	}

	public BigDecimal getIdSemestre() {
		return idSemestre;
	}

	public void setIdSemestre(BigDecimal idSemestre) {
		this.idSemestre = idSemestre;
	}

	public BigDecimal getIdTrimestre() {
		return idTrimestre;
	}

	public void setIdTrimestre(BigDecimal idTrimestre) {
		this.idTrimestre = idTrimestre;
	}

	public BigDecimal getIdMes() {
		return idMes;
	}

	public void setIdMes(BigDecimal idMes) {
		this.idMes = idMes;
	}

	public BigDecimal getNumTotalMovimientos() {
		return numTotalMovimientos;
	}

	public void setNumTotalMovimientos(BigDecimal numTotalMovimientos) {
		this.numTotalMovimientos = numTotalMovimientos;
	}

	public BigDecimal getValorTotalMovimientos() {
		return valorTotalMovimientos;
	}

	public void setValorTotalMovimientos(BigDecimal valorTotalMovimientos) {
		this.valorTotalMovimientos = valorTotalMovimientos;
	}

	public BigDecimal getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(BigDecimal valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public AnalisisPagosSaldosXHoraPK getPk() {
		return pk;
	}

	public void setPk(AnalisisPagosSaldosXHoraPK pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		String resultado = null;
			resultado =  MessageFormat.format("AnalisisPagosSaldosXHora [Fecha_valor={0},idAnio={1},idSemestre={2},idTrimestre={3},idMes={4},horaLiquidacion={5},numTotalMovimientos={6},valorTotalMovimientos={7},valorSaldo={8}]",				
					pk.getFechaValor(), idAnio,idSemestre,idTrimestre,idMes,pk.getHoraLiquidacion(),numTotalMovimientos,valorTotalMovimientos,valorSaldo );
		return resultado;
	}

	public BigDecimal getPib() {
		return pib;
	}

	public void setPib(BigDecimal pib) {
		this.pib = pib;
	}

	public BigDecimal getCalrotacion() {
		return calrotacion;
	}

	public void setCalrotacion(BigDecimal calrotacion) {
		this.calrotacion = calrotacion;
	}

	public BigDecimal getCalpib() {
		return calpib;
	}

	public void setCalpib(BigDecimal calpib) {
		this.calpib = calpib;
	}

}
