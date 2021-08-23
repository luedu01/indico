package com.indico.jee.bb;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;

@ManagedBean(name="homeMaBean")
@ApplicationScoped
public class HomeMaBean implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private static final String URL_CUENTASDEPOSITO	="cuentasDeposito.xhtml";
	private static final String URL_COMPENSACHEQUE	="compensacionCheques.xhtml";
	private static final String URL_BAJOVALOR		="bajovalor.xhtml";
	private static final String URL_GUIA			="guia.xhtml";
	
	private String 	src="";
	private int   	selected=-1;
	private int 	idTabActive;
	private boolean ocultarmostrar;
		
	@PostConstruct
	public void init() {
		selected=-1;
		src="";
		idTabActive = -1;
	}
	
	public void onTabChange(TabChangeEvent event) {
        idTabActive = ((AccordionPanel) event.getComponent()).getChildren().indexOf(event.getTab());
    }
	
	public void iconAction(ActionEvent evt) {
		String valor = (String)evt.getComponent().getAttributes().get("sel");
		updateSelected(valor);
	}
	
	public void iconAction2(ActionEvent evt) {
		String valor = (String)evt.getComponent().getAttributes().get("sel");
		updateSelected(valor);
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
	
	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public String getSrc() {
		return src;
	}
	
	private void updateSelected(String valor) {
		if (valor!=null) {
			if (valor.equals("0")) {
				selected=0;
				src=URL_CUENTASDEPOSITO;
			} else if (valor.equals("1")) {
				selected=1;
				src=URL_COMPENSACHEQUE;
			} else if (valor.equals("2")) {
				selected=2;
				src=URL_BAJOVALOR;
			} else if(valor.equals("3")) {
				selected=3;
				src=URL_GUIA;
			}
		}
	}
	
	public void buttonAction() {
        addMessage();
    }
 
    public void addMessage() {
        //FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
}