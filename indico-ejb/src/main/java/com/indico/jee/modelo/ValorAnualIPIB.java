package com.indico.jee.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { 
	@NamedQuery(name = "ValorAnualIPIB.findAll"	 , query = " select o from ValorAnualIPIB o 			 "
	),
	@NamedQuery(name = "ValorAnualIPIB.countAll", query = " select count(o) from ValorAnualIPIB o	 "
	)
})
@Table(name = "V_CUD_ValorAnualPIB")
public class ValorAnualIPIB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Num_AnioPIB")
    private String idAnio;
	
	@Column(name = "Cv_ValorPIB")
    private BigDecimal valorPIB;
	
        
	public String getIdAnio() {
		return idAnio;
	}

	public void setIdAnio(String idAnio) {
		this.idAnio = idAnio;
	}

	public BigDecimal getValorPIB() {
		return valorPIB;
	}

	public void setValorPIB(BigDecimal valorPIB) {
		this.valorPIB = valorPIB;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Anios [idAnio={0}],valorPIB={1}", idAnio,valorPIB );
	}
    
}
