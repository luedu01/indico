package com.indico.jee.bb;

import java.io.Serializable;

public class Indicador  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String subTitle;
	private String urlImagen;
	private String formulario;
	
	public Indicador(String id, String title, String subTitle, String urlImagen, String action) {
		this.id = id;
		this.title = title;
		this.subTitle = subTitle;
		this.urlImagen = urlImagen;
		this.formulario = action;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getFormulario() {
		return formulario;
	}

	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}
	
	
}
