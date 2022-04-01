package com.indico.jee.bb.depositoscud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


import com.indico.jndi.ServiceFacades;
import com.indico.util.IndicoUtils;
import com.indico.jee.bb.GraficaGeneralBean;
import com.indico.jee.modelo.TransfElectronicaAltoValor;
import com.indico.jee.util.CampoSelect;

import static com.indico.jee.util.Constants.*;

@SuppressWarnings("deprecation")
@ManagedBean(name="transferenciaBean")
@ViewScoped
public class TransferenciaBean extends GraficaGeneralBean {

	private static final long serialVersionUID = 1L;
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TransferenciaBean.class);
	
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMMdd");
	private final String preparedDate = formatter.format(date);
	
	private final String HEADER = 	"	<Header>	\r\n" +
			"	<ID>Indico - Datos Estadisticos</ID>	\r\n" +
			"	<Test>false</Test>													\r\n" +
			"	<Truncated>false</Truncated>										\r\n" +
			"	<Name xml:lang=\"es\">Indico - Datos Estadísticos</Name>	\r\n" +
			"	<Prepared>"+preparedDate+"</Prepared>	\r\n" +
			"	<Sender id=\"INDICO\">	\r\n" +
			"	<Name xml:lang=\"es\">Banco de la República</Name>	\r\n" +
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
	
	
    //ocultar mostrar mensaje
    private boolean 			ocultarmostrar;
    private String 				tipoPeriodoSelected;
    private List<CampoSelect> 	conceptos;
    private String 				concepto;
    private Map<String,String> 	conceptosSele;
    private String 				tipoaexportar;
    private String 				start;
    private String  			end;
 
    	
    @PostConstruct
    public void init() {
    	//Carga los parametros
        setTipoPeriodoSelected("1");
    	setConceptos(new ArrayList<CampoSelect>());
    	getConceptos().add(new CampoSelect("Todos",TODOS_LITERAL));
        setConcepto(TODOS_LITERAL); 
        getConceptos().addAll(ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getGrupos());
        setConceptosSele(new HashMap<String,String>());
        if (getConceptos()!=null) for (CampoSelect cs: getConceptos()) getConceptosSele().put(cs.getValue(), cs.getLabel());
        else setConceptos(new ArrayList<CampoSelect>());
    }
    
    
    public void changePanelAction() {
    	/*
    	 * requiered for p:ajax listener
    	 */
    }

    public void restaurarAction(ActionEvent evt){
    	setTipoPeriodoSelected("1");
        setConcepto(TODOS_LITERAL); 
        PrimeFaces.current().executeScript( " iefix();");
    }
    
    public void changeTabExportar() {
    	setTipoaexportar("");
    }
    
    public void exportAction(ActionEvent evt ){
    	try {
        	String a = (String)evt.getComponent().getAttributes().get("start");
        	String b = (String)evt.getComponent().getAttributes().get("end");
        	Date fechaInicial=null;
        	Date fechaFinal=null;
        	synchronized (receiveFormat) {
            	fechaInicial = receiveFormat.parse(a);
            	fechaFinal= receiveFormat.parse(b);
    		}
        	
        	if (getTipoaexportar()!=null) { 
	        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Exportando a " + getTipoaexportar() , null));
	        	List<TransfElectronicaAltoValor>  resultado = null; 
	        	if (IndicoUtils.getInstance().notNull(getConcepto()) && !getConcepto().equals(TODOS_LITERAL)) {
	        		resultado =  ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getDetalleExportarSG(fechaInicial, fechaFinal,getConcepto());
	        	} else {
	            	resultado =  ServiceFacades.getInstance().getTransfElectronicaAltoValorService().getDetalleExportar(fechaInicial, fechaFinal);
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
    
    private void createXMLFile (List<TransfElectronicaAltoValor> valores) {
    	HSSFWorkbook wb = null;
    	OutputStream output  = null;
    	try {
    		wb = new HSSFWorkbook();
    		Sheet sheet = wb.createSheet("Datos Estadísticos");
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("Fecha");
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Id. Grupo");
			cellHeader = rowHeader.createCell(2);
			cellHeader.setCellValue("Grupo");
			cellHeader = rowHeader.createCell(3);
			cellHeader.setCellValue("Id. Subgrupo");
			cellHeader = rowHeader.createCell(4);
			cellHeader.setCellValue("Subgrupo");
			cellHeader = rowHeader.createCell(5);
			cellHeader.setCellValue("No Movimientos");
			cellHeader = rowHeader.createCell(6);
			cellHeader.setCellValue("Valores Movimientos ($COP)");
			int rownum = 1;
			CellStyle cellStyle = wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
			double totalCantidad = 0d;
			double totalValor = 0d;
    		for (TransfElectronicaAltoValor te : valores) {
    			Row row = sheet.createRow(rownum++);
    			Cell cell = row.createCell(0);
    			cell.setCellStyle(cellStyle);
    			cell.setCellValue(te.getTransElectAltoValorPK().getFechaValorPK());
    			cell = row.createCell(1);
    			cell.setCellValue(te.getTransElectAltoValorPK().getIdGrupoTipoTransaccionPK());
    			cell = row.createCell(2);
    			cell.setCellValue(te.getDescGrupoTipoTransaccion());
    			cell = row.createCell(3);
    			cell.setCellValue(te.getTransElectAltoValorPK().getIdSubGrupoTipoTransaccionPK());
    			cell = row.createCell(4);
    			cell.setCellValue(te.getDescSubGrupoTipoTransaccion());
    			cell = row.createCell(5);
    			totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDouble(te.getNumTotalMovimientos());
    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(te.getCv_ValorTotalMovimientos());
    			cell.setCellValue(IndicoUtils.getInstance().assertDouble(te.getNumTotalMovimientos()));
    			cell = row.createCell(6);
    			cell.setCellValue(IndicoUtils.getInstance().assertDoubleMM(te.getCv_ValorTotalMovimientos()));
    		}
			Row row = sheet.createRow(rownum++);
			Cell cell = row.createCell(4);
			cell.setCellValue("Total");
			cell = row.createCell(5);
			cell.setCellValue(totalCantidad);
			cell = row.createCell(6);
			cell.setCellValue(totalValor);
    		
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wb.write(outputStream);

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/xls");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_tranf.xls");
			output = response.getOutputStream();
			output.write(outputStream.toByteArray());
			fc.responseComplete();
    	} catch(IOException ex) {
			logger.info(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_LITERAL, null));
    	} finally {
            try {
				if (wb!=null) wb.close();
				if (output!=null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_LITERAL, null));
			}
    	}
    }
    
    /* Csv File to download on select a option without p:fileuoload only over a method*/
    private void createCSVFile(List<TransfElectronicaAltoValor> valores) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/comma-separated-values");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_tranf.csv");
			output = response.getOutputStream();
        	String line = MessageFormat.format(FORMAT_LITERAL_6,"Fecha","Id. Grupo","Grupo","Id. Subgrupo","Subgrupo","No Movimientos","Valores Movimientos ($COP)");
        	output.write((line+"\r\n").getBytes());
			double totalCantidad = 0d;
			double totalValor = 0d;
			if(valores!=null && !valores.isEmpty()) {
	        	for (TransfElectronicaAltoValor tv : valores) {
	        		
		        	line = MessageFormat.format(FORMAT_LITERAL_6,	        	
		        		formatter2.format(tv.getTransElectAltoValorPK().getFechaValorPK()),
		    			tv.getTransElectAltoValorPK().getIdGrupoTipoTransaccionPK(),
		    			tv.getDescGrupoTipoTransaccion(),
		    			tv.getTransElectAltoValorPK().getIdSubGrupoTipoTransaccionPK(),
		    			tv.getDescSubGrupoTipoTransaccion(),
		    			//tv.getNumTotalMovimientos().intValue(),
		    			String.format("%.0f", tv.getNumTotalMovimientos()),
		    			//String.valueOf(IndicoUtils.getInstance().assertDoubleMM(tv.getCv_ValorTotalMovimientos()))
		    			String.format("%.2f", tv.getCv_ValorTotalMovimientos())
		    		);
		        	output.write((line + "\r\n").getBytes());
	    			totalCantidad = totalCantidad + IndicoUtils.getInstance().assertDouble(tv.getNumTotalMovimientos());
	    			totalValor    = totalValor + IndicoUtils.getInstance().assertDoubleMM(tv.getCv_ValorTotalMovimientos());
		        }//for
			}//if
        	line = MessageFormat.format(FORMAT_LITERAL_6,"","","","","Total",String.format("%.0f", totalCantidad),String.format("%.2f", totalValor));
        	output.write((line + "\r\n").getBytes());
	        fc.responseComplete();
        } catch (IOException e) {
			logger.info(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_LITERAL, null));
		} finally {
			try {
				if (output!=null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_LITERAL, null));
			}
		}
        return;
    }
    
    private void createSDMXFile(List<TransfElectronicaAltoValor> transfElectronicaAltoValor) {
    	OutputStream output  = null;
        try {
        	//o sea aqui el archivo ya esta creado.
        	FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("text/xml");
            response.setCharacterEncoding(UTF8_LITERAL);
            response.setHeader(CONTENT_DISPOSITION_LITERAL, "attachment; filename=indico_tranf_sdmx.xml");
			output = response.getOutputStream();
			String resultado =HEAD + HEADER;
			for (TransfElectronicaAltoValor valor : transfElectronicaAltoValor ) {
				resultado = resultado .concat( MessageFormat.format(
						"<generic:Obs>	\r\n" +
								"<generic:Time name=\"Fecha\" >{0}</generic:Time> \r\n" +
								"<generic:ObsValue name=\"Id_Grupo\"  value=\"{1}\" /> \r\n" +
								"<generic:ObsValue name=\"Grupo\" value=\"{2}\" /> \r\n" +
								"<generic:ObsValue name=\"Id_Subgrupo\" value=\"{3}\" /> \r\n" +
								"<generic:ObsValue name=\"Subgrupo\" value=\"{4}\" /> \r\n" +
								"<generic:ObsValue name=\"No Movimientos\" value=\"{5}\" /> \r\n" +
								"<generic:ObsValue name=\"Valores Movimientos ($COP)\" value=\"{6}\" /> \r\n" +
								"</generic:Obs>	\r\n", 
						formatter3.format(valor.getTransElectAltoValorPK().getFechaValorPK()),
						valor.getTransElectAltoValorPK().getIdGrupoTipoTransaccionPK(),
		    			valor.getDescGrupoTipoTransaccion(),
		    			valor.getTransElectAltoValorPK().getIdSubGrupoTipoTransaccionPK(),
		    			valor.getDescSubGrupoTipoTransaccion(),
						valor.getNumTotalMovimientos(), 
						String.format("%.2f", valor.getCv_ValorTotalMovimientos())));
						resultado = resultado.concat("\r\n");
			}
			resultado = resultado + FOOTER;
			output.write(resultado.getBytes());
			fc.responseComplete();
        } catch (IOException e) {
			logger.info(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_LITERAL, null));
		} finally {
			try {
				if (output!=null) {
					output.flush();
					output.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,ERROR_LITERAL, null));
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

	public String getTipoPeriodoSelected() {
		return tipoPeriodoSelected;
	}

	public void setTipoPeriodoSelected(String tipoPeriodoSelected) {
		this.tipoPeriodoSelected = tipoPeriodoSelected;
	}

	public List<CampoSelect> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<CampoSelect> conceptos) {
		this.conceptos = conceptos;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Map<String, String> getConceptosSele() {
		return conceptosSele;
	}

	public void setConceptosSele(Map<String, String> conceptosSele) {
		this.conceptosSele = conceptosSele;
	}

	public String getTipoaexportar() {
		return tipoaexportar;
	}

	public void setTipoaexportar(String tipoaexportar) {
		this.tipoaexportar = tipoaexportar;
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
