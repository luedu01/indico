package com.indico.jee.bb.compensacion;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

@SuppressWarnings("deprecation")
@ManagedBean(name="compComportamientoMaBean")
@ViewScoped
public class CompComportamientoMaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idTabActive = -1;
	
	private boolean ocultarmostrar = true;
	
   
	@PostConstruct
    public void init() {
		idTabActive = -1;
    }
    
    public void onTabChange(TabChangeEvent event) {
        idTabActive = ((AccordionPanel) event.getComponent()).getChildren().indexOf(event.getTab());
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

}
