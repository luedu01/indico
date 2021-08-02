package com.indico.jee.bb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class CarouselView implements Serializable {


	private static final long serialVersionUID = 1L;

	private int iniciarEn = 0;

	private List<Indicador> indicadores;

	private Indicador selectedInd;

	private int indLong = 0;
	
	private int numeroItems = 2;

	@ManagedProperty("#{indicadorService}")
	private IndicadorService service;

	@PostConstruct
	public void init() {
		indicadores = service.createIndicador(2, iniciarEn);
		indLong = service.getFullSize();
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public IndicadorService getService() {
		return service;
	}

	public void setService(IndicadorService service) {
		this.service = service;
	}

	public Indicador getSelectedInd() {
		return selectedInd;
	}

	public void setSelectedInd(Indicador selectedInd) {
		this.selectedInd = selectedInd;
	}

	public void editLeft() {
		if (iniciarEn == 0) {
			iniciarEn = indLong - 1;
			indicadores = service.createIndicador(1, indLong - 1);
		} else {
			iniciarEn = iniciarEn - 1;
			indicadores = service.createIndicador(1, iniciarEn - 1);
		}
	}

	public void proximaPagina() {
		String script = " var currentPage = PF('carouselW').page; "
				+ " var totalPages = PF('carouselW').totalPages - 1; "
				+ " var c = (currentPage === (totalPages)); " + " if (!c) { "
				+ "  PF('carouselW').setPage(currentPage + 1); " + 
				"} else PF('carouselW').setPage(0);";
		PrimeFaces.current().executeScript(script);
	}
	
	public void anteriorPagina() {
		String script = " var currentPage = PF('carouselW').page; "
				+ " var totalPages = PF('carouselW').totalPages; "
				+ " var c = (currentPage === 0); " + " if (!c) { "
				+ "  PF('carouselW').setPage(currentPage - 1); " + 
				"} else PF('carouselW').setPage(totalPages - 1);";
		PrimeFaces.current().executeScript(script);
	}
	
	public void ejecutarActionItemDesktop() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		int valorItem = Integer.parseInt(contexto.getExternalContext().getRequestParameterMap().get("valueItem"));
		if(valorItem < this.indicadores.size()) {
			this.numeroItems = valorItem;
			String execute = "$('#NextView').css('display', 'block');";
			execute = execute + "$('#PrevView').css('display', 'block');";
			PrimeFaces.current().executeScript(execute);
		} else if (valorItem == this.indicadores.size()){
			String execute = "$('#NextView').css('display', 'none');";
			execute = execute + "$('#PrevView').css('display', 'none');";
			PrimeFaces.current().executeScript(execute);
			this.numeroItems = valorItem;
		}
	}
	
	public String redireccionarPagina(String formulario) {
		return formulario;
	}

	public int getIniciarEn() {
		return iniciarEn;
	}

	public void setIniciarEn(int iniciarEn) {
		this.iniciarEn = iniciarEn;
	}

	public int getNumeroItems() {
		return numeroItems;
	}

	public void setNumeroItems(int numeroItems) {
		this.numeroItems = numeroItems;
	}

}
