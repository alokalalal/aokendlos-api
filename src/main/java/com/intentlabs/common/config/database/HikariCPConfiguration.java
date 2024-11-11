/*******************************************************************************
 * Copyright -2018 @intentlabs
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.intentlabs.common.config.database;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * This is a hikari connection pool configuration class where all database
 * related configuration will be done.
 * 
 * @author Nirav.Shah
 * @since 23/04/2018
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { //
		DataSourceAutoConfiguration.class, //
		DataSourceTransactionManagerAutoConfiguration.class })
@PropertySource(value = { "file:${catalina.base}/conf/endlos-api/db.properties" })
public class HikariCPConfiguration {

	@Value("${datasource.dataSourceClassName}")
	private String dataSourceClassName;

	@Value("${datasource.user}")
	private String user;

	@Value("${datasource.password}")
	private String password;

	@Value("${datasource.databaseName}")
	private String databaseName;

	@Value("${datasource.portNumber}")
	private String portNumber;

	@Value("${datasource.serverName}")
	private String serverName;

	@Value("${datasource.hikaricp.autoCommit}")
	private String autoCommit;

	@Value("${datasource.hikaricp.connectionTimeout}")
	private int connectionTimeout;

	@Value("${datasource.hikaricp.idleTimeout}")
	private int idleTimeout;

	@Value("${datasource.hikaricp.maxLifetime}")
	private int maxLifetime;

	@Value("${datasource.hikaricp.maximumPoolSize}")
	private int maximumPoolSize;

	@Value("${datasource.hikaricp.minimumIdle}")
	private int minimumIdle;

	@Bean(destroyMethod = "close")
	public HikariDataSource primaryDataSource() {
		Properties dataSourceProperty = new Properties();
		dataSourceProperty.put("user", user);
		dataSourceProperty.put("password", password);
		dataSourceProperty.put("databaseName", databaseName);
		dataSourceProperty.put("portNumber", portNumber);
		dataSourceProperty.put("serverName", serverName);

		Properties connectionPoolProperty = new Properties();
		connectionPoolProperty.put("dataSourceClassName", dataSourceClassName);
		connectionPoolProperty.put("maximumPoolSize", maximumPoolSize);
		connectionPoolProperty.put("connectionTimeout", connectionTimeout);
		connectionPoolProperty.put("idleTimeout", idleTimeout);
		connectionPoolProperty.put("dataSourceProperties", dataSourceProperty);
		connectionPoolProperty.put("autoCommit", autoCommit);
		connectionPoolProperty.put("maxLifetime", maxLifetime);
		connectionPoolProperty.put("minimumIdle", minimumIdle);

		HikariConfig hikariConfig = new HikariConfig(connectionPoolProperty);
		hikariConfig.addDataSourceProperty("reWriteBatchedInserts", true);

		HikariDataSource hikariDataSoruce = new HikariDataSource(hikariConfig);

		return hikariDataSoruce;
	}
}