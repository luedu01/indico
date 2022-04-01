package com.indico.jee.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({	
	@NamedQuery(name = "AnalisisChequesPesosConstantes.getChequesPesosConstantesAll", query = "		"
			+ " SELECT new com.indico.jee.util.ValorGraficable(	o.cvIPCAnual						,"
			+ "												 	o.numChequesCanje					,"
			+ "												 	o.cvValorCanje						,"
			+"													o.cvPorcentajeVariacion				,"
			+"													o.anioCanje							"	
			+"												) 									    "
			+" FROM AnalisisChequesPesosConstantes o 												"
			+" ORDER BY o.anioCanje ASC																"
			),
	
	@NamedQuery(name = "AnalisisChequesPesosConstantes.findAll", query = "							" 
		  	+" SELECT o FROM AnalisisChequesPesosConstantes o	 									" 
		  	+" ORDER BY o.anioCanje ASC"
		),
})
@Table(name = "C_CCCAgrAnualCanjePesosConst")
public class AnalisisChequesPesosConstantes {
	
	//private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Anio_Canje")
	private String anioCanje;

	@Column(name = "Cv_IPCAnual")
	private BigDecimal cvIPCAnual;

	@Column(name = "Num_ChequesCanje")
	private BigDecimal numChequesCanje;

	@Column(name = "Cv_ValorCanje")
	private BigDecimal cvValorCanje;

	@Column(name = "Cv_ValorConstanteCanje")
	private BigDecimal cvValorConstanteCanje;

	@Column(name = "Cv_PorcentajeVariacion")
	private BigDecimal cvPorcentajeVariacion;

	public String getAnioCanje() {
		return anioCanje;
	}

	public void setAnioCanje(String anioCanje) {
		this.anioCanje = anioCanje;
	}

	public BigDecimal getCvIPCAnual() {
		return cvIPCAnual;
	}

	public void setCvIPCAnual(BigDecimal cvIPCAnual) {
		this.cvIPCAnual = cvIPCAnual;
	}

	public BigDecimal getNumChequesCanje() {
		return numChequesCanje;
	}

	public void setNumChequesCanje(BigDecimal numChequesCanje) {
		this.numChequesCanje = numChequesCanje;
	}

	public BigDecimal getCvValorCanje() {
		return cvValorCanje;
	}

	public void setCvValorCanje(BigDecimal cvValorCanje) {
		this.cvValorCanje = cvValorCanje;
	}

	public BigDecimal getCvValorConstanteCanje() {
		return cvValorConstanteCanje;
	}

	public void setCvValorConstanteCanje(BigDecimal cvValorConstanteCanje) {
		this.cvValorConstanteCanje = cvValorConstanteCanje;
	}

	public BigDecimal getCvPorcentajeVariacion() {
		return cvPorcentajeVariacion;
	}

	public void setCvPorcentajeVariacion(BigDecimal cvPorcentajeVariacion) {
		this.cvPorcentajeVariacion = cvPorcentajeVariacion;
	}
}
