package com.indico.jee.servicesi;

import java.util.List;
import javax.ejb.Local;
import com.indico.jee.modelo.MedioServCompensacion;

@Local
public interface MedioServCompensacionService {
	
	List<MedioServCompensacion> finAll();

}	
