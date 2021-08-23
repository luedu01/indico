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

public class CompAnio implements Serializable {

	private static final long serialVersionUID = 1L;
		
    private List<CampoSelect> anios;
    private String anioSelected;

		
	@PostConstruct
	public void init(){
			List<Anios> aniosDb = ServiceFacades.getInstance().getAniosService().finAll();
			anios = new ArrayList<CampoSelect>();
			Calendar cal = Calendar.getInstance();
			String anioa = String.valueOf(cal.get(Calendar.YEAR));
			if (aniosDb!=null) 
					for (Anios anio : aniosDb) {
						if (anioa.equals(anio.getIdAnio())) continue;
						anios.add(new CampoSelect(anio.getIdAnio(),anio.getIdAnio()));
					}
	}
	
	/**
	 * Si la fecha (anio mes dia )es valida retorna la fecha, de lo contrario retorna null
	 * @return
	 */
	public Date getFechaInicialSeleccionada(){
    	if ( IndicoUtils.getInstance().notNull(getAnioSelected()) ) {
	           	Calendar cal = Calendar.getInstance();
	           	cal.set(Calendar.YEAR			, new Integer(getAnioSelected())  );
	           	cal.set(Calendar.MONTH	 		, 0 );
	           	cal.set(Calendar.DAY_OF_MONTH	, 1  );
	           	cal.set(Calendar.HOUR_OF_DAY	, 0 );
	           	cal.set(Calendar.MINUTE			, 0 );
	           	cal.set(Calendar.SECOND			, 0  );
	           	cal.set(Calendar.MILLISECOND	, 0);
	           	return cal.getTime();
    	}
    	return null;
	}

	public Date getFechaFinalSeleccionada(){
    	if ( IndicoUtils.getInstance().notNull(getAnioSelected())) {
   	           	Calendar cal = Calendar.getInstance();
   	           	//primero se coloca el dia primero, para evitar se cambie la fecha automaticamente al asigna el mes.
   	           	//ej. si se crea con 30 de nov y se desea setear al 28 de febrero, el sistema la aproxima al 1 de marxo. 
   	           	//esto lo evita.
   	           	cal.set(Calendar.DAY_OF_MONTH	, 1 );
   	           	//Inicia las asignaciones
   	           	cal.set(Calendar.YEAR			, new Integer(getAnioSelected())  );
   	           	cal.set(Calendar.MONTH	 		, 11 );
   	           	cal.set(Calendar.DAY_OF_MONTH	, cal.getActualMaximum(Calendar.DAY_OF_MONTH)  );
	           	cal.set(Calendar.HOUR_OF_DAY	, 23 );
	           	cal.set(Calendar.MINUTE			, 59 );
	           	cal.set(Calendar.SECOND			, 59  );
	           	cal.set(Calendar.MILLISECOND	, 999);
   	           	return cal.getTime();
       	}
       	return null;
	}
	
	
	public void changeAnioAction(ValueChangeEvent evt) {
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

}