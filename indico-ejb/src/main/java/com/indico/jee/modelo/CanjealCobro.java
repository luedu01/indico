package com.indico.jee.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
public class CanjealCobro {
	
	private BigDecimal ValorPorcentaje;
	
	private BigDecimal SerieValor;
	
	private String Tipo;
	
	private String Ejex;
	
	private String Ciudad;
	
	public CanjealCobro(String Tipo, String Ejex, String Ciudad, BigDecimal ValorPorcentaje, BigDecimal SerieaValor)
	{
		this.setTipo(Tipo);
		this.setEjex(Ejex);
		this.setCiudad(Ciudad);
		this.setValorPorcentaje(ValorPorcentaje.setScale(4, RoundingMode.DOWN));
		this.setSerieValor(SerieaValor.setScale(4, RoundingMode.DOWN));
	}

	public BigDecimal getSerieValor() {
		return SerieValor;
	}

	public void setSerieValor(BigDecimal serieValor) {
		SerieValor = serieValor;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public String getEjex() {
		return Ejex;
	}

	public void setEjex(String ejex) {
		Ejex = ejex;
	}

	public String getCiudad() {
		return Ciudad;
	}

	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}

	public BigDecimal getValorPorcentaje() {
		return ValorPorcentaje;
	}

	public void setValorPorcentaje(BigDecimal valorPorcentaje) {
		ValorPorcentaje = valorPorcentaje;
	}

}
