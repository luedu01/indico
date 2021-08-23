package com.indico.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.indico.jee.servicesi.ParametersService;
import com.indico.jee.servicesi.RangoCanjeCompensacionService;
import com.indico.jee.servicesi.AnalisisChequesCompensaService;
import com.indico.jee.servicesi.AnalisisComposicionCanjeService;
import com.indico.jee.servicesi.AnalisisPagosSaldosXHoraService;
import com.indico.jee.servicesi.AnalisisPesoContantesService;
import com.indico.jee.servicesi.AnalisisTransaccionCNIService;
import com.indico.jee.servicesi.AniosService;
import com.indico.jee.servicesi.MedioServCompensacionService;
import com.indico.jee.servicesi.ValorAnualIPIBService;
import com.indico.jee.servicesi.TransfElectronicaAltoValorService;

public class ServiceFacades {
	
	private static ServiceFacades instance;
	
	private AnalisisPagosSaldosXHoraService 	analisisPagosSaldosXHoraService;
	private AniosService 						aniosService;
	private TransfElectronicaAltoValorService 	transfElectronicaAltoValorService;
	private ValorAnualIPIBService 				valorAnualIPIBService;
	private ParametersService 					parametersService;
	private AnalisisChequesCompensaService 		analisisChequesCompensaService;
	private MedioServCompensacionService		medioServCompensacionService;
	private RangoCanjeCompensacionService		rangoCanjeCompensacionService;
	private AnalisisComposicionCanjeService		analisisComposicionCanjeService;
	private AnalisisPesoContantesService		analisisPesoContantesService;
	private AnalisisTransaccionCNIService		analisisTransaccionCNIService;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceFacades.class);
	
	private ServiceFacades(){
		/*
		 * to support singleton pattern
		 */
	}
	
	public static ServiceFacades getInstance() {
		if (instance==null) instance= new ServiceFacades(); 
		return instance;
	}
	
	/**
	 * Servicio Medios de compensacion
	 * @return
	 */
	public MedioServCompensacionService getMedioServCompensacionServiceService(){
		try {
			if (medioServCompensacionService==null) {
				medioServCompensacionService = this.searchEJBNew("java:module/MedioServCompensacionImpl!com.indico.jee.servicesi.MedioServCompensacionService");
			}
			return medioServCompensacionService;
		} catch (NamingException ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}

	
	/**
	 * Rangos de Canje
	 * @return
	 */
	public RangoCanjeCompensacionService getRangoCanjeService() {
		try {
			if (rangoCanjeCompensacionService==null) {
				rangoCanjeCompensacionService = this.searchEJBNew("java:module/RangoCanjeCompensacionImpl!com.indico.jee.servicesi.RangoCanjeCompensacionService");
			}
			return rangoCanjeCompensacionService;
		} catch (NamingException ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}

	public AnalisisPagosSaldosXHoraService getAnalisisPagosSaldosXHoraService(){
		try {
			if (analisisPagosSaldosXHoraService==null) {
				analisisPagosSaldosXHoraService = this.searchEJBNew("java:module/AnalisisPagosSaldosXHoraServiceImpl!com.indico.jee.servicesi.AnalisisPagosSaldosXHoraService");
			}
			return analisisPagosSaldosXHoraService;
		} catch (NamingException ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}
	
	public TransfElectronicaAltoValorService getTransfElectronicaAltoValorService(){
		try {
			if (transfElectronicaAltoValorService==null) {
				transfElectronicaAltoValorService = this.searchEJBNew("java:module/TransfElectronicaAltoValorServiceImpl!com.indico.jee.servicesi.TransfElectronicaAltoValorService");
			}
			return transfElectronicaAltoValorService;
		} catch (NamingException ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}
	
	public AniosService getAniosService(){
		try {
			if (aniosService==null) {
				aniosService = this.searchEJBNew("java:module/AniosServiceImpl!com.indico.jee.servicesi.AniosService");
			}
			return aniosService;
		} catch (NamingException ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}

	public ParametersService getParametersService(){
		try {
			if (parametersService==null) {
				parametersService = this.searchEJBNew("java:module/ParametersServiceImpl!com.indico.jee.servicesi.ParametersService");
			}
			return parametersService;
		} catch (NamingException ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}
	
	public ValorAnualIPIBService getValorAnualIPIBService(){
		try { 
			if (valorAnualIPIBService==null) {
				valorAnualIPIBService = this.searchEJBNew("java:module/ValorAnualIPIBServiceImpl!com.indico.jee.servicesi.ValorAnualIPIBService");
			}
			return valorAnualIPIBService;
		} catch (NamingException ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}

	public AnalisisChequesCompensaService getAnalisisChequesCompensaService(){
		try {
			if (analisisChequesCompensaService==null) {
				analisisChequesCompensaService = this.searchEJBNew("java:module/AnalisisChequesCompensaServiceImpl!com.indico.jee.servicesi.AnalisisChequesCompensaService");
			}
			return analisisChequesCompensaService;
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}
	
	public AnalisisComposicionCanjeService getAnalisisComposicionCanjeService(){
		try {
			if (analisisComposicionCanjeService==null) {
				analisisComposicionCanjeService = this.searchEJBNew("java:module/AnalisisComposicionCanjeServiceImpl!com.indico.jee.servicesi.AnalisisComposicionCanjeService");
			}
			return analisisComposicionCanjeService;
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}
	
	public AnalisisPesoContantesService getAnalisisPesoContantesService() {
		try {
			if (analisisPesoContantesService==null) {
				analisisPesoContantesService = this.searchEJBNew("java:module/AnalisisPesosConstantesServiceImpl!com.indico.jee.servicesi.AnalisisPesoContantesService");
			}
			return analisisPesoContantesService;
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
		return null; 
	}
	
	/**
	 * Servicio Transaccion CNI
	 * @return
	 */
	public AnalisisTransaccionCNIService getTransaccionCNIService() {
		try {
			if (analisisTransaccionCNIService==null) {
				analisisTransaccionCNIService = this.searchEJBNew("java:module/AnalisisTransaccionCNIServiceImpl!com.indico.jee.servicesi.AnalisisTransaccionCNIService");
			}
			return analisisTransaccionCNIService;
		} catch (NamingException ex) {
			//System.out.println(ex);
			logger.info(ex.getMessage());
		}
		return null; 
	}
		
	@SuppressWarnings("unchecked")
	public <T> T searchEJBNew(String sLookup) throws NamingException {
		T bean;
		try {
			final Context context = new InitialContext();
			bean = (T) context.lookup(sLookup);
			return bean;
		} catch (NamingException e1){
			logger.info(e1.getMessage());
		}
		return null;
	}

}