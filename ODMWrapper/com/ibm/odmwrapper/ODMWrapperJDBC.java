package com.ibm.odmwrapper;

import ilog.rules.res.model.IlrPath;
import ilog.rules.res.session.IlrJ2SESessionFactory;
import ilog.rules.res.session.config.IlrJDBCPersistenceConfig;
import ilog.rules.res.session.config.IlrPersistenceConfig;
import ilog.rules.res.session.config.IlrPersistenceType;
import ilog.rules.res.session.config.IlrSessionFactoryConfig;

/**
 * 
 * @author glaucoreis
 *
 */
public class ODMWrapperJDBC extends ODMWrapper {
	
	private String driverClass;
	private String url;
	private String user;
	private String pass;

	public ODMWrapperJDBC( String rulePath) {
		super(rulePath);
	}
	
	
	@Override
	public void initialize() {
		try { 
			IlrSessionFactoryConfig config = IlrJ2SESessionFactory.createDefaultConfig();
			IlrPersistenceConfig persistenceConfig = config.getXUConfig().getPersistenceConfig();
			IlrPersistenceType persistenceType = IlrPersistenceType.JDBC;
			persistenceConfig.setPersistenceType(persistenceType);
			IlrJDBCPersistenceConfig jdbcPersistenceConfig = config.getXUConfig().getPersistenceConfig().getJDBCPersistenceConfig();
			jdbcPersistenceConfig.setDriverClassName(driverClass);
			jdbcPersistenceConfig.setURL( url);
			jdbcPersistenceConfig.setUser(user);
			jdbcPersistenceConfig.setPassword(pass);
			factory =  new IlrJ2SESessionFactory(config);
			sessionRequest = factory.createRequest();	
			sessionRequest.setRulesetPath(IlrPath.parsePath(this.rulePath));			
			
			IlrJDBCPersistenceConfig xomPersistenceConfig = config.getXUConfig().getManagedXOMPersistenceConfig().getJDBCPersistenceConfig();
			xomPersistenceConfig.setDriverClassName(driverClass);
			xomPersistenceConfig.setURL( url);
			xomPersistenceConfig.setUser(user);
			xomPersistenceConfig.setPassword(pass);
			config.getXUConfig().getManagedXOMPersistenceConfig().setPersistenceType( IlrPersistenceType.JDBC);			
			
		}catch (Exception e) {
			error = e;
			withError = true;
		}
	}

	@Override
	public void setJDBCDriverClassName(String driver) {  this.driverClass = driver;	}

	@Override
	public void setJDBCURL(String url) { this.url = url; }

	@Override
	public void setJDBCUser(String user) { this.user = user; }

	@Override
	public void setPassword(String pass) { this.pass = pass; }
	
	@Override
	public void setDirectory(String directory) {}

}
