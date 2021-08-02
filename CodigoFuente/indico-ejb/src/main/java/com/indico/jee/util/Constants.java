package com.indico.jee.util;

import java.math.BigDecimal;

public interface Constants {
	
	public static final String DEFAULT_PIB_LITERAL = "0.00001";
	public static final String DESDE_LITERAL = "desde";
	public static final String HASTA_LITERAL = "hasta";
	public static final String HORA_LITERAL = "hora";
	public static final String UNCHECKED_LITERAL = "unchecked";
	public static final int NUMERO_DECIMALES = 2;
	public static final int NUMERO_DECIMALES_ROTACION = 12;
	public static final int NUMERO_DECIMALES_CANJE = 5;
	
	public static final String MEDIO_LITERAL= "medio" ;
	public static final String FECHA_INICIO_LITERAL = "fechaInicio";
	public static final String FECHA_FIN_LITERAL = "fechaFin";
	public static final String ID_GRUPO_LITERAL = "idGrupo";
	public static final String ID_SUBGRUPO_LITERAL = "idSubGrupo";
	
	public static final String DAY_PART_LITERAL = "-01";
	public static final String FORMAT_2D_LITERAL = "%02d";
	public static final String DATE_PART_LITERAL = "-01-01";
	public static final String MINUS_LITERAL = "-";
	
	public static final String COMPENSACION_DE_LITERAL = "Compensación de ";
	public static final String COMPENSACION_LITERAL = "compensacion";
	public static final String CHEQUES_LITERAL = "cheques";
	
	public static final String HEAD =   "<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n" +
										"<GenericData 	\r\n" +
										"	xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"	\r\n" +
										"	xmlns:common=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/common\" 		\r\n" +
										"	xmlns:generic=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/generic\" 	\r\n" +
										"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" 						\r\n" +
										"	xsi:schemaLocation=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/messageSDMXMessage.xsd\">	\r\n";
										
	public static final String BODY =  	    "	<mes:Obs> \r\n" +
											"		<mes:ObsKey> \r\n" +
											"			<mes:Value id=\"DIM_PAIS\" value=\"CO\"/> \r\n" +
											"		</mes:ObsKey> \r\n" +
											"		<mes:ObsValue value=\"0\"/> \r\n" +
											"		<mes:Attributes> \r\n" +
											"			<mes:Value id=\"VAR_FECHA_O_PERIODO\" value=\"{0}\"/> \r\n" +
											"			<mes:Value id=\"SESION\" 	value=\"{1}\" /> \r\n" +
											"			<mes:Value id=\"CIUDAD\" 	value=\"{2}\" /> \r\n" +
											"			<mes:Value id=\"MEDIO\" 	value=\"{3}\" /> \r\n" + 
											"			<mes:Value id=\"NUM\" 		value=\"{4}\" /> \r\n" +
											"			<mes:Value id=\"VALOR\" 	value=\"{5}\" /> \r\n" +
											"		</mes:Attributes> \r\n" +
											"	</mes:Obs> \r\n";
	
	public static final String FOOTER =     "			</generic:Series>	\r\n" +
											"		</generic:Group>	\r\n" +
											"	</DataSet>	\r\n" +
											"</GenericData>	\r\n";
	
	public static final String TODAS_LITERAL = "todas";
	public static final String CONTENT_DISPOSITION_LITERAL = "Content-Disposition";
	public static final String FORMAT_LITERAL_5 ="{0};{1};{2};{3};{4};{5}";
	public static final String UTF8_LITERAL = "Utf-8";
	public static final String END_LINE_LITERAL = "\r\n";
	public static final String FORMAT_LITERAL_4 ="{0};{1};{2};{3};{4}";
	public static final String FORMAT_LITERAL_3 ="{0};{1};{2};{3}";
	public static final String FORMAT_LITERAL_2 ="{0};{1};{2}";
	public static final String FORMAT_LITERAL_1 ="{0};{1}";
	public static final String ERROR_LITERAL = "Ha ocurrido un error, comuniquese con el administrador del sistema";
	public static final String FORMAT_LITERAL_6 ="{0};{1};{2};{3};{4};{5};{6}";
	public static final String TODOS_LITERAL = "todos";
	
	public static final String NUMBER_LITERAL = "150000000000";
	public static final String MINUS_NUMBER_LITERAL = "-150000000000";
	public static final String TITLE_LITERAL = "Title";
	public static final String TICKS_LITERAL ="Ticks";
	public static final String MIN_VALOR_LITERAL ="MinValor";		
	public static final String MIN_CANTIDAD_LITERAL ="MinCantidad";	
	public static final String MAX_VALOR_LITERAL =	"MaxValor";		
	public static final String MAX_CANTIDAD_LITERAL =	"MaxCantidad";		
	public static final String SERIE_VALORES_LITERAL =	"SerieValores";	
	public static final String SERIE_VALORES_LITERAL2 =	"SerieValores2";	
	public static final String SERIE_CANTIDAD_LITERAL =	"SerieCantidad";	
	public static final String SERIE_CANTIDAD_LITERAL2 =	"SerieCantidad2";
	public static final String MINX_LITERAL =	"MinX";			
	public static final String MAXX_LITERAL =	"MaxX";			
	public static final String STARTX_LITERAL =	"startX";	
	public static final String ENDX_LITERAL =	"endX";	
	public static final String SERIE_CANTIDAD_PRCT_LITERAL =	"SerieCantidadPorcentaje";	
	public static final String SERIE_VALOR_PRCT_LITERAL =	"SerieValoresPorcentaje";	
	public static final String UNDEFINED_LITERAL = "undefined";
	public static final String CIUDAD_LITERAL = "ciudad";

	public static final String CONCEPTO_LITERAL="concepto";
	
	public static final String ENERO_LITERAL = "Enero";
	public static final String UNO_LITERAL = "01";
	public static final String FEBRERO_LITERAL ="Febrero";
	public static final String DOS_LITERAL = "02";
	public static final String MARZO_LITERAL ="Marzo";
	public static final String TRES_LITERAL = "03";
	public static final String ABRIL_LITERAL ="Abril";	
	public static final String CUATRO_LITERAL = "04";
	public static final String MAYO_LITERAL ="Mayo";	
	public static final String CINCO_LITERAL = "05";
	public static final String JUNIO_LITERAL ="Junio";	
	public static final String SEIS_LITERAL = "06";
	public static final String JULIO_LITERAL ="Julio";	
	public static final String SIETE_LITERAL = "07";
	public static final String AGOSTO_LITERAL ="Agosto";	
	public static final String OCHO_LITERAL = "08";
	public static final String SEPTIEMBRE_LITERAL ="Septiembre";	
	public static final String NUEVE_LITERAL = "09";
	public static final String OCTUBRE_LITERAL ="Octubre";		
	public static final String DIEZ_LITERAL = "10";
	public static final String NOVIEMBRE_LITERAL ="Noviembre";	
	public static final String ONCE_LITERAL = "11";
	public static final String DICIEMBRE_LITERAL ="Diciembre";	
	public static final String DOCE_LITERAL = "12";
	
	public static final String UNO_ROMANO = "I";	
	public static final String DOS_ROMANO = "II";	
	public static final String TRES_ROMANO = "III";	
	public static final String CUATRO_ROMANO = "IV";
	
	public static final BigDecimal 	DIVISORMM = new BigDecimal(1000000000);
	public static final BigDecimal 	DIVISORM =  new BigDecimal(1000000000);
	public static final BigDecimal 	DIVISORC = new BigDecimal(50);
	public static final String 		COMODINSQL = "%%";
	public static final BigDecimal 	MAXGRAFICA = new BigDecimal(0.5);
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
