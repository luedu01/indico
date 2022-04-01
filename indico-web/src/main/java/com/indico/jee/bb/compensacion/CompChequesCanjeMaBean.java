package com.indico.jee.bb.compensacion;

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
import com.indico.jee.bb.GraficaGeneralBean;
import com.indico.jee.modelo.AnalisisChequesCompensa;
import com.indico.jee.modelo.MedioServCompensacion;
import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;
import static com.indico.jee.util.Constants.*;

@SuppressWarnings("deprecation")
@ManagedBean(name="compChequesCanjeMaBean")
@ViewScoped
public class CompChequesCanjeMaBean extends GraficaGeneralBean {

	private static final long serialVersionUID = 1L;
 
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompChequesCanjeMaBean.class);
	
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMMdd");
	private final String preparedDate = formatter.format(date);
	
	private final String HEADER =	"	<Header>	\r\n" +
									"	<ID>Indico - Distribución Tipo Plaza Canje Al Cobro</ID>	\r\n" +
									"	<Test>false</Test>													\r\n" +
									"	<Truncated>false</Truncated>										\r\n" +
									"	<Name xml:lang=\"es\">Indico - Distribución Tipo Plaza Canje Al Cobro</Name>	\r\n" +
									"	<Prepared>"+preparedDate+"</Prepared>	\r\n" +
									"	<Sender id=\"INDICO\">	\r\n" +
									"	<Name xml:lang=\"en\">Banco de la República</Name>	\r\n" +
									"	</Sender>	\r\n" +
									"	</Header>	\r\n" +
									"	<DataSet>	\r\n" +
									"		<generic:KeyFamilyRef>INDICO_Distribución_Tipo_Plaza_Canje_Al_Cobro</generic:KeyFamilyRef>	\r\n" +
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
		
	private List<MedioServCompensacion> tiposdeplaza;
	private String tipodeplaza;
	
       	
    @PostConstruct
    public void init() {
    	tiposdeplaza = ServiceFacades.getInstance().getMedioServCompensacionServiceService().finAll();
    	setPeriodicidad("1");
    	setTipodeplaza(TODAS_LITERAL);
    	setTipoaexportar("");
    }
    
    @Override
    public void restaurarAction(ActionEvent evt){
    	super.restaurarAction(evt);
    	tipodeplaza=TODAS_LITERAL;
    	setTipoaexportar("");
    }

	public void exportAction(ActionEvent evt) {
		try {
			if (getTipoaexportar()!=null) {				
				
		    	List<AnalisisChequesCompensa>  resultado = null; 
		    	String a = (String)evt.getComponent().getAttributes().get("start");
		    	String b = (String)evt.getComponent().getAttributes().get("end");
	        	Date fechaInicial=null;
	        	Date fechaFinal=null;
	        	synchronized (receiveFormat) {
	            	fechaInicial = receiveFormat.parse(a);
	            	fechaFinal= receiveFormat.parse(b);
	    		}
		        if (tipodeplaza.equals(TODAS_LITERAL)) {
		        	//res = ServiceFacades.getInstance().getAnalisisChequesCompensaService().getCompenAnualGR();
		        	resultado =  ServiceFacades.getInstance().getAnalisisChequesCompensaService().getDataDiarioCompenTodas(fechaInicial, fechaFinal, Integer.valueOf(getPeriodicidad()) );
		        } else {
		        	resultado =  ServiceFacades.getInstance().getAnalisisChequesCompensaService().getDataDiarioCompen(fechaInicial, fechaFinal, getTipodeplaza(), Integer.valueOf(getPeriodicidad()));
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
    	} catch(ParseException ex) {
			logger.info(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha Invalida", null));
    	}	
	}
	
    private void createXMLFile (List<AnalisisChequesCompensa> valores) {
    	HSSFWorkbook wb = null;
    	OutputStream output  = null;
    	try {
    		wb = new HSSFWorkbook();
    		Sheet sheet = wb.createSheet("indico_canje_al_cobro");
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("Fecha");
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Sesión Compensación");
			cellHeader = rowHeader.createCell(2);
			cellHeader.setCellValue("Ciudad");
			cellHeader = rowHeader.createCell(3);
			cellHeader.setCellValue("Medio");
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
    			cell.setCellValue(ana.getNombreCiudadCompensacion());
    			cell = row.createCell(3);
    			cell.setCellValue(ana.getDescMedioServCompensacion());
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
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_canje_al_cobro.xls");
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
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_canje_al_cobro.csv");
			output = response.getOutputStream();
        	String line = MessageFormat.format(FORMAT_LITERAL_5,"Fecha","Sesión Compensación","Ciudad","Medio","Cant Cheques","Valor");
        	output.write((line+"\r\n").getBytes());
			double totalCantidad = 0d;
			double totalValor = 0d;
			if(valores!=null && !valores.isEmpty()) {
	        	for (AnalisisChequesCompensa ana : valores) {
		        	line = MessageFormat.format(FORMAT_LITERAL_5,	        	
		        			formatter2.format(ana.getPk().getFechaCompensacion()),
			        		ana.getDescSesionCompensacion(),
			        		ana.getNombreCiudadCompensacion(),
			        		ana.getDescMedioServCompensacion(),
			        		String.format("%.0f", ana.getNumCheques()),
			        		//String.valueOf(IndicoUtils.getInstance().assertDouble(ana.getValorPresentado()))
			        		String.format("%.2f", ana.getValorPresentado())
		    		);
		        	output.write((line + "\r\n").getBytes());
		        	totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDoubleMM(ana.getNumCheques());
	    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(ana.getValorPresentado());
		        }//for
			}//if
        	line = MessageFormat.format(FORMAT_LITERAL_5,"TOTALES","","","",String.format("%.0f", totalCantidad), String.format("%.2f", totalValor));
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
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=Distribucion_tipo_plaza_canje_al_cobro_dmx.xml");
			output = response.getOutputStream();
			String resultado = HEAD + HEADER;
			for (AnalisisChequesCompensa valor : valores ) {
				resultado = resultado .concat( MessageFormat.format( 
						"	<generic:Obs>	\r\n" +
						"	<generic:Time name=\"Fecha\" >{0}</generic:Time> \r\n" +
						"	<generic:ObsValue name=\"Sesión Compensación\" value=\"{1}\" /> \r\n" +
						"	<generic:ObsValue name=\"Ciudad\" value=\"{2}\" /> \r\n" +
						"	<generic:ObsValue name=\"Medio\" value=\"{3}\" /> \r\n" +
						"	<generic:ObsValue name=\"Cantidad de Cheques\" value=\"{4}\" /> \r\n" +
						"	<generic:ObsValue name=\"Valor\" value=\"{5}\" /> \r\n" +
						"	</generic:Obs>	\r\n", 				
						formatter3.format(valor.getPk().getFechaCompensacion()),
						valor.getDescSesionCompensacion(),
						valor.getNombreCiudadCompensacion(),
						valor.getDescMedioServCompensacion(),
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

	public String getTipodeplaza() {
		return tipodeplaza;
	}

	public void setTipodeplaza(String tipodeplaza) {
		this.tipodeplaza = tipodeplaza;
	}

	public List<MedioServCompensacion> getTiposdeplaza() {
		return tiposdeplaza;
	}

	public void setTiposdeplaza(List<MedioServCompensacion> tiposdeplaza) {
		this.tiposdeplaza = tiposdeplaza;
	}

}
