$(function() {
	
    var selectsFocus = ["vcanje_tipoplaza","vcanje_selFiltroPorValor","vcanje_frmExportar0002\\:vcanje_selectdescargar","vcanje_frmExportar0002\\:j_idt"];
    for (const val of selectsFocus) {
      $("input[id*=" + val + "]").on("focus", function(){
        $("label[id*=" + val + "]").parent().css("outline","solid");
      });

      $("input[id*=" + val + "]").on("blur", function(){
        $("label[id*=" + val + "]").parent().css("outline","none");
      });
    }

    var selectsFocus = ["restaurar1"];
    for (const val of selectsFocus) {
      $("[id*=" + val + "]").on("focus", function(){
        $(this).parent().css("outline","solid");
      });

      $("[id*=" + val + "]").on("blur", function(){
        $(this).parent().css("outline","none");
      });
    }
  });
