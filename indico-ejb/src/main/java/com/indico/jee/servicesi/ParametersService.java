package com.indico.jee.servicesi;

import java.math.BigDecimal;

import javax.ejb.Local;

@Local
public interface ParametersService {
	
	int getCantidadDeDias() ;
	
	int getCantidadDistribucionDias();
	
	int getRangoMaximoDeDias() ;

	int getRangoMaximoDeMeses() ;

	int getRangoMaximoDeTrimestres() ;

	int getRangoMaximoDeSemestres() ;

	int getRangoMaximoDeAnios() ;

	BigDecimal getRotacionDivisorPoCeroEn();
	
	public int getRotacionHoraDeCalculo() ;
	
	public BigDecimal getPIBDefault();

}