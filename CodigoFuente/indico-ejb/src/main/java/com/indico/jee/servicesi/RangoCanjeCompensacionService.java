package com.indico.jee.servicesi;

import java.util.List;
import javax.ejb.Local;
import com.indico.jee.modelo.RangoCanjeCompensacion;

@Local
public interface RangoCanjeCompensacionService {
	
	List<RangoCanjeCompensacion> findAllRangos();
	
}
