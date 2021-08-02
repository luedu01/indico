package com.indico.jee.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class TransfElectronicaAltoValorPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "Fecha_Valor")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaValorPK;
	
	@Column(name = "Id_GrupoTipoTransaccion")
	private String idGrupoTipoTransaccionPK;
	
	@Column(name = "Id_SubGrupoTipoTransaccion")
	private String idSubGrupoTipoTransaccionPK;
	
	
	public Date getFechaValorPK() {
		return fechaValorPK;
	}
	public void setFechaValorPK(Date fechaValorPK) {
		this.fechaValorPK = fechaValorPK;
	}
	public String getIdGrupoTipoTransaccionPK() {
		return idGrupoTipoTransaccionPK;
	}
	public void setId_GrupoTipoTransaccionPK(String idGrupoTipoTransaccionPK) {
		this.idGrupoTipoTransaccionPK = idGrupoTipoTransaccionPK;
	}
	public String getIdSubGrupoTipoTransaccionPK() {
		return idSubGrupoTipoTransaccionPK;
	}
	public void setIdSubGrupoTipoTransaccionPK(String idSubGrupoTipoTransaccionPK) {
		this.idSubGrupoTipoTransaccionPK = idSubGrupoTipoTransaccionPK;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaValorPK == null) ? 0 : fechaValorPK.hashCode());
		result = prime * result + ((idGrupoTipoTransaccionPK == null) ? 0 : idGrupoTipoTransaccionPK.hashCode());
		result = prime * result + ((idSubGrupoTipoTransaccionPK == null) ? 0 : idSubGrupoTipoTransaccionPK.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransfElectronicaAltoValorPK other = (TransfElectronicaAltoValorPK) obj;
		if (fechaValorPK == null) {
			if (other.fechaValorPK != null)
				return false;
		} else if (!fechaValorPK.equals(other.fechaValorPK))
			return false;
		if (idGrupoTipoTransaccionPK == null) {
			if (other.idGrupoTipoTransaccionPK != null)
				return false;
		} else if (!idGrupoTipoTransaccionPK.equals(other.idGrupoTipoTransaccionPK))
			return false;
		if (idSubGrupoTipoTransaccionPK == null) {
			if (other.idSubGrupoTipoTransaccionPK != null)
				return false;
		} else if (!idSubGrupoTipoTransaccionPK.equals(other.idSubGrupoTipoTransaccionPK))
			return false;
		return true;
	}
	
	
}
