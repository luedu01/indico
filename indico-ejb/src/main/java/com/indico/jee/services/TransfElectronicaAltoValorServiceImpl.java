package com.indico.jee.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import com.indico.jee.modelo.TransfElectronicaAltoValor;
import com.indico.jee.servicesi.TransfElectronicaAltoValorService;
import com.indico.jee.util.CampoSelect;
import com.indico.jee.util.ValorGraficable;
import static com.indico.jee.util.Constants.*;

@Stateless
public class TransfElectronicaAltoValorServiceImpl extends GeneralServiceImpl  implements TransfElectronicaAltoValorService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TransfElectronicaAltoValorServiceImpl.class);
	
	public TransfElectronicaAltoValorServiceImpl(){
		logger.info("Created TransfElectronicaAltoValorService");
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public Date getLastDate() {
		return getEm().createNamedQuery("TransElectAltoValor.getLastDate",Date.class).getSingleResult();
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<TransfElectronicaAltoValor> finAll() {
		List<TransfElectronicaAltoValor> res = new ArrayList<TransfElectronicaAltoValor>();
		res = getEm().createNamedQuery("TransfElectronicaAltoValor.finAll",TransfElectronicaAltoValor.class).getResultList();
		return res;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public BigInteger countAll() {
		return new BigInteger(((Long)getEm().createNamedQuery("TransfElectronicaAltoValor.countAll").getSingleResult()).toString());
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<CampoSelect> getSubgrupos() {
		return  (List<CampoSelect>)getEm().createNamedQuery("TransElectAltoValor.getSubgrupos").getResultList();
	}
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<CampoSelect> getGrupos() {
		return (List<CampoSelect>)getEm().createNamedQuery("TransElectAltoValor.getGrupos").getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getAllDiarioComplete() {
		return (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllDiarioComplete").getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getAllDiario(Date desde, Date hasta, int posinicial, int cantregistros) {
		return (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllDiario")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
	}

	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoDiario(Date desde, Date hasta, String subgrupo, int posinicial, int cantregistros) {
		
		return (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoDiario")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(ID_SUBGRUPO_LITERAL, subgrupo)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getAllConjuntoDiarioBySG(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllConjuntoDiarioBySG")
				.setParameter(ID_GRUPO_LITERAL		, String.valueOf(Integer.valueOf( concepto.substring(0,5)) ))
				.setParameter(ID_SUBGRUPO_LITERAL	, String.valueOf(Integer.valueOf( concepto.substring(5,10)) ))
				.getResultList();
		return valores;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getAllConjuntoDiarioByGR(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllConjuntoDiarioByGR")
				.setParameter(ID_GRUPO_LITERAL		, concepto )
				.getResultList();
		return valores;
	}

	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getAllMensual(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllMensual")
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
	public List<ValorGraficable> getAllMensualComplete() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllMensualComplete").getResultList();
		return valores;
	}
	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoMensualCompleteSG(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoMensualComplete")
				.setParameter(ID_GRUPO_LITERAL		, String.valueOf(Integer.valueOf( concepto.substring(0,5)) ))
				.setParameter(ID_SUBGRUPO_LITERAL	, String.valueOf(Integer.valueOf( concepto.substring(5,10)) ))
				.getResultList();
		return valores;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoMensualCompleteByGR(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoMensualCompleteByGR")
				.setParameter(ID_GRUPO_LITERAL		, concepto  )
				.getResultList();
		return valores;
	}


	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoMensual(Date desde, Date hasta,String subgrupo, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoMensual")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(ID_SUBGRUPO_LITERAL, subgrupo)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}


	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getAllTrimestral(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllTrimestral")
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
	public List<ValorGraficable> getAllTrimestralComplete() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllTrimestralComplete")
				.getResultList();
		return valores;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoTrimestralSG(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoTrimestralSG")
				.setParameter(ID_GRUPO_LITERAL		, String.valueOf(Integer.valueOf( concepto.substring(0,5)) ))
				.setParameter(ID_SUBGRUPO_LITERAL	, String.valueOf(Integer.valueOf( concepto.substring(5,10)) ))
				.getResultList();
		return valores;
	}

	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoTrimestralByGR(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoTrimestralByGR")
				.setParameter(ID_GRUPO_LITERAL		,  concepto )
				.getResultList();
		return valores;
	}

	
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getConjuntoTrimestral(Date desde, Date hasta,String subgrupo, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoTrimestral")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(ID_SUBGRUPO_LITERAL, subgrupo)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getAllSemestral(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllSemestral")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Override
	public List<ValorGraficable> getAllSemestralComplete() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllSemestralComplete")
					.getResultList();
		return valores;
	}


	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoSemestralSG(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoSemestralSG")
				.setParameter(ID_GRUPO_LITERAL		, String.valueOf(Integer.valueOf( concepto.substring(0,5)) ))
				.setParameter(ID_SUBGRUPO_LITERAL	, String.valueOf(Integer.valueOf( concepto.substring(5,10)) ))
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoSemestralByGR(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoSemestralByGR")
				.setParameter(ID_GRUPO_LITERAL		, concepto )
				.getResultList();
		return valores;
	}

	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoSemestral(Date desde, Date hasta,String subgrupo, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoSemestral")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(ID_SUBGRUPO_LITERAL, subgrupo)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}

	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getAllAnual(Date desde, Date hasta,int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllAnual")
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
	public List<ValorGraficable> getAllAnualComplete() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getAllAnualComplete")
				.getResultList();
		return valores;
	}

	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoAnualSG(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoAnualSG")
				.setParameter(ID_GRUPO_LITERAL		, String.valueOf(Integer.valueOf( concepto.substring(0,5)) ))
				.setParameter(ID_SUBGRUPO_LITERAL	, String.valueOf(Integer.valueOf( concepto.substring(5,10)) ))
				.getResultList();
		return valores;
	}

	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoAnualByGR(String concepto) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoAnualByGR")
				.setParameter(ID_GRUPO_LITERAL		, concepto )
				.getResultList();
		return valores;
	}
	
	
	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> getConjuntoAnual(Date desde, Date hasta,String subgrupo, int posinicial, int cantregistros) {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.getConjuntoAnual")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.setParameter(ID_SUBGRUPO_LITERAL, subgrupo)
				.setFirstResult(posinicial)
				.setMaxResults(cantregistros)
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<TransfElectronicaAltoValor> getDetalleExportar(Date desde, Date hasta) {
		List<TransfElectronicaAltoValor> valores = new ArrayList<TransfElectronicaAltoValor>();
		valores = (List<TransfElectronicaAltoValor>)getEm().createNamedQuery("TransElectAltoValor.getDetalleExportar")
				.setParameter(DESDE_LITERAL, desde)
				.setParameter(HASTA_LITERAL, hasta)
				.getResultList();
		return valores;
	}

	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<TransfElectronicaAltoValor> getDetalleExportarSG(Date desde, Date hasta, String concepto) {
		List<TransfElectronicaAltoValor> valores = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(hasta);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		valores = (List<TransfElectronicaAltoValor>)getEm().createNamedQuery("TransElectAltoValor.getDetalleExportarSG")
				.setParameter(DESDE_LITERAL	, desde)
				.setParameter(HASTA_LITERAL	, cal.getTime())
				.setParameter(ID_GRUPO_LITERAL	, concepto)
				.getResultList();
		return valores;
	}

	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<ValorGraficable> lastMovementsDaily() {
		List<ValorGraficable> valores = null;
		valores = (List<ValorGraficable>)getEm().createNamedQuery("TransElectAltoValor.lastMovementsDaily")
				.setFirstResult(0)
				.setMaxResults(5)
				.getResultList();
		if(valores!=null) Collections.reverse(valores);
		return valores;
	}
	
}
