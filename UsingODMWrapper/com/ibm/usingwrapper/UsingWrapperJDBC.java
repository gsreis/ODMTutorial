package com.ibm.usingwrapper;
import com.ibm.odmjar.ObjetoComposto;
import com.ibm.odmwrapper.FactoryODM;
import com.ibm.odmwrapper.ODMWrapper;

public class UsingWrapperJDBC {
	public static void main(String[] args) {
		// Use factory to return Wrapper - Factory is a singleton
		ODMWrapper wrapper = FactoryODM.getWrapper("JDBC", "/testexom/operacao");
		// set parameters for Database Access
		wrapper.setJDBCDriverClassName("org.h2.Driver");
		wrapper.setJDBCURL("jdbc:h2://Users//glaucoreis//Desktop//JAR ODM//dbdata//resdb;" +
		"MODE=HSQLDB;LOCK_TIMEOUT=40000;auto_server=true");
		wrapper.setJDBCUser("res");
		wrapper.setPassword("res");

		wrapper.initialize();
		// create your input and set values (came from XOM - should be in classpath)
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
		System.out.println(composto.getCampoSaida());
	}
	

}
