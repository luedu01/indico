package com.indico.jee.bb.compennaciointer;

import static com.indico.jee.util.Constants.FORMAT_LITERAL_2;
import static com.indico.jee.util.Constants.UTF8_LITERAL;
import static com.indico.jee.util.Constants.CONTENT_DISPOSITION_LITERAL;
import static com.indico.jee.util.Constants.HEAD;
import static com.indico.jee.util.Constants.FOOTER;

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

import com.indico.exceptions.IndicoException;
import com.indico.jee.bb.GraficaGeneralBean;
import com.indico.jee.modelo.AnalisisTransaccionCNI;
import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;

@SuppressWarnings("deprecation")
@ManagedBean(name="transfeBajoValorMaBean")
@ViewScoped
public class TransfeBajoValorMaBean extends GraficaGeneralBean {
	
	private static final long serialVersionUID = 1L;
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TransfeBajoValorMaBean.class);
	
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMMdd");
	private final String preparedDate = formatter.format(date);
	
	private final String HEADER = 	"	<Header>	\r\n" +
			"			<ID>Indico - Transferencia Electrónica Bajo Valor</ID>	\r\n" +
			"			<Test>false</Test>													\r\n" +
			"			<Truncated>false</Truncated>										\r\n" +
			"			<Name xml:lang=\"es\">Indico - Transferencia Electrónica Bajo Valor</Name>	\r\n" +
			"			<Prepared>"+preparedDate+"</Prepared>	\r\n" +
			"			<Sender id=\"INDICO\">	\r\n" +
			"				<Name xml:lang=\"en\">Banco de la República</Name>	\r\n" +
			"			</Sender>	\r\n" +
			"	</Header>	\r\n" +
			"	<DataSet>	\r\n" +
			"		<generic:KeyFamilyRef>INDICO_transferencia_electronica_bajo_valor</generic:KeyFamilyRef>	\r\n" +
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

	//ocultar mostrar mensaje
	private boolean ocultarmostrar = true;


	@PostConstruct
	public void init() {
		try {
			setPeriodicidad("1");
			setTipoaexportar("");
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Invalida", null));
		}
	}
	
	@Override
    public void restaurarAction(ActionEvent evt){
    	super.restaurarAction(evt);
    	setPeriodicidad("1");
    	setTipoaexportar("");
    }
	
	public void exportAction(ActionEvent evt) {
		try {
			if (getTipoaexportar()!=null) { 
		    	String a = (String)evt.getComponent().getAttributes().get("start");
		    	String b = (String)evt.getComponent().getAttributes().get("end");
		    	if (a==null || b==null) {
		    		throw new IndicoException("Rango no permitido");
		    	}
	        	Date fechaInicial=null;
	        	Date fechaFinal=null;
	        	synchronized (receiveFormat) {
	            	fechaInicial = receiveFormat.parse(a);
	            	fechaFinal= receiveFormat.parse(b);
	    		}
		        
		        List<AnalisisTransaccionCNI> resultado =  ServiceFacades.getInstance().getTransaccionCNIService().getDataDiarioTransaccionCNI(fechaInicial, fechaFinal);
		        		        
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
			}
    	} catch(ParseException | IndicoException ex) {
			logger.info(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Invalida", null));
    	}	
	}
	
	private void createXMLFile (List<AnalisisTransaccionCNI> valores) {
    	HSSFWorkbook wb = null;
    	OutputStream output  = null;
    	try {
    		wb = new HSSFWorkbook();
    		Sheet sheet = wb.createSheet("indico_transferencia_electronica_bajo_valor");
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("Fecha");
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Valor Transacción");
			cellHeader = rowHeader.createCell(2);
			cellHeader.setCellValue("Cantidad");
			int rownum = 1;
			CellStyle cellStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
			double totalValor = 0d;
			double totalCantidad = 0d;
    		for (AnalisisTransaccionCNI ana : valores) {
    			Row row = sheet.createRow(rownum++);
    			Cell cell = row.createCell(0);
    			cell.setCellStyle(cellStyle);
    			cell.setCellValue(ana.getFechaTransaccion());
    			cell = row.createCell(1);
    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(ana.getValorTransaccion());
    			//cell.setCellValue(String.format("%.2f", ana.getValorTransaccion()));
    			cell.setCellValue(IndicoUtils.getInstance().assertDoubleMM(ana.getValorTransaccion()));
    			cell = row.createCell(2);
    			totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDoubleMM(ana.getNumTransacciones());
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getNumTransacciones()));
    		}
			Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(0);
			cell.setCellValue("TOTALES");
			cell = row.createCell(2);
			cell.setCellValue(totalCantidad);
			cell = row.createCell(1);
			//cell.setCellValue(String.format("%.2f", totalValor));
			cell.setCellValue(totalValor);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/xls");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_transferencia_electronica_bajo_valor.xls");
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
	
	private void createCSVFile(List<AnalisisTransaccionCNI> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/comma-separated-values");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_transferencia_electronica_bajo_valor.csv");
			output = response.getOutputStream();
        	String line = MessageFormat.format(FORMAT_LITERAL_2,"Fecha","Valor Transacción","Cantidad");
        	output.write((line+"\r\n").getBytes());
			double totalCantidad = 0d;
			double totalValor = 0d;
			if(valores!=null && !valores.isEmpty()) {
	        	for (AnalisisTransaccionCNI ana : valores) {
		        	line = MessageFormat.format(FORMAT_LITERAL_2,	        	
		        			formatter2.format(ana.getFechaTransaccion()),
		        			String.format("%.2f", ana.getValorTransaccion()),
		        			String.format("%.0f", ana.getNumTransacciones())
		    		);
		        	output.write((line + "\r\n").getBytes());
		        	totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDouble(ana.getNumTransacciones());
	    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(ana.getValorTransaccion());
		        }//for
			}//if
        	line = MessageFormat.format(FORMAT_LITERAL_2,"TOTALES",String.format("%.2f", totalValor),String.format("%.0f", totalCantidad));
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
	
	private void createSDMXFile(List<AnalisisTransaccionCNI> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/xml");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_transferencia_electronica_bajo_valor_sdmx.xml");
			output = response.getOutputStream();
			String resultado = HEAD + HEADER;
			for (AnalisisTransaccionCNI valor : valores ) {
				resultado = resultado .concat( MessageFormat.format(
								"<generic:Obs>	\r\n" +
	    						"	<generic:Time name=\"Fecha\">{0}</generic:Time> \r\n" +
	    						"	<generic:ObsValue name=\"Valor Transacción\" value=\"{1}\" /> \r\n" +
	    						"	<generic:ObsValue name=\"Cantidad\" value=\"{2}\" /> \r\n" +
	    						"	</generic:Obs>	\r\n",
	    				formatter3.format(valor.getFechaTransaccion()),
	    				String.format("%.2f", valor.getValorTransaccion()),
						//IndicoUtils.getInstance().assertDouble(valor.getNumTransacciones())
	    				String.format("%.0f", valor.getNumTransacciones())
				));
				resultado = resultado.concat("\r\n");
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
	
}
