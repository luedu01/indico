package com.indico.jee.servicesi;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import com.indico.jee.modelo.AnalisisChequesCompensa;
import com.indico.jee.util.ValorGraficable;

@Local
public interface AnalisisChequesCompensaService {
	
	Date getLastDate(); 
	
	List<AnalisisChequesCompensa> finAll();
	
	BigInteger countAll();
	
	Map<String,String> getCiudadesCanje (String medio);

	Map<String,String> getCiudadesDevol (String medio);
	
	List<ValorGraficable> getCompenDiarioGR();
	
	List<ValorGraficable> getCompenDiariaAllGR(String medio);

	List<ValorGraficable> getCompenMensualGR();
	
	List<ValorGraficable> getCompenMensualAllGR(String medio);

	List<ValorGraficable> getCompenTrimestralGR();
	
	List<ValorGraficable> getCompenTrimestralAllGR(String medio);

	List<ValorGraficable> getCompenSemestralGR();
	
	List<ValorGraficable> getCompenSemestralAllGR(String medio);

	List<ValorGraficable> getCompenAnualGR();
	
	List<ValorGraficable> getCompenAnualAllGR(String medio);
	
	List<ValorGraficable> getCompenDevolDiarioGR();
	
	List<ValorGraficable> getCompenDevolDiariaAllGR(String medio);

	List<ValorGraficable> getCompenDevolMensualGR();
	
	List<ValorGraficable> getCompenDevolMensualAllGR(String medio);

	List<ValorGraficable> getCompenDevolTrimestralGR();
	
	List<ValorGraficable> getCompenDevolTrimestralAllGR(String medio);

	List<ValorGraficable> getCompenDevolSemestralGR();
	
	List<ValorGraficable> getCompenDevolSemestralAllGR(String medio);

	List<ValorGraficable> getCompenDevolAnualGR();
	
	List<ValorGraficable> getCompenDevolAnualAllGR(String medio);
	
	List<AnalisisChequesCompensa> getDataDiarioDevolTodas(Date fechaInicial, Date fechaFinal,int tipo);
	
	List<AnalisisChequesCompensa> getDataDiarioDevol(Date fechaInicial, Date fechaFinal , String medio,int tipo);
	
	List<AnalisisChequesCompensa> getDataDiarioCompenTodas(Date fechaInicial, Date fechaFinal,int tipo);
	
	List<AnalisisChequesCompensa> getDataDiarioCompen(Date fechaInicial, Date fechaFinal , String medio,int tipo);
	
	/* Comportamiento Canje */
	
	List<ValorGraficable> getComportamientoCanjeAll (String tipodeplaza, String ciudad, String periodo);
		
	/* Comportamiento Devoluciones */
	
	List<ValorGraficable> getComportamientoDevolucionAll (String tipodeplaza, String ciudad, String periodo);
	
	/* Compartamiento Devolución Canje */
	
	/* Data Export */
	List<AnalisisChequesCompensa> getDataDiarioDevolCanjeTodas(Date fechaInicial, Date fechaFinal,int tipo);
	
	List<AnalisisChequesCompensa> getDataDiarioDevolCanje(Date fechaInicial, Date fechaFinal , String medio,int tipo);
	
	/* graficas */
	
	List<ValorGraficable> getDevolucionCanjeAll(String medio, String periodo);

}	
