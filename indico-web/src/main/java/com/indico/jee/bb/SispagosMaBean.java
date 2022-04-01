package com.indico.jee.bb;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.indico.jndi.ServiceFacades;

@SuppressWarnings("deprecation")
@ManagedBean(name="sispagosMaBean")
@SessionScoped
public class SispagosMaBean implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private static final String URL_TRANSFERENCIA	="./indicadores/transferencia.xhtml";
	private static final String URL_DISTRIBUCION	="./indicadores/distribucion.xhtml";
	private static final String URL_PAGOS			="./indicadores/pagos.xhtml";
	
	private String src="";
	private int   selected=0;
	
	private Date lastDateTransferencias;
	private Date lastDateIndicadores;
	private Date lastDateDistribucion;

		
	@PostConstruct
	public void init() {
		selected=0;
		src=URL_TRANSFERENCIA;
		lastDateTransferencias	= ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getLastDate();
		lastDateIndicadores		= ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getLastDate();
		lastDateDistribucion	= ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getLastDate();
		
	}
	
	public void selectAction(ActionEvent evt) {
		String valor = (String)evt.getComponent().getAttributes().get("sel");
		updateSelected(valor);
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
	
	public void next() {
		selected++ ;
		if (selected>=3) selected=0;
		updateSelected(String.valueOf(selected)); 
	}
	
	public void before() {
		selected--;
		if (selected<=-1) selected=2;
		updateSelected(String.valueOf(selected)); 
	}
	
	private void updateSelected(String valor) {
		if (valor!=null) {
			if (valor.equals("0")) {
				selected=0;
				src=URL_TRANSFERENCIA;
			} else if (valor.equals("1")) {
				selected=1;
				src=URL_DISTRIBUCION;
			} else if (valor.equals("2")) {
				selected=2;
				src=URL_PAGOS;
			}
		}
	}

	public Date getLastDateTransferencias() {
		return lastDateTransferencias;
	}

	public void setLastDateTransferencias(Date lastDateTransferencias) {
		this.lastDateTransferencias = lastDateTransferencias;
	}

	public Date getLastDateIndicadores() {
		return lastDateIndicadores;
	}

	public void setLastDateIndicadores(Date lastDateIndicadores) {
		this.lastDateIndicadores = lastDateIndicadores;
	}

	public Date getLastDateDistribucion() {
		return lastDateDistribucion;
	}

	public void setLastDateDistribucion(Date lastDateDistribucion) {
		this.lastDateDistribucion = lastDateDistribucion;
	}
	
}