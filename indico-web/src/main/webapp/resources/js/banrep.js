/**
 *  GENERAL FUNCTIONS
 **/
var _v = "1.1.1";
var showError = true;

function restartExportar(selectname) {
	setTimeout(function() {
		PF('' + selectname + '').selectValue("");
	}, 1000);
}

function formatdate(val) {
	var days = val.getDate();
	var month = val.getMonth() + 1;
	var year = val.getFullYear();
	return days + "/" + month + "/" + year;
}

function completarFechaStart(fecha,sticks,fechaFinal,startSticks) {
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
		let pos = 0;
		let nextdate = null;
		for (let i=0 ; i<sticks.length ; i++ ) {
			let currentValue = sticks[i]; 
			let actualdate = new Date(currentValue.split('-')[0],currentValue.split('-')[1]-1,currentValue.split('-')[2]);
			if (fecha>=actualdate) {
				anterior=actualdate;
				pos++;
			} else {
				//fecha=anterior;
				nextdate = actualdate;
				break;
			}
		}
		
		if (sticks.length==pos) {
			currentValue = sticks[pos-2];
			fecha = new Date(currentValue.split('-')[0],currentValue.split('-')[1]-1,currentValue.split('-')[2]);
		} else {
			fecha = anterior;
			if (fechaFinal!=null) {
				if (nextdate < fechaFinal){
					fecha = nextdate;		
				} 
			} 
		}
		
		if (startSticks!=null && startSticks > 0) {
			if(sticks.length>=startSticks) {
				fs = sticks[sticks.length-startSticks];
			} else {
				fs = sticks[0];
			}
			fecha = new Date(fs.split('-')[0],fs.split('-')[1]-1,fs.split('-')[2]);
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

function getDateStartFromDiario(compDiario) {
	var anio = $("#" + compDiario + "_sel_anio").val();
	var mes = $("#" + compDiario + "_sel_mes").val();
	var dia = $("#" + compDiario + "_sel_dia").val();
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateEndFromDiario(compDiario) {
	var anio = $("#" + compDiario + "_sel_anio").val();
	var mes = $("#" + compDiario + "_sel_mes").val();
	var dia = $("#" + compDiario + "_sel_dia").val();
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateStartFromMensual(componenteMensual) {
	var anio = $("#" + componenteMensual + "_sel_anio").val();
	var mes = $("#" + componenteMensual + "_sel_mes").val();
	var dia = "01";
	var resultado = "" + anio + "-" + ("0" + (mes)).slice(-2) + "-" + dia;
	return resultado;
}

function getDateEndFromMensual(componenteMensual) {
	var anio = $("#" + componenteMensual + "_sel_anio").val();
	var mes = $("#" + componenteMensual + "_sel_mes").val();
	var dia = new Date(anio, (parseInt(mes)), 0).getDate();
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateStartFromTrimestral(componenteTrimestral) {
	var anio = $("#" + componenteTrimestral + "_sel_anio").val();
	var mestrimestre = parseInt($("#" + componenteTrimestral + "_sel_trimestre").val());
	var dia = "01";
	return anio + "-" + ("0" + (mestrimestre)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateEndFromTrimestral(componenteTrimestral) {
	var anio = $("#" + componenteTrimestral + "_sel_anio").val();
	var mestrimestre = parseInt($("#" + componenteTrimestral + "_sel_trimestre").val()) + 2;
	var dia = new Date(anio, mestrimestre, 0).getDate();
	return anio + "-" + ("0" + (mestrimestre)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateStartFromSemestral(componenteSemestral) {
	var anio = $("#" + componenteSemestral + "_sel_anio").val();
	var messemestre = parseInt($("#" + componenteSemestral + "_sel_semestre").val());
	var dia = "01";
	return anio + "-" + ("0" + (messemestre)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateEndFromSemestral(componenteSemestral) {
	var anio = $("#" + componenteSemestral + "_sel_anio").val();
	var messemestre = parseInt($("#" + componenteSemestral + "_sel_semestre").val()) + 5;
	var dia = new Date(anio, messemestre, 0).getDate();
	return anio + "-" + ("0" + (messemestre)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateStartFromAnual(componenteAnual) {
	var anio = $("#" + componenteAnual + "_sel_anio").val();
	var mes = "01";
	var dia = "01";
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

function getDateEndFromAnual(componenteAnual) {
	var anio = $("#" + componenteAnual + "_sel_anio").val();
	var mes = "12";
	var dia = "31";
	return anio + "-" + ("0" + (mes)).slice(-2) + "-" + ("0" + (dia)).slice(-2);
}

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

/**
 * Funcion que crea un array con las horas del día
 * @returns array de horas.
 */
function hours() {
	var horas = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];
	return horas;
}

/**
 *  Nuevos componentes basados en el tiempo
 **/

function startvaluescomponentstimes(valchanged1, valchanged2, compdiario1, compdiario2, data) {

	var inicioX;
	var endX;
	if (valchanged1 != null && valchanged2 != null) {
		inicioX = valchanged1;
		endX = valchanged2;
	} else {
		var fechaA = data["startX"].split("-");
		if (fechaA.length == 1) {
			fechaA[1] = "01";
			fechaA[2] = "01";
		}
		inicioX = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		if (fechaB.length == 1) {
			fechaB[1] = "01";
			fechaB[2] = "01";
		}
		endX = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
	}
	var selanio1 = '#' + compdiario1 + "_sel_anio";
	var selmes1 = '#' + compdiario1 + "_sel_mes";
	var seldia1 = '#' + compdiario1 + "_sel_dia";
	var seltrimestre1 = '#' + compdiario1 + "_sel_trimestre";
	var selsemestre1 = '#' + compdiario1 + "_sel_semestre";

	addaniotimes(data, selanio1, inicioX);
	addmesestimes(data, selmes1, inicioX);
	adddiastimes(data, seldia1, inicioX);
	addtrimestretimes(data, seltrimestre1, inicioX);
	addsemestretimes(data, selsemestre1, inicioX);

	var selanio2 = '#' + compdiario2 + "_sel_anio";
	var selmes2 = '#' + compdiario2 + "_sel_mes";
	var seldia2 = '#' + compdiario2 + "_sel_dia";
	var seltrimestre2 = '#' + compdiario2 + "_sel_trimestre";
	var selsemestre2 = '#' + compdiario2 + "_sel_semestre";

	addaniotimes(data, selanio2, endX);
	addmesestimes(data, selmes2, endX);
	adddiastimes(data, seldia2, endX);
	addtrimestretimes(data, seltrimestre2, endX);
	addsemestretimes(data, selsemestre2, endX);
}

function updatevaluescomponentstimesfromslider(slider, compdiario1, compdiario2, data, tipo) {
	var inicioX = new Date((slider.values.min.getTime() + (60 * 60 * 1000 * 5)));
	var endX = new Date((slider.values.max.getTime() + (60 * 60 * 1000 * 5)));
	inicioX = getDateFromSlider(inicioX, tipo);
	endX = getDateFromSlider(endX, tipo);
	var selanio1 = '#' + compdiario1 + "_sel_anio";
	var selmes1 = '#' + compdiario1 + "_sel_mes";
	var seldia1 = '#' + compdiario1 + "_sel_dia";
	var seltrimestre1 = '#' + compdiario1 + "_sel_trimestre";
	var selsemestre1 = '#' + compdiario1 + "_sel_semestre";
	addaniotimes(data, selanio1, inicioX);
	addmesestimes(data, selmes1, inicioX);
	adddiastimes(data, seldia1, inicioX);
	addtrimestretimes(data, seltrimestre1, inicioX);
	addsemestretimes(data, selsemestre1, inicioX);

	var selanio2 = '#' + compdiario2 + "_sel_anio";
	var selmes2 = '#' + compdiario2 + "_sel_mes";
	var seldia2 = '#' + compdiario2 + "_sel_dia";
	var seltrimestre2 = '#' + compdiario2 + "_sel_trimestre";
	var selsemestre2 = '#' + compdiario2 + "_sel_semestre";
	addaniotimes(data, selanio2, endX);
	addmesestimes(data, selmes2, endX);
	adddiastimes(data, seldia2, endX);
	addtrimestretimes(data, seltrimestre2, endX);
	addsemestretimes(data, selsemestre2, endX);
}

function addaniotimes(data, selanio, selected) {

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

function changedatesselectedstimes(compdiario1, compdiario2, data, slider) {

	var mes1, dia1, anio1;
	var mes2, dia2, anio2;

	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(compdiario1 + "_sel_anio");
	var secValue = selAnio1.options[1].value;
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;

	//Primer y ultimo valor de meses
	var selMes1 = document.getElementById(compdiario1 + "_sel_mes");
	if (selMes1 != null) {
		var secMesValue = selMes1.options[1].value;
		var lastMesValue = selMes1.options[selMes1.options.length - 1].value;
	}

	//Primer y ultimo valor de meses2
	var selMes2 = document.getElementById(compdiario2 + "_sel_mes");
	var secMesValue2 = selMes2.options[1].value;
	var lastMesValue2 = selMes2.options[selMes2.options.length - 1].value;

	//Primer y ultimo valor de días
	var selDia1 = document.getElementById(compdiario1 + "_sel_dia");
	if (selDia1 != null) {
		var secDiaValue = selDia1.options[1].value;
		var lastDiaValue = selDia1.options[selDia1.options.length - 1].value;
	}

	//Primer y ultimo valor de día 2
	var selDia2 = document.getElementById(compdiario2 + "_sel_dia");
	if (selDia2 != null) {
		var secDiaValue2 = selDia2.options[1].value;
		var lastDiaValue2 = selDia2.options[selDia2.options.length - 1].value;
	}

	if ($("#" + compdiario1 + "_sel_anio").length) {
		anio1 = $("#" + compdiario1 + "_sel_anio").val();
		if (anio1 == "") {
			anio1 = secValue;
		}
	}

	if ($("#" + compdiario1 + "_sel_mes").length) {
		mes1 = $("#" + compdiario1 + "_sel_mes").val();
		if (mes1 == "") {
			mes1 = secMesValue;
		}
	} else {
		mes1 = "01"
	}

	if ($("#" + compdiario1 + "_sel_dia").length) {
		dia1 = $("#" + compdiario1 + "_sel_dia").val();
		if (dia1 == "") {
			dia1 = secDiaValue;
		}
	} else {
		dia1 = "01";
	}

	if ($("#" + compdiario2 + "_sel_anio").length) {
		anio2 = $("#" + compdiario2 + "_sel_anio").val();
		if (anio2 == "") {
			anio2 = lastValue;
		}
	}

	if ($("#" + compdiario2 + "_sel_mes").length) {
		mes2 = $("#" + compdiario2 + "_sel_mes").val();
		if (mes2 == "") {
			mes2 = lastMesValue2;
		}
	} else {
		mes2 = "01";
	}

	if ($("#" + compdiario2 + "_sel_dia").length) {
		dia2 = $("#" + compdiario2 + "_sel_dia").val();
		if (dia2 == "") {
			dia2 = lastDiaValue2;
		}

		/*	if(parseInt(anio1) > parseInt(anio2)){
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
			}*/

		if (anio1 == secValue && (mes1 == parseInt(secMesValue - 1)) && parseInt(dia1) == secDiaValue && parseInt(dia1) == parseInt(dia2)) {
			dia1 = dia2;
			dia2 = (parseInt(dia2) + 10).toString();
		}

		if (anio1 == anio2 && (mes1 == mes2) && parseInt(dia1) == parseInt(dia2)) {
			dia1 = lastDiaValue2 - 10;
			dia2 = lastDiaValue2;

		}

		if (anio1 == lastValue && (mes1 == parseInt(lastMesValue2 - 1)) && parseInt(dia1) == lastDiaValue2) {
			//dia1 = (parseInt(dia1)-10).toString()
			dia2 = dia1;
		}

	} else {

		if (parseInt(anio1) > parseInt(anio2) && parseInt(mes1) >= parseInt(mes2)) {
			anio1 = secValue;
			anio2 = secValue;
			mes1 = mes2 - 1;
			mes2 = mes2;
		}
		else if (parseInt(anio1) > parseInt(anio2) && parseInt(mes1) <= parseInt(mes2)) {
			anio1 = secValue;
			anio2 = secValue;
			mes1 = mes2 - 1;
			mes2 = mes2;
		}

		if (parseInt(anio1) == parseInt(anio2) && parseInt(mes1) >= parseInt(mes2)) {
			mes1 = mes2 - 1;
			mes2 = lastMesValue2;
			if (mes1 == 0) {
				mes1 = "1";
				mes2 = "2";
			}
		}

		if (parseInt(anio1) < parseInt(anio2) && parseInt(mes1) >= parseInt(mes2)) {
			mes1 = mes1;
			mes2 = mes2;
		}

		dia2 = "01";
	}
	var inicio = new Date(anio1, mes1 - 1, dia1);
	var fin = new Date(anio2, mes2 - 1, dia2);
	
	//
	addaniotimes(data, "#" + compdiario1 + "_sel_anio", inicio);
	addmesestimes(data, "#" + compdiario1 + "_sel_mes", inicio);
	adddiastimes(data, "#" + compdiario1 + "_sel_dia", inicio);

	addaniotimes(data, "#" + compdiario2 + "_sel_anio", fin);
	addmesestimes(data, "#" + compdiario2 + "_sel_mes", fin);
	adddiastimes(data, "#" + compdiario2 + "_sel_dia", fin);
	//updates plotes.
}

function updatedataplottimes(plot1, plot2, compdiario1, compdiario2, data, slider, tipo) {

	var serieP1 = [];
	var serieP2 = [];
	var counta = 0;
	var countb = 0;
	var anio1, mes1, dia1, trimestre1, semestre1;
	var anio2, mes2, dia2, trimestre2, semestre2;
	/*Componente 1*/
	if ($("#" + compdiario1 + "_sel_anio").length) {
		anio1 = $("#" + compdiario1 + "_sel_anio").val();
	}
	if ($("#" + compdiario1 + "_sel_mes").length) {
		mes1 = ("0" + $("#" + compdiario1 + "_sel_mes").val()).slice(-2);
	}
	if ($("#" + compdiario1 + "_sel_dia").length) {
		dia1 = ("0" + $("#" + compdiario1 + "_sel_dia").val()).slice(-2);
	} else {
		dia1 = "01";
	}
	if ($("#" + compdiario1 + "_sel_trimestre").length) {
		trimestre1 = ("0" + $("#" + compdiario1 + "_sel_trimestre").val()).slice(-2);
	}
	if ($("#" + compdiario1 + "_sel_semestre").length) {
		semestre1 = ("0" + $("#" + compdiario1 + "_sel_semestre").val()).slice(-2);
	}

	/*Componente 2*/
	if ($("#" + compdiario2 + "_sel_anio").length) {
		anio2 = ("0" + $("#" + compdiario2 + "_sel_anio").val()).slice(-4);
	} else {
		anio2 = "2010";
	}
	if ($("#" + compdiario2 + "_sel_mes").length) {
		mes2 = ("0" + $("#" + compdiario2 + "_sel_mes").val()).slice(-2);
	}
	if ($("#" + compdiario2 + "_sel_dia").length) {
		dia2 = ("0" + $("#" + compdiario2 + "_sel_dia").val()).slice(-2);
	} else {
		dia2 = "01";
	}
	if ($("#" + compdiario2 + "_sel_trimestre").length) {
		trimestre2 = ("0" + $("#" + compdiario2 + "_sel_trimestre").val()).slice(-2);
	}
	if ($("#" + compdiario2 + "_sel_semestre").length) {
		semestre2 = ("0" + $("#" + compdiario2 + "_sel_semestre").val()).slice(-2);
	}
	var mts1 = (semestre1 == null ? (trimestre1 == null ? (mes1) : trimestre1) : semestre1) == null ? "01" : (semestre1 == null ? (trimestre1 == null ? (mes1) : trimestre1) : semestre1);
	var mts2 = (semestre2 == null ? (trimestre2 == null ? (mes2) : trimestre2) : semestre2) == null ? "01" : (semestre2 == null ? (trimestre2 == null ? (mes2) : trimestre2) : semestre2);
	/*****   *****/
	var fec_p1 = anio1 + "-" + "" + mts1 + "-" + dia1;
	var fec_p2 = anio2 + "-" + "" + mts2 + "-" + dia2;

	var seriename;
	if (data["SerieValores"] == null) {
		seriename = datosValor(data["SerieCantidad"]);
	} else {
		seriename = datosValor(data["SerieValores"]);
	}

	var horas = hours();
	var serie = seriename.length;

	for (var i = 0; i < serie; i++) {

		var fec001 = seriename[i][0];
		var hora = parseInt(seriename[i][1]);
		var valor = seriename[i][2];
		if (fec001 == fec_p1) {

			for (var h = 0; h < horas.length; h++) {
				if (hora == horas[h]) {
					serieP1[h] = [hora, valor];
				}
			}
		}
		if (fec001 == fec_p2) {

			for (var h = 0; h < horas.length; h++) {
				if (hora == horas[h]) {
					serieP2[h] = [hora, valor];
				}
			}
		}
	}//for

	var coud1 = 0;
	var coud2 = 0;
	for (var s = 0; s < serieP1.length; s++) {
		try {
			var datos = serieP1[s].length;
			if (datos > 0) {
				coud1++;
			}
		} catch (e) {

		}
	}

	for (var s = 0; s < serieP2.length; s++) {
		try {
			var datos = serieP1[s].length;
			if (datos > 0) {
				coud2++;
			}
		} catch (e) {

		}
	}

	if (coud1 < 24) {
		for (var h = 0; h < horas.length; h++) {
			var flag = true;

			for (var s = 0; s < serieP1.length; s++) {
				try {
					var hs = serieP1[s][0];
					if (horas[h] == hs) {
						flag = false;
						break;
					}
				} catch (e) {
					var flag = true;
				}
			}

			if (flag == true) {
				serieP1[h] = [horas[h], 0];
			}
		}
	}



	if (coud2 < 24) {
		for (var h = 0; h < horas.length; h++) {
			flag = true;

			for (var s = 0; s < serieP2.length; s++) {
				try {
					var hs = serieP2[s][0];
					if (horas[h] == hs) {
						flag = false;
						break;
					}
				} catch (e) {
					var flag = true;
				}
			}

			if (flag == true) {
				serieP2[h] = [horas[h], 0];
			}
		}
	}

	plot1.series[0].data = serieP1;
	plot1.series[1].data = serieP2;
	plot1.legend.labels = [formatterLabel(tipo, fec_p1), formatterLabel(tipo, fec_p2)];
	//plot1.resetAxesScale();
	plot1.replot();

	plot2.series[0].data = serieP1;
	plot2.series[1].data = serieP2;
	//plot2.resetAxesScale();
	plot2.replot();
}

//Update de la gráfica transacción por concepto
function updatedataplottimesrangestran(plot1, plot2, compdiario1, compdiario2, data, slider, tipo) {
	var anio1, mes1, dia1, trimestre1, semestre1;
	var anio2, mes2, dia2, trimestre2, semestre2;

	/*Componente 1*/
	if ($("#" + compdiario1 + "_sel_anio").length) {
		anio1 = $("#" + compdiario1 + "_sel_anio").val();
	}
	if ($("#" + compdiario1 + "_sel_mes").length) {
		mes1 = ("0" + $("#" + compdiario1 + "_sel_mes").val()).slice(-2);
	}
	if ($("#" + compdiario1 + "_sel_dia").length) {
		dia1 = ("0" + $("#" + compdiario1 + "_sel_dia").val()).slice(-2);
	} else {
		dia1 = "01";
	}
	if ($("#" + compdiario1 + "_sel_trimestre").length) {
		trimestre1 = ("0" + $("#" + compdiario1 + "_sel_trimestre").val()).slice(-2);
	}
	if ($("#" + compdiario1 + "_sel_semestre").length) {
		semestre1 = ("0" + $("#" + compdiario1 + "_sel_semestre").val()).slice(-2);
	}

	/*Componente 2*/
	if ($("#" + compdiario2 + "_sel_anio").length) {
		anio2 = ("0" + $("#" + compdiario2 + "_sel_anio").val()).slice(-4);
	} else {
		anio2 = "2010";
	}
	if ($("#" + compdiario2 + "_sel_mes").length) {
		mes2 = ("0" + $("#" + compdiario2 + "_sel_mes").val()).slice(-2);
	}
	if ($("#" + compdiario2 + "_sel_dia").length) {
		dia2 = ("0" + $("#" + compdiario2 + "_sel_dia").val()).slice(-2);
	} else {
		dia2 = "01";
	}
	if ($("#" + compdiario2 + "_sel_trimestre").length) {
		trimestre2 = ("0" + $("#" + compdiario2 + "_sel_trimestre").val()).slice(-2);
	}
	if ($("#" + compdiario2 + "_sel_semestre").length) {
		semestre2 = ("0" + $("#" + compdiario2 + "_sel_semestre").val()).slice(-2);
	}
	var mts1 = (semestre1 == null ? (trimestre1 == null ? (mes1) : trimestre1) : semestre1) == null ? "01" : (semestre1 == null ? (trimestre1 == null ? (mes1) : trimestre1) : semestre1);
	var mts2 = (semestre2 == null ? (trimestre2 == null ? (mes2) : trimestre2) : semestre2) == null ? "01" : (semestre2 == null ? (trimestre2 == null ? (mes2) : trimestre2) : semestre2);
	/*****   *****/
	var fec_p1 = new Date(anio1, (parseInt(mts1) - 1), dia1);
	var fec_p2 = new Date(anio2, (parseInt(mts2) - 1), dia2);
	var serieValores = datos(data["SerieValores"]);
	var serievaloresseleccionada = [[]];
	if (serieValores != null) {
		var j = 0;
		for (var i = 0; i < serieValores.length; i++) {
			var aux = serieValores[i][0];
			if (aux != null) {
				var fec001 = aux.split("-");
				fec001 = new Date(fec001[0], (parseInt(fec001[1]) - 1), fec001[2])
				var valor = serieValores[i][1];
				if (fec_p1 <= fec001 && fec_p2 >= fec001) {
					serievaloresseleccionada[j] = [fec001, valor];
					j++;
				}
			}
			else {
				var aux = (serieValores[i].valorFecha);
				var fec001 = aux.split("-");
				fec001 = new Date(fec001[0], (parseInt(fec001[1]) - 1), fec001[2])
				var valor = serieValores[i][1];
				if (fec_p1 <= fec001 && fec_p2 >= fec001) {
					serievaloresseleccionada[j] = [fec001, valor];
					j++;
				}
			}

		}
	}

	var serieCantidad = datos(data["SerieCantidad"]);
	var seriecantidadsseleccionada = [[]];
	if (serieCantidad != null) {
		var j1 = 0;
		for (var i1 = 0; i1 < serieCantidad.length; i1++) {
			var aux = serieCantidad[i1][0]
			if (aux != null) {
				var fec002 = aux.split("-");
				fec002 = new Date(fec002[0], (parseInt(fec002[1]) - 1), fec002[2])
				var cantidad = serieCantidad[i1][1];
				if (fec_p1 <= fec002 && fec_p2 >= fec002) {
					seriecantidadsseleccionada[j1] = [fec002, cantidad];
					j1++;
				}
			}
			else {
				var aux = (serieCantidad[i1].valorFecha);
				var fec002 = aux.split("-");
				fec002 = new Date(fec002[0], (parseInt(fec002[1]) - 1), fec002[2])
				var cantidad = serieCantidad[i1].serieValor;
				if (fec_p1 <= fec002 && fec_p2 >= fec002) {
					seriecantidadsseleccionada[j1] = [fec002, cantidad];
					j1++;
				}
			}

		}
	}
	//actualiza el slider
	$("#" + slider).dateRangeSlider("values", fec_p1, fec_p2);
	if (slider.length) {
		var datesinit = {
			values: {
				min: fec_p1,
				max: fec_p2,
			},
			updateselects_no: true,
		}
		$("#" + slider).trigger("valuesChanged", datesinit);
	}
}

// Update para las gráficas Rotación y PIB
function updatedataplottimesranges(plot1, plot2, compdiario1, compdiario2, data, slider, tipo) {
	var anio1, mes1, dia1, trimestre1, semestre1;
	var anio2, mes2, dia2, trimestre2, semestre2;
	/*Componente 1*/

	if ($("#" + compdiario1 + "_sel_anio").length) {
		anio1 = $("#" + compdiario1 + "_sel_anio").val();
	}
	if ($("#" + compdiario1 + "_sel_mes").length) {
		mes1 = ("0" + $("#" + compdiario1 + "_sel_mes").val()).slice(-2);
	}
	if ($("#" + compdiario1 + "_sel_dia").length) {
		dia1 = ("0" + $("#" + compdiario1 + "_sel_dia").val()).slice(-2);
	} else {
		dia1 = "01";
	}
	if ($("#" + compdiario1 + "_sel_trimestre").length) {
		trimestre1 = ("0" + $("#" + compdiario1 + "_sel_trimestre").val()).slice(-2);
	}
	if ($("#" + compdiario1 + "_sel_semestre").length) {
		semestre1 = ("0" + $("#" + compdiario1 + "_sel_semestre").val()).slice(-2);
	}

	/*Componente 2*/
	if ($("#" + compdiario2 + "_sel_anio").length) {
		anio2 = ("0" + $("#" + compdiario2 + "_sel_anio").val()).slice(-4);
	} else {
		anio2 = "2010";
	}
	if ($("#" + compdiario2 + "_sel_mes").length) {
		mes2 = ("0" + $("#" + compdiario2 + "_sel_mes").val()).slice(-2);
	}
	if ($("#" + compdiario2 + "_sel_dia").length) {
		dia2 = ("0" + $("#" + compdiario2 + "_sel_dia").val()).slice(-2);
	} else {
		dia2 = "01";
	}
	if ($("#" + compdiario2 + "_sel_trimestre").length) {
		trimestre2 = ("0" + $("#" + compdiario2 + "_sel_trimestre").val()).slice(-2);
	}
	if ($("#" + compdiario2 + "_sel_semestre").length) {
		semestre2 = ("0" + $("#" + compdiario2 + "_sel_semestre").val()).slice(-2);
	}
	var mts1 = (semestre1 == null ? (trimestre1 == null ? (mes1) : trimestre1) : semestre1) == null ? "01" : (semestre1 == null ? (trimestre1 == null ? (mes1) : trimestre1) : semestre1);
	var mts2 = (semestre2 == null ? (trimestre2 == null ? (mes2) : trimestre2) : semestre2) == null ? "01" : (semestre2 == null ? (trimestre2 == null ? (mes2) : trimestre2) : semestre2);
	/*****   *****/
	var fec_p1 = new Date(anio1, (parseInt(mts1) - 1), dia1);
	var fec_p2 = new Date(anio2, (parseInt(mts2) - 1), dia2);
	var serieValores = data["SerieValores"];
	var serievaloresseleccionada = [[]];
	if (serieValores != null) {
		var j = 0;
		for (var i = 0; i < serieValores.length; i++) {
			var aux = serieValores[i][0];
			if (aux != null) {
				var fec001 = aux.split("-");
				fec001 = new Date(fec001[0], (parseInt(fec001[1]) - 1), fec001[2])
				var valor = serieValores[i][1];
				if (fec_p1 <= fec001 && fec_p2 >= fec001) {
					serievaloresseleccionada[j] = [fec001, valor];
					j++;
				}
			}
			else {
				var aux = (serieValores[i].valorFecha);
				var fec001 = aux.split("-");
				fec001 = new Date(fec001[0], (parseInt(fec001[1]) - 1), fec001[2])
				var valor = serieValores[i][1];
				if (fec_p1 <= fec001 && fec_p2 >= fec001) {
					serievaloresseleccionada[j] = [fec001, valor];
					j++;
				}
			}

		}
	}

	var serieCantidad = data["SerieCantidad"];
	var seriecantidadsseleccionada = [[]];
	if (serieCantidad != null) {
		var j1 = 0;
		for (var i1 = 0; i1 < serieCantidad.length; i1++) {
			var aux = serieCantidad[i1][0]
			if (aux != null) {
				var fec002 = aux.split("-");
				fec002 = new Date(fec002[0], (parseInt(fec002[1]) - 1), fec002[2])
				var cantidad = serieCantidad[i1][1];
				if (fec_p1 <= fec002 && fec_p2 >= fec002) {
					seriecantidadsseleccionada[j1] = [fec002, cantidad];
					j1++;
				}
			}
			else {
				var aux = (serieCantidad[i1].valorFecha);
				var fec002 = aux.split("-");
				fec002 = new Date(fec002[0], (parseInt(fec002[1]) - 1), fec002[2])
				var cantidad = serieCantidad[i1].serieValor;
				if (fec_p1 <= fec002 && fec_p2 >= fec002) {
					seriecantidadsseleccionada[j1] = [fec002, cantidad];
					j1++;
				}
			}

		}
	}
	//actualiza el slider
	$("#" + slider).dateRangeSlider("values", fec_p1, fec_p2);
	if (slider.length) {
		var datesinit = {
			values: {
				min: fec_p1,
				max: fec_p2,
			},
			updateselects_no: true,
		}
		$("#" + slider).trigger("valuesChanged", datesinit);
	}
}

function changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, slider) {

	var anio1, mes1, dia1, anio1;
	var anio2, mes2, dia2, anio2;

	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(comptrimestral1 + "_sel_anio");
	var secValue = selAnio1.options[1].value;
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;

	//Primer y ultimo valor de trimestre
	var selQuarter1 = document.getElementById(comptrimestral1 + "_sel_trimestre");
	var secTriValue = selQuarter1.options[1].value;
	var lastTriValue = selQuarter1.options[selQuarter1.options.length - 1].value;

	if ($("#" + comptrimestral1 + "_sel_anio").length) {
		anio1 = $("#" + comptrimestral1 + "_sel_anio").val();
		if (anio1 == "") {
			anio1 = secValue;
		}
	}

	if ($("#" + comptrimestral1 + "_sel_trimestre").length) {
		var valor = $("#" + comptrimestral1 + "_sel_trimestre").val();
		if (valor == "") {
			valor = secTriValue;
		}
		switch (valor) {
			case "1":
				mes1 = "0";
				break;
			case "4":
				mes1 = "3";
				break;
			case "7":
				mes1 = "6";
				break;
			case "10":
				mes1 = "9";
				break;
			default:
				mes1 = "0";
		}
	} else {
		mes1 = "0"
	}

	dia1 = "01";

	if ($("#" + comptrimestral2 + "_sel_anio").length) {
		anio2 = $("#" + comptrimestral2 + "_sel_anio").val();
		if (anio2 == "") {
			anio2 = lastValue;
		}
	}

	if ($("#" + comptrimestral2 + "_sel_trimestre").length) {
		var valor2 = $("#" + comptrimestral2 + "_sel_trimestre").val();

		if (valor2 == "") {
			valor2 = lastTriValue;
		}

		if (parseInt(anio1) > parseInt(anio2)) {
			anio1 = secValue;
			anio2 = lastValue;
		}

		if (parseInt(anio1) == parseInt(anio2) && parseInt(valor) >= parseInt(valor2)) {
			mes1 = "0";
			valor2 = lastTriValue;
		}

		if (parseInt(anio1) == lastValue && $("#" + comptrimestral2 + "_sel_semestre").length <= 2) {
			anio1 = lastValue - 1;
		}

		switch (valor2) {
			case "1":
				mes2 = "0";
				break;
			case "4":
				mes2 = "3";
				break;
			case "7":
				mes2 = "6";
				break;
			case "10":
				mes2 = "9";
				break;
		}
	} else {
		mes2 = "0";
	}
	dia2 = "01";
	var inicio = new Date(anio1, mes1, dia1);
	var fin = new Date(anio2, mes2, dia2);

	addaniotimes(data, "#" + comptrimestral1 + "_sel_anio", inicio);
	addtrimestretimes(data, "#" + comptrimestral1 + "_sel_trimestre", inicio);
	addaniotimes(data, "#" + comptrimestral2 + "_sel_anio", fin);
	addtrimestretimes(data, "#" + comptrimestral2 + "_sel_trimestre", fin);

}

function changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, slider) {

	//var fCount = data.
	var anio1, mes1, dia1, anio1;
	var anio2, mes2, dia2, anio2;

	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(compsemestral1 + "_sel_anio");
	var secValue = selAnio1.options[1].value;
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;

	//Primer y ultimo valor de año
	var selSem1 = document.getElementById(compsemestral1 + "_sel_semestre");

	if (selSem1 != null) {
		var secSemValue = selSem1.options[1].value;
		var lastSemValue = selSem1.options[selSem1.options.length - 1].value;
	}


	if ($("#" + compsemestral1 + "_sel_anio").length) {
		anio1 = $("#" + compsemestral1 + "_sel_anio").val();
		if (anio1 == "") {
			anio1 = secValue;
		}
	}

	if ($("#" + compsemestral1 + "_sel_semestre").length) {
		var valor = $("#" + compsemestral1 + "_sel_semestre").val();

		if (valor == "") {
			valor = secSemValue;
		}

		switch (valor) {
			case "1":
				mes1 = "0";
				break;
			case "7":
				mes1 = "6";
				break;
		}
	} else {
		mes1 = "0"
	}

	dia1 = "01";

	if ($("#" + compsemestral2 + "_sel_anio").length) {
		anio2 = $("#" + compsemestral2 + "_sel_anio").val();

		if (anio2 == "") {
			anio2 = lastValue;
		}
	} else {
		anio2 = "2010";
	}

	if ($("#" + compsemestral2 + "_sel_semestre").length) {
		var valor2 = $("#" + compsemestral2 + "_sel_semestre").val();

		if (valor2 == "") {
			valor2 = lastSemValue;
		}

		if (parseInt(anio1) > parseInt(anio2)) {
			anio1 = lastValue;
			anio2 = lastValue;
		}

		/*if(parseInt(valor) > parseInt(valor2)){
			mes1 = "0";
			valor2 = lastSemValue;
		}*/

		if (parseInt(anio1) == parseInt(anio2) && parseInt(valor) == parseInt(valor2)) {
			mes1 = "0";
			valor2 = lastSemValue;
		}

		if (parseInt(anio1) == lastValue && $("#" + compsemestral2 + "_sel_semestre").length <= 2) {
			anio1 = lastValue - 1;
		}

		switch (valor2) {
			case "1":
				mes2 = "0";
				break;
			case "7":
				mes2 = "6";
				break;
		}
	} else {
		if (parseInt(anio1) >= parseInt(anio2)) {
			anio1 = secValue;
			anio2 = lastValue;
		}
		mes2 = "0";
	}
	dia2 = "01";

	var inicio = new Date(anio1, mes1, dia1);
	var fin = new Date(anio2, mes2, dia2);
	//
	addaniotimes(data, "#" + compsemestral1 + "_sel_anio", inicio);
	addsemestretimes(data, "#" + compsemestral1 + "_sel_semestre", inicio);
	addaniotimes(data, "#" + compsemestral2 + "_sel_anio", fin);
	addsemestretimes(data, "#" + compsemestral2 + "_sel_semestre", fin);
}

function valuesPlotChangedTimes(dataSlider, controllerPlot, targetPlot, compdiario1, compdiario2, data, tipo) {

	try {
		minimo = dataSlider.values.min;
		maximo = dataSlider.values.max;

		xStart2 = (targetPlot.axes.xaxis.u2p(minimo)).toFixed(20);
		xEnd2 = (targetPlot.axes.xaxis.u2p(maximo)).toFixed(20);
		
		var v = targetPlot;
		var x = controllerPlot.plugins.cursor;
		var w = v.axes;
		x._zoom.zooming = true;
		x._zoom.started = true;
		x._zoom.start = [xStart2, 1];
		var gridEnd = {
			x: xEnd2,
			y: 1,
		};
		x._zoom.end = [xEnd2, 1];
		x._zoom.gridpos = gridEnd;
		
		var dataEnd = {
			xaxis: maximo,
			yaxis: 1,
		}
		x._zoom.axes.start = {
			xaxis: minimo,
			yaxis: 1,
		}
		x._zoom.axes.end = {
			xaxis: maximo,
			yaxis: 1,
		}
		if (minimo instanceof Date) {
			w.xaxis.tickInterval = getTickInterval(tipo);
			w.xaxis.numberTicks = getTicks(minimo, maximo, tipo);
		}
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
	} catch (err) {
		console.log(err);
	}
}

function valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, compdiario1, compdiario2, data, tipo) {
	var minimo = dataSlider.values.min;
	var maximo = dataSlider.values.max;

	var xStart2 = (targetPlot.axes.xaxis.u2p(minimo)).toFixed(20);
	var xEnd2 = (targetPlot.axes.xaxis.u2p(maximo)).toFixed(20);

	var v = targetPlot;
	var x = controllerPlot.plugins.cursor;
	var w = v.axes;

	x._zoom.zooming = true;
	x._zoom.started = true;
	x._zoom.start = [xStart2, 1];
	var gridEnd = {
		x: xEnd2,
		y: 1,
	};
	x._zoom.end = [xEnd2, 1];
	x._zoom.gridpos = gridEnd;
	var dataEnd = {
		xaxis: maximo,
		yaxis: 1,
	}
	var diferencia = Math.abs(Math.round(maximo - minimo));
	if (diferencia <= 12) {
		w.xaxis.numberTicks = diferencia + 1;
		w.xaxis.tickInterval = 1;
	}

	x._zoom.axes.start = {
		xaxis: minimo,
		yaxis: 1,
	}
	x._zoom.axes.end = {
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

function startvaluescomponents(valchanged1, valchanged2, compdiario1, compdiario2, data) {
	var inicioX;
	var endX;
	if (valchanged1 != null && valchanged2 != null) {
		inicioX = valchanged1;
		endX = valchanged2;
	} else {
		inicioX = data["startX"];
		endX = data["endX"];
	}
	var selanio1 = '#' + compdiario1 + "_sel_anio";
	var selmes1 = '#' + compdiario1 + "_sel_mes";
	var seldia1 = '#' + compdiario1 + "_sel_dia";
	var seltrimestre1 = '#' + compdiario1 + "_sel_trimestre";
	var selsemestre1 = '#' + compdiario1 + "_sel_semestre";

	addanio(data, selanio1, inicioX);
	addmeses(data, selanio1, selmes1, inicioX);
	adddias(data, selanio1, selmes1, seldia1, inicioX);
	addtrimestre(data, selanio1, selmes1, seltrimestre1, inicioX);
	addsemestre(data, selanio1, selmes1, selsemestre1, inicioX);

	var selanio2 = '#' + compdiario2 + "_sel_anio";
	var selmes2 = '#' + compdiario2 + "_sel_mes";
	var seldia2 = '#' + compdiario2 + "_sel_dia";
	var seltrimestre2 = '#' + compdiario2 + "_sel_trimestre";
	var selsemestre2 = '#' + compdiario2 + "_sel_semestre";

	addanio(data, selanio2, endX);
	addmeses(data, selanio2, selmes2, endX);
	adddias(data, selanio2, selmes2, seldia2, endX);
	addtrimestre(data, selanio2, selmes2, seltrimestre2, endX);
	addsemestre(data, selanio2, selmes2, selsemestre2, endX);

}

function addanio(data, selanio, selected) {
	if ($(selanio).length) {
		var selectanio = $(selanio);
		selectanio.empty();
		var option = $('<option/>').val("").text("Año");
		option.appendTo(selectanio);
		var fechas = data["Ticks"];
		var years = new Set();;
		for (var i = 0; i < fechas.length; i++) {
			years.add(new Date(fechas[i]).getFullYear());
		}
		if (years.length > 0) {
			for (var i1 = 0; i1 < years.length; i1++) {
				option = $('<option/>').val(years[i1]).text(years[i1]);
				option.appendTo(selectanio);
			}//for
		}//of
		selectanio.val(new Date(selected).getFullYear());
	}
}

function addmeses(data, selanio, selmes, selected) {
	if ($(selanio).length && $(selmes).length) {
		var selectmes = $(selmes);
		selectmes.empty();
		var option = $('<option/>').val("").text("Mes");
		option.appendTo(selectmes);
		var anioselected = (new Date(selected)).getFullYear();
		var messelected = (new Date(selected)).getMonth();
		var fechas = data["Ticks"];
		var mes_max = -1;
		for (var i = 0; i < fechas.length; i++) {
			var fecha = new Date(fechas[i]);
			if (fecha.getFullYear() == anioselected) {
				if (fecha.getMonth() > mes_max) mes_max = fecha.getMonth();
			}//
		}//
		mes_max = mes_max + 1;
		if (mes_max >= 1) {
			option = $('<option/>').val("1").text("Enero");
			option.appendTo(selectmes);
		}
		if (mes_max >= 2) {
			option = $('<option/>').val("2").text("Febrero");
			option.appendTo(selectmes);
		}
		if (mes_max >= 3) {
			option = $('<option/>').val("3").text("Marzo");
			option.appendTo(selectmes);
		}
		if (mes_max >= 4) {
			option = $('<option/>').val("4").text("Abril");
			option.appendTo(selectmes);
		}
		if (mes_max >= 5) {
			option = $('<option/>').val("5").text("Mayo");
			option.appendTo(selectmes);
		}
		if (mes_max >= 6) {
			option = $('<option/>').val("6").text("Junio");
			option.appendTo(selectmes);
		}
		if (mes_max >= 7) {
			option = $('<option/>').val("7").text("Julio");
			option.appendTo(selectmes);
		}
		if (mes_max >= 8) {
			option = $('<option/>').val("8").text("Agosto");
			option.appendTo(selectmes);
		}
		if (mes_max >= 9) {
			option = $('<option/>').val("9").text("Septiembre");
			option.appendTo(selectmes);
		}
		if (mes_max >= 10) {
			option = $('<option/>').val("10").text("Octubre");
			option.appendTo(selectmes);
		}
		if (mes_max >= 11) {
			option = $('<option/>').val("11").text("Noviembre");
			option.appendTo(selectmes);
		}
		if (mes_max >= 12) {
			option = $('<option/>').val("12").text("Diciembre");
			option.appendTo(selectmes);
		}
		selectmes.val(messelected + 1);
	}
}

function adddias(data, selanio, selmes, seldias, selected) {
	if ($(seldias).length) {
		var selectdia = $(seldias);
		selectdia.empty();
		var fecha = new Date(selected);
		var ultimoDia = new Date(fecha.getFullYear(), fecha.getMonth() + 1, 0).getDate();
		for (var i = 1; i <= ultimoDia; i++) {
			var option = $('<option/>').val(i).text(i);
			option.appendTo(selectdia);
		};
		selectdia.val(fecha.getDate());
	}
}

function addtrimestre(data, aniocomp, mescomp, trimestre, selected) {
	if ($(trimestre).length) {
		var selecttrimestre = $(trimestre);
		selecttrimestre.empty();
		var option = $('<option/>').val("1").text("I");
		option.appendTo(selecttrimestre);
		option = $('<option/>').val("4").text("II");
		option.appendTo(selecttrimestre);
		option = $('<option/>').val("7").text("III");
		option.appendTo(selecttrimestre);
		option = $('<option/>').val("10").text("IV");
		option.appendTo(selecttrimestre);
		var trimestreseleccionado = new Date(selected).getMonth() + 1;
		selecttrimestre.val("" + trimestreseleccionado + "");
	}
}

function addsemestre(data, aniocomp, mescomp, semestre, selected) {
	if ($(semestre).length) {
		var selectsemestre = $(semestre);
		selectsemestre.empty();
		var option = $('<option/>').val("1").text("I");
		option.appendTo(selectsemestre);
		option = $('<option/>').val("7").text("II");
		option.appendTo(selectsemestre);
		var semestreseleccionado = new Date(selected).getMonth() + 1;
		selectsemestre.val("" + semestreseleccionado + "");
	}
}

function changedatesselecteds(compdiario1, compdiario2, data, slider) {
	var anio1, mes1, dia1;
	var anio2, mes2, dia2;

	if ($("#" + compdiario1 + "_sel_anio").length) {
		anio1 = $("#" + compdiario1 + "_sel_anio").val();
	}
	if ($("#" + compdiario1 + "_sel_mes").length) {
		mes1 = $("#" + compdiario1 + "_sel_mes").val();
	} else {
		mes1 = "01"
	}

	if ($("#" + compdiario1 + "_sel_dia").length) {
		dia1 = $("#" + compdiario1 + "_sel_dia").val();
	} else {
		dia1 = "01";
	}
	if ($("#" + compdiario2 + "_sel_anio").length) {
		anio2 = $("#" + compdiario2 + "_sel_anio").val();
	} else {
		anio2 = "2010";
	}
	if ($("#" + compdiario2 + "_sel_mes").length) {
		mes2 = $("#" + compdiario2 + "_sel_mes").val();
	} else {
		mes2 = "01";
	}

	if ($("#" + compdiario2 + "_sel_dia").length) {
		dia2 = $("#" + compdiario2 + "_sel_dia").val();
	} else {
		dia2 = "01";
	}
	var fecha1 = new Date(anio1, mes1 - 1, dia1);
	var fecha2 = new Date(anio2, mes2 - 1, dia2);
	var datesinit = {
		values: {
			min: fecha1,
			max: fecha2,
		},
		updateselects_no: true,
	}
	$("#" + slider).trigger("valuesChanged", datesinit);
}

function changedatesselectedstrimestrales(comptrimestral1, comptrimestral2, data, slider) {
	var anio1, mes1, dia1;
	var anio2, mes2, dia2;

	if ($("#" + comptrimestral1 + "_sel_anio").length) {
		anio1 = $("#" + comptrimestral1 + "_sel_anio").val();
	}
	if ($("#" + comptrimestral1 + "_sel_trimestre").length) {
		var valor = $("#" + comptrimestral1 + "_sel_trimestre").val();
		switch (valor) {
			case "1":
				mes1 = "0";
				break;
			case "4":
				mes1 = "3";
				break;
			case "7":
				mes1 = "6";
				break;
			case "10":
				mes1 = "9";
				break;
		}
	} else {
		mes1 = "0"
	}

	dia1 = "01";

	if ($("#" + comptrimestral2 + "_sel_anio").length) {
		anio2 = $("#" + comptrimestral2 + "_sel_anio").val();
	} else {
		anio2 = "2010";
	}

	if ($("#" + comptrimestral2 + "_sel_trimestre").length) {
		valor = $("#" + comptrimestral2 + "_sel_trimestre").val();
		switch (valor) {
			case "1":
				mes2 = "0";
				break;
			case "4":
				mes2 = "3";
				break;
			case "7":
				mes2 = "6";
				break;
			case "10":
				mes2 = "9";
				break;
		}
	} else {
		mes2 = "0";
	}
	dia2 = "01";

	var fecha1 = new Date(anio1, mes1, dia1);
	var fecha2 = new Date(anio2, mes2, dia2);

	var datesinit = {
		values: {
			min: fecha1,
			max: fecha2,
		},
		updateselects_no: true,
	}
	$("#" + slider).trigger("valuesChanged", datesinit);
}

function changedatesselectedssemestrales(compsemestral1, compsemestral2, data, slider) {

	var anio1, mes1, dia1;
	var anio2, mes2, dia2;

	if ($("#" + compsemestral1 + "_sel_anio").length) {
		anio1 = $("#" + compsemestral1 + "_sel_anio").val();
	}

	if ($("#" + compsemestral1 + "_sel_semestre").length) {
		var valor = $("#" + compsemestral1 + "_sel_semestre").val();
		switch (valor) {
			case "1":
				mes1 = "0";
				break;
			case "7":
				mes1 = "6";
				break;
		}
	} else {
		mes1 = "0"
	}

	dia1 = "01";

	if ($("#" + compsemestral2 + "_sel_anio").length) {
		anio2 = $("#" + compsemestral2 + "_sel_anio").val();
	} else {
		anio2 = "2010";
	}

	if ($("#" + compsemestral2 + "_sel_semestre").length) {
		valor = $("#" + compsemestral2 + "_sel_semestre").val();
		switch (valor) {
			case "1":
				mes2 = "0";
				break;
			case "7":
				mes2 = "6";
				break;
		}
	} else {
		mes2 = "0";
	}
	dia2 = "01";

	var fecha1 = new Date(anio1, mes1, dia1);
	var fecha2 = new Date(anio2, mes2, dia2);
	var datesinit = {
		values: {
			min: fecha1,
			max: fecha2,
		},
		updateselects_no: true,
	}
	$("#" + slider).trigger("valuesChanged", datesinit);
}

function getDateFromSlider(fechaSlider, tipo) {
	var fechaRes, mes;
	mes = fechaSlider.getMonth();
	var anio = fechaSlider.getFullYear();
	var dia = "01";
	switch (tipo) {
		case 1:
			fechaRes = fechaSlider;
			break;
		case 2:
			fechaRes = fechaSlider;
			break;
		case 3:
			if (mes >= 0 && mes <= 2) mes = 0;
			if (mes >= 3 && mes <= 5) mes = 3;
			if (mes >= 6 && mes <= 8) mes = 6;
			if (mes >= 9 && mes <= 11) mes = 9;
			fechaRes = new Date(anio, mes, dia);
			break;
		case 4:
			if (mes >= 0 && mes <= 5) mes = 0;
			if (mes >= 6 && mes <= 11) mes = 6;
			fechaRes = new Date(anio, mes, dia);
			break;
		case 5:
			mes = "01";
			fechaRes = new Date(anio, mes, dia);
			break;
	}
	return fechaRes;
}

function valuesPlotChanged(dataSlider, controllerPlot, targetPlot, compdiario1, compdiario2, data, tipo) {

	var xDateStart = getDateFromSlider(dataSlider.values.min, tipo);
	var xDateEnd = getDateFromSlider(dataSlider.values.max, tipo);

	var xStart = (controllerPlot.axes.xaxis.u2p(dataSlider.values.min)).toFixed(2);
	var xEnd = (controllerPlot.axes.xaxis.u2p(dataSlider.values.max)).toFixed(2);
	var v = targetPlot;
	var x = controllerPlot.plugins.cursor;

	var updateperiodo = dataSlider.updateperiodo;

	x._zoom.zooming = true;
	x._zoom.started = true;
	x._zoom.start = [xStart, 1];
	var gridEnd = {
		x: xEnd,
		y: 1,
	};
	x._zoom.end = [xEnd, 1];
	x._zoom.gridpos = gridEnd;
	var dataEnd = {
		xaxis: xDateEnd,
		yaxis: 1,
	}
	/**/
	x._zoom.axes.start = {
		xaxis: xDateStart,
		yaxis: 1,
	}
	x._zoom.axes.end = {
		xaxis: xDateEnd,
		yaxis: 1,
	}
	//x.doZoom(gridEnd, dataEnd, v, x);
	x.doZoom(gridEnd, dataEnd, v, x)
	x._zoom.started = false;
	x._zoom.zooming = false;
	//j(document).unbind("mousemove.jqplotCursor", h);
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
	if (updateperiodo == null || updateperiodo == true || updateperiodo == "undefined" || updateperiodo.length) {
		startvaluescomponents(xDateStart, xDateEnd, compdiario1, compdiario2, data);
	}
}

function resizePlot(zoomplot, miniplot, idMini, idDivSlider) {
	zoomplot.replot({ resetAxes: false });
	/*get positions super plot*/
	var s_left = zoomplot._defaultGridPadding.left;
	var s_right = zoomplot._defaultGridPadding.right;
	var left = zoomplot._gridPadding.left;
	var right = zoomplot._gridPadding.right;
	var width = zoomplot._plotDimensions.width;
	var style = "width:".concat((width - left)).concat("px !important; ")
		.concat("left:").concat(0).concat("px !important;")
		.concat("right:").concat(right - s_right).concat("px !important;")
		.concat("height:").concat(55).concat("px !important;")
		;

	;
	$("#" + idMini)[0].setAttribute("style", style);
	miniplot.replot({ resetAxes: false });
	var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
		.concat("margin-top:").concat(-8).concat("px !important;")
		.concat("left:").concat(left - s_left - 12).concat("px !important;")
		.concat("right:").concat(right).concat("px !important;")
		.concat("max-width:").concat("100% !important;")
		.concat("position: relative;")
		;
	$("#" + idDivSlider)[0].setAttribute("style", styleSlider);
	$("#" + idDivSlider).dateRangeSlider('resize');
}

function resizePlotSingle(zoomplot, miniplot, idMini, idDivSlider) {
	zoomplot.replot({ resetAxes: false });
	/*get positions super plot*/
	var s_left = zoomplot._defaultGridPadding.left;
	var s_right = zoomplot._defaultGridPadding.right;
	var left = zoomplot._gridPadding.left;
	var right = zoomplot._gridPadding.right;
	var width = zoomplot._plotDimensions.width;
	var style = "width:".concat((width)).concat("px !important; ")
		.concat("left:").concat(0).concat("px !important;")
		.concat("right:").concat(right - s_right).concat("px !important;")
		.concat("height:").concat(55).concat("px !important;")
		;

	;
	$("#" + idMini)[0].setAttribute("style", style);
	miniplot.replot({ resetAxes: false });
	var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
		.concat("margin-top:").concat(-8).concat("px !important;")
		.concat("left:").concat(left - s_left - 12).concat("px !important;")
		.concat("right:").concat(right).concat("px !important;")
		.concat("max-width:").concat("100% !important;")
		.concat("position: relative;")
		;
	$("#" + idDivSlider)[0].setAttribute("style", styleSlider);
	$("#" + idDivSlider).dateRangeSlider('resize');
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

function formatterLabel(tipo, valor) {
	var fecvar = valor.split("-");
	var anio = fecvar[0];
	var mes = fecvar[1];
	var dia = fecvar[2];
	switch (tipo) {
		case 1: return anio + "-" + mes + "-" + dia;
		case 2: return anio + "-" + mes;
		case 3:
			var month = parseInt(mes);
			var year = parseInt(anio);
			var df = "" + year;
			if (month <= 2) {
				df = + df + "-I";
			} else if (month <= 5) {
				df = df + "-II";
			} else if (month <= 8) {
				df = df + "-III";
			} else {
				df = df + "-IV";
			}
			return df;
		case 4:
			var month1 = parseInt(mes);
			var year1 = parseInt(anio);
			var df1 = "" + year1;
			if (month1 <= 5) {
				df1 = + df1 + "-I";
			} else {
				df1 = + df1 + "-II";
			}
			return df1;
		case 5:
			return anio;
	}
}

function getTickInterval(tipo) {
	var mintickinterval;
	switch (tipo) {
		case 1: mintickinterval = "1 days";
			break;
		case 2: mintickinterval = "1 month";
			break;
		case 3: mintickinterval = "6 months";
			break;
		case 4: mintickinterval = "12 months";
			break;
		case 5: mintickinterval = "2 years";
			break;
	}
	return mintickinterval
}

function getTicks(min, max, tipo) {
	var resultado;
	switch (tipo) {
		case 1:
			var dias = Math.abs(Math.round((min.getTime() - max.getTime()) / (60 * 60 * 1000 * 24)));
			if (dias <= 10) resultado = dias;
			else resultado = "10"
			break;
		case 2:
			var meses = Math.abs(Math.round((min.getTime() - max.getTime()) / (60 * 60 * 1000 * 24 * 30)));
			if (meses <= 10) resultado = meses;
			else resultado = "10"
			break;
		case 3:
			var trimestres = Math.abs(Math.round((min.getTime() - max.getTime()) / (60 * 60 * 1000 * 24 * 90)));
			if (trimestres <= 10) resultado = trimestres;
			else resultado = 10;
			break;
		case 4:
			var semestres = Math.abs(Math.round((min.getTime() - max.getTime()) / (60 * 60 * 1000 * 24 * 180)));
			if (semestres <= 10) resultado = semestres;
			else resultado = 10;
			break;
		case 5:
			var anios = Math.abs(min.getFullYear() - max.getFullYear());
			if (anios <= 10) resultado = anios;
			else resultado = 10;
			break;
	}
	return resultado == 1 ? 2 : resultado;
}


/**
 * BASE 
 */

function createplotmax(name, data, tipo) {

	var title, serieValores, serieCantidad, minValor, minCantidad;
	title = data["Title"];
	serieValores = data["SerieValores"];
	serieValores = parsedata(serieValores);
	serieCantidad = data["SerieCantidad"];
	minValor = data["MinValor"];
	minCantidad = data["MinCantidad"];
	var maxValor = data["MaxValor"];
	var maxCantidad = data["MaxCantidad"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [serieValores, serieCantidad], {
		sprintf: {
			thousandsSeparator: '.',
			decimalMark: ',',
		},

		//use_excanvas : false,		
		title: title,
		// Provide a custom seriesColors array to override the default colors.
		textColor: "#003E6C",
		seriesColors: ['#850024', '#006fb9'],

		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
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
			showSwatches: true,
			//xoffset: 90,
			//yoffset: 45,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 5,
			rowSpacing: "0px",
			rendererOptions: {
				//		          numberRows: '2',
				//		          numberColumns: '2'
				seriesToggle: "true",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},

		series: [
			{
				color: '#850024',
				lineWidth: 0.5,
				shadow: true,
				label: 'Valor',
				markerOptions: { size: 4, style: 'filledCircle' },
				rendererOptions: {
					smooth: false,
					css: {
						background: '#850024'
					}
				}
			},
			{
				yaxis: 'y2axis',
				lineWidth: 0.5,
				shadow: true,
				label: 'Número Transacciones',
				markerOptions: {
					size: 4,
					style: 'filledCircle'
				},
				rendererOptions: {
					smooth: false,
					css: {
						background: '#006fb9'
					}
				}
			},
		],
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
		},

		cursor: {
			show: true,
			zoom: true,
			showTooltip: true,
			dblClickReset: false,
		},

		highlighter: {
			show: true,
			sizeAdjust: 1,
			tooltipOffset: 9,
			useYTickMarks: true,
			useXTickMarks: true,
			currentNeighbor: null,
			showMarker: true,
			lineWidthAdjust: 1,
			showTooltip: true,
			tooltipLocation: 'e',
			fadeTooltip: false,
			tooltipAxes: '(x , y)',
			tooltipSeparator: ', ',
			useAxesFormatters: true,
		},

		axesDefaults: {
			//	    	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
			numberTicks: 5,
			showTickMarks: true,
			useSeriesColor: true,
			rendererOptions: {
				alignTicks: false,
			}
		},

		axes: {
			xaxis: {
				numberTicks: 5,
				tickInterval: '3 months',
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				renderer: $.jqplot.DateAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: -45,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: true,
					size: 4,
					markSize: 4,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				label: 'Valor',
				fontSize: '6px',
				showLabel: true,
				textColor: "#850024",
				show: true,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: true,
					pad: 1.2,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#850024",
					formatString: "%'.2f",
				}
			},
			y2axis: {
				min: minCantidad,
				max: maxCantidad,
				showLabel: true,
				label: 'Número Transacciones',
				textColor: "#006fb9",
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: true,
					size: 4,
					markSize: 4,
					show: true,
					pad: 1.2,
					formatString: '%.0f',
					textColor: "#006fb9",
					fontFamily: '"Roboto", sans-serif',
					fontSize: "8pt",
				},
			},
		},
	});
	return plot2;
}

function createplotmin(name, data, tipo) {

	var title, ticks, serieValores, serieCantidad, minValor, minCantidad;
	serieValores = data["SerieValores"];
	serieValores = parsedata(serieValores);
	serieCantidad = data["SerieCantidad"];
	minValor = data["MinValor"];
	minCantidad = data["MinCantidad"];
	var maxValor = data["MaxValor"];
	var maxCantidad = data["MaxCantidad"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];

	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot1 = $.jqplot(name, [serieValores, serieCantidad], {
		// Provide a custom seriesColors array to override the default colors.
		textColor: "#003E6C",
		seriesColors: ['#850024', '#006fb9'],
		seriesDefaults: {
			lineWidth: 0.5,
			fill: false,
			fillAndStroke: false,
			showMarker: true,
			markerOptions: {
				size: 0,
				style: 'filledCircle'
			},
			rendererOptions: {
				varyBarColor: true,
				smooth: false,
				fillToZero: true,
			},
			animation: {
				show: false
			},
		},
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0
		},
		axesDefaults: {
			showTickMarks: false,
			drawBaseline: false,
			numberTicks: 5,
			useSeriesColor: false,
			rendererOptions: {
				alignTicks: false,
			}
		},
		axes: {
			xaxis: {
				numberTicks: 5,
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				renderer: $.jqplot.DateAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: -45,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: false,
					size: 4,
					markSize: 4,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				label: 'Valor',
				fontSize: '6px',
				showLabel: false,
				textColor: "#850024",
				show: false,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: false,
					pad: 1.2,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#850024",
					formatString: "%'.2f",
				}
			},
			y2axis: {
				min: minCantidad,
				max: maxCantidad,
				showLabel: false,
				label: 'Número Transacciones',
				textColor: "#006fb9",
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					size: 4,
					markSize: 4,
					show: false,
					pad: 1.2,
					formatString: '%.0f',
					textColor: "#006fb9",
					fontFamily: '"Roboto", sans-serif',
					fontSize: "8pt",
				},
			},
		},
		cursor: {
			show: false,
			//		       showTooltip: true,
			zoom: true,
			showTooltip: false,
			constrainZoomTo: 'x',
			//		       tooltipAxisGroups: {
			//		    	   xaxis:10, 
			//		    	   yaxis:20,
			//		       },
			looseZoom: true,
			dblClickReset: false,
		}
	});
	return plot1;
}

var mensageError = "No hay datos para visualizar";

function brmensaje(message, type) {
	return '<div class="ui-messages-' + type + ' ui-corner-all">' +
		'<a href="#" class="ui-messages-close" onclick="$(this).parent().slideUp();return false;">' +
		'<span class="ui-icon ui-icon-close" />' +
		'</a>' +
		'<span class="ui-messages-' + type + '-icon" />' +
		'<ul>' +
		'<li>' +
		'<span class="ui-messages-' + type + '-summary">' +
		message +
		'</span>' +
		'</li>' +
		'</ul>' +
		'</div>';
};


/**
 * TRANSFERENCIAS
 **/

var datatransferdiaria;
function scroll() {

	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	{

		try {
			var bodyheight = document.getElementById('chartzoomslidertransf').clientHeight;


		}
		catch (err) {
			var bodyheight = 0;
		}

		if (bodyheight > 0) {

			window.scrollTo(0, bodyheight);
		}

	} else {
		window.scrollTo(0, 300);

	}
}

function sleep (time) {
  return new Promise((resolve) => setTimeout(resolve, time));
}

function createSliderMinDiario(divchartzoomslider, compdiario1, compdiario2, concepto, label, errormessage, almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getTransferencias({ 'concepto': concepto });
		$("#" + divchartzoomslider).empty();
		$("#" + divchartzoomslider)[0].setAttribute("class", "superzoom");
		if (label == null || label == "undefined") label = "Todos";
		data["Title"] = data["Title"] + " - " + label;

		vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		ticks = data["Ticks"];
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd,10);
		
		ticks = data["Ticks"];
		//		
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		from=datestart;
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			from = dateend.getTime() - (dia * 180);
			from = new Date(from);
			
			from2= dateend.getTime() - (dia * 9);
			from2 = new Date(from2);
			vStorageFecStart = completarFechaStart(from2,ticks,vStorageFecEnd,10);
			vStorageFecEnd=dateend;
		}
		from2=vStorageFecStart;
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compdiario1, compdiario2, data);
		
		divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom";
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneral(divchartmax.id, data, 1);
		xmin = targetPlot.axes.xaxis.min;
		xmax = targetPlot.axes.xaxis.max;
		s_left = targetPlot._defaultGridPadding.left;
		s_right = targetPlot._defaultGridPadding.right;
		left = targetPlot._gridPadding.left;
		right = targetPlot._gridPadding.right;
		width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		style = "width:".concat((width - left)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right - s_right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmingeneral(idMini, data, 1);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		
		styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
			
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		

		var dateSlider;
		dateSlider= $("#" + idDivSlider).dateRangeSlider({
				range: {
					min: { days: 1 },
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
		
		//trigger first load
		var datesinit = {
			label: dateSlider,
			values: {
				min: from2,
				max: vStorageFecEnd,
			},
			first: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, fechaAActualizar) {
			valuesPlotChangedTimes(fechaAActualizar, controllerPlot, targetPlot, compdiario1, compdiario2, data, 1);
			if (fechaAActualizar.first == null || fechaAActualizar.first === undefined || fechaAActualizar.first == false) {
				changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
				updatevaluescomponentstimesfromslider(fechaAActualizar, compdiario1, compdiario2, data, 1);
			}
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});
		
		$("#" + compdiario1 + "_sel_anio").unbind("change");
		$("#" + compdiario1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_mes").unbind("change");
		$("#" + compdiario1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_dia").unbind("change");
		$("#" + compdiario1 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_anio").unbind("change");
		$("#" + compdiario2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_mes").unbind("change");
		$("#" + compdiario2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_dia").unbind("change");
		$("#" + compdiario2 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$(document).ready(function(){
			$("#" + idDivSlider).trigger("valuesChanged", datesinit);
			scroll();
  		});
 
  	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};


var datatransfermensual;
function createSliderMinMensual(divchartzoomslider, compmensual1, compmensual2, concepto, label, errormessage, almacen,onetime) {
	let targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getTransferenciasMensuales({ 'concepto': concepto });
		$("#" + divchartzoomslider).empty();
		$("#" + divchartzoomslider)[0].setAttribute("class", "superzoom");
		if (label == null || label == "undefined") label = "Todos";
		data["Title"] = data["Title"] + " - " + label;

		let vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		let vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		ticks = data["Ticks"];
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);

		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compmensual1, compmensual2, data);

		let divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneral(divchartmax.id, data, 2);
		let s_left = targetPlot._defaultGridPadding.left;
		let s_right = targetPlot._defaultGridPadding.right;
		let left = targetPlot._gridPadding.left;
		let right = targetPlot._gridPadding.right;
		let width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		let innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		let style = "width:".concat((width - left)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right - s_right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmingeneral(idMini, data, 2);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		let innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		let styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		let datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		
		let dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		let from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 30);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 

		let dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 1 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		//trigger second load
		var datesinit2 = {
			label: dateSliderBounds,
			values: {
				min: vStorageFecStart,
				max: vStorageFecEnd,
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compmensual1, compmensual2, data, 2);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compmensual1, compmensual2, data, 2);
				savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
			}
		});

		$("#" + compmensual1 + "_sel_anio").unbind();
		$("#" + compmensual1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual1 + "_sel_mes").unbind();
		$("#" + compmensual1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_anio").unbind();
		$("#" + compmensual2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_mes").unbind();
		$("#" + compmensual2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});
		
		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		$("#" + idDivSlider).trigger("valuesChanged", datesinit2);
		scroll();

		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datatransfertrimestral;
function createSliderMinTrimestral(divchartzoomslider, comptrimestral1, comptrimestral2, concepto, label, errormessage,almacen,onetime) {
	let targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getTransferenciasTrimestrales({ 'concepto': concepto });
		$("#" + divchartzoomslider).empty();
		$("#" + divchartzoomslider)[0].setAttribute("class", "superzoom");
		if (label == null || label == "undefined") label = "Todos";
		data["Title"] = data["Title"] + " - " + label;
		let vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		let vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");


		ticks = data["Ticks"];
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);

		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, comptrimestral1, comptrimestral2, data);
		let divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneral(divchartmax.id, data, 3);
		let s_left = targetPlot._defaultGridPadding.left;
		let s_right = targetPlot._defaultGridPadding.right;
		let left = targetPlot._gridPadding.left;
		let right = targetPlot._gridPadding.right;
		let width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		let innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		let style = "width:".concat((width - left)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right - s_right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmingeneral(idMini, data, 3);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		let innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		let styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		var from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 

		
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 3 },
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
		//trigger first loaf
		let datesinit = {
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			first: true
		}

		$("#" + idDivSlider).unbind("valuesChanged");
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, comptrimestral1, comptrimestral2, data, 3);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, comptrimestral1, comptrimestral2, data, 3);
			}
		});

		$("#" + comptrimestral1 + "_sel_anio").unbind("change");
		$("#" + comptrimestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral1 + "_sel_trimestre").unbind("change");
		$("#" + comptrimestral1 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_anio").unbind("change");
		$("#" + comptrimestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_trimestre").unbind("change");
		$("#" + comptrimestral2 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scroll();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

var datatransfersemestral;
function createSliderMinSemestral(divchartzoomslider, compsemestral1, compsemestral2, concepto, label, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getTransferenciasSemestrales({ 'concepto': concepto });
		$("#" + divchartzoomslider).empty();
		$("#" + divchartzoomslider)[0].setAttribute("class", "superzoom");
		if (label == null || label == "undefined") label = "Todos";
		data["Title"] = data["Title"] + " - " + label;
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		ticks = data["Ticks"];
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compsemestral1, compsemestral2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom";
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneral(divchartmax.id, data, 4);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width - left)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right - s_right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmingeneral(idMini, data, 4);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 
		
		
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 6 },
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
		//trigger first loaf
		var datesinit = {
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			first: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compsemestral1, compsemestral2, data, 4);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compsemestral1, compsemestral2, data, 4);
				savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
			}
		});

		$("#" + compsemestral1 + "_sel_anio").unbind();
		$("#" + compsemestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral1 + "_sel_semestre").unbind();
		$("#" + compsemestral1 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_anio").unbind();
		$("#" + compsemestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_semestre").unbind();
		$("#" + compsemestral2 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scroll();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

var datatransferanual;
function createSliderMinAnual(divchartzoomslider, companual1, companual2, concepto, label, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getTransferenciasAnuales({ 'concepto': concepto });
		ticks = data["Ticks"];
		
		$("#" + divchartzoomslider).empty();
		$("#" + divchartzoomslider)[0].setAttribute("class", "superzoom");
		if (label == null || label == "undefined") label = "Todos";
		data["Title"] = data["Title"] + " - " + label;


		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		ticks = data["Ticks"];
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, companual1, companual2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneral(divchartmax.id, data, 5);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width - left)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right - s_right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmingeneral(idMini, data, 5);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);

		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 

		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 12 },
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
		//trigger first loaf
		var datesinit = {
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, companual1, companual2, data, 5);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, companual1, companual2, data, 5);
				savedOldDatesStoStorage(almacen, companual1, companual2, "5");
			}
		});

		$("#" + companual1 + "_sel_anio").unbind("change");
		$("#" + companual1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + companual2 + "_sel_anio").unbind("change");
		$("#" + companual2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimesrangestran(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scroll();


	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}

};

function createplotmaxgeneral(name, data, tipo) {
	var title, serieValores, serieCantidad, minValor, minCantidad;
	title = data["Title"];
	//serieValores 	= data["SerieValores"];
	//serieValores = parsedata(serieValores);
	serieValores = datos(data["SerieValores"]);
	//serieCantidad	= data["SerieCantidad"];
	serieCantidad = datos(data["SerieCantidad"]);
	minValor = data["MinValor"];
	minCantidad = data["MinCantidad"];
	maxValor = data["MaxValor"];
	maxCantidad = data["MaxCantidad"];
	minX = data["MinX"];
	maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	fecha1 = minX.split("-");
	minX = new Date(fecha1[0], (parseInt(fecha1[1]) - 1), fecha1[2])
	fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0], (parseInt(fecha2[1]) - 1), fecha2[2]);

	var plot2 = $.jqplot(name, [serieValores, serieCantidad], {
		title: title,
		textColor: "#003E6C",
		seriesColors: ['#850024', '#006fb9'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
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
			label: '',
			showSwatches: true,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 5,
			rowSpacing: "0px",
			rendererOptions: {
				seriesToggle: "true",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},
		series: [
			{
				color: '#850024',
				lineWidth: 0.5,
				shadow: true,
				label: 'Valor',
				markerOptions: { size: 4, style: 'filledCircle' },
				rendererOptions: {
					smooth: false,
					css: {
						background: '#850024'
					}
				}
			},
			{
				yaxis: 'y2axis',
				lineWidth: 0.5,
				shadow: true,
				label: 'Número Transacciones',
				markerOptions: {
					size: 4,
					style: 'filledCircle'
				},
				rendererOptions: {
					smooth: false,
					css: {
						background: '#006fb9'
					}
				}
			},
		],
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
			left: "0",
		},
		cursor: {
			show: true,
			zoom: true,
			showTooltip: false,
			dblClickReset: false,
		},
		highlighter: {
			show: true,
			sizeAdjust: 1,
			tooltipOffset: 9,
			useYTickMarks: true,
			useXTickMarks: true,
			currentNeighbor: null,
			showMarker: true,
			lineWidthAdjust: 1,
			showTooltip: true,
			tooltipLocation: 'e',
			fadeTooltip: false,
			tooltipAxes: '(x , y)',
			tooltipSeparator: ', ',
			useAxesFormatters: true,
		},
		axesDefaults: {
			showTickMarks: true,
			useSeriesColor: true,
			rendererOptions: {
				alignTicks: true,
			}
		},
		axes: {
			xaxis: {
				numberTicks: getTicks(minX, maxX, tipo),
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				pad: 1.0,
				padMin: 1.0,
				tickInterval: getTickInterval(tipo),
				renderer: $.jqplot.DateAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: -45,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: true,
					size: 4,
					markSize: 4,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				label: '<div><div style="padding-left: 24px;">Valor</div><div style="color: gray; width: 120px !important">(Miles de Millones)<div></div>',
				showLabel: true,
				textColor: "#850024",
				show: true,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					forceTickAt0: true,
				},
				tickOptions: {
					showTicks: true,
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: true,
					pad: 1.0,
					Minpad: 1.0,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#850024",
					formatString: "%'.2f",
				}
			},
			y2axis: {
				min: minCantidad,
				max: maxCantidad,
				showLabel: true,
				label: '<div style="padding-left: 28px;"><div style="padding-left: 18px;">Número</div><div>Transacciones</div></div>',
				textColor: "#006fb9",
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					forceTickAt0: true,
				},
				tickOptions: {
					showTicks: true,
					fontSize: "8pt",
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: true,
					show: true,
					size: 4,
					markSize: 6,
					pad: 5.2,
					minPad: 1.5,
					textColor: "#006fb9",
					fontFamily: '"Roboto", sans-serif',
					formatString: "%'.0f",
				},
			},
		},
	});
	return plot2;
}

function createplotmingeneral(name, data, tipo) {
	var serieValores, serieCantidad, minValor, minCantidad;
	//serieValores 	= data["SerieValores"];
	//serieValores = parsedata(serieValores);
	serieValores = datos(data["SerieValores"]);
	//serieCantidad	= data["SerieCantidad"];
	serieCantidad = datos(data["SerieCantidad"]);
	minValor = data["MinValor"];
	minCantidad = data["MinCantidad"];
	maxValor = data["MaxValor"];
	maxCantidad = data["MaxCantidad"];
	minX = data["MinX"];
	maxX = data["MaxX"];
	fecha1 = minX.split("-");
	minX = new Date(fecha1[0], (parseInt(fecha1[1]) - 1), fecha1[2])
	fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0], (parseInt(fecha2[1]) - 1), fecha2[2]);

	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [serieValores, serieCantidad], {
		textColor: "#003E6C",
		seriesColors: ['#850024', '#006fb9'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
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
			labels: ['', ''],
			animation: {
				show: false
			},
		},

		legend: {
			show: false,
			label: '',
			showLabels: false,
			showSwatches: false,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 0,
			rowSpacing: "0px",
			rendererOptions: {
				seriesToggle: "false",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},

		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
			pad: 0,
		},
		cursor: {
			show: false,
			zoom: true,
			showTooltip: true,
			constrainZoomTo: 'x',
			dblClickReset: false,

		},
		highlighter: {
			show: false,
		},
		axesDefaults: {
			showTickMarks: false,
			useSeriesColor: false,
			drawBaseline: false,
			pad: 1.2,
			rendererOptions: {
				alignTicks: true,
			}
		},

		axes: {
			xaxis: {
				numberTicks: getTicks(minX, maxX, tipo),
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				pad: 1,
				renderer: $.jqplot.DateAxisRenderer,
				tickInterval: getTickInterval(tipo),
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: -45,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: false,
					size: 0,
					markSize: 0,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				showLabel: false,
				label: 'Cantidad',
				show: false,
				textColor: "#850024",
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					forceTickAt0: true,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontSize: "8pt",
					size: 0,
					markSize: 0,
					show: false,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#850024",
					formatString: "%'.2f",
				}
			},
			y2axis: {
				min: minCantidad,
				max: maxCantidad,
				showLabel: false,
				label: 'Número Transacciones',
				textColor: "#006fb9",
				show: false,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					forceTickAt0: true,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontSize: "8pt",
					size: 0,
					markSize: 0,
					show: false,
				},
			},
		},
	});
	return plot2;
}


/**
 * ROTACION 
 **/

function scrollrotacion() {

	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	{

		try {
			var bodyheight = document.getElementById('chartzoomsliderrotacion').clientHeight;


		}
		catch (err) {
			var bodyheight = 0;
		}

		if (bodyheight > 0) {

			window.scrollTo(0, bodyheight);
		}

	} else {
		window.scrollTo(0, 400);

	}
}
var datarotadiaria;
function createSliderRotaMinDiario(divchartzoomslider, compdiario1, compdiario2, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {

		if (!($("#" + divchartzoomslider).length)) return;
		data  = RestServices.getRotacionDiaria();
		ticks = data["Ticks"]; 

		vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		
		dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		ticks = data["Ticks"];
		from=datestart;
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000
			
			from = dateend.getTime() - (dia * 180);
			from = new Date(from);

			from2 = dateend.getTime() - (dia * 9);
			from2 = new Date(from2);
			
			vStorageFecStart = completarFechaStart(from2,ticks);
			vStorageFecEnd=dateend;
		} 
		from2=vStorageFecStart;
		
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compdiario1, compdiario2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacion(divchartmax.id, data, 1);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right - s_right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmingeneralRotacion(idMini, data, 1);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider({
				range: {
					min: { days: 1, },
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
		
		//trigger first load
		var datesinit = {
			label: dateSliderBounds,
			values: {
				min: from,
				max: vStorageFecEnd,
			},
			first: true
		}
		var datesinit2 = {
			label: dateSliderBounds,
			values: {
				min: from2,
				max: vStorageFecEnd,
			},
			first: true
		}


		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compdiario1, compdiario2, data, 1);
			//if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compdiario1, compdiario2, data, 1);
				savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
			//}
		});


		$("#" + compdiario1 + "_sel_anio").unbind();
		$("#" + compdiario1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_mes").unbind();
		$("#" + compdiario1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_dia").unbind();
		$("#" + compdiario1 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_anio").unbind();
		$("#" + compdiario2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_mes").unbind();
		$("#" + compdiario2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_dia").unbind();
		$("#" + compdiario2 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		$("#" + idDivSlider).trigger("valuesChanged", datesinit2);
		scrollrotacion();
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datarotamensual;
function createSliderRotaMinMensual(divchartzoomslider, compmensual1, compmensual2, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;

		data = RestServices.getRotacionMensual();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compmensual1, compmensual2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacion(divchartmax.id, data, 2);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacion(idMini, data, 2);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);


		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		var ticks = data["Ticks"];
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 
		
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					//min: {days: 30},
					min: { months: 1 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		//trigger second load
		var datesinit2 = {
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			//first: true
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compmensual1, compmensual2, data, 2);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compmensual1, compmensual2, data, 2);
			}
		});

		$("#" + compmensual1 + "_sel_anio").unbind();
		$("#" + compmensual1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual1 + "_sel_mes").unbind();
		$("#" + compmensual1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_anio").unbind();
		$("#" + compmensual2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});
		$("#" + compmensual2 + "_sel_mes").unbind();
		$("#" + compmensual2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		$("#" + idDivSlider).trigger("valuesChanged", datesinit2);

		scrollrotacion();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datarotatrimestral;
function createSliderRotaMinTrimestral(divchartzoomslider, comptrimestral1, comptrimestral2, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getRotacionTrimestral();
		ticks=data["Ticks"];
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, comptrimestral1, comptrimestral2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacion(divchartmax.id, data, 3);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacion(idMini, data, 3);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 
		
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 3 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, comptrimestral1, comptrimestral2, data, 3);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, comptrimestral1, comptrimestral2, data, 3);
				savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
			}
		});


		$("#" + comptrimestral1 + "_sel_anio").unbind();
		$("#" + comptrimestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral1 + "_sel_trimestre").unbind();
		$("#" + comptrimestral1 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_anio").unbind();
		$("#" + comptrimestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_trimestre").unbind();
		$("#" + comptrimestral2 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scrollrotacion();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datarotasemestral;
function createSliderRotaMinSemestral(divchartzoomslider, compsemestral1, compsemestral2, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getRotacionSemestral();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compsemestral1, compsemestral2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacion(divchartmax.id, data, 4);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacion(idMini, data, 4);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 
		
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 6 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compsemestral1, compsemestral2, data, 4);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compsemestral1, compsemestral2, data, 4);
				savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
			}
		});

		$("#" + compsemestral1 + "_sel_anio").unbind();
		$("#" + compsemestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral1 + "_sel_semestre").unbind();
		$("#" + compsemestral1 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_anio").unbind();
		$("#" + compsemestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_semestre").unbind();
		$("#" + compsemestral2 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});
		
		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scrollrotacion();
		
		jQuery(window).resize(function() {
			resizePlot(targetPlot, controllerPlot, idMini, idDivSlider);
		});
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datarotaanual;
function createSliderRotaMinAnual(divchartzoomslider, companual1, companual2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data  = RestServices.getRotacionAnual();
		ticks = data["Ticks"] ;
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, companual1, companual2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacion(divchartmax.id, data, 5);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacion(idMini, data, 5);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { years: 1 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, companual1, companual2, data, 5);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, companual1, companual2, data, 5);
				savedOldDatesStoStorage(almacen, companual1, companual2, "5");
			}
		});

		$("#" + companual1 + "_sel_anio").unbind();
		$("#" + companual1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + companual2 + "_sel_anio").unbind();
		$("#" + companual2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scrollrotacion();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

function parsedata(serieValores) {

	let serieValoresF = [];
	for (var i = 0; i < serieValores.length; i++) {
		var valor2 = parseFloat(serieValores[i][1].toFixed(2));
		var fecha = (serieValores[i][0]);
		serieValoresF.push([fecha, valor2]);
	}
	return serieValoresF;
}

function datos(serieValores) {


	var serieValoresF = [];

	for (var i = 0; i < serieValores.length; i++) {
		var valor2 = serieValores[i].serieValor;
		var fecha = (serieValores[i].valorFecha);
		serieValoresF.push([fecha, valor2]);
	}
	return serieValoresF;
}

function datosValor(serieValores) {

	var serieValorD = [];

	for (var i = 0; i < serieValores.length; i++) {
		var valor2 = serieValores[i].serieValor;
		var fecha = (serieValores[i].valorFecha);
		var hora = (serieValores[i].hora);
		serieValorD.push([fecha, hora, valor2]);
	}
	return serieValorD;
}

function createplotmaxgeneralRotacion(name, data, tipo) {
	var title, serieValores, serieCantidad, minValor, minCantidad;
	title = data["Title"];

	serieValores = datos(data["SerieValores"]);
	//serieValores = parsedata(serieValores);
	minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var ValorMx = parseFloat(maxValor.toFixed(2));
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0], (parseInt(fecha1[1]) - 1), fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0], (parseInt(fecha2[1]) - 1), fecha2[2]);
	var plot2 = $.jqplot(name, [serieValores], {
		title: title,
		textColor: "#003E6C",
		seriesColors: ['#850024'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
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
			showSwatches: true,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 5,
			rowSpacing: "0px",
			rendererOptions: {
				seriesToggle: "true",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},
		series: [
			{
				color: '#850024',
				lineWidth: 0.5,
				shadow: true,
				label: 'Rotación',
				markerOptions: { size: 4, style: 'filledCircle' },
				rendererOptions: {
					smooth: false,
					css: {
						background: '#850024'
					}
				}
			},
		],
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
		},
		cursor: {
			show: true,
			zoom: true,
			showTooltip: false,
			dblClickReset: false,
		},
		highlighter: {
			show: true,
			sizeAdjust: 1,
			tooltipOffset: 9,
			useYTickMarks: true,
			useXTickMarks: true,
			currentNeighbor: null,
			showMarker: true,
			lineWidthAdjust: 1,
			showTooltip: true,
			tooltipLocation: 'e',
			fadeTooltip: false,
			tooltipAxes: '(x , y)',
			tooltipSeparator: ', ',
			useAxesFormatters: true,
		},
		axesDefaults: {
			showTickMarks: true,
			useSeriesColor: true,
			rendererOptions: {
				alignTicks: false,
			}
		},

		axes: {
			xaxis: {
				numberTicks: getTicks(minX, maxX, tipo),
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				tickInterval: getTickInterval(tipo),
				renderer: $.jqplot.DateAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: -45,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: true,
					size: 4,
					markSize: 4,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				autoscale: false,
				min: minValor,
				max: ValorMx,
				label: 'Valor',
				fontSize: '6px',
				showLabel: true,
				textColor: "#850024",
				show: true,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: true,
					pad: 1.2,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#850024",
					formatString: "%'.2f",
				}
			},
		},
	});
	return plot2;
}

function createplotmingeneralRotacion(name, data, tipo) {
	var serieValores, serieCantidad, minValor, minCantidad;
	serieValores = datos(data["SerieValores"]);
	//serieValores = parsedata(serieValores);
	minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0], (parseInt(fecha1[1]) - 1), fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0], (parseInt(fecha2[1]) - 1), fecha2[2]);
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var plot2 = $.jqplot(name, [serieValores], {
		textColor: "#003E6C",
		seriesColors: ['#850024'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
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
			animation: {
				show: false
			},
		},
		legend: {
			show: false,
			showLabels: false,
			showSwatches: false,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 0,
			rowSpacing: "0px",
			rendererOptions: {
				seriesToggle: "false",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},
		series: [
			{
				color: '#850024',
				lineWidth: 0.5,
				shadow: true,
				label: 'Valor',
				showLabel: 'false',
				markerOptions: { size: 4, style: 'filledCircle' },
				rendererOptions: {
					smooth: false,
					css: {
						background: '#850024'
					}
				}
			},
		],
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
			pad: 0,
		},
		cursor: {
			show: false,
			zoom: true,
			showTooltip: true,
			constrainZoomTo: 'x',
			dblClickReset: false,

		},
		highlighter: {
			show: false,
		},
		axesDefaults: {
			showTickMarks: false,
			useSeriesColor: false,
			drawBaseline: false,
			pad: 0,
			rendererOptions: {
				alignTicks: true,
			}
		},
		axes: {
			xaxis: {
				numberTicks: getTicks(minX, maxX, tipo),
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				tickInterval: getTickInterval(tipo),
				renderer: $.jqplot.DateAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: -45,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: false,
					size: 0,
					markSize: 0,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				showLabel: false,
				show: false,
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontSize: "8pt",
					size: 0,
					markSize: 0,
					show: false,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#850024",
					formatString: "%'.2f",
				}
			},
		},
	});
	return plot2;
}


/**
 * ROTACION PIB
 **/
function scrollpib() {

	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	{

		try {
			var bodyheight = document.getElementById('chartzoomsliderrotacionpib').clientHeight;
			var bodyheight2 = document.getElementById('chartzoomsliderrotacion').clientHeight;

		}
		catch (err) {
			var bodyheight2 = 0;
		}
		if (bodyheight2 > 0) {
			var bodyheigh2t = bodyheight2 + 100;
		}

		var bodyheight = document.documentElement.scrollHeight;
		window.scrollTo(0, bodyheight);
	} else {

		var bodyheight = document.documentElement.scrollHeight;
		window.scrollTo(0, bodyheight);
	}

}
var datapibdiario;
function createSliderRotaMinDiarioPib(divchartzoomslider, compdiario1, compdiario2, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getRotacionPIBDiaria();
		ticks= data["Ticks"];

		$("#" + divchartzoomslider).empty();
		vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		from = datestart;
		var ticks = data["Ticks"];
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			
			from = dateend.getTime() - (dia * 180);
			from = new Date(from);
			
			from2 = dateend.getTime() - (dia * 9);
			from2 = new Date(from2);
			vStorageFecStart 	= 	completarFechaStart(from2,ticks);
			vStorageFecEnd		=	dateend;
		} 		
		from2 = vStorageFecStart;
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compdiario1, compdiario2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacionPib(divchartmax.id, data, 1);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmingeneralRotacionPib(idMini, data, 1);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider({
				range: {
					min: { days: 1 },
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
		//trigger first load
		var datesinit = {
			label: dateSliderBounds,
			values: {
				min: from,
				max: vStorageFecEnd,
			},
			updateperiodo: true
		}

		var datesinit2 = {
			label: dateSliderBounds,
			values: {
				min: from2,
				max: vStorageFecEnd,
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compdiario1, compdiario2, data, 1);
			//if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compdiario1, compdiario2, data, 1);
				savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
			//}
		});

		$("#" + compdiario1 + "_sel_anio").unbind();
		$("#" + compdiario1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_mes").unbind();
		$("#" + compdiario1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_dia").unbind();
		$("#" + compdiario1 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
		});

		$("#" + compdiario2 + "_sel_anio").unbind();
		$("#" + compdiario2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_mes").unbind();
		$("#" + compdiario2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
		});

		$("#" + compdiario2 + "_sel_dia").unbind();
		$("#" + compdiario2 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedatapflottimesranges(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		$("#" + idDivSlider).trigger("valuesChanged", datesinit2);
		scrollpib();
				
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datapibmensual;
function createSliderRotaMinMensualPib(divchartzoomslider, compmensual1, compmensual2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getRoracionPIBMensual();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		$("#" + divchartzoomslider).empty();
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compmensual1, compmensual2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacionPib(divchartmax.id, data, 2);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacionPib(idMini, data, 2);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		var ticks = data["Ticks"];
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 

		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 1 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		//trigger second load
		var datesinit2 = {
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compmensual1, compmensual2, data, 2);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compmensual1, compmensual2, data, 2);
				savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
			}
		});

		$("#" + compmensual1 + "_sel_anio").unbind();
		$("#" + compmensual1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual1 + "_sel_mes").unbind();
		$("#" + compmensual1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_anio").unbind();
		$("#" + compmensual2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_mes").unbind();
		$("#" + compmensual2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});
		
		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		$("#" + idDivSlider).trigger("valuesChanged", datesinit2);

		scrollpib();
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datapibtrimestral;
function createSliderRotaMinTrimestralPib(divchartzoomslider, comptrimestral1, comptrimestral2, errormessage,almacen,onetime) {
	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getRotacionPIBTrimestral();
		ticks = data["Ticks"];
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		$("#" + divchartzoomslider).empty();
		
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, comptrimestral1, comptrimestral2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacionPib(divchartmax.id, data, 3);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacionPib(idMini, data, 3);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 3 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider,controllerPlot,targetPlot,comptrimestral1,comptrimestral2,data,3);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, comptrimestral1, comptrimestral2, data, 3);
				savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
			}
		});

		$("#" + comptrimestral1 + "_sel_anio").unbind();
		$("#" + comptrimestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral1 + "_sel_trimestre").unbind();
		$("#" + comptrimestral1 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_anio").unbind();
		$("#" + comptrimestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_trimestre").unbind();
		$("#" + comptrimestral2 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});
		
		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scrollpib();
		
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datapibsemestral;
function createSliderRotaMinSemestralPib(divchartzoomslider, compsemestral1, compsemestral2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getRotacionPIBSemestral();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		$("#" + divchartzoomslider).empty();
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);

		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compsemestral1, compsemestral2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacionPib(divchartmax.id, data, 4);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacionPib(idMini, data, 4);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 

		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { months: 6 },
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
			label: dateSliderBounds,
			values: {
				min: vStorageFecStart,
				max: vStorageFecEnd,
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, compsemestral1, compsemestral2, data, 4);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, compsemestral1, compsemestral2, data, 4);
				savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
			}
		});

		$("#" + compsemestral1 + "_sel_anio").unbind();
		$("#" + compsemestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral1 + "_sel_semestre").unbind();
		$("#" + compsemestral1 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_anio").unbind();
		$("#" + compsemestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_semestre").unbind();
		$("#" + compsemestral2 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});
		
		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scrollpib();
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

var datapibanual;
function createSliderRotaMinAnualPib(divchartzoomslider, companual1, companual2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data 	= RestServices.getRotacionPibAnual();
		ticks 	= data["Ticks"];
	
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, companual1, companual2, data);
		/*load*/
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot = createplotmaxgeneralRotacionPib(divchartmax.id, data, 5);
		/*get positions super plot*/
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		/*create mini port*/
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;")
			;
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		/*associate*/
		controllerPlot = createplotmingeneralRotacionPib(idMini, data, 5);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		//		/*create slider*/
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		var datestart = data["MinX"].split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		var dateend = data["MaxX"].split("-");;
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		var from = data["startX"].split("-");
		from = new Date(from[0], parseInt(from[1]) - 1, from[2]);
		from2=vStorageFecStart;
		if (onetime!=null && onetime=="1") {
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecEnd=dateend;
		} 
		var dateSliderBounds = $("#" + idDivSlider).dateRangeSlider(
			{
				range: {
					min: { years: 1 },
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
			label: dateSliderBounds,
			values: {
					min: from2,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dateSlider) {
			valuesPlotChangedTimes(dateSlider, controllerPlot, targetPlot, companual1, companual2, data, 5);
			if (dateSlider.first == null || dateSlider.first == false) {
				updatevaluescomponentstimesfromslider(dateSlider, companual1, companual2, data, 5);
				savedOldDatesStoStorage(almacen, companual1, companual2, "5");
			}
		});

		$("#" + companual1 + "_sel_anio").unbind();
		$("#" + companual1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});
		$("#" + companual2 + "_sel_anio").unbind();
		$("#" + companual2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimesranges(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + idDivSlider).trigger("valuesChanged", datesinit);
		scrollpib();
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
};

function createplotmaxgeneralRotacionPib(name, data, tipo) {

	var title, serieValores, serieCantidad, minValor, minCantidad;
	title = data["Title"];

	serieValores = datos(data["SerieValores"]);
	//serieValores = parsedata(serieValores);
	minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0], (parseInt(fecha1[1]) - 1), fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0], (parseInt(fecha2[1]) - 1), fecha2[2]);
	var plot2 = $.jqplot(name, [serieValores], {
		title: title,
		textColor: "#003E6C",
		seriesColors: ['#006fb9'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
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
			showSwatches: true,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 5,
			rowSpacing: "0px",
			rendererOptions: {
				seriesToggle: "true",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},
		series: [
			{
				color: '#006fb9',
				lineWidth: 0.5,
				shadow: true,
				label: 'PIB',
				markerOptions: { size: 4, style: 'filledCircle' },
				rendererOptions: {
					smooth: false,
					css: {
						background: '#006fb9'
					}
				}
			},
		],
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
		},
		cursor: {
			show: true,
			zoom: true,
			showTooltip: false,
			dblClickReset: false,
		},
		highlighter: {
			show: true,
			sizeAdjust: 1,
			tooltipOffset: 9,
			useYTickMarks: true,
			useXTickMarks: true,
			currentNeighbor: null,
			showMarker: true,
			lineWidthAdjust: 1,
			showTooltip: true,
			tooltipLocation: 'e',
			fadeTooltip: false,
			tooltipAxes: '(x , y)',
			tooltipSeparator: ', ',
			useAxesFormatters: true,
		},
		axesDefaults: {
			showTickMarks: true,
			useSeriesColor: true,
			rendererOptions: {
				alignTicks: false,
			}
		},
		axes: {
			xaxis: {
				numberTicks: getTicks(minX, maxX, tipo),
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				tickInterval: getTickInterval(tipo),
				renderer: $.jqplot.DateAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: -45,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: true,
					size: 4,
					markSize: 4,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				autoscale: false,
				min: minValor,
				max: maxValor,
				label: 'Valor',
				fontSize: '6px',
				showLabel: true,
				textColor: "#006fb9",
				show: true,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: true,
					pad: 1.2,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#006fb9",
					formatString: "%'.2f",
				}
			},
		},
	});
	return plot2;
}

function createplotmingeneralRotacionPib(name, data, tipo) {
	var serieValores, serieCantidad, minValor, minCantidad;
	serieValores = datos(data["SerieValores"]);
	//serieValores = parsedata(serieValores);
	minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0], (parseInt(fecha1[1]) - 1), fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0], (parseInt(fecha2[1]) - 1), fecha2[2]);
	var plot2 = $.jqplot(name, [serieValores], {
		textColor: "#003E6C",
		seriesColors: ['#006fb9'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
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
			animation: {
				show: false
			},
		},
		legend: {
			show: false,
			showLabels: false,
			showSwatches: false,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 0,
			rowSpacing: "0px",
			rendererOptions: {
				seriesToggle: "false",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},
		series: [
			{
				color: '#006fb9',
				lineWidth: 0.5,
				shadow: true,
				label: 'Valor',
				showLabel: 'false',
				markerOptions: { size: 4, style: 'filledCircle' },
				rendererOptions: {
					smooth: false,
					css: {
						background: '#006fb9'
					}
				}
			},
		],
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
			pad: 0,
		},
		cursor: {
			show: false,
			zoom: true,
			showTooltip: true,
			constrainZoomTo: 'x',
			dblClickReset: false,
		},
		highlighter: {
			show: false,
		},
		axesDefaults: {
			showTickMarks: false,
			useSeriesColor: false,
			drawBaseline: false,
			pad: 0,
			rendererOptions: {
				alignTicks: true,
			}
		},
		axes: {
			xaxis: {
				numberTicks: getTicks(minX, maxX, tipo),
				min: minX,
				max: maxX,
				label: 'Fecha',
				showLabel: false,
				tickInterval: getTickInterval(tipo),
				renderer: $.jqplot.DateAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: -45,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: false,
					size: 0,
					markSize: 0,
					formatString: formatter(tipo),
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				showLabel: false,
				show: false,
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontSize: "8pt",
					size: 0,
					markSize: 0,
					show: false,
					fontFamily: '"Roboto", sans-serif',
					textColor: "#006fb9",
					formatString: "%'.2f",
				}
			},
		},
	});
	return plot2;
}

/**
 * DISTRIBUCION VALOR  
 **/

/** DISTRIBUCION VALOR */

function scrollvalor() {

	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	{

		try {
			var bodyheight = document.getElementById('chartzoomsliderdistribucion').clientHeight;


		}
		catch (err) {
			var bodyheight = 0;
		}

		if (bodyheight > 0) {

			window.scrollTo(0, bodyheight);
		}

	} else {
		window.scrollTo(0, 400);

	}
}


function createSliderDistriValorMinDiario(divchartzoomslider, compdiario1, compdiario2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;

		data = RestDistribucionValor.getDistribucionValorDiario();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		
		vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		from=datestart;
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			from = dateend.getTime() - (dia * 180);
			from = new Date(from);

			from2 = dateend.getTime() - (dia * 1);
			from2 = new Date(from2);
			from2 = completarFechaStart(from2,ticks);
			
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
		} 
		from2 = vStorageFecStart;

		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compdiario1, compdiario2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucionvalor(divchartmax.id, data, 1, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucionvalor(idMini, data, 1, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		//trigger first load
		var datesinit = {
			values: {
					min: from,
					max: vStorageFecEnd
			},
			updateperiodo: true
		}

		//trigger second load
		var datesinit2 = {
			values: {
				min: from2,
				max: vStorageFecEnd,
			},
			updateperiodo: true
		}

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, compdiario1, compdiario2, data, 1)
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_anio").unbind();
		$("#" + compdiario1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_mes").unbind();
		$("#" + compdiario1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_dia").unbind();
		$("#" + compdiario1 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_anio").unbind();
		$("#" + compdiario2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_mes").unbind();
		$("#" + compdiario2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_dia").unbind();
		$("#" + compdiario2 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		
		scrollvalor();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriValorMinMensual(divchartzoomslider, compmensual1, compmensual2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestDistribucionValor.getDistribucionValorMensual();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();

		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);

		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
		} 	
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compmensual1, compmensual2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucionvalor(divchartmax.id, data, 2, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucionvalor(idMini, data, 2, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;");
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, compmensual1, compmensual2, data, 2)
		});

		$("#" + compmensual1 + "_sel_anio").unbind();
		$("#" + compmensual1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual1 + "_sel_mes").unbind();
		$("#" + compmensual1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_anio").unbind();
		$("#" + compmensual2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_mes").unbind();
		$("#" + compmensual2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		scrollvalor();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriValorMinTrimestral(divchartzoomslider, comptrimestral1, comptrimestral2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestDistribucionValor.getDistribucionValorTrimestral();
		ticks = data["Ticks"];
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
		} 				
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, comptrimestral1, comptrimestral2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucionvalor(divchartmax.id, data, 3, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucionvalor(idMini, data, 3, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		scrollvalor();

		$("#" + idDivSlider).unbind("valuesChanged");
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, comptrimestral1, comptrimestral2, data, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral1 + "_sel_anio").unbind("change");
		$("#" + comptrimestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral1 + "_sel_trimestre").unbind("change");
		$("#" + comptrimestral1 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_anio").unbind("change");
		$("#" + comptrimestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_trimestre").unbind("change");
		$("#" + comptrimestral2 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriValorMinSemestral(divchartzoomslider, compsemestral1, compsemestral2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestDistribucionValor.getDistribucionValorSemestral();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);

		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
		} 				
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compsemestral1, compsemestral2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucionvalor(divchartmax.id, data, 4, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucionvalor(idMini, data, 4, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		scrollvalor();
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, compsemestral1, compsemestral2, data, 4)
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral1 + "_sel_anio").unbind();
		$("#" + compsemestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral1 + "_sel_semestre").unbind();
		$("#" + compsemestral1 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_anio").unbind();
		$("#" + compsemestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_semestre").unbind();
		$("#" + compsemestral2 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriValorMinAnual(divchartzoomslider, companual1, companual2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestDistribucionValor.getDistribucionValorAnual();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);

		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);

		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
		} 				
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, companual1, companual2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucionvalor(divchartmax.id, data, 5, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucionvalor(idMini, data, 5, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		scrollvalor();
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, companual1, companual2, data, 5)
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + companual1 + "_sel_anio").unbind();
		$("#" + companual1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + companual2 + "_sel_anio").unbind();
		$("#" + companual2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createplotmaxdistribucionvalor(name, data, tipo, periodo1, periodo2) {

	var title, serieValores, serieCantidad, minValor, minCantidad;
	title = data["Title"];
	//serieValores 	= data["SerieValores"];
	//serieValores = parsedata(serieValores);
	serieValores = datosValor(data["SerieValores"]);
	minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var serieP1 = [];
	var serieP2 = [];
	var counta = datosValor(data["SerieValores"].length);
	var countb = 0;
	var fec_p1 = "" + periodo1.getFullYear() + "-" + ("0".concat(periodo1.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo1.getDate())).slice(-2);
	var fec_p2 = "" + periodo2.getFullYear() + "-" + ("0".concat(periodo2.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo2.getDate())).slice(-2);

	var horas = hours();//[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];

	for (var i = 0; i < serieValores.length; i++) {
		var fec001 = serieValores[i][0];
		var hora = parseInt(serieValores[i][1]);
		var valor = serieValores[i][2];
		if (fec001 == fec_p1) {
			for (var h = 0; h < horas.length; h++) {
				if (hora == horas[h]) {
					serieP1[h] = [hora, valor];
				}
			}
		}
		if (fec001 == fec_p2) {
			for (var h = 0; h < horas.length; h++) {
				if (hora == horas[h]) {
					serieP2[h] = [hora, valor];
				}
			}
		}
	}//for


	var coud1 = 0;
	var coud2 = 0;
	for (var s = 0; s < serieP1.length; s++) {
		try {
			var datos = serieP1[s].length;
			if (datos > 0) {
				coud1++;
			}
		} catch (e) {

		}
	}


	for (var s = 0; s < serieP2.length; s++) {
		try {
			var datos = serieP2[s].length;
			if (datos > 0) {
				coud2++;
			}
		} catch (e) {

		}
	}


	if (coud1 < 24) {
		for (var h = 0; h < horas.length; h++) {
			var flag = true;

			for (var s = 0; s < serieP1.length; s++) {
				try {
					var hs = serieP1[s][0];
					if (horas[h] == hs) {
						flag = false;
						break;
					}
				} catch (e) {
					var flag = true;
				}
			}

			if (flag == true) {
				serieP1[h] = [horas[h], 0];
			}
		}

	}



	if (coud2 < 24) {
		for (var h = 0; h < horas.length; h++) {
			flag = true;

			for (var s = 0; s < serieP2.length; s++) {
				try {
					var hs = serieP2[s][0];
					if (horas[h] == hs) {
						flag = false;
						break;
					}
				} catch (e) {
					var flag = true;
					//console.log(e);
				}
			}

			if (flag == true) {
				serieP2[h] = [horas[h], 0];
			}
		}

	}



	var label1 = formatterLabel(tipo, fec_p1);
	var label2 = formatterLabel(tipo, fec_p2);

	var plot2 = $.jqplot(name, [serieP1, serieP2],

		{
			//use_excanvas : false,		
			title: title,
			// Provide a custom seriesColors array to override the default colors.
			textColor: "#003E6C",
			seriesColors: ['#850024', '#006fb9'],
			pointLabels: {
				show: true,
				showLabel: true,
			},
			seriesDefaults: {
				renderer: $.jqplot.BlockRenderer,
				showMarker: true,
				markerOptions: { size: 4, style: 'filledCircle' },
				lineWidth: 1,
				shadow: true,
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
				showSwatches: true,
				placement: "insideGrid",
				location: "ne",
				fontSize: '11px',
				pad: 5,
				rowSpacing: "0px",
				labels: [label1, label2],
				rendererOptions: {
					seriesToggle: "true",
					seriesToggleReplot: {
						resetAxes: false
					}
				}
			},
			grid: {
				background: '#ffffff',
				borderColor: '#eeeeee',
				gridLineColor: '#F5F5F5',
				shadow: false,
				drawBorder: true,
				gridLineWidth: 0.8,
				borderWidth: 0.8,
			},

			cursor: {
				show: true,
				zoom: true,
				showTooltip: false,
				dblClickReset: false,
			},

			highlighter: {
				show: true,
				sizeAdjust: 1,
				tooltipOffset: 9,
				useYTickMarks: true,
				useXTickMarks: true,
				currentNeighbor: null,
				showMarker: true,
				lineWidthAdjust: 1,
				showTooltip: true,
				tooltipLocation: 'e',
				fadeTooltip: false,
				tooltipAxes: '(x , y)',
				tooltipSeparator: '  ,  ',
				useAxesFormatters: true,
			},
			axesDefaults: {
				showTickMarks: true,
				useSeriesColor: false,
				rendererOptions: {
					alignTicks: false,
				}
			},
			axes: {
				xaxis: {
					min: 0,
					max: 24,
					label: 'Hora',
					showLabel: true,
					pad: 1,
					renderer: $.jqplot.LinearAxisRenderer,
					rendererOptions: {
						tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					},
					tickOptions: {
						showTicks: true,
						angle: 0,
						showLabel: true,
						showMark: true,
						showGridline: true,
						fontFamily: '"Roboto", sans-serif',
						fontSize: '9pt',
						show: true,
						size: 4,
						markSize: 4,
						formatString: "%d",
					}
				},
				yaxis: {
					min: minValor,
					max: maxValor,
					label: 'Valor',
					fontSize: '6px',
					showLabel: true,
					show: true,
					rendererOptions: {
						tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					},
					tickOptions: {
						showTicks: true,
						angle: 0,
						showLabel: true,
						showMark: true,
						showGridline: false,
						fontSize: "8pt",
						size: 4,
						markSize: 6,
						show: true,
						pad: 1,
						fontFamily: '"Roboto", sans-serif',
						labelPosition: 'top',
						formatString: "%'.2f %",
					}
				},
			},
		});

	return plot2;
}

function createplotmindistribucionvalor(name, data, tipo, periodo1, periodo2) {

	var title, serieValores, serieCantidad, minValor, minCantidad;
	title = data["Title"];
	//
	serieValores = datosValor(data["SerieValores"]);
	var minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var serieP1 = [[]];
	var serieP2 = [[]];
	var counta = 0;
	var countb = 0;
	var fec_p1 = "" + periodo1.getFullYear() + "-" + ("0".concat(periodo1.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo1.getDate())).slice(-2);
	var fec_p2 = "" + periodo2.getFullYear() + "-" + ("0".concat(periodo2.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo2.getDate())).slice(-2);

	for (var i = 0; i < serieValores.length; i++) {
		var fec001 = serieValores[i][0];
		var hora = parseInt(serieValores[i][1]);
		var valor = serieValores[i][2];
		if (fec001 == fec_p1) {
			serieP1[counta] = [hora, valor];
			counta++;
		}
		if (fec001 == fec_p2) {
			serieP2[countb] = [hora, valor];
			countb++;
		}
	}//for 

	var plot2 = $.jqplot(name, [serieP1, serieP2], {
		textColor: "#003E6C",
		seriesColors: ['#850024', '#006fb9'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
			showMarker: false,
			fill: false,
			fillAndStroke: true,
			lineWidth: 0.5,
			shadow: false,
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: false,
				barMargin: 1,
				shadowDepth: 5,
			},
			animation: {
				show: false
			},
		},

		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
			pad: 0,
		},

		cursor: {
			show: false,
			zoom: true,
			showTooltip: false,
			constrainZoomTo: 'x',
			dblClickReset: false,
		},

		axesDefaults: {
			showTickMarks: false,
			useSeriesColor: false,
			rendererOptions: {
				alignTicks: false,
			}
		},
		axes: {
			xaxis: {
				min: 0,
				max: 23,
				label: 'Hora',
				showLabel: false,
				pad: 1,
				renderer: $.jqplot.LinearAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '9pt',
					show: false,
					size: 4,
					markSize: 4,
					formatString: "%d",
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				label: 'Valor',
				fontSize: '6px',
				showLabel: false,
				show: true,
				pad: 1,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: false,
					pad: 1,
					fontFamily: '"Roboto", sans-serif',
					labelPosition: 'top',
					formatString: "%'.2f",
				}
			},
		},
	});

	return plot2;
}

/**
 * DISTRIBUCION CANTIDAD  
 **/


/** DISTRIBUCION CANTIDAD */
function scrollcantidad() {

	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	{

		try {
			var bodyheight = document.getElementById('chartzoomslidercantidad').clientHeight;
			var bodyheight2 = document.getElementById('chartzoomsliderdistribucion').clientHeight;

		}
		catch (err) {
			var bodyheight2 = 0;
		}
		if (bodyheight2 > 0) {
			var bodyheight2 = bodyheight2 + 200;
		}

		var bodyheight2 = bodyheight2 + 50;
		var final = bodyheight + bodyheight2;
		window.scrollTo(0, final);

	}
}

function createSliderDistriCantMinDiario(divchartzoomslider, compdiario1, compdiario2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getDistribucionCantidadDiario();
		ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);

		datestart = ticks[0];
		datestart = datestart.split("-");
		datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
		//
		dateend = ticks[ticks.length - 1].split("-");
		dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
		
		from=datestart;
		if (onetime!=null && onetime=="1") {
			dia = 24*60*60*1000;
			from = dateend.getTime() - (dia * 180);
			from = new Date(from);

			from2 = dateend.getTime() - (dia * 1);
			from2 = new Date(from2);
			from2 = completarFechaStart(from2,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
		}
		from2 = vStorageFecStart;
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compdiario1, compdiario2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);

		targetPlot = createplotmaxdistribucioncantidad(divchartmax.id, data, 1, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucioncantidad(idMini, data, 1, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		scrollcantidad();
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, compdiario1, compdiario2, data, 1)
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_anio").unbind();
		$("#" + compdiario1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_mes").unbind();
		$("#" + compdiario1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario1 + "_sel_dia").unbind();
		$("#" + compdiario1 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_anio").unbind();
		$("#" + compdiario2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_mes").unbind();
		$("#" + compdiario2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

		$("#" + compdiario2 + "_sel_dia").unbind();
		$("#" + compdiario2 + "_sel_dia").change(function(evt) {
			changedatesselectedstimes(compdiario1, compdiario2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compdiario1, compdiario2, data, idDivSlider, 1);
			savedOldDatesStoStorage(almacen, compdiario1, compdiario2, "1");
		});

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriCantMinMensual(divchartzoomslider, compmensual1, compmensual2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;

		data = RestServices.getDistribucionCantidadMensual();
		let ticks = data["Ticks"];
		
		$("#" + divchartzoomslider).empty();
		
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		if (onetime!=null && onetime=="1") {
			
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
			
		} 				
		
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compmensual1, compmensual2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucioncantidad(divchartmax.id, data, 2, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucioncantidad(idMini, data, 2, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
		;
		
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		scrollcantidad();
		
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, compmensual1, compmensual2, data, 2)
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});


		$("#" + compmensual1 + "_sel_anio").unbind();
		$("#" + compmensual1 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual1 + "_sel_mes").unbind();
		$("#" + compmensual1 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_anio").unbind();
		$("#" + compmensual2 + "_sel_anio").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

		$("#" + compmensual2 + "_sel_mes").unbind();
		$("#" + compmensual2 + "_sel_mes").change(function(evt) {
			changedatesselectedstimes(compmensual1, compmensual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compmensual1, compmensual2, data, idDivSlider, 2);
			savedOldDatesStoStorage(almacen, compmensual1, compmensual2, "2");
		});

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriCantMinTrimestral(divchartzoomslider, comptrimestral1, comptrimestral2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getDistribucionCantidadTrimestal();
		let ticks = data["Ticks"];
		
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		if (onetime!=null && onetime=="1") {
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
			
		} 				
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, comptrimestral1, comptrimestral2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucioncantidad(divchartmax.id, data, 3, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucioncantidad(idMini, data, 3, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, comptrimestral1, comptrimestral2, data, 3)
		});

		$("#" + comptrimestral1 + "_sel_anio").unbind();
		$("#" + comptrimestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral1 + "_sel_trimestre").unbind();
		$("#" + comptrimestral1 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_anio").unbind();
		$("#" + comptrimestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		$("#" + comptrimestral2 + "_sel_trimestre").unbind();
		$("#" + comptrimestral2 + "_sel_trimestre").change(function(evt) {
			changedatesselectedstrimestralestimes(comptrimestral1, comptrimestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, comptrimestral1, comptrimestral2, data, idDivSlider, 3);
			savedOldDatesStoStorage(almacen, comptrimestral1, comptrimestral2, "3");
		});

		scrollcantidad();

	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriCantMinSemestral(divchartzoomslider, compsemestral1, compsemestral2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getDistribucionCantidadSemestral();
		let ticks = data["Ticks"];
		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		
		if (onetime!=null && onetime=="1") {
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
			
		} 				
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, compsemestral1, compsemestral2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucioncantidad(divchartmax.id, data, 4, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucioncantidad(idMini, data, 4, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, compsemestral1, compsemestral2, data, 4)
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral1 + "_sel_anio").unbind();
		$("#" + compsemestral1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral1 + "_sel_semestre").unbind();
		$("#" + compsemestral1 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_anio").unbind();
		$("#" + compsemestral2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});

		$("#" + compsemestral2 + "_sel_semestre").unbind();
		$("#" + compsemestral2 + "_sel_semestre").change(function(evt) {
			changedatesselectedssemestralestimes(compsemestral1, compsemestral2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, compsemestral1, compsemestral2, data, idDivSlider, 4);
			savedOldDatesStoStorage(almacen, compsemestral1, compsemestral2, "4");
		});
		
		scrollcantidad();
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createSliderDistriCantMinAnual(divchartzoomslider, companual1, companual2, errormessage,almacen,onetime) {

	var targetPlot, controllerPlot, idMini, idDivSlider, data;
	try {
		if (!($("#" + divchartzoomslider).length)) return;
		data = RestServices.getDistribucionCantidadAnual();
		let ticks = data["Ticks"];

		$("#" + divchartzoomslider).empty();
		/* start selects components */
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");
		
		vStorageFecEnd 		= completarFechaEnd(vStorageFecEnd,ticks);
		vStorageFecStart 	= completarFechaStart(vStorageFecStart,ticks,vStorageFecEnd);
		
		if (onetime!=null && onetime=="1") {
			let datestart = ticks[0];
			datestart = datestart.split("-");
			datestart = new Date(datestart[0], (parseInt(datestart[1]) - 1), datestart[2]);
			//
			
			let dateend = ticks[ticks.length - 1].split("-");
			dateend = new Date(dateend[0], (parseInt(dateend[1]) - 1), dateend[2]);
			
			let dia = 24*60*60*1000;
			let fromtmp = dateend.getTime() - (dia * 2);
			fromtmp = new Date(fromtmp);
			from2 = completarFechaStart(fromtmp,ticks);
			vStorageFecStart = from2;
			vStorageFecEnd = dateend;
			
		} 				
		startvaluescomponentstimes(vStorageFecStart, vStorageFecEnd, companual1, companual2, data);
		var divchartmax = document.createElement('div');
		divchartmax.id = divchartzoomslider + "_chartMax";
		divchartmax.style = "chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		var fechaA = data["startX"].split("-");
		var periodo1 = new Date(fechaA[0], parseInt(fechaA[1]) - 1, fechaA[2]);
		var fechaB = data["endX"].split("-");
		var periodo2 = new Date(fechaB[0], parseInt(fechaB[1]) - 1, fechaB[2]);
		targetPlot = createplotmaxdistribucioncantidad(divchartmax.id, data, 5, vStorageFecStart, vStorageFecEnd);
		var s_left = targetPlot._defaultGridPadding.left;
		var s_right = targetPlot._defaultGridPadding.right;
		var left = targetPlot._gridPadding.left;
		var right = targetPlot._gridPadding.right;
		var width = targetPlot._plotDimensions.width;
		idMini = divchartzoomslider + "_divmini";
		var innerDivMini = document.createElement('div');
		innerDivMini.id = idMini;
		var style = "width:".concat((width)).concat("px !important; ")
			.concat("left:").concat(0).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("height:").concat(55).concat("px !important;").concat(" display: none;");
		innerDivMini.setAttribute("style", style);
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		controllerPlot = createplotmindistribucioncantidad(idMini, data, 5, vStorageFecStart, vStorageFecEnd);
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider + "_slider";
		var innerDivSlider = document.createElement('div');
		innerDivSlider.id = idDivSlider;
		var styleSlider = "width:".concat((width - left - right + s_left + s_right + 24)).concat("px !important; ")
			.concat("margin-top:").concat(-8).concat("px !important;")
			.concat("left:").concat(left - s_left - 12).concat("px !important;")
			.concat("right:").concat(right).concat("px !important;")
			.concat("position: relative;")
			;
		innerDivSlider.setAttribute("style", styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		
		$("#" + idDivSlider).unbind();
		$("#" + idDivSlider).bind("valuesChanged", function(evt, dataSlider) {
			valuesPlotChangedTimesHour(dataSlider, controllerPlot, targetPlot, companual1, companual2, data, 5)
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + companual1 + "_sel_anio").unbind();
		$("#" + companual1 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});

		$("#" + companual2 + "_sel_anio").unbind();
		$("#" + companual2 + "_sel_anio").change(function(evt) {
			changedatesselectedssemestralestimes(companual1, companual2, data, idDivSlider);
			updatedataplottimes(targetPlot, controllerPlot, companual1, companual2, data, idDivSlider, 5);
			savedOldDatesStoStorage(almacen, companual1, companual2, "5");
		});
		
		scrollcantidad();
		
	} catch (err) {
		if (showError) { console.log(err); }
		$("[id*='" + errormessage + "'").empty();
		$("[id*='" + errormessage + "'").append(brmensaje(mensageError, "error"));
	}
}

function createplotmaxdistribucioncantidad(name, data, tipo, periodo1, periodo2) {

	var title, serieValores, serieCantidad, minValor, minCantidad;
	title = data["Title"];
	//serieCantidad 	= data["SerieCantidad"];
	serieCantidad = datosValor(data["SerieCantidad"]);
	minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var serieP1 = [];
	var serieP2 = [];
	var counta = 0;
	var countb = 0;
	var fec_p1 = "" + periodo1.getFullYear() + "-" + ("0".concat(periodo1.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo1.getDate())).slice(-2);
	var fec_p2 = "" + periodo2.getFullYear() + "-" + ("0".concat(periodo2.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo2.getDate())).slice(-2);

	var horas = hours();//[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];

	for (var i = 0; i < serieCantidad.length; i++) {
		var fec001 = serieCantidad[i][0];
		var hora = parseInt(serieCantidad[i][1]);
		var valor = serieCantidad[i][2];
		if (fec001 == fec_p1) {
			for (var h = 0; h < horas.length; h++) {
				if (hora == horas[h]) {
					serieP1[h] = [hora, valor];
				}
			}
		}
		if (fec001 == fec_p2) {
			for (var h = 0; h < horas.length; h++) {
				if (hora == horas[h]) {
					serieP2[h] = [hora, valor];
				}
			}
		}
	}//for


	var coud1 = 0;
	var coud2 = 0;
	for (var s = 0; s < serieP1.length; s++) {
		try {
			var datos = serieP1[s].length;
			if (datos > 0) {
				coud1++;
			}
		} catch (e) {

		}
	}

	for (var s = 0; s < serieP2.length; s++) {
		try {
			var datos = serieP2[s].length;
			if (datos > 0) {
				coud2++;
			}
		} catch (e) {

		}
	}

	if (coud1 < 24) {
		for (var h = 0; h < horas.length; h++) {
			var flag = true;

			for (var s = 0; s < serieP1.length; s++) {
				try {
					var hs = serieP1[s][0];
					if (horas[h] == hs) {
						flag = false;
						break;
					}
				} catch (e) {
					var flag = true;
				}
			}

			if (flag == true) {
				serieP1[h] = [horas[h], 0];
			}
		}
	}



	if (coud2 < 24) {
		for (var h = 0; h < horas.length; h++) {
			flag = true;

			for (var s = 0; s < serieP2.length; s++) {
				try {
					var hs = serieP2[s][0];
					if (horas[h] == hs) {
						flag = false;
						break;
					}
				} catch (e) {
					var flag = true;
					//console.log(e);
				}
			}

			if (flag == true) {
				serieP2[h] = [horas[h], 0];
			}
		}

	}



	//labels
	var label1 = formatterLabel(tipo, fec_p1);
	var label2 = formatterLabel(tipo, fec_p2);
	var plot2 = $.jqplot(name, [serieP1, serieP2], {
		//use_excanvas : false,		
		title: title,
		// Provide a custom seriesColors array to override the default colors.
		textColor: "#003E6C",
		seriesColors: ['#850024', '#006fb9'],
		pointLabels: {
			show: true,
			showLabel: true,
		},
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
			showMarker: true,
			markerOptions: { size: 4, style: 'filledCircle' },
			lineWidth: 1,
			shadow: true,
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
			showSwatches: true,
			//xoffset: 90,
			//yoffset: 45,
			placement: "insideGrid",
			location: "ne",
			fontSize: '11px',
			pad: 5,
			rowSpacing: "0px",
			labels: [label1, label2],
			rendererOptions: {
				seriesToggle: "true",
				seriesToggleReplot: {
					resetAxes: false
				}
			}
		},
		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
		},

		cursor: {
			show: true,
			zoom: true,
			showTooltip: false,
			dblClickReset: false,
		},

		highlighter: {
			show: true,
			sizeAdjust: 1,
			tooltipOffset: 9,
			useYTickMarks: true,
			useXTickMarks: true,
			currentNeighbor: null,
			showMarker: true,
			lineWidthAdjust: 1,
			showTooltip: true,
			tooltipLocation: 'e',
			fadeTooltip: false,
			tooltipAxes: '(x , y)',
			tooltipSeparator: '  ,  ',
			useAxesFormatters: true,
		},

		axesDefaults: {
			showTickMarks: true,
			useSeriesColor: false,
			rendererOptions: {
				alignTicks: false,
			}
		},

		axes: {
			xaxis: {
				//numberTicks: 10,
				//ticks: ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23'],
				//type: 'integer',
				min: 0,
				max: 24,
				label: 'Hora',
				showLabel: true,
				pad: 1,
				//tickInterval: 1,
				renderer: $.jqplot.LinearAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: true,
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: true,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '10pt',
					show: true,
					size: 4,
					markSize: 4,
					formatString: "%d",
				}
			},
			yaxis: {
				//autoscale:false,	
				min: minValor,
				max: maxValor,
				label: 'Valor',
				fontSize: '6px',
				showLabel: true,
				//textColor: "#006fb9",
				show: true,
				//renderer: $.jqplot.LinearAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				//gridLineColor : '#F5F5F5',
				//lineColor : '#F5F5F5',
				tickOptions: {
					showTicks: true,
					angle: 0,
					showLabel: true,
					showMark: true,
					showGridline: false,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: true,
					pad: 1,
					fontFamily: '"Roboto", sans-serif',
					labelPosition: 'top',
					formatString: "%'.2f %",
				}
			},
		},
	});

	return plot2;
}

function createplotmindistribucioncantidad(name, data, tipo, periodo1, periodo2) {

	var serieValores, serieCantidad, minValor, minCantidad;
	//serieCantidad 	= data["SerieCantidad"];
	serieCantidad = datosValor(data["SerieCantidad"]);
	minValor = data["MinValor"];
	var maxValor = data["MaxValor"];
	var minX = data["MinX"];
	var maxX = data["MaxX"];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var serieP1 = [[]];
	var serieP2 = [[]];
	var counta = 0;
	var countb = 0;
	var fec_p1 = "" + periodo1.getFullYear() + "-" + ("0".concat(periodo1.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo1.getDate())).slice(-2);
	var fec_p2 = "" + periodo2.getFullYear() + "-" + ("0".concat(periodo2.getMonth() + 1)).slice(-2) + "-" + ("0".concat(periodo2.getDate())).slice(-2);

	for (var i = 0; i < serieCantidad.length; i++) {
		var fec001 = serieCantidad[i][0];
		var hora = parseInt(serieCantidad[i][1]);
		var valor = serieCantidad[i][2];
		if (fec001 == fec_p1) {
			serieP1[counta] = [hora, valor];
			counta++;
		}
		if (fec001 == fec_p2) {
			serieP2[countb] = [hora, valor];
			countb++;
		}
	}//for

	var plot2 = $.jqplot(name, [serieP1, serieP2], {
		textColor: "#003E6C",
		seriesColors: ['#850024', '#006fb9'],
		seriesDefaults: {
			renderer: $.jqplot.BlockRenderer,
			showMarker: false,
			fill: false,
			fillAndStroke: true,
			lineWidth: 0.5,
			shadow: false,
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: false,
				barMargin: 1,
				shadowDepth: 5,
			},
			animation: {
				show: false
			},
		},

		grid: {
			background: '#ffffff',
			borderColor: '#eeeeee',
			gridLineColor: '#F5F5F5',
			shadow: false,
			drawBorder: true,
			gridLineWidth: 0.8,
			borderWidth: 0.8,
			pad: 0,
		},

		cursor: {
			show: false,
			zoom: true,
			showTooltip: false,
			constrainZoomTo: 'x',
			dblClickReset: false,
		},

		axesDefaults: {
			showTickMarks: false,
			useSeriesColor: false,
			rendererOptions: {
				alignTicks: false,
			}
		},

		axes: {
			xaxis: {
				min: 0,
				max: 23,
				//mintickInterval: 1,
				label: 'Hora',
				showLabel: false,
				pad: 1,
				renderer: $.jqplot.LinearAxisRenderer,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontFamily: '"Roboto", sans-serif',
					fontSize: '8pt',
					show: false,
					size: 4,
					markSize: 4,
					formatString: "%d",
				}
			},
			yaxis: {
				min: minValor,
				max: maxValor,
				label: 'Valor',
				fontSize: '6px',
				showLabel: false,
				show: true,
				pad: 1,
				rendererOptions: {
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
				},
				tickOptions: {
					showTicks: false,
					angle: 0,
					showLabel: false,
					showMark: false,
					showGridline: false,
					fontSize: "8pt",
					size: 4,
					markSize: 6,
					show: false,
					pad: 1,
					fontFamily: '"Roboto", sans-serif',
					labelPosition: 'top',
					formatString: "%'.2f",
				}
			},
		},
	});

	return plot2;
}
