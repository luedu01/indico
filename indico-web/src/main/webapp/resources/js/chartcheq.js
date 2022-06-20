/**
 *  GENERAL FUNCTIONS
 **/

function completarFechaStart(fecha,sticks) {
	if (fecha == null && (sticks==null || sticks.length==0)) return null;
	if (fecha instanceof Date) {
	} else {
		var anio,mes,dia;
		if (fecha!=null){
			anio = fecha.split('-')[0];
			mes = fecha.split('-')[1];
			dia = fecha.split('-')[2];
	
			if (mes === undefined) { mes = "01"; }
			if (dia === undefined) { dia = "01"; }
		}
		fecha = new Date(anio, mes - 1, dia);
	} 

	if (isNaN(fecha) == true) {
  		fecha=new Date(sticks[0].split('-')[0],sticks[0].split('-')[1]-1,sticks[0].split('-')[2]);
	} else {
		let anterior=new Date(sticks[0].split('-')[0],sticks[0].split('-')[1]-1,sticks[0].split('-')[2]);
		for (let i=0 ; i<sticks.length ; i++ ) {
			let currentValue = sticks[i]; 
			let actualdate = new Date(currentValue.split('-')[0],currentValue.split('-')[1]-1,currentValue.split('-')[2]);
			if (fecha>=actualdate) {
				anterior=actualdate;
			}else{
				//fecha=anterior;
				break;
			}
		}
	}
	return fecha;
}

function completarFechaEnd(fecha,sticks) {
	if (fecha == null && (sticks ==null || sticks.length==0)) return null;
	var anio,mes,dia;
	if (fecha!=null){
		anio = fecha.split('-')[0];
		mes = fecha.split('-')[1];
		dia = fecha.split('-')[2];

		if (mes === undefined) { mes = "01"; }
		if (dia === undefined) { 
			dia = new Date(anio, (parseInt(mes)), 0).getDate(); 
		}
	}
	var fecha = new Date(anio, mes-1, dia);
	if (isNaN(fecha) == true) {
		fecha = sticks[sticks.length-1];
  		fecha=new Date(fecha.split('-')[0],fecha.split('-')[1]-1,fecha.split('-')[2]);
	} else {
		let anterior = sticks[0];
		anterior=new Date(anterior.split('-')[0],(anterior.split('-')[1]-1),anterior.split('-')[2]);
		for (let i=0 ; i<sticks.length ; i++ ) {
			let currentValue = sticks[i]; 
			let actualdate = new Date(currentValue.split('-')[0],(currentValue.split('-')[1]-1),currentValue.split('-')[2]);
			if (fecha>=actualdate) {
				anterior=actualdate;
			}else{
				fecha=anterior;
				break;
			}
		}
		if (fecha>anterior) {
			fecha=anterior;
		}
	}	
	return fecha;
}

function savedOldDatesStoStorage(almacen, componente1, componente2, anterior) {

	var vfecStart;
	var vfecEnd;

	anterior=parseInt(anterior);

	switch (anterior) {
		case 1:
			vfecStart = getDateStartFromDiario(componente1);
			vfecEnd = getDateEndFromDiario(componente2);
			break;
		case 2:
			vfecStart = getDateStartFromMensual(componente1);
			vfecEnd = getDateEndFromMensual(componente2);
			break;
		case 3:
			vfecStart = getDateStartFromTrimestral(componente1);
			vfecEnd = getDateEndFromTrimestral(componente2);
			break;
		case 4:
			vfecStart = getDateStartFromSemestral(componente1);
			vfecEnd = getDateEndFromSemestral(componente2);
			break;
		case 5:
			vfecStart = getDateStartFromAnual(componente1);
			vfecEnd = getDateStartFromAnual(componente2);
			break;
	}

	localStorage.setItem(almacen + "_fecStart", vfecStart);
	localStorage.setItem(almacen + "_fecEnd", vfecEnd);

}

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

// Parser de objetos a arreglo
function datosValor(serieValores){
     
     var serieValorD =[];
     
      for (var i = 0; i < serieValores.length; i++) {
          var valor2 = serieValores[i].serieValor;
          var fecha = (serieValores[i].valorFecha);
         serieValorD.push([fecha, valor2]);
      }
      return serieValorD;
 }

// Parser de objetos a arreglo de 4
function datosValor4(Valores4){
     
     var ValorD =[];
     
      for (var i = 0; i < Valores4.length; i++) {
			var ejex = Valores4[i].ejex;
			var serie = (Valores4[i].serie);
			var valoripc = Valores4[i].valoripc;	
			var variacionpor = (Valores4[i].variacionpor);
			ValorD.push([ejex, serie, valoripc, variacionpor]);
      }
      return ValorD;
 }


/**
 *  Nuevos componentes basados en el tiempo
 **/

function startvaluescomponentstimes(valchanged1,valchanged2,compdiario1,compdiario2,data) {

	var inicioX;
	var endX;
	if (valchanged1 != null && valchanged2 != null) {
		inicioX = valchanged1;
		endX = valchanged2;
	} else {
		inicioX = data["startX"];
		endX = data["endX"];
		var fechaA = data["startX"].split("-");
		if (fechaA.length==1) {
			fechaA[1]="01";
			fechaA[2]="01";
		}	
		inicioX = new Date(fechaA[0],parseInt(fechaA[1])-1,fechaA[2]);
		var fechaB = data["endX"].split("-");
		if (fechaB.length==1) {
			fechaB[1]="01";
			fechaB[2]="01";
		}	
		endX = new Date(fechaB[0],parseInt(fechaB[1])-1,fechaB[2]);
	}
	
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

function updatevaluescomponentstimesfromslider(slider,compdiario1,compdiario2,data,tipo) {
	
	if  (isNaN(slider.values.min.getTime())) return;
	if  (isNaN(slider.values.max.getTime())) return;
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

/**
 * Función que agrega los años en el select option
 * @param data
 * @param selanio
 * @param selected
 * @returns
 */
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

/**
 * Función agrega los meses
 * @param data
 * @param selmeses
 * @param selected
 * @returns
 */
function addmesestimes(data, selmeses, selected) {
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


/**
 * Función que agrega los días
 * @param data
 * @param seldia
 * @param selected
 * @returns
 */
function adddiastimes(data, seldia, selected) {
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

/**
 * Funcion que agrega los trimestres
 * @param data
 * @param seltrimestre
 * @param selected
 * @returns
 */
function addtrimestretimes(data, seltrimestre, selected) {
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

/**
 * Función que agrega los semestres
 * @param data
 * @param selsemestre
 * @param selected
 * @returns
 */
function addsemestretimes(data, selsemestre, selected) {
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


function changedatesselectedstimes(compdiario1,compdiario2,data,slider) {

	var mes1,dia1,anio1; 
	var mes2,dia2,anio2;
	
	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(compdiario1+"_sel_anio");
	var secValue = selAnio1.options[1].value;
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;
	
	//Primer y ultimo valor de meses
	var selMes1 = document.getElementById(compdiario1+"_sel_mes");
	if(selMes1 != null){
		var secMesValue = selMes1.options[1].value;
		var lastMesValue = selMes1.options[selMes1.options.length - 1].value;
	}
	
	//Primer y ultimo valor de meses2
	var selMes2 = document.getElementById(compdiario2+"_sel_mes");
	var secMesValue2 = selMes2.options[1].value;
	var lastMesValue2 = selMes2.options[selMes2.options.length - 1].value;
		
	//Primer y ultimo valor de días
	var selDia1 = document.getElementById(compdiario1+"_sel_dia");
	if(selDia1 != null){
		var secDiaValue = selDia1.options[1].value;
		var lastDiaValue = selDia1.options[selDia1.options.length - 1].value;
	}
	
	//Primer y ultimo valor de día 2
	var selDia2 = document.getElementById(compdiario2+"_sel_dia");
	if(selDia2 != null){
		var secDiaValue2 = selDia2.options[1].value;
		var lastDiaValue2 = selDia2.options[selDia2.options.length - 1].value;
	}
	
	if ($("#"+compdiario1+"_sel_anio").length ) {
		anio1 	= $("#"+compdiario1+"_sel_anio").val();
		if(anio1 == ""){
			anio1 	= secValue;
		}
	}
	
	if ($("#"+compdiario1+"_sel_mes").length) {
		mes1 	= $("#"+compdiario1+"_sel_mes").val();
		if(mes1 == ""){
			mes1 = secMesValue;
		}
				
	} else  {
		mes1="01" 
	}
	
	if ($("#"+compdiario1+"_sel_dia").length) {
		dia1 	= $("#"+compdiario1+"_sel_dia").val();
		if(dia1 == ""){
			dia1 = secDiaValue;
		}
	} else {
		dia1 ="01";
	}
	
	if ($("#"+compdiario2+"_sel_anio").length) {
		anio2 	= $("#"+compdiario2+"_sel_anio").val();
		if(anio2 == ""){
			anio2 	= lastValue;
		}
	}
	
	if ($("#"+compdiario2+"_sel_mes").length) {
		mes2 	= $("#"+compdiario2+"_sel_mes").val();
		if(mes2 == ""){
			mes2 = lastMesValue2;
		}
				
	} else {
		mes2="01";
	}

	if ($("#"+compdiario2+"_sel_dia").length) {
		dia2 	= $("#"+compdiario2+"_sel_dia").val();
		if(dia2 == ""){
			dia2 = lastDiaValue2;
		}
		
		/*if(parseInt(anio1) > parseInt(anio2)){
			anio1 = secValue;
			anio2 = lastValue;
		}
		
		if(parseInt(mes1) > parseInt(mes2)){
			mes1 = secMesValue;
			mes2 = lastMesValue2;
		}
		
		if(parseInt(dia1) > parseInt(dia2)){
			dia1 = (parseInt(dia2)-10).toString();
			dia2 = lastDiaValue2;
		}
		*/
  
  
		if(anio1 == secValue && (mes1 == parseInt(secMesValue-1)) && parseInt(dia1) == secDiaValue && parseInt(dia1) == parseInt(dia2)){
			dia1 = dia2;
			dia2 = (parseInt(dia2)+10).toString();
		}
		
		if(anio1 == anio2 && (mes1 == mes2) && parseInt(dia1) == parseInt(dia2)){
			dia1 = lastDiaValue2-10;
			dia2 = lastDiaValue2;
			
		}
		
		if(anio1 == lastValue && (mes1 == parseInt(lastMesValue2-1)) && parseInt(dia1) == lastDiaValue2){
			dia1 = (parseInt(dia1)-10).toString()
			dia2 = dia1;
		}
		
	} else {
		/*if(parseInt(anio1) > parseInt(anio2)){
			anio1 = secValue;
			anio2 = lastValue;
		}
		
		if(parseInt(mes1) >= parseInt(mes2)){
			mes1 = secMesValue;
			mes2 = lastMesValue2;
		}*/
		if(parseInt(anio1) > parseInt(anio2) && parseInt(mes1) >= parseInt(mes2) ){
			anio1 = secValue;
			anio2 = secValue;
			mes1 = mes2-1;
			mes2 = mes2;
		}
		else if(parseInt(anio1) > parseInt(anio2) && parseInt(mes1) <= parseInt(mes2) ){
			anio1 = secValue;
			anio2 = secValue;
			mes1 = mes2-1;
			mes2 = mes2;
		}
		
		if(parseInt(anio1) == parseInt(anio2) && parseInt(mes1) >= parseInt(mes2)){
			mes1 = mes2-1;
			mes2 = mes2;
			if(mes1==0){
				mes1 ="1";
				mes2="2";
			}
		}
		
		if(parseInt(anio1) < parseInt(anio2) && parseInt(mes1) >= parseInt(mes2)){
			mes1 = mes1;
			mes2 = mes2;
		}
		dia2 ="01";
	}
	var inicio = new Date(anio1,mes1-1,dia1); 
	var fin = new Date(anio2,mes2-1,dia2);
	
										 
									 
	//
	addaniotimes(data	,"#"+compdiario1+"_sel_anio",inicio);
	addmesestimes(data	,"#"+compdiario1+"_sel_mes",inicio);
	adddiastimes(data	,"#"+compdiario1+"_sel_dia",inicio);
	addaniotimes(data	,"#"+compdiario2+"_sel_anio",fin);
	addmesestimes(data	,"#"+compdiario2+"_sel_mes",fin);
	adddiastimes(data	,"#"+compdiario2+"_sel_dia",fin);
	//updates plotes.
}


function updatedataplottimes(plot1,plot2,compdiario1,compdiario2,data,slider,tipo) {
	var serieP1 = [[]];  
	var serieP2 = [[]];
	var counta = 0;
	var countb = 0;
	var anio1,mes1,dia1,trimestre1,semestre1,anio1; 
	var anio2,mes2,dia2,trimestre2,semestre2,anio2;
	/*Componente 1*/
	if ($("#"+compdiario1+"_sel_anio").length) {
		anio1 	= $("#"+compdiario1+"_sel_anio").val();	
	}
	if ($("#"+compdiario1+"_sel_mes").length) {
		mes1 	= ("0"+$("#"+compdiario1+"_sel_mes").val()).slice(-2);	
	} 
	if ($("#"+compdiario1+"_sel_dia").length) {
		dia1 	= ("0"+$("#"+compdiario1+"_sel_dia").val()).slice(-2);	
	} else {
		dia1 ="01";
	}
	if ($("#"+compdiario1+"_sel_trimestre").length) {
		trimestre1 	= ("0"+$("#"+compdiario1+"_sel_trimestre").val()).slice(-2);	
	} 
	if ($("#"+compdiario1+"_sel_semestre").length) {
		semestre1 	= ("0"+$("#"+compdiario1+"_sel_semestre").val()).slice(-2);	
	} 
	
	/*Componente 2*/
	if ($("#"+compdiario2+"_sel_anio").length) {
		anio2 	= ("0"+$("#"+compdiario2+"_sel_anio").val()).slice(-4);	
	} else {
		anio2 = "2010";
	}
	if ($("#"+compdiario2+"_sel_mes").length) {
		mes2 	= ("0"+$("#"+compdiario2+"_sel_mes").val()).slice(-2);	
	} 
	if ($("#"+compdiario2+"_sel_dia").length) {
		dia2 	= ("0"+$("#"+compdiario2+"_sel_dia").val()).slice(-2);	
	} else {
		dia2 ="01";
	}
	if ($("#"+compdiario2+"_sel_trimestre").length) {
		trimestre2 	= ("0"+$("#"+compdiario2+"_sel_trimestre").val()).slice(-2);	
	} 
	if ($("#"+compdiario2+"_sel_semestre").length) {
		semestre2 	= ("0"+$("#"+compdiario2+"_sel_semestre").val()).slice(-2);	
	} 
	var mts1 = (semestre1==null?(trimestre1==null?(mes1):trimestre1):semestre1)==null?"01":(semestre1==null?(trimestre1==null?(mes1):trimestre1):semestre1);
	var mts2 = (semestre2==null?(trimestre2==null?(mes2):trimestre2):semestre2)==null?"01":(semestre2==null?(trimestre2==null?(mes2):trimestre2):semestre2);
	/*****   *****/
	var fec_p1 = anio1 + "-" + "" + mts1 + "-" +  dia1;
	var fec_p2 = anio2 + "-" + "" + mts2 + "-" +  dia2;
	var seriename;
	if (data["SerieValores"]==null){
		seriename="SerieCantidad";
	} else { seriename="SerieValores"; }
	
	for (var i = 0; i < data[seriename].length; i++) {
		 var fec001 = data[seriename][i][0];
		 var hora 	= parseInt(data[seriename][i][1]);
		 var valor 	= data[seriename][i][2];
		 if (fec001==fec_p1) {
			 serieP1[counta]=[ hora , valor ];
			 counta ++;
		 }
		 if (fec001==fec_p2) {
			 serieP2[countb]= [ hora,valor ] ;
			 countb++;
		 }
	}//for
	plot1.series[0].data = serieP1;
	plot1.series[1].data = serieP2;
	plot1.legend.labels= [formatterLabel(tipo,fec_p1),formatterLabel(tipo,fec_p2)];
	//plot1.resetAxesScale();
	plot1.replot();

	plot2.series[0].data = serieP1;
	plot2.series[1].data = serieP2;
	//plot2.resetAxesScale();
	plot2.replot();
}

function updatedataplottimesranges(plot1,plot2,compdiario1,compdiario2,data,slider,tipo) {

	var serieP1 = [[]];  
	var serieP2 = [[]];
	var counta = 0;
	var countb = 0;
	var anio1,mes1,dia1,trimestre1,semestre1,anio1; 
	var anio2,mes2,dia2,trimestre2,semestre2,anio2;
	/*Componente 1*/
	if ($("#"+compdiario1+"_sel_anio").length) {
		anio1 	= $("#"+compdiario1+"_sel_anio").val();	
	}
	if ($("#"+compdiario1+"_sel_mes").length) {
		mes1 	= ("0"+$("#"+compdiario1+"_sel_mes").val()).slice(-2);	
	} 
	if ($("#"+compdiario1+"_sel_dia").length) {
		dia1 	= ("0"+$("#"+compdiario1+"_sel_dia").val()).slice(-2);	
	} else {
		dia1 ="01";
	}
	if ($("#"+compdiario1+"_sel_trimestre").length) {
		trimestre1 	= ("0"+$("#"+compdiario1+"_sel_trimestre").val()).slice(-2);	
	} 
	if ($("#"+compdiario1+"_sel_semestre").length) {
		semestre1 	= ("0"+$("#"+compdiario1+"_sel_semestre").val()).slice(-2);	
	} 
	
	/*Componente 2*/
	if ($("#"+compdiario2+"_sel_anio").length) {
		anio2 	= ("0"+$("#"+compdiario2+"_sel_anio").val()).slice(-4);	
	} else {
		anio2 = "2010";
	}
	if ($("#"+compdiario2+"_sel_mes").length) {
		mes2 	= ("0"+$("#"+compdiario2+"_sel_mes").val()).slice(-2);	
	} 
	if ($("#"+compdiario2+"_sel_dia").length) {
		dia2 	= ("0"+$("#"+compdiario2+"_sel_dia").val()).slice(-2);	
	} else {
		dia2 ="01";
	}
	if ($("#"+compdiario2+"_sel_trimestre").length) {
		trimestre2 	= ("0"+$("#"+compdiario2+"_sel_trimestre").val()).slice(-2);	
	} 
	if ($("#"+compdiario2+"_sel_semestre").length) {
		semestre2 	= ("0"+$("#"+compdiario2+"_sel_semestre").val()).slice(-2);	
	} 
	var mts1 = (semestre1==null?(trimestre1==null?(mes1):trimestre1):semestre1)==null?"01":(semestre1==null?(trimestre1==null?(mes1):trimestre1):semestre1);
	var mts2 = (semestre2==null?(trimestre2==null?(mes2):trimestre2):semestre2)==null?"01":(semestre2==null?(trimestre2==null?(mes2):trimestre2):semestre2);
	/*****   *****/
	var fec_p1 = new Date(anio1 , (parseInt(mts1)-1)  , dia1);
	var fec_p2 = new Date(anio2 , (parseInt(mts2)-1) ,  dia2);
	
	var serieValores = data["SerieValores"];
	
	if (serieValores == undefined) { 
		serieValores = data["SerieValoresPorcentaje"];
	}
	
	serieValores = datosValor(serieValores);
	var serievaloresseleccionada=[[]];
	
	if (serieValores!=null) {
		var j=0;
		for (var i=0;i<serieValores.length; i++) {
			var aux = serieValores[i][0] 
			var fec001 = aux.split("-");
			fec001 = new Date(fec001[0],(parseInt(fec001[1])-1),fec001[2])
			var valor 	= serieValores[i][1];
			 if (fec_p1<=fec001 && fec_p2>=fec001) {
				 serievaloresseleccionada[j]=[fec001,valor];
				 j++;
			 }
		}
	}
	
	var serieCantidad = data["SerieCantidad"];
	
	if (serieCantidad == undefined) { 
		serieCantidad = data["SerieCantidadPorcentaje"];
	}
	
	serieCantidad = datosValor(serieCantidad);
	var seriecantidadsseleccionada=[[]];
	if (serieCantidad!=null) {
		var j=0;
		for (var i=0;i<serieCantidad.length; i++) {
			var aux = serieCantidad[i][0] 
			var fec001 = aux.split("-");
			fec001 = new Date(fec001[0],(parseInt(fec001[1])-1),fec001[2])
			var cantidad 	= serieCantidad[i][1];
			 if (fec_p1<=fec001 && fec_p2>=fec001) {
				 seriecantidadsseleccionada[j]=[fec001,cantidad];
				 j++;
			 }
		}
	}
	$("#"+slider).dateRangeSlider("values", fec_p1 , fec_p2 );
	//actualiza el slider
	if (slider.length) {
		var datesinit = {
				values : {
						min: fec_p1,
						max: fec_p2,
				},
				updateselects_no: true,
			}
			$("#"+slider).trigger("valuesChanged",datesinit);
	}
}

function changedatesselectedstrimestralestimes(comptrimestral1,comptrimestral2,data,slider) {

	var anio1,mes1,dia1,trimestre1,semestre1,anio1; 
	var anio2,mes2,dia2,trimestre2,semestre2,anio2;
	
	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(comptrimestral1+"_sel_anio");
	var secValue = selAnio1.options[1].value;
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;
	
	//Primer y ultimo valor de trimestre
	var selQuarter1 = document.getElementById(comptrimestral1+"_sel_trimestre");
	var secTriValue = selQuarter1.options[1].value;
	var lastTriValue = selQuarter1.options[selQuarter1.options.length - 1].value;
	
	if ($("#"+comptrimestral1+"_sel_anio").length) {
		anio1 	= $("#"+comptrimestral1+"_sel_anio").val();
		if(anio1 == ""){
			anio1 = secValue;
		}
	}
	
	if ($("#"+comptrimestral1+"_sel_trimestre").length) {
		var valor = $("#"+comptrimestral1+"_sel_trimestre").val();
		if(valor == ""){
			valor 	= secTriValue;
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
		}
	} else  {
		mes1="0" 
	}
	
	dia1 ="01";
	
	if ($("#"+comptrimestral2+"_sel_anio").length) {
		anio2 	= $("#"+comptrimestral2+"_sel_anio").val();
		if(anio2 == ""){
			anio2 	= lastValue;
		}
	}
	
	if ($("#"+comptrimestral2+"_sel_trimestre").length) {
		valor2 = $("#"+comptrimestral2+"_sel_trimestre").val();
		
		if(valor2 == ""){
			valor2 = lastTriValue;
		}
		
		if(parseInt(anio1) > parseInt(anio2)){
			anio1 = secValue;
			anio2 = lastValue;
		}
		
		if(parseInt(anio1) == parseInt(anio2) && parseInt(valor) >= parseInt(valor2)){
			mes1="0";
			valor2 = lastTriValue;
		}
		
		switch(valor2) {
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
	
	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(compsemestral1+"_sel_anio");
	var secValue = selAnio1.options[1].value;
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;
	
	//Primer y ultimo valor de año
	var selSem1 = document.getElementById(compsemestral1+"_sel_semestre");
	
	if(selSem1 != null){
		var secSemValue = selSem1.options[1].value;
		var lastSemValue = selSem1.options[selSem1.options.length - 1].value;
	}

	if ($("#"+compsemestral1+"_sel_anio").length) {
		anio1 	= $("#"+compsemestral1+"_sel_anio").val();
		if(anio1 == ""){
			anio1 = secValue;
		}
	}
	
	if ($("#"+compsemestral1+"_sel_semestre").length) {
		var valor = $("#"+compsemestral1+"_sel_semestre").val();
		
		if(valor == ""){
			valor = secSemValue;
		}
		
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
		
		if(anio2 == ""){
			anio2 	= lastValue;
		}
	} else {
		anio2 = "2010";
	}

	if ($("#"+compsemestral2+"_sel_semestre").length) {
		valor2 = $("#"+compsemestral2+"_sel_semestre").val();
		
		if(valor2 == ""){
			valor2 = lastSemValue;
		}
		
		if(parseInt(anio1) > parseInt(anio2)){
			anio1 = secValue;
			anio2 = lastValue;
		}
		
		/*if(parseInt(valor) > parseInt(valor2)){
			mes1 = "0";
			valor2 = lastSemValue;
		}*/
		
		if(parseInt(anio1) == parseInt(anio2) && parseInt(valor) == parseInt(valor2)){
			mes1 = "0";
			valor2 = lastSemValue;
		}
		
		switch(valor2) {
			case "1":
				mes2="0";
				break;
			case "7":
				mes2="6";
				break;
		}
	} else {
		if(parseInt(anio1) >= parseInt(anio2)){
			anio1 = secValue;
			anio2 = lastValue;
		}
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


/**
 * Componentes Antiguos basados en los datos 
 **/

function addanio(data,selanio,selected) {
	if ($(selanio).length) {
		var selectanio = $(selanio);
		selectanio.empty();
		var option = $('<option/>').val("").text("Año").attr('disabled','disabled');
		option.appendTo(selectanio);
		
		var fechas =  data["Ticks"];
		var years = new Set();; 
		for (var i = 0; i < fechas.length; i++) {
		  years.add(new Date(fechas[i]).getFullYear());
		}
		if (years.length>0){
			for (var i=0 ; i<years.length; i++) {
				option = $('<option/>').val(years[i]).text(years[i]);
				option.appendTo(selectanio);
			}//for
		}//of
		selectanio.val(new Date(selected).getFullYear());
	}
}

function getDateFromSlider(fechaSlider,tipo) {
	var fechaRes;
	var mes 	= fechaSlider.getMonth();
	var anio 	= fechaSlider.getFullYear();
	var dia 	= "01";
	switch (tipo) {
		case 1:
			fechaRes = fechaSlider;
			break;
		case 2:
			fechaRes = fechaSlider;
			break;
		case 3:
			if (mes>=0 && mes<=2) mes = 0;
			if (mes>=3 && mes<=5) mes = 3;
			if (mes>=6 && mes<=8) mes = 6;
			if (mes>=9 && mes<=11)mes = 9;
			fechaRes = new Date(anio,mes,dia);
			break;
		case 4:
			if (mes>=0 && mes<=5) mes = 0;
			if (mes>=6 && mes<=11) mes = 6;
			fechaRes = new Date(anio,mes,dia);
			break;
		case 5:
			mes = "01";
			fechaRes = new Date(anio,mes,dia);;
			break;
	}
	return fechaRes;
}

function resizePlot(zoomplot,miniplot,idMini,idDivSlider){
	zoomplot.replot({ resetAxes: false });
	/*get positions super plot*/
	var s_left 		= zoomplot._defaultGridPadding.left;
	var s_right 	= zoomplot._defaultGridPadding.right;
	var left 		= zoomplot._gridPadding.left;
	var right		= zoomplot._gridPadding.right;
	var width 		= zoomplot._plotDimensions.width;
	var style =         "width:".concat((width-left)).concat("px !important; ")
					.concat("left:").concat(0).concat("px !important;")
					.concat("right:").concat(right-s_right).concat("px !important;")
					.concat("height:").concat(55).concat("px !important;")
					;
						
	;
	$("#"+idMini)[0].setAttribute("style",style);
	miniplot.replot({ resetAxes: false });
	var styleSlider = "width:".concat((width-left-right+s_left+s_right+24)).concat("px !important; ")
	  .concat("margin-top:").concat(-8).concat("px !important;")
	  .concat("left:").concat(left-s_left-12).concat("px !important;")
	  .concat("right:").concat(right).concat("px !important;")
	  .concat("max-width:").concat("100% !important;")
	  .concat("position: relative;")
	;
	$("#"+idDivSlider)[0].setAttribute("style",styleSlider);
	$("#"+idDivSlider).dateRangeSlider('resize');
}

function resizePlotSingle(zoomplot,miniplot,idMini,idDivSlider){
	zoomplot.replot({ resetAxes: false });
	/*get positions super plot*/
	var s_left 		= zoomplot._defaultGridPadding.left;
	var s_right 	= zoomplot._defaultGridPadding.right;
	var left 		= zoomplot._gridPadding.left;
	var right		= zoomplot._gridPadding.right;
	var width 		= zoomplot._plotDimensions.width;
	var style =         "width:".concat((width)).concat("px !important; ")
					.concat("left:").concat(0).concat("px !important;")
					.concat("right:").concat(right-s_right).concat("px !important;")
					.concat("height:").concat(55).concat("px !important;")
					;
						
	;
	$("#"+idMini)[0].setAttribute("style",style);
	miniplot.replot({ resetAxes: false });
	var styleSlider = "width:".concat((width-left-right+s_left+s_right+24)).concat("px !important; ")
	  .concat("margin-top:").concat(-8).concat("px !important;")
	  .concat("left:").concat(left-s_left-12).concat("px !important;")
	  .concat("right:").concat(right).concat("px !important;")
	  .concat("max-width:").concat("100% !important;")
	  .concat("position: relative;")
	;
	$("#"+idDivSlider)[0].setAttribute("style",styleSlider);
	$("#"+idDivSlider).dateRangeSlider('resize');
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

var isMobile = {
	    Android: function() {
	        return navigator.userAgent.match(/Android/i);
	    },
	    BlackBerry: function() {
	        return navigator.userAgent.match(/BlackBerry/i);
	    },
	    iOS: function() {
	        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
	    },
	    Opera: function() {
	        return navigator.userAgent.match(/Opera Mini/i);
	    },
	    Windows: function() {
	        return navigator.userAgent.match(/IEMobile/i);
	    },
	    any: function() {
	        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
	    }
	};


function parsedata(serieValores){
	
	let serieValoresF = [];
 	for (var i = 0; i < serieValores.length; i++) {
 	    var valor2 = parseFloat(serieValores[i][1].toFixed(2));
 	    var fecha = (serieValores[i][0]);
 	    serieValoresF.push([fecha, valor2]);
 	}
 	return serieValoresF;
}
/**
 * COMPORTAMIENTO HISTORICO CANJE
 **/
function createSliderComportamientoCanje(divchartzoomslider,periodo, compPeriodo1,compPeriodo2,tipodeplaza,ciudad,label,errormessage,almacen,onetime){
	
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	var period = parseInt(periodo,10);
	//Rangos del slider de control
	var first;
	var minDays;
	var updateperiodo;
	var addday=0;
	switch (period) {
		case 1:		minDays = 1;
					addday = 10;
					first = true;
					updateperiodo = true;
			break;
		case 2: 	minDays = 60;
					minMonth = 1;
					first = false;
					updateperiodo = true;
			break;
		case 3: 	minDays = 90;
					minMonth = 3;
					first = true;
					updateperiodo = true;
			break;  
		case 4: 	minMonth = 6;
					first = true;
					minDays = 180;
					updateperiodo = true;
		break;
		case 5: 	minMonth = 12;
					first = false;
					updateperiodo = true;
					minDays = 360;
			break;
	};
	
	try {
		data =  RestCompensacionComportamientoServices.getComportamientoCanje({'tipodeplaza':tipodeplaza,'ciudad':ciudad,'periodo':period});
		
		$("#"+divchartzoomslider).empty();
		$("#"+divchartzoomslider)[0].setAttribute("class","superzoom");
		
		if (label==null || label =="undefined") label = "Todos";
		data["Title"] = data["Title"] + " - " + label;
		
		vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecStart = completarFechaStart(vStorageFecStart,data["Ticks"]);
		vStorageFecEnd = completarFechaEnd(vStorageFecEnd,data["Ticks"]);
		
		ticks = data["Ticks"];
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		dateend = ticks[ticks.length - 1];
		dateend = dateend.split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * ( minDays +addday));
			fromtmp = new Date(fromtmp);
			vStorageFecStart = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
			from = vStorageFecStart;
		} 
		from2=vStorageFecStart;
		startvaluescomponentstimes (vStorageFecStart,vStorageFecEnd,compPeriodo1,compPeriodo2,data);

		divchartmax = document.createElement('div');
		
		divchartmax.id=divchartzoomslider+"_chartMax";
		divchartmax.style="chartcustom"
		
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		
		//Función que crea la gráfica de acuerdo al periodo
		targetPlot = createplotmaxgeneralCanje (divchartmax.id,	data, period);
		
		xmin 		= targetPlot.axes.xaxis.min;
		xmax 		= targetPlot.axes.xaxis.max;
		s_left 		= targetPlot._defaultGridPadding.left;
		s_right 	= targetPlot._defaultGridPadding.right;
		left 		= targetPlot._gridPadding.left;
		right		= targetPlot._gridPadding.right;
		width 		= targetPlot._plotDimensions.width;
		
		idMini = divchartzoomslider+"_divmini";
		innerDivMini = document.createElement('div');
		innerDivMini.id  = idMini;
		style =  "width:".concat((width-left)).concat("px !important; ")
					.concat("left:").concat(0).concat("px !important;")
					.concat("right:").concat(right-s_right).concat("px !important;")
					.concat("height:").concat(55).concat("px !important;");
		
		innerDivMini.setAttribute("style",style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		
		controllerPlot = createplotmingeneralCanje (idMini, data, period);
		
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider+"_slider";
		
		innerDivSlider = document.createElement('div');
		innerDivSlider.id=idDivSlider;
		
		styleSlider = "width:".concat((width-left-right+s_left+s_right+24)).concat("px !important; ")
							  .concat("margin-top:").concat(-8).concat("px !important;")
							  .concat("left:").concat(left-s_left-12).concat("px !important;")
							  .concat("right:").concat(right).concat("px !important;")
							  .concat("position: relative;");
		
		innerDivSlider.setAttribute("style",styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		
		dateSlider=null;
		if (period==1) {
				dateSlider = $("#"+idDivSlider).dateRangeSlider({
					range:{
		        	    min: {days: minDays},
			  		},
			  		bounds: {
			  			min: datestart,
			  			max: dateend
			  		},
			  		defaultValues: {
			  			min: from2,
			  			max: vStorageFecEnd
			  		},
		        });
		} else {
			dateSlider = $("#"+idDivSlider).dateRangeSlider({
					range:{
		        	    min: {months: minMonth},
			  		},
			  		bounds: {
			  			min: datestart,
			  			max: dateend
			  		},
			  		defaultValues: {
			  			min: from2,
			  			max: vStorageFecEnd
			  		},
			 	});
		}

		//trigger first load
		var datesinit = {
				label: dateSlider,
				values : {
					min: from2,
					max: vStorageFecEnd,
				},
				first: first,
				updateperiodo: updateperiodo
		}
		
		//trigger first load
		var datesinit2 = {
				label: dateSlider,
				values : {
					min: from2,
					max: vStorageFecEnd,
				},
				first: first,
				updateperiodo: updateperiodo
		}
		
		$("#"+idDivSlider).unbind();
		$("#"+idDivSlider).bind("valuesChanged", function(evt,initializedate){
			valuesPlotChangedTimes(initializedate,controllerPlot,targetPlot,compPeriodo1,compPeriodo2,data,period);
			//if (dateSlider.first==null || dateSlider.first==false) {
			updatevaluescomponentstimesfromslider(initializedate,compPeriodo1,compPeriodo2,data,period);
			//}	else {
			//	updatevaluescomponentstimesfromslider(dateSlider,compPeriodo1,compPeriodo2,data,period);
			//}
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		 var ua = window.navigator.userAgent;
		    var msie = ua.indexOf("MSIE ");
		 if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
		    {
				var bodyheight2 = document.getElementById('vcompcanje_chart_chartMax').clientHeight;
				window.scrollTo(0, bodyheight2);
		    }else{
		    	var bodyheight2 = document.getElementById('vcompcanje_chart_chartMax').clientHeight;
				window.scrollTo(0, bodyheight2);
		    }
		/**
		 * 
		 */
		//Retira eventos y actualiza datos en años
		$("#"+compPeriodo1+"_sel_anio").unbind();
		$("#"+compPeriodo1+"_sel_anio").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 3: 	changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 4: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			};		
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_anio").unbind();
		$("#"+compPeriodo2+"_sel_anio").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 3: 	changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 4: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			};
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en mes
		$("#"+compPeriodo1+"_sel_mes").unbind();
		$("#"+compPeriodo1+"_sel_mes").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_mes").unbind();
		$("#"+compPeriodo2+"_sel_mes").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en dia
		$("#"+compPeriodo1+"_sel_dia").unbind();
		$("#"+compPeriodo1+"_sel_dia").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_dia").unbind();
		$("#"+compPeriodo2+"_sel_dia").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en Semestre
		$("#"+compPeriodo1+"_sel_semestre").unbind();
		$("#"+compPeriodo1+"_sel_semestre").change(function(evt) {
			if(period == 4){
				changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_semestre").unbind();
		$("#"+compPeriodo2+"_sel_semestre").change(function(evt) {
			if(period == 4){
				changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en trimestre
		$("#"+compPeriodo1+"_sel_trimestre").unbind();
		$("#"+compPeriodo1+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}			
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_trimestre").unbind();
		$("#"+compPeriodo2+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
    	setTimeout(() => {
			$("#"+idDivSlider).trigger("valuesChanged",datesinit);
			$("#"+idDivSlider).trigger("valuesChanged",datesinit2);
			scroll();
    	},2000);	
		
	} catch (err) {
		$("[id*='"+errormessage+"'").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

/**
 * COMPORTAMIENTO HISTORICO DEVOLUCION 
 **/
function createSliderComportamientoDevolucion(divchartzoomslider,periodo,compPeriodo1,compPeriodo2,tipodeplaza,ciudad,label,errormessage,almacen,onetime){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	var period = parseInt(periodo,10);
	
	//Rangos del slider de control
	var first;
	var minDays;
	var updateperiodo;
	var addday=0;
	switch (period) {
		case 1:		minDays = 1;
					addday = 10;
					first = true;
					updateperiodo = true;
			break;
		case 2: 	minDays = 60;
					minMonth = 1;
					first = false;
					updateperiodo = true;
			break;
		case 3: 	minDays = 90;
					minMonth = 3;
					first = true;
					updateperiodo = true;
			break;  
		case 4: 	minMonth = 6;
					first = true;
					minDays = 180;
					updateperiodo = true;
		break;
		case 5: 	minMonth = 12;
					first = false;
					updateperiodo = true;
					minDays = 360;
			break;
	};
	
	try {
		data =  RestCompensacionComportamientoServices.getComportamientoDevolucion({'tipodeplaza':tipodeplaza,'ciudad':ciudad,'periodo':period});
		$("#"+divchartzoomslider).empty();
		$("#"+divchartzoomslider)[0].setAttribute("class","superzoom");
		if (label==null || label =="undefined") label = "Todos";
		data["Title"] = data["Title"] + " - " + label;
		
		vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecStart = completarFechaStart(vStorageFecStart,data["Ticks"]);
		vStorageFecEnd = completarFechaEnd(vStorageFecEnd,data["Ticks"]);

		ticks = data["Ticks"];
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		dateend = ticks[ticks.length - 1];
		dateend = dateend.split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * ( minDays +addday));
			fromtmp = new Date(fromtmp);
			vStorageFecStart = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
			from = vStorageFecStart;
		} 
		from2=vStorageFecStart;
		startvaluescomponentstimes (vStorageFecStart,vStorageFecEnd,compPeriodo1,compPeriodo2,data);
		
		divchartmax = document.createElement('div');
		divchartmax.id=divchartzoomslider+"_chartMax";
		divchartmax.style="chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);

		//Función que crea la gráfica de acuerdo al periodo
		targetPlot 		= createplotmaxgeneralCanje (divchartmax.id, data, period);
		
		xmin = targetPlot.axes.xaxis.min;
		xmax = targetPlot.axes.xaxis.max;
		
		s_left 		= targetPlot._defaultGridPadding.left;
		s_right 	= targetPlot._defaultGridPadding.right;
		left 		= targetPlot._gridPadding.left;
		right		= targetPlot._gridPadding.right;
		width 		= targetPlot._plotDimensions.width;
		
		idMini = divchartzoomslider+"_divmini";
		innerDivMini = document.createElement('div');
		innerDivMini.id =idMini;
		style =         "width:".concat((width-left)).concat("px !important; ")
					.concat("left:").concat(0).concat("px !important;")
					.concat("right:").concat(right-s_right).concat("px !important;")
					.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style",style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		
		//Crea la gráfica mini de control de acuerdo al periodo
		controllerPlot 	= createplotmingeneralCanje (idMini, data, period);
		
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider+"_slider";
		innerDivSlider = document.createElement('div');
		innerDivSlider.id=idDivSlider;
		styleSlider = "width:".concat((width-left-right+s_left+s_right+24)).concat("px !important; ")
								  .concat("margin-top:").concat(-8).concat("px !important;")
								  .concat("left:").concat(left-s_left-12).concat("px !important;")
								  .concat("right:").concat(right).concat("px !important;")
								  .concat("position: relative;")
		;
		innerDivSlider.setAttribute("style",styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		
		dateSlider=null;
		if (period==1) {
				dateSlider = $("#"+idDivSlider).dateRangeSlider({
					range:{
		        	    min: {days: minDays},
			  		},
			  		bounds: {
			  			min: datestart,
			  			max: dateend
			  		},
			  		defaultValues: {
			  			min: from2,
			  			max: vStorageFecEnd
			  		},
		        });
		} else {
			dateSlider = $("#"+idDivSlider).dateRangeSlider({
					range:{
		        	    min: {months: minMonth},
			  		},
			  		bounds: {
			  			min: datestart,
			  			max: dateend
			  		},
			  		defaultValues: {
			  			min: from2,
			  			max: vStorageFecEnd
			  		},
			 	});
		}
		
		//trigger first load
		var datesinit = {
				label: dateSlider,
				values : {
					min: from2,
					max: vStorageFecEnd,
				},
				first: first,
				updateperiodo: updateperiodo
		}
		
		//trigger first load
		var datesinit2 = {
				label: dateSlider,
				values : {
					min: from2,
					max: vStorageFecEnd,
				},
				first: first,
				updateperiodo: updateperiodo
		}
		
		$("#"+idDivSlider).unbind();
		$("#"+idDivSlider).bind("valuesChanged", function(evt,dateSlider){
			valuesPlotChangedTimes(dateSlider,controllerPlot,targetPlot,compPeriodo1,compPeriodo2,data,period);
			//if (dateSlider.first==null || dateSlider.first==false) {
				updatevaluescomponentstimesfromslider(dateSlider,compPeriodo1,compPeriodo2,data,period);
				savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
			//}
		});
		
		var ua = window.navigator.userAgent;
		    var msie = ua.indexOf("MSIE ");
		 if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
		    {
			 var bodyheight = document.getElementById('vcompdevol_chart').clientHeight;
			 try 
			 { 
				 var bodyheight2 = document.getElementById('vcompcanje_chart_chartMax').clientHeight;
				
			 }
			 catch(err){
				 bodyheight2 = 0;
			 }
			 if (bodyheight2 > 0 ) {
				 bodyheight2 = bodyheight2 + 200;
			 }
			
			 var bodyheight2 = bodyheight2 + 50;
			 var final =bodyheight+bodyheight2;
				window.scrollTo(0,final );
				
		    }else{
		    	
		    	var bodyheight = document.getElementById('vcompdevol_chart').clientHeight;
				 try 
				 { 
					 var bodyheight2 = document.getElementById('vcompcanje_chart_chartMax').clientHeight;
					
				 }
				 catch(err){
					 bodyheight2 = 0;
				 }
				 if (bodyheight2 > 0 ) {
					 bodyheight2 = bodyheight2 + 200;
				 }
				
				 var bodyheight2 = bodyheight2 + 50;
				 var final =bodyheight+bodyheight2;
					window.scrollTo(0,final );
		    }
		  
		/**
		 * 
		 */
		//Retira eventos y actualiza datos en años
		$("#"+compPeriodo1+"_sel_anio").unbind();
		$("#"+compPeriodo1+"_sel_anio").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 3: 	changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 4: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			};		
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_anio").unbind();
		$("#"+compPeriodo2+"_sel_anio").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 3: 	changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 4: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			};
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en mes
		$("#"+compPeriodo1+"_sel_mes").unbind();
		$("#"+compPeriodo1+"_sel_mes").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_mes").unbind();
		$("#"+compPeriodo2+"_sel_mes").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en dia
		$("#"+compPeriodo1+"_sel_dia").unbind();
		$("#"+compPeriodo1+"_sel_dia").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_dia").unbind();
		$("#"+compPeriodo2+"_sel_dia").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
		});
		
		//Retira eventos y actualiza datos en Semestre
		$("#"+compPeriodo1+"_sel_semestre").unbind();
		$("#"+compPeriodo1+"_sel_semestre").change(function(evt) {
			if(period == 4){
				changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_semestre").unbind();
		$("#"+compPeriodo2+"_sel_semestre").change(function(evt) {
			if(period == 4){
				changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en trimestre
		$("#"+compPeriodo1+"_sel_trimestre").unbind();
		$("#"+compPeriodo1+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}			
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_trimestre").unbind();
		$("#"+compPeriodo2+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+idDivSlider).trigger("valuesChanged",datesinit);
		$("#"+idDivSlider).trigger("valuesChanged",datesinit2);
	
	} catch (err) {
		console.log(err);
		$("[id*='"+errormessage+"'").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
}

/**
 * Funcion que gráfica Target comportamiento Canje/Devolucion
 * @param name
 * @param data
 * @param tipo
 * @returns
 */
function createplotmaxgeneralCanje(name,data,tipo) {
 	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
 	ticks 			= data["Ticks"];
 	serieValores 	= datosValor(data["SerieValores"]);
 	//serieValores = parsedata(serieValores);
 	serieCantidad	= datosValor(data["SerieCantidad"]);
  	minValor		= data["MinValor"];
 	minCantidad		= data["MinCantidad"];
 	maxValor		= data["MaxValor"];
 	maxCantidad		= data["MaxCantidad"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	var plot2 = $.jqplot(name, [serieValores,serieCantidad] , {
        title:title,
        textColor:  "#003E6C",
        seriesColors:['#850024', '#006fb9'],
		seriesDefaults:{ 
			renderer:$.jqplot.BlockRenderer,
			showMarker: true, 
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
			},
            animation: {
                show: false
            },
        },
        legend: {
            renderer: $.jqplot.EnhancedLegendRenderer,
            show: true,
            showLabels: true,
            label:'',
            showSwatches:true,
            placement: "insideGrid",
            location: "ne",
            fontSize: '11px',
            pad:5,
            rowSpacing: "0px",
            rendererOptions: {
                seriesToggle: "true",
                seriesToggleReplot: {
                	resetAxes: false
                }
            }
        },
        series:[
			{	color: '#850024', 
				lineWidth: 0.5, 
				shadow: true, 
				label:'Valor',
				markerOptions: { size:4, style:'filledCircle' },
				rendererOptions: {
					smooth: false,
		               css: {
		            	   background:'#850024'
		               }
		        }
			},
		    {	yaxis: 'y2axis', 
				lineWidth: 0.5, 
				shadow: true, 
				label:'Cantidad' , 
				markerOptions: { 
						size:4, 
						style:'filledCircle' 
				},
				rendererOptions: {
					smooth: false,
					css: {
			        	background:'#006fb9'
			        }
			    }
			},
	    ],
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : true,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		left: "0",
        },
	    cursor:{
	    	show: true,
	    	zoom:true,
	    	showTooltip:false,
	    	dblClickReset: false,
	    },
	    highlighter : {
    		show : true,
    		sizeAdjust : 1,
    		tooltipOffset : 9,
    		useYTickMarks : true,
    		useXTickMarks : true,
    		currentNeighbor : null,
    		showMarker : true,
    		lineWidthAdjust : 1,
    		showTooltip : true,
    		tooltipLocation : 'e',
    		fadeTooltip : false,
    		tooltipAxes : '(x , y)',
    		tooltipSeparator : ', ',
    		useAxesFormatters : true,
	    },
	    axesDefaults: { 
        	showTickMarks:true,
        	useSeriesColor:true, 
        	rendererOptions: {
        		alignTicks: true,
        	} 
	    },	
        axes:{
            xaxis:{
            	numberTicks: getTicks(minX,maxX,tipo),
            	min:minX,
            	max:maxX,
            	label:'Fecha',
            	showLabel: false,
            	pad: 1,
            	tickInterval: getTickInterval(tipo),
            	renderer:$.jqplot.DateAxisRenderer,
				rendererOptions:{ 
					tickRenderer:$.jqplot.CanvasAxisTickRenderer ,
				}, 
                tickOptions: {
                	showTicks: true, 
                	angle: -45,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	fontFamily:'"Roboto", sans-serif',
	            	fontSize: '8pt',
	            	show:true,
	            	size:4,
	            	markSize:4,
	            	formatString: formatter(tipo),
                }
            },
            yaxis:{
            	min:minValor,
            	max:maxValor,
            	//label:'<div><div>Valor Compensado</div><div style="color: gray;">(Millones COP)<div></div>',
            	label:'<div><div>Valor Compensado</div><div style="color: gray; width: 120px !important">(Miles de Millones COP)<div></div>',
            	showLabel:true,
            	textColor: "#850024",
            	show:true,
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: true,
				}, 
                tickOptions: {
                	showTicks: true, 
                	angle: 0,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	fontSize: "8pt",
	            	size:4,
	            	markSize:6,
	            	show:true,
	            	pad: 1,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#850024",
	        		formatString: "%'.2f",
                }
            },
            y2axis:{
            	min:minCantidad,
            	max:maxCantidad,
            	showLabel:true,
            	label: '<div style="padding-left: 24px;"><div>Cantidad</div></div>',
            	textColor: "#006fb9",
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: true
				}, 
                tickOptions: {
                	showTicks: true, 
	            	fontSize: "8pt",
                	angle: 0,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	show:true,
	            	size:4,
	            	markSize:6,
	            	pad: 1.2,
	        		textColor: "#006fb9",
	            	fontFamily:'"Roboto", sans-serif',
	        		formatString: "%'.0f",
                },
            },
        },
	});
	return plot2;
}

/**
 * Funcion que gráfica Controller comportamiento Canje/Devolucion
 * @param name
 * @param data
 * @param tipo
 * @returns
 */
function createplotmingeneralCanje(name,data,tipo) {
 	
	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
 	ticks 			= data["Ticks"];
 	serieValores 	= datosValor(data["SerieValores2"]);
 	//serieValores = parsedata(serieValores);
 	serieCantidad	= datosValor(data["SerieCantidad2"]);
  	minValor		= data["MinValor"];
 	minCantidad		= data["MinCantidad"];
 	maxValor		= data["MaxValor"];
 	maxCantidad		= data["MaxCantidad"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	
 	$.jqplot.sprintf.thousandsSeparator = '.';
 	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = '';
	plot2 = $.jqplot(name, [serieValores,serieCantidad] , {
        textColor:  "#003E6C",
        seriesColors:['#850024', '#006fb9'],
		seriesDefaults:{ 
			renderer:$.jqplot.BlockRenderer,
			lineWidth: 0.5,
			showMarker: false,
        	fill: false,
        	fillAndStroke: true,
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
			},
			labels: ['',''],			
            animation: {
                show: false
            },
        },
        
        legend: {
            show: false,
            label:'',
            showLabels: false,
            showSwatches:false,
            placement: "insideGrid",
            location: "ne",
            fontSize: '11px',
            pad:0,
            rowSpacing: "0px",
            rendererOptions: {
                seriesToggle: "false",
                seriesToggleReplot: {
                	resetAxes: false
                }
            }
        },
        
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : true,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		pad: 0,
        },
	    cursor:{
	    	show: false,
	    	zoom:true,
	    	showTooltip:true,
	    	constrainZoomTo: 'x',
	    	dblClickReset: false,
	    	
	    },
	    highlighter : {
    		show : false,
	    },
	    axesDefaults: { 
        	showTickMarks:false,
        	useSeriesColor:false, 
        	
        	rendererOptions: {
        		alignTicks: true,
        	} 
	    },	
        
        axes:{
            xaxis:{
            	numberTicks: getTicks(minX,maxX,tipo),
            	min:minX,
            	max:maxX,
            	label:'Fecha',
            	showLabel: false,
            	pad: 1,
            	renderer:$.jqplot.DateAxisRenderer,
            	tickInterval: getTickInterval(tipo),
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer ,
				}, 
                tickOptions: {
                	showTicks: false, 
                	angle: -45,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	fontFamily:'"Roboto", sans-serif',
	            	fontSize: '8pt',
	            	show:false,
	            	size:0,
	            	markSize:0,
	            	formatString: formatter(tipo),
                }
            },
            yaxis:{
            	min:minValor,
            	max:maxValor,
            	showLabel:false,
            	label:'Cantidad',
            	show:false,
				rendererOptions:{ 
					  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
					  forceTickAt0: true,
					}, 
                tickOptions: {
                	showTicks: false, 
                	angle: 0,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	fontSize: "8pt",
	            	size:0,
	            	markSize:0,
	            	show:false,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#850024",
	        		formatString: "%'.2f",
                }
            },
            y2axis:{
            	min:minCantidad,
            	max:maxCantidad,
            	showLabel:true,
            	label: '<div><div>Cantidad</div><div style="color: gray;">Cheques</div></div>',
            	textColor: "#006fb9",
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: false,
				 forceTickAt100: false,
				}, 
                tickOptions: {
                	showTicks: false, 
	            	fontSize: "8pt",
                	angle: 0,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	show:false,
	            	size:4,
	            	markSize:6,
	            	pad: 1.2,
	        		textColor: "#006fb9",
	            	fontFamily:'"Roboto", sans-serif',
	        		formatString: "%'.0f",
                },
            },
        },
	});
	return plot2;
}
/**
 * DEVOLUCION CON RESPECTO AL CANJE
 **/
function createSliderDevolucionCanje(divchartzoomslider,periodo,compPeriodo1,compPeriodo2,medioservicio,label,errormessage,almacen){
	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	var period = parseInt(periodo,10);
	//Rangos del slider de control
	var first;
	var minDays;
	var updateperiodo;
	var addday=0;
	switch (period) {
		case 1:		minDays = 1;
					addday = 10;
					first = true;
					updateperiodo = true;
			break;
		case 2: 	minDays = 60;
					minMonth = 1;
					first = false;
					updateperiodo = true;
			break;
		case 3: 	minDays = 90;
					minMonth = 3;
					first = true;
					updateperiodo = true;
			break;  
		case 4: 	minMonth = 6;
					first = true;
					minDays = 180;
					updateperiodo = true;
		break;
		case 5: 	minMonth = 12;
					first = false;
					updateperiodo = true;
					minDays = 360;
			break;
	};
	
	try {	
		data =  RestCompensacionComportamientoServices.getCompDevolucionCanje({'medioservicio':medioservicio,'periodo':period});
		
		$("#"+divchartzoomslider).empty();
		$("#"+divchartzoomslider)[0].setAttribute("class","superzoom");
		
		if (label==null || label =="undefined") label = "Todas";
		data["Title"] = data["Title"] + " - " + label;
		
		vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecStart = completarFechaStart(vStorageFecStart,data["Ticks"]);
		vStorageFecEnd = completarFechaEnd(vStorageFecEnd,data["Ticks"]);

		ticks = data["Ticks"];
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		dateend = ticks[ticks.length - 1];
		dateend = dateend.split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * ( minDays +addday));
			fromtmp = new Date(fromtmp);
			vStorageFecStart = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
			from = vStorageFecStart;
		} 
		from2=vStorageFecStart;
		startvaluescomponentstimes (vStorageFecStart,vStorageFecEnd,compPeriodo1,compPeriodo2,data);
		
		divchartmax = document.createElement('div');
		
		divchartmax.id=divchartzoomslider+"_chartMax";
		divchartmax.style="chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot 		= createplotmaxgeneralDevolCanje (divchartmax.id, data, period);
		
		xmin = targetPlot.axes.xaxis.min;
		xmax = targetPlot.axes.xaxis.max;
		s_left 		= targetPlot._defaultGridPadding.left;
		s_right 	= targetPlot._defaultGridPadding.right;
		left 		= targetPlot._gridPadding.left;
		right		= targetPlot._gridPadding.right;
		width 		= targetPlot._plotDimensions.width;
		idMini = divchartzoomslider+"_divmini";
		innerDivMini = document.createElement('div');
		innerDivMini.id =idMini;
		style =         "width:".concat((width-left)).concat("px !important; ")
					.concat("left:").concat(0).concat("px !important;")
					.concat("right:").concat(right-s_right).concat("px !important;")
					.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style",style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		
		controllerPlot 	= createplotmingeneralDevolCanje (idMini, data, period);
		
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider+"_slider";
		
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id=idDivSlider;
		var styleSlider = "width:".concat((width-left-right+s_left+s_right+24)).concat("px !important; ")
								  .concat("margin-top:").concat(-8).concat("px !important;")
								  .concat("left:").concat(left-s_left-12).concat("px !important;")
								  .concat("right:").concat(right).concat("px !important;")
								  .concat("position: relative;")
		;
		
		innerDivSlider.setAttribute("style",styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		var dateSlider = $("#"+idDivSlider).dateRangeSlider( 
        		{
        			range:{ 
                	    min: {days: minDays}, 
        	  		},
        	  		bounds: {
        	  			min: datestart, 
        	  			max: dateend
        	  		},
        	  		defaultValues: {
        	  			min: from2, 
        	  			max: vStorageFecEnd
        	  		},
        	 	}
		);
		
		//trigger first load
		var datesinit = {
			label: dateSlider,
			values : {
				min: from2,
				max: vStorageFecEnd,
			},
			first: first,
			updateperiodo: updateperiodo
		}
		
		//trigger first load 2
		var datesinit2 = {
			label: dateSlider,
			values : {
				min: from2,
				max: vStorageFecEnd,
			},
			first: first,
			updateperiodo: updateperiodo
		}

		$("#"+idDivSlider).unbind();
		$("#"+idDivSlider).bind("valuesChanged", function(evt,dateSlider){
			valuesPlotChangedTimes(dateSlider,controllerPlot,targetPlot,compPeriodo1,compPeriodo2,data,period);
			//if (dateSlider.first==null || dateSlider.first==false) {
				updatevaluescomponentstimesfromslider(dateSlider,compPeriodo1,compPeriodo2,data,period);
				savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
			//}
		});
		
		/**
		 * 
		 */
		//Retira eventos y actualiza datos en años
		$("#"+compPeriodo1+"_sel_anio").unbind();
		$("#"+compPeriodo1+"_sel_anio").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 3: 	changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 4: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			};		
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_anio").unbind();
		$("#"+compPeriodo2+"_sel_anio").change(function(evt) {
			
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 3: 	changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 4: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			};
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en mes
		$("#"+compPeriodo1+"_sel_mes").unbind();
		$("#"+compPeriodo1+"_sel_mes").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_mes").unbind();
		$("#"+compPeriodo2+"_sel_mes").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
				case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en dia
		$("#"+compPeriodo1+"_sel_dia").unbind();
		$("#"+compPeriodo1+"_sel_dia").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_dia").unbind();
		$("#"+compPeriodo2+"_sel_dia").change(function(evt) {
			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en Semestre
		$("#"+compPeriodo1+"_sel_semestre").unbind();
		$("#"+compPeriodo1+"_sel_semestre").change(function(evt) {
			if(period == 4){
				changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_semestre").unbind();
		$("#"+compPeriodo2+"_sel_semestre").change(function(evt) {
			if(period == 4){
				changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		//Retira eventos y actualiza datos en trimestre
		$("#"+compPeriodo1+"_sel_trimestre").unbind();
		$("#"+compPeriodo1+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}			
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+compPeriodo2+"_sel_trimestre").unbind();
		$("#"+compPeriodo2+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
			savedOldDatesStoStorage(almacen, compPeriodo1, compPeriodo2, period);
		});
		
		$("#"+idDivSlider).trigger("valuesChanged",datesinit);
		$("#"+idDivSlider).trigger("valuesChanged",datesinit2);
		window.scrollTo(0, 380);

		
	} catch (err) {
		console.log(err);
		$("[id*='"+errormessage+"'").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

//GRÁFICAS

function createplotmaxgeneralDevolCanje(name,data,tipo) {

	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
 	ticks 			= data["Ticks"];
 	serieValores 	= data["SerieValoresPorcentaje"];
 	serieValores = datosValor(serieValores);
 	serieCantidad	= data["SerieCantidadPorcentaje"];
 	serieCantidad	= datosValor(serieCantidad);
   
 	minValor		= data["MinValor"];
 	minCantidad		= data["MinCantidad"];
 	maxValor		= data["MaxValor"];
 	maxCantidad		= data["MaxCantidad"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	var plot2 = $.jqplot(name, [serieValores,serieCantidad] , {
        title:title,
        textColor:  "#003E6C",
        seriesColors:['#850024', '#006fb9'],
		seriesDefaults:{ 
			renderer:$.jqplot.BlockRenderer,
			showMarker: true, 
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
			},
            animation: {
                show: false
            },
        },
        legend: {
            renderer: $.jqplot.EnhancedLegendRenderer,
            show: true,
            showLabels: true,
            label:'',
            showSwatches:true,
            placement: "insideGrid",
            location: "ne",
            fontSize: '11px',
            pad:5,
            rowSpacing: "0px",
            rendererOptions: {
                seriesToggle: "true",
                seriesToggleReplot: {
                	resetAxes: false
                }
            }
        },
        series:[
			{	color: '#850024', 
				lineWidth: 0.5, 
				shadow: true, 
				label:'Valor',
				markerOptions: { size:4, style:'filledCircle' },
				rendererOptions: {
					smooth: false,
		               css: {
		            	   background:'#850024'
		               }
		        }
			},
		    {	yaxis: 'y2axis', 
				lineWidth: 0.5, 
				shadow: true, 
				label:'Cantidad' , 
				markerOptions: { 
						size:4, 
						style:'filledCircle' 
				},
				rendererOptions: {
					smooth: false,
					css: {
			        	background:'#006fb9'
			        }
			    }
			},
	    ],
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : true,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		left: "0",
        },
	    cursor:{
	    	show: true,
	    	zoom:true,
	    	showTooltip:false,
	    	dblClickReset: false,
	    },
	    highlighter : {
    		show : true,
    		sizeAdjust : 1,
    		tooltipOffset : 9,
    		useYTickMarks : true,
    		useXTickMarks : true,
    		currentNeighbor : null,
    		showMarker : true,
    		lineWidthAdjust : 1,
    		showTooltip : true,
    		tooltipLocation : 'e',
    		fadeTooltip : false,
    		tooltipAxes : '(x , y)',
    		tooltipSeparator : ', ',
    		useAxesFormatters : true,
	    },
	    axesDefaults: { 
        	showTickMarks:true,
        	useSeriesColor:true, 
        	rendererOptions: {
        		alignTicks: true,
        	} 
	    },	
        axes:{
            xaxis:{
            	numberTicks: getTicks(minX,maxX,tipo),
            	min:minX,
            	max:maxX,
            	label:'Fecha',
            	showLabel: false,
            	pad: 1,
            	tickInterval: getTickInterval(tipo),
            	renderer:$.jqplot.DateAxisRenderer,
				rendererOptions:{ 
					tickRenderer:$.jqplot.CanvasAxisTickRenderer ,
				}, 
                tickOptions: {
                	showTicks: true, 
                	angle: -45,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	fontFamily:'"Roboto", sans-serif',
	            	fontSize: '8pt',
	            	show:true,
	            	size:4,
	            	markSize:4,
	            	formatString: formatter(tipo),
                }
            },
            yaxis:{
            	min:minValor,
            	max:maxValor,
            	//label:'<div><div style="padding-left: 24px;">Valor</div><div style="color: gray; width: 120px !important">(Millones COP)<div></div>',
            	label:'<div><div style="padding-left: 24px;">Valor</div></div>',
            	showLabel:true,
            	textColor: "#850024",
            	show:true,
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: true,
				}, 
                tickOptions: {
                	showTicks: true, 
                	angle: 0,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	fontSize: "8pt",
	            	size:4,
	            	markSize:6,
	            	show:true,
	            	pad: 1,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#850024",
	        		formatString: "%'.2f %",
                }
            },
            y2axis:{
            	min:minCantidad,
            	max:maxCantidad,
            	showLabel:true,
            	label: '<div style="padding-left: 24px;">Cantidad</div>',
            	textColor: "#006fb9",
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: true,
				}, 
                tickOptions: {
                	showTicks: true, 
	            	fontSize: "8pt",
                	angle: 0,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	show:true,
	            	size:4,
	            	markSize:6,
	            	pad: 1.2,
	        		textColor: "#006fb9",
	            	fontFamily:'"Roboto", sans-serif',
	        		formatString: "%'.2f %",
                },
            },
        },
	});
	return plot2;
}

function createplotmingeneralDevolCanje(name,data,tipo) {

	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
 	ticks 			= data["Ticks"];
 	serieValores 	= data["SerieValoresPorcentaje"];
 	serieValores = datosValor(serieValores);
 	serieCantidad	= data["SerieCantidadPorcentaje"];
	serieValores = datosValor(serieValores);
  	minValor		= data["MinValor"];
 	minCantidad		= data["MinCantidad"];
 	maxValor		= data["MaxValor"];
 	maxCantidad		= data["MaxCantidad"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	
 	$.jqplot.sprintf.thousandsSeparator = '.';
 	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [serieValores,serieCantidad] , {
        textColor:  "#003E6C",
        seriesColors:['#850024', '#006fb9'],
		seriesDefaults:{ 
			renderer:$.jqplot.BlockRenderer,
			lineWidth: 0.5,
			showMarker: false,
        	fill: false,
        	fillAndStroke: true,
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
			},
			labels: ['',''],			
            animation: {
                show: false
            },
        },
        
        legend: {
            show: false,
            label:'',
            showLabels: false,
            showSwatches:false,
            placement: "insideGrid",
            location: "ne",
            fontSize: '11px',
            pad:0,
            rowSpacing: "0px",
            rendererOptions: {
                seriesToggle: "false",
                seriesToggleReplot: {
                	resetAxes: false
                }
            }
        },
        
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : true,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		pad: 0,
        },
	    cursor:{
	    	show: false,
	    	zoom:true,
	    	showTooltip:true,
	    	constrainZoomTo: 'x',
	    	dblClickReset: false,
	    	
	    },
	    highlighter : {
    		show : false,
	    },
	    axesDefaults: { 
        	showTickMarks:false,
        	useSeriesColor:false, 
        	rendererOptions: {
        		alignTicks: true,
        	} 
	    },	
        
        axes:{
            xaxis:{
            	numberTicks: getTicks(minX,maxX,tipo),
            	min:minX,
            	max:maxX,
            	label:'Fecha',
            	showLabel: false,
            	pad: 1,
            	renderer:$.jqplot.DateAxisRenderer,
            	tickInterval: getTickInterval(tipo),
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer ,
				}, 
                tickOptions: {
                	showTicks: false, 
                	angle: -45,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	fontFamily:'"Roboto", sans-serif',
	            	fontSize: '8pt',
	            	show:false,
	            	size:0,
	            	markSize:0,
	            	formatString: formatter(tipo),
                }
            },
            yaxis:{
            	min:minValor,
            	max:maxValor,
            	showLabel:false,
            	label:'Cantidad',
            	show:false,
				rendererOptions:{ 
					  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
					  forceTickAt0: true,
					}, 
                tickOptions: {
                	showTicks: false, 
                	angle: 0,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	fontSize: "8pt",
	            	size:0,
	            	markSize:0,
	            	show:false,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#850024",
	        		formatString: "%'.2f",
                }
            },
            y2axis:{
            	min:minCantidad,
            	max:maxCantidad,
            	showLabel:true,
            	label: '<div><div>Cantidad</div><div style="color: gray;">Cheques</div></div>',
            	textColor: "#006fb9",
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: false,
				  forceTickAt100: false,
				}, 
                tickOptions: {
                	showTicks: false, 
	            	fontSize: "8pt",
                	angle: 0,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	show:false,
	            	size:4,
	            	markSize:6,
	            	pad: 1.2,
	        		textColor: "#006fb9",
	            	fontFamily:'"Roboto", sans-serif',
	        		formatString: "%'.2f",
                },
            },
        },
	});
	return plot2;
}

/**
 * CHEQUES EN PESO CONSTANTES
 * @author ciglesiascrespo
 **/

function createSliderChequesPesos(divchartzoomslider,errormessage){

	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	var period = 5;
	
	try {	
		data =  RestCompensacionChequesPesosConstantes.getCompChequesPesos();
		
		$("#"+divchartzoomslider).empty();
		$("#"+divchartzoomslider)[0].setAttribute("class","superzoom");
					
		var divchartmax = document.createElement('div');
		
		divchartmax.id=divchartzoomslider+"_chartMax";
		divchartmax.style="chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot 		= createplotmaxgeneralAnualidadPesos (divchartmax.id, data, period);
		
		var xmin = targetPlot.axes.xaxis.min;
		var xmax = targetPlot.axes.xaxis.max;
		var s_left 		= targetPlot._defaultGridPadding.left;
		var s_right 	= targetPlot._defaultGridPadding.right;
		var left 		= targetPlot._gridPadding.left;
		var right		= targetPlot._gridPadding.right;
		var width 		= targetPlot._plotDimensions.width;
		idMini = divchartzoomslider+"_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id =idMini;
		var style =         "width:".concat((width-left)).concat("px !important; ")
					.concat("left:").concat(0).concat("px !important;")
					.concat("right:").concat(right-s_right).concat("px !important;")
					.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style",style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		
		controllerPlot 	= createplotmingeneralAnualidadPesos (idMini, data, period);
		
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider+"_slider";
		
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id=idDivSlider;
		var styleSlider = "width:".concat((width-left-right+s_left+s_right+24)).concat("px !important; ")
								  .concat("margin-top:").concat(-8).concat("px !important;")
								  .concat("left:").concat(left-s_left-12).concat("px !important;")
								  .concat("right:").concat(right).concat("px !important;")
								  .concat("position: relative;")
		;
		innerDivSlider.setAttribute("style",styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart 	= data["MinX"].split("-");
		datestart		= new Date(datestart[0],(parseInt(datestart[1])-1),datestart[2]);
		var dateend 	= data["MaxX"].split("-");;
		dateend			= new Date(dateend[0],(parseInt(dateend[1])-1),dateend[2]);
		var from 			= data["startX"].split("-");
		from			= new Date(from[0],parseInt(from[1])-1,from[2]);
		var to				= dateend;
		
		//Rangos del slider de control
		var minDays;
		var first;
		var updateperiodo;
		
		
		var dateSlider = $("#"+idDivSlider).dateRangeSlider( 
        		{
        			range:{ 
                	    min: {months: 12}, 
        	  		},
        	  		bounds: {
        	  			min: datestart, 
        	  			max: dateend
        	  		},
        	  		defaultValues: {
        	  			min: from, 
        	  			max: to
        	  		},
        	 	}
		);
		//trigger first load
		var datesinit = {
			label: dateSlider,
			values : {
				min: from,
				max: to,
			},
			first: first,
			updateperiodo: updateperiodo
		}

		$("#"+idDivSlider).unbind();
		$("#"+idDivSlider).bind("valuesChanged", function(evt,dateSlider){
			var compPeriodo1; 
			var compPeriodo2;
			valuesPlotChangedTimes(dateSlider,controllerPlot,targetPlot,compPeriodo1,compPeriodo2,data,period);
		});
		
		$("#"+idDivSlider).trigger("valuesChanged",datesinit);
		
	} catch (err) {
		$("[id*='"+errormessage+"'").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

//GRÁFICAS



function createplotmaxgeneralAnualidadPesos(name,data,tipo) {

 	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
	var text ='';
    var text1='';
 	var cad = title;
      var res = cad.split(" ");
      var i1;
      
      var lontot = 0;
      for (i1 = 0; i1 < res.length; i1++) {
         
          if (lontot < 40)
          { text = text+ res[i1]+" ";}
          else
          {text1 = text1+ res[i1]+" ";}
          lontot  = lontot + res[i1].length;
       
    }
       
 	ticks 			= data["Ticks"];
 	serieValores 	= datosValor4(data["SerieValores"]);
 	serieCantidad	= datosValor4(data["SerieCantidad"]);
 	minValor		= data["MinValor"];
 	minCantidad		= data["MinCantidad"];
 	maxValor		= data["MaxValor"];
 	maxCantidad		= data["MaxCantidad"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	var plot2 = $.jqplot(name, [serieValores,serieCantidad] , {
        title:'<div class="row"><div class="col col-lg-12" style="line-height: 1.5;">' +text +'</div></div><div class="row"><div calss="col col-lg-12" style="line-height:1.5;">' + text1+ '</div></div></div>',
        textColor:  "#003E6C",
        seriesColors:['#850024', '#006fb9'],
		seriesDefaults:{ 
			renderer:$.jqplot.BlockRenderer,
			showMarker: true, 
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
			},
            animation: {
                show: false
            },
        },
        legend: {
            renderer: $.jqplot.EnhancedLegendRenderer,
            show: true,
            showLabels: true,
            label:'',
            showSwatches:true,
            placement: "insideGrid",
            location: "ne",
            fontSize: '11px',
            pad:5,
            rowSpacing: "0px",
            rendererOptions: {
                seriesToggle: "true",
                seriesToggleReplot: {
                	resetAxes: false
                }
            }
        },
        series:[
			{	color: '#850024', 
				lineWidth: 0.5, 
				shadow: true, 
				label:'Valor',
				markerOptions: { size:4, style:'filledCircle' },
				rendererOptions: {
					smooth: false,
		               css: {
		            	   background:'#850024'
		               }
		        }
			},
		    {	yaxis: 'y2axis', 
				lineWidth: 0.5, 
				shadow: true, 
				label:'Cantidad' , 
				markerOptions: { 
						size:4, 
						style:'filledCircle' 
				},
				rendererOptions: {
					smooth: false,
					css: {
			        	background:'#006fb9'
			        }
			    },
			    highlighter: { 
			    	yvalues: 1,
			    	tooltipAxes : 'y',
			    	useAxesFormatters : true,
			    	formatString: '<table class="jqplot-highlighter"> \
		                <tr><td>Cantidad:</td><td>%s</td></tr> \
				    		</table>'
			    }
			}
	    ],
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : true,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		left: "0",
        },
	    cursor:{
	    	show: true,
	    	zoom:true,
	    	showTooltip:false,
	    	dblClickReset: false,
	    },
	    highlighter : {
	    	show : true,
    		sizeAdjust : 1,
    		tooltipOffset : 9,
    		useYTickMarks : true,
    		useXTickMarks : true,
    		currentNeighbor : null,
    		showMarker : true,
    		lineWidthAdjust : 1,
    		showTooltip : true,
    		tooltipLocation : 'e',
    		fadeTooltip : false,
    		yvalues: 1,
	    	tooltipAxes : 'y',
    		useAxesFormatters : true,
    		formatString: '<table class="jqplot-highlighter"> \
                <tr><td>Valor:</td><td>%s</td></tr> \
		    	</table>'
	    },
	    axesDefaults: { 
        	showTickMarks:true,
        	useSeriesColor:true, 
        	rendererOptions: {
        		alignTicks: true,
        	} 
	    },	
        axes:{
            xaxis:{
            	numberTicks: getTicks(minX,maxX,tipo),
            	min:minX,
            	max:maxX,
            	label:'Fecha',
            	showLabel: false,
            	pad: 1,
            	tickInterval: getTickInterval(tipo),
            	renderer:$.jqplot.DateAxisRenderer,
				rendererOptions:{ 
					tickRenderer:$.jqplot.CanvasAxisTickRenderer ,
				}, 
                tickOptions: {
                	showTicks: true, 
                	angle: -45,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	fontFamily:'"Roboto", sans-serif',
	            	fontSize: '8pt',
	            	show:true,
	            	size:4,
	            	markSize:4,
	            	formatString: formatter(tipo),
                }
            },
            yaxis:{
            	min:minValor,
            	max:maxValor,
            	label:'<div><div style="padding-left: 24px;">Valor</div><div style="color: gray; width: 100px !important">(Miles de <br/> Millones COP)<div></div>',
            	showLabel:true,
            	textColor: "#850024",
            	show:true,
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: true,
				}, 
                tickOptions: {
                	showTicks: true, 
                	angle: 0,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	fontSize: "8pt",
	            	size:4,
	            	markSize:6,
	            	show:true,
	            	pad: 1,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#850024",
	        		formatString: "%'.2f",
                }
            },
            y2axis:{
            	min:minCantidad,
            	max:maxCantidad,
            	showLabel:true,
            	label: '<div style="padding-left: 24px;">Cantidad</div>',
            	textColor: "#006fb9",
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: true,
				}, 
                tickOptions: {
                	showTicks: true, 
	            	fontSize: "8pt",
                	angle: 0,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	show:true,
	            	size:4,
	            	markSize:6,
	            	pad: 1.2,
	        		textColor: "#006fb9",
	            	fontFamily:'"Roboto", sans-serif',
	        		formatString: "%'.0f",
                },
            },
        },
	});
	return plot2;
}

function createplotmingeneralAnualidadPesos(name,data,tipo) {
 	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
 	ticks 			= data["Ticks"];
 	serieValores 	= data["SerieValores"];
 	serieCantidad	= data["SerieCantidad"];
 	minValor		= data["MinValor"];
 	minCantidad		= data["MinCantidad"];
 	maxValor		= data["MaxValor"];
 	maxCantidad		= data["MaxCantidad"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	
 	$.jqplot.sprintf.thousandsSeparator = '.';
 	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [serieValores,serieCantidad] , {
        textColor:  "#003E6C",
        seriesColors:['#850024', '#006fb9'],
		seriesDefaults:{ 
			renderer:$.jqplot.BlockRenderer,
			lineWidth: 0.5,
			showMarker: false,
        	fill: false,
        	fillAndStroke: true,
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 1,
				shadowDepth: 5,
			},
			labels: ['',''],			
            animation: {
                show: false
            },
        },
        
        legend: {
            show: false,
            label:'',
            showLabels: false,
            showSwatches:false,
            placement: "insideGrid",
            location: "ne",
            fontSize: '11px',
            pad:0,
            rowSpacing: "0px",
            rendererOptions: {
                seriesToggle: "false",
                seriesToggleReplot: {
                	resetAxes: false
                }
            }
        },
        
	    grid: {
    		background : '#ffffff',
    		borderColor : '#eeeeee',
    		gridLineColor : '#F5F5F5',
    		shadow : false,
    		drawBorder : true,
    		gridLineWidth : 0.8,
    		borderWidth: 0.8,
    		pad: 0,
        },
	    cursor:{
	    	show: false,
	    	zoom:true,
	    	showTooltip:true,
	    	constrainZoomTo: 'x',
	    	dblClickReset: false,
	    	
	    },
	    highlighter : {
    		show : false,
	    },
	    axesDefaults: { 
        	showTickMarks:false,
        	useSeriesColor:false, 
        	rendererOptions: {
        		alignTicks: true,
        	} 
	    },	
        
        axes:{
            xaxis:{
            	numberTicks: getTicks(minX,maxX,tipo),
            	min:minX,
            	max:maxX,
            	label:'Fecha',
            	showLabel: false,
            	pad: 1,
            	renderer:$.jqplot.DateAxisRenderer,
            	tickInterval: getTickInterval(tipo),
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer ,
				}, 
                tickOptions: {
                	showTicks: false, 
                	angle: -45,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	fontFamily:'"Roboto", sans-serif',
	            	fontSize: '8pt',
	            	show:false,
	            	size:0,
	            	markSize:0,
	            	formatString: formatter(tipo),
                }
            },
            yaxis:{
            	min:minValor,
            	max:maxValor,
            	showLabel:false,
            	label:'Cantidad',
            	show:false,
				rendererOptions:{ 
					  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
					  forceTickAt0: true,
					}, 
                tickOptions: {
                	showTicks: false, 
                	angle: 0,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	fontSize: "8pt",
	            	size:0,
	            	markSize:0,
	            	show:false,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#850024",
	        		formatString: "%'.2f",
                }
            },
            y2axis:{
            	min:minCantidad,
            	max:maxCantidad,
            	showLabel:true,
            	label: '<div><div>Cantidad</div><div style="color: gray;">Cheques</div></div>',
            	textColor: "#006fb9",
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: false,
				  forceTickAt100: false,
				}, 
                tickOptions: {
                	showTicks: false, 
	            	fontSize: "8pt",
                	angle: 0,
                	showLabel:false,
                	showMark:false,
	            	showGridline:false,
	            	show:false,
	            	size:4,
	            	markSize:6,
	            	pad: 1.2,
	        		textColor: "#006fb9",
	            	fontFamily:'"Roboto", sans-serif',
	        		formatString: "%'.2f",
                },
            },
        },
	});
	return plot2;
}



