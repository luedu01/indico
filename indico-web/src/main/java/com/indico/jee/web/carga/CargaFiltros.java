package com.indico.jee.web.carga;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.indico.jee.util.Constants.*;

import com.indico.jee.util.CampoSelect;

public class CargaFiltros {
	
	private static CargaFiltros instance;
	

	
	public static CargaFiltros getInstance(){
		if(instance == null){
			instance = new CargaFiltros();
		}
		return instance;
	}
	
	
	public static List<CampoSelect> obtenerMeses(){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		resultado.add(new CampoSelect(ENERO_LITERAL		, UNO_LITERAL));
		resultado.add(new CampoSelect(FEBRERO_LITERAL		, DOS_LITERAL));
		resultado.add(new CampoSelect(MARZO_LITERAL		, TRES_LITERAL));
		resultado.add(new CampoSelect(ABRIL_LITERAL		, CUATRO_LITERAL));
		resultado.add(new CampoSelect(MAYO_LITERAL		, CINCO_LITERAL));
		resultado.add(new CampoSelect(JUNIO_LITERAL		, SEIS_LITERAL));
		resultado.add(new CampoSelect(JULIO_LITERAL		, SIETE_LITERAL));
		resultado.add(new CampoSelect(AGOSTO_LITERAL		, OCHO_LITERAL));
		resultado.add(new CampoSelect(SEPTIEMBRE_LITERAL	, NUEVE_LITERAL));
		resultado.add(new CampoSelect(OCTUBRE_LITERAL		, DIEZ_LITERAL));
		resultado.add(new CampoSelect(NOVIEMBRE_LITERAL	, ONCE_LITERAL));
		resultado.add(new CampoSelect(DICIEMBRE_LITERAL	, DOCE_LITERAL));
		return resultado;
	}
	
	public List<CampoSelect> obtenerMeses(String anio){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		if (anio==null) return resultado;	

		Calendar now = Calendar.getInstance();
		Integer mesActual = now.get(Calendar.MONTH)+1;
		Integer anioActual = now.get(Calendar.YEAR);
		if (anioActual.equals(Integer.valueOf(anio)) ) {
			if(mesActual>1)  resultado.add(new CampoSelect(ENERO_LITERAL		, UNO_LITERAL));
			if(mesActual>2)  resultado.add(new CampoSelect(FEBRERO_LITERAL	, DOS_LITERAL));
			if(mesActual>3)  resultado.add(new CampoSelect(MARZO_LITERAL		, TRES_LITERAL));
			if(mesActual>4)  resultado.add(new CampoSelect(ABRIL_LITERAL		, CUATRO_LITERAL));
			if(mesActual>5)  resultado.add(new CampoSelect(MAYO_LITERAL		, CINCO_LITERAL));
			if(mesActual>6)  resultado.add(new CampoSelect(JUNIO_LITERAL		, SEIS_LITERAL));
			if(mesActual>7)  resultado.add(new CampoSelect(JULIO_LITERAL		, SIETE_LITERAL));
			if(mesActual>8)  resultado.add(new CampoSelect(AGOSTO_LITERAL	, OCHO_LITERAL));
			if(mesActual>9)  resultado.add(new CampoSelect(SEPTIEMBRE_LITERAL, NUEVE_LITERAL));
			if(mesActual>10) resultado.add(new CampoSelect(OCTUBRE_LITERAL	, DIEZ_LITERAL));
			if(mesActual>11) resultado.add(new CampoSelect(NOVIEMBRE_LITERAL	, ONCE_LITERAL));
			if(mesActual>12) resultado.add(new CampoSelect(DICIEMBRE_LITERAL	, DOCE_LITERAL));
		} else {
			resultado.add(new CampoSelect(ENERO_LITERAL		, UNO_LITERAL));
			resultado.add(new CampoSelect(FEBRERO_LITERAL		, DOS_LITERAL));
			resultado.add(new CampoSelect(MARZO_LITERAL		, TRES_LITERAL));
			resultado.add(new CampoSelect(ABRIL_LITERAL		, CUATRO_LITERAL));
			resultado.add(new CampoSelect(MAYO_LITERAL		, CINCO_LITERAL));
			resultado.add(new CampoSelect(JUNIO_LITERAL		, SEIS_LITERAL));
			resultado.add(new CampoSelect(JULIO_LITERAL		, SIETE_LITERAL));
			resultado.add(new CampoSelect(AGOSTO_LITERAL		, OCHO_LITERAL));
			resultado.add(new CampoSelect(SEPTIEMBRE_LITERAL	, NUEVE_LITERAL));
			resultado.add(new CampoSelect(OCTUBRE_LITERAL		, DIEZ_LITERAL));
			resultado.add(new CampoSelect(NOVIEMBRE_LITERAL	, ONCE_LITERAL));
			resultado.add(new CampoSelect(DICIEMBRE_LITERAL	, DOCE_LITERAL));
		}
		return resultado;
	}
	
	public List<CampoSelect> obtenerMesesAnioMesDia(String anio){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		if (anio==null) return resultado;	

		Calendar now = Calendar.getInstance();
		Integer mesActual = now.get(Calendar.MONTH)+1;
		Integer anioActual = now.get(Calendar.YEAR);
		if (anioActual.equals(Integer.valueOf(anio)) ) {
			if(mesActual>=1)  resultado.add(new CampoSelect(ENERO_LITERAL		, UNO_LITERAL));
			if(mesActual>=2)  resultado.add(new CampoSelect(FEBRERO_LITERAL	, DOS_LITERAL));
			if(mesActual>=3)  resultado.add(new CampoSelect(MARZO_LITERAL		, TRES_LITERAL));
			if(mesActual>=4)  resultado.add(new CampoSelect(ABRIL_LITERAL		, CUATRO_LITERAL));
			if(mesActual>=5)  resultado.add(new CampoSelect(MAYO_LITERAL		, CINCO_LITERAL));
			if(mesActual>=6)  resultado.add(new CampoSelect(JUNIO_LITERAL		, SEIS_LITERAL));
			if(mesActual>=7)  resultado.add(new CampoSelect(JULIO_LITERAL		, SIETE_LITERAL));
			if(mesActual>=8)  resultado.add(new CampoSelect(AGOSTO_LITERAL	, OCHO_LITERAL));
			if(mesActual>=9)  resultado.add(new CampoSelect(SEPTIEMBRE_LITERAL, NUEVE_LITERAL));
			if(mesActual>=10) resultado.add(new CampoSelect(OCTUBRE_LITERAL	, DIEZ_LITERAL));
			if(mesActual>=11) resultado.add(new CampoSelect(NOVIEMBRE_LITERAL	, ONCE_LITERAL));
			if(mesActual>=12) resultado.add(new CampoSelect(DICIEMBRE_LITERAL	, DOCE_LITERAL));
		} else {
			resultado.add(new CampoSelect(ENERO_LITERAL		, UNO_LITERAL));
			resultado.add(new CampoSelect(FEBRERO_LITERAL		, DOS_LITERAL));
			resultado.add(new CampoSelect(MARZO_LITERAL		, TRES_LITERAL));
			resultado.add(new CampoSelect(ABRIL_LITERAL		, CUATRO_LITERAL));
			resultado.add(new CampoSelect(MAYO_LITERAL		, CINCO_LITERAL));
			resultado.add(new CampoSelect(JUNIO_LITERAL		, SEIS_LITERAL));
			resultado.add(new CampoSelect(JULIO_LITERAL		, SIETE_LITERAL));
			resultado.add(new CampoSelect(AGOSTO_LITERAL		, OCHO_LITERAL));
			resultado.add(new CampoSelect(SEPTIEMBRE_LITERAL	, NUEVE_LITERAL));
			resultado.add(new CampoSelect(OCTUBRE_LITERAL		, DIEZ_LITERAL));
			resultado.add(new CampoSelect(NOVIEMBRE_LITERAL	, ONCE_LITERAL));
			resultado.add(new CampoSelect(DICIEMBRE_LITERAL	, DOCE_LITERAL));
		}
		return resultado;
	}

	
	public List<CampoSelect> obtenerTrimestre(){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		resultado.add(new CampoSelect(UNO_ROMANO			, UNO_LITERAL));
		resultado.add(new CampoSelect(DOS_ROMANO			, DOS_LITERAL));
		resultado.add(new CampoSelect(TRES_ROMANO			, TRES_LITERAL));
		resultado.add(new CampoSelect(CUATRO_ROMANO			, CUATRO_LITERAL));
		return resultado;
	}
	
	public List<CampoSelect> obtenerTrimestre(String anio){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		if (anio!=null) {
			Calendar now = Calendar.getInstance();
			Integer mesA = now.get(Calendar.MONTH)+1;
			Integer anioActual = now.get(Calendar.YEAR);
			if (!anioActual.equals(Integer.valueOf(anio))) {
				resultado.add(new CampoSelect(UNO_ROMANO			, UNO_LITERAL));
				resultado.add(new CampoSelect(DOS_ROMANO			, DOS_LITERAL));
				resultado.add(new CampoSelect(TRES_ROMANO			, TRES_LITERAL));
				resultado.add(new CampoSelect(CUATRO_ROMANO			, CUATRO_LITERAL));
			} else {
				if (mesA>3) resultado.add(new CampoSelect(UNO_ROMANO		, UNO_LITERAL));
				if (mesA>6) resultado.add(new CampoSelect(DOS_ROMANO		, DOS_LITERAL));
				if (mesA>9) resultado.add(new CampoSelect(TRES_ROMANO	, TRES_LITERAL));
			}
		}
		return resultado;
	}	

		
	
	public List<CampoSelect> obtenerSemestre(){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		resultado.add(new CampoSelect(UNO_ROMANO			, UNO_LITERAL));
		resultado.add(new CampoSelect(DOS_ROMANO			, DOS_LITERAL));
		return resultado;
	}
	
	public List<CampoSelect> obtenerSemestre(String anio){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		if (anio!=null) {
			Calendar now = Calendar.getInstance();
			Integer mesA = now.get(Calendar.MONTH)+1;
			Integer anioActual = now.get(Calendar.YEAR);
			
			if (!anioActual.equals(Integer.valueOf(anio))) {
				resultado.add(new CampoSelect(UNO_ROMANO			, UNO_LITERAL));
				resultado.add(new CampoSelect(DOS_ROMANO			, DOS_LITERAL));
			} else {
				if (mesA>6) resultado.add(new CampoSelect(UNO_ROMANO			, UNO_LITERAL));
			}
		}
		return resultado;
	}
	
	
	public List<CampoSelect> obtenerDiasMes(String mes,String anioEnt){
		List<CampoSelect> resultado = new ArrayList<CampoSelect>();
		if (mes!=null && anioEnt!=null) {
			Calendar now  =Calendar.getInstance();
			Calendar calSelected = Calendar.getInstance();
			calSelected.set(Calendar.HOUR, 0);
			calSelected.set(Calendar.MINUTE, 0);
			calSelected.set(Calendar.SECOND, 0);
			calSelected.set(Calendar.MILLISECOND, 0);
			calSelected.set(Calendar.MONTH,Integer.valueOf(mes)-1);
			calSelected.set(Calendar.YEAR,Integer.valueOf(anioEnt));
			int minimo = calSelected.getActualMinimum(Calendar.DAY_OF_MONTH);
			int maximo = calSelected.getActualMaximum(Calendar.DAY_OF_MONTH);
			for ( int dia = minimo ; dia<=maximo ; dia++) {
				resultado.add(new CampoSelect(String.format(FORMAT_2D_LITERAL, dia) , String.format(FORMAT_2D_LITERAL, dia)));
				if ( 	(now.get(Calendar.YEAR)==calSelected.get(Calendar.YEAR)) && 
						(now.get(Calendar.MONTH)==calSelected.get(Calendar.MONTH)) &&
						(now.get(Calendar.DAY_OF_MONTH)-1<=dia)
					) {
					break;
				}
			}
		}
		return resultado;
	}
}
