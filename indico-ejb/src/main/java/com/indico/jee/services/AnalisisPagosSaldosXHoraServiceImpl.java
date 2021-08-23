package com.indico.jee.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.indico.jee.modelo.AnalisisPagosSaldosXHora;
import com.indico.jee.modelo.ValorAnualIPIB;
import com.indico.jee.servicesi.AnalisisPagosSaldosXHoraService;
import com.indico.jee.servicesi.ParametersService;
import com.indico.jee.servicesi.ValorAnualIPIBService;
import com.indico.jee.util.ValorGraficable;
import static com.indico.jee.util.Constants.*;

@Stateless
public class AnalisisPagosSaldosXHoraServiceImpl extends GeneralServiceImpl implements AnalisisPagosSaldosXHoraService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AnalisisPagosSaldosXHoraServiceImpl.class);
	
	//numero_decimales = 10
	private static final int NUMERO_DECIMALES = 2;

	
	private static final String EXPORTAR_PATH_LITERAL = "AnalisisPagosSaldosXHora.getDetalleExportar";
	
	@EJB
	private ParametersService parametersService;

	@EJB
	private ValorAnualIPIBService valorAnualIPIBService;

	
	public AnalisisPagosSaldosXHoraServiceImpl(){
		logger.info("Created AnalisisPagosSaldosXHoraService");
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Date getLastDate() {
		Date res = null; 
		res = getEm().createNamedQuery("AnalisisPagosSaldosXHora.getLastDate",Date.class).getSingleResult();
		return res;
	}
	
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<AnalisisPagosSaldosXHora> finAll() { 
		return getEm().createNamedQuery("AnalisisPagosSaldosXHora.findAll",AnalisisPagosSaldosXHora.class).getResultList();
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public BigInteger countAll() {
		return new BigInteger(((Long)getEm().createNamedQuery("AnalisisPagosSaldosXHora.countAll").getSingleResult()).toString());

	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getAllDiario(Date desde, Date hasta, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
			valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getAllDiario")
					.setParameter(DESDE_LITERAL, desde)
					.setParameter(HASTA_LITERAL, getFechaInicial(hasta,1))
					.setFirstResult(posinicial)
					.setMaxResults(cantregistros)
					.getResultList();
			//calcula en terminos de porcentaje
			BigDecimal totalValor	= BigDecimal.ZERO; 
			BigDecimal totalCantidad= BigDecimal.ZERO;
			if (valores!=null && !valores.isEmpty()) {
				//suma todos los valores
				for (ValorGraficable val : valores) {
					totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
					totalCantidad = totalCantidad.add(val.getSerieCantidad()==null?BigDecimal.ZERO:val.getSerieCantidad());
				}//for
				//actualiza los valores de cada cantidad y de cada valor
				for (ValorGraficable val : valores) {
					val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
					val.setSerieCantidad((val.getSerieCantidad()==null?BigDecimal.ZERO:(val.getSerieCantidad().divide(totalCantidad,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
					
					if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
					if ((val.getSerieCantidad().compareTo(new BigDecimal("100"))>0)) val.setSerieCantidad(new BigDecimal("100")); 
				}//for
			}//if
		return valores;
	}

	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoDiario(Date desde, Date hasta, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
			valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoDiario")
					.setParameter(DESDE_LITERAL, desde)
					.setParameter(HASTA_LITERAL, hasta)
					.setFirstResult(posinicial)
					.setMaxResults(cantregistros)
					.getResultList();
		return valores;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoDiarioCantidadSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoDiarioSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidad(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
			}
		}
		return valores;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoDiarioValorSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoDiarioValorSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieValor(getaPartWithOutPercent(vg.getSerieValor(), totalesValor.get(vg.getEjeX())));
			}
		}
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getAllMensual(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getAllMensual")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoMensual(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = new ArrayList<ValorGraficable>();
		//PERIODO 1
		List<ValorGraficable> p1  = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensual")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, getFechaFinal(desde,2))
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		//PERIODO 2
		List<ValorGraficable> p2 = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensual")
				.setParameter(DESDE_LITERAL, getFechaInicial(hasta,2))
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList()
				;
		if (p1!=null) {
			valores.addAll(p1);
		}
		if (p2!=null) {
			valores.addAll(p2);
		}
		//calcula en terminos de porcentaje
		BigDecimal totalValor	= BigDecimal.ZERO; 
		BigDecimal totalCantidad= BigDecimal.ZERO;
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
				totalCantidad = totalCantidad.add(val.getSerieCantidad()==null?BigDecimal.ZERO:val.getSerieCantidad());
			}//fornew ArrayList<ValorGraficable>()
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				val.setSerieCantidad((val.getSerieCantidad()==null?BigDecimal.ZERO:(val.getSerieCantidad().divide(totalCantidad,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
				if ((val.getSerieCantidad().compareTo(new BigDecimal("100"))>0)) val.setSerieCantidad(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoMensualCantidadSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensualSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidad(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
			}
		}
		return valores;
	}


	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoMensualValorSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensualSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieValor(getaPartWithOutPercent(vg.getSerieValor(), totalesValor.get(vg.getEjeX())));
			}
		}
		return valores;
	}	
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getAllTrimestral(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getAllTrimestral")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoTrimestral(Date desde, Date hasta, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		//PERIODO 1
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestral")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, getFechaFinal(desde,3))
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		//PERIODO 2
		if (valores==null) valores = new ArrayList<ValorGraficable>();
		List<ValorGraficable> vll = ((List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestral")
				.setParameter(DESDE_LITERAL, getFechaInicial(hasta,3))
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList());
		if (vll!=null) valores.addAll(vll);
		//calcula en terminos de porcentaje
		BigDecimal totalValor	= BigDecimal.ZERO; 
		BigDecimal totalCantidad= BigDecimal.ZERO;
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
				totalCantidad = totalCantidad.add(val.getSerieCantidad()==null?BigDecimal.ZERO:val.getSerieCantidad());
			}//for
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				val.setSerieCantidad((val.getSerieCantidad()==null?BigDecimal.ZERO:(val.getSerieCantidad().divide(totalCantidad,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
				if ((val.getSerieCantidad().compareTo(new BigDecimal("100"))>0)) val.setSerieCantidad(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoTrimestralCantidadSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestralSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidad(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
			}
		}
		return valores;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoTrimestralValorSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestralSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieValor(getaPartWithOutPercent(vg.getSerieValor(), totalesValor.get(vg.getEjeX())));
			}
		}
		return valores;
	}		

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getAllSemestral(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getAllSemestral")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}


	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoSemestral(Date desde, Date hasta, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		//PERIODO 1
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestral")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, getFechaFinal(desde,4))
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		//PERIODO 2
		if (valores==null) valores = new ArrayList<ValorGraficable>();
		List<ValorGraficable> vll = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestral")
				.setParameter(DESDE_LITERAL, getFechaInicial(hasta,4))
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		if (vll!=null) valores.addAll(vll);
		//calcula en terminos de porcentaje
		BigDecimal totalValor	= BigDecimal.ZERO; 
		BigDecimal totalCantidad= BigDecimal.ZERO;
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
				totalCantidad = totalCantidad.add(val.getSerieCantidad()==null?BigDecimal.ZERO:val.getSerieCantidad());
			}//for
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				val.setSerieCantidad((val.getSerieCantidad()==null?BigDecimal.ZERO:(val.getSerieCantidad().divide(totalCantidad,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
				if ((val.getSerieCantidad().compareTo(new BigDecimal("100"))>0)) val.setSerieCantidad(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoSemestralCantidadSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestralSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidad(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
			}
		}
		return valores;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoSemestralValorSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestralSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieValor(getaPartWithOutPercent(vg.getSerieValor(), totalesValor.get(vg.getEjeX())));
			}
		}
		return valores;
	}			
	
	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getAllAnual(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getAllAnual")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}

	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoAnual(Date desde, Date hasta, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = new ArrayList<ValorGraficable>();
		//PERIODO 1
		List<ValorGraficable> p1 = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnual")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, getFechaFinal(desde,5))
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		//PERIODO 2
		List<ValorGraficable> p2 = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnual")
				.setParameter(DESDE_LITERAL, getFechaInicial(hasta,5))
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		if (p1!=null) {
			valores.addAll(p1);
		}
		if (p2!=null) {
			valores.addAll(p2);
		}
		//calcula en terminos de porcentaje
		BigDecimal totalValor	= BigDecimal.ZERO; 
		BigDecimal totalCantidad= BigDecimal.ZERO;
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
				totalCantidad = totalCantidad.add(val.getSerieCantidad()==null?BigDecimal.ZERO:val.getSerieCantidad());
			}//for
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				val.setSerieCantidad((val.getSerieCantidad()==null?BigDecimal.ZERO:(val.getSerieCantidad().divide(totalCantidad,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
				
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
				if ((val.getSerieCantidad().compareTo(new BigDecimal("100"))>0)) val.setSerieCantidad(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoAnualCantidadSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnualSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieCantidad(getaPartWithOutPercent(vg.getSerieCantidad(), totalesCantidad.get(vg.getEjeX())));
			}
		}
		return valores;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoAnualValorSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnualSG").getResultList();
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			//construye los movimientos diarios
			for (ValorGraficable vg: valores) {
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			//calcula el porcentaje de los valores.
			for (ValorGraficable vg: valores) {
				vg.setSerieValor(getaPartWithOutPercent(vg.getSerieValor(), totalesValor.get(vg.getEjeX())));
			}
		}
		return valores;
	}			
	
	

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<AnalisisPagosSaldosXHora> getDetalleExportarPeriodo(Date desde, Date hasta, int tipo) {
		List<AnalisisPagosSaldosXHora> valores = new ArrayList<AnalisisPagosSaldosXHora>();
		//PERIODO 1 - CALCULAR FECHA FINAL
		List<AnalisisPagosSaldosXHora> p1 = new ArrayList<AnalisisPagosSaldosXHora>();
		List<AnalisisPagosSaldosXHora> p2 = new ArrayList<AnalisisPagosSaldosXHora>();
		
		p1 = getEm().createNamedQuery(EXPORTAR_PATH_LITERAL)
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, getFechaFinal(desde,tipo))
				.getResultList();
		//PERIODO 1 - CALCULAR FECHA INICIAL
		p2 = getEm().createNamedQuery(EXPORTAR_PATH_LITERAL)
				.setParameter(DESDE_LITERAL, getFechaInicial(hasta,tipo))
				.setParameter(HASTA_LITERAL, hasta)
				.getResultList();
		
		if (p1!=null && !p1.isEmpty()) {
			valores.addAll(p1);
		}
		if (p2!=null && !p2.isEmpty()) {
			valores.addAll(p2);
		}
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		Calendar fecha = Calendar.getInstance();
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (AnalisisPagosSaldosXHora val : valores) {
				fecha.setTime(val.getPk().getFechaValor());
				BigDecimal valorPIB = pibs.get( fecha.get(Calendar.YEAR) -1);
				val.setPib(valorPIB==null?defaultPIB:valorPIB);
			}//for
		}//if
		return valores;
	}
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<AnalisisPagosSaldosXHora> getDetalleExportarDesdeHasta(Date desde, Date hasta, int tipo) {
		List<AnalisisPagosSaldosXHora> valores = null;
		//PERIODO 1 - CALCULAR FECHA FINAL
		valores = (List<AnalisisPagosSaldosXHora>)getEm().createNamedQuery(EXPORTAR_PATH_LITERAL)
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		Calendar fecha = Calendar.getInstance();
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (AnalisisPagosSaldosXHora val : valores) {
				fecha.setTime(val.getPk().getFechaValor());
				BigDecimal valorPIB = pibs.get( fecha.get(Calendar.YEAR) -1);
				val.setPib(valorPIB==null?defaultPIB:valorPIB);
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getDetalleExportarRotaDesdeHastaVG(Date desde, Date hasta) {
		List<ValorGraficable> valores = new ArrayList<ValorGraficable>();
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getDetalleExportarRotaDesdeHastaVG")
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getDetalleExportarPibDesdeHastaVG(Date desde, Date hasta) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getDetalleExportarPibDesdeHastaVG")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	
	/*Calcula la fecha inicial a partir de la fecha final*/
	private Date getFechaInicial(Date fechaFinal,int tipo) {
		Calendar calF = Calendar.getInstance();
		calF.setTime(fechaFinal);
		calF.set(Calendar.HOUR_OF_DAY		, 0);
		calF.set(Calendar.MINUTE			, 0);
		calF.set(Calendar.SECOND			, 0);
		calF.set(Calendar.MILLISECOND		, 0);
		if (tipo==2) {
			calF.setTime(fechaFinal);
			calF.set(Calendar.DAY_OF_MONTH	,1);
		} else if (tipo==3) {
			calF.setTime(fechaFinal);
			calF.set(Calendar.DAY_OF_MONTH	, 1);
			calF.add(Calendar.MONTH			, -2);
		} else if (tipo==4) {
			calF.setTime(fechaFinal);
			calF.set(Calendar.DAY_OF_MONTH	, 1);
			calF.add(Calendar.MONTH			,-5);
		}else if (tipo==5) {
			calF.setTime(fechaFinal);
			calF.set(Calendar.DAY_OF_MONTH	, 1);
			calF.add(Calendar.YEAR			, -1);
		}
		return calF.getTime(); 
	}
	

	/*Calcula la fecha final a partir de la fecha inicial*/
	private Date getFechaFinal(Date fechaInicial,int tipo) {
		Calendar calInit = Calendar.getInstance();
		calInit.setTime(fechaInicial);
		calInit.set(Calendar.HOUR_OF_DAY	, 23);
		calInit.set(Calendar.MINUTE			, 59);
		calInit.set(Calendar.SECOND			, 59);
		calInit.set(Calendar.MILLISECOND	, 999);
		if (tipo==2) {
			calInit.set(Calendar.DAY_OF_MONTH	, calInit.getActualMaximum(Calendar.DAY_OF_MONTH));

		} else if (tipo==3) {
			calInit.set(Calendar.DAY_OF_MONTH	, 1);
			calInit.add(Calendar.MONTH			, 2);
			calInit.set(Calendar.DAY_OF_MONTH	, calInit.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (tipo==4) {
			calInit.set(Calendar.DAY_OF_MONTH	, 1);
			calInit.add(Calendar.MONTH			, 5); 
			calInit.set(Calendar.DAY_OF_MONTH	, calInit.getActualMaximum(Calendar.DAY_OF_MONTH));
		}else if (tipo==5) {
			calInit.set(Calendar.DAY_OF_MONTH	, 1);
			calInit.add(Calendar.YEAR			, 1); 
			calInit.set(Calendar.DAY_OF_MONTH	, calInit.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return calInit.getTime(); 
	}

	/**
	 * @see com.indico.jee.servicesi.AnalisisPagosSaldosXHoraService#lastMovementsDaily()
	 */
	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> lastMovementsDaily() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisPagosSaldosXHora.lastMovementsDaily")
				.setFirstResult(0)
				.setMaxResults(2)
				.getResultList();
		if(valores!=null) Collections.reverse(valores);
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoDiarioRotacion(Date desde, Date hasta , 	int posinicial, int cantregistros, int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoDiarioRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//calcula en terminos de porcentaje
		BigDecimal totalValor	= BigDecimal.ZERO; 
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
			}//for
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))).multiply(new BigDecimal(100)));
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoDiarioRotacionSG() {
		return  (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoDiarioRotacionSG")
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
	}
	
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoMensualRotacion(Date desde, Date hasta, int posinicial, int cantregistros,int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensualRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//calcula en terminos de porcentaje
		BigDecimal totalValor	= BigDecimal.ZERO; 
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
			}//for
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))).multiply(new BigDecimal(100)));
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoMensualRotacionSG() {
		return  (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensualRotacionSG")
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
	}
	
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoTrimestralRotacion(Date desde, Date hasta, int posinicial,int cantregistros, int tipo) {
	
		return  (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestralRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoTrimestralRotacionSG() {
		return (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestralRotacionSG")
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
	}
	
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoSemestralRotacion(Date desde, Date hasta, int posinicial, int cantregistros,int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestralRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//calcula en terminos de porcentaje
		BigDecimal totalValor	= BigDecimal.ZERO; 
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
			}//for
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))).multiply(new BigDecimal(100)));
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}

	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoSemestralRotacionSG() {
		return (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestralRotacionSG")
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		
	}
	
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoAnualRotacion(Date desde, Date hasta, int posinicial, int cantregistros,int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnualRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		BigDecimal totalValor	= BigDecimal.ZERO; 
		if (valores!=null && !valores.isEmpty()) {
			//suma todos los valores
			for (ValorGraficable val : valores) {
				totalValor = totalValor.add(val.getSerieValor()==null?BigDecimal.ZERO:val.getSerieValor());
			}//for
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(totalValor,NUMERO_DECIMALES, RoundingMode.HALF_UP))).multiply(new BigDecimal(100)));
				if ((val.getSerieValor().compareTo(new BigDecimal("100"))>0)) val.setSerieValor(new BigDecimal("100")); 
			}//for
		}//if
		return valores;
	}


	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoAnualRotacionSG() {
		return (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnualRotacionSG")
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
	}

	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoDiarioPIB(Date desde, Date hasta , 	int posinicial, int cantregistros, int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoDiarioRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
	return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoDiarioPIBSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoDiarioRotacionPIBSG")
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}
	
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER) 
	@Override
	public List<ValorGraficable> getConjuntoMensualPIB(Date desde, Date hasta, int posinicial, int cantregistros,int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensualRotacionPIBSG")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER) 
	@Override
	public List<ValorGraficable> getConjuntoMensualPIBSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoMensualRotacionPIBSG")
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoTrimestralPIB(Date desde, Date hasta, int posinicial,int cantregistros, int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestralRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoTrimestralPIBSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoTrimestralRotacionPIBSG")
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoSemestralPIB(Date desde, Date hasta, int posinicial, int cantregistros,int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestralRotacionPIBSG")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoSemestralPIBSG() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoSemestralRotacionPIBSG")
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
			


		}//if
		return valores;
	}
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoAnualPIB(Date desde, Date hasta, int posinicial, int cantregistros,int tipo) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnualRotacion")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(HORA_LITERAL, parametersService.getRotacionHoraDeCalculo())
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getConjuntoAnualPIBSG() {  
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>) getEm().createNamedQuery("AnalisisPagosSaldosXHora.getConjuntoAnualRotacionPIBSG")
				.getResultList();
		//actualiza los valores de cada cantidad y de cada valor en terminos de (%)
		Map<Integer,BigDecimal> pibs = getPIB();
		BigDecimal defaultPIB = parametersService.getPIBDefault();
		if (defaultPIB==null) defaultPIB = new BigDecimal(DEFAULT_PIB_LITERAL);
		if (valores!=null && !valores.isEmpty()) {
			//actualiza los valores de cada cantidad y de cada valor
			for (ValorGraficable val : valores) {
				BigDecimal valorPIB = pibs.get(val.getAnio()-1);
				if(valorPIB==null) { valorPIB = defaultPIB ; }
				val.setSerieValor((val.getSerieValor()==null?BigDecimal.ZERO:(val.getSerieValor().divide(valorPIB,NUMERO_DECIMALES, RoundingMode.HALF_UP))));
			}//for
		}//if
		return valores;
	}

	private Map<Integer,BigDecimal> getPIB() {
		Map<Integer,BigDecimal> res = new HashMap<Integer,BigDecimal>();
		List<ValorAnualIPIB> pibs = valorAnualIPIBService.finAll();
		for (ValorAnualIPIB vp: pibs) {
			res.put(new Integer(vp.getIdAnio()), vp.getValorPIB());
		}
		return res;
	}
	

	private BigDecimal getaPartWithOutPercent(BigDecimal numerador,BigDecimal denominador) {
		if (numerador==null || denominador==null || BigDecimal.ZERO.compareTo(numerador)==0 || BigDecimal.ZERO.compareTo(denominador)==0 ) {
			return BigDecimal.ZERO; 
		}
		return numerador.divide(denominador,NUMERO_DECIMALES, RoundingMode.HALF_UP);
	}

}


