package com.indico.jee.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.indico.exceptions.IndicoException;
import com.indico.jee.modelo.Series;
import com.indico.jee.util.ValorGraficable;
import com.indico.jndi.ServiceFacades;
import static com.indico.jee.util.Constants.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RestDistribucionValor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestServices.class);
	
	private static final String PARTICIPACION_VALOR_LITERAL = "Participaci\u00f3n - Valor";
	
	/**
	 *  VALOR
	 */
	@GET
	@Path("/distribucionvalor/diaria")
	public Map <Object,Object> getDistribucionValorDiario() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoDiarioValorSG();
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDistribucionDias();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][3];
		
			Series[] valores = new Series [vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);  
			int row=0;
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX();
				ticksA.add(vg.getEjeX());
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getHora();
				//valores[row][2] = vg.getSerieValor().multiply(new BigDecimal(100));
				valores[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieValor().multiply(new BigDecimal(100)));
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) {
	        		minimoValor =vg.getSerieValor();
	        	}
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) {
	        		maximoValor =vg.getSerieValor();
	        	}

				row++;
			}
			//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }

	        /*if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }*/
	        
	        //Asignación de valores procentales al eje vertical de la gráfica
	        minimoValor = minimoValor.multiply(new BigDecimal(100));
	        maximoValor = maximoValor.multiply(new BigDecimal(100));
	        
	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-(dias)];
	        String to   = ticks[ticks.length-1];
	        
	       /* String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-(dias)];*/
	        
			resultado.put(TITLE_LITERAL			, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL		, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		}
			
		return resultado;
	}

	@GET
	@Path("/distribucionvalor/mensual")
	public Map <Object,Object> getDistribucionValorMensual() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoMensualValorSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][3];
			
			Series[] valores = new Series [vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);  
			int row=0;
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX();
				ticksA.add(vg.getEjeX());
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getHora();
				//valores[row][2] = vg.getSerieValor().multiply(new BigDecimal(100));
				valores[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieValor().multiply(new BigDecimal(100)));
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) {
	        		minimoValor =vg.getSerieValor();
	        	}
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) {
	        		maximoValor =vg.getSerieValor();
	        	}
				row++;
			}
			//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }

	       /* if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }*/
	
	      //Asignación de valores procentales al eje vertical de la gráfica
	        minimoValor = minimoValor.multiply(new BigDecimal(100));
	        maximoValor = maximoValor.multiply(new BigDecimal(100));
	       
	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-2];
	        String to   = ticks[ticks.length-1];
	        
	      /*String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2];*/
	        
			resultado.put(TITLE_LITERAL			, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
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
	@Path("/distribucionvalor/trimestral")
	public Map <Object,Object> getDistribucionValorTrimestral() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoTrimestralValorSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][3];
			
			Series[] valores = new Series[vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX();
				ticksA.add(vg.getEjeX());
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getHora();
				//valores[row][2] = vg.getSerieValor().multiply(new BigDecimal(100));
				valores[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieValor().multiply(new BigDecimal(100)));
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) {
	        		minimoValor =vg.getSerieValor();
	        	}
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) {
	        		maximoValor =vg.getSerieValor();
	        	}
				row++;
			}
			//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        
			/*if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }*/

	      //Asignación de valores procentales al eje vertical de la gráfica
	        minimoValor = minimoValor.multiply(new BigDecimal(100));
	        maximoValor = maximoValor.multiply(new BigDecimal(100));
	        
	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-2];
	        String to   = ticks[ticks.length-1];
	        
	       /* String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = (String)ticks[ticks.length-2];
	        String to = (String)ticks[ticks.length-1];*/
	        
			resultado.put(TITLE_LITERAL			, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
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
	@Path("/distribucionvalor/semestral")
	public Map <Object,Object> getDistribucionValorSemestral() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoSemestralValorSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][3];
			
			Series[] valores = new Series [vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX();
				ticksA.add(vg.getEjeX());
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getHora();
				//valores[row][2] = vg.getSerieValor().multiply(new BigDecimal(100));
				valores[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieValor().multiply(new BigDecimal(100)));
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) {
	        		minimoValor =vg.getSerieValor();
	        	}
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) {
	        		maximoValor =vg.getSerieValor();
	        	}
				row++;
			}
			//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	      //Asignación de valores procentales al eje vertical de la gráfica
	        minimoValor = minimoValor.multiply(new BigDecimal(100));
	        maximoValor = maximoValor.multiply(new BigDecimal(100));
	        
	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-2];
	        String to   = ticks[ticks.length-1];
	        
	       /* String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = (String)ticks[ticks.length-2];
	        String to = (String)ticks[ticks.length-1];*/
	        
			resultado.put(TITLE_LITERAL			, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
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
	@Path("/distribucionvalor/anual")
	public Map <Object,Object> getDistribucionValorAnual() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoAnualValorSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][3];
			
			Series[] valores = new Series [vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX()+DATE_PART_LITERAL;
				ticksA.add(vg.getEjeX()+DATE_PART_LITERAL);

				//valores
				//valores[row][0] = vg.getEjeX()+"-01-01";
				//valores[row][1] = vg.getHora();
				//valores[row][2] = vg.getSerieValor().multiply(new BigDecimal(100));
				valores[row] = new Series(vg.getEjeX()+DATE_PART_LITERAL, vg.getHora(), vg.getSerieValor().multiply(new BigDecimal(100)));
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) {
	        		minimoValor =vg.getSerieValor();
	        	}
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) {
	        		maximoValor =vg.getSerieValor();
	        	}
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        //Asignación de valores procentales al eje vertical de la gráfica
	        minimoValor = minimoValor.multiply(new BigDecimal(100));
	        maximoValor = maximoValor.multiply(new BigDecimal(100));

	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-2];
	        String to   = ticks[ticks.length-1];
	        
			
	        /*String minX = (String)ticks[0];
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = (String)ticks[ticks.length-2];
	        String to = (String)ticks[ticks.length-1];*/

			resultado.put(TITLE_LITERAL			, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor);
			resultado.put(SERIE_VALORES_LITERAL	, valores);
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
