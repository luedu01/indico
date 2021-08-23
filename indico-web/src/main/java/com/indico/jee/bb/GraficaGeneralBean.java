package com.indico.jee.bb;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;

public abstract class GraficaGeneralBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ActionEvent evt;
		
	protected SimpleDateFormat receiveFormat = new SimpleDateFormat("yyyy-MM-dd");

	protected SimpleDateFormat exportFormat = new SimpleDateFormat("yyyy-MM-dd");

	protected SimpleDateFormat generalFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	protected static final int NUMERO_DECIMALES = 4;
	
    private String tipoaexportar;
    private String start;
    private String end;
    private String periodicidad;
    private String rangesSelected;
	
    public GraficaGeneralBean() {
    }
    
    public void restaurarAction(ActionEvent evt){
    	this.setEvt(evt);
    	periodicidad="1";
    	PrimeFaces.current().executeScript( " iefix();");
    }

    public void changeTabExportar() {
    	setTipoaexportar("");
    }

	public void changePanelAction() {
	}

	public String getTipoaexportar() {
		return tipoaexportar;
	}

	public void setTipoaexportar(String tipoaexportar) {
		this.tipoaexportar = tipoaexportar;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public ActionEvent getEvt() {
		return evt;
	}

	public void setEvt(ActionEvent evt) {
		this.evt = evt;
	}

	public String getRangesSelected() {
		return rangesSelected;
	}

	public void setRangesSelected(String rangesSelected) {
		this.rangesSelected = rangesSelected;
	}

	
    
}
