package com.indico.jee.services;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import com.indico.jee.modelo.ValorAnualIPIB;
import com.indico.jee.servicesi.ValorAnualIPIBService;

@Stateless
public class ValorAnualIPIBServiceImpl extends GeneralServiceImpl implements ValorAnualIPIBService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ValorAnualIPIBService.class);

	public ValorAnualIPIBServiceImpl(){
		logger.info("Created ValorAnualIPIBServiceImpl");
	}

	public List<ValorAnualIPIB> finAll() {
		return getEm().createNamedQuery("ValorAnualIPIB.findAll",ValorAnualIPIB.class).getResultList();
	
	}
	
	public BigInteger countAll() {
		return new BigInteger(((Long)getEm().createNamedQuery("ValorAnualIPIB.countAll").getSingleResult()).toString());
	}
}
