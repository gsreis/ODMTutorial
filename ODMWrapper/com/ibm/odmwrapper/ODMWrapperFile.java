package com.ibm.odmwrapper;

import java.io.File;

import ilog.rules.res.model.IlrPath;
import ilog.rules.res.session.IlrJ2SESessionFactory;
import ilog.rules.res.session.config.IlrFilePersistenceConfig;
import ilog.rules.res.session.config.IlrPersistenceConfig;
import ilog.rules.res.session.config.IlrPersistenceType;
import ilog.rules.res.session.config.IlrSessionFactoryConfig;

/**
 * 
 * @author glaucoreis
 *
 */
public class ODMWrapperFile extends ODMWrapper {
	
	private String directory;

	public ODMWrapperFile( String rulePath) {
		super(rulePath);
	}
	
	@Override
	public void initialize() {
		try { 	
			IlrSessionFactoryConfig config = IlrJ2SESessionFactory.createDefaultConfig();		
			IlrPersistenceConfig persistenceConfig = config.getXUConfig().getPersistenceConfig();
			IlrPersistenceType persistenceType = IlrPersistenceType.FILE;
			persistenceConfig.setPersistenceType(persistenceType);
			IlrFilePersistenceConfig filePersistenceConfig = config.getXUConfig().
					getPersistenceConfig().getFilePersistenceConfig();
						
			filePersistenceConfig.setDirectory(new File(directory));
			
			IlrFilePersistenceConfig xomPersistenceConfig = config.getXUConfig().
					getManagedXOMPersistenceConfig().getFilePersistenceConfig();
			xomPersistenceConfig.setDirectory(new File(directory));
			config.getXUConfig().getManagedXOMPersistenceConfig().setPersistenceType( IlrPersistenceType.FILE);	

			factory =  new IlrJ2SESessionFactory(config);
			sessionRequest = factory.createRequest();	
			sessionRequest.setRulesetPath(IlrPath.parsePath(this.rulePath));
			
		}catch (Exception e) {
			error = e;
			withError = true;
		}
	}

	@Override
	public void setJDBCDriverClassName(String driver) {  }

	@Override
	public void setJDBCURL(String url) {  }

	@Override
	public void setJDBCUser(String user) { }

	@Override
	public void setPassword(String pass) {  }
	
	@Override
	public void setDirectory(String directory) {this.directory = directory;}

}
