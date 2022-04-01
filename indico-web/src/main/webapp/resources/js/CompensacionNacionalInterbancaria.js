/**
 * Crea las gráficas para Transacción CNI
 */

function scroll (){

	 var ua = window.navigator.userAgent;
	    var msie = ua.indexOf("MSIE ");
	 if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	    {

		 try
		 {
			 var bodyheight = document.getElementById('vbajoval_chart').clientHeight;
		 }
		 catch(err){
			var bodyheight = 0;
		 }

		 if (bodyheight > 0  ) {
				window.scrollTo(0,bodyheight);
		 }

	    } else {
	    	window.scrollTo(0,300);

	    }
}

function parsedata(serieValores){

	let serieValoresF = [];
 	for (var i = 0; i < serieValores.length; i++) {
 	    var valor2 = parseFloat(serieValores[i][1].toFixed(2));
 	    var fecha = (serieValores[i][0]);
 	    serieValoresF.push([fecha, valor2]);
 	}
 	return serieValoresF;
}


function datos(serieValores){


	var  serieValoresF = [];

	 for (var i = 0; i < serieValores.length; i++) {
		 var valor2 = serieValores[i].serieValor;
		 var fecha = (serieValores[i].valorFecha);
		 serieValoresF.push([fecha, valor2]);
	 }
	 return serieValoresF;
}

function datosValor(serieValores){

	var serieValorD =[];

	 for (var i = 0; i < serieValores.length; i++) {
		 var valor2 = serieValores[i].serieValor;
		 var fecha = (serieValores[i].valorFecha);
		serieValorD.push([fecha, valor2]);
	 }
	 return serieValorD;
}

function getDateCounter(fCounter, dateend, period){

	if(fCounter == 1){
		switch(period) {
			case 1:
				return dateend	= new Date(dateend[0],1,2);
				break;
			case 2:
				return dateend	= new Date(dateend[0],2,1);
				break;
			case 3:
				return dateend	= new Date(dateend[0],3,1);
				break;
			case 4:
				return dateend	= new Date(dateend[0],6,1);
				break;
			case 5:
				return dateend	= new Date(dateend[0],11,31);
				break;
			default:
				return dateend	= new Date(dateend[0],(parseInt(dateend[1])-1),dateend[2]);
				break;
		}
	}else{
		return dateend	= new Date(dateend[0],(parseInt(dateend[1])-1),dateend[2]);
	}
}

function startvaluescomponentstimesPeriod(valchanged1,valchanged2,compdiario1,compdiario2,data,period,fCounter) {

	var inicioX;
	var endX;
	if (valchanged1!=null && valchanged2!=null) {
		inicioX = valchanged1;
		endX 	= valchanged2;
	} else {
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
		endX = getDateCounter(fCounter, fechaB, period);//new Date(fechaB[0],parseInt(fechaB[1])-1,fechaB[2]);
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


function createSliderTransBajoValor(divchartzoomslider,periodo,compPeriodo1,compPeriodo2,errormessage){

	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	var period = parseInt(periodo,10);

	try {
		data =  RestTransaccionCNI.getTransaccionCNI({'periodo':period});
		//var fCounter = data['SerieCantidadPorcentaje'].length;

		serieCantidad = data["SerieCantidadPorcentaje"];
		serieCantidad = datosValor(serieCantidad);
		var fCounter = serieCantidad.length;


		$("#"+divchartzoomslider).empty();
		$("#"+divchartzoomslider)[0].setAttribute("class","superzoom");

		data["Title"] = data["Title"];

		//startvaluescomponentstimes(null,null,compPeriodo1,compPeriodo2,data);
		startvaluescomponentstimesPeriod(null,null,compPeriodo1,compPeriodo2,data, period, fCounter);
		var divchartmax = document.createElement('div');

		divchartmax.id=divchartzoomslider+"_chartMax";
		divchartmax.style="chartcustom"
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		targetPlot 		= createplotmaxgeneralTransaccionCNI (divchartmax.id, data, period);

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

		controllerPlot 	= createplotmingeneralTransaccionCNI (idMini, data, period);

		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot._noToImageButton = true;
		idDivSlider = divchartzoomslider+"_slider";

		var innerDivSlider = document.createElement('div');
		innerDivSlider.id=idDivSlider;
		var styleSlider = "width:".concat((width-left-right+s_left+s_right+24)).concat("px !important; ")
								  .concat("margin-top:").concat(-8).concat("px !important;")
								  .concat("left:").concat(left-s_left-12).concat("px !important;")
								  .concat("right:").concat(right).concat("px !important;")
								  .concat("position: relative;");
		innerDivSlider.setAttribute("style",styleSlider);
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);

		var datestart 	= data["MinX"].split("-");
		datestart		= new Date(datestart[0],(parseInt(datestart[1])-1),datestart[2]);
		var dateend 	= data["MaxX"].split("-");

		dateend 		= getDateCounter(fCounter, dateend, period);

		var from 		= data["startX"].split("-");
		from			= new Date(from[0],parseInt(from[1])-1,from[2]);
		var to			= dateend;

		//Rangos del slider de control
		var minDays;
		var first;
		var updateperiodo;
		var minDays;
		var first;
		var updateperiodo;
		switch (period) {
			case 1:
						minDays = 10;
						first = true;
				break;
			case 2: 	minDays = 60;
						first = false;
						updateperiodo = true;
				break;
			case 3: 	minDays = 93;
						first = true;
				break;
			case 4: 	minDays = 185;
					first = true;
			break;
			case 5: 	minDays = 360;
						first = false;
						updateperiodo = true;
				break;
		};

		try{
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
	        	  			min: from,
	        	  			max: to
	        	  		},
	        	 	}
			);
		} catch (err) {
			console.log(err);
		}
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

		try{
		$("#"+idDivSlider).unbind();
		$("#"+idDivSlider).bind("valuesChanged", function(evt,dateSlider){

			valuesPlotChangedTimes(dateSlider,controllerPlot,targetPlot,compPeriodo1,compPeriodo2,data,period);
			if (dateSlider.first==null || dateSlider.first==false) {
				updatevaluescomponentstimesfromslider(dateSlider,compPeriodo1,compPeriodo2,data,period);
			}
		});

		$("#"+idDivSlider).trigger("valuesChanged",datesinit);

		} catch (err) {
			console.log(err);
		}

		scroll();
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
		});

		//Retira eventos y actualiza datos en dia
		$("#"+compPeriodo1+"_sel_dia").unbind();
		$("#"+compPeriodo1+"_sel_dia").change(function(evt) {

			switch (period) {
				case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					break;
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
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
		});

		$("#"+compPeriodo2+"_sel_semestre").unbind();
		$("#"+compPeriodo2+"_sel_semestre").change(function(evt) {

			if(period == 4){
				changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
		});

		//Retira eventos y actualiza datos en trimestre
		$("#"+compPeriodo1+"_sel_trimestre").unbind();
		$("#"+compPeriodo1+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
		});

		$("#"+compPeriodo2+"_sel_trimestre").unbind();
		$("#"+compPeriodo2+"_sel_trimestre").change(function(evt) {
			if(period == 3){
				changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
			}
			updatedataplottimesranges(targetPlot,controllerPlot,compPeriodo1,compPeriodo2,data,idDivSlider,period);
		});

	} catch (err) {
		console.log(err);
		$("[id*='"+errormessage+"'").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

//GRÁFICAS

function createplotmaxgeneralTransaccionCNI(name,data,tipo) {

	//var fCounter = data['SerieCantidadPorcentaje'].length;
 	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
 	ticks 			= data["Ticks"];
 	serieValores 	= data["SerieValoresPorcentaje"];
 	//serieValores = parsedata(serieValores);
	 serieValores = datosValor(serieValores);
 	serieCantidad	= data["SerieCantidadPorcentaje"];
 	serieCantidad	= datosValor(serieCantidad);
	//
	var fCounter = serieCantidad.length;

 	minValor		= data["MinValor"];
 	minCantidad		= data["MinCantidad"];
 	maxValor		= data["MaxValor"];
 	maxCantidad		= data["MaxCantidad"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
 	starx			= data["startX"];
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = starx.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = getDateCounter(fCounter, fecha2, tipo);
	//maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
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
            //label:'',
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
    		sizeAdjust : 5,
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
	            	fontSize: '9pt',
	            	show:true,
	            	size:4,
	            	markSize:4,
	            	formatString: formatter(tipo),
                }
            },
            yaxis:{
            	min:minValor,
            	max:maxValor,
				label: '<div style="padding-rigth: 24px;font-size:14px;">Valor (Miles de Millones)</div>',
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
	            	fontSize: "10pt",
	            	size:4,
	            	markSize:6,
	            	show:true,
	            	pad: 1,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#850024",
	        		formatString: "%' .2f ",
                }
            },
            y2axis:{
            	min:minCantidad,
            	max:maxCantidad,
            	showLabel:true,
            	label: '<div style="padding-left: 24px;font-size:14px;">Número de Transacciones</div>',
            	textColor: "#006fb9",
				rendererOptions:{
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: true,
				},
                tickOptions: {
                	showTicks: true,
	            	fontSize: "10pt",
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

function createplotmingeneralTransaccionCNI(name,data,tipo) {
 	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
 	title 			= data["Title"];
 	ticks 			= data["Ticks"];
 	serieValores 	= data["SerieValoresPorcentaje"];
 	//serieValores = parsedata(serieValores);
	serieValores = datosValor(serieValores);
 	serieCantidad	= data["SerieCantidadPorcentaje"];
	serieCantidad	= datosValor(serieCantidad);
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
	            	fontSize: '9pt',
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
	            	fontSize: "9pt",
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
            	label: '<div><div>Número </div><div style="color: gray;">Operaciones</div></div>',
            	textColor: "#006fb9",
				rendererOptions:{
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer,
				  forceTickAt0: false,
				  forceTickAt100: false,
				},
                tickOptions: {
                	showTicks: false,
	            	fontSize: "9pt",
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
	            	formatString: "%'.2d",
                },
            },
        },
	});
	return plot2;
}
