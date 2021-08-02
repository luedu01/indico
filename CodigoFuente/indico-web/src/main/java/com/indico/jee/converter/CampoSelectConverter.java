package com.indico.jee.converter;

import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import com.indico.jee.util.CampoSelect;

@FacesConverter(forClass = CampoSelect.class, value = "campoSelectConverter")
public class CampoSelectConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.trim().equals("")) {
			return null;
		} else {
			String[] valores = submittedValue.split("-");
			try {
				return new CampoSelect(valores[0], valores[1]);
			} catch (Exception fe) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccion Desconocida"));
			}
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null)
			return null;
		else if (value instanceof String && value.equals(""))
			return null;
		else {
			String resultado = "";
			CampoSelect val = (CampoSelect) value;
			resultado = MessageFormat.format("{0}-{1}", val.getValue() , val.getLabel()) ;
			return resultado;
		}
	}
}