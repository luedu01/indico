package com.indico.jee.bb.compensacion;

import static com.indico.jee.util.Constants.HEAD;
import static com.indico.jee.util.Constants.CONTENT_DISPOSITION_LITERAL;
import static com.indico.jee.util.Constants.FOOTER;
import static com.indico.jee.util.Constants.FORMAT_LITERAL_3;
import static com.indico.jee.util.Constants.UTF8_LITERAL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.indico.jee.bb.GraficaGeneralBean;
import com.indico.jee.modelo.AnalisisChequesPesosConstantes;
import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;

@SuppressWarnings("deprecation")
@ManagedBean(name="compChequesPesoMaBean")
@ViewScoped
public class CompChequesPesoMaBean extends GraficaGeneralBean {

	private static final long serialVersionUID = 1L;
 
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompChequesPesoMaBean.class);
	
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	private final String preparedDate = formatter.format(date);
	
	private final String HEADER = 	"	<Header>	\r\n" +
									"			<ID>Indico - Comportamiento Anualizado del Canje de Cheques</ID>	\r\n" +
									"			<Test>false</Test>													\r\n" +
									"			<Truncated>false</Truncated>										\r\n" +
									"			<Name xml:lang=\"es\">Indico - Comportamiento Anualizado del Canje de Cheques</Name>	\r\n" +
									"			<Prepared>"+preparedDate+"</Prepared>	\r\n" +
									"			<Sender id=\"INDICO\">	\r\n" +
									"				<Name xml:lang=\"en\">Banco de la República</Name>	\r\n" +
									"			</Sender>	\r\n" +
									"	</Header>	\r\n" +
									"	<DataSet>	\r\n" +
									"		<generic:KeyFamilyRef>INDICO_Comportamiento_Anualizado_Canje_Cheques</generic:KeyFamilyRef>	\r\n" +
									"		<generic:Group type=\"SiblingGroup\">	\r\n" +
									"			<generic:Attributes>	\r\n" +
									"				<generic:Value concept=\"DECIMALS\" value=\"2\"/>		\r\n" +
									"				<generic:Value concept=\"UNIT_MEASURE\" value=\"COP\"/>	\r\n" +
									"				<generic:Value concept=\"UNIT_MULT\" value=\"1\"/>		\r\n" +
									"			</generic:Attributes>	\r\n" +
									"			<generic:Series>	\r\n" +
									"				<generic:SeriesKey>		\r\n" +
									"					<generic:Value concept=\"FREQ\" value=\"A\"/>	\r\n" +
									"				</generic:SeriesKey>	\r\n" +
									"				<generic:Attributes>	\r\n" +
									"					<generic:Value concept=\"TIME_FORMAT\" value=\"602\"/>	\r\n" +
									"				</generic:Attributes>	\r\n"; 
	
	//ocultar mostrar mensaje
    private boolean 						ocultarmostrar = true;
	
    @PostConstruct
    public void init() {
    	    
    }
    
	public void exportAction(ActionEvent evt) {
		
		if (getTipoaexportar()!=null) {				
		
			List<AnalisisChequesPesosConstantes>  resultado = null; 
			
			resultado =  ServiceFacades.getInstance().getAnalisisPesoContantesService().getDataPesosConstantes();
			
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
	}
	
    private void createXMLFile (List<AnalisisChequesPesosConstantes> valores) {
    	HSSFWorkbook wb = null;
    	OutputStream output  = null;
    	try {
    		wb = new HSSFWorkbook();
    		Sheet sheet = wb.createSheet("indico_anualizado_canje");
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("Año");
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Cantidad");
			cellHeader = rowHeader.createCell(2);
			cellHeader.setCellValue("Valor");
			cellHeader = rowHeader.createCell(3);
			cellHeader.setCellValue("IPC");
			int rownum = 1;

    		for (AnalisisChequesPesosConstantes ana : valores) {
    			Row row = sheet.createRow(rownum++);
    			Cell cell = row.createCell(0);
    			cell.setCellValue(ana.getAnioCanje());
    			cell = row.createCell(1);
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getNumChequesCanje()));
    			cell = row.createCell(2);
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getCvValorCanje()));
    			cell = row.createCell(3);
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(ana.getCvIPCAnual()));
    		}
    		
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/xls");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_anualidad_canje.xls");
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
    
    private void createCSVFile(List<AnalisisChequesPesosConstantes> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/comma-separated-values");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_anualidad_canje.csv");
			output = response.getOutputStream();
        	String line = MessageFormat.format(FORMAT_LITERAL_3,"Año","Cantidad","Valor","IPC");
        	output.write((line+"\r\n").getBytes());
			//double totalCantidad = 0d;
			//double totalValor = 0d;
			if(valores!=null && !valores.isEmpty()) {
	        	for (AnalisisChequesPesosConstantes ana : valores) {
		        	line = MessageFormat.format(FORMAT_LITERAL_3,	        	
			        		ana.getAnioCanje(),
			        		String.format("%.0f", ana.getNumChequesCanje()),
			        		String.format("%.2f", ana.getCvValorCanje()),
			        		String.format("%.2f", ana.getCvIPCAnual())
		    		);
		        	output.write((line + "\r\n").getBytes());
		        	//totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDouble(ana.getNumChequesCanje());
	    			//totalValor    = totalValor + IndicoUtils.getInstance().assertDouble(ana.getCvValorCanje());
		        }//for
			}//if
        	//line = MessageFormat.format(FORMAT_LITERAL_3,"TOTALES","","","",totalCantidad,totalValor);
        	//output.write((line + "\r\n").getBytes());
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

    private void createSDMXFile(List<AnalisisChequesPesosConstantes> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/xml");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_anualidad_canje_sdmx.xml");
			output = response.getOutputStream();
			String resultado = HEAD + HEADER;
			for (AnalisisChequesPesosConstantes valor : valores ) {
				resultado = resultado.concat( MessageFormat.format(
						"				<generic:Obs>	\r\n" +
						"					<generic:Time name=\"Año\" >{0}</generic:Time> \r\n" +
						"					<generic:ObsValue name=\"Cantidad\" value=\"{1}\" /> \r\n" +
						"					<generic:ObsValue name=\"Valor\" value=\"{2}\" /> \r\n" +
						"					<generic:ObsValue name=\"IPC\" value=\"{3}\" /> \r\n" +
						"				</generic:Obs>	\r\n", 
						valor.getAnioCanje(),
					
						String.format("%.0f", valor.getNumChequesCanje()),
						String.format("%.2f", valor.getCvValorCanje()),
						IndicoUtils.getInstance().assertDouble(valor.getCvIPCAnual())
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