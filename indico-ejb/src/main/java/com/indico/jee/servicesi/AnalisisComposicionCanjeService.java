package com.indico.jee.servicesi;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.indico.jee.modelo.AnalisisComposicionCanje;
import com.indico.jee.util.ValorGraficable;

@Local
public interface AnalisisComposicionCanjeService {
	
	Date getLastDate(); 
	
	List<AnalisisComposicionCanje> finAll();

	List<ValorGraficable> getComposicionCanjeAll(String idperiodo);
	
	List<ValorGraficable> getComposicionCanjeByRango(String idperiodo, String[] idRangoCanje);
	
	/* Data Export */
	List<AnalisisComposicionCanje> getDataDiarioCompoCanjeTodas(int fechaInicial, int fechaFinal,int tipo);
	
	List<AnalisisComposicionCanje> getDataDiarioCompoCanje(int fechaInicial, int fechaFinal , String[] rangoArray,int tipo);
}
