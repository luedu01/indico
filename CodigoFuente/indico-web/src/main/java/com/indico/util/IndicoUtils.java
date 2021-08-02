package com.indico.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.indico.exceptions.IndicoException;

public class IndicoUtils {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(IndicoUtils.class);
	
	private static IndicoUtils util;
	
	private IndicoUtils(){
	}

	public static IndicoUtils getInstance(){
		if(util==null) util = new IndicoUtils(); 
		return util;
	}
	
	public boolean notNull(Object obj) {
		return !(obj==null || obj.toString().equals("")); 
	}
	
    public int daysBetween(Date fechaInicial, Date fechaFinal){
        return (int)( (fechaFinal.getTime() - fechaInicial.getTime()) / (1000 * 60 * 60 * 24));
    }

    public int monthsBetween(Date fechaInicial, Date fechaFinal) throws IndicoException {
    	try {
        	if(fechaInicial==null || fechaFinal==null || fechaFinal.getTime()<fechaInicial.getTime()) throw new IndicoException("Fecha Invalida"); 
        	Calendar fi = Calendar.getInstance();
        	fi.setTime(fechaInicial);
        	Calendar ff = Calendar.getInstance();
        	ff.setTime(fechaFinal);
        	if (fi.get(Calendar.YEAR)==ff.get(Calendar.YEAR)){
        		return ff.get(Calendar.MONTH)-fi.get(Calendar.MONTH);
        	} else {
        		int anios = ff.get(Calendar.YEAR)-fi.get(Calendar.YEAR) - 1;
        		int meses = anios * 12 ;
        		int mi = 12 - fi.get(Calendar.MONTH);
        		int mf = ff.get(Calendar.MONTH) + 1;
        		meses = meses + mi + mf;
        		return meses; 
        	}
    	} catch (IndicoException ex) {
    		logger.info(ex.getMessage());
    		throw ex;
    	} catch (Exception ex) {
    		logger.info(ex.getMessage());
    		throw new IndicoException("Error convirtiendo meses");
    	}
    }

    public int trimestreBetween(Date fi, Date ff) throws IndicoException {
    	try {
        	int trimestre = monthsBetween(fi,ff) / 3;
            return trimestre<=0?1:trimestre;
    	} catch(Exception ex ) {
    		logger.info(ex.getMessage());
    		throw new IndicoException("Error convirtiendo trimestres");
    	}
    }

    public int semestreBetween(Date fi, Date ff) throws IndicoException {
    	int semestre = monthsBetween(fi,ff) / 6;
        return semestre<=0?1:semestre;
    }

    public int aniosBetween(Date fechaInicial, Date fechaFinal) throws IndicoException {
    	try {
	    	Calendar fi = Calendar.getInstance();
	    	fi.setTime(fechaInicial);
	    	Calendar ff = Calendar.getInstance();
	    	ff.setTime(fechaFinal);
	    	return ff.get(Calendar.YEAR)-fi.get(Calendar.YEAR);
    	} catch(Exception ex ) {
    		logger.info(ex.getMessage());
    		throw new IndicoException("Error convirtiendo trimestres");
    	}
    }
    
    public BigDecimal assertBigDecimal(BigDecimal valor){
    	return valor==null?BigDecimal.ZERO:valor;
    }
    
    public double assertDoubleMM(BigDecimal valor){
    	return valor==null?0d:valor.doubleValue();
    }

    public double assertDouble(BigDecimal valor){
    	return valor==null?0d:valor.doubleValue();
    }

}
