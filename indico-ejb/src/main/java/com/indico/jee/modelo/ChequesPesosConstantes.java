package com.indico.jee.modelo;

import java.math.BigDecimal;

public class ChequesPesosConstantes {

	private String Ejex;
	private BigDecimal Serie ;
	private BigDecimal ValorIPC;
	private BigDecimal VariacionPor;
	
	public ChequesPesosConstantes() {
		super();
	}

	public ChequesPesosConstantes(String ejex, BigDecimal serie, BigDecimal valorIPC, BigDecimal variacionPor) {
		super();
		Ejex = ejex;
		Serie = serie;
		ValorIPC = valorIPC;
		VariacionPor = ((variacionPor == null) ? BigDecimal.ZERO : variacionPor);

	}

	public String getEjex() {
		return Ejex;
	}

	public void setEjex(String ejex) {
		Ejex = ejex;
	}

	public BigDecimal getSerie() {
		return Serie;
	}

	public void setSerie(BigDecimal serie) {
		Serie = serie;
	}

	public BigDecimal getValorIPC() {
		return ValorIPC;
	}

	public void setValorIPC(BigDecimal valorIPC) {
		ValorIPC = valorIPC;
	}

	public BigDecimal getVariacionPor() {
		return VariacionPor;
	}

	public void setVariacionPor(BigDecimal variacionPor) {
		VariacionPor = variacionPor;
	}
	
	
	
	
	
}

