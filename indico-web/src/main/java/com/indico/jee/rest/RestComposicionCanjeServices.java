package com.indico.jee.rest;

import static com.indico.jee.util.Constants.ENDX_LITERAL;
import static com.indico.jee.util.Constants.ERROR_LITERAL;
import static com.indico.jee.util.Constants.MAXX_LITERAL;
import static com.indico.jee.util.Constants.MAX_VALOR_LITERAL;
import static com.indico.jee.util.Constants.MINUS_NUMBER_LITERAL;
import static com.indico.jee.util.Constants.MINX_LITERAL;
import static com.indico.jee.util.Constants.MIN_VALOR_LITERAL;
import static com.indico.jee.util.Constants.NUMBER_LITERAL;
import static com.indico.jee.util.Constants.STARTX_LITERAL;
import static com.indico.jee.util.Constants.TICKS_LITERAL;
import static com.indico.jee.util.Constants.TITLE_LITERAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.indico.exceptions.IndicoException;
import com.indico.jee.modelo.CanjeInterbancario;
import com.indico.jee.modelo.RangoCanjeCompensacion;
import com.indico.jee.util.ValorGraficable;
import com.indico.jndi.ServiceFacades;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RestComposicionCanjeServices implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	//private SimpleDateFormat receiveFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestServices.class);
	
	private static final String COMPOSICION_CANJE_LITERAL = "Composición del Canje Interbancario";
	
	/**
	 * Metodos de Composicion del Canje Interbancario
	 * @param medioservicio
	 * @return
	 * @throws IndicoException
	 */
	@GET
	@Path("/composicionCanjeService/{periodo}/{rangocanje}")
	public Map <Object,Object> getComposicionCanje(@PathParam("periodo") String idperiodo, @PathParam("rangocanje") String idRangoCanje) throws IndicoException {	
		
		Map <Object,Object> resultado = new HashMap <Object,Object>();  
		try {
			List<ValorGraficable> vgl = null ; 		
			String[] rangosSelected = idRangoCanje.split(",");	
			
			if (idRangoCanje==null || idRangoCanje.equals("Todos")) {
				vgl = ServiceFacades.getInstance().getAnalisisComposicionCanjeService().getComposicionCanjeAll(idperiodo);
			} else {
				vgl = ServiceFacades.getInstance().getAnalisisComposicionCanjeService().getComposicionCanjeByRango(idperiodo, rangosSelected);
			}
			
			if (vgl!=null && !vgl.isEmpty()) {

				/**
		        * Crea arreglo ticks (Fechas del eje x)
		        */
				String[] ticks = vgl.stream()
		    			.sorted(Comparator.comparing(ValorGraficable::getEjeX))
		    			.map(ValorGraficable::getEjeX)
		    			.distinct()
		    			.toArray(String[]::new);
				
		        BigDecimal minimaCantidad = new BigDecimal(NUMBER_LITERAL);   
		        BigDecimal maximaCantidad = new BigDecimal(MINUS_NUMBER_LITERAL);   
				
		        //Lista de todos los Rangos de la BD
		        List<RangoCanjeCompensacion> rangoscanje = ServiceFacades.getInstance().getRangoCanjeService().findAllRangos();
		        
		        int fechaTotal = ticks.length;
		        int serieTotal = rangoscanje.size();		     
		        
		        Object[][] rangos = new Object[fechaTotal][1];
		        Object[][] rangosValor = new Object[fechaTotal][1];
		        List<String> rangoscanjetmp = new ArrayList<String>();
		        					
		        //recorre los datos obtenidos
		        //recorre las fechas de los datos
		        int fc = 0;
	        	for(String t : ticks) {

	        		CanjeInterbancario[] cantidades = new CanjeInterbancario[serieTotal];
	        		
	        		CanjeInterbancario [] valores = new CanjeInterbancario[serieTotal];
    				
    				String fecha="";
	        		for (ValorGraficable vg : vgl) {
		        		//compara las fechas de las listas
		        		if(t.equals(vg.getEjeX())) {
		        			//recorre los rangos de la bd
		        			int rco = 0;
		        			//if(cantidades[0][2] == null) {
		        			if(cantidades[0] == null) {
		        				for(RangoCanjeCompensacion rc : rangoscanje) {
		        					cantidades[rco] = new CanjeInterbancario();
		        					valores[rco] = new CanjeInterbancario();
		        					
			    					//cantidades[rco][0] = Integer.parseInt(rc.getIdRangoCanje());
			    					cantidades[rco].setIdRangoCanje(Integer.parseInt(rc.getIdRangoCanje()));
			    					//valores[rco][0] = Integer.parseInt(rc.getIdRangoCanje());
			    					valores[rco].setIdRangoCanje(Integer.parseInt(rc.getIdRangoCanje()));
			    					rco++;
			        			}
		        			}
		        			
		        			for(int i=0; i<cantidades.length; i++) {
		        				//if(cantidades[i][0].equals(Integer.parseInt(vg.getIdRangoCanje()))) {
		        				if(cantidades[i].getIdRangoCanje().equals(Integer.parseInt(vg.getIdRangoCanje()))) {
		        					//cantidades[i][1] = vg.getSerieCantidadPorcentaje();
		        					cantidades[i].setPorcentaje(vg.getSerieCantidadPorcentaje());
									//cantidades[i][2] = vg.getEjeX();
		        					cantidades[i].setEjex(vg.getEjeX());
									//valores[i][1] = vg.getSerieValorPorcentaje();
		        					valores[i].setPorcentaje(vg.getSerieValorPorcentaje());
									//valores[i][2] = vg.getEjeX()
		        					valores[i].setEjex(vg.getEjeX());
									fecha = vg.getEjeX();
									break;
		        				}
		        			}
		        		}
		        	}
	        		
	        		for(int i=0; i<cantidades.length; i++) {
        				//if(cantidades[i][1]==null) {
	        			if(cantidades[i].getPorcentaje()==null) {
        					//cantidades[i][1] = BigDecimal.ZERO;
	        				cantidades[i].setPorcentaje(BigDecimal.ZERO);
        					//cantidades[i][2] = fecha;
	        				cantidades[i].setEjex(fecha);
        				}
        				//if(valores[i][1]==null) {
	        			if(valores[i].getPorcentaje() ==null) {
        					//valores[i][1] = BigDecimal.ZERO;
	        				valores[i].setPorcentaje(BigDecimal.ZERO);
        					//valores[i][2] = fecha;
	        				valores[i].setEjex(fecha);
        				}
        			}
	        		
	        		rangos[fc][0]= cantidades;
					rangosValor[fc][0] = valores;
					fc++;
		        }	        
		        
		        //Obtener Labels
				if (idRangoCanje==null || idRangoCanje.equals("Todos")) {
					for(RangoCanjeCompensacion rc : rangoscanje) {
						if(rc.getValorFinal() == null) {
							rangoscanjetmp.add("Mayor a "+rc.getValorInicial());
						}else {
							rangoscanjetmp.add(rc.getValorInicial()+"-"+rc.getValorFinal());
						}
					}
				} else {
					for(RangoCanjeCompensacion rc : rangoscanje) {
						for (String rs: rangosSelected) {
							if ( rc.getIdRangoCanje().equals(rs)) {
								if(rc.getValorFinal() == null) {
									rangoscanjetmp.add("Mayor a "+rc.getValorInicial());
								}else {
									rangoscanjetmp.add(rc.getValorInicial()+"-"+rc.getValorFinal());
								}
							}
						}
					}
				}
		        
		        List<String> rangoLabesDis = rangoscanjetmp.stream().distinct().collect(Collectors.toList());
		        
				Object[][] RangosLabel = new Object[rangoscanjetmp.size()][1];
				
				int fila=0;
				for (String rl : rangoLabesDis) {
					RangosLabel[fila][0]=rl.toString();
					fila++;
				}
		        
				//asignacion de valores minimos.
		        if ((minimaCantidad.compareTo(BigDecimal.ZERO)>0) ) {
		        	minimaCantidad = BigDecimal.ZERO;
		        }
		        
		        //Asignación de valores procentales al eje vertical de la gráfica
		        minimaCantidad = minimaCantidad.multiply(new BigDecimal(100));
		        maximaCantidad = maximaCantidad.multiply(new BigDecimal(100));
		        		        
		        String minX = ticks[0];
		        String maxX = ticks[ticks.length-1];
		        
		        //minimos y maximos en x
		        String from;
		        if(ticks.length > 12) {
		        	from = ticks[ticks.length-12];
		        }else {
		        	from = ticks[ticks.length-1];
		        }
		        String to   = ticks[ticks.length-1];
		        
		        //
				resultado.put(TITLE_LITERAL				, COMPOSICION_CANJE_LITERAL);
				resultado.put(TICKS_LITERAL				, ticks);
				resultado.put(MIN_VALOR_LITERAL			, minimaCantidad);
				resultado.put(MAX_VALOR_LITERAL			, maximaCantidad);
				resultado.put("Rangos"					, rangos);
				resultado.put("RangosValores"			, rangosValor);
				resultado.put("LabelsRangos"			, RangosLabel);	
				resultado.put(MINX_LITERAL				, minX);
				resultado.put(MAXX_LITERAL				, maxX);
				resultado.put(STARTX_LITERAL			, from);
				resultado.put(ENDX_LITERAL				, to);
		    }
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new IndicoException(ERROR_LITERAL);
		} 
		return resultado;
			
	}
}