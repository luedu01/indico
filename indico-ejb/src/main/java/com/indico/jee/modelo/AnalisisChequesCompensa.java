package com.indico.jee.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
 
@Entity
@NamedQueries( {
  @NamedQuery(name = "AnalisisChequesCompensa.getLastDate", query = "		" +
			  	" SELECT max (o.pk.fechaCompensacion) FROM AnalisisChequesCompensa o 	" 
	),
  @NamedQuery(name = "AnalisisChequesCompensa.findAll", query = "		" +
	  	" SELECT o FROM AnalisisChequesCompensa o 						" +
	  	" ORDER BY o.pk.fechaCompensacion DESC"
	),
  @NamedQuery(name = "AnalisisChequesCompensa.countAll", query = "	"+
	  	" select count(o) from AnalisisChequesCompensa o "
  ),
  @NamedQuery(name = "AnalisisChequesCompensa.getDataDiarioDevolTodas", query = "				" +
			" SELECT o FROM AnalisisChequesCompensa o 											" +
			" WHERE o.pk.idSesionCompensacion=2 AND 											" +	
			"       o.pk.fechaCompensacion BETWEEN :fechaInicio AND :fechaFin  					" +
			" ORDER BY o.pk.fechaCompensacion, o.descMedioServCompensacion, o.nombreCiudadCompensacion		ASC				"
	),
  @NamedQuery(name = "AnalisisChequesCompensa.getDataDiarioDevol", query = "					" +
			" SELECT o FROM AnalisisChequesCompensa o 											" +
			" WHERE o.pk.idSesionCompensacion=2 AND o.pk.idMedioServCompensacion=:medio AND		" +	
  			"       o.pk.fechaCompensacion BETWEEN :fechaInicio AND :fechaFin  					" +
  			" ORDER BY o.pk.fechaCompensacion, o.descMedioServCompensacion, o.nombreCiudadCompensacion		ASC				"
	),
  @NamedQuery(name = "AnalisisChequesCompensa.getDataDiarioCompenTodas", query = "				" +
			" SELECT o FROM AnalisisChequesCompensa o 											" +
			" WHERE o.pk.idSesionCompensacion=1 AND 											" +	
			"       o.pk.fechaCompensacion BETWEEN :fechaInicio AND :fechaFin  					" +
			" ORDER BY o.pk.fechaCompensacion, o.descMedioServCompensacion, o.nombreCiudadCompensacion		ASC				"
	),
@NamedQuery(name = "AnalisisChequesCompensa.getDataDiarioCompen", query = "						" +
			" SELECT o FROM AnalisisChequesCompensa o 											" +
			" WHERE o.pk.idSesionCompensacion=1 AND o.pk.idMedioServCompensacion=:medio AND		" +	
			"       o.pk.fechaCompensacion BETWEEN :fechaInicio AND :fechaFin  					" +
			" ORDER BY o.pk.fechaCompensacion, o.nombreCiudadCompensacion, o.descMedioServCompensacion		ASC				"
	),
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenDiarioGR", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
		  	"													o.pk.fechaCompensacion				," +
		  	"													o.descMedioServCompensacion			," +
		  	"												 	SUM(o.numCheques)					," +
		  	"												 	SUM(o.valorPresentado)				 " +
		  	"												) 										 " +  
		  	" FROM AnalisisChequesCompensa o 														 " +
		  	" WHERE  o.pk.idSesionCompensacion=1 													 " +
		  	" GROUP BY o.pk.fechaCompensacion,o.descMedioServCompensacion							 " +
		  	" ORDER BY o.pk.fechaCompensacion,o.descMedioServCompensacion		ASC					 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenDiariaAllGR", query = "					 			" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
		  	"													o.pk.fechaCompensacion						," +
		  	"													o.nombreCiudadCompensacion 					," +
		  	"												 	SUM(o.numCheques)							," +
		  	"												 	SUM(o.valorPresentado)				 		" +
		  	"												) 										 		" +  
		  	" FROM AnalisisChequesCompensa o 														 		" +
		  	" WHERE    o.pk.idSesionCompensacion=1 AND o.pk.idMedioServCompensacion=:medio					" +
		  	" GROUP BY o.pk.idMedioServCompensacion,o.pk.fechaCompensacion,o.nombreCiudadCompensacion		" +
		  	" ORDER BY o.pk.idMedioServCompensacion,o.pk.fechaCompensacion,o.nombreCiudadCompensacion	ASC" 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenMensualGR", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
		  	"													o.idAnio							," +
		  	"													o.idMes								," +
		  	"													o.descMedioServCompensacion			," +
		  	"												 	SUM(o.numCheques)					," +
		  	"												 	SUM(o.valorPresentado)				," +
		  	"													0									 " + 
		  	"												) 										 " +  
		  	" FROM AnalisisChequesCompensa o 														 " +
		  	" WHERE  o.pk.idSesionCompensacion=1 													 " +
		  	" GROUP BY o.idAnio,o.idMes,o.descMedioServCompensacion							 		 " +
		  	" ORDER BY o.idAnio,o.idMes,o.descMedioServCompensacion		ASC					 		 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenMensualAllGR", query = "					 			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
		  	"													o.idAnio									," +
		  	"													o.idMes										," +
		  	"													o.nombreCiudadCompensacion 					," +
		  	"												 	SUM(o.numCheques)							," +
		  	"												 	SUM(o.valorPresentado)				 		," +
		  	"													0									 		 " +  
		  	"												) 										 		 " +  
		  	" FROM AnalisisChequesCompensa o 														 		 " +
		  	" WHERE    o.pk.idSesionCompensacion=1 AND o.pk.idMedioServCompensacion=:medio					 " +
		  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.idMes,o.nombreCiudadCompensacion		 		 " +
		  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.idMes,o.nombreCiudadCompensacion	ASC  	 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenTrimestralGR", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
		  	"													o.idAnio							," +
		  	"													o.idTrimestre						," +
		  	"													o.descMedioServCompensacion			," +
		  	"												 	SUM(o.numCheques)					," +
		  	"												 	SUM(o.valorPresentado)				," +
		  	"													1									 " +  
		  	"												) 										 " +  
		  	" FROM AnalisisChequesCompensa o 														 " +
		  	" WHERE  o.pk.idSesionCompensacion=1 													 " +
		  	" GROUP BY o.idAnio,o.idTrimestre,o.descMedioServCompensacion							 		 " +
		  	" ORDER BY o.idAnio,o.idTrimestre,o.descMedioServCompensacion		ASC					 		 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenTrimestralAllGR", query = "					 			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
		  	"													o.idAnio									," +
		  	"													o.idTrimestre								," +
		  	"													o.nombreCiudadCompensacion 					," +
		  	"												 	SUM(o.numCheques)							," +
		  	"												 	SUM(o.valorPresentado)				 		," +
		  	"													1									 		 " +  
		  	"												) 										 		 " +  
		  	" FROM AnalisisChequesCompensa o 														 		 " +
		  	" WHERE    o.pk.idSesionCompensacion=1 AND o.pk.idMedioServCompensacion=:medio					 " +
		  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.idTrimestre,o.nombreCiudadCompensacion		 		 " +
		  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.idTrimestre,o.nombreCiudadCompensacion	ASC  	 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenSemestralGR", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
		  	"													o.idAnio							," +
		  	"													o.idSemestre						," +
		  	"													o.descMedioServCompensacion			," +
		  	"												 	SUM(o.numCheques)					," +
		  	"												 	SUM(o.valorPresentado)				," +
		  	"													2									 " +  
		  	"												) 										 " +  
		  	" FROM AnalisisChequesCompensa o 														 " +
		  	" WHERE  o.pk.idSesionCompensacion=1 													 " +
		  	" GROUP BY o.idAnio,o.idSemestre,o.descMedioServCompensacion							 		 " +
		  	" ORDER BY o.idAnio,o.idSemestre,o.descMedioServCompensacion		ASC					 		 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenSemestralAllGR", query = "					 			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
		  	"													o.idAnio									," +
		  	"													o.idSemestre								," +
		  	"													o.nombreCiudadCompensacion 					," +
		  	"												 	SUM(o.numCheques)							," +
		  	"												 	SUM(o.valorPresentado)				 		," +
		  	"													2 								 		 	" +  
		  	"												) 										 		 " +  
		  	" FROM AnalisisChequesCompensa o 														 		 " +
		  	" WHERE    o.pk.idSesionCompensacion=1 AND o.pk.idMedioServCompensacion=:medio					 " +
		  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.idSemestre,o.nombreCiudadCompensacion		 		 " +
		  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.idSemestre,o.nombreCiudadCompensacion	ASC  	 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenAnualGR", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
		  	"													o.idAnio							," +
		  	"													o.descMedioServCompensacion			," +
		  	"												 	SUM(o.numCheques)					," +
		  	"												 	SUM(o.valorPresentado)				" +
		  	"												) 										 " +  
		  	" FROM AnalisisChequesCompensa o 														 " +
		  	" WHERE  o.pk.idSesionCompensacion=1 													 " +
		  	" GROUP BY o.idAnio,o.descMedioServCompensacion							 		 " +
		  	" ORDER BY o.idAnio,o.descMedioServCompensacion		ASC					 		 " 
		), 
  @NamedQuery(name = "AnalisisChequesCompensa.getCompenAnualAllGR", query = "					 			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
		  	"													o.idAnio									," +
		  	"													o.nombreCiudadCompensacion 					," +
		  	"												 	SUM(o.numCheques)							," +
		  	"												 	SUM(o.valorPresentado)				 		" +
		  	"												) 										 		 " +  
		  	" FROM AnalisisChequesCompensa o 														 		 " +
		  	" WHERE    o.pk.idSesionCompensacion=1 AND o.pk.idMedioServCompensacion=:medio					 " +
		  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.nombreCiudadCompensacion		 		 " +
		  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.nombreCiudadCompensacion	ASC  	 " 
		), 
  /**
   * NameQuerys de Devolucion de Chques
   */
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolDiarioGR", query = "					 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
	  	"													o.pk.fechaCompensacion				," +
	  	"													o.descMedioServCompensacion			," +
	  	"												 	SUM(o.numCheques)					," +
	  	"												 	SUM(o.valorPresentado)	 			" +
	  	"												) 										 " +  
	  	" FROM AnalisisChequesCompensa o 														 " +
	  	" WHERE  o.pk.idSesionCompensacion=2 													 " +
	  	" GROUP BY o.pk.fechaCompensacion,o.descMedioServCompensacion							 " +
	  	" ORDER BY o.pk.fechaCompensacion,o.descMedioServCompensacion		ASC					 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolDiariaAllGR", query = "					 			" +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
	  	"													o.pk.fechaCompensacion						," +
	  	"													o.nombreCiudadCompensacion 					," +
	  	"												 	SUM(o.numCheques)							," +
	  	"												 	SUM(o.valorPresentado)				 		" +
	  	"												) 										 		" +  
	  	" FROM AnalisisChequesCompensa o 														 		" +
	  	" WHERE    o.pk.idSesionCompensacion=2 AND o.pk.idMedioServCompensacion=:medio					" +
	  	" GROUP BY o.pk.idMedioServCompensacion,o.pk.fechaCompensacion,o.nombreCiudadCompensacion		" +
	  	" ORDER BY o.pk.idMedioServCompensacion,o.pk.fechaCompensacion,o.nombreCiudadCompensacion	ASC" 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolMensualGR", query = "					 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
	  	"													o.idAnio							," +
	  	"													o.idMes								," +
	  	"													o.descMedioServCompensacion			," +
	  	"												 	SUM(o.numCheques)					," +
	  	"												 	SUM(o.valorPresentado)				," +
	  	"													0									 " +  
	  	"												) 										 " +  
	  	" FROM AnalisisChequesCompensa o 														 " +
	  	" WHERE  o.pk.idSesionCompensacion=2 													 " +
	  	" GROUP BY o.idAnio,o.idMes,o.descMedioServCompensacion							 		 " +
	  	" ORDER BY o.idAnio,o.idMes,o.descMedioServCompensacion		ASC					 		 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolMensualAllGR", query = "					 			 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
	  	"													o.idAnio									," +
	  	"													o.idMes										," +
	  	"													o.nombreCiudadCompensacion 					," +
	  	"												 	SUM(o.numCheques)							," +
	  	"												 	SUM(o.valorPresentado)				 		," +
	  	"													0									 		 " +  
	  	"												) 										 		 " +  
	  	" FROM AnalisisChequesCompensa o 														 		 " +
	  	" WHERE    o.pk.idSesionCompensacion=2 AND o.pk.idMedioServCompensacion=:medio					 " +
	  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.idMes,o.nombreCiudadCompensacion		 		 " +
	  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.idMes,o.nombreCiudadCompensacion	ASC  	 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolTrimestralGR", query = "					 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
	  	"													o.idAnio							," +
	  	"													o.idTrimestre						," +
	  	"													o.descMedioServCompensacion			," +
	  	"												 	SUM(o.numCheques)					," +
	  	"												 	SUM(o.valorPresentado)				," +
	  	"													1									 " +  
	  	"												) 										 " +  
	  	" FROM AnalisisChequesCompensa o 														 " +
	  	" WHERE  o.pk.idSesionCompensacion=2 													 " +
	  	" GROUP BY o.idAnio,o.idTrimestre,o.descMedioServCompensacion							 		 " +
	  	" ORDER BY o.idAnio,o.idTrimestre,o.descMedioServCompensacion		ASC					 		 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolTrimestralAllGR", query = "					 			 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
	  	"													o.idAnio									," +
	  	"													o.idTrimestre								," +
	  	"													o.nombreCiudadCompensacion 					," +
	  	"												 	SUM(o.numCheques)							," +
	  	"												 	SUM(o.valorPresentado)				 		," +
	  	"													1									 		 " +  
	  	"												) 										 		 " +  
	  	" FROM AnalisisChequesCompensa o 														 		 " +
	  	" WHERE    o.pk.idSesionCompensacion=2 AND o.pk.idMedioServCompensacion=:medio					 " +
	  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.idTrimestre,o.nombreCiudadCompensacion		 		 " +
	  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.idTrimestre,o.nombreCiudadCompensacion	ASC  	 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolSemestralGR", query = "					 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
	  	"													o.idAnio							," +
	  	"													o.idSemestre						," +
	  	"													o.descMedioServCompensacion			," +
	  	"												 	SUM(o.numCheques)					," +
	  	"												 	SUM(o.valorPresentado)				," +
	  	"													2									 " +  
	  	"												) 										 " +  
	  	" FROM AnalisisChequesCompensa o 														 " +
	  	" WHERE  o.pk.idSesionCompensacion=2 													 " +
	  	" GROUP BY o.idAnio,o.idSemestre,o.descMedioServCompensacion							 		 " +
	  	" ORDER BY o.idAnio,o.idSemestre,o.descMedioServCompensacion		ASC					 		 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolSemestralAllGR", query = "					 			 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
	  	"													o.idAnio									," +
	  	"													o.idSemestre								," +
	  	"													o.nombreCiudadCompensacion 					," +
	  	"												 	SUM(o.numCheques)							," +
	  	"												 	SUM(o.valorPresentado)				 		," +
	  	"													2 								 		 	" +  
	  	"												) 										 		 " +  
	  	" FROM AnalisisChequesCompensa o 														 		 " +
	  	" WHERE    o.pk.idSesionCompensacion=2 AND o.pk.idMedioServCompensacion=:medio					 " +
	  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.idSemestre,o.nombreCiudadCompensacion		 		 " +
	  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.idSemestre,o.nombreCiudadCompensacion	ASC  	 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolAnualGR", query = "					 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	'todas'								,"+
	  	"													o.idAnio							," +
	  	"													o.descMedioServCompensacion			," +
	  	"												 	SUM(o.numCheques)					," +
	  	"												 	SUM(o.valorPresentado)				" +
	  	"												) 										 " +  
	  	" FROM AnalisisChequesCompensa o 														 " +
	  	" WHERE  o.pk.idSesionCompensacion=2 													 " +
	  	" GROUP BY o.idAnio,o.descMedioServCompensacion							 		 " +
	  	" ORDER BY o.idAnio,o.descMedioServCompensacion		ASC					 		 " 
	), 
	@NamedQuery(name = "AnalisisChequesCompensa.getCompenDevolAnualAllGR", query = "					 			 " +
	  	" SELECT new com.indico.jee.util.ValorGraficable(	o.pk.idMedioServCompensacion				,"+
	  	"													o.idAnio									," +
	  	"													o.nombreCiudadCompensacion 					," +
	  	"												 	SUM(o.numCheques)							," +
	  	"												 	SUM(o.valorPresentado)				 		" +
	  	"												) 										 		 " +  
	  	" FROM AnalisisChequesCompensa o 														 		 " +
	  	" WHERE    o.pk.idSesionCompensacion=2 AND o.pk.idMedioServCompensacion=:medio					 " +
	  	" GROUP BY o.pk.idMedioServCompensacion,o.idAnio,o.nombreCiudadCompensacion		 		 " +
	  	" ORDER BY o.pk.idMedioServCompensacion,o.idAnio,o.nombreCiudadCompensacion	ASC  	 " 
	), 

	/**
	 * Comportamiento de Canje  Al Cobro.
	 */
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoCanjeDiarioAll", query = "				 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaCompensacion						," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						" +
		  	"												) 											" +  
		  	" FROM AnalisisChequesCompensa o 														 	" +
		  	" WHERE o.pk.idSesionCompensacion=1	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.pk.fechaCompensacion															" +
		  	" ORDER BY o.pk.fechaCompensacion ASC 														" 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoCanjeMensualAll", query = "			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												 o.idMes									," +		
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +
		  	"												 0											" +	
		  	"												) 											" +  
		  	" FROM AnalisisChequesCompensa o 														 	" +
		  	" WHERE o.pk.idSesionCompensacion=1	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio,o.idMes																	" +
		  	" ORDER BY o.idAnio,o.idMes		 ASC 														" 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoCanjeTrimestralAll", query = "			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												 o.idTrimestre								," +		
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +
		  	"												 1											" +	
		  	"												) 											" +  
		  	" FROM AnalisisChequesCompensa o 														 	" +
		  	" WHERE o.pk.idSesionCompensacion=1	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio,o.idTrimestre															" +
		  	" ORDER BY o.idAnio,o.idTrimestre		 ASC												" 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoCanjeSemestralAll", query = "			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												 o.idSemestre								," +		
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +
		  	"												 2											" +	
		  	"												) 											" +  
		  	" FROM AnalisisChequesCompensa o 														 	" +
		  	" WHERE o.pk.idSesionCompensacion=1	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio,o.idSemestre															" +
		  	" ORDER BY o.idAnio,o.idSemestre		 ASC												" 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoCanjeAnualAll", query = "				" +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +	
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +	
		  	"												 'anual'									" +
		  	"												) 											" +  
		  	" FROM AnalisisChequesCompensa o 														 	" +
		  	" WHERE o.pk.idSesionCompensacion=1	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio																			" +
		  	" ORDER BY o.idAnio		 ASC																" 
	),
	/**
	 * Comportamiento Historico Devoluciones.
	 */
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoDevolDiarioAll", query = "				 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaCompensacion						," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						" +
		  	"												) 											" +  
		  	" FROM AnalisisChequesCompensa o 														 	" +
		  	" WHERE o.pk.idSesionCompensacion=2	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.pk.fechaCompensacion															" +
		  	" ORDER BY o.pk.fechaCompensacion ASC 														" 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoDevolMensualAll", query = "			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	" 												 o.idMes									," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +
		  	"												 0 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idSesionCompensacion=2	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio,o.idMes																	 " +
		  	" ORDER BY o.idAnio,o.idMes ASC 															 	" 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoDevolTrimestralAll", query = "			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	" 												 o.idTrimestre								," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +
		  	"												 1 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idSesionCompensacion=2	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio,o.idTrimestre															 " +
		  	" ORDER BY o.idAnio,o.idTrimestre ASC 													     " 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoDevolSemestralAll", query = "			 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	" 												 o.idSemestre								," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +
		  	"												 2 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idSesionCompensacion=2	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio,o.idSemestre															 " +
		  	" ORDER BY o.idAnio,o.idSemestre ASC 														 " 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getComportamientoDevolAnualAll", query = "				 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorPresentado)						," +
		  	"												 'anual'									 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idSesionCompensacion=2	AND 													" +
		  	"		o.pk.idMedioServCompensacion LIKE :medio 	AND   									" +
		  	"		o.pk.idCiudadDaneCompensacion LIKE :ciudad		 								    " +
		  	" GROUP BY o.idAnio																	 		 " +
		  	" ORDER BY o.idAnio ASC 															 		 " 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getCiudadesCanje", query = "				 " +
		  	" SELECT DISTINCT  o.pk.idCiudadDaneCompensacion,  o.nombreCiudadCompensacion 	 			 " +
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idSesionCompensacion=1	AND o.pk.idMedioServCompensacion = :medio				 " + 	
		  	" ORDER BY o.nombreCiudadCompensacion 														 " 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getCiudadesDevol", query = "				 " +
		  	" SELECT DISTINCT  o.pk.idCiudadDaneCompensacion,  o.nombreCiudadCompensacion 	 			 " +
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idSesionCompensacion=2	AND o.pk.idMedioServCompensacion = :medio				 " + 	
		  	" ORDER BY o.nombreCiudadCompensacion 														 " 
	),
	/**
	 * Comportamiento Devolucion Canje.
	 */
	@NamedQuery(name = "AnalisisChequesCompensa.getDevolucionCanjeDiarioAll", query = "					 " +
		  	" SELECT new com.indico.jee.util.ValorGraficable(o.pk.fechaCompensacion						," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.valorPresentado ELSE 0 END)	," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.valorPresentado ELSE 0 END)	 " +
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idMedioServCompensacion LIKE :medio 	   										 " +
		  	" GROUP BY o.pk.fechaCompensacion 															 " +
		  	" ORDER BY o.pk.fechaCompensacion ASC 														 " 
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getDevolucionCanjeMensualAll", query = "				 " +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												o.idMes										," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.valorPresentado ELSE 0 END)	," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.valorPresentado ELSE 0 END)	," +
			"														0									 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idMedioServCompensacion LIKE :medio 	   										 " +
		  	" GROUP BY o.idAnio, o.idMes																 " +
		  	" ORDER BY o.idAnio, o.idMes ASC															 "
		),
	@NamedQuery(name = "AnalisisChequesCompensa.getDevolucionCanjeTrimestralAll", query = "				 " +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												o.idTrimestre								," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.valorPresentado ELSE 0 END)	," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.valorPresentado ELSE 0 END)	," +
			"														1									 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idMedioServCompensacion LIKE :medio 	   										 " +
		  	" GROUP BY o.idAnio, o.idTrimestre															 " +
		  	" ORDER BY o.idAnio, o.idTrimestre ASC														 "
		),
	@NamedQuery(name = "AnalisisChequesCompensa.getDevolucionCanjeSemestralAll", query = "				 " +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												o.idSemestre								," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.valorPresentado ELSE 0 END)	," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.valorPresentado ELSE 0 END)	," +
			"														2									 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idMedioServCompensacion LIKE :medio 	   										 " +
		  	" GROUP BY o.idAnio, o.idSemestre															 " +
		  	" ORDER BY o.idAnio, o.idSemestre ASC														 "
		),
	@NamedQuery(name = "AnalisisChequesCompensa.getDevolucionCanjeAnualAll", query = "					 " +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.numCheques ELSE 0 END)		," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 1 THEN o.valorPresentado ELSE 0 END)	," +
			"			SUM(CASE WHEN o.pk.idSesionCompensacion = 2 THEN o.valorPresentado ELSE 0 END)	 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisChequesCompensa o 														 	 " +
		  	" WHERE o.pk.idMedioServCompensacion LIKE :medio 	   										 " +
		  	" GROUP BY o.idAnio																			 " +
		  	" ORDER BY o.idAnio ASC																		 "
		),
	@NamedQuery(name = "AnalisisChequesCompensa.getDataDiarioDevolCanjeTodas", query = "				 " +
			" select o FROM AnalisisChequesCompensa o 													 " +
			" WHERE  o.pk.fechaCompensacion BETWEEN :fechaInicio AND :fechaFin  						 " +
			" ORDER BY o.pk.fechaCompensacion, o.descMedioServCompensacion, o.nombreCiudadCompensacion ASC	"
	),
	@NamedQuery(name = "AnalisisChequesCompensa.getDataDiarioDevolCanje", query = "						" +
			" SELECT o FROM AnalisisChequesCompensa o 													" +
			" WHERE o.pk.idMedioServCompensacion=:medio AND												" +	
  			"       o.pk.fechaCompensacion BETWEEN :fechaInicio AND :fechaFin  							"
	),
})
@Table(name = "C_CCCAnalisisChequesCompensa")
public class AnalisisChequesCompensa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AnalisisChequesCompensaPK pk;
	
	@Column(name = "Id_Anio")
    private String idAnio;
    
	@Column(name = "Id_Semestre")
    private BigDecimal idSemestre;
    
	@Column(name = "Id_Trimestre")
    private BigDecimal idTrimestre;
    
	@Column(name = "Id_Mes")
    private BigDecimal idMes;

    @Column(name = "Nombre_CiudadCompensacion")
    private String nombreCiudadCompensacion;
    
    @Column(name = "Desc_SesionCompensacion")
    private String descSesionCompensacion;
    
    @Column(name = "Desc_MedioServCompensacion")
    private String descMedioServCompensacion;
    
    @Column(name = "Num_Cheques")
    private BigDecimal numCheques;

    @Column(name = "Cv_ValorPresentado")
    private BigDecimal valorPresentado;

        
	public AnalisisChequesCompensaPK getPk() {
		return pk;
	}

	public void setPk(AnalisisChequesCompensaPK pk) {
		this.pk = pk;
	}

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

	public String getNombreCiudadCompensacion() {
		return nombreCiudadCompensacion;
	}

	public void setNombreCiudadCompensacion(String nombreCiudadCompensacion) {
		this.nombreCiudadCompensacion = nombreCiudadCompensacion;
	}

	public String getDescSesionCompensacion() {
		return descSesionCompensacion;
	}

	public void setDescSesionCompensacion(String descSesionCompensacion) {
		this.descSesionCompensacion = descSesionCompensacion;
	}

	public String getDescMedioServCompensacion() {
		return descMedioServCompensacion;
	}

	public void setDescMedioServCompensacion(String descMedioServCompensacion) {
		this.descMedioServCompensacion = descMedioServCompensacion;
	}

	public BigDecimal getNumCheques() {
		return numCheques;
	}

	public void setNumCheques(BigDecimal numCheques) {
		this.numCheques = numCheques;
	}

	public BigDecimal getValorPresentado() {
		return valorPresentado;
	}

	public void setValorPresentado(BigDecimal valorPresentado) {
		this.valorPresentado = valorPresentado;
	}
    
    
    
}
