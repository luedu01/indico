package com.indico.jee.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	 @NamedQuery(name = "AnalisisTransaccionCNI.getDatadiarioTransaccionCNI", query = "					" +
			  	" SELECT o FROM AnalisisTransaccionCNI o 							" +
			  	" WHERE o.fechaTransaccion BETWEEN :fechaInicio AND :fechaFin  		" +
			  	" ORDER BY o.fechaTransaccion ASC"
			),
	 @NamedQuery(name = "AnalisisTransaccionCNI.getTransaccionCNIDiarioAll", query = "					 " +
			  	" SELECT new com.indico.jee.util.ValorGraficable(o.fechaTransaccion						," +
			  	"												 SUM(o.numTransacciones)				," +
			  	"												 SUM(o.valorTransaccion)/1000000000				 " +
			  	"												) 										 " +  
			  	" FROM AnalisisTransaccionCNI o 														 " +
			  	" GROUP BY o.fechaTransaccion 															 " +
			  	" ORDER BY o.fechaTransaccion ASC 														 " 
		),
	@NamedQuery(name = "AnalisisTransaccionCNI.getTransaccionCNIMensualAll", query = "				 	" +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												o.idMes										," +
			"												SUM(o.numTransacciones)						," +
		  	"												SUM(o.valorTransaccion)/1000000000	 		," +
			"												0											 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisTransaccionCNI o 														 	 " +
		  	" GROUP BY o.idAnio, o.idMes																 " +
		  	" ORDER BY o.idAnio, o.idMes ASC															 "
		),
	@NamedQuery(name = "AnalisisTransaccionCNI.getTransaccionCNITrimestralAll", query = "				 " +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												o.idTrimestre								," +
			"												SUM(o.numTransacciones)						," +
		  	"												SUM(o.valorTransaccion)/1000000000	 		," +
			"												1											 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisTransaccionCNI o 														 	 " +
		  	" GROUP BY o.idAnio, o.idTrimestre															 " +
		  	" ORDER BY o.idAnio, o.idTrimestre ASC														 "
		),
	@NamedQuery(name = "AnalisisTransaccionCNI.getTransaccionCNISemestralAll", query = "				 " +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												o.idSemestre								," +
			"												SUM(o.numTransacciones)						," +
		  	"												SUM(o.valorTransaccion)/1000000000	 		," +
			"												2											 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisTransaccionCNI o 														 	 " +
		  	" GROUP BY o.idAnio, o.idSemestre															 " +
		  	" ORDER BY o.idAnio, o.idSemestre ASC														 "
		),
	@NamedQuery(name = "AnalisisTransaccionCNI.getTransaccionCNIAnualAll", query = "					 " +
			" SELECT new com.indico.jee.util.ValorGraficable(o.idAnio									," +
			"												SUM(o.numTransacciones)						," +
		  	"												SUM(o.valorTransaccion)/1000000000	 		," + 
		  	"												'anual'										 " + 
		  	"												) 											 " +  
		  	" FROM AnalisisTransaccionCNI o 														 	 " +
		  	" GROUP BY o.idAnio																			 " +
		  	" ORDER BY o.idAnio ASC																		 "
		),
})
@Table(name ="C_CNIAnalisisTransaccion")

public class AnalisisTransaccionCNI  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Id
	@Column(name = "Fecha_Transaccion")
    private Date fechaTransaccion;
	
	@Column(name = "Id_Anio")
    private String idAnio;
	
	@Column(name = "Id_Semestre")
    private BigDecimal idSemestre;
    
	@Column(name = "Id_Trimestre")
    private BigDecimal idTrimestre;
    
	@Column(name = "Id_Mes")
    private BigDecimal idMes;
	
	@Column(name = "Num_Transacciones")
    private BigDecimal numTransacciones;

    @Column(name = "Cv_MontoTransaccion")
    private BigDecimal valorTransaccion;

	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public BigDecimal getIdSemestre() {
		return idSemestre;
	}

	public void setIdSemestre(BigDecimal idSemestre) {
		this.idSemestre = idSemestre;
	}

	public BigDecimal getIdTrimestre() {
		return idTrimestre;
	}

	public void setIdTrimestre(BigDecimal idTrimestre) {
		this.idTrimestre = idTrimestre;
	}

	public BigDecimal getIdMes() {
		return idMes;
	}

	public void setIdMes(BigDecimal idMes) {
		this.idMes = idMes;
	}

	public BigDecimal getNumTransacciones() {
		return numTransacciones;
	}

	public void setNumTransacciones(BigDecimal numTransacciones) {
		this.numTransacciones = numTransacciones;
	}

	public BigDecimal getValorTransaccion() {
		return valorTransaccion;
	}

	public void setValorTransaccion(BigDecimal valorTransaccion) {
		this.valorTransaccion = valorTransaccion;
	}

	public String getIdAnio() {
		return idAnio;
	}

	public void setIdAnio(String idAnio) {
		this.idAnio = idAnio;
	}
}
