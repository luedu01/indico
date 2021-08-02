package com.indico.jee.servicesi;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.indico.jee.modelo.AnalisisTransaccionCNI;
import com.indico.jee.util.ValorGraficable;

@Local
public interface AnalisisTransaccionCNIService {
	
	/* Data Export */
	List<AnalisisTransaccionCNI> getDataDiarioTransaccionCNI(Date fechaInicial, Date fechaFinal);
	
	/* graficas */
	List<ValorGraficable> getTransaccionCNIALL(String periodo);
}
