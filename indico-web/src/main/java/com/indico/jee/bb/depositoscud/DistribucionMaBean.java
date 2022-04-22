package com.indico.jee.bb.depositoscud;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

@SuppressWarnings("deprecation")
@ManagedBean(name="distribucionMaBean")
@ViewScoped
public class DistribucionMaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idTabActive;
	
	private boolean ocultarmostrar = true;

	private String titleValor = "Desplegar Gráfica";
	private String titleCantidad = "Desplegar Gráfica";

       	
    @PostConstruct
    public void init() {
    	idTabActive = -1;
    }

    public void onTabClose(TabCloseEvent event) {
        idTabActive = ((AccordionPanel) event.getComponent()).getChildren().indexOf(event.getTab());
        if (event.getTab().getTitletip()!=null && event.getTab().getTitletip().equals("Desplegar Gráfica")) {
        	event.getTab().setTitletip("Contraer Gráfica");
        } else {
        	event.getTab().setTitletip("Desplegar Gráfica");
        }
        //FacesContext.getCurrentInstance();
    }
    
    public void onTabChange(TabChangeEvent event) {
        idTabActive = ((AccordionPanel) event.getComponent()).getChildren().indexOf(event.getTab());
        if (event.getTab().getTitletip()!=null && event.getTab().getTitletip().equals("Desplegar Gráfica")) {
        	event.getTab().setTitletip("Contraer Gráfica");
        } else {
        	event.getTab().setTitletip("Desplegar Gráfica");
        }
    }

    
    public void showHideMessageInformation(){
    	ocultarmostrar = !ocultarmostrar;
    }
    
	public boolean isOcultarmostrar() {
		return ocultarmostrar;
	}

	public void setOcultarmostrar(boolean ocultarmostrar) {
		this.ocultarmostrar = ocultarmostrar;
	}

	public int getIdTabActive() {
		return idTabActive;
	}

	public void setIdTabActive(int idTabActive) {
		this.idTabActive = idTabActive;
	}

	public String getTitleValor() {
		return titleValor;
	}

	public void setTitleValor(String titleValor) {
		this.titleValor = titleValor;
	}

	public String getTitleCantidad() {
		return titleCantidad;
	}

	public void setTitleCantidad(String titleCantidad) {
		this.titleCantidad = titleCantidad;
	}

}
