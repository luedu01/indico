package com.indico.jee.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AnalisisComposicionCanjePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "AnioMes_Canje")
    private Integer anioMesCanje;

    @Column(name = "Id_RangoCanje")
    private String idRangoCanje;

    @Column(name = "Id_MedioServCompensacion")
    private String idMedioServCompensacion;

	public Integer getAnioMesCanje() {
		return anioMesCanje;
	}

	public void setAnioMesCanje(Integer anioMesCanje) {
		this.anioMesCanje = anioMesCanje;
	}

	public String getIdRangoCanje() {
		return idRangoCanje;
	}

	public void setIdRangoCanje(String idRangoCanje) {
		this.idRangoCanje = idRangoCanje;
	}

	public String getIdMedioServCompensacion() {
		return idMedioServCompensacion;
	}

	public void setIdMedioServCompensacion(String idMedioServCompensacion) {
		this.idMedioServCompensacion = idMedioServCompensacion;
	}
    
    
}
