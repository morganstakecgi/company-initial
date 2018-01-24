package io.rscale.training.company;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
@Profile("cloud")

public class DataSourceConfig extends AbstractCloudConfig {
    public DataSourceConfig() {
    }

    @Bean
    public DataSource dataSource() throws Exception {
        DataSource dataSource = cloud().getSingletonServiceConnector(DataSource.class, null);
        if ( !isMySQL(dataSource)) {
            throw new UnsatisfiedDependencyException("javax.sql.DataSource", "javax.sql.DataSource", "javax.sql.DataSource", "MySQL required when running cloud profile.");
        }
        return dataSource;
    }

    private boolean isMySQL(DataSource dataSource) {
    		try {
				return dataSource.getConnection().getMetaData().getDriverName().equalsIgnoreCase("mysql");
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
    }

}