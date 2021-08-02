package com.indico.jee.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static com.indico.jee.util.Constants.*;

public class ValorGraficable implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String ejeX;

	private String tipo;
	
	private String ciudad;
	
	private BigDecimal serieValor2;
	
	private BigDecimal serieCantidad2;

	private BigDecimal serieValor;
	
	private BigDecimal serieCantidad;

	private BigDecimal serieValorPorcentaje;
	
	private BigDecimal serieCantidadPorcentaje;

	private Integer anio;

	private int hora;

	private String IdRangoCanje;

	private BigDecimal valorInicialRango;

	private BigDecimal valorFinalRango;
	
	private BigDecimal valorIPC;
	
	private BigDecimal VariacionPor;
	
	private static final int MENSUAL 		=0;
	private static final int TRIMESTRAL 	=1;
	private static final int SEMESTRAL 		=2;
	
		
	private static final SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 *  Metodos Utilizados para grafica de transacciones
	 *   
	 **/
	public  ValorGraficable(Date fecha, BigDecimal serieCantidad,Long serieValor ) {
		this(fecha, serieCantidad, BigDecimal.valueOf(serieValor));
	}

	public  ValorGraficable(Date fecha, BigDecimal serieCantidad,double serieValor ) {
		this(fecha, serieCantidad, BigDecimal.valueOf(serieValor));
	}

	/**
	 * Metodos utilizados para pib y rotación
	 * 
	 */
	public  ValorGraficable(Date fecha,int serieCantidad, double serieValor ) {
		this(fecha, BigDecimal.ZERO, BigDecimal.valueOf(serieValor));
	}
	public  ValorGraficable(Date fecha,int serieCantidad, BigDecimal serieValor ) {
		this(fecha, BigDecimal.ZERO, serieValor);
	}
	
	public  ValorGraficable(Date fecha, double serieValor ) {
		this(fecha, BigDecimal.valueOf(serieValor));
	}
	
	public  ValorGraficable(Date fecha, BigDecimal serieValor ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		if (fecha != null) this.anio = cal.get(Calendar.YEAR);
		synchronized (sd1) {
			this.ejeX 			= sd1.format(fecha);
		}
		this.serieValor		=  serieValor;
	}
	
	public  ValorGraficable(Date fecha, BigDecimal serieCantidad,BigDecimal serieValor ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		if (fecha != null) this.anio = cal.get(Calendar.YEAR);
		synchronized (sd1) {
			this.ejeX 			= sd1.format(fecha);
		}
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}

	public  ValorGraficable(String anio, BigDecimal mesTrimestreoSemestre, BigDecimal serieCantidad , Long serieValor , int tipoGrafico) {
		this(anio, mesTrimestreoSemestre, serieCantidad , BigDecimal.valueOf(serieValor) , tipoGrafico);
	}

	public  ValorGraficable(String anio, BigDecimal mesTrimestreoSemestre, BigDecimal serieCantidad , double serieValor , int tipoGrafico) {
		this(anio, mesTrimestreoSemestre, serieCantidad , BigDecimal.valueOf(serieValor) , tipoGrafico);
	}

	public  ValorGraficable(String anio, BigDecimal mesTrimestreoSemestre, BigDecimal serieCantidad , BigDecimal serieValor , int tipoGrafico) {
		if (anio != null) this.anio = new Integer(anio);
		switch (tipoGrafico) {
			case MENSUAL: this.ejeX 	= ""+anio+MINUS_LITERAL + String.format(FORMAT_2D_LITERAL, mesTrimestreoSemestre.intValue()) + DAY_PART_LITERAL;
				break;
			case TRIMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesTrimestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			case SEMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesSemestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			default:
				this.ejeX = String.valueOf(mesTrimestreoSemestre.intValue());
		}		
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}

	public  ValorGraficable(String anio, BigDecimal serieCantidad , Long serieValor) {
		this(anio, serieCantidad , BigDecimal.valueOf(serieValor));
	}

	public  ValorGraficable(String anio, BigDecimal serieCantidad , double serieValor) {
		this(anio, serieCantidad , BigDecimal.valueOf(serieValor));
	}

	public  ValorGraficable(String anio, BigDecimal serieCantidad , BigDecimal serieValor) {
		if (anio != null) this.anio = new Integer(anio);
		this.ejeX 			= ""+anio;
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}

	
	/**
	 * Utilizados para grafica de pagos y distribucion
	 * 
	 **/
	public  ValorGraficable(Date fecha, Long hora, BigDecimal serieCantidad,BigDecimal serieValor ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		if (fecha != null) this.anio = cal.get(Calendar.YEAR);
		synchronized(sd1) {
			this.ejeX 			= sd1.format(fecha);
		}
		this.hora			= hora==null?0:hora.intValue();
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}

	public  ValorGraficable(Date fecha, Integer hora, BigDecimal serieCantidad,BigDecimal serieValor ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		if (fecha != null) this.anio = cal.get(Calendar.YEAR);
		synchronized(sd1) {
			this.ejeX 			= sd1.format(fecha);
		}
		this.hora			= hora==null?0:hora.intValue();
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}

	public  ValorGraficable(String anio, BigDecimal mesTrimestreoSemestre,Long hora, BigDecimal serieCantidad , BigDecimal serieValor , int tipoGrafico) {
		if (anio != null) this.anio = new Integer(anio);
		switch (tipoGrafico) {
			case MENSUAL: this.ejeX 	= ""+anio+MINUS_LITERAL + String.format(FORMAT_2D_LITERAL, mesTrimestreoSemestre.intValue()) + DAY_PART_LITERAL;
				break;
			case TRIMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesTrimestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			case SEMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesSemestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			default:
				this.ejeX = String.valueOf(mesTrimestreoSemestre.intValue());
		}		
		this.hora			= hora==null?0:hora.intValue();
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}

	public  ValorGraficable(String anio, BigDecimal mesTrimestreoSemestre,Integer hora, BigDecimal serieCantidad , BigDecimal serieValor , int tipoGrafico) {
		if (anio != null) this.anio = new Integer(anio);
		switch (tipoGrafico) {
			case MENSUAL: this.ejeX 	= ""+anio+MINUS_LITERAL + String.format(FORMAT_2D_LITERAL, mesTrimestreoSemestre.intValue()) + DAY_PART_LITERAL;
				break;
			case TRIMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesTrimestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			case SEMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesSemestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			default:
				this.ejeX = String.valueOf(mesTrimestreoSemestre.intValue());
		}		
		this.hora			= hora==null?0:hora.intValue();
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}
	
	public  ValorGraficable(String anio, int hora, BigDecimal serieCantidad , BigDecimal serieValor) {
		if (anio != null) this.anio = new Integer(anio);
		this.hora			= hora;
		this.ejeX 			= ""+anio;
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}

	public  ValorGraficable(String anio, Long hora, BigDecimal serieCantidad , BigDecimal serieValor) {
		if (anio != null) this.anio = new Integer(anio);
		this.hora			= hora==null?0:hora.intValue();
		this.ejeX 			= ""+anio;
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}
	
	/** Utilizados  EN COMPENSACION CHEQUES**/
	public  ValorGraficable(String tipo, Date fecha, String ciudad, BigDecimal serieCantidad,BigDecimal serieValor ) {
		this.tipo=tipo;
		this.ciudad=ciudad;
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		if (fecha != null) this.anio = cal.get(Calendar.YEAR);
		synchronized(sd1) {
			this.ejeX 			= sd1.format(fecha);
		}
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}
	
	public  ValorGraficable(String tipo, String anio, BigDecimal mesTrimestreoSemestre, String ciudad, BigDecimal serieCantidad,BigDecimal serieValor, int tipoGrafico) {
		this.tipo=tipo;
		this.ciudad=ciudad;
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
		switch (tipoGrafico) {
			case MENSUAL: this.ejeX 	= ""+anio+MINUS_LITERAL + String.format(FORMAT_2D_LITERAL, mesTrimestreoSemestre.intValue()) + DAY_PART_LITERAL;
				break;
			case TRIMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesTrimestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			case SEMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesSemestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			default:
				this.ejeX = String.valueOf(mesTrimestreoSemestre.intValue());
		}	
	}
	
	public  ValorGraficable(String tipo,String anio, String ciudad, BigDecimal serieCantidad , BigDecimal serieValor) {
		this.tipo=tipo;
		this.ejeX = anio + DATE_PART_LITERAL;
		this.ciudad=ciudad;
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}
	
	public  ValorGraficable(String anio, BigDecimal serieCantidad , BigDecimal serieValor, String anual) {
		this.ejeX = anio + DATE_PART_LITERAL;
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
	}
	
	/**
	 * Metodos para Devolucion respecto al Canje
	 * @param fecha
	 * @param idcompensacion
	 * @param serieCantidad
	 * @param serieValor
	 */
	public  ValorGraficable(Date fecha, BigDecimal serieCantidad, BigDecimal serieCantidad2, BigDecimal serieValor, BigDecimal serieValor2 ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		if (fecha != null) this.anio = cal.get(Calendar.YEAR);
		synchronized(sd1) {
			this.ejeX 			= sd1.format(fecha);
		}
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
		this.serieValor2	= serieValor2;
		this.serieCantidad2	= serieCantidad2;
	}
	/**
	 * Metodos para Devolucion respecto al Canje por periodo diferente a Diario
	 * @param mensual - trimestre - Semestre
	 * @param mesTrimestreoSemestre
	 * @param serieCantidad
	 * @param serieCantidad2
	 * @param serieValor
	 * @param serieValor2
	 */
	public  ValorGraficable(String anio, BigDecimal mesTrimestreoSemestre, BigDecimal serieCantidad, BigDecimal serieCantidad2, BigDecimal serieValor, BigDecimal serieValor2, int tipoGrafico) {
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
		this.serieValor2		= serieValor2;
		this.serieCantidad2	= serieCantidad2;
		switch (tipoGrafico) {
			case MENSUAL: this.ejeX 	= ""+anio+MINUS_LITERAL + String.format(FORMAT_2D_LITERAL, mesTrimestreoSemestre.intValue()) + DAY_PART_LITERAL;
				break;
			case TRIMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesTrimestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			case SEMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesSemestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			default:
				this.ejeX = String.valueOf(mesTrimestreoSemestre.intValue());
		}	
	}
	
	/**
	 * Anual
	 * @param tipo
	 * @param anio
	 * @param ciudad
	 * @param serieCantidad
	 * @param serieValor
	 */
	public  ValorGraficable(String anio, BigDecimal serieCantidad , BigDecimal serieCantidad2, BigDecimal serieValor, BigDecimal serieValor2) {
		this.ejeX = anio + DATE_PART_LITERAL;
		this.serieValor		= serieValor;
		this.serieCantidad	= serieCantidad;
		this.serieValor2		= serieValor2;
		this.serieCantidad2	= serieCantidad2;
	}
	
	/**
	 * Metodos para Composicion del Canje por periodo
	 * @param mensual - trimestre - Semestre
	 * @param mesTrimestreoSemestre
	 * @param serieCantidad
	 * @param serieCantidad2
	 * @param serieValor
	 * @param serieValor2
	 */
	public  ValorGraficable(String anio, BigDecimal mesTrimestreoSemestre, BigDecimal serieCantidad, BigDecimal serieValor, 
			String IdRangoCanje, BigDecimal valorInicialRango, BigDecimal valorFinalRango,  int tipoGrafico) {
		this.serieValor			= serieValor;
		this.serieCantidad		= serieCantidad;
		this.IdRangoCanje 		= IdRangoCanje;
		this.valorInicialRango	= valorInicialRango;
		this.valorFinalRango	= valorFinalRango;
		
		switch (tipoGrafico) {
			case MENSUAL: this.ejeX 	= ""+anio+MINUS_LITERAL + String.format(FORMAT_2D_LITERAL, mesTrimestreoSemestre.intValue()) + DAY_PART_LITERAL;
				break;
			case TRIMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesTrimestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			case SEMESTRAL: this.ejeX = ""+anio +MINUS_LITERAL+ getMesSemestre(mesTrimestreoSemestre.intValue()) +DAY_PART_LITERAL ;
				break;
			default:
				this.ejeX = String.valueOf(mesTrimestreoSemestre.intValue());
		}	
	}
	
	/**
	 * Metodos para Composicion del Canje por periodo
	 * @param anual
	 * @param mesTrimestreoSemestre
	 * @param serieCantidad
	 * @param serieCantidad2
	 * @param serieValor
	 * @param serieValor2
	 */
	public  ValorGraficable(String anio, BigDecimal serieCantidad, BigDecimal serieValor, 
			String IdRangoCanje, BigDecimal valorInicialRango, BigDecimal valorFinalRango) {
		this.ejeX = anio + DATE_PART_LITERAL;
		this.serieValor			= serieValor;
		this.serieCantidad		= serieCantidad;
		this.IdRangoCanje 		= IdRangoCanje;
		this.valorInicialRango	= valorInicialRango;
		this.valorFinalRango	= valorFinalRango;
	}
	
	public  ValorGraficable(BigDecimal valorIPC, BigDecimal serieCantidad, BigDecimal serieValor, BigDecimal VariacionPor,String anio) {
		this.ejeX = anio + DATE_PART_LITERAL;
		this.serieValor			= serieValor;
		this.serieCantidad		= serieCantidad;
		this.valorIPC			= valorIPC;
		this.VariacionPor		= VariacionPor;
	}
	
	/**
	 * Obtiene trimestre de acuerdo al mes
	 * @param valor
	 * @return
	 */
	private String getMesTrimestre(int valor) {
		String result;
		switch (valor) {
			case 1: result= "01"; 
					break;
			case 2: result= "04"; 
			        break;
			case 3: result= "07";
			         break;
			case 4: result= "10";
			        break;
			default: result = "INVALIDO";
		}
		return result;
	}

	private String getMesSemestre(int valor) {
		String result;
		switch (valor) {
			case 1: result= "01"; 
			        break;
		    case 2: result= "07";
	                break;
		    default: result = "INVALIDO";			
		}
		return result;
	}

	public String getEjeX() {
		return ejeX;
	}

	public void setEjeX(String ejeX) {
		this.ejeX = ejeX;
	}

	public BigDecimal getSerieValor() {
		return serieValor;
	}

	public void setSerieValor(BigDecimal seriaValor) {
		this.serieValor = seriaValor;
	}
	
	public BigDecimal getSerieValor2() {
		return serieValor2;
	}

	public void setSerieValor2(BigDecimal seriaValor2) {
		this.serieValor2 = seriaValor2;
	}

	public BigDecimal getSerieCantidad() {
		return serieCantidad;
	}

	public void setSerieCantidad(BigDecimal serieCantidad) {
		this.serieCantidad = serieCantidad;
	}
	
	public BigDecimal getSerieCantidad2() {
		return serieCantidad2;
	}

	public void setSerieCantidad2(BigDecimal serieCantidad2) {
		this.serieCantidad2 = serieCantidad2;
	}
	
	@Override
	public String toString(){
		return MessageFormat.format("[x={0};cantidad={1};valor={2}]", ejeX,serieCantidad,serieValor);
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public Integer getAnio() {
		return anio==null?0:anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	} 
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ejeX == null) ? 0 : ejeX.hashCode());
		result = prime * result + ((serieValor == null) ? 0 : serieValor.hashCode());
		result = prime * result + ((serieCantidad == null) ? 0 : serieCantidad.hashCode());
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + (Integer.toString(hora).hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null)return false;
		if (getClass() != obj.getClass()) return false;
		if (!(obj instanceof ValorGraficable)) return false;
		ValorGraficable other = (ValorGraficable) obj;
		return (this.equals(other));
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public BigDecimal getSerieValorPorcentaje() {
		return serieValorPorcentaje;
	}

	public void setSerieValorPorcentaje(BigDecimal serieValorPorcentaje) {
		this.serieValorPorcentaje = serieValorPorcentaje;
	}

	public BigDecimal getSerieCantidadPorcentaje() {
		return serieCantidadPorcentaje;
	}

	public void setSerieCantidadPorcentaje(BigDecimal serieCantidadPorcentaje) {
		this.serieCantidadPorcentaje = serieCantidadPorcentaje;
	}

	public String getIdRangoCanje() {
		return IdRangoCanje;
	}

	public void setIdRangoCanje(String idRangoCanje) {
		IdRangoCanje = idRangoCanje;
	}

	public BigDecimal getValorInicialRango() {
		return valorInicialRango;
	}

	public void setValorInicialRango(BigDecimal valorInicialRango) {
		this.valorInicialRango = valorInicialRango;
	}

	public BigDecimal getValorFinalRango() {
		return valorFinalRango;
	}

	public void setValorFinalRango(BigDecimal valorFinalRango) {
		this.valorFinalRango = valorFinalRango;
	}

	public BigDecimal getValorIPC() {
		return valorIPC;
	}

	public void setValorIPC(BigDecimal valorIPC) {
		this.valorIPC = valorIPC;
	}

	public BigDecimal getVariacionPor() {
		return VariacionPor;
	}

	public void setVariacionPor(BigDecimal variacionPor) {
		VariacionPor = variacionPor;
	}
	
}
