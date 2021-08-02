package com.indico.jee.bb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import static com.indico.jee.util.Constants.*;

@ManagedBean(name = "indicadorService")
@ApplicationScoped
public class IndicadorService implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String[] id;
    
    private static final String[] titles;
    
    private static final String[] subTitles;
    
    private static final String[] urlImagen;
    
    private static final String[] action;
    
    private static final String IMAGE_COMP_CHEQUES_LITERAL = "./resources/images/GrisCompensacionCheques001.svg";
 
    static {
        id = new String[4];
        id[0] = "sistemacuentasdepositocud";
        id[1] = "sistemadepagosdealtovalor";
        id[2] = "sistemadepagosdealtovalo2";
        id[3] = "sistemadepagosdealtovalo2";
 
        titles = new String[4];
        titles[0] = "sistema de Cuentas de ";
        titles[1] = COMPENSACION_DE_LITERAL;
        titles[2] = COMPENSACION_DE_LITERAL;
        titles[3] = COMPENSACION_DE_LITERAL;
        
        subTitles = new String[4];
        subTitles[0] = "depósito – cud";
        subTitles[1] = CHEQUES_LITERAL;
        subTitles[2] = CHEQUES_LITERAL;
        subTitles[3] = CHEQUES_LITERAL;
        
        urlImagen = new String[4];
        urlImagen[0] = "./resources/images/Sistema de Pagos de Alto Valor.svg";
        urlImagen[1] = IMAGE_COMP_CHEQUES_LITERAL;
        urlImagen[2] = IMAGE_COMP_CHEQUES_LITERAL;
        urlImagen[3] = IMAGE_COMP_CHEQUES_LITERAL;
        
        action = new String[4];
        action[0]= "estadistica";
        action[1]= COMPENSACION_LITERAL;
        action[2]= COMPENSACION_LITERAL;
        action[3]= COMPENSACION_LITERAL;
    }
 
    private int fullSize = subTitles.length;
    
    public List<Indicador> createIndicador(int size, int beg) {
        List<Indicador> list = new ArrayList<Indicador>();
        if(fullSize - beg >= size){
        	for(int i = beg ; i < size ; i++) {
            	Indicador indi = new Indicador(id[i], titles[i], subTitles[i], urlImagen[i], action[i]);
                list.add(indi);
            }
        }else{
        	for(int i = beg ; i < fullSize ; i++){
        		Indicador indi = new Indicador(id[i], titles[i], subTitles[i], urlImagen[i], action[i]);
                list.add(indi);
        	}
        	for(int i = 0 ; i <= beg-1 ; i++){
        		Indicador indi = new Indicador(id[i], titles[i], subTitles[i], urlImagen[i], action[i]);
                list.add(indi);
        	}
        }
        return list;
    }
    
    public String getId(int position){
    	return id[position];
    }
    
    public String getTitle(int position){
    	return titles[position];
    }
    
    public String getSubtitle(int position){
    	return subTitles[position];
    }
    
    public String getAction(int position){
    	return action[position];
    }
    
    public String getUrlImagen(int position){
    	return urlImagen[position];
    }

	public int getFullSize() {
		return fullSize;
	}

	public void setFullSize(int fullSize) {
		this.fullSize = fullSize;
	}

}
