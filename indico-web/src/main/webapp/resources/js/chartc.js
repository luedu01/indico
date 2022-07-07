/**
 *  GENERAL FUNCTIONS
 **/

var _ENTERO		=1;
var _PORCENTAJE	=2;
var _NUMERICO	=3;
var logerrors 	=true;
var _DIARIO		=1;
var _MENSUAL	=2;
var _TRIMESTRAL	=3;
var _SEMESTRAL	=4;
var _ANUAL		=5;

function restartExportar(selectname){
	 setTimeout(function () {
			PF(''+selectname+'').selectValue("");
	    }, 1000);
}

function formatdate (val){
        var days = val.getDate();
        var month = val.getMonth() + 1;
        var year = val.getFullYear();
        return days + "/" + month + "/" + year;
}

function completarFechaSelected(fecha,sticks,periodoactual,periodoanterior) {

	if (fecha == null) return null;
	
	anio = fecha.split('-')[0];
	if (anio == null) return null;
	mes = fecha.split('-')[1];
	dia = fecha.split('-')[2];

	if (mes === undefined) { mes = "01"; }
	if (dia === undefined) { dia = "01"; }
	 

	fecha = new Date(anio, mes-1, dia);
	if (isNaN(fecha) == true) {
		fecha = sticks[sticks.length-1];
  		fecha=new Date(fecha.split('-')[0],fecha.split('-')[1]-1,fecha.split('-')[2]);
	} else {
		anterior = sticks[sticks.length-1];
		anterior=new Date(anterior.split('-')[0],(anterior.split('-')[1]-1),anterior.split('-')[2]);
		j=0;
		for (i=sticks.length-1 ; i>=0 ; i-- ) {
			currentValue = sticks[i]; 
			actualdate = new Date(currentValue.split('-')[0],(currentValue.split('-')[1]-1),currentValue.split('-')[2]);
			if (actualdate<=fecha) {
				break;
			}
			anterior=actualdate;
		}
		periodoactual=parseInt(periodoactual);
		periodoanterior=parseInt(periodoanterior);
		if(periodoactual>periodoanterior) {
			if (fecha>actualdate) {
				fecha = anterior;
			} else {
				fecha = actualdate;
			}
		}	else {
			if (fecha<actualdate) {
				fecha = anterior;
			} else {
				fecha = actualdate;
			}
		}	

	}	
	return fecha;
}



function savedOldDatesStoStorage(almacen, componente1, anterior) {
	var vfecSelected;
	anterior = parseInt(anterior);
	switch (anterior) {
		case 1:
			vfecSelected = getDateStartFromDiario(componente1);
			break;
		case 2:
			vfecSelected = getDateStartFromMensual(componente1);
			break;
		case 3:
			vfecSelected = getDateStartFromTrimestral(componente1);
			break;
		case 4:
			vfecSelected = getDateStartFromSemestral(componente1);
			break;
		case 5:
			vfecSelected = getDateStartFromAnual(componente1);
			break;
	}
	localStorage.setItem(almacen + "_fecSelected", vfecSelected);
}





function getDateStartFromDiario(compDiario) {
	var anio = $("#"+compDiario+"_sel_anio").val();
	var mes = $("#"+compDiario+"_sel_mes").val();
	var dia = $("#"+compDiario+"_sel_dia").val();
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateEndFromDiario(compDiario) {
	var anio = $("#"+compDiario+"_sel_anio").val();
	var mes = $("#"+compDiario+"_sel_mes").val();
	var dia = $("#"+compDiario+"_sel_dia").val();
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateStartFromMensual(componenteMensual) {
	var anio = $("#"+componenteMensual+"_sel_anio").val();
	var mes = $("#"+componenteMensual+"_sel_mes").val();
	var dia = "01";
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateEndFromMensual(componenteMensual) {
	var anio = $("#"+componenteMensual+"_sel_anio").val();
	var mes = $("#"+componenteMensual+"_sel_mes").val();
	var dia = new Date(anio, (parseInt(mes)) , 0).getDate();
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateStartFromTrimestral(componenteTrimestral) {
	var anio = $("#"+componenteTrimestral+"_sel_anio").val();
	var mestrimestre = parseInt($("#"+componenteTrimestral+"_sel_trimestre").val());
	var dia = "01";
	return anio + "-" + ("0" + (mestrimestre)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateEndFromTrimestral(componenteTrimestral) {
	var anio = $("#"+componenteTrimestral+"_sel_anio").val();
	var mestrimestre = parseInt($("#"+componenteTrimestral+"_sel_trimestre").val())+2;
	var dia = new Date(anio, mestrimestre , 0).getDate();
	return anio + "-" + ("0" + (mestrimestre)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateStartFromSemestral(componenteSemestral) {
	var anio = $("#"+componenteSemestral+"_sel_anio").val();
	var messemestre = parseInt($("#"+componenteSemestral+"_sel_semestre").val());
	var dia = "01";
	return anio + "-" + ("0" + (messemestre)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateEndFromSemestral(componenteSemestral) {
	var anio = $("#"+componenteSemestral+"_sel_anio").val();
	var messemestre = parseInt($("#"+componenteSemestral+"_sel_semestre").val())+5;
	var dia = new Date(anio, messemestre , 0).getDate();
	return anio + "-" + ("0" + (messemestre)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateStartFromAnual(componenteAnual) {
	var anio = $("#"+componenteAnual+"_sel_anio").val();
	var mes = "01";
	var dia =  "01";
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

function getDateEndFromAnual(componenteAnual) {
	var anio = $("#"+componenteAnual+"_sel_anio").val();
	var mes = "12";
	var dia = "31";
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" +("0" + (dia)).slice(-2);
}

/**
 *  Nuevos componentes basados en el tiempo
 **/

function startvaluescomponentstimes(valchanged1,compdiario1,data,tipo) {
	var selected;
	if (valchanged1!=null) {
		selected = valchanged1;
	} else {
		var fechasea;
		switch (tipo) {
			case _DIARIO:
				fechasea = getDateStartFromDiario(compdiario1);
				break;
			case _MENSUAL:
				fechasea = getDateStartFromMensual(compdiario1);
				break;
			case _TRIMESTRAL:
				fechasea = getDateStartFromTrimestral(compdiario1);
				break;
			case _SEMESTRAL:
				fechasea = getDateStartFromSemestral(compdiario1);
				break;
			case _ANUAL:
				fechasea = getDateStartFromAnual(compdiario1);
				break;
		}
		var fechaselecteda;
		if (fechasea!=null) {
			if (fechasea.length==10) {
				var fechaAux = fechasea.split("-");
				if (fechaAux.length==3){
					fechaselecteda = new Date(fechaAux[0],parseInt(fechaAux[1])-1,fechaAux[2])
				}
			}
		}
		selected = data["endX"];
		var fechaA = data["endX"].split("-");
		if (fechaA.length==1) {
			fechaA[1]="01";
			fechaA[2]="01";
		}
		selected = new Date(fechaA[0],parseInt(fechaA[1])-1,fechaA[2])
		if (fechaselecteda instanceof Date ) {
			selected = new Date(fechaA[0],parseInt(fechaA[1])-1,fechaA[2]);
			if (selected>fechaselecteda) { selected=fechaselecteda ; }
		} else selected = new Date(fechaA[0],parseInt(fechaA[1])-1,fechaA[2]);
	}
	var selanio1    	= '#'+compdiario1+"_sel_anio";
	var selmes1 		= '#'+compdiario1+"_sel_mes";
	var seldia1 		= '#'+compdiario1+"_sel_dia";
	var seltrimestre1 	= '#'+compdiario1+"_sel_trimestre";
	var selsemestre1 	= '#'+compdiario1+"_sel_semestre";

	addaniotimes(data,selanio1,selected);
	addmesestimes(data,selmes1,selected);
	adddiastimes(data,seldia1,selected);
	addtrimestretimes(data,seltrimestre1,selected);
	addsemestretimes(data,selsemestre1,selected);

}

function updatevaluescomponentstimesfromslider(slider,compdiario1,compdiario2,data,tipo) {
	var inicioX 	= new Date((slider.values.min.getTime()+(60*60*1000*5)));
	var endX		= new Date((slider.values.max.getTime()+(60*60*1000*5)));
	inicioX = getDateFromSlider(inicioX,tipo);
	endX = getDateFromSlider(endX,tipo);
	var selanio1    	= '#'+compdiario1+"_sel_anio";
	var selmes1 		= '#'+compdiario1+"_sel_mes";
	var seldia1 		= '#'+compdiario1+"_sel_dia";
	var seltrimestre1 	= '#'+compdiario1+"_sel_trimestre";
	var selsemestre1 	= '#'+compdiario1+"_sel_semestre";
	addaniotimes(data,selanio1,inicioX);
	addmesestimes(data,selmes1,inicioX);
	adddiastimes(data,seldia1,inicioX);
	addtrimestretimes(data,seltrimestre1,inicioX);
	addsemestretimes(data,selsemestre1,inicioX);

	var selanio2    	= '#'+compdiario2+"_sel_anio";
	var selmes2 		= '#'+compdiario2+"_sel_mes";
	var seldia2 		= '#'+compdiario2+"_sel_dia";
	var seltrimestre2 	= '#'+compdiario2+"_sel_trimestre";
	var selsemestre2 	= '#'+compdiario2+"_sel_semestre";
	
	addaniotimes(data,selanio2,endX);
	addmesestimes(data,selmes2,endX);
	adddiastimes(data,seldia2,endX);
	addtrimestretimes(data,seltrimestre2,endX);
	addsemestretimes(data,selsemestre2,endX);
}

function addaniotimes(data,selanio,selected) {
	if ($(selanio).length) {
		var selectanio = $(selanio);
		selectanio.empty();
		var fechaA = data["MinX"].split("-");
		var minXdistValor = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["MaxX"].split("-");
		var maxXdistValor = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		var aniostart = minXdistValor.getFullYear();
		var anioend = maxXdistValor.getFullYear();
		var option = $('<option/>').val("").text("Año").attr('disabled', 'disabled');
		option.appendTo(selectanio);
		var index = 0;
		for (var anio = aniostart; anio <= anioend; anio++) {
			index = index + 1;
			if (anio == selected.getFullYear()) {
				option = $('<option selected="selected"/>').val(anio).text(anio);
				option.attr("tabindex", index);
			} else {
				option = $('<option/>').val(anio).text(anio);
			}
			option.appendTo(selectanio);
		}
		selectaniolabel = $(selanio + "_label");
		if (selectaniolabel != undefined) {
			selectaniolabel.text(selected.getFullYear());
		}
	}
}

function addmesestimes(data,selmeses,selected) {
	if ($(selmeses).length) {
		var selectmeses = $(selmeses);
		selectmeses.empty();
		var fechaA = data["MinX"].split("-");
		var minXdistValor = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["MaxX"].split("-");
		var maxXdistValor = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		var aniostart = minXdistValor.getFullYear();
		var anioend = maxXdistValor.getFullYear();
		var mesInicial = 1;
		var mesFinal = 12;
		if (aniostart == selected.getFullYear()) {
			mesInicial = minXdistValor.getMonth() + 1;
		}
		if (anioend == selected.getFullYear()) {
			mesFinal = maxXdistValor.getMonth() + 1;
		}
		var messeleccionado = selected.getMonth() + 1;
		if ((messeleccionado < mesInicial) || (messeleccionado > mesFinal)) {
			messeleccionado = mesInicial;
		}

		//
		var option = $('<option/>').val("").text("Mes").attr('disabled', 'disabled');
		option.appendTo(selectmeses);
		mesTexto = "";

		if (mesInicial <= 1 && mesFinal >= 1) {
			option = $('<option/>').val("1").text("Enero");
			if (messeleccionado == 1) { option.attr("selected", "true"); mesTexto = "Enero"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 2 && mesFinal >= 2) {
			option = $('<option/>').val("2").text("Febrero");
			if (messeleccionado == 2) { option.attr("selected", "true"); mesTexto = "Febrero"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 3 && mesFinal >= 3) {
			option = $('<option/>').val("3").text("Marzo");
			if (messeleccionado == 3) { option.attr("selected", "true"); mesTexto = "Marzo"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 4 && mesFinal >= 4) {
			option = $('<option/>').val("4").text("Abril");
			if (messeleccionado == 4) { option.attr("selected", "true"); mesTexto = "Abril"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 5 && mesFinal >= 5) {
			option = $('<option/>').val("5").text("Mayo");
			if (messeleccionado == 5) { option.attr("selected", "true"); mesTexto = "Mayo"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 6 && mesFinal >= 6) {
			option = $('<option/>').val("6").text("Junio");
			if (messeleccionado == 6) { option.attr("selected", "true"); mesTexto = "Junio"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 7 && mesFinal >= 7) {
			option = $('<option/>').val("7").text("Julio");
			if (messeleccionado == 7) { option.attr("selected", "true"); mesTexto = "Julio"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 8 && mesFinal >= 8) {
			option = $('<option/>').val("8").text("Agosto");
			if (messeleccionado == 8) { option.attr("selected", "true"); mesTexto = "Agosto"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 9 && mesFinal >= 9) {
			option = $('<option/>').val("9").text("Septiembre");
			if (messeleccionado == 9) { option.attr("selected", "true"); mesTexto = "Septiembre"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 10 && mesFinal >= 10) {
			option = $('<option/>').val("10").text("Octubre");
			if (messeleccionado == 10) { option.attr("selected", "true"); mesTexto = "Octubre"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 11 && mesFinal >= 11) {
			option = $('<option/>').val("11").text("Noviembre");
			if (messeleccionado == 11) { option.attr("selected", "true"); mesTexto = "Noviembre"; }
			option.appendTo(selectmeses);
		}
		if (mesInicial <= 12 && mesFinal >= 12) {
			option = $('<option/>').val("12").text("Diciembre");
			if (messeleccionado == 12) { option.attr("selected", "true"); mesTexto = "Diciembre"; }
			option.appendTo(selectmeses);
		}
		selectmeseslabel = $(selmeses + "_label");
		if (selectmeseslabel != undefined) {
			selectmeseslabel.text(mesTexto);
		}

	}
}

function adddiastimes(data,seldia,selected) {
	if ($(seldia).length) {
		var selectdias = $(seldia);
		selectdias.empty();
		var fechaA = data["MinX"].split("-");
		var minXdistValor = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["MaxX"].split("-");
		var maxXdistValor = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		var diaInicial = 1;
		var diaFinal = (new Date(selected.getFullYear(), selected.getMonth() + 1, 0)).getDate();
		var diaseleccionado = selected.getDate();
		if ((diaseleccionado < diaInicial) || (diaseleccionado > diaFinal)) {
			diaseleccionado = diaInicial;
		}
		if (selected.getFullYear() == minXdistValor.getFullYear() && selected.getMonth() + 1 == minXdistValor.getMonth() + 1) {
			diaInicial = minXdistValor.getDate();
		}
		if (selected.getFullYear() == maxXdistValor.getFullYear() && selected.getMonth() + 1 == maxXdistValor.getMonth() + 1) {
			diaFinal = maxXdistValor.getDate();
		}
		var option = $('<option/>').val("").text("Día").attr('disabled', 'disabled');
		option.appendTo(selectdias);
		for (var i = diaInicial; i <= diaFinal; i++) {
			if (i == diaseleccionado) {
				option = $('<option selected="selected"/>').val(i).text(i);
				option.attr("selected", "true");
			} else {
				option = $('<option/>').val(i).text(i);
			}
			option.appendTo(selectdias);
		};
		selectdiaslabel = $(seldia + "_label");
		if (selectdiaslabel != undefined) {
			selectdiaslabel.text(diaseleccionado);
		}
	}
}

function addtrimestretimes(data,seltrimestre,selected) {
	if ($(seltrimestre).length) {
		var selecttrimestre = $(seltrimestre);
		selecttrimestre.empty();

		var fechaA = data["MinX"].split("-");
		var minXdistValor = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["MaxX"].split("-");
		var maxXdistValor = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		var periodoInicial = 1;
		var periodoFinal = 10;
		var aniostart = minXdistValor.getFullYear();
		var anioend = maxXdistValor.getFullYear();

		var trimestreseleccionado = selected.getMonth() + 1;
		if ((trimestreseleccionado < periodoInicial) || (trimestreseleccionado > periodoFinal)) {
			trimestreseleccionado = periodoInicial;
		}
		if (aniostart == selected.getFullYear()) {
			periodoInicial = minXdistValor.getMonth() + 1;
		}
		if (anioend == selected.getFullYear()) {
			periodoFinal = maxXdistValor.getMonth() + 1;
		}
		var trimestreLabel = "";
		var option = $('<option/>').val("").text("Trimestre").attr('disabled', 'disabled');
		option.appendTo(selecttrimestre);
		if (periodoInicial <= 1 && periodoFinal >= 1) {
			if (trimestreseleccionado <= 1 && trimestreseleccionado >= 1) {
				option = $('<option selected="selected"/>').val("1").text("I");
				option.attr("selected", "true")
				trimestreLabel = "I";
			} else {
				option = $('<option/>').val("1").text("I");
			}
			option.appendTo(selecttrimestre);
		}
		if (periodoInicial <= 4 && periodoFinal >= 4) {
			if (trimestreseleccionado <= 4 && trimestreseleccionado >= 4) {
				option = $('<option selected="selected"/>').val("4").text("II");
				option.attr("selected", "true")
				trimestreLabel = "II";
			} else {
				option = $('<option/>').val("4").text("II");
			}
			option.appendTo(selecttrimestre);
		}
		if (periodoInicial <= 7 && periodoFinal >= 7) {
			if (trimestreseleccionado <= 7 && trimestreseleccionado >= 7) {
				option = $('<option selected="selected"/>').val("7").text("III");
				option.attr("selected", "true")
				trimestreLabel = "III";
			} else {
				option = $('<option/>').val("7").text("III");
			}
			option.appendTo(selecttrimestre);
		}
		if (periodoInicial <= 10 && periodoFinal >= 10) {
			if (trimestreseleccionado <= 10 && trimestreseleccionado >= 10) {
				option = $('<option selected="selected"/>').val("10").text("IV");
				option.attr("selected", "true")
				trimestreLabel = "IV";
			} else {
				option = $('<option/>').val("10").text("IV");
			}
			option.appendTo(selecttrimestre);
		}
		//

		selecttriemstrelabel = $(seltrimestre + "_label");
		if (selecttriemstrelabel != undefined) {
			selecttriemstrelabel.text(trimestreLabel);
		}

	}
}

function addsemestretimes(data,selsemestre,selected) {
	if ($(selsemestre).length) {
		var selectsemestre = $(selsemestre);
		selectsemestre.empty();
		var fechaA = data["MinX"].split("-");
		var minXdistValor = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["MaxX"].split("-");
		var maxXdistValor = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		var periodoInicial = 1;
		var periodoFinal = 7;
		var aniostart = minXdistValor.getFullYear();
		var anioend = maxXdistValor.getFullYear();

		var semestreseleccionado = selected.getMonth() + 1;
		if ((semestreseleccionado < periodoInicial) || (semestreseleccionado > periodoFinal)) {
			semestreseleccionado = periodoInicial;
		}

		if (aniostart == selected.getFullYear()) {
			periodoInicial = minXdistValor.getMonth() + 1;
		}

		if (anioend == selected.getFullYear()) {
			periodoFinal = maxXdistValor.getMonth() + 1;
		}

		var option = $('<option/>').val("").text("Semestre").attr('disabled', 'disabled');
		option.appendTo(selectsemestre);
		semestreLabel = "";
		if (periodoInicial <= 1 && periodoFinal >= 1) {
			if (semestreseleccionado <= 6) {
				option = $('<option selected="selected"/>').val("1").text("I");
				semestreLabel = "I";
			} else {
				option = $('<option/>').val("1").text("I");
			}
			option.appendTo(selectsemestre);
		}
		if (periodoInicial <= 7 && periodoFinal >= 7) {
			if (semestreseleccionado >= 7) {
				option = $('<option selected="selected"/>').val("7").text("II");
				option.attr("selected", "true")
				semestreLabel = "II";
			} else {
				option = $('<option/>').val("7").text("II");
			}
			option.appendTo(selectsemestre);
		}

		selectsemestrelabel = $(selsemestre + "_label");
		if (selectsemestrelabel != undefined) {
			selectsemestrelabel.text(semestreLabel);
		}

	}
}

function changedatesselectedstimes(componente,data) {

	var anio1,mes1,dia1,trimestre1,semestre1;

	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(componente+"_sel_anio");
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;

	//Primer y ultimo valor de meses
	var selMes1 = document.getElementById(componente+"_sel_mes");
	if(selMes1 != null){
		var lastMesValue = selMes1.options[selMes1.options.length - 1].value;
	}

	////Primer y ultimo valor de días
	var selDia1 = document.getElementById(componente+"_sel_dia");
	if(selDia1 != null){
		var lastDiaValue = selDia1.options[selDia1.options.length - 1].value;
	}

	//Primer y ultimo valor de trimestre
	var selQuarter1 = document.getElementById(componente+"_sel_trimestre");
	if(selQuarter1 != null){
		var lastTriValue = selQuarter1.options[selQuarter1.options.length - 1].value;
	}

	//Primer y ultimo valor de año
	var selSem1 = document.getElementById(componente+"_sel_semestre");
	if(selSem1 != null){
		var lastSemValue = selSem1.options[selSem1.options.length - 1].value;
	}

	if ($("#"+componente+"_sel_anio").length) {
		anio1 	= $("#"+componente+"_sel_anio").val();
		if(anio1 == ""){
			anio1 	= lastValue;
		}
	}

	if ($("#"+componente+"_sel_mes").length) {
		mes1 	= $("#"+componente+"_sel_mes").val();
		if(mes1 == ""){
			mes1 = lastMesValue;
		}
		mes1 = mes1-1;

	}else{
		mes1="0";
	}

	if ($("#"+componente+"_sel_dia").length) {
		dia1 	= $("#"+componente+"_sel_dia").val();
		if(dia1 == ""){
			dia1 = lastDiaValue;
		}
	}else {
		dia1 ="01";
	}

	if ($("#"+componente+"_sel_trimestre").length) {
		var valor = $("#"+componente+"_sel_trimestre").val();
		if(valor == ""){
			valor 	= lastTriValue;
		}
		switch(valor) {
			case "1":
				mes1="0";
				break;
			case "4":
				mes1="3";
				break;
			case "7":
				mes1="6";
				break;
			case "10":
				mes1="9";
				break;
			default:
				mes1="01";
		}
		dia1 ="01";
	}

	if ($("#"+componente+"_sel_semestre").length) {
		var valor = $("#"+componente+"_sel_semestre").val();

		if(valor == ""){
			valor = lastSemValue;
		}

		switch(valor) {
			case "1":
				mes1="0";
				break;
			case "7":
				mes1="6";
				break;
		}

		dia1 ="01";
	}

	var selected = new Date(anio1,mes1,dia1);
	//
	addaniotimes(data	,"#"+componente+"_sel_anio",selected);
	addmesestimes(data	,"#"+componente+"_sel_mes",selected);
	adddiastimes(data	,"#"+componente+"_sel_dia",selected);
	addtrimestretimes(data	,"#"+componente+"_sel_trimestre",selected);
	addsemestretimes(data	,"#"+componente+"_sel_semestre",selected);
}

function updatedatapieplottimesranges(leftplot,rigthplot,componente,divvalores,divcantidad,data,tipo,errormessage,chartvaloresid,chartcantidadid) {
	var counta = 0;
	var countb = 0;
	var anio1,mes1,dia1,trimestre1,semestre1,anio1;
	var dateselected;
	switch (tipo) {
		case 1:
			dateselected=getDateStartFromDiario(componente);
			break;
		case 2:
			dateselected=getDateStartFromMensual(componente);
			break;
		case 3:
			dateselected=getDateStartFromTrimestral(componente);
			break;
		case 4:
			dateselected=getDateStartFromSemestral(componente);
			break;
		case 5:
			dateselected=getDateStartFromAnual(componente);
			break;
	}//
	
	var datosvalores = getDataFiltered(data["SerieValores"],dateselected);
	var datoscantidad = getDataCountFiltered(data["SerieCantidad"],dateselected);

	if (datosvalores[0].length && datoscantidad[0].length) {
		$("#"+chartvaloresid).show();
		$("#"+chartcantidadid).show();
		$("[id*='"+errormessage+"']").empty();
		leftplot.series[0].data = datosvalores[0];
		leftplot.data[0]		= datosvalores[0];
		var leftplot2 = leftplot;
		leftplot.replot();
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftplot2,_NUMERICO);
		document.getElementById(divvalores).innerHTML=legendavalores;
		rigthplot.data[0] 		 = datoscantidad[0];
		rigthplot.series[0].data = datoscantidad[0];
		$("#"+divcantidad).empty();
		var rigthplot2 = rigthplot;
		rigthplot.replot();
		var legendacantidades = createlegend("Cantidad de cheques", datoscantidad[1],rigthplot2,_ENTERO);
		document.getElementById(divcantidad).innerHTML=legendacantidades;
		//plot2.resetAxesScale();
	} else {
		$("#"+chartvaloresid).hide();
		$("#"+chartcantidadid).hide();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
}

function updatedCanjealCobro(leftplot,rigthplot,componente,divvalores,divcantidad,data,tipo,errormessage,chartvaloresid,chartcantidadid) {
	//debugger;
	var counta = 0;
	var countb = 0;
	var anio1,mes1,dia1,trimestre1,semestre1,anio1;
	var dateselected;
	tipo = parseInt(tipo);
	switch (tipo) {
		case 1:
			dateselected=getDateStartFromDiario(componente);
			break;
		case 2:
			dateselected=getDateStartFromMensual(componente);
			break;
		case 3:
			dateselected=getDateStartFromTrimestral(componente);
			break;
		case 4:
			dateselected=getDateStartFromSemestral(componente);
			break;
		case 5:
			dateselected=getDateStartFromAnual(componente);
			break;
	}//
	     
	var datosvalores = getDataFiltereCompensacion(data["SerieValores"],dateselected);
	var datoscantidad = getDataCountFiltered(data["SerieCantidad"],dateselected);
	
	if (datosvalores[0].length && datoscantidad[0].length) {
		$("#"+chartvaloresid).show();
		$("#"+chartcantidadid).show();
		$("[id*='"+errormessage+"']").empty();
		leftplot.series[0].data = datosvalores[0];
		leftplot.data[0]		= datosvalores[0];
		var leftplot2 = leftplot;
		leftplot.replot();
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftplot2,_NUMERICO);
		document.getElementById(divvalores).innerHTML=legendavalores;
		rigthplot.data[0] 		 = datoscantidad[0];
		rigthplot.series[0].data = datoscantidad[0];
		$("#"+divcantidad).empty();
		var rigthplot2 = rigthplot;
		rigthplot.replot();
		var legendacantidades = createlegend("Cantidad de cheques", datoscantidad[1],rigthplot2,_ENTERO);
		document.getElementById(divcantidad).innerHTML=legendacantidades;
		//plot2.resetAxesScale();
	} else {
		$("#"+chartvaloresid).hide();
		$("#"+chartcantidadid).hide();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
}

function changedatesselectedstrimestralestimes(comptrimestral1,comptrimestral2,data,slider) {
	var anio1,mes1,dia1,trimestre1,semestre1,anio1;
	var anio2,mes2,dia2,trimestre2,semestre2,anio2;

	if ($("#"+comptrimestral1+"_sel_anio").length) {
		anio1 	= $("#"+comptrimestral1+"_sel_anio").val();
	}

	if ($("#"+comptrimestral1+"_sel_trimestre").length) {
		var valor = $("#"+comptrimestral1+"_sel_trimestre").val();
		switch(valor) {
			case "1":
				mes1="0";
				break;
			case "4":
				mes1="3";
				break;
			case "7":
				mes1="6";
				break;
			case "10":
				mes1="9";
				break;
		}
	} else  {
		mes1="0"
	}

	dia1 ="01";

	if ($("#"+comptrimestral2+"_sel_anio").length) {
		anio2 	= $("#"+comptrimestral2+"_sel_anio").val();
	} else {
		anio2 = "2010";
	}

	if ($("#"+comptrimestral2+"_sel_trimestre").length) {
		var valor = $("#"+comptrimestral2+"_sel_trimestre").val();
		switch(valor) {
			case "1":
				mes2="0";
				break;
			case "4":
				mes2="3";
				break;
			case "7":
				mes2="6";
				break;
			case "10":
				mes2="9";
				break;
		}
	} else {
		mes2="0";
	}
	dia2 ="01";
	var inicio = new Date(anio1,mes1,dia1);
	var fin = new Date(anio2,mes2,dia2);

	addaniotimes(data	,"#"+comptrimestral1+"_sel_anio",inicio);
	addtrimestretimes(data	,"#"+comptrimestral1+"_sel_trimestre",inicio);
	addaniotimes(data	,"#"+comptrimestral2+"_sel_anio",fin);
	addtrimestretimes(data	,"#"+comptrimestral2+"_sel_trimestre",fin);

}

function changedatesselectedssemestralestimes(compsemestral1,compsemestral2,data,slider) {
	var anio1,mes1,dia1,trimestre1,semestre1,anio1;
	var anio2,mes2,dia2,trimestre2,semestre2,anio2;

	if ($("#"+compsemestral1+"_sel_anio").length) {
		anio1 	= $("#"+compsemestral1+"_sel_anio").val();
	}

	if ($("#"+compsemestral1+"_sel_semestre").length) {
		var valor = $("#"+compsemestral1+"_sel_semestre").val();
		switch(valor) {
			case "1":
				mes1="0";
				break;
			case "7":
				mes1="6";
				break;
		}
	} else  {
		mes1="0"
	}

	dia1 ="01";

	if ($("#"+compsemestral2+"_sel_anio").length) {
		anio2 	= $("#"+compsemestral2+"_sel_anio").val();
	} else {
		anio2 = "2010";
	}

	if ($("#"+compsemestral2+"_sel_semestre").length) {
		var valor = $("#"+compsemestral2+"_sel_semestre").val();
		switch(valor) {
			case "1":
				mes2="0";
				break;
			case "7":
				mes2="6";
				break;
		}
	} else {
		mes2="0";
	}
	dia2 ="01";

	var inicio = new Date(anio1,mes1,dia1);
	var fin = new Date(anio2,mes2,dia2);
	//
	addaniotimes(data	,"#"+compsemestral1+"_sel_anio",inicio);
	addsemestretimes(data	,"#"+compsemestral1+"_sel_semestre",inicio);
	addaniotimes(data	,"#"+compsemestral2+"_sel_anio",fin);
	addsemestretimes(data	,"#"+compsemestral2+"_sel_semestre",fin);
}

function valuesPlotChangedTimes(dataSlider,controllerPlot,targetPlot,compdiario1,compdiario2,data,tipo) {
	var minimo 	= dataSlider.values.min;
	var maximo	= dataSlider.values.max;

	var xStart 	=(controllerPlot.axes.xaxis.u2p(minimo)).toFixed(2);
	var xEnd	=(controllerPlot.axes.xaxis.u2p(maximo)).toFixed(2);
	var xStart2 	=(targetPlot.axes.xaxis.u2p(minimo)).toFixed(2);
	var xEnd2	=(targetPlot.axes.xaxis.u2p(maximo)).toFixed(2);

	var v = targetPlot;
    var x = controllerPlot.plugins.cursor;
    var z = x.zoomCanvas._ctx.canvas.height;
    var q = x.zoomCanvas._ctx.canvas.width;
    var w = v.axes;
    var o_start = controllerPlot.plugins.cursor._zoom.start[0];
    var o_end = controllerPlot.plugins.cursor._zoom.end[0];
    var o_end=xEnd2;
    var updateperiodo = dataSlider.updateperiodo;
	x._zoom.zooming = true;
	x._zoom.started = true;
	x._zoom.start=[xStart2,1];
	var gridEnd = {
	    		x : xEnd2,
	    		y : 1,
	};
	x._zoom.end = [xEnd2, 1];
	x._zoom.gridpos = gridEnd;
	var dataEnd = {
	    		xaxis: maximo,
	    		yaxis: 1,
	}
	var t = x._zoom.datapos=dataEnd;
	x._zoom.axes.start={
		xaxis: minimo,
		yaxis: 1,
	}
	x._zoom.axes.end={
		xaxis: maximo,
		yaxis: 1,
	}
	if (minimo instanceof Date) {
		w.xaxis.tickInterval 	= getTickInterval(tipo);
		w.xaxis.numberTicks 	= getTicks(minimo,maximo,tipo) ;
	}
	x.doZoom(gridEnd, dataEnd, v, x);
    x._zoom.started = false;
    x._zoom.zooming = false;
    //$(document).unbind("mousemove.jqplotCursor", h);
    if (document.onselectstart != undefined && x._oldHandlers.onselectstart != null) {
        document.onselectstart = x._oldHandlers.onselectstart;
        x._oldHandlers.onselectstart = null
    }
    if (document.ondrag != undefined && x._oldHandlers.ondrag != null) {
        document.ondrag = x._oldHandlers.ondrag;
        x._oldHandlers.ondrag = null
    }
    if (document.onmousedown != undefined && x._oldHandlers.onmousedown != null) {
        document.onmousedown = x._oldHandlers.onmousedown;
        x._oldHandlers.onmousedown = null
    }
}

function valuesPlotChangedTimesHour(dataSlider,controllerPlot,targetPlot,compdiario1,compdiario2,data,tipo) {
	var minimo 	= dataSlider.values.min;
	var maximo	= dataSlider.values.max;

	var xStart 	=(controllerPlot.axes.xaxis.u2p(minimo)).toFixed(2);
	var xEnd	=(controllerPlot.axes.xaxis.u2p(maximo)).toFixed(2);
	var xStart2 =(targetPlot.axes.xaxis.u2p(minimo)).toFixed(2);
	var xEnd2	=(targetPlot.axes.xaxis.u2p(maximo)).toFixed(2);

	var v = targetPlot;
    var x = controllerPlot.plugins.cursor;
    var z = x.zoomCanvas._ctx.canvas.height;
    var q = x.zoomCanvas._ctx.canvas.width;
    var w = v.axes;
    var o_start = controllerPlot.plugins.cursor._zoom.start[0];
    var o_end = controllerPlot.plugins.cursor._zoom.end[0];
    var o_end=xEnd2;
    var updateperiodo = dataSlider.updateperiodo;
	x._zoom.zooming = true;
	x._zoom.started = true;
	x._zoom.start=[xStart2,1];
	var gridEnd = {
	    		x : xEnd2,
	    		y : 1,
	};
	x._zoom.end = [xEnd2, 1];
	x._zoom.gridpos = gridEnd;
	var dataEnd = {
	    		xaxis: maximo,
	    		yaxis: 1,
	}
	var diferencia = Math.abs(Math.round(maximo-minimo));
	if (diferencia<=12) {
		w.xaxis.numberTicks 	= diferencia+1;
		w.xaxis.tickInterval 	= 1;
		var alignTicks 				= true;
	}
	var t = x._zoom.datapos=dataEnd;
	x._zoom.axes.start={
		xaxis: minimo,
		yaxis: 1,
	}
	x._zoom.axes.end={
		xaxis: maximo,
		yaxis: 1,
	}
	x.doZoom(gridEnd, dataEnd, v, x);
	x.doZoom(gridEnd, dataEnd, v, x);
    x._zoom.started = false;
    x._zoom.zooming = false;
    if (document.onselectstart != undefined && x._oldHandlers.onselectstart != null) {
        document.onselectstart = x._oldHandlers.onselectstart;
        x._oldHandlers.onselectstart = null
    }
    if (document.ondrag != undefined && x._oldHandlers.ondrag != null) {
        document.ondrag = x._oldHandlers.ondrag;
        x._oldHandlers.ondrag = null
    }
    if (document.onmousedown != undefined && x._oldHandlers.onmousedown != null) {
        document.onmousedown = x._oldHandlers.onmousedown;
        x._oldHandlers.onmousedown = null
    }
}

function formatter(tipo) {
	switch (tipo) {
		case 1: return "DIARIO"
		case 2: return "MENSUAL"
		case 3: return "TRIMESTRAL"
		case 4: return "SEMESTRAL"
		case 5: return "ANUAL"
	}
}

function formatterLabel(tipo,valor) {
	var fecvar =  valor.split("-");
	var anio = fecvar[0];
	var mes = fecvar[1];
	var dia = fecvar[2];
	switch (tipo) {
	case 1: return anio+"-"+mes+"-"+dia;
	case 2: return anio+"-"+mes;
	case 3:
	    var month = parseInt(mes);
	    var year = parseInt(anio);
	    var df =""+year;
	    if (month<=2) {
	    	df =  + df + "-I" ;
	    } else if (month<=5) {
	    	df = df + "-II" ;
	    } else if (month<=8) {
	    	df = df + "-III";
	    } else {
	    	df = df + "-IV";
	    }
	    return df;
	case 4:
	    var month = parseInt(mes);
	    var year = parseInt(anio);
	    var df =""+year;
	    if	(month<=5) {
	    	df =  + df + "-I" ;
	    } else {
	    	df = + df + "-II";
	    }
	    return df;
	case 5:
		return anio;
	}
}

function getTickInterval(tipo) {
	var mintickinterval;
	switch (tipo){
		case 1: mintickinterval="10 days";
			break;
		case 2: mintickinterval="1 month";
			break;
		case 3: mintickinterval="6 months";
			break;
		case 4: mintickinterval="12 months";
			break;
		case 5: mintickinterval="2 years";
			break;
	}
	return mintickinterval
}

function getTicks(min,max,tipo) {
	var resultado;
	switch (tipo) {
	case 1:
		 var dias = Math.abs(Math.round((min.getTime() - max.getTime())/(60*60*1000*24)));
		 if (dias<=10) resultado=dias;
		 else resultado = "10"
		break;
	case 2:
		 var meses = Math.abs(Math.round((min.getTime() - max.getTime())/(60*60*1000*24*30)));
		 if (meses<=10) resultado = meses;
		 else resultado = "10"
		break;
	case 3:
		 var trimestres = Math.abs(Math.round((min.getTime() - max.getTime())/(60*60*1000*24*90)));
		 if (trimestres<=10) resultado = trimestres;
		 else resultado=10;
		break;
	case 4:
		 var semestres = Math.abs(Math.round((min.getTime() - max.getTime())/(60*60*1000*24*180)));
		 if (semestres<=10) resultado = semestres;
		 else resultado=10;
		break;
	case 5:
		 var anios = Math.abs(min.getFullYear()-max.getFullYear());
		 if (anios<=10) resultado = anios;
		 else resultado=10;
		break;
	}
	return resultado==1?2:resultado;
}

var mensageError ="No hay datos para visualizar";

function brmensaje(message, type){
    return '<div class="ui-messages-' + type + ' ui-corner-all">'+
        '<a href="#" class="ui-messages-close" onclick="$(this).parent().slideUp();return false;">'+
            '<span class="ui-icon ui-icon-close" />'+
        '</a>'+
        '<span class="ui-messages-' + type + '-icon" />'+
        '<ul>'+
            '<li>'+
                '<span class="ui-messages-' + type + '-summary">'+
                message +
                '</span>'+
            '</li>'+
        '</ul>'+
    '</div>';
};

var formatNumber = {
	separador: ".", // separador para los miles
	sepDecimal: ',', // separador para los decimales
	formatear:function (num,decimales){

					//numero de decimales.
					num=Number(num).toFixed(decimales)
					num +='';
					var splitStr = num.split('.');
					var splitLeft = splitStr[0];
					var splitRight = splitStr.length > 1 ? this.sepDecimal + splitStr[1] : '';
					var regx = /(\d+)(\d{3})/;
					while (regx.test(splitLeft)) {
						splitLeft = splitLeft.replace(regx, '$1' + this.separador + '$2');
					}
					return this.simbol + splitLeft +splitRight;
		 	},
	new:function(num, decimales,simbol){
		 this.simbol = simbol ||'';
		 return this.formatear(num,decimales);
	}
}

function createlegend(titulo,valor,plot,tipodatototal) {

	var numero;
	if (tipodatototal==_ENTERO) {//entero
		numero = formatNumber.new(valor,0);
	} if (tipodatototal==2) {//porcentaje
		numero = Number(valor*100).toFixed(3);
	} else if  (tipodatototal==_NUMERICO) {
		numero = formatNumber.new(valor,3);
	}
	var line="<center><table class=\"custompielegendparent\">	"+
	 	 " 	<tr> 									"+
		 "		<td style=\"Wid100\" colspan=\"3\">	"+
  		 "		<div class=\"NoWrap\">				"+
  		 "			<div class=\"custompielegen\">	"+
  		 "				<div>"+titulo+"</div> "+
  		 "			</div>							"+
  		 "			<div class=\"FloatRigth\">Total " + numero + "</div>"+
  		 "		</div>								" +
		 "		</td>								" +
		 "	</tr>									" ;
	if (plot.data[0].length) {
		for (var i=0; i<plot.data[0].length;i++) {
			var porcentaje;
			//if(parseFloat(plot.data[0][i][1]) < parseFloat(0.001)){
				//var porcentaje = Number((parseFloat(plot.data[0][i][1])*100)).toFixed(3)
			var porcentaje = Number((parseFloat(plot.data[0][i][1])*100)).toFixed(3).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
			/*}else{
				var porcentaje = Number(plot.data[0][i][1]*100).toFixed(3);
			}*/

			line=line +"<tr style=\"color: #535353 !important;\"> 									";
			line=line +"    <td style='background: "+ plot.seriesColors[i] +"; border-top: 4px solid white !important; border-bottom: 4px solid white !important; margin-left: 10px !important'>";
			line=line +"    </td>														"
			line=line +"    <td>".concat(plot.data[0][i][0]);
			line=line +"    </td>														"
			line=line +"    <td style='text-align: right;'>".concat( porcentaje ) +"%";
			line=line +"    </td>														"
			line=line + "</tr></center>													";
		}
	}
	line=line +	 " </table>	" ;
	return line;
}

function getDataFiltered(datos,dateselected) {
	if (dateselected instanceof Date) {
		dateselected = ""+dateselected.getFullYear()+"-" + "" + (("0"+(dateselected.getMonth() + 1)).slice(-2) +"-" + ("0"+(dateselected.getDate())).slice(-2));
	} 
	
	var datasel=[];
	var datossumados=[];
	var totalesParse=[];
	var j=0;
	var totalS=0;
	var totales			=0;
	for (var i=0; i<datos.length; i++) {
		if (datos[i]["ejex"]==dateselected) {
			datasel[j]=new Array(datos[i]["ciudad"],datos[i]["valorPorcentaje"])
		   //totales = parseFloat(totales + datos[i]["valorPorcentaje"]);
		   totales = parseFloat(totales + datos[i]["serieValor"]);
		   datossumados[j]=new Array(datos[i]["ciudad"],datos[i]["serieValor"],datos[i]["valorPorcentaje"])
		   totalS= totalS + 1;
			j++;
		}
	}

	var datosseleccionados = [[]];
	//datosseleccionados[0] = datasel.sort(ordenar);
	datosseleccionados[0] = datasel;
	datosseleccionados[1] = totales;
	return datosseleccionados;
}

//Cantidades
function getDataCountFiltered(datos,dateselected) {
	if (dateselected instanceof Date) {
		dateselected = ""+dateselected.getFullYear()+"-" + "" + (("0"+(dateselected.getMonth() + 1)).slice(-2) +"-" + ("0"+(dateselected.getDate())).slice(-2));
	} 
	
    //linea 1077
	var datasel=[];
	var datossumados=[];
	var totalesParse=[];
	var j=0;
	var totalS=0;
	var totales			=0;
	for (var i=0; i<datos.length; i++) {
		if (datos[i][1]==dateselected) {
			datasel[j]=new Array(datos[i][2],datos[i][3])
		   totales = parseFloat(totales + datos[i][4]);

		   datossumados[j]=new Array(datos[i][2],datos[i][4],datos[i][3])
		   totalS= totalS + 1;
			j++;
		}
	}

	var datosseleccionados = [[]];
	//ordenar datase1 
	//datosseleccionados[0] = datasel.sort(ordenar);
	datosseleccionados[0] = datasel;
	datosseleccionados[1] = totales;
	var totalvalorsumado = totalS;
	return datosseleccionados;
}

function ordenar(a,b) {
	  if (a[1] < b[1]) {
	    return 1;
	  }
	  if (a[1] > b[1]) {
	    return -1;
	  }
	  // a must be equal to b
	  return 0;
}

function ordenarValores(a,b) {
	  if (a.valorPorcentaje < b.valorPorcentaje) {
	    return 1;
	  }
	  if (a.valorPorcentaje > b.valorPorcentaje) {
	    return -1;
	  }
	  // a must be equal to b
	  return 0;
}

function ordenarCantidades(a,b) {
	  if (a[4] < b[4]) {
	    return 1;
	  }
	  if (a[4] > b[4]) {
	    return -1;
	  }
	  // a must be equal to b
	  return 0;
}

/**
 * COMPENSACION CHEQUES CANJE
 **/

function scroll (){
	 var ua = window.navigator.userAgent;
	    var msie = ua.indexOf("MSIE ");
	 if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	    {

		 try
		 {
			 var bodyheight = document.getElementById('vcanje_chartvalores').clientHeight;
			 var bodyheight2 = document.getElementById('vdevol_chartvalores').clientHeight;

		 }
		 catch(err){
			var bodyheight2 = 0;
		 }

		 if (bodyheight > 0  ) {
			var boyheight2 = boyheight2 -50;
				window.scrollTo(0,bodyheight);
		 }

	    }else {
	    	window.scrollTo(0,333);

	    }
}

function createChartMinDiario(chartvalores,chartcantidad,compdiario1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {

		data =  RestCompensacionServices.getCompensacionDiaria({'tipodeplaza':tipodeplaza});
		
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}
		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		
		vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"],1,periodoAnterior);

		ticks = data["Ticks"];
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		//
		if (onetime!=null && onetime=="1") {
			vStorageFecSelected=dateend;
		} 				
		startvaluescomponentstimes (vStorageFecSelected,compdiario1,data,_DIARIO);
		
		$("#"+chartvalores)[0].setAttribute("class","");
		chartvaloresid=chartvalores+"_chart";
		divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		datasel = [[]];
		dateselected = data["endX"];
		datosvalores 	= getDataFiltereCompensacion(data["SerieValores"],vStorageFecSelected);
		leftPlot 		= createplotpieleft ( chartvaloresid, datosvalores[0] , _DIARIO);
		legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		divlegendvalores = document.createElement('div');
		divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		chartcantidadid=chartcantidad+"_chart";
		divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		rigthPlot 		= createplotpieright ( chartcantidadid		,	datoscantidades[0] , _DIARIO);
		legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		divlegendcantidad = document.createElement('div');
		divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+compdiario1+"_sel_anio").unbind();
		$("#"+compdiario1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,compdiario1,divlegendvaloresid,divlegendcantidadid,data,_DIARIO,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compdiario1, "1")
		});
		$("#"+compdiario1+"_sel_mes").unbind();
		$("#"+compdiario1+"_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,compdiario1,divlegendvaloresid,divlegendcantidadid,data,_DIARIO,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compdiario1, "1")
		});

		$("#"+compdiario1+"_sel_dia").unbind();
		$("#"+compdiario1+"_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,compdiario1,divlegendvaloresid,divlegendcantidadid,data,_DIARIO,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compdiario1, "1")
		});
		
		scroll();
		

	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

function createChartMinMensual(chartvalores,chartcantidad,compmensual1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {
		data =  RestCompensacionServices.getCompensacionMensual({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}

		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"],2,periodoAnterior);
		ticks = data["Ticks"];
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		} 				
		startvaluescomponentstimes (vStorageFecSelected,compmensual1,data,_MENSUAL);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltereCompensacion(data["SerieValores"],vStorageFecSelected);
		var leftPlot 		= createplotpieleft ( chartvaloresid		,	datosvalores[0] , _MENSUAL);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpieright ( chartcantidadid		,	datoscantidades[0] , _MENSUAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+compmensual1+"_sel_anio").unbind();
		$("#"+compmensual1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,compmensual1,divlegendvaloresid,divlegendcantidadid,data,_MENSUAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compmensual1, "2");
		});

		$("#"+compmensual1+"_sel_mes").unbind();
		$("#"+compmensual1+"_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,compmensual1,divlegendvaloresid,divlegendcantidadid,data,_MENSUAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compmensual1, "2");
		});
		scroll();
		
	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

function createChartMinTrimestral(chartvalores,chartcantidad,comptrimestral1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {
		data =  RestCompensacionServices.getCompensacionTrimestral({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}

		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"],3,periodoAnterior);
		if (onetime!=null && onetime=="1") {
			let ticks = data["Ticks"];
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		} 				
		startvaluescomponentstimes (vStorageFecSelected,comptrimestral1,data,_TRIMESTRAL);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltereCompensacion(data["SerieValores"],vStorageFecSelected);
		var leftPlot 		= createplotpieleft ( chartvaloresid		,	datosvalores[0] , _TRIMESTRAL);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpieright ( chartcantidadid		,	datoscantidades[0] , _TRIMESTRAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		scroll();

		$("#"+comptrimestral1+"_sel_anio").unbind();
		$("#"+comptrimestral1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(comptrimestral1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,comptrimestral1,divlegendvaloresid,divlegendcantidadid,data,_TRIMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, comptrimestral1, "3");
		});

		$("#"+comptrimestral1+"_sel_trimestre").unbind();
		$("#"+comptrimestral1+"_sel_trimestre").change(function(evt) {
			changedatesselectedstimes(comptrimestral1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,comptrimestral1,divlegendvaloresid,divlegendcantidadid,data,_TRIMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, comptrimestral1, "3");
		});
	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
}

function createChartMinSemestral(chartvalores,chartcantidad,compsemestral1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {
		data =  RestCompensacionServices.getCompensacionSemestral({'tipodeplaza':tipodeplaza});

		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}
		
		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected",4,periodoAnterior);
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);

		ticks = data["Ticks"];
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		} 				
		startvaluescomponentstimes (vStorageFecSelected,compsemestral1,data,_SEMESTRAL);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltereCompensacion(data["SerieValores"],vStorageFecSelected);
		var leftPlot 		= createplotpieleft ( chartvaloresid		,	datosvalores[0] , _SEMESTRAL);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpieright ( chartcantidadid		,	datoscantidades[0] , _SEMESTRAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		scroll();

		$("#"+compsemestral1+"_sel_anio").unbind();
		$("#"+compsemestral1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(compsemestral1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,compsemestral1,divlegendvaloresid,divlegendcantidadid,data,_SEMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compsemestral1, "4");
		});

		$("#"+compsemestral1+"_sel_semestre").unbind();
		$("#"+compsemestral1+"_sel_semestre").change(function(evt) {
			changedatesselectedstimes(compsemestral1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,compsemestral1,divlegendvaloresid,divlegendcantidadid,data,_SEMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compsemestral1, "4");
		});

	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
	
}

function getDataFiltereCompensacion(datos,dateselected) {
	if (dateselected instanceof Date) {
		dateselected = ""+dateselected.getFullYear()+"-" + "" + (("0"+(dateselected.getMonth() + 1)).slice(-2) +"-" + ("0"+(dateselected.getDate())).slice(-2));
	} 
	var datasel=[];
	var datossumados=[];
	var totalesParse=[];
	var j=0;
	var totales=0;
	for (var i=0; i<datos.length; i++) {
		if (datos[i].ejex==dateselected) {
			datasel[j]=new Array(datos[i].ciudad,datos[i].valorPorcentaje)
		    totales = parseFloat(totales + datos[i].serieValor);

		    datossumados[j]=new Array(datos[i].ciudad,datos[i].serieValor,datos[i].valorPorcentaje)
			j++;
		}
	}

	var datosseleccionados = [[]];
	//datosseleccionados[0] = datasel.sort(ordenar);
	datosseleccionados[0] = datasel;
	datosseleccionados[1] = totales;
	return datosseleccionados;

}

function createChartMinAnual(chartvalores,chartcantidad,companual1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	
	try {
	
		data =  RestCompensacionServices.getCompensacionAnual({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}

		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;

		vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		if (vStorageFecSelected!=null && typeof vStorageFecSelected === "string") {
			vStorageFecSelected=vStorageFecSelected.substring(0,4)+"-01-01";
		}
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"],5,periodoAnterior);

		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		ticks = data["Ticks"];
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		}
		
		startvaluescomponentstimes (vStorageFecSelected,companual1,data,_ANUAL);
		
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		datasel = [[]];
		dateselected = data["endX"];;
		datosvalores = getDataFiltereCompensacion(data["SerieValores"],vStorageFecSelected);
		
		leftPlot 		= createplotpieleft ( chartvaloresid		,	datosvalores[0] , _ANUAL);
		legendavalores = createlegend("Valor de Cheques"			, datosvalores[1],leftPlot,_NUMERICO);
		divlegendvalores = document.createElement('div');
		divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);

		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpieright ( chartcantidadid		,	datoscantidades[0] , _ANUAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+companual1+"_sel_anio").unbind();
		$("#"+companual1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(companual1,data);
			updatedCanjealCobro(leftPlot,rigthPlot,companual1,divlegendvaloresid,divlegendcantidadid,data,_ANUAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, companual1, "5");
		});

		scroll();

	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}

};

function createplotpieleft (name,data,tipo) {
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [data] , {
		seriesColors:[
			'#5299d3',
			'#9bc1db',
			'#6dbfcd',
			'#89b0b5',
			'#94ad9e',
			'#1aa461',
			'#9ac874',
			'#d7d7b2',
			'#b3b1d9',
			'#bdacc0',
			'#d6e1f4',
			'#e3edf6',
			'#c2e0e8',
			'#b9d0d3',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#e5e4cc',
			'#c6c4e4',
			'#cdc1d0',
			'#8db8d6',
			'#00aabc',
			'#a9c4e8',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#006fb9',
			'#66a3c8',
			'#0092a6',
			'#00656b',
			'#003b25',
			'#008d3f',
			'#52aa32',
			'#b8b87b',
			'#3b4395',
			'#593969',
			'#1c8dcc',
			'#7dafd1',
			'#6dbfcd',
			'#4b755f',
			'#1aa461',
			'#9ac874',
			'#4b755f',
			'#c3c38d',
			'#6668ae',
			'#8e7092',
			'#1c8dcc',
			'#00aabc',
			'#a9c4e8',
			'#94ad9e',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#94ad9e'
			],
		seriesDefaults:{
			renderer:$.jqplot.PieRenderer,
			showMarker: true,
			textColor:  "#ffffff",
			rendererOptions: {
				diameter:234,
				dataLabelPositionFactor:0.77,
				//dataLabelThreshold : 0, todos los dataTables
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 3,
				showDataLabels: true,
				dataLabelFormatString: '%.3f%'
			},
            animation: {
                show: false
            },
        },
	    grid: {
    		background : 'rgba(255, 255, 255, 0.01)',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : false,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		left: "0",

        },
        legend: {
        	dataLabelFormatString: '%.3f%'
        },
	});
	return plot2;
}


function createplotpieright (name,data,tipo) {
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [data] , {
		seriesColors:[
			'#5299d3',
			'#9bc1db',
			'#6dbfcd',
			'#89b0b5',
			'#94ad9e',
			'#1aa461',
			'#9ac874',
			'#d7d7b2',
			'#b3b1d9',
			'#bdacc0',
			'#d6e1f4',
			'#e3edf6',
			'#c2e0e8',
			'#b9d0d3',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#e5e4cc',
			'#c6c4e4',
			'#cdc1d0',
			'#8db8d6',
			'#00aabc',
			'#a9c4e8',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#006fb9',
			'#66a3c8',
			'#0092a6',
			'#00656b',
			'#003b25',
			'#008d3f',
			'#52aa32',
			'#b8b87b',
			'#3b4395',
			'#593969',
			'#1c8dcc',
			'#7dafd1',
			'#6dbfcd',
			'#4b755f',
			'#1aa461',
			'#9ac874',
			'#4b755f',
			'#c3c38d',
			'#6668ae',
			'#8e7092',
			'#1c8dcc',
			'#00aabc',
			'#a9c4e8',
			'#94ad9e',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#94ad9e'
			],
		seriesDefaults:{
			renderer:$.jqplot.PieRenderer,
			showMarker: true,
			textColor:  "#ffffff",
			rendererOptions: {
				diameter:234,
				dataLabelPositionFactor:0.77,
				//dataLabelThreshold : 0, todos los dataTables
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 3,
				showDataLabels: true,
				dataLabelFormatString: '%.3f%',
			},

            animation: {
                show: false
            },
        },
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : false,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		left: "0",
        }
	});
	return plot2;
}



/**
 * COMPENSACION CHEQUES DEVOLUCIONES
 **/

function scroll2 (){

	 var ua = window.navigator.userAgent;
	    var msie = ua.indexOf("MSIE ");
	 if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	    {

		 try
		 {
			 var bodyheight2 = document.getElementById('vdevol_chartvalores').clientHeight;
			 var bodyheight = document.getElementById('vcanje_chartvalores').clientHeight;

		 }
		 catch(err){
			var bodyheight = 0;
		 }
		 if (bodyheight > 0 ) {
			 var bodyheight2 = bodyheight2 + 150;
		 }

		 var bodyheight2 = bodyheight2 + 50;
		 var final =bodyheight+bodyheight2;
			window.scrollTo(0,final );

	    }
	 else {
		 window.scrollTo(0,414 );

	 }
}

function createChartMinDiarioDevol(chartvalores,chartcantidad,compdiario1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){

	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	
	try {
		data =  RestCompensacionServices.getCompensacionDevolDiaria({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}
		
		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		ticks = data["Ticks"];

		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 9);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = dateend;
		}
		 				
		startvaluescomponentstimes (vStorageFecSelected,compdiario1,data,_DIARIO);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltered(data["SerieValores"],vStorageFecSelected);
		var leftPlot 		= createplotpieleftdevol ( chartvaloresid		,	datosvalores[0] , _DIARIO);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpierightdevol ( chartcantidadid		,	datoscantidades[0] , _DIARIO);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+compdiario1+"_sel_anio").unbind();
		$("#"+compdiario1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,compdiario1,divlegendvaloresid,divlegendcantidadid,data,_DIARIO,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compdiario1, "1");
		});

		$("#"+compdiario1+"_sel_mes").unbind();
		$("#"+compdiario1+"_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,compdiario1,divlegendvaloresid,divlegendcantidadid,data,_DIARIO,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compdiario1, "1");
		});

		$("#"+compdiario1+"_sel_dia").unbind();
		$("#"+compdiario1+"_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,compdiario1,divlegendvaloresid,divlegendcantidadid,data,_DIARIO,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compdiario1, "1");
		});


		scroll2 ();

	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

function createChartMinMensualDevol(chartvalores,chartcantidad,compmensual1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {
		data =  RestCompensacionServices.getCompensacionDevolMensual({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}
		
		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		if (onetime!=null && onetime=="1") {
			let ticks = data["Ticks"];
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		} 		
		startvaluescomponentstimes (vStorageFecSelected,compmensual1,data,_MENSUAL);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltered(data["SerieValores"],vStorageFecSelected);
		var leftPlot 		= createplotpieleftdevol ( chartvaloresid		,	datosvalores[0] , _MENSUAL);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpierightdevol ( chartcantidadid		,	datoscantidades[0] , _MENSUAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+compmensual1+"_sel_anio").unbind();
		$("#"+compmensual1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,compmensual1,divlegendvaloresid,divlegendcantidadid,data,_MENSUAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compmensual1, "2");
		});

		$("#"+compmensual1+"_sel_mes").unbind();
		$("#"+compmensual1+"_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,compmensual1,divlegendvaloresid,divlegendcantidadid,data,_MENSUAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compmensual1, "2");
		});
		scroll2 ();
	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

function createChartMinTrimestralDevol(chartvalores,chartcantidad,comptrimestral1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {
		data =  RestCompensacionServices.getCompensacionDevolTrimestral({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}
		
		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		if (onetime!=null && onetime=="1") {
			let ticks = data["Ticks"];
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		} 		
		startvaluescomponentstimes (vStorageFecSelected,comptrimestral1,data,_TRIMESTRAL);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltered(data["SerieValores"],vStorageFecSelected);
		var leftPlot 		= createplotpieleftdevol ( chartvaloresid		,	datosvalores[0] , _TRIMESTRAL);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpierightdevol ( chartcantidadid		,	datoscantidades[0] , _TRIMESTRAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+comptrimestral1+"_sel_anio").unbind();
		$("#"+comptrimestral1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(comptrimestral1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,comptrimestral1,divlegendvaloresid,divlegendcantidadid,data,_TRIMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, comptrimestral1, "3");
		});

		$("#"+comptrimestral1+"_sel_trimestre").unbind();
		$("#"+comptrimestral1+"_sel_trimestre").change(function(evt) {
			changedatesselectedstimes(comptrimestral1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,comptrimestral1,divlegendvaloresid,divlegendcantidadid,data,_TRIMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, comptrimestral1, "3");
		});
		scroll2 ();
	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
}

function createChartMinSemestralDevol(chartvalores,chartcantidad,compsemestral1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {
		data =  RestCompensacionServices.getCompensacionDevolSemestral({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}
		
		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		if (onetime!=null && onetime=="1") {
			let ticks = data["Ticks"];
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		} 		
		startvaluescomponentstimes (vStorageFecSelected,compsemestral1,data,_SEMESTRAL);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltered(data["SerieValores"],vStorageFecSelected);
		var leftPlot 		= createplotpieleftdevol ( chartvaloresid		,	datosvalores[0] , _SEMESTRAL);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpierightdevol ( chartcantidadid		,	datoscantidades[0] , _SEMESTRAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+compsemestral1+"_sel_anio").unbind();
		$("#"+compsemestral1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(compsemestral1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,compsemestral1,divlegendvaloresid,divlegendcantidadid,data,_SEMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compsemestral1, "4");
		});

		$("#"+compsemestral1+"_sel_semestre").unbind();
		$("#"+compsemestral1+"_sel_semestre").change(function(evt) {
			changedatesselectedstimes(compsemestral1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,compsemestral1,divlegendvaloresid,divlegendcantidadid,data,_SEMESTRAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, compsemestral1, "4");
		});

		scroll2 ();
	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
}

function createChartMinAnualDevol(chartvalores,chartcantidad,companual1,tipodeplaza,label,errormessage,almacen,onetime,periodoAnterior){
	
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	try {
		data =  RestCompensacionServices.getCompensacionDevolAnual({'tipodeplaza':tipodeplaza});
		if ("MUL"==tipodeplaza){
			data["SerieValores"].sort(ordenarValores);
			data["SerieCantidad"].sort(ordenarCantidades);
		}
		
		/* PIE VALORES */
		$("#"+chartvalores).empty();
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		var vStorageFecSelected = localStorage.getItem(almacen + "_fecSelected");
		vStorageFecSelected = completarFechaSelected(vStorageFecSelected,data["Ticks"]);
		if (onetime!=null && onetime=="1") {
			let ticks = data["Ticks"];
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			vStorageFecSelected = new Date(fromtmp);
			vStorageFecSelected = completarFechaStart(fromtmp,ticks);
		} 		
		startvaluescomponentstimes (vStorageFecSelected,companual1,data,_ANUAL);
		$("#"+chartvalores)[0].setAttribute("class","");
		var chartvaloresid=chartvalores+"_chart";
		var divchart = document.createElement('div');
		divchart.style="chartcustom"
		divchart.id=chartvaloresid;
		document.getElementById(chartvalores).appendChild(divchart);
		var datasel = [[]];
		var dateselected = data["endX"];
		var datosvalores = getDataFiltered(data["SerieValores"],vStorageFecSelected);
		
		var leftPlot 		= createplotpieleftdevol ( chartvaloresid		,	datosvalores[0] , _ANUAL);
		var legendavalores = createlegend("Valor de Cheques", datosvalores[1],leftPlot,_NUMERICO);
		var divlegendvalores = document.createElement('div');
		var divlegendvaloresid=chartvalores+"_legend"
		divlegendvalores.id=divlegendvaloresid;
		divlegendvalores.innerHTML=legendavalores;
		document.getElementById(chartvalores).appendChild(divlegendvalores);
		/* PIE CANTIDAD */
		$("#"+chartcantidad).empty();
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		$("#"+chartcantidad)[0].setAttribute("class","");
		var chartcantidadid=chartcantidad+"_chart";
		var divchartcant = document.createElement('div');
		//divchartcant.style="chartcustom"
		divchartcant.id=chartcantidadid;
		document.getElementById(chartcantidad).appendChild(divchartcant);
		var datoscantidades = getDataCountFiltered(data["SerieCantidad"],vStorageFecSelected);
		var rigthPlot 		= createplotpierightdevol ( chartcantidadid		,	datoscantidades[0] , _ANUAL);
		var legendacantidad = createlegend("Cantidad de cheques" , datoscantidades[1],rigthPlot,_ENTERO);
		var divlegendcantidad = document.createElement('div');
		var divlegendcantidadid=chartcantidad+"_legend"
		divlegendcantidad.id=divlegendcantidadid;
		divlegendcantidad.innerHTML=legendacantidad;
		document.getElementById(chartcantidad).appendChild(divlegendcantidad);

		$("#"+companual1+"_sel_anio").unbind();
		$("#"+companual1+"_sel_anio").change(function(evt) {
			changedatesselectedstimes(companual1,data);
			updatedatapieplottimesranges(leftPlot,rigthPlot,companual1,divlegendvaloresid,divlegendcantidadid,data,_ANUAL,errormessage,chartvalores,chartcantidad);
			savedOldDatesStoStorage(almacen, companual1, "5");
		});
		scroll2 ();
	} catch (err) {
		if (logerrors) console.log(err);
		$("[id*='"+errormessage+"'").show();
		$("[id*='"+errormessage+"']").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}

};

function createplotpieleftdevol (name,data,tipo) {
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [data] , {
		seriesColors:[
			'#5299d3',
			'#9bc1db',
			'#6dbfcd',
			'#89b0b5',
			'#94ad9e',
			'#1aa461',
			'#9ac874',
			'#d7d7b2',
			'#b3b1d9',
			'#bdacc0',
			'#d6e1f4',
			'#e3edf6',
			'#c2e0e8',
			'#b9d0d3',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#e5e4cc',
			'#c6c4e4',
			'#cdc1d0',
			'#8db8d6',
			'#00aabc',
			'#a9c4e8',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#006fb9',
			'#66a3c8',
			'#0092a6',
			'#00656b',
			'#003b25',
			'#008d3f',
			'#52aa32',
			'#b8b87b',
			'#3b4395',
			'#593969',
			'#1c8dcc',
			'#7dafd1',
			'#6dbfcd',
			'#4b755f',
			'#1aa461',
			'#9ac874',
			'#4b755f',
			'#c3c38d',
			'#6668ae',
			'#8e7092',
			'#1c8dcc',
			'#00aabc',
			'#a9c4e8',
			'#94ad9e',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#94ad9e'
		],
		seriesDefaults:{
			renderer:$.jqplot.PieRenderer,
			showMarker: true,
			textColor:  "#ffffff",
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
				showDataLabels: true,
				dataLabelFormatString: '%.3f%'
			},
            animation: {
                show: false
            },
        },
	    grid: {
    		background : 'rgba(255, 255, 255, 0.01)',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : false,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		left: "0",
        },
        legend: {
        	dataLabelFormatString: '%.3f%'
        },
	});
	return plot2;
}

function createplotpierightdevol (name,data,tipo) {
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [data] , {
		seriesColors:[
			'#5299d3',
			'#9bc1db',
			'#6dbfcd',
			'#89b0b5',
			'#94ad9e',
			'#1aa461',
			'#9ac874',
			'#d7d7b2',
			'#b3b1d9',
			'#bdacc0',
			'#d6e1f4',
			'#e3edf6',
			'#c2e0e8',
			'#b9d0d3',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#e5e4cc',
			'#c6c4e4',
			'#cdc1d0',
			'#8db8d6',
			'#00aabc',
			'#a9c4e8',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#006fb9',
			'#66a3c8',
			'#0092a6',
			'#00656b',
			'#003b25',
			'#008d3f',
			'#52aa32',
			'#b8b87b',
			'#3b4395',
			'#593969',
			'#1c8dcc',
			'#7dafd1',
			'#6dbfcd',
			'#4b755f',
			'#1aa461',
			'#9ac874',
			'#4b755f',
			'#c3c38d',
			'#6668ae',
			'#8e7092',
			'#1c8dcc',
			'#00aabc',
			'#a9c4e8',
			'#94ad9e',
			'#94c59c',
			'#bbd8a0',
			'#628873',
			'#d7d7b2',
			'#7979b9',
			'#ad97b0',
			'#eaf0fa',
			'#aec2b5',
			'#c4ddc5',
			'#d8e8c8',
			'#94ad9e'
		],
		seriesDefaults:{
			renderer:$.jqplot.PieRenderer,
			showMarker: true,
			textColor:  "#ffffff",
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
				showDataLabels: true,
				dataLabelFormatString: '%.3f%'
			},
            animation: {
                show: false
            },
        },
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : false,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		left: "0",
        },
	});
	return plot2;
}
