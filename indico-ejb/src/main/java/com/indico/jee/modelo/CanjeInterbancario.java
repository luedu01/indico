package com.indico.jee.modelo;

import java.math.BigDecimal;
public class CanjeInterbancario {
	private Integer IdRangoCanje;
	private BigDecimal Porcentaje;
	private String Ejex;
	
	public CanjeInterbancario() {
		
	}
	
	public CanjeInterbancario(Integer idRangoCanje, BigDecimal porcentaje, String ejex) {
		super();
		IdRangoCanje = idRangoCanje;
		Porcentaje = porcentaje;
		Ejex = ejex;
	}

	public Integer getIdRangoCanje() {
		return IdRangoCanje;
	}

	public void setIdRangoCanje(Integer idRangoCanje) {
		IdRangoCanje = idRangoCanje;
	}

	public BigDecimal getPorcentaje() {
		return Porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		Porcentaje = porcentaje;
	}

	public String getEjex() {
		return Ejex;
	}

	public void setEjex(String ejex) {
		Ejex = ejex;
	}
	
	
}

