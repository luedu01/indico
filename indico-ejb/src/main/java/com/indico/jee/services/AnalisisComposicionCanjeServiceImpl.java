package com.indico.jee.services;

import static com.indico.jee.util.Constants.FECHA_FIN_LITERAL;
import static com.indico.jee.util.Constants.FECHA_INICIO_LITERAL;
import static com.indico.jee.util.Constants.NUMERO_DECIMALES;
import static com.indico.jee.util.Constants.UNCHECKED_LITERAL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.indico.jee.modelo.AnalisisComposicionCanje;
import com.indico.jee.servicesi.AnalisisComposicionCanjeService;
import com.indico.jee.util.ValorGraficable;

@Stateless
public class AnalisisComposicionCanjeServiceImpl extends GeneralServiceImpl implements AnalisisComposicionCanjeService {
	
private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AnalisisChequesCompensaServiceImpl.class);
	
	public AnalisisComposicionCanjeServiceImpl(){
		logger.info("Created AnalisisComposicionCanjeServiceImpl");
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Date getLastDate() {
		Date res = null; 
		try{
			res = getEm().createNamedQuery("AnalisisComposicionCanje.getLastDate",Date.class).getSingleResult();
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return res;
	}
	
		
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<AnalisisComposicionCanje> finAll() {
		List<AnalisisComposicionCanje> res = null;
		try{
			res = getEm().createNamedQuery("AnalisisComposicionCanje.findAll",AnalisisComposicionCanje.class).getResultList();
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return res;
	}
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getComposicionCanjeAll(String idPeriodo) {
		
		List<ValorGraficable> valores = null;
		
		switch(idPeriodo) {
			case("2"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeMensualAll").getResultList();
				break;
			case("3"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeTrimestralAll").getResultList();
				break;
			case("4"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeSemestralAll").getResultList();
				break;
			case("5"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeAnualAll").getResultList();
				break;
		}
		
				
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())).multiply(new BigDecimal(100)));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor(), totalesValor.get(vg.getEjeX())).multiply(new BigDecimal(100)));
			}
		}
		
		return valores;
	}
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getComposicionCanjeByRango(String idPeriodo, String[] idRangoCanje) {
		
		List<ValorGraficable> valores = null;
		List<String> rangos = Arrays.asList(idRangoCanje);
		switch(idPeriodo) {
			case("2"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeMensualByRango")
					.setParameter("idRangoCanje", rangos)
					.getResultList();
				break;
			case("3"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeTrimestralByRango")
					.setParameter("idRangoCanje", rangos)
					.getResultList();
				break;
			case("4"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeSemestralByRango")
					.setParameter("idRangoCanje", rangos)
					.getResultList();
				break;
			case("5"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisComposicionCanje.getComposicionCanjeAnualByRango")
					.setParameter("idRangoCanje", rangos)
					.getResultList();
				break;
		}
		
				
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())).multiply(new BigDecimal(100)));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor(), totalesValor.get(vg.getEjeX())).multiply(new BigDecimal(100)));
			}
		}
		
		return valores;
	}
	
	private BigDecimal getaPartWithOutPercent(BigDecimal numerador,BigDecimal denominador) {
		if (numerador==null || denominador==null || BigDecimal.ZERO.compareTo(numerador)==0 || BigDecimal.ZERO.compareTo(denominador)==0 ) {
			return BigDecimal.ZERO; 
		}
		return numerador.divide(denominador,NUMERO_DECIMALES, RoundingMode.HALF_UP);
	}
	
/* Export Data */
	
	@Override
	public List<AnalisisComposicionCanje> getDataDiarioCompoCanjeTodas(int fechaInicial, int fechaFinal , int tipo) {
		List<AnalisisComposicionCanje> valores = new ArrayList<AnalisisComposicionCanje>();
		valores = (List<AnalisisComposicionCanje>)getEm().createNamedQuery("AnalisisComposicionCanje.getDataDiarioCompoCanjeTodas",AnalisisComposicionCanje.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.getResultList();
		return valores;
	}
	
	@Override
	public List<AnalisisComposicionCanje> getDataDiarioCompoCanje(int fechaInicial, int fechaFinal, String[] rangosel , int tipo) {
		List<AnalisisComposicionCanje> valores = new ArrayList<AnalisisComposicionCanje>();
		List<String> rangos = Arrays.asList(rangosel);
		valores = (List<AnalisisComposicionCanje>)getEm().createNamedQuery("AnalisisComposicionCanje.getDataDiarioCompoCanje",AnalisisComposicionCanje.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.setParameter("idRangoCanje", rangos)	
				.getResultList();
		return valores;
	}
}
