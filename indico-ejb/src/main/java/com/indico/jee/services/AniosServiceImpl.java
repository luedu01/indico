package com.indico.jee.services;

import java.util.List;
import javax.ejb.Stateless;
import com.indico.jee.modelo.Anios;
import com.indico.jee.servicesi.AniosService;

@Stateless
public class AniosServiceImpl extends GeneralServiceImpl implements AniosService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AniosServiceImpl.class);

	public AniosServiceImpl(){
		logger.info("Created AniosService");
	}

	public List<Anios> finAll() {
		return getEm().createNamedQuery("AnalisisPagosSaldosXHora.getAnios",Anios.class).getResultList();
		
	} 
}
