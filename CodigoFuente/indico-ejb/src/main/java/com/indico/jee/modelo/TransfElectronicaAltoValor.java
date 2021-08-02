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

@Entity
@NamedQueries( { 
	@NamedQuery(name = "TransElectAltoValor.getLastDate", query = "		" +
		  	" SELECT max (t.transElectAltoValorPK.fechaValorPK) FROM TransfElectronicaAltoValor t 	" 
			),
	  @NamedQuery(name = "TransElectAltoValor.findAll", query = ""						+
	  		" select o from TransfElectronicaAltoValor o "								+
	  		" where o.transElectAltoValorPK.idGrupoTipoTransaccionPK is not null or "	+
	  		"       o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK is not null "	+
	  		" order by o.transElectAltoValorPK.fechaValorPK ASC"),
	  
	  @NamedQuery(name = "TransElectAltoValor.countAll", query = ""
		  		+ "select count(o) from TransfElectronicaAltoValor o"),
	  @NamedQuery(name = "TransElectAltoValor.getSubgrupos", query = "									 " +
		  	" select new com.indico.jee.util.CampoSelect(o.descSubGrupoTipoTransaccion,o.transElectAltoValorPK.idGrupoTipoTransaccionPK,o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK) " +
		  	" from TransfElectronicaAltoValor o 														 									" +
		  	" GROUP BY o.transElectAltoValorPK.idGrupoTipoTransaccionPK,o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK,o.descSubGrupoTipoTransaccion 									" +
		  	" ORDER BY o.descSubGrupoTipoTransaccion																						"),
	  
	  @NamedQuery(name = "TransElectAltoValor.getGrupos", query = "									 " +
			  	" select new com.indico.jee.util.CampoSelect(o.descGrupoTipoTransaccion,o.transElectAltoValorPK.idGrupoTipoTransaccionPK) 	" +
			  	" from TransfElectronicaAltoValor o 														 								" +
			  	" GROUP BY o.descGrupoTipoTransaccion,o.transElectAltoValorPK.idGrupoTipoTransaccionPK 										" +
			  	" ORDER BY o.descGrupoTipoTransaccion																					"),
	  @NamedQuery(name = "TransElectAltoValor.getDetalleExportar", query = "									 		" +
			  	" SELECT o	FROM TransfElectronicaAltoValor o 													" +
			  	" JOIN FETCH o.transElectAltoValorPK															" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta  						" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK		 											" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getDetalleExportarSG", query = "									 		" +
			  	" SELECT o	FROM TransfElectronicaAltoValor o 													" +
			  	" JOIN FETCH o.transElectAltoValorPK															" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta  		AND				" +
			  	" 		o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo						" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK,o.transElectAltoValorPK.idGrupoTipoTransaccionPK" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getAllDiario", query = "									 		 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.transElectAltoValorPK.fechaValorPK			," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta  						" +
			  	" GROUP BY o.transElectAltoValorPK.fechaValorPK																								" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK		ASC										" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getAllDiarioComplete", query = "									 		 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.transElectAltoValorPK.fechaValorPK			," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" GROUP BY o.transElectAltoValorPK.fechaValorPK																								" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK		ASC										" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoDiario", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.transElectAltoValorPK.fechaValorPK			," +
			  	"												 o.numTotalMovimientos							," +
			  	"												 o.cvValorTotalMovimientos/1000000000			" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta AND 					" +
			  	"       o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK = :idSubGrupo						" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK ASC " 
			),
	  @NamedQuery(name = "TransElectAltoValor.getAllConjuntoDiarioBySG", query = "								 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.transElectAltoValorPK.fechaValorPK			," +
			  	"												 o.numTotalMovimientos							," +
			  	"												 o.cvValorTotalMovimientos/1000000000			" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE 																						" +
			  	" o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo		AND						" +
			  	" o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK 	= :idSubGrupo							" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK ASC 											" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getAllConjuntoDiarioByGR", query = "								 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.transElectAltoValorPK.fechaValorPK			," +
			  	"											SUM(o.numTotalMovimientos)							," +
			  	"											SUM(o.cvValorTotalMovimientos)/1000000000			" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE 																						" +
			  	" o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo								" +
			  	" GROUP BY o.transElectAltoValorPK.fechaValorPK		 											" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK ASC 											" 
			),
	  @NamedQuery(name = "TransElectAltoValor.lastMovementsDaily", query = "									" +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.transElectAltoValorPK.fechaValorPK			," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" GROUP BY o.transElectAltoValorPK.fechaValorPK													" +
			  	" ORDER BY o.transElectAltoValorPK.fechaValorPK	DESC											" 
		  		),
	  
	  @NamedQuery(name = "TransElectAltoValor.getAllMensual", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									,"+
			  	"												 o.idMes									," +
			  	"												 SUM(o.numTotalMovimientos)					," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000	," +
			  	"												 0											 " +
			  	"												) 											 " +  
			  	" FROM TransfElectronicaAltoValor o 														 " +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta 	 				 " +
			  	" GROUP BY o.idAnio,o.idMes																	 " +
			  	" ORDER BY o.idAnio ASC ,o.idMes ASC 														 " 
			  ), 
	  @NamedQuery(name = "TransElectAltoValor.getAllMensualComplete", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									,"+
			  	"												 o.idMes									," +
			  	"												 SUM(o.numTotalMovimientos)					," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000	," +
			  	"												 0											 " +
			  	"												) 											 " +  
			  	" FROM TransfElectronicaAltoValor o 														 " +
			  	" GROUP BY o.idAnio,o.idMes																	 " +
			  	" ORDER BY o.idAnio ASC ,o.idMes ASC 														 " 
			  ), 
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoMensual", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idMes,										" +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 0												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta AND 					" +
			  	"       o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK = :idSubGrupo						" + 
			  	" GROUP BY o.idAnio,o.idMes																		" +
			  	" ORDER BY o.idAnio ASC ,o.idMes ASC 																	" 
			  ), 
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoMensualComplete", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idMes,										" +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 0												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo		AND				" +
			  	" 		o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK 	= :idSubGrupo					" +
			  	" GROUP BY o.idAnio,o.idMes																		" +
			  	" ORDER BY o.idAnio ASC ,o.idMes ASC 															" 
			  ), 
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoMensualCompleteByGR", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idMes,										" +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 0												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo						" +
			  	" GROUP BY o.idAnio,o.idMes																		" +
			  	" ORDER BY o.idAnio ASC ,o.idMes ASC 															" 
			  ), 
	  @NamedQuery(name = "TransElectAltoValor.getAllTrimestral", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idTrimestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 1												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta 	 					" +
			  	" GROUP BY o.idAnio,o.idTrimestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idTrimestre	ASC															" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getAllTrimestralComplete", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idTrimestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 1												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" GROUP BY o.idAnio,o.idTrimestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idTrimestre	ASC															" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoTrimestral", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idTrimestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 1												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta AND 					" +
			  	"       o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK = :idSubGrupo						" + 
			  	" GROUP BY o.idAnio,o.idTrimestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idTrimestre ASC																" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoTrimestralSG", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idTrimestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 1												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo		AND				" +
			  	" 		o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK 	= :idSubGrupo					" +
			  	" GROUP BY o.idAnio,o.idTrimestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idTrimestre ASC																" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoTrimestralByGR", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idTrimestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 1												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo						" +
			  	" GROUP BY o.idAnio,o.idTrimestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idTrimestre ASC																" 
			), 
	  @NamedQuery(name = "TransElectAltoValor.getAllSemestral", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idSemestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 2												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta  					" +
			  	" GROUP BY o.idAnio,o.idSemestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idSemestre	ASC													" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getAllSemestralComplete", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idSemestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 2												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" GROUP BY o.idAnio,o.idSemestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idSemestre	ASC													" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoSemestral", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idSemestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 2												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta AND 					" +
			  	"       o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK = :idSubGrupo						" + 
			  	" GROUP BY o.idAnio,o.idSemestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idSemestre	ASC													" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoSemestralByGR", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 o.idSemestre									," +
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		," +
			  	"												 2												" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo						" +
			  	" GROUP BY o.idAnio,o.idSemestre																" +
			  	" ORDER BY o.idAnio ASC ,o.idSemestre	ASC													" 
			),

	  @NamedQuery(name = "TransElectAltoValor.getAllAnual", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta 	 					" +
			  	" GROUP BY o.idAnio																				" +
			  	" ORDER BY o.idAnio	 ASC																" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getAllAnualComplete", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" GROUP BY o.idAnio																				" +
			  	" ORDER BY o.idAnio	 ASC																" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoAnual", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.fechaValorPK  BETWEEN :desde AND :hasta AND 					" +
			  	"       o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK = :idSubGrupo						" + 
			  	" GROUP BY o.idAnio																				" +
			  	" ORDER BY o.idAnio	ASC																" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoAnualSG", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo		AND				" +
			  	" 		o.transElectAltoValorPK.idSubGrupoTipoTransaccionPK 	= :idSubGrupo					" +
			  	" GROUP BY o.idAnio																				" +
			  	" ORDER BY o.idAnio	ASC																" 
			),
	  @NamedQuery(name = "TransElectAltoValor.getConjuntoAnualByGR", query = "									 	 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										,"+
			  	"												 SUM(o.numTotalMovimientos)						," +
			  	"												 SUM(o.cvValorTotalMovimientos)/1000000000		" +
			  	"												) 												" +  
			  	" FROM TransfElectronicaAltoValor o 														 	" +
			  	" WHERE o.transElectAltoValorPK.idGrupoTipoTransaccionPK 		= :idGrupo						" +
			  	" GROUP BY o.idAnio																				" +
			  	" ORDER BY o.idAnio	ASC																" 
			)
})	  
@Table(name = "C_CUDTransfElectronicaAltoVal")
public class TransfElectronicaAltoValor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TransfElectronicaAltoValorPK transElectAltoValorPK;
    
	@Column(name = "Id_Anio")
    private String idAnio;
    
	@Column(name = "Id_Semestre")
    private BigDecimal idSemestre;
    
	@Column(name = "Id_Trimestre")
    private BigDecimal idTrimestre;
    
	@Column(name = "Id_Mes")
    private BigDecimal idMes;

    @Column(name = "Desc_GrupoTipoTransaccion")
    private String descGrupoTipoTransaccion;

    @Column(name = "Desc_SubGrupoTipoTransaccion")
    private String descSubGrupoTipoTransaccion;
    
    @Column(name = "Num_TotalMovimientos")
    private BigDecimal numTotalMovimientos;
    
    @Column(name = "Cv_ValorTotalMovimientos")
    private BigDecimal cvValorTotalMovimientos;

    
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
	
	public void setId_Trimestre(BigDecimal idTrimestre) {
		this.idTrimestre = idTrimestre;
	}
	
	public BigDecimal getIdMes() {
		return idMes;
	}
	
	public void setIdMes(BigDecimal idMes) {
		this.idMes = idMes;
	}
	
	public String getDescGrupoTipoTransaccion() {
		return descGrupoTipoTransaccion;
	}
	
	public void setDescGrupoTipoTransaccion(String descGrupoTipoTransaccion) {
		this.descGrupoTipoTransaccion = descGrupoTipoTransaccion;
	}
	
	public String getDescSubGrupoTipoTransaccion() {
		return descSubGrupoTipoTransaccion;
	}
	
	public void setDescSubGrupoTipoTransaccion(String descSubGrupoTipoTransaccion) {
		this.descSubGrupoTipoTransaccion = descSubGrupoTipoTransaccion;
	}
	
	public BigDecimal getNumTotalMovimientos() {
		return numTotalMovimientos;
	}
	
	public void setNum_TotalMovimientos(BigDecimal numTotalMovimientos) {
		this.numTotalMovimientos = numTotalMovimientos;
	}
	
	public BigDecimal getCv_ValorTotalMovimientos() {
		return cvValorTotalMovimientos;
	}
	
	public void setCv_ValorTotalMovimientos(BigDecimal cvValorTotalMovimientos) {
		this.cvValorTotalMovimientos = cvValorTotalMovimientos;
	}

	public TransfElectronicaAltoValorPK getTransElectAltoValorPK() {
		return transElectAltoValorPK;
	}

	public void setTransElectAltoValorPK(TransfElectronicaAltoValorPK transElectAltoValorPK) {
		this.transElectAltoValorPK = transElectAltoValorPK;
	}

	@Override
	public String toString() {
		return MessageFormat.format("TransElectAltoValor[idAnio={0},idSemestre={1},idTrimestre={2},idMes={3},descGrupoTipoTransaccion={4},descSubGrupoTipoTransaccion={5},numTotalMovimientos={6},cvValorTotalMovimientos={7}]", 
				idAnio,idSemestre,idTrimestre,idMes,descGrupoTipoTransaccion,descSubGrupoTipoTransaccion,numTotalMovimientos,cvValorTotalMovimientos);
	}

	
    
}
