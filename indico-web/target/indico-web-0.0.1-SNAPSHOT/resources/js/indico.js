//Asigna a todos los links ejecutar el onload al cargar la pagina
//$(function(){
//	$('div[onload]').trigger('onload');
//	$('div[onresize]').trigger('resize');
//});

function start() {
	//PF('statusDialog').show();
}

function stop() {
	//PF('statusDialog').hide();
}

function onTabShow(panel) {
	iefix();
}

/**/

function exportChartPipe(selection, graficaW) {
	//debugger;
	var grafica = $("#" + graficaW + "_chart");
	if (grafica.length) {
		var options = {
			x_offset : 20,
			y_offset : 50,
			backgroundColor : "white",

		};
		if (selection == "jpg") {
			var image = grafica.jqplotToImageStr(options);
			var url = image.replace(/^data:image\/[^;]+/,
					'data:application/octet-stream');
			var link = document.createElement('a');
			link.download = "Canje.png";
			link.href = url;
			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);
		} else if (selection == "pdf") {
			var image = grafica.jqplotToImageStr(options);
			var docDefinition = {
				content : [ 'Gr&#225;fica Canje', {
					image : image,
					width : 520,
				} ]
			}
			pdfMake.createPdf(docDefinition).open();
			pdfMake.createPdf(docDefinition).download('primefaces-charts.pdf');
		}
	}
}

/**
 * funcion que exporta dos gráficas Pies Cheques
 * @param selection
 * @param graficaW
 * @param graficaW2
 * @param name
 * @returns
 */
function exportChartPipe(selection, graficaW, graficaW2, name) {
	
	var anio, mes1, dia1, semestre, trimestre;
	switch(name){
		case 'Canje Al Cobro':
			if ($("#vcanje_valperiodo1_sel_anio").val() != null) {
				anio 	= $("#vcanje_valperiodo1_sel_anio").val();
			}else if ($("#vcanje_valmensual1_sel_anio").val() != null) {
				anio 	= $("#vcanje_valmensual1_sel_anio").val();
			}else if ($("#vcanje_valtrimestral1_sel_anio").val() != null) {
				anio 	= $("#vcanje_valtrimestral1_sel_anio").val();
			}else if ($("#vcanje_valsemestral1_sel_anio").val() != null) {
				anio 	= $("#vcanje_valsemestral1_sel_anio").val();
			}else if ($("#vcanje_valanual1_sel_anio").val() != null) {
				anio 	= $("#vcanje_valanual1_sel_anio").val();
			}
			
			if ($("#vcanje_valperiodo1_sel_mes").val() != null) {
				mes1 	= $("#vcanje_valperiodo1_sel_mes").val();
			}else if ($("#vcanje_valmensual1_sel_mes").val() != null) {
				mes1 	= $("#vcanje_valmensual1_sel_mes").val();
			}else {mes1 = null;}
			
			if ($("#vcanje_valperiodo1_sel_dia").val() != null) {
				dia1 	= $("#vcanje_valperiodo1_sel_dia").val();
			}else {dia1 = null;}
			
			if ($("#vcanje_valtrimestral1_sel_trimestre").val() != null) {
				trimestre 	= $("#vcanje_valtrimestral1_sel_trimestre option:selected").text();
			}else {trimestre = null;}
			
			if ($("#vcanje_valsemestral1_sel_semestre").val() != null) {
				semestre = $("#vcanje_valsemestral1_sel_semestre option:selected").text();
			}else{semestre = null; }
			
			break;
		case 'Devoluciones':
			if ($("#vdevol_valperiodo1_sel_anio").val() != null) {
				anio 	= $("#vdevol_valperiodo1_sel_anio").val();
			}else if ($("#vdevol_valmensual1_sel_anio").val() != null) {
				anio 	= $("#vdevol_valmensual1_sel_anio").val();
			}else if ($("#vdevol_valtrimestral1_sel_anio").val() != null) {
				anio 	= $("#vdevol_valtrimestral1_sel_anio").val();
			}else if ($("#vdevol_valsemestral1_sel_anio").val() != null) {
				anio 	= $("#vdevol_valsemestral1_sel_anio").val();
			}else if ($("#vdevol_valanual1_sel_anio").val() != null) {
				anio 	= $("#vdevol_valanual1_sel_anio").val();
			}else {anio = null;}
			
			if ($("#vdevol_valperiodo1_sel_mes").val() != null) {
				mes1 	= $("#vdevol_valperiodo1_sel_mes").val();
			}else if ($("#vdevol_valmensual1_sel_mes").val() != null) {
				mes1 	= $("#vdevol_valmensual1_sel_mes").val();
			}else {mes1 = null;}
			
			if ($("#vdevol_valperiodo1_sel_dia").val() != null) {
				dia1 	= $("#vdevol_valperiodo1_sel_dia").val();
			}else {dia1 = null;}
			
			if ($("#vdevol_valtrimestral1_sel_trimestre").val() != null) {
				trimestre 	= $("#vdevol_valtrimestral1_sel_trimestre option:selected").text();
			}else {trimestre = null;}
			
			if ($("#vdevol_valsemestral1_sel_semestre").val() != null) {
				semestre = $("#vdevol_valsemestral1_sel_semestre option:selected").text();
			}else{semestre = null; }
			
		break;
	}
	
	var name2;
	
	if(anio != null){
		name2 = name+" "+anio;
	}
	
	if(mes1 != null){
		if(dia1 != null){
			name2 = name+" "+anio+" - "+mes1+" - "+dia1;
		}else{
			name2 = name+" "+anio+" - "+mes1;
		}
	}
	
	if(trimestre != null){		
		name2 = name+" "+anio+" - Trimestre "+trimestre;
	}

	if(semestre != null){
		name2 = name+" "+anio+" - Semestre "+semestre;
	}
	
	var grafica = $("#" + graficaW + "_chart");
	var grafica2 = $("#" + graficaW2 + "_chart");

	if (grafica.length) {
		var options = {
			x_offset : 20,
			y_offset : 50,
			backgroundColor : "white",
		};

		var imagesrc = grafica.jqplotToImageStr(options);
		var imagesrc2 = grafica2.jqplotToImageStr(options);
		/** *************************************************************************************************** */
		// create a canvas to join imgs
		var canvas = document.createElement('canvas');
		canvas.id = 'c';
		canvas.width = 1200;
		canvas.height = 500;
		var ctx1 = canvas.getContext("2d");
		ctx1.fillRect(0, 0, 1200, 500);
		ctx1.fillStyle = "#FFFFFF";
		document.body.appendChild(canvas);
		// get from DOM
		var canvas = document.getElementById("c");
		var ctx = canvas.getContext("2d");
		ctx.fillStyle = "#FFFFFF";
		ctx.fillRect(0, 0, 1200, 500);

		var image = new Image();
		var imagesplited = new Image();
		var image2 = new Image();
		var imagesplited2 = new Image();

		image.src = imagesrc;
		image2.src = imagesrc2;

		image.onload = function() {
			// split image
			//console.log(splicircularImage(image));
			imagesplited.src = splicircularImage(image);
		};

		image2.onload = function() {
			// split image
			//console.log(splicircularImage(image2));
			imagesplited2.src = splicircularImage(image2);
		};

		imagesplited.onload = function() {

			ctx.drawImage(imagesplited, 120, 30);
			var text = $(
					"#"
							+ graficaW
							+ "_legend > center > table > tbody > tr:nth-child(1) > td > div > div.custompielegen")
					.text().trim();
			var text2 = $(
					"#"
							+ graficaW
							+ "_legend > center > table > tbody > tr:nth-child(1) > td > div > div.FloatRigth")
					.text().trim();
			var arr = GetCellValues("#" + graficaW + "_legend > center > table");
			arr.unshift([ text, text2 ]);
			drawBottomdata(ctx, 0, imagesplited, arr, name2);
		}
		imagesplited2.onload = function() {
			ctx.drawImage(imagesplited2, 620, 30);

			// data footer image
			var text = $(
					"#"
							+ graficaW2
							+ "_legend > center > table > tbody > tr:nth-child(1) > td > div > div.custompielegen > div")
					.text().trim();
			var text2 = $(
					"#"
							+ graficaW2
							+ "_legend> center > table > tbody > tr:nth-child(1) > td > div > div.FloatRigth")
					.text().trim();

			var arr = GetCellValues("#" + graficaW2
					+ "_legend > center > table");
			arr.unshift([ text, text2 ]);
			drawBottomdata(ctx, 500, imagesplited2, arr, name2);
			
			canvas = document.getElementById("c");
			if (selection == "jpg") {
				if (canvas.msToBlob) { //for IE
	                var blob = canvas.msToBlob();
	                window.navigator.msSaveBlob(blob, name+'.png');
	            } else {
					//console.log("jpg")
					var url = canvas.toDataURL("image/png");
					//console.log(url);
					var link = document.createElement('a');
					link.download = name2+'.png';
					link.href = url;
					document.body.appendChild(link);
					link.click();
	
					document.body.removeChild(link);
	            }
			} else if (selection == "pdf") {
				 var image = canvas.toDataURL("image/png");
				 var docDefinition = {
				 // by default we use portrait, you can change it to landscape
				 //if you wish
				 pageOrientation: 'landscape',
				 // a string or { width: number, height: number }
				 pageSize: 'A2',
				 content : [
					 {
						 image : image,
						 width : 1200,
					 } 
				 ]
				}
				 pdfMake.createPdf(docDefinition).open();
				 pdfMake.createPdf(docDefinition).download(name2+'.pdf');
			}
			document.body.removeChild(canvas);
		};
	}
}

function splicircularImage(image) {
	var canvas = document.createElement('canvas');
	var ctx = canvas.getContext('2d');
	canvas.width = 250;
	canvas.height = 300;
	var wx = image.width / 2;
	var yx = 20;
	ctx.drawImage(image, -(wx - 120), -65, image.width, image.height);
	return canvas.toDataURL();
}

function drawBottomdata(ctx, xpoint, image, array, name) {
	
	ctx.font = "bold 20px Arial";
	ctx.fillText(name, 10, 10);
	
	var text = array[0][0];
	var text2 = array[0][1];
	var initpoint = (image.width / 5) + xpoint;
	ctx.textBaseline = "alphabetic";
	ctx.font = "bold 12px Arial";
	ctx.fillStyle = "#850024";
	ctx.fillText(text, initpoint, image.height);

	var text_width = ctx.measureText(text).width;
	var initline = initpoint + text_width;
	var endline = initline + 209;

	// Reset the current path
	ctx.beginPath();
	ctx.strokeStyle = "#850024";
	// Staring point X
	ctx.moveTo(initline, image.height);
	// End point X
	ctx.lineTo(endline, image.height);
	// Make the line visible
	ctx.stroke();

	ctx.fillText(text2, endline, image.height);

	// nextrows

	for (var i = 1; i < array.length; i += 1) {
		var initpoint2 = initpoint - 2;
		var initpoint2text = initpoint + 12 + 22;
		var nextrow = image.height + (15 * i);
		ctx.textBaseline = "top";

		// rect
		ctx.fillStyle = array[i][0];
		ctx.fillRect(initpoint2, nextrow, 20, 8);
		ctx.stroke();

		ctx.fillStyle = "#535353";
		ctx.fillText(array[i][1], initpoint2text, nextrow);

		var text_width2 = ctx.measureText(array[i][2]).width;
		var initpoint2text2 = initpoint2text + (350 - text_width2);

		ctx.fillText(array[i][2], initpoint2text2, nextrow);
	}

}

function GetCellValues(table) {
	var arr = [];
	var tableTRs = document.querySelectorAll(table + '> tbody  > tr');
	// Convert buttons NodeList to an array
	var tableTRsArr = Array.prototype.slice.call(tableTRs);
	tableTRsArr.shift();

	for (var i = 0; i < tableTRsArr.length; i += 1) {
		arr[i] = [];
		var cellLength = tableTRsArr[i].cells.length;
		for (var y = 0; y < cellLength; y += 1) {
			var cell = tableTRsArr[i].cells[y];
			if (y == 0) {
				// this substring fix "Shorthand Property code CSS" because some
				// browser return more tah others @qleoz12 GITHUB
				arr[i][y] = cell.style.background.substr(0,
						cell.style.background.indexOf(")") + 1);
			} else {
				arr[i][y] = cell.innerHTML.trim();
			}
		}
	}
	return arr;
}

/**
 * Funcion que exporta gráficas en PDF y JPG para Transferencia
 * @param selection
 * @param graficaW
 * @param name
 * @returns
 */
function exportChart(selection, graficaW, name) {
	var grafica = $("#" + graficaW + "_chartMax");
	var grafica2 = $("#" + graficaW + "_chartMax2");
	var idGrafica = graficaW + "_chartMax";
	var idGrafica2 = graficaW + "_chartMax2"; 
	
	var options = {
			x_offset : 20,
			y_offset : 50,
			backgroundColor : "white",
		};
	
	if(grafica.length){
		exportaGraficas(selection, idGrafica, options, name);
	}
	
	if(grafica2.length){
		exportaGraficas(selection, idGrafica2, options, name);
	}

}
/**
 * Funcion que exporta gráficas en PDF y JPG para Composicion
 * @param selection
 * @param graficaW
 * @param name
 * @returns
 */
function exportChart2(selection, graficaW, name) {
	var grafica = $("#" + graficaW + "_chartMax");
	var grafica2 = $("#" + graficaW + "_chartMax2");
	var idGrafica = graficaW + "_chartMax";
	var idGrafica2 = graficaW + "_chartMax2"; 
	
	if (grafica.length) {
		var options = {
			x_offset : 20,
			y_offset : 60,
			backgroundColor : "white",
		};

		var imagesrc = grafica.jqplotToImageStr(options);
		var imagesrc2 = grafica2.jqplotToImageStr(options);
		/** *************************************************************************************************** */
		// create a canvas to join imgs
		var canvas = document.createElement('canvas');
		canvas.id = 'c';
		canvas.width = 1920;
		canvas.height = 1080;
		var ctx1 = canvas.getContext("2d");
		ctx1.fillRect(0, 0, 1920, 1080);
		ctx1.fillStyle = "#FFFFFF";
		document.body.appendChild(canvas);
		// get from DOM
		var canvas = document.getElementById("c");
		var ctx = canvas.getContext("2d");
		ctx.fillStyle = "#FFFFFF";
		ctx.fillRect(0, 0, 1920, 1080);

		var image = new Image();
		var imagesplited = new Image();
		var image2 = new Image();
		var imagesplited2 = new Image();

		image.src = imagesrc;
		image2.src = imagesrc2;

		image.onload = function() {
			ctx.drawImage(image, 60, 40);
		}
		
		image2.onload = function() {
			ctx.drawImage(image2, 60, 420);
			
			canvas = document.getElementById("c");
			if (selection == "jpg") {
				if (canvas.msToBlob) { //for IE
	                var blob = canvas.msToBlob();
	                window.navigator.msSaveBlob(blob, name+'.png');
	            } else {
					//console.log("jpg")
					var url = canvas.toDataURL("image/png");
					//console.log(url);
					var link = document.createElement('a');
					link.download = name+'.png';
					link.href = url;
					document.body.appendChild(link);
					link.click();
					document.body.removeChild(link);
	            }
			} else if (selection == "pdf") {
				 var image = canvas.toDataURL("image/png");
				 var docDefinition = {
				 // by default we use portrait, you can change it to landscape
				 //if you wish
				 pageOrientation: 'landscape',
				 // a string or { width: number, height: number }
				 pageSize: 'A2',
				 content : [
					 {
						 image : image,
						 width : 1920,
					 } 
				 ]
				}
				 pdfMake.createPdf(docDefinition).open();
				 pdfMake.createPdf(docDefinition).download(name+'.pdf');
			}
			document.body.removeChild(canvas);
		};
	}
}


/**
 * Exporta gráficas 
 * @param selection
 * @param graficaW
 * @param name
 * @returns
 */
function exportaGraficas(selection, idGrafica, options, name){
	
	if (selection == "jpg") {
		//debugger;
		var image = $("#" + idGrafica).jqplotToImageStr(options);
		var grafica = $("#" + idGrafica);
		
		var imagesrc = grafica.jqplotToImageStr(options);
		
		// create a canvas to join imgs
		var canvas = document.createElement('canvas');
		canvas.id = 'c';
		canvas.width = 1920;
		canvas.height = 1080;
		var ctx1 = canvas.getContext("2d");
		ctx1.fillRect(0, 0, 1200, 600);
		ctx1.fillStyle = "#FFFFFF";
		document.body.appendChild(canvas);
		// get from DOM
		var canvas = document.getElementById("c");
		var ctx = canvas.getContext("2d");
		ctx.fillStyle = "#FFFFFF";
		ctx.fillRect(0, 0, 1200, 600);

		var image2 = new Image();
		image2.src = imagesrc;
		image2.onload = function() {
			ctx.drawImage(image2, 60, 40);
			canvas = document.getElementById("c");
			if (canvas.msToBlob) { //for IE
	            var blob = canvas.msToBlob();
	            window.navigator.msSaveBlob(blob, name+'.png');
	        }else{
			
	        	var url = image.replace(/^data:image\/[^;]+/,
					'data:application/octet-stream');
			
				var link = document.createElement('a');
				link.download = name+".png";
				link.href = url;
				document.body.appendChild(link);
				link.click();
				document.body.removeChild(link);
	        }
			document.body.removeChild(canvas);
		}
		
	} else if (selection == "pdf") {
		var image = $("#" + idGrafica).jqplotToImageStr(
				options);
		var docDefinition = {
			content : [ name, {
				image : image,
				width : 520,
			} ]
		}
		pdfMake.createPdf(docDefinition).open();
		pdfMake.createPdf(docDefinition).download(name+'.pdf');
	}
}

function chartextenderTransferenciaComplete() {
	this.cfg.textColor = "#003E6C";
	this.cfg.seriesColors = [ '#850024', '#006fb9' ];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';

	this.zoomEnabled = true;

	this.cfg.cursor = {
		show : true,
		zoom : false,
		constrainZoomTo : 'x'
	};

	this.cfg.grid = {
		background : '#ffffff',
		borderColor : '#eeeeee',
		gridLineColor : '#F5F5F5',
		shadow : false,
		drawBorder : false,
		gridLineWidth : 0
	};

	this.cfg.legend = {
		show : false,
		placement : 'inside'
	};

	this.cfg.seriesDefaults = {
		fill : true,
		fillAndStroke : false,
		showMarker : false,
		rendererOptions : {
			smooth : false,
			animation : {
				show : true
			},
		},
	};

	this.cfg.axesDefaults.showTickMarks = false;

	this.cfg.highlighter = {
		show : false,
		sizeAdjust : 1,
		tooltipOffset : 9,
		useYTickMarks : false,
		useXTickMarks : false,
		currentNeighbor : null,
		showMarker : false,
		lineWidthAdjust : 1,
		showTooltip : false,
		tooltipLocation : 'e',
		fadeTooltip : false,
		tooltipAxes : 'y',
		tooltipSeparator : ', ',
		useAxesFormatters : false,
		yvalues : 0,
		xvalues : 0
	};

	/* Serie Eje Y */
	this.cfg.axes.xaxis.showLabel = false;
	this.cfg.axes.xaxis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
	this.cfg.axes.xaxis.labelRenderer = $.jqplot.AxisTickRenderer;
	this.cfg.axes.xaxis.tickOptions.showMark = false;
	this.cfg.axes.xaxis.tickOptions.showGridline = false;
	this.cfg.axes.xaxis.tickOptions.size = 0;
	this.cfg.axes.xaxis.tickOptions.markSize = 0;
	this.cfg.axes.xaxis.tickOptions.show = false;
	this.cfg.axes.xaxis.tickOptions.showLabel = false;

	this.cfg.axes.yaxis.showLabel = false;
	this.cfg.axes.yaxis.labelRenderer = $.jqplot.AxisTickRenderer;
	this.cfg.axes.yaxis.tickOptions.showMark = false;
	this.cfg.axes.yaxis.tickOptions.showGridline = false;
	this.cfg.axes.yaxis.tickOptions.size = 0;
	this.cfg.axes.yaxis.tickOptions.markSize = 0;
	this.cfg.axes.yaxis.tickOptions.show = false;
	this.cfg.axes.yaxis.tickOptions.showLabel = false;

	this.cfg.axes.y2axis.showLabel = false;
	this.cfg.axes.y2axis.labelRenderer = $.jqplot.AxisTickRenderer;
	this.cfg.axes.y2axis.tickOptions.showMark = false;
	this.cfg.axes.y2axis.tickOptions.showGridline = false;
	this.cfg.axes.y2axis.tickOptions.size = 0;
	this.cfg.axes.y2axis.tickOptions.markSize = 0;
	this.cfg.axes.y2axis.tickOptions.show = false;
	this.cfg.axes.y2axis.tickOptions.showLabel = false;

	// this.cfg.axes.xaxis.tickInterval = 50;
	// this.cfg.axes = {
	// xaxis : {
	// pad: 0,
	// min: 0,
	// max: 100,
	// showLabel:false,
	// labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
	// tickInterval: 3,
	// tickOptions: {
	// showGridline: false,
	// }
	// },
	// yaxis: {
	// borderColor: "#850024",
	// textColor: "#850024",
	// showLabel:false,
	// labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
	// labelOptions: {
	// textColor: "#850024",
	// angle: 0,
	// fontSize: '8pt',
	// fontWeight: 'bold',
	// fontFamily:'"Roboto", sans-serif'
	// },
	// tickOptions : {
	// textColor: "#850024",
	// formatString: "%'.2f",
	// }
	// }
	// }
}

function chartextenderTransferencia() {

	this.cfg.textColor = "#003E6C";
	this.cfg.seriesColors = [ '#850024', '#006fb9' ];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	this.zoomEnabled = true;

	this.cfg.cursor = {
		show : true,
		zoom : true,
		constrainZoomTo : 'x'
	};

	/*
	 * plot2.axes.yaxis.min = minY; plot2.axes.yaxis.max = maxY;
	 * plot2.yaxis._numberTicks
	 */

	this.cfg.grid = {
		background : '#ffffff',
		borderColor : '#eeeeee',
		gridLineColor : '#F5F5F5',
		shadow : false,
		drawBorder : true,
		gridLineWidth : 0
	};

	this.cfg.legend = {
		show : true,
		placement : 'inside'
	};

	this.cfg.seriesDefaults = {
		showMarker : true
	}

	this.cfg.highlighter = {
		show : true,
		sizeAdjust : 5,
		tooltipOffset : 9,
		useYTickMarks : true,
		useXTickMarks : true,
		currentNeighbor : null,
		showMarker : true,
		lineWidthAdjust : 2.5,
		showTooltip : true,
		tooltipLocation : 'e',
		fadeTooltip : true,
		tooltipAxes : 'y',
		tooltipSeparator : ', ',
		useAxesFormatters : true,
		yvalues : 1
	};

	/* Serie Eje Y */

	this.cfg.axes.yaxis.borderColor = "#850024";
	this.cfg.axes.yaxis.showLabel = true;
	this.cfg.axes.yaxis.label = 'Valor';
	this.cfg.axes.yaxis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
	this.cfg.axes.yaxis.labelOptions = {
		textColor : "#850024",
		angle : 0,
		fontSize : '8pt',
		fontWeight : 'bold',
		fontFamily : '"Roboto", sans-serif'
	}
	this.cfg.axes.yaxis.textColor = "#850024";
	this.cfg.axes.yaxis.borderColor = "#850024";
	this.cfg.axes.yaxis.tickOptions = {
		textColor : "#850024",
		formatString : "%'.2f",
	}

	/* Segunda serie eje y */
	this.cfg.axes.y2axis.borderColor = "#006fb9";
	this.cfg.axes.y2axis.tickOptions.textColor = "#006fb9";
	this.cfg.axes.y2axis.tickOptions = {
		textColor : "#006fb9",
		formatString : '%.0f',
	}

	this.cfg.axes.y2axis.showLabel = true;
	this.cfg.axes.y2axis.label = "Cantidad";
	this.cfg.axes.y2axis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
	this.cfg.axes.y2axis.labelOptions = {
		textColor : "#006fb9",
		angle : 0,
		fontSize : '8pt',
		fontWeight : 'bold',
		fontFamily : '"Roboto", sans-serif'
	}

};// chart transferencia

function updateRangeSlider(rangeId) {

	$("#sliderTransferencia").rangeSlider({
		bounds : {
			min : 0,
			max : 100
		}
	});

	// Change options after slider creation
	$("#sliderTransferencia").rangeSlider("option", "bounds", {
		min : 10,
		max : 90
	});

	// Get option value
	var bounds = $("#slider").rangeSlider("option", "bounds");

	// Set date option
	$("#dateSliderTransferencia").dateRangeSlider("option", "bounds", {
		min : new Date(2012, 0, 1),
		max : new Date(2012, 11, 31)
	});

	// Set multiple options at once
	$("#dateSliderTransferencia").dateRangeSlider("option", {
		bounds : {
			min : new Date(2012, 0, 1),
			max : new Date(2012, 11, 31)
		},
		enabled : false
	});

};

function sliderTransferenciaFn(divId, chart2) {
	div = document.getElementById(divId)
	var div = document.getElementById(divId);
	var chart2N = document.getElementById(chart2);
	var wt = $.jqplot.CanvasManager.canvases[0].width;
	var w = $.jqplot.CanvasManager.canvases[19].width + 44;
	var le = parseInt($.jqplot.CanvasManager.canvases[19].style.left, 10);
	var left = wt - w - le - 34;
	// div.style.minWidth=(w-1)+"px";
	// div.style.maxWidth=w+"px";
	div.style.width = w + "px";
	chart2N.width = w;
	// div.style.paddingLeft=left+"px";
	div.style.marginLeft = left + "px";
	chart2N.style.marginLeft = left + "px !important";
}

function chartextenderDistValor() {

	this.cfg.textColor = "#003E6C";
	this.cfg.seriesColors = [ '#850024', '#006fb9' ];
	this.cfg.escapeHTML = false;
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';

	this.cfg.grid = {
		background : '#ffffff',
		borderColor : '#eeeeee',
		gridLineColor : '#F5F5F5',
		shadow : false,
		drawBorder : true,
		gridLineWidth : 0
	};

	this.cfg.legend = {
		show : true,
		placement : 'inside'
	};

	this.cfg.highlighter = {
		show : true,
		tooltipOffset : 9,
		useYTickMarks : true,
		useXTickMarks : true,
		currentNeighbor : null,
		showMarker : false,
		lineWidthAdjust : 2.5,
		sizeAdjust : 9,
		showTooltip : true,
		tooltipLocation : 'nw',
		fadeTooltip : true,
		tooltipAxes : 'y',
		tooltipSeparator : ', ',
		useAxesFormatters : true,
		yvalues : 1
	};

	this.cfg.axes.xaxis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
	this.cfg.axes.xaxis = {
		label : 'Horas',
		showLabel : true,
		ticks : [ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
				"12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
				"22", "23" ],
		tickOptions : {
			angle : -0,
			fontSize : '1em'
		},
		labelOptions : {
			textColor : "#850024",
			angle : 0,
			fontSize : '8pt',
			fontWeight : 'bold',
			fontFamily : '"Roboto", sans-serif'
		}
	}
	this.cfg.axes.yaxis = {
		labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
		borderColor : "#850024",
		showLabel : true,
		escapeHTML : false,
		label : "Valor (%)",
		textColor : "#850024",
		labelOptions : {
			textColor : "#850024",
			angle : 0,
			fontSize : '8pt',
			fontWeight : 'bold',
			fontFamily : '"Roboto", sans-serif',
			escapeHTML : false,
		},
		tickOptions : {
			textColor : "#850024",
			formatString : "%'.2f",
		}
	}

	this.cfg.axes.y2axis = {
		tickOptions : {
			show : false
		}
	}
}

function chartextenderDistCantidad() {

	this.cfg.textColor = "#003E6C";
	this.cfg.seriesColors = [ '#850024', '#006fb9' ];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';

	this.cfg.grid = {
		background : '#ffffff',
		borderColor : '#eeeeee',
		gridLineColor : '#F5F5F5',
		shadow : false,
		drawBorder : true,
		gridLineWidth : 0
	};

	this.cfg.legend = {
		show : true,
		placement : 'inside'
	};

	this.cfg.highlighter = {
		show : true,
		sizeAdjust : 1,
		tooltipOffset : 9,
		useYTickMarks : true,
		useXTickMarks : true,
		currentNeighbor : null,
		showMarker : false,
		lineWidthAdjust : 2.5,
		showTooltip : true,
		tooltipLocation : 'nw',
		fadeTooltip : true,
		tooltipAxes : 'y',
		tooltipSeparator : ', ',
		useAxesFormatters : false,
		yvalues : 1,
		tooltipFormatString : '%s'
	};

	this.cfg.axes.xaxis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
	this.cfg.axes.xaxis = {
		label : 'Horas',
		showLabel : true,
		ticks : [ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
				"12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
				"22", "23" ],
		tickOptions : {
			angle : -0,
			fontSize : '1em'
		},
		labelOptions : {
			textColor : "#850024",
			angle : 0,
			fontSize : '8pt',
			fontWeight : 'bold',
			fontFamily : '"Roboto", sans-serif'
		}
	}

	this.cfg.axes.yaxis.borderColor = "#850024";
	this.cfg.axes.yaxis.showLabel = true;
	this.cfg.axes.yaxis.label = 'Cantidad (operaciones)';
	this.cfg.axes.yaxis.labelRenderer = $.jqplot.CanvasAxisLabelRenderer;
	this.cfg.axes.yaxis.labelOptions = {
		textColor : "#850024",
		angle : 0,
		fontSize : '8pt',
		fontWeight : 'bold',
		fontFamily : '"Roboto", sans-serif'
	}
	this.cfg.axes.yaxis.textColor = "#850024";
	this.cfg.axes.yaxis.borderColor = "#850024";
	this.cfg.axes.yaxis.tickOptions = {
		textColor : "#850024",
		formatString : "%'.2f",
	}

	this.cfg.axes.y2axis = {
		tickOptions : {
			show : false
		}
	}

}

function chartextenderPagosRotacion() {
	this.cfg.textColor = "#003E6C";
	this.cfg.seriesColors = [ '#850024' ];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';

	this.cfg.grid = {
		background : '#ffffff',
		borderColor : '#eeeeee',
		gridLineColor : '#F5F5F5',
		shadow : false,
		drawBorder : true,
		gridLineWidth : 0
	};
	this.cfg.legend = {
		show : true,
		placement : 'inside'
	};
	this.cfg.seriesDefaults = {
		showMarker : true
	};

	this.cfg.highlighter = {
		show : true,
		tooltipOffset : 9,
		useYTickMarks : true,
		useXTickMarks : true,
		currentNeighbor : null,
		showMarker : true,
		lineWidthAdjust : 2.5,
		sizeAdjust : 5,
		showTooltip : true,
		tooltipLocation : 'e',
		fadeTooltip : true,
		tooltipAxes : 'y',
		tooltipSeparator : ', ',
		useAxesFormatters : true,
		yvalues : 1
	};
	// this.cfg.axes.xaxis.tickOptions.formatString= "%m-%d-%Y";
	this.cfg.axes.yaxis = {
		borderColor : "#850024",
		textColor : "#850024",
		showLabel : true,
		label : 'Valor',
		labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
		labelOptions : {
			textColor : "#850024",
			angle : 0,
			fontSize : '8pt',
			fontWeight : 'bold',
			fontFamily : '"Roboto", sans-serif'
		},
		tickOptions : {
			textColor : "#850024",
			formatString : "%'.2f",
		}
	}
}

function chartextenderPagosPIB() {
	this.cfg.textColor = "#003E6C";
	this.cfg.seriesColors = [ '#850024' ];
	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';

	this.cfg.grid = {
		background : '#ffffff',
		borderColor : '#eeeeee',
		gridLineColor : '#F5F5F5',
		shadow : false,
		drawBorder : true,
		gridLineWidth : 0
	};
	this.cfg.legend = {
		show : true,
		placement : 'inside'
	};
	this.cfg.seriesDefaults = {
		showMarker : true
	};

	this.cfg.highlighter = {
		show : true,
		tooltipOffset : 9,
		useYTickMarks : true,
		useXTickMarks : true,
		currentNeighbor : null,
		showMarker : true,
		lineWidthAdjust : 2.5,
		sizeAdjust : 5,
		showTooltip : true,
		tooltipLocation : 'e',
		fadeTooltip : true,
		tooltipAxes : 'y',
		tooltipSeparator : ', ',
		useAxesFormatters : true,
		yvalues : 1
	};

	this.cfg.axes.yaxis = {
		textColor : "#850024",
		borderColor : "#850024",
		showLabel : true,
		label : 'Valor',
		labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
		labelOptions : {
			textColor : "#850024",
			angle : 0,
			fontSize : '8pt',
			fontWeight : 'bold',
			fontFamily : '"Roboto", sans-serif'
		},
		tickOptions : {
			textColor : "#850024",
			formatString : "%'.2f",
		}
	}
}
