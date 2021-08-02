package com.indico.jee.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import static com.indico.jee.util.Constants.*;
public class Series {
	
	private String ValorFecha;
	
	private BigDecimal SerieValor;
	
	
	
	public Series(String ValorFecha, BigDecimal SerieValor)
	{
		this.setValorFecha(ValorFecha);
		this.setSerieValor(SerieValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
	}
	

	public BigDecimal getSerieValor() {
		return SerieValor;
	}

	public void setSerieValor(BigDecimal serieValor) {
		SerieValor = serieValor;
	}



	public String getValorFecha() {
		return ValorFecha;
	}



	public void setValorFecha(String valorFecha) {
		ValorFecha = valorFecha;
	}

}
