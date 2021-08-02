package com.indico.jee.modelo;

import java.io.Serializable;
import java.text.MessageFormat;

public class Anios implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String idAnio;
    
    public Anios(String idAnio){
    		this.idAnio=idAnio;
    }
    
	public String getIdAnio() {
		return idAnio;
	}

	public void setIdAnio(String idAnio) {
		this.idAnio = idAnio;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Anios [idAnio={0}]", idAnio );
	}
}
