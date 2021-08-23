package com.indico.jee.services;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GeneralServiceImpl {

    @Resource SessionContext sessionContext;
    @PersistenceContext(unitName = "BanrepPU")
    private EntityManager em;

    protected EntityManager getEm(){
    	return em;
    }
    
}
