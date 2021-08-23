package com.indico.jee.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class AnalisisChequesCompensaPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Fecha_Compensacion")
    private Date fechaCompensacion;
	
    @Column(name = "Id_CiudadDANECompensacion")
    private String idCiudadDaneCompensacion;

    @Column(name = "Id_SesionCompensacion")
    private String idSesionCompensacion;

    @Column(name = "Id_MedioServCompensacion")
    private String idMedioServCompensacion;

    
	public Date getFechaCompensacion() {
		return fechaCompensacion;
	}


	public void setFechaCompensacion(Date fechaCompensacion) {
		this.fechaCompensacion = fechaCompensacion;
	}


	public String getIdCiudadDaneCompensacion() {
		return idCiudadDaneCompensacion;
	}


	public void setIdCiudadDaneCompensacion(String idCiudadDaneCompensacion) {
		this.idCiudadDaneCompensacion = idCiudadDaneCompensacion;
	}


	public String getIdSesionCompensacion() {
		return idSesionCompensacion;
	}


	public void setIdSesionCompensacion(String idSesionCompensacion) {
		this.idSesionCompensacion = idSesionCompensacion;
	}


	public String getIdMedioServCompensacion() {
		return idMedioServCompensacion;
	}


	public void setIdMedioServCompensacion(String idMedioServCompensacion) {
		this.idMedioServCompensacion = idMedioServCompensacion;
	}

    
}
