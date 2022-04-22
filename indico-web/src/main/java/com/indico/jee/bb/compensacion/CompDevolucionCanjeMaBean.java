package com.indico.jee.bb.compensacion;

import static com.indico.jee.util.Constants.HEAD;
import static com.indico.jee.util.Constants.FOOTER;
import static com.indico.jee.util.Constants.TODAS_LITERAL;
import static com.indico.jee.util.Constants.UTF8_LITERAL;
import static com.indico.jee.util.Constants.CONTENT_DISPOSITION_LITERAL;
import static com.indico.jee.util.Constants.FORMAT_LITERAL_5;

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
import com.indico.jee.modelo.AnalisisChequesCompensa;
import com.indico.jee.modelo.MedioServCompensacion;
import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;


@SuppressWarnings("deprecation")
@ManagedBean(name="compDevolucionCanjeMaBean")
@ViewScoped
public class CompDevolucionCanjeMaBean extends GraficaGeneralBean {

	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompDevolucionCanjeMaBean.class);
	
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMMdd");
	private final String preparedDate = formatter.format(date);
	
	private final String HEADER = 	"	<Header>	\r\n" +
									"			<ID>Indico - Devolución al Canje</ID>	\r\n" +
									"			<Test>false</Test>													\r\n" +
									"			<Truncated>false</Truncated>										\r\n" +
									"			<Name xml:lang=\"es\">Indico - Devolución al Canje</Name>	\r\n" +
									"			<Prepared>"+preparedDate+"</Prepared>	\r\n" +
									"			<Sender id=\"INDICO\">	\r\n" +
									"				<Name xml:lang=\"en\">Banco de la República</Name>	\r\n" +
									"			</Sender>	\r\n" +
									"	</Header>	\r\n" +
									"	<DataSet>	\r\n" +
									"		<generic:KeyFamilyRef>INDICO_devolucion_al_canje</generic:KeyFamilyRef>	\r\n" +
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
    private boolean 					ocultarmostrar = true;
	private List<MedioServCompensacion> mediosdeservicio;
	private String 						medioservicio;
	
       	
    @PostConstruct
    public void init() {
    	try {
        	mediosdeservicio = ServiceFacades.getInstance().getMedioServCompensacionServiceService().finAll();
        	setPeriodicidad("1");
        	setMedioservicio(TODAS_LITERAL);
        	setTipoaexportar("");
    	} catch (Exception ex) {
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Invalida", null));
    	}
    }
       
    
    @Override
    public void restaurarAction(ActionEvent evt){
    	super.restaurarAction(evt);
    	setPeriodicidad("1");
    	setMedioservicio(TODAS_LITERAL);
    	setTipoaexportar("");
    }

	public void exportAction(ActionEvent evt) {
		try {
			if (getTipoaexportar()!=null) { 
		    	List<AnalisisChequesCompensa>  resultado = null; 
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
		        if (medioservicio.equals(TODAS_LITERAL)) {
		        	resultado =  ServiceFacades.getInstance().getAnalisisChequesCompensaService().getDataDiarioDevolCanjeTodas(fechaInicial, fechaFinal, Integer.valueOf(getPeriodicidad()) );
		        } else {
		        	resultado =  ServiceFacades.getInstance().getAnalisisChequesCompensaService().getDataDiarioDevolCanje(fechaInicial, fechaFinal, getMedioservicio(), Integer.valueOf(getPeriodicidad())  );
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
			}
    	} catch(ParseException | IndicoException ex) {
			logger.info(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Invalida", null));
    	}	
	}
	
    private void createXMLFile (List<AnalisisChequesCompensa> valores) {
    	HSSFWorkbook wb = null;
    	OutputStream output  = null;
    	try {
    		wb = new HSSFWorkbook();
    		Sheet sheet = wb.createSheet("indico_devolucion_al_canje");
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("Fecha");
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Sesión Compensación");
			cellHeader = rowHeader.createCell(2);
			cellHeader.setCellValue("Medio");
			cellHeader = rowHeader.createCell(3);
			cellHeader.setCellValue("Ciudad");
			cellHeader = rowHeader.createCell(4);
			cellHeader.setCellValue("Cant Cheques");
			cellHeader = rowHeader.createCell(5);
			cellHeader.setCellValue("Valor");
			int rownum = 1;
			CellStyle cellStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
			double totalValor = 0d;
			double totalCantidad = 0d;
    		for (AnalisisChequesCompensa ana : valores) {
    			Row row = sheet.createRow(rownum++);
    			Cell cell = row.createCell(0);
    			cell.setCellStyle(cellStyle);
    			cell.setCellValue(ana.getPk().getFechaCompensacion());
    			cell = row.createCell(1);
    			cell.setCellValue(ana.getDescSesionCompensacion());
    			cell = row.createCell(2);
    			cell.setCellValue(ana.getDescMedioServCompensacion());
    			cell = row.createCell(3);
    			cell.setCellValue(ana.getNombreCiudadCompensacion());
    			cell = row.createCell(4);
    			totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDouble(ana.getNumCheques());
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getNumCheques()));
    			cell = row.createCell(5);
    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(ana.getValorPresentado());
    			cell.setCellValue(IndicoUtils.getInstance().assertDoubleMM(ana.getValorPresentado()));
    		}
			Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(1);
			cell.setCellValue("TOTALES");
			cell = row.createCell(4);
			cell.setCellValue(totalCantidad);
			cell = row.createCell(5);
			cell.setCellValue(totalValor);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/xls");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_devolucion_al_canje.xls");
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
    
    private void createCSVFile(List<AnalisisChequesCompensa> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/comma-separated-values");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_devolucion_al_canje.csv");
			output = response.getOutputStream();
        	String line = MessageFormat.format(FORMAT_LITERAL_5,"Fecha","Sesión Compensación","Medio","Ciudad","Cant Cheques","Valor");
        	output.write((line+"\r\n").getBytes());
			double totalCantidad = 0d;
			double totalValor = 0d;
			if(valores!=null && !valores.isEmpty()) {
	        	for (AnalisisChequesCompensa ana : valores) {
		        	line = MessageFormat.format(FORMAT_LITERAL_5,	        	
		        		formatter2.format(ana.getPk().getFechaCompensacion()),
		        		ana.getDescSesionCompensacion(),
		        		ana.getDescMedioServCompensacion(),
		        		ana.getNombreCiudadCompensacion(),
		        		String.format("%.0f", ana.getNumCheques()),
		        		String.format("%.2f", ana.getValorPresentado())
		        		//String.valueOf(IndicoUtils.getInstance().assertDouble(ana.getValorPresentado()))
		    		);
		        	output.write((line + "\r\n").getBytes());
		        	totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDoubleMM(ana.getNumCheques());
	    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(ana.getValorPresentado());
		        }//for
			}//if
        	line = MessageFormat.format(FORMAT_LITERAL_5,"TOTALES","","","",String.format("%.0f", totalCantidad),String.format("%.2f", totalValor));
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

    private void createSDMXFile(List<AnalisisChequesCompensa> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/xml");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_devolucion_al_canje_sdmx.xml");
			output = response.getOutputStream();
			String resultado = HEAD + HEADER;
			for (AnalisisChequesCompensa valor : valores ) {
				resultado = resultado .concat( MessageFormat.format(
								"<generic:Obs>	\r\n" +
	    						"	<generic:Time name=\"Fecha\" >{0}</generic:Time> \r\n" +
	    						"	<generic:ObsValue name=\"Sesión Compensación\" value=\"{1}\" /> \r\n" +
	    						"	<generic:ObsValue name=\"Medio\" value=\"{2}\" /> \r\n" +
	    						"	<generic:ObsValue name=\"Ciudad\" value=\"{3}\" /> \r\n" +
	    						"	<generic:ObsValue name=\"Cantidad de Cheques\" value=\"{4}\" /> \r\n" +
	    						"	<generic:ObsValue name=\"Valor\" value=\"{5}\" /> \r\n" +
	    						"	</generic:Obs>	\r\n",
	    				formatter3.format(valor.getPk().getFechaCompensacion()),
						valor.getDescSesionCompensacion(),
						valor.getDescMedioServCompensacion(),
						valor.getNombreCiudadCompensacion(),
						String.format("%.0f", valor.getNumCheques()),
						String.format("%.2f", valor.getValorPresentado())
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

	public String getMedioservicio() {
		return medioservicio;
	}

	public void setMedioservicio(String medioservicio) {
		this.medioservicio = medioservicio;
	}

	public List<MedioServCompensacion> getMediosdeservicio() {
		return mediosdeservicio;
	}

	public void setMediosdeservicio(List<MedioServCompensacion> mediosdeservicio) {
		this.mediosdeservicio = mediosdeservicio;
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
