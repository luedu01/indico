package com.indico.jee.rest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.indico.exceptions.IndicoException;
import com.indico.jee.modelo.Series;
import com.indico.jee.util.ValorGraficable;
import com.indico.jndi.ServiceFacades;
import static com.indico.jee.util.Constants.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RestServices implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestServices.class);
	
	
	private static final String SERIE_CANTIDAD_LITERAL =	"SerieCantidad";
	//private BigDecimal SerieCantidad[][];
	private static final String TRANSACCIONES_CUD_LITERAL="Transacciones liquidadas en CUD";
	private static final String PARTICIPACION_VALOR_LITERAL="Participaci\u00f3n - Valor";
	private static final String PARTICIPACION_CANTIDAD_LITERAL="Participaci\u00f3n - Cantidad";
	private static final String INDICADORES_PAGOS_PIB_LITERAL="Indicadores de Pagos - PIB";
	private static final String INDICADORES_PAGOS_ROTACION_LITERAL="Indicadores de Pagos - Rotaci\u00f3n";
	
	
	/**
	 *  TRANSFERENCIA 
	 **/
	@GET
	@Path("/transferenciasService/{concepto}")
	public Map <Object,Object> getTransferencias(@PathParam(CONCEPTO_LITERAL) String concepto) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();
		try {
			List<ValorGraficable> vgl = null ; 
			if (concepto==null || concepto.equals(TODOS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getAllDiarioComplete();
			} else {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getAllConjuntoDiarioByGR(concepto);
			}
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			//Object[][] cantidad = new  Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Series[] cantidad = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				
				//cantidad
				//cantidad[row][0] = vg.getEjeX();
				//cantidad[row][1] = vg.getSerieCantidad();
				cantidad[row] = new Series(vg.getEjeX(), vg.getSerieCantidad());
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) minimoCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
	        	if (0<vg.getSerieCantidad().compareTo(maximoCantidad)) maximoCantidad =vg.getSerieCantidad();
				row++;
			}//for
			//Asignación de valores minimos y maximos.
			if ((minimoValor.compareTo(BigDecimal.ZERO)>0)) {
	        	minimoValor = BigDecimal.ZERO;
	        }
			if((minimoCantidad.compareTo(BigDecimal.ZERO)>0)){
				minimoCantidad = BigDecimal.ZERO;
			}
			if(minimoValor.compareTo(maximoValor)==0){
				maximoValor = maximoValor.add(BigDecimal.ONE);
			}
			if(minimoCantidad.compareTo(maximoCantidad)==0){
				maximoCantidad = maximoCantidad.add(BigDecimal.ONE);
			}
	
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        dias = ticks.length-dias;
	        String from = (String)ticks[dias<0?0:dias];
	        String maxX = (String)ticks[ticks.length-1];
	        String minX = (String)ticks[0];
	        //
			resultado.put(TITLE_LITERAL			, TRANSACCIONES_CUD_LITERAL);
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
	@Path("/transferenciasMensualesService/{concepto}")
	public Map <Object,Object> getTransferenciasMensuales(@PathParam(CONCEPTO_LITERAL) String concepto) throws IndicoException  {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (concepto==null || concepto.equals(TODOS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getAllMensualComplete();
			} else {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getConjuntoMensualCompleteByGR(concepto);
			}
			
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			//Object[][] cantidad = new  Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Series[] cantidad = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				
				//cantidad
				//cantidad[row][0] = vg.getEjeX();
				//cantidad[row][1] = vg.getSerieCantidad();
				cantidad[row] = new Series(vg.getEjeX(), vg.getSerieCantidad());
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) minimoCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
	        	if (0<vg.getSerieCantidad().compareTo(maximoCantidad)) maximoCantidad =vg.getSerieCantidad();
				row++;
			}//for
			//Asignación de valores minimos y maximos.
			if ((minimoValor.compareTo(BigDecimal.ZERO)>0)) {
	        	minimoValor = BigDecimal.ZERO;
	        }
			if((minimoCantidad.compareTo(BigDecimal.ZERO)>0)){
				minimoCantidad = BigDecimal.ZERO;
			}
			if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
			if (minimoCantidad.compareTo(maximoCantidad)==0 ) {
	        	maximoCantidad = maximoCantidad.add(BigDecimal.ONE) ;
	        }
			
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2];
	        //
	        String minX = (String)ticks[0];
	        String maxX = (String)ticks[ticks.length-1];
	        //
			resultado.put(TITLE_LITERAL			, TRANSACCIONES_CUD_LITERAL);
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
	@Path("/transferenciasTrimestralesService/{concepto}")
	public Map <Object,Object> getTransferenciasTrimestrales(@PathParam(CONCEPTO_LITERAL) String concepto) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (concepto==null || concepto.equals(TODOS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getAllTrimestralComplete();
			} else {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getConjuntoTrimestralByGR(concepto);
			}
			
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			//Object[][] cantidad = new  Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Series[] cantidad = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				
				//cantidad
				//cantidad[row][0] = vg.getEjeX();
				//cantidad[row][1] = vg.getSerieCantidad();
				cantidad[row] = new Series(vg.getEjeX(), vg.getSerieCantidad());
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) minimoCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
	        	if (0<vg.getSerieCantidad().compareTo(maximoCantidad)) maximoCantidad =vg.getSerieCantidad();
				row++;
			}//for
			//Asignación de valores minimos y maximos.
			if ((minimoValor.compareTo(BigDecimal.ZERO)>0)) {
	        	minimoValor = BigDecimal.ZERO;
	        }
			if((minimoCantidad.compareTo(BigDecimal.ZERO)>0)){
				minimoCantidad = BigDecimal.ZERO;
			}
			if(minimoValor.compareTo(maximoValor)==0){
				maximoValor = maximoValor.add(BigDecimal.ONE);
			}
			if(minimoCantidad.compareTo(maximoCantidad)==0){
				maximoCantidad = maximoCantidad.add(BigDecimal.ONE);
			}
			
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2];
	        //
	        String minX = (String)ticks[0];
	        String maxX = (String)ticks[ticks.length-1];
	        //
			resultado.put(TITLE_LITERAL			, TRANSACCIONES_CUD_LITERAL);
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
	@Path("/transferenciasSemestralesService/{concepto}")
	public Map <Object,Object> getTransferenciasSemestrales(@PathParam(CONCEPTO_LITERAL) String concepto) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (concepto==null || concepto.equals(TODOS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getAllSemestralComplete();
			} else {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getConjuntoSemestralByGR(concepto);
			}
			
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			//Object[][] cantidad = new  Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Series[] cantidad = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				
				//cantidad
				//cantidad[row][0] = vg.getEjeX();
				//cantidad[row][1] = vg.getSerieCantidad();
				cantidad[row] = new Series(vg.getEjeX(), vg.getSerieCantidad());
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) minimoCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
	        	if (0<vg.getSerieCantidad().compareTo(maximoCantidad)) maximoCantidad =vg.getSerieCantidad();
				row++;
			}//for
			//Asignación de valores minimos y maximos.
			if ((minimoValor.compareTo(BigDecimal.ZERO)>0)) {
	        	minimoValor = BigDecimal.ZERO;
	        }
			if((minimoCantidad.compareTo(BigDecimal.ZERO)>0)){
				minimoCantidad = BigDecimal.ZERO;
			}
			if(minimoValor.compareTo(maximoValor)==0){
				maximoValor = maximoValor.add(BigDecimal.ONE);
			}
			if(minimoCantidad.compareTo(maximoCantidad)==0){
				maximoCantidad = maximoCantidad.add(BigDecimal.ONE);
			}
			
	        //minimos y maximos en x
	        String minX = (String)ticks[0];
	        String maxX = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2];
	        String to = maxX;
	        //
			resultado.put(TITLE_LITERAL			, TRANSACCIONES_CUD_LITERAL);
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
	@Path("/transferenciasAnualesService/{concepto}")
	public Map <Object,Object> getTransferenciasAnuales(@PathParam(CONCEPTO_LITERAL) String concepto) throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 
			if (concepto==null || concepto.equals(TODOS_LITERAL)) {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getAllAnualComplete();
			} else {
				vgl = ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getConjuntoAnualByGR(concepto);
			}
			
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			//Object[][] cantidad = new  Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Series[] cantidad = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX()+DATE_PART_LITERAL;
				//valores
				//valores[row][0] = vg.getEjeX()+DATE_PART_LITERAL;
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX()+DATE_PART_LITERAL, vg.getSerieValor());
				
				//cantidad
				//cantidad[row][0] = vg.getEjeX()+DATE_PART_LITERAL;
				//cantidad[row][1] = vg.getSerieCantidad();
				cantidad[row] = new Series(vg.getEjeX()+DATE_PART_LITERAL, vg.getSerieCantidad());
				
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) minimoCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
	        	if (0<vg.getSerieCantidad().compareTo(maximoCantidad)) maximoCantidad =vg.getSerieCantidad();
				row++;
			}//for
			//Asignación de valores minimos y maximos.
			if ((minimoValor.compareTo(BigDecimal.ZERO)>0)) {
	        	minimoValor = BigDecimal.ZERO;
	        }
			if((minimoCantidad.compareTo(BigDecimal.ZERO)>0)){
				minimoCantidad = BigDecimal.ZERO;
			}
			if(minimoValor.compareTo(maximoValor)==0){
				maximoValor = maximoValor.add(BigDecimal.ONE);
			}
			if(minimoCantidad.compareTo(maximoCantidad)==0){
				maximoCantidad = maximoCantidad.add(BigDecimal.ONE);
			}
			
	        //minimos y maximos en x
	        String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2] ;
	        //
			resultado.put(TITLE_LITERAL			, TRANSACCIONES_CUD_LITERAL);
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
	 *  ROTACION
	 */
	
	@GET
	@Path("/rotacion/diaria")
	public Map <Object,Object> getRotacionDiaria() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoDiarioRotacionSG();
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//cantidad
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if (minimoValor.compareTo(BigDecimal.ZERO)>0 ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        String to = (String)ticks[ticks.length-1];
	        dias = ticks.length-dias;
	        String from = (String)ticks[dias<0?0:dias];
	        String maxX = (String)ticks[ticks.length-1];
	        String minX = (String)ticks[0];
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_ROTACION_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
			resultado.put(MAX_VALOR_LITERAL		, maximoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
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
	@Path("/rotacion/mensual")
	public Map <Object,Object> getRotacionMensual() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoMensualRotacionSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				//
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if (minimoValor.compareTo(BigDecimal.ZERO)>0) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        //minimos y maximos en x
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2];
	        //
	        String minX = (String)ticks[0];
	        String maxX = (String)ticks[ticks.length-1];
	        // // //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_ROTACION_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
			resultado.put(MAX_VALOR_LITERAL		, maximoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
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
	@Path("/rotacion/trimestral")
	public Map <Object,Object> getRotacionTrimestral() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoTrimestralRotacionSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//cantidad
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				//
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if (minimoValor.compareTo(BigDecimal.ZERO)>0) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }	        
	        //
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2];
	        //
	        String minX = (String)ticks[0];
	        String maxX = (String)ticks[ticks.length-1];
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_ROTACION_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
			resultado.put(MAX_VALOR_LITERAL		, maximoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
			resultado.put(SERIE_VALORES_LITERAL	, valores);
			resultado.put(MINX_LITERAL			, minX);
			resultado.put(MAXX_LITERAL			, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL			, to);
		} catch (Exception  e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		}
		return resultado;
	}

	@GET
	@Path("/rotacion/semestral")
	public Map <Object,Object> getRotacionSemestral() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoSemestralRotacionSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];

			BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				//
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }	        
	        //
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2];
	        //
	        String minX = (String)ticks[0];
	        String maxX = (String)ticks[ticks.length-1];
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_ROTACION_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
			resultado.put(MAX_VALOR_LITERAL		, maximoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
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
	@Path("/rotacion/anual")
	public Map <Object,Object> getRotacionAnual() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoAnualRotacionSG();
			Series[] Cantidad = new Series[vgl.size()];
			Series[] Valores = new Series[vgl.size()];
			//valores y cantidad 
				
			//  getSerieCantidad
			//Object[][] valores = new Object[vgl.size()][2];
			//Object[][] cantidad = new  Object[vgl.size()][2];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal minimoCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
	        BigDecimal maximoCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX()+DATE_PART_LITERAL;
				//cantidad
				//valores[row][0] = vg.getEjeX()+DATE_PART_LITERAL;
				//valores[row][1] = vg.getSerieValor();
				//valores
				//cantidad[row][0] = vg.getEjeX()+DATE_PART_LITERAL;
				//cantidad[row][1] = vg.getSerieCantidad();
				
				
				Cantidad[row] = new Series(vg.getEjeX()+DATE_PART_LITERAL, vg.getSerieCantidad());
				Valores[row] = new Series(vg.getEjeX()+DATE_PART_LITERAL, vg.getSerieValor().setScale(2, RoundingMode.DOWN));
			
				//
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0>vg.getSerieCantidad().compareTo(minimoCantidad)) minimoCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
	        	if (0<vg.getSerieCantidad().compareTo(maximoCantidad)) maximoCantidad =vg.getSerieCantidad();
				row++;
			}//for
	        //minimos y maximos en x
	        String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2] ;
	        //
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }	        
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_ROTACION_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
			resultado.put(MIN_CANTIDAD_LITERAL		, minimoCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximoValor.setScale(NUMERO_DECIMALES_ROTACION, RoundingMode.DOWN));
			resultado.put(MAX_CANTIDAD_LITERAL		, maximoCantidad);
			//resultado.put(SERIE_VALORES_LITERAL	, valores);
			//resultado.put(SERIE_CANTIDAD_LITERAL	, cantidad);
			
				
			resultado.put(SERIE_VALORES_LITERAL, Valores);
			resultado.put(SERIE_CANTIDAD_LITERAL, Cantidad);
				
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
	 *  ROTACION PIB
	 */
	@GET
	@Path("/rotacion/pib/diaria")
	public Map <Object,Object> getRotacionPIBDiaria() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoDiarioPIBSG();
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDeDias();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				//
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        
	        String to = (String)ticks[ticks.length-1];
	        dias = ticks.length-dias;
	        String from = (String)ticks[dias<0?0:dias];
	        String maxX = (String)ticks[ticks.length-1];
	        String minX = (String)ticks[0];
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_PIB_LITERAL);
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
	@Path("/rotacion/pib/mensual")
	public Map <Object,Object> getRoracionPIBMensual() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoMensualPIBSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				//
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        //minimos y maximos en x
	        String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2] ;
	        //
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_PIB_LITERAL);
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
	@Path("/rotacion/pib/trimestral")
	public Map <Object,Object> getRotacionPIBTrimestral() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoTrimestralPIBSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//cantidad
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
				//
				valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        //minimos y maximos en x
	        String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2] ;
	        //
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_PIB_LITERAL);
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
	@Path("/rotacion/pib/semestral")
	public Map <Object,Object> getRotacionPIBSemestral() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoSemestralPIBSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX();
				//valores
				//valores[row][0] = vg.getEjeX();
				//valores[row][1] = vg.getSerieValor();
	        	valores[row] = new Series(vg.getEjeX(), vg.getSerieValor());
				if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        //minimos y maximos en x
	        String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2] ;
	        //
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_PIB_LITERAL);
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
	@Path("/rotacion/pib/anual")
	public Map <Object,Object> getRotacionPibAnual() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoAnualPIBSG();
			//valores y cantidad
			//Object[][] valores = new Object[vgl.size()][2];
			Series[] valores = new Series[vgl.size()];
			Object[] ticks = new  Object[vgl.size()];
	        BigDecimal minimoValor = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximoValor = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticks[row]=vg.getEjeX()+DATE_PART_LITERAL;
				//valores
				//valores[row][0] = vg.getEjeX()+DATE_PART_LITERAL;
				//valores[row][1] = vg.getSerieValor();
				//
				valores[row] = new Series(vg.getEjeX()+DATE_PART_LITERAL, vg.getSerieValor());
	        	if (0>vg.getSerieValor().compareTo(minimoValor)) minimoValor =vg.getSerieValor();
	        	if (0<vg.getSerieValor().compareTo(maximoValor)) maximoValor =vg.getSerieValor();
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimoValor.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimoValor = BigDecimal.ZERO;
	        }
	        if (minimoValor.compareTo(maximoValor)==0 ) {
	        	maximoValor = maximoValor.add(BigDecimal.ONE) ;
	        }
	        //minimos y maximos en x
	        //minimos y maximos en x
	        String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String to = (String)ticks[ticks.length-1];
	        String from = (String)ticks[ticks.length-2] ;
	        //
			resultado.put(TITLE_LITERAL			, INDICADORES_PAGOS_PIB_LITERAL);
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
	
	/**
	 *  CANTIDAD
	 */
	@GET
	@Path("/distribucion/cantidad/diaria")
	public Map <Object,Object> getDistribucionCantidadDiario() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoDiarioCantidadSG();
			Integer dias = ServiceFacades.getInstance().getParametersService().getCantidadDistribucionDias();
			
			//valores y cantidad
			//Object[][] cantidades = new Object[vgl.size()][3];
			
			Series[] cantidades = new Series[vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimaCantidad = new BigDecimal(NUMBER_LITERAL);  
	        BigDecimal maximaCantidad = new BigDecimal(MINUS_NUMBER_LITERAL); 
			int row=0;
	
			for (ValorGraficable vg : vgl) {
				//System.out.println(vg.getSerieCantidad());
				//System.out.println("Fecha : "+vg.getEjeX());
				
				//ticks[row]=vg.getEjeX();
				ticksA.add(vg.getEjeX());
				//valores
				//cantidades[row][0] = vg.getEjeX();
				//cantidades[row][1] = vg.getHora();
				//cantidades[row][2] = vg.getSerieCantidad().multiply(new BigDecimal(100));
				cantidades[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieCantidad().multiply(new BigDecimal(100)));

	        	if (0>vg.getSerieCantidad().compareTo(minimaCantidad)) minimaCantidad =vg.getSerieCantidad();
				if (0<vg.getSerieCantidad().compareTo(maximaCantidad)) maximaCantidad =vg.getSerieCantidad();
				row++;

			}//for
			//asignacion de valores minimos.
	        if ((minimaCantidad.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimaCantidad = BigDecimal.ZERO;
	        }
			if(minimaCantidad.compareTo(maximaCantidad)==0){
				maximaCantidad = maximaCantidad.add(BigDecimal.ONE);
			}

	        //Asignación de valores procentales al eje vertical de la gráfica
	        minimaCantidad = minimaCantidad.multiply(new BigDecimal(100));
	        maximaCantidad = maximaCantidad.multiply(new BigDecimal(100));
	        
	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-(dias)];
	        String to   = ticks[ticks.length-1];
	        
	       /* String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = (String)ticks[ticks.length-(dias)];
	        String to = (String)ticks[ticks.length-1];*/
	        
	        
			resultado.put(TITLE_LITERAL			, PARTICIPACION_CANTIDAD_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimaCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximaCantidad);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidades);
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
	@Path("/distribucion/cantidad/mensual")
	public Map <Object,Object> getDistribucionCantidadMensual() throws IndicoException {
		
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoMensualCantidadSG();
			//valores y cantidad
			//Object[][] cantidades = new Object[vgl.size()][3];
			
			Series[] cantidades = new Series[vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimaCantidad = new BigDecimal(NUMBER_LITERAL);
	        BigDecimal maximaCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX();
				ticksA.add(vg.getEjeX());
				//valores
				//cantidades[row][0] = vg.getEjeX();
				//cantidades[row][1] = vg.getHora();
				//cantidades[row][2] = vg.getSerieCantidad().multiply(new BigDecimal(100));
				
				cantidades[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieCantidad().multiply(new BigDecimal(100)));

	        	if (0>vg.getSerieCantidad().compareTo(minimaCantidad)) minimaCantidad =vg.getSerieCantidad();
				if (0<vg.getSerieCantidad().compareTo(maximaCantidad)) maximaCantidad =vg.getSerieCantidad();
				row++;

			}//for
			//asignacion de valores minimos.
	        if ((minimaCantidad.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimaCantidad = BigDecimal.ZERO;
	        }
			if(minimaCantidad.compareTo(maximaCantidad)==0){
				maximaCantidad = maximaCantidad.add(BigDecimal.ONE);
			}
	        //Asignación de valores procentales al eje vertical de la gráfica
	        minimaCantidad = minimaCantidad.multiply(new BigDecimal(100));
	        maximaCantidad = maximaCantidad.multiply(new BigDecimal(100));
	        
	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-2];
	        String to   = ticks[ticks.length-1];	
	        
	      /*  String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = (String)ticks[ticks.length-2];
	        String to = (String)ticks[ticks.length-1];*/
	        
			resultado.put(TITLE_LITERAL				, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL				, ticks);
			resultado.put(MIN_VALOR_LITERAL			, minimaCantidad);
			resultado.put(MAX_VALOR_LITERAL			, maximaCantidad);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidades);
			resultado.put(MINX_LITERAL				, minX);
			resultado.put(MAXX_LITERAL				, maxX);
			resultado.put(STARTX_LITERAL			, from);
			resultado.put(ENDX_LITERAL				, to);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		}
		return resultado;
	}
	
	@GET
	@Path("/distribucion/cantidad/trimestral")
	public Map <Object,Object> getDistribucionCantidadTrimestal() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoTrimestralCantidadSG();

			//valores y cantidad
			//Object[][] cantidades = new Object[vgl.size()][3];
			
			Series[] cantidades = new Series[vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();

			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimaCantidad = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximaCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX();
				ticksA.add(vg.getEjeX());
				//valores
				//cantidades[row][0] = vg.getEjeX();
				//cantidades[row][1] = vg.getHora();
				//cantidades[row][2] = vg.getSerieCantidad().multiply(new BigDecimal(100));
				
				cantidades[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieCantidad().multiply(new BigDecimal(100)));
	        	if (0>vg.getSerieCantidad().compareTo(minimaCantidad)) minimaCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieCantidad().compareTo(maximaCantidad)) maximaCantidad =vg.getSerieCantidad();
				row++;

			}//for
			//asignacion de valores minimos.
	        if ((minimaCantidad.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimaCantidad = BigDecimal.ZERO;
	        }
			if(minimaCantidad.compareTo(maximaCantidad)==0){
				maximaCantidad = maximaCantidad.add(BigDecimal.ONE);
			}

	      //Asignación de valores procentales al eje vertical de la gráfica
	        minimaCantidad = minimaCantidad.multiply(new BigDecimal(100));
	        maximaCantidad = maximaCantidad.multiply(new BigDecimal(100));
	    
			String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-2];
	        String to   = ticks[ticks.length-1];

		/*	
	        String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = (String)ticks[ticks.length-2];
	        String to = (String)ticks[ticks.length-1];*/
	        
			resultado.put(TITLE_LITERAL			, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimaCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximaCantidad);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidades);
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
	@Path("/distribucion/cantidad/semestral")
	public Map <Object,Object> getDistribucionCantidadSemestral() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoSemestralCantidadSG();

			//valores y cantidad
			//Object[][] cantidades = new Object[vgl.size()][3];
			
			Series[] cantidades = new Series[vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimaCantidad = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximaCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0;
			for (ValorGraficable vg : vgl) {
				ticksA.add(vg.getEjeX());
				//valores
				//cantidades[row][0] = vg.getEjeX();
				//cantidades[row][1] = vg.getHora();
				//cantidades[row][2] = vg.getSerieCantidad().multiply(new BigDecimal(100));
				
				cantidades[row] = new Series(vg.getEjeX(), vg.getHora(), vg.getSerieCantidad().multiply(new BigDecimal(100)));
				
	        	if (0>vg.getSerieCantidad().compareTo(minimaCantidad)) minimaCantidad =vg.getSerieCantidad();
	        	if (0<vg.getSerieCantidad().compareTo(maximaCantidad)) maximaCantidad =vg.getSerieCantidad();
				row++;
			}//for
			//asignacion de valores minimos.
			//REVISAR
	        if ((minimaCantidad.compareTo(BigDecimal.ZERO)>0) ) {
	        	//maximaCantidad = BigDecimal.ZERO;
				minimaCantidad = BigDecimal.ZERO;
	        }
			if(minimaCantidad.compareTo(maximaCantidad)==0){
				maximaCantidad = maximaCantidad.add(BigDecimal.ONE);
			}
	      //Asignación de valores procentales al eje vertical de la gráfica
	        minimaCantidad = minimaCantidad.multiply(new BigDecimal(100));
	        maximaCantidad = maximaCantidad.multiply(new BigDecimal(100));
	        
	        String[] ticks = ticksA.toArray(new String[ticksA.size()]);
	        String minX = ticks[0];
	        String maxX = ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = ticks[ticks.length-2];
	        String to   = ticks[ticks.length-1];	
	        
	     /*   String minX = (String)ticks[0] ;
	        String maxX = (String)ticks[ticks.length-1];
	        //minimos y maximos en x
	        String from = (String)ticks[ticks.length-2];
	        String to = (String)ticks[ticks.length-1];*/
	        
			resultado.put(TITLE_LITERAL			, PARTICIPACION_VALOR_LITERAL);
			resultado.put(TICKS_LITERAL			, ticks);
			resultado.put(MIN_VALOR_LITERAL		, minimaCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximaCantidad);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidades);
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
	@Path("/distribucion/cantidad/anual")
	public Map <Object,Object> getDistribucionCantidadAnual() throws IndicoException {
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getConjuntoAnualCantidadSG();
		
			//valores y cantidad
			//Object[][] cantidades = new Object[vgl.size()][3];
			
			Series[] cantidades = new Series[vgl.size()];
			Set<String> ticksA = new  TreeSet<String>();
			//Object[] ticks = new Object[vgl.size()];
	        BigDecimal minimaCantidad = new BigDecimal(NUMBER_LITERAL);   
	        BigDecimal maximaCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
			int row=0; 
			for (ValorGraficable vg : vgl) {
				//ticks[row]=vg.getEjeX()+DATE_PART_LITERAL;
				ticksA.add(vg.getEjeX()+DATE_PART_LITERAL);
				//valores
				//cantidades[row][0] = vg.getEjeX()+DATE_PART_LITERAL;
				//cantidades[row][1] = vg.getHora();
				//cantidades[row][2] = vg.getSerieCantidad().multiply(new BigDecimal(100));
				
				cantidades[row] = new Series(vg.getEjeX()+DATE_PART_LITERAL, vg.getHora(), vg.getSerieCantidad().multiply(new BigDecimal(100)));
				
	        	if (0>vg.getSerieCantidad().compareTo(minimaCantidad)) {
	        		minimaCantidad =vg.getSerieCantidad();
	        	}
	        	if (0<vg.getSerieCantidad().compareTo(maximaCantidad)) {
	        		maximaCantidad =vg.getSerieCantidad();
	        	}
				row++;
			}//for
			//asignacion de valores minimos.
	        if ((minimaCantidad.compareTo(BigDecimal.ZERO)>0) ) {
	        	minimaCantidad = BigDecimal.ZERO;
	        }
			if(minimaCantidad.compareTo(maximaCantidad)==0){
				maximaCantidad = maximaCantidad.add(BigDecimal.ONE);
			}
	        //Asignación de valores procentales al eje vertical de la gráfica
	        minimaCantidad = minimaCantidad.multiply(new BigDecimal(100));
	        maximaCantidad = maximaCantidad.multiply(new BigDecimal(100));
	        
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
			resultado.put(MIN_VALOR_LITERAL		, minimaCantidad);
			resultado.put(MAX_VALOR_LITERAL		, maximaCantidad);
			resultado.put(SERIE_CANTIDAD_LITERAL	, cantidades);
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