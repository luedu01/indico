package com.indico.jee.servicesi;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

import com.indico.jee.modelo.ValorAnualIPIB;

@Local
public interface ValorAnualIPIBService {

	public List<ValorAnualIPIB> finAll();
	
	public BigInteger countAll();
}
