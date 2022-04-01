package com.indico.jee.bb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SuppressWarnings("deprecation")
@ManagedBean(name="BreadcrumbBean")
@SessionScoped
public class BreadcrumbBean implements Serializable{

	/**
	 *path = ctx.getExternalContext().getRequestContextPath(); 
	 */
	private static final long serialVersionUID = 1L;
	
	private String path;
	
	@PostConstruct
    public void init() {
		setPath(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
