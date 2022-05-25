package com.indico.jee.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.indico.jee.modelo.AnalisisChequesCompensa;
import com.indico.jee.servicesi.AnalisisChequesCompensaService;
import com.indico.jee.util.ValorGraficable;
import static com.indico.jee.util.Constants.*;

@Stateless
public class AnalisisChequesCompensaServiceImpl extends GeneralServiceImpl implements AnalisisChequesCompensaService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AnalisisChequesCompensaServiceImpl.class);
	
	public AnalisisChequesCompensaServiceImpl(){
		logger.info("Created AnalisisChequesCompensaServiceImpl");
	}

	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Date getLastDate() {
		Date res = null; 
		try{
			res = getEm().createNamedQuery("AnalisisChequesCompensa.getLastDate",Date.class).getSingleResult();
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return res;
	}

		
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<AnalisisChequesCompensa> finAll() {
		
		List<AnalisisChequesCompensa> res = null;
		try{
			res = getEm().createNamedQuery("AnalisisChequesCompensa.findAll",AnalisisChequesCompensa.class).getResultList();
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return res;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public BigInteger countAll() {
		
		BigInteger resultado=BigInteger.ZERO;
		try{
			resultado = new BigInteger(((Long)getEm().createNamedQuery("AnalisisChequesCompensa.countAll").getSingleResult()).toString());
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return resultado;
	}


	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Map<String,String> getCiudadesCanje(String medio) {
		
		Map<String,String>  resultado=new HashMap<String,String>() ;
		try{
			@SuppressWarnings(UNCHECKED_LITERAL)
			List<Object[]> res = (List<Object[]>)getEm().createNamedQuery("AnalisisChequesCompensa.getCiudadesCanje").setParameter(MEDIO_LITERAL, medio).getResultList();
			if (res!=null) { 
				for(Object[] val : res) {
					resultado.put(val[0].toString() , val[1].toString());
				}
			}
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return resultado;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Map<String,String> getCiudadesDevol(String medio) {
		
		Map<String,String>  resultado=new HashMap<String,String>() ;
		try{
			@SuppressWarnings(UNCHECKED_LITERAL)
			List<Object[]> res = (List<Object[]>)getEm().createNamedQuery("AnalisisChequesCompensa.getCiudadesDevol").setParameter(MEDIO_LITERAL, medio).getResultList();
			if (res!=null) { 
				for(Object[] val : res) {
					resultado.put(val[0].toString() , val[1].toString());
				}
			}
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		return resultado;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDiarioGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDiarioGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDiariaAllGR(String medio) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDiariaAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
				
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenMensualGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenMensualGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenMensualAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenMensualAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}


	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenTrimestralGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenTrimestralGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenTrimestralAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenTrimestralAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
			
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenSemestralGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenSemestralGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenSemestralAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenSemestralAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenAnualGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenAnualGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenAnualAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenAnualAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
			
			if (medio!=null && medio.equalsIgnoreCase("MUL")) {
				Collections.sort(valores, new Comparator<ValorGraficable>() {
				    @Override
				    public int compare(ValorGraficable vg1, ValorGraficable vg2) {
				        return vg2.getSerieValorPorcentaje().compareTo(vg1.getSerieValorPorcentaje());
				    }
				});
			}
			
		}
		return valores;
	}

	/**
	 * Métodos de las devoluciones de cheques
	 */
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolDiarioGR() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolDiarioGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolDiariaAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolDiariaAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolMensualGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolMensualGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolMensualAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolMensualAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolTrimestralGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolTrimestralGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolTrimestralAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolTrimestralAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolSemestralGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolSemestralGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolSemestralAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolSemestralAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolAnualGR() {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolAnualGR").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getCompenDevolAnualAllGR(String medio) {
		
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getCompenDevolAnualAllGR")
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValores = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValores.put(vg.getEjeX(), totalesValores.containsKey(vg.getEjeX()) ? totalesValores.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(vg.getSerieValor().divide(DIVISORMM), totalesValores.get(vg.getEjeX()).divide(DIVISORMM)));
			}
		}
		return valores;
	}
	
	/**
	 * Métodos de componsesacion de cheques
	 */
	@Override
	public List<AnalisisChequesCompensa> getDataDiarioDevolTodas(Date fechaInicial, Date fechaFinal , int tipo) {
		List<AnalisisChequesCompensa> valores = new ArrayList<AnalisisChequesCompensa>();
		valores = (List<AnalisisChequesCompensa>)getEm().createNamedQuery("AnalisisChequesCompensa.getDataDiarioDevolTodas",AnalisisChequesCompensa.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.getResultList();
		return valores;
	}

	@Override
	public List<AnalisisChequesCompensa> getDataDiarioDevol(Date fechaInicial, Date fechaFinal,String medio , int tipo) {
		List<AnalisisChequesCompensa> valores = new ArrayList<AnalisisChequesCompensa>();
		valores = (List<AnalisisChequesCompensa>)getEm().createNamedQuery("AnalisisChequesCompensa.getDataDiarioDevol",AnalisisChequesCompensa.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		return valores;
	}

	@Override
	public List<AnalisisChequesCompensa> getDataDiarioCompenTodas(Date fechaInicial, Date fechaFinal , int tipo) {
		List<AnalisisChequesCompensa> valores = new ArrayList<AnalisisChequesCompensa>();
		valores = (List<AnalisisChequesCompensa>)getEm().createNamedQuery("AnalisisChequesCompensa.getDataDiarioCompenTodas",AnalisisChequesCompensa.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.getResultList();
		return valores;
	}

	@Override
	public List<AnalisisChequesCompensa> getDataDiarioCompen(Date fechaInicial, Date fechaFinal,String medio , int tipo) {
		List<AnalisisChequesCompensa> valores = new ArrayList<AnalisisChequesCompensa>();
		valores = (List<AnalisisChequesCompensa>)getEm().createNamedQuery("AnalisisChequesCompensa.getDataDiarioCompen",AnalisisChequesCompensa.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
		return valores;
	}	
	

	private BigDecimal getaPartWithOutPercent(BigDecimal numerador,BigDecimal denominador) {
		if (numerador==null || denominador==null || BigDecimal.ZERO.compareTo(numerador)==0 || BigDecimal.ZERO.compareTo(denominador)==0 )return BigDecimal.ZERO; 
		return numerador.divide(denominador,NUMERO_DECIMALES_CANJE, RoundingMode.HALF_UP);
	}

	/**
	 * Metodos de Comportamiento Canjes
	 */
	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	public List<ValorGraficable> getComportamientoCanjeAll(String tipodeplaza, String ciudadT,String periodo) {
				
		List<ValorGraficable> valores = null;
		String medio;
		String ciudad;
		
		if(tipodeplaza.compareTo(TODAS_LITERAL)==0) {
			medio = COMODINSQL;
		}else {
			medio = tipodeplaza;
		}
		if(ciudadT.compareTo(TODAS_LITERAL)==0) {
			ciudad = COMODINSQL;
		}else {
			ciudad = ciudadT;
		}
		
		switch(periodo) {
			case("1"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoCanjeDiarioAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("2"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoCanjeMensualAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("3"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoCanjeTrimestralAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("4"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoCanjeSemestralAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("5"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoCanjeAnualAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
		}
		return valores;
	}
	
	
	/**
	 * Metodos de Devoluciones
	 */
	
	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	public List<ValorGraficable> getComportamientoDevolucionAll(String tipodeplaza, String ciudadT,String periodo) {
				
		List<ValorGraficable> valores = null;
		String medio;
		String ciudad;
		
		if(tipodeplaza.compareTo(TODAS_LITERAL)==0) {
			medio = COMODINSQL;
		}else {
			medio = tipodeplaza;
		}
		if(ciudadT.compareTo(TODAS_LITERAL)==0) {
			ciudad = COMODINSQL;
		}else {
			ciudad = ciudadT;
		}
		
		switch(periodo) {
			case("1"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoDevolDiarioAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("2"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoDevolMensualAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("3"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoDevolTrimestralAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("4"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoDevolSemestralAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
			case("5"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getComportamientoDevolAnualAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.setParameter(CIUDAD_LITERAL, ciudad)	
						.getResultList();
				break;
		}
		return valores;
	}
	
	/**
	 * Metodos de Devolucion Canje
	 */
	
	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	public List<ValorGraficable> getDevolucionCanjeAll(String tipodeplaza, String periodo) {
				
		List<ValorGraficable> valores = null;
		String medio;
		
		if(tipodeplaza.compareTo(TODAS_LITERAL)==0) {
			medio = COMODINSQL;
		}else {
			medio = tipodeplaza;
		}
		
		switch(periodo) {
			case("1"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getDevolucionCanjeDiarioAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.getResultList();
				break;
			case("2"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getDevolucionCanjeMensualAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.getResultList();
				break;
			case("3"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getDevolucionCanjeTrimestralAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.getResultList();
				break;
			case("4"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getDevolucionCanjeSemestralAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.getResultList();
				break;
			case("5"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesCompensa.getDevolucionCanjeAnualAll")
						.setParameter(MEDIO_LITERAL, medio)	
						.getResultList();
				break;
		}
		
		if (valores!=null) { 
			Map<String,BigDecimal> valoresSesion2 = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> cantidadSesion2 = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				cantidadSesion2.put(vg.getEjeX(), cantidadSesion2.containsKey(vg.getEjeX()) ? cantidadSesion2.get(vg.getEjeX()).add(vg.getSerieCantidad2()) : vg.getSerieCantidad2());
				valoresSesion2.put(vg.getEjeX(), valoresSesion2.containsKey(vg.getEjeX()) ? valoresSesion2.get(vg.getEjeX()).add(vg.getSerieValor2()) : vg.getSerieValor2());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidadPorcentaje(getaPartWithOutPercent(cantidadSesion2.get(vg.getEjeX()), vg.getSerieCantidad()).multiply(BigDecimal.valueOf(100)));
				vg.setSerieValorPorcentaje(getaPartWithOutPercent(valoresSesion2.get(vg.getEjeX()),vg.getSerieValor()).multiply(BigDecimal.valueOf(100)));
			}
		}
		
		return valores;
	}
		
	/* Export Data */
	
	@Override
	public List<AnalisisChequesCompensa> getDataDiarioDevolCanjeTodas(Date fechaInicial, Date fechaFinal , int tipo) {
		return (List<AnalisisChequesCompensa>)getEm().createNamedQuery("AnalisisChequesCompensa.getDataDiarioDevolCanjeTodas",AnalisisChequesCompensa.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.getResultList();
	}
	
	@Override
	public List<AnalisisChequesCompensa> getDataDiarioDevolCanje(Date fechaInicial, Date fechaFinal, String medio , int tipo) {
		return (List<AnalisisChequesCompensa>)getEm().createNamedQuery("AnalisisChequesCompensa.getDataDiarioDevol",AnalisisChequesCompensa.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.setParameter(MEDIO_LITERAL, medio)	
				.getResultList();
	}
	
}


