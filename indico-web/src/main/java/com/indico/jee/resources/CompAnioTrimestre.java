package com.indico.jee.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;
import com.indico.jee.modelo.Anios;
import com.indico.jee.util.CampoSelect;
import com.indico.jee.web.carga.CargaFiltros;

public class CompAnioTrimestre implements Serializable {

	private static final long serialVersionUID = 1L;
		
    private List<CampoSelect> anios;
    private String anioSelected;

    private List<CampoSelect> trimestres;
    private String trimestreSelected;
    	
	@PostConstruct
	public void init(){
		List<Anios> aniosDb = ServiceFacades.getInstance().getAniosService().finAll();
		anios = new ArrayList<CampoSelect>();
		if (aniosDb!=null) for (Anios anio : aniosDb) anios.add(new CampoSelect(anio.getIdAnio(),anio.getIdAnio()));
		trimestres = CargaFiltros.getInstance().obtenerTrimestre(anioSelected);
	}
	
	/**
	 * Si la fecha (anio mes dia )es valida retorna la fecha, de lo contrario retorna null
	 * @return
	 */
	public Date getFechaInicialSeleccionada(){
    	if ( IndicoUtils.getInstance().notNull(getAnioSelected())  &&
       		 IndicoUtils.getInstance().notNull(trimestreSelected)   
       		) {
	           	Calendar cal = Calendar.getInstance();
	           	cal.set(Calendar.YEAR			, new Integer(getAnioSelected())  );
   	           	if (trimestreSelected.equals("01")) 		cal.set(Calendar.MONTH	 , 0 ); 
   	           	else if (trimestreSelected.equals("02")) 	cal.set(Calendar.MONTH	 , 3 );
   	           	else if (trimestreSelected.equals("03")) 	cal.set(Calendar.MONTH	 , 6 );
   	           	else if (trimestreSelected.equals("04")) 	cal.set(Calendar.MONTH	 , 9 );
	           	else cal.set(Calendar.MONTH	 , 0 );
	           	cal.set(Calendar.DAY_OF_MONTH	, 1  );
	           	cal.set(Calendar.HOUR_OF_DAY	, 0 );
	           	cal.set(Calendar.MINUTE			, 0 );
	           	cal.set(Calendar.SECOND			, 0  );
	           	cal.set(Calendar.MILLISECOND	, 0  );
	           	return cal.getTime();
    	}
    	return null;
	}

	public Date getFechaFinalSeleccionada(){
    	if ( IndicoUtils.getInstance().notNull(getAnioSelected())  		&&
          		 IndicoUtils.getInstance().notNull(trimestreSelected)   
          	) {
   	           	Calendar cal = Calendar.getInstance();
   	           	//primero se coloca el dia primero, para evitar se cambie la fecha automaticamente al asigna el mes.
   	           	//ej. si se crea con 30 de nov y se desea setear al 28 de febrero, el sistema la aproxima al 1 de marxo. 
   	           	//esto lo evita.
   	           	cal.set(Calendar.DAY_OF_MONTH	, 1 );
   	           	//Inicia las asignaciones
   	           	cal.set(Calendar.YEAR			, new Integer(getAnioSelected())  );
   	           	if (trimestreSelected.equals("01")) 		cal.set(Calendar.MONTH	 , 2 ); 
   	           	else if (trimestreSelected.equals("02")) 	cal.set(Calendar.MONTH	 , 5 );
   	           	else if (trimestreSelected.equals("03")) 	cal.set(Calendar.MONTH	 , 8 );
   	           	else if (trimestreSelected.equals("04")) 	cal.set(Calendar.MONTH	 , 11 );
   	           	else cal.set(Calendar.MONTH	 	, 2 );
   	           	//
   	           	cal.set(Calendar.DAY_OF_MONTH	, cal.getActualMaximum(Calendar.DAY_OF_MONTH) );
	           	cal.set(Calendar.HOUR_OF_DAY	, 23 );
	           	cal.set(Calendar.MINUTE			, 59 );
	           	cal.set(Calendar.SECOND			, 59  );
	           	cal.set(Calendar.MILLISECOND	, 999);
   	           	return cal.getTime();
       	}
       	return null;
	}
	
	
	public void changeAnioAction(ValueChangeEvent evt) {
		String anio = (String)evt.getNewValue();
		if (anio==null) return;
		trimestres = CargaFiltros.getInstance().obtenerTrimestre(anio);
	}

	public void changeTrimestreAction(ValueChangeEvent evt) {
		/*
		 * for p:ajax listener
		 */
	}

	public List<CampoSelect> getAnios() {
		return anios;
	}

	public void setAnios(List<CampoSelect> anios) {
		this.anios = anios;
	}

	public String getAnioSelected() {
		return anioSelected;
	}

	public void setAnioSelected(String anioSelected) {
		this.anioSelected = anioSelected;
	}

	public List<CampoSelect> getTrimestres() {
		return trimestres;
	}

	public void setTrimestres(List<CampoSelect> trimestres) {
		this.trimestres = trimestres;
	}

	public String getTrimestreSelected() {
		return trimestreSelected;
	}

	public void setTrimestreSelected(String trimestreSelected) {
		this.trimestreSelected = trimestreSelected;
	}

}