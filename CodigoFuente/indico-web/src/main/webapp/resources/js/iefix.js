//<![CDATA[ 

/*
 * this funtion only must to run on ie to fix the flexbox capability
 */
function iefix()
{
	  if(navigator.userAgent.match(/Trident\/7\./)) 
      {
		  var x=document.getElementsByClassName("ui-g");
		  var i;
		  for (i = 0; i < x.length; i++) {
			  if(x[i].style.display=="")
			  {
				  //console.log( x[i])
				  //console.log( x[i].style.display);
				  //console.log("-----")
				  x[i].style.display="block";
			  }
		  }
      }
};

function handleAjax(data) {
    var status = data.status;

    switch(status) {
        case "begin":
        	//console.log("This is invoked right before ajax request is sent.");//
            break;

        case "complete":
        	//console.log(" ajax response is returned.");//  ajax response is returned.
            break;

        case "success":
        	//console.log("processing of ajax response and update of HTML DOM.");//  successful processing of ajax response and update of HTML DOM.
            someFunction();
            break;
    }
}

//]]>
