package com.indico.jee.services;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.indico.jee.servicesi.ParametersService;

@Stateless
public class ParametersServiceImpl implements ParametersService {
	

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getCantidadDeDias() {
		return 10; 
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getCantidadDistribucionDias() {
		return 2; 
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getRangoMaximoDeDias() {
		return 30;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getRangoMaximoDeMeses() {
		return 24;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getRangoMaximoDeTrimestres() {
		return 20;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getRangoMaximoDeSemestres() {
		return 20;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getRangoMaximoDeAnios() {
		return 29;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public BigDecimal getRotacionDivisorPoCeroEn(){
		return new BigDecimal("0.0000001");
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public int getRotacionHoraDeCalculo() {
		return 7;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public BigDecimal getPIBDefault(){
		return new BigDecimal("1000000000");
	}
	
}