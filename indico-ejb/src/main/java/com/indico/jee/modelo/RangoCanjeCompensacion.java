package com.indico.jee.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( {
	@NamedQuery(name = "RangoCanjeCompensacion.findAll", query = "" +
		" SELECT o FROM RangoCanjeCompensacion o ORDER BY o.valorInicial ASC" 
	)
})	  
@Table(name = "V_CCC_RangoCanje")
public class RangoCanjeCompensacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id_RangoCanje")
    private String idRangoCanje;
	
	@Column(name = "Cv_ValorInicial")
    private BigDecimal valorInicial;
	
	@Column(name = "Cv_ValorFinal")
    private BigDecimal valorFinal;
	
	@Column(name = "Fecha_InicioVigencia")
    private String fechaInicio;
	
	@Column(name = "Fecha_FinVigencia")
    private String fechaFin;

	public String getIdRangoCanje() {
		return idRangoCanje;
	}

	public void setIdRangoCanje(String idRangoCanje) {
		this.idRangoCanje = idRangoCanje;
	}

	public BigDecimal getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(BigDecimal valorInicial) {
		this.valorInicial = valorInicial;
	}

	public BigDecimal getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
