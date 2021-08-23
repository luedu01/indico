package com.indico.jee.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.indico.jee.modelo.AnalisisTransaccionCNI;
import com.indico.jee.servicesi.AnalisisTransaccionCNIService;
import com.indico.jee.util.ValorGraficable;

import static com.indico.jee.util.Constants.FECHA_INICIO_LITERAL;
import static com.indico.jee.util.Constants.FECHA_FIN_LITERAL;
import static com.indico.jee.util.Constants.UNCHECKED_LITERAL;

@Stateless
public class AnalisisTransaccionCNIServiceImpl extends GeneralServiceImpl implements AnalisisTransaccionCNIService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AnalisisTransaccionCNIService.class);
	
	public AnalisisTransaccionCNIServiceImpl(){
		logger.info("Created AnalisisTransaccionCNIServiceImpl");
	}
	
	@Override
	public List<AnalisisTransaccionCNI> getDataDiarioTransaccionCNI(Date fechaInicial, Date fechaFinal) {
		return  (List<AnalisisTransaccionCNI>)getEm().createNamedQuery("AnalisisTransaccionCNI.getDatadiarioTransaccionCNI",AnalisisTransaccionCNI.class)
				.setParameter(FECHA_INICIO_LITERAL, fechaInicial)	
				.setParameter(FECHA_FIN_LITERAL, fechaFinal)	
				.getResultList();
	}

	@Override
	@SuppressWarnings(UNCHECKED_LITERAL)
	public List<ValorGraficable> getTransaccionCNIALL(String periodo) {
		List<ValorGraficable> valores = null;
		
		switch(periodo) {
			case("1"):
			
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisTransaccionCNI.getTransaccionCNIDiarioAll")
						.getResultList();
				
				break;
			case("2"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisTransaccionCNI.getTransaccionCNIMensualAll")
						.getResultList();
				break;
			case("3"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisTransaccionCNI.getTransaccionCNITrimestralAll")
						.getResultList();
				break;
			case("4"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisTransaccionCNI.getTransaccionCNISemestralAll")
						.getResultList();
				break;
			case("5"):
				valores = (List<ValorGraficable>)getEm().createNamedQuery("AnalisisTransaccionCNI.getTransaccionCNIAnualAll")
						.getResultList();
				break;
		}
		
		return valores;
	}
	
	
}
