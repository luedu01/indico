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
import static com.indico.jee.util.Constants.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RestCompensacionComportamientoServices implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat receiveFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestCompensacionComportamientoServices.class);
	
	
	private static final String CANJE_COBRO_LITERAL = "Canje al Cobro";
	private static final String DEVOLUCIONES_LITERAL = "Devoluciones";
	private static final String DEVOLUCION_CANJE_LITERAL = "Devolución al Canje";
		
	
	/**
	 * Metodo que devuelve el comportamiento del canje
	 * @param tipodeplaza
	 * @param ciudad
	 * @param periodo
	 * @return
	 * @throws IndicoException
	 */
	@GET
	@Path("/compComportamientoCanjeService/{tipodeplaza}/{ciudad}/{periodo}")
	public Map <Object,Object> getComportamientoCanje(@PathParam("tipodeplaza") String tipodeplaza,@PathParam(CIUDAD_LITERAL) String ciudad, @PathParam("periodo") String periodo) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getComportamientoCanjeAll(tipodeplaza, ciudad, periodo);
			
			if (vgl!=null && !vgl.isEmpty()) {
				Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
				//valores y cantidad
				Object[][] valores = new Object[vgl.size()][2];
				Object[][] valores2 = new  Object[vgl.size()][2];
				Object[][] cantidad = new  Object[vgl.size()][2];
				Object[][] cantidad2 = new  Object[vgl.size()][2];
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
					valores2[row][0] = vg.getEjeX();
					valores2[row][1] = vg.getSerieValor().divide(DIVISORM);
					//valores
					cantidad[row][0] = vg.getEjeX();
					cantidad[row][1] = vg.getSerieCantidad();
					//
					cantidad2[row][0] = vg.getEjeX();
					cantidad2[row][1] = vg.getSerieCantidad().divide(DIVISORC);
					
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
				resultado.put(TITLE_LITERAL			, CANJE_COBRO_LITERAL);
				resultado.put(TICKS_LITERAL			, ticks);
				resultado.put(MIN_VALOR_LITERAL		, minimoValor);
				resultado.put(MIN_CANTIDAD_LITERAL	, minimoCantidad);
				resultado.put(MAX_VALOR_LITERAL		, maximoValor);
				resultado.put(MAX_CANTIDAD_LITERAL	, maximoCantidad);
				resultado.put(SERIE_VALORES_LITERAL	, valores);
				resultado.put(SERIE_VALORES_LITERAL2	, valores2);
				resultado.put(SERIE_CANTIDAD_LITERAL2, cantidad2);
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

	/**
	 * Devoluciones
	 * @param tipodeplaza
	 * @param ciudad
	 * @param periodo
	 * @return
	 * @throws IndicoException
	 */
	@GET
	@Path("/compComportamientoDevolService/{tipodeplaza}/{ciudad}/{periodo}")
	public Map <Object,Object> getComportamientoDevolucion(@PathParam("tipodeplaza") String tipodeplaza,@PathParam(CIUDAD_LITERAL) String ciudad, @PathParam("periodo") String periodo) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getComportamientoDevolucionAll(tipodeplaza, ciudad, periodo);
			
			if (vgl!=null && !vgl.isEmpty()) {
				Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
				//valores y cantidad
				Object[][] valores = new Object[vgl.size()][2];
				Object[][] valores2 = new  Object[vgl.size()][2];
				Object[][] cantidad = new  Object[vgl.size()][2];
				Object[][] cantidad2 = new  Object[vgl.size()][2];
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
					valores2[row][0] = vg.getEjeX();
					valores2[row][1] = vg.getSerieValor().divide(DIVISORM);
					//valores
					cantidad[row][0] = vg.getEjeX();
					cantidad[row][1] = vg.getSerieCantidad();
					//
					cantidad2[row][0] = vg.getEjeX();
					cantidad2[row][1] = vg.getSerieCantidad().divide(DIVISORC);
				
					
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
		        //
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
		        
				resultado.put(TITLE_LITERAL			, DEVOLUCIONES_LITERAL);
				resultado.put(TICKS_LITERAL			, ticks);
				resultado.put(MIN_VALOR_LITERAL		, minimoValor);
				resultado.put(MIN_CANTIDAD_LITERAL	, minimoCantidad);
				resultado.put(MAX_VALOR_LITERAL		, maximoValor);
				resultado.put(MAX_CANTIDAD_LITERAL	, maximoCantidad);
				resultado.put(SERIE_VALORES_LITERAL	, valores);
				resultado.put(SERIE_VALORES_LITERAL2	, valores2);
				resultado.put(SERIE_CANTIDAD_LITERAL2, cantidad2);
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

	/**
	 * Metodos de Devolucion por Canje
	 * @param medioservicio
	 * @return
	 * @throws IndicoException
	 */
	@GET
	@Path("/devolucionCanjeService/{medioservicio}/{periodo}")
	public Map <Object,Object> getCompDevolucionCanje(@PathParam("medioservicio") String medioservicio, @PathParam("periodo") String periodo) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getDevolucionCanjeAll(medioservicio, periodo);
						
			if (vgl!=null && !vgl.isEmpty()) {
				Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
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
					valores[row][1] = vg.getSerieValorPorcentaje();
					//valores
					cantidad[row][0] = vg.getEjeX();
					cantidad[row][1] = vg.getSerieCantidadPorcentaje();
					//
		        	if (0>vg.getSerieValorPorcentaje().compareTo(minimoValor)) {
		        		minimoValor =vg.getSerieValorPorcentaje();
		        	}
		        	if (0>vg.getSerieCantidadPorcentaje().compareTo(minimoCantidad)) {
		        		minimoCantidad =vg.getSerieCantidadPorcentaje();
		        	}
		        	if (0<vg.getSerieValorPorcentaje().compareTo(maximoValor)) {
		        		maximoValor =vg.getSerieValorPorcentaje();
		        	}
		        	if (0<vg.getSerieCantidadPorcentaje().compareTo(maximoCantidad)) {
		        		maximoCantidad =vg.getSerieCantidadPorcentaje();
		        	}
					row++;
				}//for
				//asignacion de valores minimos.
		        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) && (minimoCantidad.compareTo(BigDecimal.ZERO)>0) ) {
		        	minimoValor = BigDecimal.ZERO;
		        	minimoCantidad = BigDecimal.ZERO;
		        }
		        
		        maximoCantidad 	= maximoCantidad.add(MAXGRAFICA);
		        maximoValor 	= maximoValor.add(MAXGRAFICA);
		        
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
				resultado.put(TITLE_LITERAL					, DEVOLUCION_CANJE_LITERAL);
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
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	
}
