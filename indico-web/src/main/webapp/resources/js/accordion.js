
$(function() {
	setTimeout(() => {
	  $(".ui-icon-triangle-1-e").each(function(idx){
		$(this).attr("title","Desplegar");
	  });
	  $(".ui-icon-triangle-1-s").each(function(idx){
		$(this).attr("title","Contraer");
	  });
	}, 800);
	$(".tabtittles2").unbind();
	$(".tabtittles2").on("click",function(){
	  setTimeout(() => {
		$(".tabtittles2").each(function(){
		  let expanded = $(this).hasClass('ui-state-active');
			if(expanded) {
			  const span = $( this ).find( "span" );
			  span.removeAttr("title");
			  span.attr("title","Contraer");
			} else {
			  const span = $( this ).find( "span" );
			  span.removeAttr();
			  span.attr("title","Desplegar");
			}
		});

		// Eventos de tabs internas
		$(".tabtittles").on("click", function(){

		  let expanded = $(this).hasClass('ui-state-hover');
		  setTimeout(() => {
			if(expanded) {
			  $( this ).find( "span" ).removeAttr();
			  $( this ).find( "span" ).attr("title","Contraer Gráfica");
			} else {
			  $( this ).find( "span" ).removeAttr();
			  $( this ).find( "span" ).attr("title","Desplegar Gráfica");
			}
		  },800);

		});
	}, 800);

	});
  });