package com.indico.jee.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( {
	@NamedQuery(name = "MedioServCompensacion.findAll", query = "" +
		" SELECT o FROM MedioServCompensacion o ORDER BY o.descMedioServCompensacion" 
	)
})	  
@Table(name = "V_CCC_MedioServCompensacion")
public class MedioServCompensacion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id_MedioServCompensacion")
    private String idMedioServCompensacion;
	
	@Column(name = "Desc_MedioServCompensacion")
    private String descMedioServCompensacion;
    
    public String getIdMedioServCompensacion() {
		return idMedioServCompensacion;
	}

	public void setIdMedioServCompensacion(String idMedioServCompensacion) {
		this.idMedioServCompensacion = idMedioServCompensacion;
	}

	public String getDescMedioServCompensacion() {
		return descMedioServCompensacion;
	}

	public void setDescMedioServCompensacion(String descMedioServCompensacion) {
		this.descMedioServCompensacion = descMedioServCompensacion;
	}

    
   
}
