package com.ibm.usingwrapper;
import com.ibm.odmjar.ObjetoComposto;
import com.ibm.odmwrapper.FactoryODM;
import com.ibm.odmwrapper.ODMWrapper;

public class UsingWrapperJava {

	/*
	 * Sample using JAR to run ODM Rule
	 * Using ODMWrapper, a utility set os Classes that hide some of complexity of ODM usage with Java
	 */
	public static void main(String[] args) {
		// Use factory to return Wrapper - Factory is a singleton
		ODMWrapper wrapper = FactoryODM.getWrapper("Java", "/testexom/operacao");
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
		System.out.println("Rules fired " + wrapper.getNumberFiredRules());
		System.out.println(composto.getCampoSaida());
	}

}
