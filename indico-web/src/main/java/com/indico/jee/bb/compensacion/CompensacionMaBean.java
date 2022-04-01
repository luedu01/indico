package com.indico.jee.bb.compensacion;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import com.indico.jndi.ServiceFacades;

@SuppressWarnings("deprecation")
@ManagedBean(name="compensacionMaBean")
@ViewScoped
public class CompensacionMaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompensacionMaBean.class);
	
	private static final String URL_CHEQUES			="./compensacion/cheques.xhtml";
	private static final String URL_COMPORTAMIENTO	="./compensacion/comportamiento.xhtml";
	private static final String URL_DEVOLUCION		="./compensacion/devolucioncanje.xhtml";
	private static final String URL_COMPOSICION		="./compensacion/composicioncanje.xhtml";
	private static final String URL_ANUALIDADPESOS	="./compensacion/chequespesosconstantes.xhtml";
	
	private String src="";
	private int   selected=0;
	
	private Date lastDateCheques;
	private Date lastDateComportamiento;
	private Date lastDateDevolucion;

	public CompensacionMaBean(){
		logger.info("CompensacionMaBean"); 
	}
	
	@PostConstruct
	public void init() {
		//determina que debe iniciar
		selected=0;
		src=URL_CHEQUES;
		lastDateCheques	= ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getLastDate();
		lastDateComportamiento		= ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getLastDate();
		lastDateDevolucion	= ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getLastDate();
		
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
		if (selected>=5) selected=0;
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
				src=URL_CHEQUES;
			} else if (valor.equals("1")) {
				selected=1;
				src=URL_COMPORTAMIENTO;
			} else if (valor.equals("2")) {
				selected=2;
				src=URL_DEVOLUCION;
			} else if (valor.equals("3")) {
				selected=3;
				src=URL_COMPOSICION;
			}else if (valor.equals("4")) {
				selected=4;
				src=URL_ANUALIDADPESOS;
			}
		}
	}

	public Date getLastDateCheques() {
		return lastDateCheques;
	}

	public void setLastDateCheques(Date lastDateCheques) {
		this.lastDateCheques = lastDateCheques;
	}

	public Date getLastDateComportamiento() {
		return lastDateComportamiento;
	}

	public void setLastDateComportamiento(Date lastDateComportamiento) {
		this.lastDateComportamiento = lastDateComportamiento;
	}

	public Date getLastDateDevolucion() {
		return lastDateDevolucion;
	}

	public void setLastDateDevolucion(Date lastDateDevolucion) {
		this.lastDateDevolucion = lastDateDevolucion;
	}
	
}