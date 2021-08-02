package com.indico.jee.services;

import java.util.List;
import javax.ejb.Stateless;
import com.indico.jee.modelo.MedioServCompensacion;
import com.indico.jee.servicesi.MedioServCompensacionService;

@Stateless
public class MedioServCompensacionImpl extends GeneralServiceImpl implements MedioServCompensacionService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MedioServCompensacionImpl.class);

	public MedioServCompensacionImpl(){
		logger.info("Created MedioServCompensacionImpl");
	}

	public List<MedioServCompensacion> finAll() {
		return getEm().createNamedQuery("MedioServCompensacion.findAll",MedioServCompensacion.class).getResultList();
	} 
}
