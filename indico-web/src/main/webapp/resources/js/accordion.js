
$(function() {
	$( document ).ready(function() {
			
			let desplegar="Desplegar";
			let contraer="Contraer";
			
			size = $(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-corner-all.tabtittles2").length;
				if (size==0) {			
					$(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-corner-all.tabtittles2").removeAttr("title");
					$(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-corner-all.tabtittles2").attr("title",desplegar);
					$(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-corner-all.tabtittles2").on("click",function(){
						let expanded = $(this).hasClass('ui-state-active');
						if(expanded) {
							$(this).removeAttr("title");
							$(this).attr("title",desplegar);
						} else {
							$(this).removeAttr("title");
							$(this).attr("title",contraer);
						}
					});
				} else {
					
					elements = $(".ui-accordion-header.ui-helper-reset.ui-state-default.ui-corner-all.tabtittles2");
					$.each( elements, function( i, val ) {
						$(val).find("span").removeAttr("title");
						$(val).find("span").attr("title",desplegar);
						
						$(val).find("span").unbind("click");
						$(val).find("span").on('click', function(){
							setTimeout(() => {
								let expanded = $(this).hasClass('ui-icon-triangle-1-s');
								if (expanded==true) {
									$(val).find("span").removeAttr("title");
									$(val).find("span").attr("title",contraer);
								} else {
									$(val).find("span").removeAttr("title");
									$(val).find("span").attr("title",desplegar);
								}
							},2000);	
						});
					});	
			}
	});				
		
		$( document ).ready(function() {
			let desplegargrafica="Desplegar Gráfica";
			let contraergrafica="Contraer Gráfica";
			let time=0;

			size = $(".ui-accordion-header.ui-helper-reset.ui-state-default.tabtittles").length;
			
			if (size==0) {
				$(".ui-accordion-header.ui-helper-reset.ui-state-default.tabtittles").removeAttr("title");
				$(".ui-accordion-header.ui-helper-reset.ui-state-default.tabtittles").attr("title",desplegargrafica);
	
				$(".ui-accordion-header.ui-helper-reset.ui-state-default.tabtittles").on("click",function() {
					setTimeout(() => {
					     titlea = $(this).attr("title");
						if (titlea === desplegargrafica) {
							$(this).removeAttr("title");
							$(this).attr("title",contraergrafica);
							time=0;
						} else {
							$(this).removeAttr("title");
							$(this).attr("title",desplegargrafica);
							time=0;
						}
				  },time)		
				});
			} else {
				elements = $(".ui-accordion-header.ui-helper-reset.ui-state-default.tabtittles");
				$.each( elements, function( i, val ) {
					titlea = $(val).find("span").attr("title");
					if (titlea === undefined) {
						$(val).find("span").removeAttr("title");
						$(val).find("span").attr("title",desplegargrafica);
					} 
					
					$(val).find("span").unbind("click");
					$(val).find("span").on('click', function() {
						titlea = $(val).find("span").attr("title");						
						if (titlea === desplegargrafica) {
							$(val).find("span").removeAttr("title");
							$(val).find("span").attr("title",contraergrafica);
						} else {
							$(val).find("span").removeAttr("title");
							$(val).find("span").attr("title",desplegargrafica);
						}				
					 });
					 
 				});				
				
		}
	});			
});
  