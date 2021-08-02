package com.indico.jee.servicesi;

import java.util.List;

import javax.ejb.Local;

import com.indico.jee.modelo.AnalisisChequesPesosConstantes;
import com.indico.jee.util.ValorGraficable;

@Local
public interface AnalisisPesoContantesService {
	
	List<ValorGraficable> getChequesPesosAll();
	
	
	/* Data Export */
	List<AnalisisChequesPesosConstantes> getDataPesosConstantes();

}
