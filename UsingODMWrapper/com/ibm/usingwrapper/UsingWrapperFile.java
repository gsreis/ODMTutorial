package com.ibm.usingwrapper;

import com.ibm.odmjar.ObjetoComposto;
import com.ibm.odmwrapper.FactoryODM;
import com.ibm.odmwrapper.ODMWrapper;

public class UsingWrapperFile {
	public static void main(String[] args) {
		// Use factory to return Wrapper - Factory is a singleton
		ODMWrapper wrapper = FactoryODM.getWrapper("file", "/regratestes/operacao"); 
		wrapper.setDirectory("/Users/glaucoreis/Desktop/diretorio/");
		wrapper.initialize();
		ObjetoComposto composto = new ObjetoComposto();
		composto.setCampoNumerico(10);
		composto.setCampoString("umastring");
		wrapper.putInputParameter("composto", composto);
		try {
			 wrapper.execute();
		} catch (Exception e) {
			wrapper.getError().printStackTrace();
		}
		// If ok, you can get the number of rules fired
		System.out.println("Rules fired " + wrapper.getNumberFiredRules());
		if (wrapper.getNumberFiredRules() == 0 )
			System.out.println(wrapper.getError().getMessage());
		System.out.println(composto.getCampoSaida());
	}

}
