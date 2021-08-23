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

public class CompAnioMesDia implements Serializable {

	private static final long serialVersionUID = 1L;
		
    private List<CampoSelect> anios;
    private String anioSelected;

    private List<CampoSelect> meses;
    private String mesSelected;
    
    private List<CampoSelect> dias;
    private String diasSelected;
	
	
	@PostConstruct
	public void init(){
			this.anios = new ArrayList<CampoSelect>();
			List<Anios> aniosDb = ServiceFacades.getInstance().getAniosService().finAll();
			if (aniosDb!=null) for (Anios anio : aniosDb) this.anios.add(new CampoSelect(anio.getIdAnio(),anio.getIdAnio()));
			meses = CargaFiltros.getInstance().obtenerMesesAnioMesDia(anioSelected);
	}
	
	public void changeAnioAction(ValueChangeEvent evt) {
		diasSelected=null;
		dias = new ArrayList<CampoSelect>(); 
		if ((String) evt.getNewValue()!=null) meses = CargaFiltros.getInstance().obtenerMesesAnioMesDia(((String)evt.getNewValue()));
		if (getMesSelected()!=null && ((String) evt.getNewValue())!=null) dias=CargaFiltros.getInstance().obtenerDiasMes(getMesSelected(), ((String)evt.getNewValue()));
	}

	public void changeMesAction(ValueChangeEvent evt) {
		diasSelected=null;
		dias = new ArrayList<CampoSelect>();
		if (((String)evt.getNewValue())!=null && getAnioSelected()!=null)
		dias=CargaFiltros.getInstance().obtenerDiasMes(((String)evt.getNewValue()), getAnioSelected());
	}
	
	public void  loadMontsAndDays(){
		meses = CargaFiltros.getInstance().obtenerMesesAnioMesDia(anioSelected);
		dias=CargaFiltros.getInstance().obtenerDiasMes(getMesSelected(), anioSelected);
	}

	/**
	 * Si la fecha (anio mes dia )es valida retorna la fecha, de lo contrario retorna null
	 * @return
	 */
	public Date getFechaInicialSeleccionada(){
    	if ( IndicoUtils.getInstance().notNull(getAnioSelected())  &&
       		 IndicoUtils.getInstance().notNull(getMesSelected())   &&
       		 IndicoUtils.getInstance().notNull(getDiasSelected())     
       		) {
	           	Calendar cal = Calendar.getInstance();
	           	cal.set(Calendar.YEAR			, new Integer(getAnioSelected())  );
	           	cal.set(Calendar.MONTH			, new Integer(getMesSelected()) -1);
	           	cal.set(Calendar.DAY_OF_MONTH	, new Integer(getDiasSelected())  );
	           	cal.set(Calendar.HOUR_OF_DAY	, 0 );
	           	cal.set(Calendar.MINUTE			, 0 );
	           	cal.set(Calendar.SECOND			, 0  );
	           	cal.set(Calendar.MILLISECOND	, 0);
	           	return cal.getTime();
    	}
    	return null;
	}

	public Date getFechaFinalSeleccionada(){
    	if ( IndicoUtils.getInstance().notNull(getAnioSelected())  &&
       		 IndicoUtils.getInstance().notNull(getMesSelected())   &&
       		 IndicoUtils.getInstance().notNull(getDiasSelected())     
       		) {
	           	Calendar cal = Calendar.getInstance();
	           	cal.set(Calendar.YEAR			, new Integer(getAnioSelected())  );
	           	cal.set(Calendar.MONTH			, new Integer(getMesSelected())-1 );
	           	cal.set(Calendar.DAY_OF_MONTH	, new Integer(getDiasSelected())  );
	           	cal.set(Calendar.HOUR_OF_DAY	, 23 );
	           	cal.set(Calendar.MINUTE			, 59 );
	           	cal.set(Calendar.SECOND			, 59  );
	           	cal.set(Calendar.MILLISECOND	, 999);
	           	return cal.getTime();
    	}
    	return null;
	}

	public void changeDiaAction(ValueChangeEvent evt) {
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

	public List<CampoSelect> getMeses() {
		return meses;
	}

	public void setMeses(List<CampoSelect> meses) {
		this.meses = meses;
	}

	public String getMesSelected() {
		return mesSelected;
	}

	public void setMesSelected(String mesSelected) {
		this.mesSelected = mesSelected;
	}

	public List<CampoSelect> getDias() {
		return dias;
	}

	public void setDias(List<CampoSelect> dias) {
		this.dias = dias;
	}

	public String getDiasSelected() {
		return diasSelected;
	}

	public void setDiasSelected(String diasSelected) {
		this.diasSelected = diasSelected;
	}

}