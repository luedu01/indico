package com.indico.jee.bb.depositoscud;

import static com.indico.jee.util.Constants.HEAD;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.primefaces.PrimeFaces;
import com.indico.exceptions.IndicoException;
import com.indico.jee.bb.GraficaGeneralBean;
import com.indico.jee.util.ValorGraficable;
import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;
import static com.indico.jee.util.Constants.*;

@SuppressWarnings("deprecation")
@ManagedBean(name="pagosRotacionMaBean")
@ViewScoped
public class PagosRotacionMaBean extends GraficaGeneralBean {

	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PagosRotacionMaBean.class);
	
    private boolean ocultarmostrar = true;
    
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMMdd");
	
	private final String preparedDate = formatter.format(date);
	
	private final String HEADER = 	"	<Header>	\r\n" +
			"	<ID>Indico - Datos Estadísticos</ID>	\r\n" +
			"	<Test>false</Test>													\r\n" +
			"	<Truncated>false</Truncated>										\r\n" +
			"	<Name xml:lang=\"es\">Indico - Datos Estadísticos</Name>	\r\n" +
			"	<Prepared>"+preparedDate+"</Prepared>	\r\n" +
			"	<Sender id=\"INDICO\">	\r\n" +
			"	<Name xml:lang=\"en\">Banco de la República</Name>	\r\n" +
			"	</Sender>	\r\n" +
			"	</Header>	\r\n" +
			"	<DataSet>	\r\n" +
			"		<generic:KeyFamilyRef>INDICO_Datos_Estadísticos</generic:KeyFamilyRef>	\r\n" +
			"		<generic:Group type=\"SiblingGroup\">	\r\n" +
			"			<generic:Attributes>	\r\n" +
			"				<generic:Value concept=\"DECIMALS\" value=\"2\"/>		\r\n" +
			"				<generic:Value concept=\"UNIT_MEASURE\" value=\"COP\"/>	\r\n" +
			"				<generic:Value concept=\"UNIT_MULT\" value=\"1\"/>		\r\n" +
			"			</generic:Attributes>	\r\n" +
			"			<generic:Series>	\r\n" +
			"				<generic:SeriesKey>		\r\n" +
			"					<generic:Value concept=\"FREQ\" value=\"D\"/>	\r\n" +
			"				</generic:SeriesKey>	\r\n" +
			"				<generic:Attributes>	\r\n" +
			"					<generic:Value concept=\"TIME_FORMAT\" value=\"102\"/>	\r\n" +
			"				</generic:Attributes>	\r\n"; 
    private String tipoaexportar;
    private String start;
    private String end;
    private String periodicidad;
    
	
        	
    @PostConstruct
    public void init() {
    	//Carga los parametros
    	periodicidad="1";
    }

    public void restaurarAction(ActionEvent evt){
    	periodicidad="1";
    	 PrimeFaces.current().executeScript( " iefix();");
    }

    public void changeTabExportar() {
    	setTipoaexportar("");
    }
    
	public void exportAction(ActionEvent evt) {
    	try {
    		String TipoExpo = getTipoaexportar();
        	String a = (String)evt.getComponent().getAttributes().get("start");
        	String b = (String)evt.getComponent().getAttributes().get("end");
        	if (a==null || b==null) throw new IndicoException("Fechas Invalidas");
        	Date fechaInicial=null;
        	Date fechaFinal=null;
        	synchronized (receiveFormat) {
            	fechaInicial = receiveFormat.parse(a);
            	fechaFinal= receiveFormat.parse(b);
    		}
    		if (TipoExpo!=null) {
	        	List<ValorGraficable>  resultado = ServiceFacades.getInstance().getAnalisisPagosSaldosXHoraService().getDetalleExportarRotaDesdeHastaVG(fechaInicial, fechaFinal);
	
	    		if (TipoExpo!=null) {
		    		if (TipoExpo.equals("xls")) {
		            	createXMLFile(resultado);
		    		} else if (TipoExpo.equals("csv")) {
		            	createCSVFile(resultado);
		    		} else if (TipoExpo.equals("sdmx")) {
		    			createSDMXFile(resultado);
		    		}
		            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Archivo exportado satisfactoriamente", null));
	    		} 
	    		
	    		TipoExpo = null;
		    	setTipoaexportar("");
    		}

    	} catch(ParseException | IndicoException ex) {
			logger.info(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Invalida", null));
    	}	
	}
	
	public void changePanelAction() {
		/*
		 * required for p:ajaxListener
		 */
	}
	
    private void createXMLFile (List<ValorGraficable> valores) {
    	HSSFWorkbook wb = null;
    	OutputStream output  = null;
    	try {
    		wb = new HSSFWorkbook();
    		Sheet sheet = wb.createSheet("Datos Estadísticos");
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("Fecha");
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Rotación");
			int rownum = 1;
			CellStyle cellStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
			double totalValor = 0d;
    		for (ValorGraficable ana : valores) {
    			Row row = sheet.createRow(rownum++);
    			Cell cell = row.createCell(0);
    			cell.setCellStyle(cellStyle);
    			cell.setCellValue(ana.getEjeX());
    			cell = row.createCell(1);
    			cell.setCellValue(ana.getSerieValor()==null?0d:ana.getSerieValor().doubleValue());
    			cell.setCellValue(IndicoUtils.getInstance().assertDoubleMM(ana.getSerieValor()));
    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(ana.getSerieValor());
    		}
			Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(0);
			cell.setCellValue("Total");
			cell = row.createCell(1);
			cell.setCellValue(totalValor);
    		
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/xls");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_pagos_rotacion.xls");
			output = response.getOutputStream();
			output.write(outputStream.toByteArray());
			fc.responseComplete();
    	} catch(IOException ex) {
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
    
    /* Csv File to download on select a option without p:fileuoload only over a method*/
    private void createCSVFile(List<ValorGraficable> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/comma-separated-values");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_pagos_rotacion.csv");
			output = response.getOutputStream();
        	String line = MessageFormat.format(FORMAT_LITERAL_1,"Fecha","Rotación");
        	output.write((line+"\r\n").getBytes());
			double totalValor = 0d;
			if(valores!=null && !valores.isEmpty()) {
	        	for (ValorGraficable ana : valores) {
		        	line = MessageFormat.format(FORMAT_LITERAL_1,	        	
		        		ana.getEjeX(),
		        		String.format("%.2f", ana.getSerieValor())
		    			//String.valueOf(IndicoUtils.getInstance().assertDouble(ana.getSerieValor()))
		    		);
		        	output.write((line + "\r\n").getBytes());
	    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(ana.getSerieValor());
		        }//for
			}//if
        	//line = MessageFormat.format(FORMAT_LITERAL_1,"Total",(String.valueOf(totalValor)));
			line = MessageFormat.format(FORMAT_LITERAL_1,"Total",(String.format("%.2f", totalValor)));
        	output.write((line + "\r\n").getBytes());
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

    private void createSDMXFile(List<ValorGraficable> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/xml");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_pagos_rotacion_sdmx.xml");
			output = response.getOutputStream();
			String resultado = HEAD + HEADER;
			for (ValorGraficable valor : valores ) {
				try {
					Date dateRotacion = new SimpleDateFormat("yyyy-MM-dd").parse(valor.getEjeX());
					resultado = resultado .concat( MessageFormat.format(
							"<generic:Obs>	\r\n" +
									"<generic:Time name=\"Fecha\" >{0}</generic:Time> \r\n" +
									"	<generic:ObsValue name=\"Rotación\" value=\"{1}\" /> \r\n" +
									"</generic:Obs>	\r\n", 
							formatter3.format(dateRotacion),
							valor.getSerieValor() ));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			resultado = resultado + FOOTER;
			output.write(resultado.getBytes());
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
    
	public boolean isOcultarmostrar() {
		return ocultarmostrar;
	}

	public void setOcultarmostrar(boolean ocultarmostrar) {
		this.ocultarmostrar = ocultarmostrar;
	}

	public String getTipoaexportar() {
		return tipoaexportar;
	}

	public void setTipoaexportar(String tipoaexportar) {
		this.tipoaexportar = tipoaexportar;
	}

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
