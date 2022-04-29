/**
 * Crea las gráficas para la Composición del Canje
 */

function scroll (){
	
	 var ua = window.navigator.userAgent;
	    var msie = ua.indexOf("MSIE ");
	 if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
	    {
		
		 try 
		 { 
			 var bodyheight = document.getElementById('vcomcanje_chart').clientHeight;
			
			
		 }
		 catch(err){
			var bodyheight = 0;
		 }
		 
		 if (bodyheight > 0  ) {
			  var bodyheight = bodyheight -70;
				window.scrollTo(0,400);
		 }
			
	    }else {
	    	
	    	 try 
			 { 
				 var bodyheight = document.getElementById('vcomcanje_chart').clientHeight;
				
				
			 }
			 catch(err){
				var bodyheight = 0;
			 }
			 
			 if (bodyheight > 0  ) {
				  var bodyheight = bodyheight -70;
					window.scrollTo(0,400);
			 }
	    	
	    }
}

function createSliderComposicionCanje(divchartzoomslider, periodo,compPeriodo1,compPeriodo2,rangocanjesel,label,errormessage,almacen){

	var targetPlot,controllerPlot,idMini,idDivSlider,data;
	var period = parseInt(periodo,10);
  
	try {
		data =  RestComposicionCanjeServices.getComposicionCanje({'periodo':period,'rangocanje':rangocanjesel});
		
		$("#"+divchartzoomslider).empty();
		$("#"+divchartzoomslider)[0].setAttribute("class","superzoomStack");
		//concatena el Label de la clase con el label por defecto
		if (label==null || label =="undefined") label = "Todos";
		
		//Concatena el Título Geerico con el Label del filtro de rangos (Todos)
		data["Title"] = data["Title"];
		
		var vStorageFecStart = localStorage.getItem(almacen + "_fecStart");
		var vStorageFecEnd = localStorage.getItem(almacen + "_fecEnd");

		vStorageFecStart = completarFechaStart(vStorageFecStart,data["Ticks"]);
		vStorageFecEnd = completarFechaEnd(vStorageFecEnd,data["Ticks"]);

		//Inicializa los componentes de tiempo
		startvaluescomponentstimes (vStorageFecStart, vStorageFecEnd, compPeriodo1, compPeriodo2, data);
		
		//Crea Div para la gráfica
		var divchartmax = document.createElement('div');
		var divchartmax2 = document.createElement('div');
		var separador = document.createElement('div');
		
		var styleSeprador = "height:".concat(55).concat("px !important;");
		separador.setAttribute("style",styleSeprador);
		//Agrega el id para el div
		divchartmax.id=divchartzoomslider+"_chartMax";
		divchartmax2.id=divchartzoomslider+"_chartMax2";
		//Agrega los estilos para el div
		divchartmax.style="chartcustom";
		divchartmax2.style="chartcustom";
		divchartmax2.style="margin-top: 40px";
		//Agregar al Div nuevo el div de la gráfica
		document.getElementById(divchartzoomslider).appendChild(divchartmax);
		document.getElementById(divchartzoomslider).appendChild(separador);
		document.getElementById(divchartzoomslider).appendChild(divchartmax2);
		
		//Función que crea la gráfica de acuerdo al periodo
		targetPlot = createplotmaxComposicionCanje (divchartmax.id, data , period, 1);
		targetPlot2 = createplotmaxComposicionCanje (divchartmax2.id, data , period, 2);
		//Crea los estilos para el plot destino
		var s_left 		= targetPlot._defaultGridPadding.left;
		var s_right 	= targetPlot._defaultGridPadding.right;
		var left 		= targetPlot._gridPadding.left;
		var right		= targetPlot._gridPadding.right;
		var width 		= targetPlot._plotDimensions.width;
		
		idMini = divchartzoomslider+"_divmini";
		//Crea el div Mini
		var innerDivMini = document.createElement('div');
		
		//Agrega el id para div Mini
		innerDivMini.id = idMini;
		
		//Crea los estilos para Mini
		var style = "width:".concat((width-left)).concat("px !important; ")
					.concat("left:").concat(0).concat("px !important;")
					.concat("right:").concat(right-s_right).concat("px !important;")
					.concat("height:").concat(55).concat("px !important;");
		
		//Agrega los estilos
		innerDivMini.setAttribute("style",style);
		
		//Agrega el div Mini al Div de las Gráficas
		document.getElementById(divchartzoomslider).appendChild(innerDivMini);
		
		//Crea la gráfica mini de control de acuerdo al periodo
		controllerPlot 	= createplotminComposicionCanje (idMini, data , period);
		
		//agrega el control del mini al principal
		$.jqplot.Cursor.zoomProxy(targetPlot, controllerPlot);
		$.jqplot.Cursor.zoomProxy(targetPlot2, controllerPlot);
		//Quita el boton de convertir en imagen
		$.jqplot._noToImageButton = true;
		
		//Crea Div de union
		idDivSlider = divchartzoomslider+"_slider";
		var innerDivSlider = document.createElement('div');
		//Agrega un id al nuevo div
		innerDivSlider.id=idDivSlider;
		//Crea los estilos del Div
		var styleSlider = "width:".concat((width-left)).concat("px !important; ")
							  .concat("margin-top:").concat(-8).concat("px !important;")
							  .concat("left:").concat(left-s_left-12).concat("px !important;")
							  .concat("right:").concat(right).concat("px !important;")
							  .concat("position: relative;");

		//Agrega los estilos
		innerDivSlider.setAttribute("style",styleSlider);
		//Agregar el nuevo Div al Div principal
		document.getElementById(divchartzoomslider).appendChild(innerDivSlider);
		
		
		var ticks = data ["Ticks"];		
		//Variable de Fecha de inicio
		var datestart 	= data["MinX"].split("-");
		//Convierte la fecha a formato de JS
		datestart		= new Date(datestart[0],(parseInt(datestart[1])-1),datestart[2]);
		//Variable de Fecha de Din
		var dateend 	= data["MaxX"].split("-");
		//Convierte la fecha a formato de JS
		dateend			= new Date(dateend[0],(parseInt(dateend[1])-1),dateend[2]);
		//Variable de fecha Desde
		var from 	= data["startX"].split("-");
		//Convierte la fecha a formato de JS
		from		= new Date(from[0],parseInt(from[1])-1,from[2]);
		//Variable de fecha Hasta
		var to				= dateend;
	
		//Rangos del slider de control
		
		var minDays, maxDays;
		switch (period) {
			case 2: 	minDays =1;
				break;
			case 3: 	minDays = 3;
				break;
			case 4: 	minDays = 6;
				break;
			case 5: 	minDays = 12;
				break;
		};
		
		var byStep;
		switch (period) {
			case 2: 	byStep =1;
				break;
			case 3: 	byStep = 3;
				break;
			case 4: 	byStep = 6;
				break;
			case 5: 	byStep = 12;
				break;
		};
		
		
				var dateSlider = $("#"+idDivSlider).dateRangeSlider( {
				                    			range:{ 
						                    	    min: {months: minDays}
				                    	  		},
				                    	  		bounds: {
				                    	  			min: datestart, 
				                    	  			max: dateend
				                    	  		},
				                    	  		defaultValues: {
				                    	  			min: vStorageFecStart, 
				                    	  			max: vStorageFecEnd
				                    	  		},
				                    	  		step:{
				                    	  			months: byStep
				                    	  		},
				                 });
				
				//Funcion que inicializa en la primera carga de página el slider
				var datesinit = {
						label: dateSlider,
						values : {
							min: vStorageFecStart,
							max: vStorageFecEnd,
						},
						first: true
				}
				
				//Remueve los eventos para el Slider
				$("#"+idDivSlider).unbind();
				//Actualiza el Slider agregandole un evento
				$("#"+idDivSlider).bind("valuesChanged", function(evt,dateSlider){
					//if (dateSlider.first==null || dateSlider.first==false) {
						updFilterBySliderReplot(dateSlider,compPeriodo1,compPeriodo2,data,period,targetPlot,targetPlot2,"slider");
					//}
				});
				
				//Lanza la reubicación del Slider
				$("#"+idDivSlider).trigger("valuesChanged",datesinit);
				scroll();
				
				/**
				 * 
				 */
				//Retira eventos y actualiza datos en años
				$("#"+compPeriodo1+"_sel_anio").unbind();
				$("#"+compPeriodo1+"_sel_anio").change(function(evt) {
					switch (period) {
						case 1, 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
						case 3: 		changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
						case 4, 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
					};		
					
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,period,"");
				});
				
				$("#"+compPeriodo2+"_sel_anio").unbind();
				$("#"+compPeriodo2+"_sel_anio").change(function(evt) {
					switch (period) {
						case 1, 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
						case 3: 		changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
						case 4, 5: 	changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
					};
					
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,period,"");
				});
				//Retira eventos y actualiza datos en Semestre
				$("#"+compPeriodo1+"_sel_semestre").unbind();
				$("#"+compPeriodo1+"_sel_semestre").change(function(evt) {
					if(period == 4){
						changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					}
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,4,"");
				});
				
				$("#"+compPeriodo2+"_sel_semestre").unbind();
				$("#"+compPeriodo2+"_sel_semestre").change(function(evt) {
					if(period == 4){
						changedatesselectedssemestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					}
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,4,"");
				});
				
				//Retira eventos y actualiza datos en trimestre
				$("#"+compPeriodo1+"_sel_trimestre").unbind();
				$("#"+compPeriodo1+"_sel_trimestre").change(function(evt) {
					if(period == 3){
						changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					}			
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,3,"");
				});
				
				$("#"+compPeriodo2+"_sel_trimestre").unbind();
				$("#"+compPeriodo2+"_sel_trimestre").change(function(evt) {
					if(period == 3){
						changedatesselectedstrimestralestimes(compPeriodo1,compPeriodo2,data,idDivSlider);
					}
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,3,"");
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
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,period,"");
				});
				
				$("#"+compPeriodo2+"_sel_mes").unbind();
				$("#"+compPeriodo2+"_sel_mes").change(function(evt) {
					switch (period) {
						case 1: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
						case 2: 	changedatesselectedstimes(compPeriodo1,compPeriodo2,data,idDivSlider);
							break;
					}
					updatePlotFromFilters(targetPlot,targetPlot2,compPeriodo1,compPeriodo2,data,idDivSlider,period,"");
				});	
	} catch (err) {
		console.log(err);
		$("[id*='"+errormessage+"'").empty();
		$("[id*='"+errormessage+"'").append(brmensaje(mensageError, "error"));
	}
};

/**
 * Funcion que actualiza los gráficos segun el slider
 * @param slider
 * @param compdiario1
 * @param compdiario2
 * @param data
 * @param tipo
 * @param targetPlot
 * @param targetPlot2
 * @returns
 */
function updFilterBySliderReplot(slider,compdiario1,compdiario2,data,tipo,targetPlot,targetPlot2, fslider) {
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
	
	updatePlotFromFilters(targetPlot,targetPlot2,compdiario1,compdiario2,data,slider,tipo, fslider);
}

/**
 * Funcion que actualiza las gráficas
 * @param plot1 Gráfica principal
 * @param plot2 Gráfica secundaria
 * @param compdiario1 filtro de fecha 1
 * @param compdiario2 filtro de fecha 2
 * @param data Data con la serie
 * @param slider slider de control
 * @param tipo de reporte
 * @returns
 */
function updatePlotFromFilters(plot1,plot2,compdiario1,compdiario2,data,slider,tipo, fslider) {
	var anio1,mes1,dia1,trimestre1,semestre1; 
	var anio2,mes2,dia2,trimestre2,semestre2;
	
	//Primer y ultimo valor de año
	var selAnio1 = document.getElementById(compdiario1+"_sel_anio");
	var secValue = selAnio1.options[1].value;
	var lastValue = selAnio1.options[selAnio1.options.length - 1].value;
	
	/*filtro 1*/
	if ($("#"+compdiario1+"_sel_anio").length) {
		anio1 	= $("#"+compdiario1+"_sel_anio").val();	
	}else{
		anio1 = secValue;
	}
	if ($("#"+compdiario1+"_sel_mes").length) {
		mes1 	= ("0"+$("#"+compdiario1+"_sel_mes").val()).slice(-2);
		mes1p	= ("0"+$("#"+compdiario1+"_sel_mes").val()).slice(-2);
		mes1 = mes1-1;
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
	
	/*filtro 2*/
	if ($("#"+compdiario2+"_sel_anio").length) {
		anio2 	= ("0"+$("#"+compdiario2+"_sel_anio").val()).slice(-4);	
	} else {
		anio2 = lastValue;
	}
	if ($("#"+compdiario2+"_sel_mes").length) {
		mes2 	= ("0"+$("#"+compdiario2+"_sel_mes").val()).slice(-2);
		mes2p 	= ("0"+$("#"+compdiario2+"_sel_mes").val()).slice(-2);
		mes2 = mes2-1;
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
	
	if(tipo == 2){
		var fec_p1 = anio1 + "-" + "" + mes1p + "-" +  dia1;
		var fec_p2 = anio2 + "-" + "" + mes2p + "-" +  dia2;
	}else{
		var fec_p1 = anio1 + "-" + "" + mts1 + "-" +  dia1;
		var fec_p2 = anio2 + "-" + "" + mts2 + "-" +  dia2;
	}
	
	
	if(fslider == "slider"){
		
	}else{
		$("#"+slider).dateRangeSlider("values", new Date(anio1, mts1, dia1), new Date(anio2, mts2, dia2));
	}
		
	var ticksSeries =	data["Rangos"];
	var ticksSeriesVal =	data["RangosValores"];	

	var ticks = generaSeriesFecha(ticksSeries, fec_p1, fec_p2, plot1, tipo);
	var ticks2 = generaSeriesFecha(ticksSeriesVal, fec_p1, fec_p2, plot2, tipo);
	//fechas que se muestran en el plot
	var ticks = GeneraTicks(ticks, 1, tipo);
	var ticks2 = GeneraTicks(ticks2, 1, tipo);
	if (ticks.length==0 && ticks2.length==0){

	} else {
		plot1.axes.xaxis.ticks = ticks;
		plot1.replot();
		plot2.axes.xaxis.ticks = ticks2;
		plot2.replot();
	}
	
}

/**
 * Funcion que genera las series filtra por fecha
 * @param ArregloObjetosin data entrada
 * @param fec_p1 fecha del filtro 1
 * @param fec_p2 fecha del filtro 2
 * @returns
 */
function generaSeriesFecha(ArregloObjetosin, fec_p1, fec_p2, plot, tipo){

	var serieCantidad2 = [];
	var flag=arr = ArregloObjetosin[0][0].length;
	var size1 = ArregloObjetosin.length;
	var size2 = ArregloObjetosin[0].length;
	var n=0;
	do{
		var serietmp = [];
		var tickstmp = [];
		for(var i = 0; i<size1; i++){ 		 
			var sortedArr = ArregloObjetosin[i][0].sort(comparar);
			var fechaSerie = sortedArr[n]["ejex"];
			if (fec_p1<=fechaSerie && fec_p2>=fechaSerie) {
				serietmp.push(parseFloat(sortedArr[n]["porcentaje"]));
				tickstmp.push(fechaSerie);
			}
		}
		serieCantidad2.push(serietmp);
		n++;
		flag--;
	}while(flag > 0);

 	plot.replot({data:serieCantidad2});
 	return tickstmp;
}

function comparar(a, b) {
	return a - b;
}

/**
 * Funcion que genera las series para el bar stack
 * @param ArregloObjetos
 * @returns
 */
function GeneraSeries(ArregloObjetosin, plotTipe){

	var serieCantidad2 = [];
	var flag=arr = ArregloObjetosin[0][0].length;
	var size1 = ArregloObjetosin.length;
	var size2 = ArregloObjetosin[0].length;
	var n=0;
	do{
		var serietmp = [];
		var rq=0;
		var i=0;
		/*if(plotTipe==1){
			if(size1>12){
				rq = size1-12;
			}else{
				rq=0;
			}
		}else{
			rq=0;
		}*/
		
		for(i = rq; i<size1; i++){ 		 
			var sortedArr = ArregloObjetosin[i][0].sort(comparar);
			serietmp.push(parseFloat(sortedArr[n]["porcentaje"]));
		}
		serieCantidad2.push(serietmp);
		n++;
		flag--;
	}while(flag > 0);
	return serieCantidad2;
}

/**
 * Funcion que devuelve el array de colores dados por el banco
 * @returns
 */
function colors(){
	var colores = ["#0079C1","#009543","#910028","#FAE600","#0097AC","#EAB010","#559DC5","#8E9295","#653B71","#FF9A00","#B49583","#B6B97D","#7A003C","#FFB300","#559DC5","#BC9B6A","#006469"];
	return colores;
}

/**
 * funcion que devuelve las series con los diferentes colores
 * @param colores
 * @param LabelsRangos
 * @returns
 */
function GeneraLabels(LabelsRangos, colores){
	var series=[];
	for(var i=0; i<LabelsRangos.length; i++){
		var Labels = LabelsRangos[i].toString();
		var color = colores[i].toString();
		series.push(
	    {	color: color, 
				lineWidth: 0.5, 
				shadow: true, 
				label:Labels,
				markerOptions: { size:4, style:'filledCircle' },
				rendererOptions: {
					smooth: false,
					   css: {
						   background:color
					   }
				}
			}  
	  );
	}
	series.push({
		yaxis: 'yaxis', 
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
	});
	return series;
}

function GeneraTicks(ticksin, plotType, tipo){
	var ticksRecortada = [];
	var ct = ticksin.length;
	var rq=0;
	if(plotType == 1){
		for(var i=rq; i<ct; i++){
			ticksRecortada.push(formatterLabel(tipo,ticksin[i]));
		}
	}else{
		ticksRecortada = ticksin;
	}
	
	return ticksRecortada;
}

function formatterLabel(tipo,valor) {
	var fecvar =  valor.split("-");
	var anio = fecvar[0];
	var mes = fecvar[1];
	var dia = fecvar[2];
	switch (tipo) {
	case 1: return anio+"-"+mes+"-"+dia;
	case 2: return mes+"-"+anio;
	case 3: 
	    var month = parseInt(mes);
	    var year = parseInt(anio);
	    var df =""+year;
	    if (month<=2) {
	    	df = "I-" + df ;
	    } else if (month<=5) {
	    	df = "II-" + df  ;
	    } else if (month<=8) {
	    	df = "III-" + df  ;
	    } else {
	    	df = "IV-" + df ;
	    }
	    return df;
	case 4:
	    var month = parseInt(mes);
	    var year = parseInt(anio);
	    var df =""+year;
	    if	(month<=5) {
	    	df =  "I-"  + df  ;
	    } else {
	    	df = "II-"  + df ;
	    }
	    return df;
	case 5:
		return anio;
	}
}
//GRÁFICAS
function createplotmaxComposicionCanje(name,data,tipo, gCanValor) {
	var title,ticks,serieValores,serieCantidad,minValor,minCantidad,maxValor,maxCantidad,minX,maxX,label;

	//ticks 			= GeneraTicks(data["Ticks"],1, tipo);
	ticks 			= data["Ticks"];
 	serieCantidad 	= data["Rangos"];
 	serieValores 	= data["RangosValores"];
 	if(gCanValor == 1){
 		var serieRecortada	= GeneraSeries(serieCantidad, 1);
 		title = data["Title"];
 		label = 'Cantidad';
 	}else{
 		var serieRecortada	= GeneraSeries(serieValores, 1);
 		title = '';
 		label = 'Valor';
 	}
 	
 	var ticksRecortada = GeneraTicks(ticks, 1,tipo);
 	minValor		= data["MinValor"];
 	maxValor		= data["MaxValor"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
 	 		
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	
	var colores = colors();
	
	var series = GeneraLabels(data["LabelsRangos"], colores);
	var plot2 = $.jqplot(name, serieRecortada , {
		title:title,
		stackSeries: true,
	    textColor:  "#003E6C",
	    seriesColors: colores,
	    seriesDefaults:{
	    	renderer:$.jqplot.BarRenderer,
	    	showMarker: true,
	    	lineWidth: 0.5,
			showMarker: false,
			fill: false,
			fillAndStroke: true,
			rendererOptions: {
				smooth: false,
				fillToZero: true,
				varyBarColor: true,
				barMargin: 15,
				shadowDepth: 5,
			},
			labels: ['',''],			
			animation: {
				show: false
            },
           // pointLabels: {show: true, formatString: '%d'}
	    },
	    series:series,
    	legend: {
    		renderer: $.jqplot.EnhancedLegendRenderer,
    		show: true,
            showLabels: true,
            showSwatches:true,
            placement: "outside",
            location: "e",
            //fontSize: '11px',
            pad:5,
            rowSpacing: "5px",
            rendererOptions: {
              seriesToggle: "true",
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
	 		left: "0",
	     },
	    cursor:{
	    	show: true,
	    	zoom:false,
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
    		yvalues: 3,
	    	tooltipAxes : 'xy',
    		useAxesFormatters : true,
      		formatString: '<table class="jqplot-highlighter"> \
                <tr><td style="display:none;">%s</td><td>%s</td></tr> \
	    		</table>'
	    },
	    axesDefaults: { 
        	showTickMarks:true,
        	useSeriesColor:false, 
        	rendererOptions: {
        		alignTicks: false,
        	} 
	    },
	    axes: {
	    	xaxis: {
	    	  	numberTicks: getTicks(minX,maxX,tipo),
          		min:minX,
	          	max:maxX,
	          	label:'Fecha',
	          	showLabel: false,
	          	pad: 1,
	          	tickInterval: getTickInterval(tipo),
            	renderer:$.jqplot.CategoryAxisRenderer,
	          	ticks:  ticksRecortada,
	          	rendererOptions:{ 
					tickRenderer:$.jqplot.CanvasAxisTickRenderer ,
				}, 
                tickOptions: {
                	showTicks: true, 
                	angle: -90,
                	showLabel:true,
                	showMark:true,
	            	showGridline:true,
	            	fontFamily:'"Roboto", sans-serif',
	            	fontSize: '8pt',
	            	show:true,
	            	size:4,
	            	markSize:4,
	            	formatString:  formatter(tipo),
                }
	    	},
	    	yaxis: {
	    		min:minValor,
            	max:100,
            	label: label,
            	fontSize: '10px',
            	showLabel:true,
            	textColor: "#006fb9",
            	show:true,
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer
				},
		        padMin: 0,
                tickOptions: {
                	labelPosition: 'middle', 
                    angle:-45,
                	showTicks: true, 
                	angle: 0,
                	showLabel:true,
                	showMark:true,
	            	showGridline:false,
	            	fontSize: "8pt",
	            	size:4,
	            	markSize:6,
	            	show:true,
	            	pad: 1,
	            	fontFamily:'"Roboto", sans-serif',
	        		textColor: "#006fb9",
	        		labelPosition: 'top',
	        		formatString: "%'.2f %",
                }
		      }
	    }	    
	  });
	  
	return plot2;
}

//Gráfica de control
function createplotminComposicionCanje(name,data,tipo) {
	var title,ticks2,serieValores,serieCantidad2,minValor,minCantidad,maxValor,maxCantidad,minX,maxX;
	title 			= data["Title"];
	//ticks2 			= GeneraTicks(data["Ticks"],1,tipo);
	ticks2 			= data["Ticks"];
 	serieCantidad2 	= data["Rangos"];
 	var ticksRecortada2 = GeneraTicks(ticks2, 2,tipo);
 	var serieRecortada2	= GeneraSeries(serieCantidad2, 2);
 	LabelsRangos 	= data["LabelsRangos"];
 	minValor		= data["MinValor"];
 	maxValor		= data["MaxValor"];
 	minX			= data["MinX"];
 	maxX			= data["MaxX"];
 	 		
 	$.jqplot.sprintf.thousandsSeparator = '.';
	$.jqplot.sprintf.decimalMark = ',';
	var fecha1 = minX.split("-");
	minX = new Date(fecha1[0],(parseInt(fecha1[1])-1),fecha1[2])
	var fecha2 = maxX.split("-");
	maxX = new Date(fecha2[0],(parseInt(fecha2[1])-1),fecha2[2]);
	
	var colores = colors();
	var series = GeneraLabels(data["LabelsRangos"], colores);

	var plot2 = $.jqplot(name, serieRecortada2, {
	    stackSeries: true,
	    textColor:  "#003E6C",
		seriesColors:colores,
	    captureRightClick: true,
	    seriesDefaults:{
	      renderer:$.jqplot.BarRenderer,
	      rendererOptions: {
	          barMargin: 25  
	      },
	      pointLabels: {show: false}
	    },
        cursor:{
	    	show: true,
	    	zoom:false,
	    	constrainZoomTo: 'none',
	    	showTooltip:false,
	    	dblClickReset: false
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
    	axes: {
	      xaxis: {
	    	  numberTicks: getTicks(minX,maxX,tipo),
	    	  min:minX,
	    	  max:maxX,
	    	  label:'Fecha',
	    	  showLabel: false,
	    	  pad: 1,
	    	  ticks: ticksRecortada2,
	    	  renderer:$.jqplot.CategoryAxisRenderer,
	    	  tickInterval: getTickInterval(tipo),
				rendererOptions:{ 
				  tickRenderer:$.jqplot.CanvasAxisTickRenderer
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
	      yaxis: {
	        padMin: 0,
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
        		formatString: "%'.2d",
            }
	      }
	    },
	    legend: {
	      show: false
	    }      
	  });
	  
	return plot2;
	
}