package com.indico.jee.services;

import static com.indico.jee.util.Constants.UNCHECKED_LITERAL;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import com.indico.jee.modelo.AnalisisChequesPesosConstantes;
import com.indico.jee.servicesi.AnalisisPesoContantesService;
import com.indico.jee.util.ValorGraficable;

@Stateless
public class AnalisisPesosConstantesServiceImpl extends GeneralServiceImpl implements AnalisisPesoContantesService{

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AnalisisPesosConstantesServiceImpl.class);
	
	public AnalisisPesosConstantesServiceImpl(){
		logger.info("Created AnalisisPesosConstantesServiceImpl");
	}
	
	@SuppressWarnings(UNCHECKED_LITERAL)
	@Override
	public List<ValorGraficable> getChequesPesosAll() {
		List<ValorGraficable> valores = null;
		
		valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisChequesPesosConstantes.getChequesPesosConstantesAll").getResultList();
						
		if (valores!=null) {
			Map<String,BigDecimal> totalesValor = new HashMap<String,BigDecimal>(); 
			Map<String,BigDecimal> totalesCantidad = new HashMap<String,BigDecimal>(); 
			
			//construye los cheques
			for (ValorGraficable vg: valores) {
				totalesCantidad.put(vg.getEjeX(), totalesCantidad.containsKey(vg.getEjeX()) ? totalesCantidad.get(vg.getEjeX()).add(vg.getSerieCantidad()) : vg.getSerieCantidad());
				totalesValor.put(vg.getEjeX(), totalesValor.containsKey(vg.getEjeX()) ? totalesValor.get(vg.getEjeX()).add(vg.getSerieValor()) : vg.getSerieValor());
			}
			
		}
		
		return valores;
	}
	
/* Export Data */
	
	@Override
	public List<AnalisisChequesPesosConstantes> getDataPesosConstantes() {
		return  (List<AnalisisChequesPesosConstantes>)getEm().createNamedQuery("AnalisisChequesPesosConstantes.findAll",AnalisisChequesPesosConstantes.class)
				.getResultList();
	}

}
