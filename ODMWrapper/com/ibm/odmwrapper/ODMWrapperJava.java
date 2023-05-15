package com.ibm.odmwrapper;

import ilog.rules.res.model.IlrPath;
import ilog.rules.res.session.IlrJ2SESessionFactory;
import ilog.rules.res.session.config.IlrPersistenceType;
import ilog.rules.res.session.config.IlrSessionFactoryConfig;
import ilog.rules.res.session.config.IlrXUConfig;

/**
 * 
 * @author glaucoreis
 *
 */
public class ODMWrapperJava extends ODMWrapper {
	
	public ODMWrapperJava( String rulePath) {
		super(rulePath);
	}

	@Override
	public void initialize() {
		try { 
			IlrSessionFactoryConfig factoryConfig = IlrJ2SESessionFactory.createDefaultConfig();
			IlrXUConfig xuConfig = factoryConfig.getXUConfig();
			xuConfig.setLogAutoFlushEnabled(false);
			xuConfig.getPersistenceConfig().setPersistenceType(IlrPersistenceType.MEMORY);
			xuConfig.getManagedXOMPersistenceConfig().setPersistenceType(IlrPersistenceType.MEMORY);
			factory =  new IlrJ2SESessionFactory(factoryConfig);	
			sessionRequest = factory.createRequest();	
			sessionRequest.setRulesetPath(IlrPath.parsePath(this.rulePath));
		}catch (Exception e) {
			error = e;
			withError = true;
		}

	}

	@Override
	public void setJDBCDriverClassName(String driver) {}

	@Override
	public void setJDBCURL(String url) {}

	@Override
	public void setJDBCUser(String user) {}

	@Override
	public void setPassword(String pass) {}

	@Override
	public void setDirectory(String directory) {}

}
