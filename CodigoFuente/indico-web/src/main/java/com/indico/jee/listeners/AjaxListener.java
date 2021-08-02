package com.indico.jee.listeners;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;

public class AjaxListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        /*
         * for primefaces
         */
    }

    @Override
    public void afterPhase(PhaseEvent event) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            String facesRequestHeader = httpRequest.getHeader("Faces-Request");
            boolean isAjaxRequest = facesRequestHeader != null && facesRequestHeader.equals("partial/ajax");
            if (isAjaxRequest && (!httpRequest.isRequestedSessionIdValid())) {
               		if ( !httpRequest.getRequestURI().contains("index.xhtml") ) {
                        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
                        handler.performNavigation("inicio");
            		} else if (httpRequest.getRequestURI().contains("login.xhtml") && httpRequest.getDispatcherType().equals(DispatcherType.FORWARD)) {
                        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
                        handler.performNavigation("login");
            		}
            	
            }
   }
}