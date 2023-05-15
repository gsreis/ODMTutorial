package com.ibm.odmwrapper;

/**
 * 
 * @author glaucoreis
 *
 */
public class FactoryODM {
	
	private FactoryODM instance; 
	
	private FactoryODM() { 

	}
	
	public FactoryODM getInstance() { 
		if (instance == null)
			 instance = new  FactoryODM();
		return instance;
	}
	/**
	 * 
	 * @param type - Can be "Java" or "JDBC". Case is ignored
	 * @param rulePath - The Rule Path for Rule
	 * you can get it from Rule Execution server, tab Explorer 
	 * @return
	 */
	public static ODMWrapper getWrapper(String type, String rulePath) { 
		if (type.equalsIgnoreCase("jdbc"))
				return new ODMWrapperJDBC(rulePath);
		else if (type.equalsIgnoreCase("java"))
			    return new ODMWrapperJava(rulePath);
		else  if (type.equalsIgnoreCase("File"))
			return new ODMWrapperFile(rulePath);
		else
			return null;
	}
	

}
