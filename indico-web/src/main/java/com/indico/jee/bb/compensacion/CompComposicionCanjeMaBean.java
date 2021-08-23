package com.indico.jee.bb.compensacion;

import static com.indico.jee.util.Constants.HEAD;
import static com.indico.jee.util.Constants.CONTENT_DISPOSITION_LITERAL;
import static com.indico.jee.util.Constants.FOOTER;
import static com.indico.jee.util.Constants.FORMAT_LITERAL_6;
import static com.indico.jee.util.Constants.UTF8_LITERAL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;
import com.indico.exceptions.IndicoException;
import com.indico.jee.bb.GraficaGeneralBean;
import com.indico.jee.modelo.AnalisisComposicionCanje;
import com.indico.jee.modelo.RangoCanjeCompensacion;

@ManagedBean(name="compComposicionCanjeMaBean")
@ViewScoped
public class CompComposicionCanjeMaBean extends GraficaGeneralBean {
	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompComposicionCanjeMaBean.class);
	
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");	
	private final String preparedDate = formatter.format(date);
	
	private final String HEADER = 	"	<Header>	\r\n" +
									"			<ID>Indico - Composición del Canje Interbancario</ID>	\r\n" +
									"			<Test>false</Test>													\r\n" +
									"			<Truncated>false</Truncated>										\r\n" +
									"			<Name xml:lang=\"es\">Indico - Composición del Canje Interbancario</Name>	\r\n" +
									"			<Prepared>"+preparedDate+"</Prepared>	\r\n" +
									"			<Sender id=\"INDICO\">	\r\n" +
									"				<Name xml:lang=\"en\">Banco de la República</Name>	\r\n" +
									"			</Sender>	\r\n" +
									"	</Header>	\r\n" +
									"	<DataSet>	\r\n" +
									"		<generic:KeyFamilyRef>INDICO_Composición_del_Canje_Interbancario</generic:KeyFamilyRef>	\r\n" +
									"		<generic:Group type=\"SiblingGroup\">	\r\n" +
									"			<generic:Attributes>	\r\n" +
									"				<generic:Value concept=\"DECIMALS\" value=\"2\"/>		\r\n" +
									"				<generic:Value concept=\"UNIT_MEASURE\" value=\"COP\"/>	\r\n" +
									"				<generic:Value concept=\"UNIT_MULT\" value=\"1\"/>		\r\n" +
									"			</generic:Attributes>	\r\n" +
									"			<generic:Series>	\r\n" +
									"				<generic:SeriesKey>		\r\n" +
									"					<generic:Value concept=\"FREQ\" value=\"M\"/>	\r\n" +
									"				</generic:SeriesKey>	\r\n" +
									"				<generic:Attributes>	\r\n" +
									"					<generic:Value concept=\"TIME_FORMAT\" value=\"610\"/>	\r\n" +
									"				</generic:Attributes>	\r\n"; 
	
	//ocultar mostrar mensaje
    private boolean 						ocultarmostrar;
	private List<RangoCanjeCompensacion> 	rangoscanje;
	private String 							rango;
	private List<RangoCanjeCompensacion>	selectedRangos;
      	
    @PostConstruct
    public void init() {
    	try {
    		rangoscanje = ServiceFacades.getInstance().getRangoCanjeService().findAllRangos();
        	setPeriodicidad("2");
        	setSelectedRangos(rangoscanje);
        	//setRango(TODOS_LITERAL);
        	setTipoaexportar("");
    	} catch (Exception ex) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Invalida", null));
    	}
    }
       
    
    @Override
    public void restaurarAction(ActionEvent evt){
    	super.restaurarAction(evt);
    	setPeriodicidad("2");
    	setRango(rango);
    	setTipoaexportar("");
    }
    
    public void exportAction(ActionEvent evt) {
    	try {
		if (getTipoaexportar()!=null) { 
			List<AnalisisComposicionCanje>  resultado = null; 
			
			String a = (String)evt.getComponent().getAttributes().get("start");
			String b = (String)evt.getComponent().getAttributes().get("end");
			
			String ranges = (String)evt.getComponent().getAttributes().get("rangesSelected");
			
			String[] rangosSelected = ranges.split(",");
			
			if (a==null || b==null) {
	    		throw new IndicoException("Rango no permitido");
	    	}
        	Integer fechaInicial=null;
        	Integer fechaFinal=null;
        	
			a.substring(0, 8).replace("-", "");
			b.substring(0, 8).replace("-", "");
			
			fechaInicial = Integer.parseInt(a.substring(0, 8).replace("-", ""));
			fechaFinal = Integer.parseInt(b.substring(0, 8).replace("-", ""));
					
			
			if (ranges==null || ranges.equals("Todos")) {
		    	resultado =  ServiceFacades.getInstance().getAnalisisComposicionCanjeService().getDataDiarioCompoCanjeTodas(fechaInicial, fechaFinal, Integer.valueOf(getPeriodicidad()) );
		    } else {
		    	resultado =  ServiceFacades.getInstance().getAnalisisComposicionCanjeService().getDataDiarioCompoCanje(fechaInicial, fechaFinal, rangosSelected, Integer.valueOf(getPeriodicidad()));
		    }		        
		
			if (getTipoaexportar()!=null) {
				if (getTipoaexportar().equals("xls")) {
		        	createXMLFile(resultado);
				} else if (getTipoaexportar().equals("csv")) {
		        	createCSVFile(resultado);
				} else if (getTipoaexportar().equals("sdmx")) {
					createSDMXFile(resultado);
				}
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Archivo exportado satisfactoriamente", null));
			}    		   
			setTipoaexportar("");
		}}
    	catch(Exception ex) {
    		ex.getStackTrace();
    	}
	}
	
    private void createXMLFile (List<AnalisisComposicionCanje> valores) {
    	HSSFWorkbook wb = null;
    	OutputStream output  = null;
    	try {
    		wb = new HSSFWorkbook();
    		Sheet sheet = wb.createSheet("Composición del Canje Interbancario");
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("Fecha(mm/yyyy)");
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Tipo de plaza");
			cellHeader = rowHeader.createCell(2);
			cellHeader.setCellValue("Rango");
			cellHeader = rowHeader.createCell(3);
			cellHeader.setCellValue("Desde");
			cellHeader = rowHeader.createCell(4);
			cellHeader.setCellValue("Hasta");
			cellHeader = rowHeader.createCell(5);
			cellHeader.setCellValue("Cant Cheques");
			cellHeader = rowHeader.createCell(6);
			cellHeader.setCellValue("Valor (Pesos)");
			int rownum = 1;
			//CellStyle cellStyle = wb.createCellStyle();
			//CreationHelper createHelper = wb.getCreationHelper();
			//cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM"));

    		for (AnalisisComposicionCanje ana : valores) {
    			Row row = sheet.createRow(rownum++);
    			Cell cell = row.createCell(0);
    			//cell.setCellStyle(cellStyle);
    			String fecha = ana.getPk().getAnioMesCanje()+"01";
    			Date tradeDate = new SimpleDateFormat("yyyyMMdd").parse(fecha);
    			String krwtrDate = new SimpleDateFormat("dd-MM-yyyy").format(tradeDate);
    			cell.setCellValue(krwtrDate.substring(3, 10));
    			cell = row.createCell(1);
    			cell.setCellValue(ana.getPk().getIdMedioServCompensacion());
    			cell = row.createCell(2);
    			cell.setCellValue(ana.getDescmediocompensacion());
    			cell = row.createCell(3);
    			cell.setCellValue(String.format("%.2f",ana.getValorInicialRango()));
    			//cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getValorInicialRango()));
    			cell = row.createCell(4);
    			if(IndicoUtils.getInstance().assertDouble(ana.getValorFinalRango())==0) {
    				cell.setCellValue("");
    			}else {
    				cell.setCellValue(String.format("%.2f",ana.getValorFinalRango()));
    			}
    			
    			cell = row.createCell(5);
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getNumCheques()));
    			cell = row.createCell(6);
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getValorCanje()));
    		}
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/xls");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_composicion_canje.xls");
			output = response.getOutputStream();
			output.write(outputStream.toByteArray());
			fc.responseComplete();
    	} catch(Exception ex) {
			logger.info(ex.getMessage());
    	} finally {
            try {
				if (wb!=null) wb.close();
				if (output!=null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
    	}
    }
    
    private void createCSVFile(List<AnalisisComposicionCanje> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/comma-separated-values");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_composicion_canje.csv");
			output = response.getOutputStream();
        	String line = MessageFormat.format(FORMAT_LITERAL_6,"Fecha","Tipo de Plaza","Rango","Desde", "Hasta", "Cant Cheques","Valor (Pesos)");
        	output.write((line+"\r\n").getBytes());

			if(valores!=null && !valores.isEmpty()) {
	        	for (AnalisisComposicionCanje ana : valores) {
	        		String fecha = ana.getPk().getAnioMesCanje()+"01";
	    			Date tradeDate;
	    			String rangoFin="";
	    			if(ana.getValorFinalRango()==null) {
	    				rangoFin ="";
	    			}else {
	    				rangoFin = String.format("%.2f",ana.getValorFinalRango());
	    			}
					try {
						tradeDate = new SimpleDateFormat("yyyyMMdd").parse(fecha);
						String krwtrDate = new SimpleDateFormat("dd-MM-yyyy").format(tradeDate);
						line = MessageFormat.format(FORMAT_LITERAL_6,
				    			krwtrDate.substring(3, 10),
				        		ana.getPk().getIdMedioServCompensacion(),
				        		ana.getDescmediocompensacion(),
				        		ana.getValorInicialRango(),
				        		//ana.getValorFinalRango(),
				        		rangoFin,
				        		String.format("%.0f", ana.getNumCheques()),
	    						String.format("%.2f", ana.getValorCanje())	
				        		
				    		);
				        	output.write((line + "\r\n").getBytes());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }//for
			}//if
        	fc.responseComplete();
        } catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			try {
				if (output!=null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
        return;
    }	

    private void createSDMXFile(List<AnalisisComposicionCanje> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/xml");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_composicion_canje_sdmx.xml");
			output = response.getOutputStream();
			String resultado = HEAD + HEADER;
			
			for (AnalisisComposicionCanje valor : valores ) {
				String fecha = valor.getPk().getAnioMesCanje()+"01";
				Date tradeDate;
				
				String rangoFin="";
				if(valor.getValorFinalRango()==null) {
					rangoFin ="";
				}else {
					rangoFin = String.format("%.2f",valor.getValorFinalRango());
				}
				
				try {
					tradeDate = new SimpleDateFormat("yyyyMMdd").parse(fecha);
					String krwtrDate = new SimpleDateFormat("yyyyMMdd").format(tradeDate);
					resultado = resultado .concat( MessageFormat.format(
									"<generic:Obs>	\r\n" +
		    						"	<generic:Time name=\"Fecha\" >{0}</generic:Time> \r\n" +
		    						"	<generic:ObsValue name=\"Tipo de plaza\" value=\"{1}\" /> \r\n" +
		    						"	<generic:ObsValue name=\"Rango\" value=\"{2}\" /> \r\n" +
		    						"	<generic:ObsValue name=\"Desde\" value=\"{3}\" /> \r\n" +
		    						"	<generic:ObsValue name=\"Hasta\" value=\"{4}\" /> \r\n" +
		    						"	<generic:ObsValue name=\"Cantidad de Cheques\" value=\"{5}\" /> \r\n" +
		    						"	<generic:ObsValue name=\"Valor (Pesos)\" value=\"{6}\" /> \r\n" +
		    						"	</generic:Obs>	\r\n",
							
		    						krwtrDate.substring(0, 6),
									valor.getDescmediocompensacion(),
									valor.getPk().getIdRangoCanje(),
									String.format("%.2f", valor.getValorInicialRango()),
									//String.format("%.2f", valor.getValorFinalRango()),
									rangoFin,
									String.format("%.0f", valor.getNumCheques()),
									String.format("%.2f", valor.getValorCanje())
									
					));
					resultado = resultado.concat("\r\n");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			resultado = resultado + FOOTER;
			output.write((byte[])resultado.getBytes());
			fc.responseComplete();
        } catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			try {
				if (output!=null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
        return;
    }
    
	public void showHideMessageInformation(ActionEvent evt){
    	ocultarmostrar = !ocultarmostrar;
    }
    

	public boolean isOcultarmostrar() {
		return ocultarmostrar;
	}

	public void setOcultarmostrar(boolean ocultarmostrar) {
		this.ocultarmostrar = ocultarmostrar;
	}


	public List<RangoCanjeCompensacion> getRangoscanje() {
		return rangoscanje;
	}


	public void setRangoscanje(List<RangoCanjeCompensacion> rangoscanje) {
		this.rangoscanje = rangoscanje;
	}


	public String getRango() {
		return rango;
	}


	public void setRango(String rango) {
		this.rango = rango;
	}


	public List<RangoCanjeCompensacion> getSelectedRangos() {
		return selectedRangos;
	}


	public void setSelectedRangos(List<RangoCanjeCompensacion> selectedRangos) {
		this.selectedRangos = selectedRangos;
	}
	
		
}
