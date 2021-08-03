
function start() {
	PF('statusDialog').show();
}

function stop() {
	PF('statusDialog').hide();
}

function exportChart(selection) {
	if (selection == "jpg") {
		var image = PF('graficaTransaccionW').exportAsImage();
		var imageSrc = $(image).attr('src');
		var url = imageSrc.replace(/^data:image\/[^;]+/,
				'data:application/octet-stream');
		var link = document.createElement('a');
		link.download = "GraficaSispagos.png";
		link.href = url;
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
	} else if (selection == "pdf") {
		var docDefinition = {
			content : [
					'Grafica sistema de pagos de alto valor',
					{
						image : $(PF('graficaTransaccionW').exportAsImage())
								.attr('src'),
						width : 520
					} ]
		}

		pdfMake.createPdf(docDefinition).open();
		pdfMake.createPdf(docDefinition).download('primefaces-charts.pdf');
	}
}

function chartextender() {
	this.cfg.textColor = "#003E6C";
	this.cfg.seriesColors = [ '#5e2129', '#003E6C' ];

	this.cfg.highlighter = {
		show : true,
		sizeAdjust : 2,
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
	this.cfg.axes.xaxis.tickOptions.angle = "-45";

	this.cfg.axes.yaxis.borderColor = "#5e2129";
	this.cfg.axes.yaxis.textColor = "#5e2129";
	this.cfg.axes.yaxis.borderColor = "#5e2129";
	this.cfg.axes.yaxis.tickOptions.textColor = "#5e2129";

	this.cfg.axes.y2axis.borderColor = "#003E6C";
	this.cfg.axes.y2axis.tickOptions.textColor = "#003E6C";
	this.cfg.axes.y2axis.borderColor = "#003E6C";
	this.cfg.axes.y2axis.tickOptions.textColor = "#003E6C";

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
		placement : 'outsideGrid'
	};
	this.cfg.seriesDefaults = {
		showMarker : true
	}

}
