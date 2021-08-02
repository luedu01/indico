package com.indico.jee.util;

import java.io.Serializable;

public class CampoSelect implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String label;
	private String value;
	
	
	public CampoSelect(String label, String grupo, String subgrupo) {
		super();
		this.label = label;
		this.value = String.format("%05d", Integer.parseInt(grupo)) + String.format("%05d", Integer.parseInt(subgrupo));
	}

	public CampoSelect(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode () {
		int result = 17;
		int resultA=0;
		if (value==null) {
			resultA=0;
		} else {
			resultA = value.hashCode();
		}
		result = 31 * result + resultA;
		return result; 
	}
	
	@Override
	public boolean equals ( Object obj) {
		if (this==obj) return true; 
		if (obj==null) return false;
		if (!(obj instanceof CampoSelect)) return false;
		if (((CampoSelect)obj).getValue()==null) return false;
		if (value==null) return false;
		return value.equals(((CampoSelect)obj).getValue());
	}
	
	@Override
	public String toString() {
		return "CampoSelect [label=" + label + ", value=" + value + "]";
	}

}
