package com.indico.jee.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.indico.exceptions.IndicoException;
import com.indico.jee.util.ValorGraficable;
import com.indico.jndi.ServiceFacades;

import static com.indico.jee.util.Constants.ERROR_LITERAL;
import static com.indico.jee.util.Constants.NUMBER_LITERAL;
import static com.indico.jee.util.Constants.MINUS_NUMBER_LITERAL;
import static com.indico.jee.util.Constants.MAXGRAFICA;
import static com.indico.jee.util.Constants.TITLE_LITERAL;
import static com.indico.jee.util.Constants.TICKS_LITERAL;
import static com.indico.jee.util.Constants.MIN_VALOR_LITERAL;
import static com.indico.jee.util.Constants.MIN_CANTIDAD_LITERAL;
import static com.indico.jee.util.Constants.MAX_VALOR_LITERAL;
import static com.indico.jee.util.Constants.MAX_CANTIDAD_LITERAL;
import static com.indico.jee.util.Constants.SERIE_VALOR_PRCT_LITERAL;
import static com.indico.jee.util.Constants.SERIE_CANTIDAD_PRCT_LITERAL;
import static com.indico.jee.util.Constants.MINX_LITERAL;
import static com.indico.jee.util.Constants.MAXX_LITERAL;
import static com.indico.jee.util.Constants.STARTX_LITERAL;
import static com.indico.jee.util.Constants.ENDX_LITERAL;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)

public class RestTransaccionCNI  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestServices.class);
	private SimpleDateFormat receiveFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final String TRANSACCION_CNI_LITERAL = "Transferencias electrónicas interbancarias de bajo valor";
	
	@GET
	@Path("/analisisTransaccionCNIService/{periodo}")
	public Map <Object,Object> getTransaccionCNI(@PathParam("periodo") String periodo) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getTransaccionCNIService().getTransaccionCNIALL(periodo);
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			if (vgl!=null && !vgl.isEmpty()) {
			
				//valores y cantidad
				Object[][] valores = new Object[vgl.size()][2];
				Object[][] cantidad = new  Object[vgl.size()][2];
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
					valores[row][1] = vg.getSerieValor();
					//valores
					cantidad[row][0] = vg.getEjeX();
					cantidad[row][1] = vg.getSerieCantidad();
					//
					if (0>vg.getSerieValor().compareTo(minimoValor)) {
		        		minimoValor =vg.getSerieValor();
		        	}
		        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) {
		        		minimoCantidad =vg.getSerieCantidad();
		        	}
		        	if (0<vg.getSerieValor().compareTo(maximoValor)) {
		        		maximoValor =vg.getSerieValor();
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
		     
		      //
				resultado.put(TITLE_LITERAL					, TRANSACCION_CNI_LITERAL);
				resultado.put(TICKS_LITERAL					, ticks);
				resultado.put(MIN_VALOR_LITERAL				, minimoValor);
				resultado.put(MIN_CANTIDAD_LITERAL			, minimoCantidad);
				resultado.put(MAX_VALOR_LITERAL				, maximoValor);
				resultado.put(MAX_CANTIDAD_LITERAL			, maximoCantidad);
				resultado.put(SERIE_VALOR_PRCT_LITERAL		, valores);
				resultado.put(SERIE_CANTIDAD_PRCT_LITERAL	, cantidad);
				resultado.put(MINX_LITERAL					, minX);
				resultado.put(MAXX_LITERAL					, maxX);
				resultado.put(STARTX_LITERAL				, from);
				resultado.put(ENDX_LITERAL					, to);
			}
		} catch (Exception e) {
			//System.out.println(e);
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}
}
