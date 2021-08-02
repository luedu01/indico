package com.indico.jee.services;

import java.util.List;
import javax.ejb.Stateless;
import com.indico.jee.modelo.RangoCanjeCompensacion;
import com.indico.jee.servicesi.RangoCanjeCompensacionService;

@Stateless
public class RangoCanjeCompensacionImpl extends GeneralServiceImpl implements RangoCanjeCompensacionService{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RangoCanjeCompensacionService.class);

	public RangoCanjeCompensacionImpl(){
		logger.info("Created RangoCanjeCompensacionService");
	}

	public List<RangoCanjeCompensacion> findAllRangos() {
		return getEm().createNamedQuery("RangoCanjeCompensacion.findAll",RangoCanjeCompensacion.class).getResultList();
	} 
}
