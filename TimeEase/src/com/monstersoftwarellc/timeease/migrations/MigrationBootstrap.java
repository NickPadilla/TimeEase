/**
 * 
 */
package com.monstersoftwarellc.timeease.migrations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.migration.SchemaVersion;


/**
 * Initializes the database migration and or setup. 
 * We use some of FlyWay's internal logic here to allow us to check for the existence of flyway
 * @author navid
 *
 */
public class MigrationBootstrap implements PersistenceUnitPostProcessor {

	private boolean buildTables = false;
	
	private DataSource dataSource;
	
	private String baseDir;
	
	private String version;
	
	public void initialize(){

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        Flyway flyway = new Flyway();
        flyway.setBaseDir(baseDir);
        flyway.setDataSource(dataSource);
        
        // see if the flyway migrations table exists 
        // if not setup and then configure tables.
        if(!tableExists(jdbcTemplate, getCurrentSchema(jdbcTemplate), "schema_version")){
        	flyway.setInitialVersion(new SchemaVersion(version));
        	flyway.init();
        	buildTables = true;
        }else{
        	flyway.migrate();
        }
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean tableExists(JdbcTemplate jdbcTemplate,final String schema, final String table) {
        return (Boolean) jdbcTemplate.execute(new ConnectionCallback() {
            public Boolean doInConnection(Connection connection) throws SQLException, DataAccessException {
            	String temp = schema;
            	if(temp == null)
            		temp = "public";
                ResultSet resultSet = connection.getMetaData().getTables(null, temp.toUpperCase(),
                        table, null);
                return resultSet.next();
            }
        });
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCurrentSchema(JdbcTemplate jdbcTemplate) {
        return (String) jdbcTemplate.execute(new ConnectionCallback() {
            public String doInConnection(Connection connection) throws SQLException, DataAccessException {
                ResultSet resultSet = connection.getMetaData().getSchemas();
                while (resultSet.next()) {
                    if (resultSet.getBoolean("IS_DEFAULT")) {
                        return resultSet.getString("TABLE_SCHEM");
                    }
                }
                return null;
            }
        });
    }
    
	
	/* (non-Javadoc)
	 * @see org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor#postProcessPersistenceUnitInfo(org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo)
	 */
	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		// if build tables set properties to tell eclipseLink to create db tables.
		// When the entity manager starts it will create the model tables.
		if(buildTables){
			pui.addProperty(PersistenceUnitProperties.DDL_GENERATION, "create-tables");
//			pui.addProperty(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");
//			pui.addProperty(PersistenceUnitProperties.DDL_GENERATION_MODE, "both");			
		}
//		pui.addProperty(PersistenceUnitProperties.LOGGING_LEVEL, "FINE");
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the baseDir
	 */
	public String getBaseDir() {
		return baseDir;
	}

	/**
	 * @param baseDir the baseDir to set
	 */
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
