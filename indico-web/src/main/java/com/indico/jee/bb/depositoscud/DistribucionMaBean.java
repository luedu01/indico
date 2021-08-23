package com.indico.jee.bb.depositoscud;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

@ManagedBean(name="distribucionMaBean")
@ViewScoped
public class DistribucionMaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idTabActive;
	
	private boolean ocultarmostrar;

       	
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
