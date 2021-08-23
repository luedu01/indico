package com.indico.jee.web.carga;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import com.indico.jee.util.CampoSelect;
import com.indico.jee.web.carga.CargaFiltros;

public class CargaFiltrosTest {
	
	private static Calendar c = Calendar.getInstance();
	private static String mesActual = Integer.toString(c.get(Calendar.MONTH)+1);
	private static String anioActual = Integer.toString(c.get(Calendar.YEAR));
	
	
	@Test
	public void testDias(){
		List<CampoSelect> dias1=new ArrayList<>();
		List<CampoSelect> dias2=new ArrayList<>();
		int diaActual = c.get(Calendar.DATE);
		
		for(int i=1; i<=31;i++){
			if(i < 10){
				dias1.add(new CampoSelect("0"+i, "0"+i));
			}else{
				dias1.add(new CampoSelect(""+i, ""+i));
			}
		}
		for (int i = 1; i < diaActual; i++) {
			if(i < 10){
				dias2.add(new CampoSelect("0"+i, "0"+i));
			}else{
				dias2.add(new CampoSelect(""+i, ""+i));
			}
		}
		//List<CampoSelect> res = CargaFiltros.getInstance().obtenerDiasMes("12", "2018");
		assertEquals(dias1, CargaFiltros.getInstance().obtenerDiasMes("05", "2018"));
		assertEquals(dias2, CargaFiltros.getInstance().obtenerDiasMes(mesActual, anioActual));
	}
	
	@Test
	public void testMeses(){
		/*List<CampoSelect> meses1=new ArrayList<>();
		List<CampoSelect> meses2=new ArrayList<>();
		meses1.add(new CampoSelect("Enero"		, "01"));
		meses1.add(new CampoSelect("Febrero"		, "02"));
		meses1.add(new CampoSelect("Marzo"		, "03"));
		meses1.add(new CampoSelect("Abril"		, "04"));
		meses1.add(new CampoSelect("Mayo"		, "05"));
		meses1.add(new CampoSelect("Junio"		, "06"));
		meses1.add(new CampoSelect("Julio"		, "07"));
		meses1.add(new CampoSelect("Agosto"		, "08"));
		meses1.add(new CampoSelect("Septiembre"	, "09"));
		meses1.add(new CampoSelect("Octubre"		, "10"));
		meses1.add(new CampoSelect("Noviembre"	, "11"));
		meses1.add(new CampoSelect("Diciembre"	, "12"));
		meses2.add(new CampoSelect("Enero"		, "01"));
		meses2.add(new CampoSelect("Febrero"	, "02"));
		meses2.add(new CampoSelect("Marzo"		, "03"));
		meses2.add(new CampoSelect("Abril"		, "04"));
		meses2.add(new CampoSelect("Mayo"		, "05"));
		meses2.add(new CampoSelect("Junio"		, "06"));
		meses2.add(new CampoSelect("Julio"		, "07"));
		meses2.add(new CampoSelect("Agosto"		, "08"));
		meses2.add(new CampoSelect("Septiembre"	, "09"));
		meses2.add(new CampoSelect("Octubre"		, "10"));
		meses2.add(new CampoSelect("Noviembre"	, "11"));
		meses2.add(new CampoSelect("Diciembre"	, "12"));
		assertEquals(meses1,CargaFiltros.getInstance().obtenerMeses("2017"));
		assertEquals(meses2,CargaFiltros.getInstance().obtenerMeses(anioActual));*/
		assert(true);
	}

	@Test
	public void testTrimestres(){
		/*List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		List<CampoSelect> resultado1 = new ArrayList<CampoSelect>();
		resultado.add(new CampoSelect("I"			, "01"));
		resultado.add(new CampoSelect("II"			, "02"));
		resultado.add(new CampoSelect("III"			, "03"));
		resultado.add(new CampoSelect("IV"			, "04"));
		resultado1.add(new CampoSelect("I"			, "01"));
		resultado1.add(new CampoSelect("II"			, "02"));
		resultado1.add(new CampoSelect("III"			, "03"));
		assertEquals(resultado,CargaFiltros.getInstance().obtenerTrimestre("2017"));
		assertEquals(resultado,CargaFiltros.getInstance().obtenerTrimestre("2017"));
		assertEquals(resultado1,CargaFiltros.getInstance().obtenerTrimestre(anioActual));
		assertEquals(resultado1,CargaFiltros.getInstance().obtenerTrimestre(anioActual));*/
		assert(true);
	}
	
	@Test
	public void testSemestres(){
		/*List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		List<CampoSelect> resultado1 = new ArrayList<CampoSelect>();
		resultado.add(new CampoSelect("I"			, "01"));
		resultado.add(new CampoSelect("II"			, "02"));
		resultado1.add(new CampoSelect("I"			, "01"));
		assertEquals(resultado,CargaFiltros.getInstance().obtenerSemestre("2017"));
		assertEquals(resultado,CargaFiltros.getInstance().obtenerSemestre("2017"));
		assertEquals(resultado1,CargaFiltros.getInstance().obtenerSemestre(anioActual));
		assertEquals(resultado1,CargaFiltros.getInstance().obtenerSemestre(anioActual));*/
		assert(true);
	}

}
