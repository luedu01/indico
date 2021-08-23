package com.indico.jee.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class AnalisisPagosSaldosXHoraPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Fecha_Valor")
    private Date fechaValor;
	
    @Column(name = "Id_HoraLiquidacion")
    private Integer horaLiquidacion;

	public Date getFechaValor() {
		return fechaValor;
	}

	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}

	public Integer getHoraLiquidacion() {
		return horaLiquidacion;
	}

	public void setHoraLiquidacion(Integer horaLiquidacion) {
		this.horaLiquidacion = horaLiquidacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaValor == null) ? 0 : fechaValor.hashCode());
		result = prime * result + ((horaLiquidacion == null) ? 0 : horaLiquidacion.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AnalisisPagosSaldosXHoraPK other = (AnalisisPagosSaldosXHoraPK) obj; 
		if (fechaValor == null) {
			if (other.fechaValor != null) return false;
		} else if (!fechaValor.equals(other.fechaValor)) return false;
		if (horaLiquidacion == null) { if (other.horaLiquidacion != null) return false; }
		else if (!horaLiquidacion.equals(other.horaLiquidacion)) {return false; }
		return true;
	}
	
	
}
