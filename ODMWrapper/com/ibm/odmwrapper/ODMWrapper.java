package com.ibm.odmwrapper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ilog.rules.res.model.IlrFormatException;
import ilog.rules.res.session.IlrJ2SESessionFactory;
import ilog.rules.res.session.IlrSessionException;
import ilog.rules.res.session.IlrSessionRequest;
import ilog.rules.res.session.IlrSessionResponse;
import ilog.rules.res.session.IlrStatelessSession;


/**
 * 
 * @author glaucoreis
 *
 */
public abstract class ODMWrapper {
	
	private static final String ILOG_RULES_FIRED_RULES_COUNT = "ilog.rules.firedRulesCount";
	
	protected IlrJ2SESessionFactory factory;	
	protected IlrSessionRequest sessionRequest;
	protected IlrSessionResponse sessionResponse;
	protected String rulePath;
	protected boolean executed = false;
	protected boolean withError = false;
	protected Exception error = null;
	
	public ODMWrapper(String rulePath) { 
		this.rulePath = rulePath;
	}
	/**
	 * Return true if execution with error. 
	 * Before execution, if error is true, the initialization fail
	 * In booth cases, @getError() return the exception
	 * @return
	 */
	public Exception getError() { return error; }
	
	/**
	 * Return true if execution with error. 
	 * Before execution, if error is true, the initialization fail
	 * In booth cases, @getError() return the exception
	 * @return
	 */
	public boolean isWithError() { return withError; }

	/**
	 * This is an abstract method that is dependent of implementation
	 * The kind of ODMWrapper is created by @Factory class, and is called at constructor
	 * It creates factory for Java or JDBC
	 * If this process fail, @isWithError() and @getError() returns reason
	 * WARNING - Call initialize just after set all JDBC parameters, if apply
	 */
	public abstract void initialize(); 
	
	/**
	 * @return IlrJ2SESessionFactory created by construtor
	 * This process can fail, and @isWithError() and @getError() returns reason
	 */
	public IlrJ2SESessionFactory getFactory() { return factory; }
	
	/**
	 * @return a Set of keys for Rule input
	 * if not available return an empty set. Never returns null
	 */
	public Set<String> getInputKeys() {
		if (sessionRequest != null)
			return sessionRequest.getInputParameters().keySet();
		else 
			return new HashSet<String>();
	}
	
	/**
	 * @return a Set of keys for Rule output
	 * if not available return an empty set. Never returns null
	 */
	public Set<String> getOutputKeys() {
		if (sessionResponse != null)
			return sessionResponse.getOutputParameters().keySet();
		else 
			return new HashSet<String>();
	}
	
	/** 
	 * If any error occurred during initialization, the insert is ignored
	 * @param key - must match with implementation rule in decision Center. Please check the name
	 * @param value - Any Object can be passed, including Wrapper classes
	 */
	public void putInputParameter(String key, Object value) { 
		if (!isWithError() & sessionRequest != null)
			sessionRequest.setInputParameter(key, value);
	}
	
	/**
	 * Get all input parameters for request
	 * If an error is trap at initialization, return null;
	 * If any error 
	 * @return
	 */
	public Map<String, Object> getInputParameters() { 
		if (!isWithError() & sessionRequest != null)
			return sessionRequest.getInputParameters();
		return null;
	}
	
	/**
	 * Get just one input parameters for request
	 * If an error is trap at initialization, return null;
	 * If any error 
	 * @return
	 */
	public Object getInputParameter(String key) { 
		if (!isWithError() & sessionRequest != null)
			return sessionRequest.getInputParameters().get(key);
		return null;
	}
	
	/**
	 * Get Results after execution
	 * If @execute is not called, returns null for this request
	 * @param key
	 * @return
	 */
	public Map<String, Object> getOutputParameters() {
		if (!isWithError() & sessionResponse != null)
			return sessionResponse.getOutputParameters();
		return null;
	}

	/**
	 * Get one result after execution, given a key
	 * If @execute is not called, returns null for this request
	 * @param key
	 * @return
	 */
	public Object getOutputParameter(String key) {
		if (!isWithError() & sessionResponse != null)
			return sessionResponse.getOutputParameters().get(key);
		return null;
	}
	
	/**
	 * After execution, return the number of rules fired
	 * Zero is returned if error or no rule executed
	 * @return
	 */
	public int getNumberFiredRules() {
		if (!isWithError() & sessionResponse != null) {
			Object result = sessionResponse.getOutputParameters().get(ILOG_RULES_FIRED_RULES_COUNT);
			if (result != null)
			return ((Integer) result).intValue();
		}
		return 0;
	}
	
	/**
	 * execute Rule
	 * Some errors can arrises, and should be catch by caller code
	 * @throws IlrFormatException
	 * @throws IlrSessionException
	 */
	public void execute() throws IlrFormatException, IlrSessionException { 
		if (!withError)
		{
			try { 
				IlrStatelessSession session = factory.createStatelessSession();
				sessionResponse = session.execute(sessionRequest);	
			}
			catch(Exception e) {
				e.printStackTrace(System.out);
				withError = true;
				error = e;
			}
		}
	}
	
	/**
	 * Just make sense if Object is a JDBC Wrapper
	 * @param driver
	 */
	public abstract void setJDBCDriverClassName(String driver);
	/**
	 * Just make sense if Object is a JDBC Wrapper
	 * @param url
	 */
	public abstract void setJDBCURL(String url);
	/**
	 * Just make sense if Object is a JDBC Wrapper
	 * @param user
	 */
	public abstract void setJDBCUser(String user);
	/**
	 * Just make sense if Object is a JDBC Wrapper
	 * @param pass
	 */
	public abstract void setPassword(String pass);
	/**
	 * 
	 */
	public abstract void setDirectory(String directory);
}
