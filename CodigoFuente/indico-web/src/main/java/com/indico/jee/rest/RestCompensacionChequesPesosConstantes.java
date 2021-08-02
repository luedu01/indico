package com.indico.jee.rest;

import static com.indico.jee.util.Constants.DIVISORMM;
import static com.indico.jee.util.Constants.ENDX_LITERAL;
import static com.indico.jee.util.Constants.ERROR_LITERAL;
import static com.indico.jee.util.Constants.MAXX_LITERAL;
import static com.indico.jee.util.Constants.MAX_CANTIDAD_LITERAL;
import static com.indico.jee.util.Constants.MAX_VALOR_LITERAL;
import static com.indico.jee.util.Constants.MINUS_NUMBER_LITERAL;
import static com.indico.jee.util.Constants.MINX_LITERAL;
import static com.indico.jee.util.Constants.MIN_CANTIDAD_LITERAL;
import static com.indico.jee.util.Constants.MIN_VALOR_LITERAL;
import static com.indico.jee.util.Constants.NUMBER_LITERAL;
import static com.indico.jee.util.Constants.SERIE_CANTIDAD_LITERAL;
import static com.indico.jee.util.Constants.SERIE_VALORES_LITERAL;
import static com.indico.jee.util.Constants.STARTX_LITERAL;
import static com.indico.jee.util.Constants.TICKS_LITERAL;
import static com.indico.jee.util.Constants.TITLE_LITERAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.indico.exceptions.IndicoException;
import com.indico.jee.util.ValorGraficable;
import com.indico.jndi.ServiceFacades;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RestCompensacionChequesPesosConstantes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat receiveFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestCompensacionChequesPesosConstantes.class);
	
	private final String CHEQUES_PESOS_LITERAL = "Comportamiento Anualizado del Canje de Cheques (Pesos constantes IPC Año ";
	
	int year = Year.now().getValue()-1;
	
	/**
	 * Metodo que devuelve los cheques en pesos constantes
	 * @return
	 * @throws IndicoException
	 */
	@GET
	@Path("/compChequesPesosConstantes/")
	public Map <Object,Object> getCompChequesPesos() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPesoContantesService().getChequesPesosAll();
			
			if (vgl!=null && !vgl.isEmpty()) {
				Integer dias = 365;
				//valores y cantidad
				Object[][] valores = new Object[vgl.size()][4];
				Object[][] cantidad = new  Object[vgl.size()][4];
				Object[] ticks = new  Object[vgl.size()];
		        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
		        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
		        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
		        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
				int row=0;
				for (ValorGraficable vg : vgl) {
					ticks[row]=vg.getEjeX();
					
					//cantidad
					valores[row][0] = vg.getEjeX();
					valores[row][1] = vg.getSerieValor().divide(DIVISORMM);
					valores[row][2] = vg.getValorIPC();
										
					//valores
					cantidad[row][0] = vg.getEjeX();
					cantidad[row][1] = vg.getSerieCantidad();
					cantidad[row][2] = vg.getValorIPC();
					
					if(vg.getVariacionPor() ==  null) {
						valores[row][3] = BigDecimal.ZERO;
						cantidad[row][3] = BigDecimal.ZERO;
					}else {
						valores[row][3] = vg.getVariacionPor();
						cantidad[row][3] = vg.getVariacionPor();
					}
					
					//
		        	if (0>vg.getSerieValor().divide(DIVISORMM).compareTo(minimoValor)) {
		        		minimoValor =vg.getSerieValor().divide(DIVISORMM);
		        	}
		        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) {
		        		minimoCantidad =vg.getSerieCantidad();
		        	}
		        	if (0<vg.getSerieValor().divide(DIVISORMM).compareTo(maximoValor)) {
		        		maximoValor =vg.getSerieValor().divide(DIVISORMM);
		        	}
		        	if (0<vg.getSerieCantidad().compareTo(maximoCantidad)) {
		        		maximoCantidad =vg.getSerieCantidad();
		        	}
					row++;
				}//for
				//asignacion de valores minimos.
		        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) && (minimoCantidad.compareTo(BigDecimal.ZERO)>0) ) {
		        	minimoValor = BigDecimal.ZERO;
		        	minimoCantidad = BigDecimal.ZERO;
		        }
		        //minimos y maximos en x
		        String to = (String)ticks[ticks.length-1];
		        dias = ticks.length-dias;
		        String from = (String)ticks[dias<0?0:dias];
		        String maxX = (String)ticks[ticks.length-1];
		        String minX = (String)ticks[0];
		        if (to.equals(from)) {
		        	synchronized (receiveFormat) {
		            	Calendar cal = Calendar.getInstance();
		            	Date fechaInicial = receiveFormat.parse(from);
		            	cal.setTime(fechaInicial);
		            	cal.add(Calendar.DAY_OF_MONTH,-5);
		            	from = receiveFormat.format(cal.getTime());
		            	minX = from;
		        	}
		        }
		        //
				resultado.put(TITLE_LITERAL			, CHEQUES_PESOS_LITERAL+year+")");
				resultado.put(TICKS_LITERAL			, ticks);
				resultado.put(MIN_VALOR_LITERAL		, minimoValor);
				resultado.put(MIN_CANTIDAD_LITERAL	, minimoCantidad);
				resultado.put(MAX_VALOR_LITERAL		, maximoValor);
				resultado.put(MAX_CANTIDAD_LITERAL	, maximoCantidad);
				resultado.put(SERIE_VALORES_LITERAL	, valores);
				resultado.put(SERIE_CANTIDAD_LITERAL, cantidad);
				resultado.put(MINX_LITERAL			, minX);
				resultado.put(MAXX_LITERAL			, maxX);
				resultado.put(STARTX_LITERAL		, from);
				resultado.put(ENDX_LITERAL			, to);
		    }
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}	
}