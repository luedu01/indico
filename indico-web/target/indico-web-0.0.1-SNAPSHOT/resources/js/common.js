
jQuery(document).ready(function($) {
	  var alterClass = function() {
	    var ww = document.body.clientWidth;
	    if (ww >= 992) {
	      $('.container-fluid').removeClass('mobilecontainer');
	      $('.firstCol').removeClass('mobilecol');
	      $('body').removeClass('mobilebody');
	      $('.noticias').removeClass('mobilenot');
	      $('#column').css("padding-right","78px");
	      $('#secondcol').css("padding-right","15px");
	      $('#secondcol').css("padding-left","15px");
	      $('#secondcol').css("margin-left","0px");
	      $('#secondcol').css("margin-right","0px");
	      $('.noticias').css("padding-bottom","3.5vw");
	      if(ww>=992 && ww<=1365){
	    	 $('.noticias').css("padding-bottom","3vw");  
	    	 if(ww>=992 && ww<=1190){
	    		 $('.noticias').css("padding-bottom","2.3vw");  
	    	 }
	      }
	      $('.noticias').css("margin-right","-15px");
	      $('.jumbotron').css("padding-right","4.7vw");
	      $('.content').css("font-size","1.3vw");
	      $('.titles').css("font-size","0.8vw");
	      $('.dates').css("font-size","0.75vw");
	      $('#noticias-last').css("padding-bottom","0px");
	      $('#noticias-last').css("margin-right","-15px");
	      $('#noticias-last').css("padding-left","3.3vw");
	    } else if (ww < 992 && ww >= 768) {
	      $('.container-fluid').addClass('mobilecontainer');
	      $('.firstCol').addClass('mobilecol');
	      $('body').addClass('mobilebody');
	      $('.noticias').addClass('mobilenot');
	      $('.mobileCuadro').removeClass('litCuadro');
	      $('.imagecarouselMob').removeClass('imagecarouselLit');
	      $('.linebelowMob').removeClass('linebelowLit');
	      $('.textbelowMob').removeClass('textbelowLit');
	      $('.mobileCarousel').removeClass('litCarousel');
	      $('#column').css("padding-right","0px");
	      $('#secondcol').css("padding-right","0px");
	      $('#secondcol').css("padding-left","0px");
	      $('#secondcol').css("margin-left","-15px");
	      $('#secondcol').css("margin-right","-39px");
	      $('.noticias').css("padding-bottom","32px");
	      $('.noticias').css("margin-right","-40px");
	      $('.jumbotron').css("padding-right","72px");
	      $('.content').css("font-size","20px");
	      $('.titles').css("font-size","13px");
	      $('.dates').css("font-size","12px");
	      $('#noticias-last').css("padding-bottom","32px");
	      $('#noticias-last').css("margin-right","-40px");
	      $('#noticias-last').css("padding-left","50px");
	      $('.mobileCarousel .carousel-control.left').css("margin-left","100px");
	      $('.mobileCarousel .carousel-control.right').css("margin-right","100px");
	    } else if (ww < 768){
	      $('.container-fluid').addClass('mobilecontainer');
	      $('.firstCol').addClass('mobilecol');
		  $('body').addClass('mobilebody');
		  $('.noticias').addClass('mobilenot');
		  $('.mobileCuadro').addClass('litCuadro');
		  $('.imagecarouselMob').addClass('imagecarouselLit');
		  $('.linebelowMob').addClass('linebelowLit');
		  $('.imagecarouselMob').addClass('imagecarouselLit');
		  $('.mobileCarousel').addClass('litCarousel');
	      $('#column').css("padding-right","0px");
	      $('#secondcol').css("padding-right","0px");
	      $('#secondcol').css("padding-left","0px");
	      $('#secondcol').css("margin-left","-15px");
	      $('#secondcol').css("margin-right","-39px");
	      $('.noticias').css("padding-bottom","32px");
	      $('.noticias').css("margin-right","-15px");
	      $('.jumbotron').css("padding-right","72px");
	      $('.content').css("font-size","20px");
	      $('.titles').css("font-size","13px");
	      $('.dates').css("font-size","12px");
	      $('#noticias-last').css("padding-bottom","32px");
	      $('#noticias-last').css("margin-right","-15px");
	      $('#noticias-last').css("padding-left","50px");
	      $('.mobileCarousel .carousel-control.left').css("margin-left","-6px");
	      $('.mobileCarousel .carousel-control.right').css("margin-right","-12px");
	    }
	  };
	  $(window).resize(function(){
	    alterClass();
	  });
	  //Fire it when the page first loads:
	  alterClass();
});


