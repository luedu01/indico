package com.indico.jee.servicesi;

import java.util.List;
import javax.ejb.Local;
import com.indico.jee.modelo.Anios;

@Local
public interface AniosService {

	public List<Anios> finAll();
	
}
