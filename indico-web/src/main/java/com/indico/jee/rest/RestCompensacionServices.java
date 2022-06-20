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
import static com.indico.jee.util.Constants.TODAS_LITERAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.indico.exceptions.IndicoException;
import com.indico.jee.modelo.CanjealCobro;
import com.indico.jee.util.ValorGraficable;
import com.indico.jndi.ServiceFacades;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RestCompensacionServices implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestServices.class);
	

	
	@GET
	@Path("/compensacionService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionDiaria(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDiarioGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDiariaAllGR(tipodeplaza);
			}
			if (vgl!=null && !vgl.isEmpty()) {
				Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
				//valores y cantidad
				//Object[][] valores = new Object[vgl.size()][5];
				CanjealCobro[] valores = new CanjealCobro[vgl.size()];
				Object[][] cantidad = new  Object[vgl.size()][5];
				Object[] ticks = new  Object[vgl.size()];
		        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
		        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
		        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
		        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
		        BigDecimal valor;
				int row=0;
				for (ValorGraficable vg : vgl) {
					ticks[row]=vg.getEjeX();
					//valores
					//valores[row][0] = vg.getTipo();
					//valores[row][1] = vg.getEjeX();
					//valores[row][2] = vg.getCiudad();
					//valores[row][3] = vg.getSerieValorPorcentaje();
					//valores[row][4] = vg.getSerieValor().divide(DIVISORMM);
					valor = vg.getSerieValor().divide(DIVISORMM);
					
					valores[row] = new CanjealCobro(vg.getTipo(),
					vg.getEjeX(),vg.getCiudad(),vg.getSerieValorPorcentaje(),valor);
					//cantidad
					cantidad[row][0] = vg.getTipo();
					cantidad[row][1] = vg.getEjeX();
					cantidad[row][2] = vg.getCiudad();
					cantidad[row][3] = vg.getSerieCantidadPorcentaje().setScale(4, RoundingMode.DOWN);
					cantidad[row][4] = vg.getSerieCantidad().setScale(4, RoundingMode.DOWN);
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

	@GET
	@Path("/compensacionMensualService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionMensual(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenMensualGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenMensualAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][5];
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			BigDecimal valor;
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getTipo();
				//valores[row][1] = vg.getEjeX();
				//valores[row][2] = vg.getCiudad();
				//valores[row][3] = vg.getSerieValorPorcentaje();
				//valores[row][4] = vg.getSerieValor().divide(DIVISORMM);
				
				valor = vg.getSerieValor().divide(DIVISORMM);			
				valores[row] = new CanjealCobro(vg.getTipo(),
				vg.getEjeX(),vg.getCiudad(),vg.getSerieValorPorcentaje(),valor);
				
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje().setScale(4, RoundingMode.DOWN);
				cantidad[row][4] = vg.getSerieCantidad().setScale(4, RoundingMode.DOWN);
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	@GET
	@Path("/compensacionTrimestralService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionTrimestral(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenTrimestralGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenTrimestralAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][5];
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			BigDecimal valor;
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getTipo();
				//valores[row][1] = vg.getEjeX();
				//valores[row][2] = vg.getCiudad();
				//valores[row][3] = vg.getSerieValorPorcentaje();
				//valores[row][4] = vg.getSerieValor().divide(DIVISORMM);
				valor = vg.getSerieValor().divide(DIVISORMM);
				
				valores[row] = new CanjealCobro(vg.getTipo(),
				vg.getEjeX(),vg.getCiudad(),vg.getSerieValorPorcentaje(),valor);
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje().setScale(4, RoundingMode.DOWN);
				cantidad[row][4] = vg.getSerieCantidad().setScale(4, RoundingMode.DOWN);
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	@GET
	@Path("/compensacionSemestralService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionSemestral(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenSemestralGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenSemestralAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][5];
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			BigDecimal valor;
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getTipo();
				//valores[row][1] = vg.getEjeX();
				//valores[row][2] = vg.getCiudad();
				//valores[row][3] = vg.getSerieValorPorcentaje();
				//valores[row][4] = vg.getSerieValor().divide(DIVISORMM);
				valor = vg.getSerieValor().divide(DIVISORMM);
				
				valores[row] = new CanjealCobro(vg.getTipo(),
				vg.getEjeX(),vg.getCiudad(),vg.getSerieValorPorcentaje(),valor);
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje().setScale(4, RoundingMode.DOWN);
				cantidad[row][4] = vg.getSerieCantidad().setScale(4, RoundingMode.DOWN);
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	@GET
	@Path("/compensacionAnualService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionAnual(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenAnualGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenAnualAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][5];
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);  
	        BigDecimal valor;
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getTipo();
				//valores[row][1] = vg.getEjeX();
				//valores[row][2] = vg.getCiudad();
				//valores[row][3] = vg.getSerieValorPorcentaje();
				//valores[row][4] = vg.getSerieValor().divide(DIVISORMM);
				valor = vg.getSerieValor().divide(DIVISORMM);
				//valores[row][4] = valor.setScale(2, RoundingMode.FLOOR);
				valores[row] = new CanjealCobro(vg.getTipo(),
				vg.getEjeX(),vg.getCiudad(),vg.getSerieValorPorcentaje(),valor);
				
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje().setScale(4, RoundingMode.DOWN);
				cantidad[row][4] = vg.getSerieCantidad().setScale(4, RoundingMode.DOWN);
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}
	
	/**
	 * Métodos de Devolucion de Cheques
	 * @param tipodeplaza
	 * @return
	 * @throws IndicoException
	 */
	@GET
	@Path("/compensacionDevolService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionDevolDiaria(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();
		try { 
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolDiarioGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolDiariaAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
			BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
			BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
			BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				valores[row] = new CanjealCobro(vg.getTipo(),
							vg.getEjeX()
							,vg.getCiudad()
							,vg.getSerieValorPorcentaje()
							,vg.getSerieValor().divide(DIVISORMM));
				
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje();
				cantidad[row][4] = vg.getSerieCantidad();
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	@GET
	@Path("/compensacionMensualDevolService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionDevolMensual(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolMensualGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolMensualAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				valores[row] = new CanjealCobro(vg.getTipo(),
							vg.getEjeX()
							,vg.getCiudad()
							,vg.getSerieValorPorcentaje()
							,vg.getSerieValor().divide(DIVISORMM));
				
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje();
				cantidad[row][4] = vg.getSerieCantidad();
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	@GET
	@Path("/compensacionTrimestralDevolService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionDevolTrimestral(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolTrimestralGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolTrimestralAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				valores[row] = new CanjealCobro(vg.getTipo(),
						vg.getEjeX()
						,vg.getCiudad()
						,vg.getSerieValorPorcentaje()
						,vg.getSerieValor().divide(DIVISORMM));
				
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje();
				cantidad[row][4] = vg.getSerieCantidad();
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	@GET
	@Path("/compensacionSemestralDevolService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionDevolSemestral(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolSemestralGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolSemestralAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				valores[row] = new CanjealCobro(vg.getTipo(),
							vg.getEjeX()
							,vg.getCiudad()
							,vg.getSerieValorPorcentaje()
							,vg.getSerieValor().divide(DIVISORMM));
				
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje();
				cantidad[row][4] = vg.getSerieCantidad();
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}

	@GET
	@Path("/compensacionAnualDevolService/{tipodeplaza}")
	public Map <Object,Object> getCompensacionDevolAnual(@PathParam("tipodeplaza") String tipodeplaza) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (tipodeplaza==null || tipodeplaza.equals(TODAS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolAnualGR();
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenDevolAnualAllGR(tipodeplaza);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			CanjealCobro[] valores = new CanjealCobro[vgl.size()];
			Object[][] cantidad = new  Object[vgl.size()][5];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				valores[row] = new CanjealCobro(vg.getTipo(),
							vg.getEjeX()
							,vg.getCiudad()
							,vg.getSerieValorPorcentaje()
							,vg.getSerieValor().divide(DIVISORMM));
				
				//cantidad
				cantidad[row][0] = vg.getTipo();
				cantidad[row][1] = vg.getEjeX();
				cantidad[row][2] = vg.getCiudad();
				cantidad[row][3] = vg.getSerieCantidadPorcentaje();
				cantidad[row][4] = vg.getSerieCantidad();
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
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
	}
}
