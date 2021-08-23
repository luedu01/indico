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
  @NamedQuery(name = "AnalisisComposicionCanje.getLastDate", query = "		" +
			  	" SELECT max (o.pk.anioMesCanje) FROM AnalisisComposicionCanje o 	" 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.findAll", query = "		" +
	  	" SELECT o FROM AnalisisComposicionCanje o 						" +
	  	" ORDER BY o.pk.anioMesCanje DESC"
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeMensualAll", query = "				" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	" 												 o.idMes									," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							," +
		  	"												 0 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" GROUP BY o.idAnio, o.idMes, o.valorInicialRango,o.valorFinalRango, o.pk.idRangoCanje		 " +
		  	" ORDER BY o.idAnio, o.idMes, o.valorInicialRango,o.valorFinalRango, o.pk.idRangoCanje  ASC    " 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeTrimestralAll", query = "				" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	" 												 o.idTrimestre								," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							," +
		  	"												 1 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" GROUP BY o.idAnio, o.idTrimestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango		 " +
		  	" ORDER BY o.idAnio, o.idTrimestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango ASC    " 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeSemestralAll", query = "				" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	" 												 o.idSemestre								," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							," +
		  	"												 2 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" GROUP BY o.idAnio, o.idSemestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango		 " +
		  	" ORDER BY o.idAnio, o.idSemestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango ASC    " 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeAnualAll", query = "				" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" GROUP BY o.idAnio, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango		 " +
		  	" ORDER BY o.idAnio, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango ASC    " 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeMensualByRango", query = "			" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	" 												 o.idMes									," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							," +
		  	"												 0 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" WHERE o.pk.idRangoCanje in (:idRangoCanje)												 " +
		  	" GROUP BY o.idAnio, o.idMes, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango		 " +
		  	" ORDER BY o.idAnio, o.idMes, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango ASC    " 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeTrimestralByRango", query = "				" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	" 												 o.idTrimestre								," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							," +
		  	"												 1 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" WHERE o.pk.idRangoCanje in (:idRangoCanje)												 " +
		  	" GROUP BY o.idAnio, o.idTrimestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango		 " +
		  	" ORDER BY o.idAnio, o.idTrimestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango ASC    " 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeSemestralByRango", query = "				" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	" 												 o.idSemestre								," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							," +
		  	"												 2 											 " +  
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" WHERE o.pk.idRangoCanje in (:idRangoCanje)												 " +
		  	" GROUP BY o.idAnio, o.idSemestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango		 " +
		  	" ORDER BY o.idAnio, o.idSemestre, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango ASC    " 
	),
  @NamedQuery(name = "AnalisisComposicionCanje.getComposicionCanjeAnualByRango", query = "				" +
		  " SELECT new com.indico.jee.util.ValorGraficable(o.idAnio										," +
		  	"												 SUM(o.numCheques)							," +
		  	"												 SUM(o.valorCanje)							," +
		  	"												 o.pk.idRangoCanje							," +
		  	"												 o.valorInicialRango						," +
		  	"												 o.valorFinalRango							 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisComposicionCanje o 														 	 " +
		  	" WHERE o.pk.idRangoCanje in (:idRangoCanje)												 " +
		  	" GROUP BY o.idAnio, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango		 		 " +
		  	" ORDER BY o.idAnio, o.pk.idRangoCanje,o.valorInicialRango,o.valorFinalRango ASC    		 " 
	),
  	@NamedQuery(name = "AnalisisComposicionCanje.getDataDiarioCompoCanjeTodas", query = "				" +
			" SELECT o FROM AnalisisComposicionCanje o 													" +
			" WHERE  o.pk.anioMesCanje BETWEEN :fechaInicio AND :fechaFin  								" +
			" ORDER BY o.pk.anioMesCanje,cast( o.pk.idRangoCanje as integer) ASC   "
	),
	@NamedQuery(name = "AnalisisComposicionCanje.getDataDiarioCompoCanje", query = "					" +
			" SELECT o FROM AnalisisComposicionCanje o 													" +
			" WHERE o.pk.idRangoCanje in (:idRangoCanje) AND											" +	
			"       o.pk.anioMesCanje BETWEEN :fechaInicio AND :fechaFin  								" +
			" ORDER BY o.pk.anioMesCanje, cast( o.pk.idRangoCanje as integer) ASC  	"
	),
})
@Table(name = "C_CCCComposicionMensualCanje")
public class AnalisisComposicionCanje implements Serializable{
	  
	private static final long serialVersionUID = 1L;
		
	@EmbeddedId
	private AnalisisComposicionCanjePK pk;
	
	@Column(name = "Id_Anio")
    private String idAnio;
    
	@Column(name = "Id_Semestre")
    private BigDecimal idSemestre;
    
	@Column(name = "Id_Trimestre")
    private BigDecimal idTrimestre;
    
	@Column(name = "Id_Mes")
    private BigDecimal idMes;
	
	@Column(name = "Desc_MedioServCompensacion")
	private String descmediocompensacion;
	
	@Column(name = "Num_Cheques")
	private BigDecimal numCheques;
	
	@Column(name = "Cv_ValorCanje")
	private BigDecimal valorCanje;

	@Column(name = "Cv_ValorInicialRango")
	private BigDecimal valorInicialRango;
	
	@Column(name = "Cv_ValorFinalRango")
	private BigDecimal valorFinalRango;

	public AnalisisComposicionCanjePK getPk() {
		return pk;
	}

	public void setPk(AnalisisComposicionCanjePK pk) {
		this.pk = pk;
	}
	
	public String getDescmediocompensacion() {
		return descmediocompensacion;
	}

	public void setDescmediocompensacion(String descmediocompensacion) {
		this.descmediocompensacion = descmediocompensacion;
	}

	public BigDecimal getNumCheques() {
		return numCheques;
	}

	public void setNumCheques(BigDecimal numCheques) {
		this.numCheques = numCheques;
	}

	public BigDecimal getValorCanje() {
		return valorCanje;
	}

	public void setValorCanje(BigDecimal valorCanje) {
		this.valorCanje = valorCanje;
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

	public BigDecimal getValorInicialRango() {
		return valorInicialRango;
	}

	public void setValorInicialRango(BigDecimal valorInicialRango) {
		this.valorInicialRango = valorInicialRango;
	}

	public BigDecimal getValorFinalRango() {
		return valorFinalRango;
	}

	public void setValorFinalRango(BigDecimal valorFinalRango) {
		this.valorFinalRango = valorFinalRango;
	}
	
}
