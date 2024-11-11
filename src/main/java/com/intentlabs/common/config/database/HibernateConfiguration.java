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

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * This is a hibernate configuration.
 * 
 * @author Nirav.Shah
 * @since 02/05/2018
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { //
        DataSourceAutoConfiguration.class, //
        DataSourceTransactionManagerAutoConfiguration.class})
public class HibernateConfiguration {

	@Value("${hibernate.dialect}")
	private String dialect;

	@Value("${hibernate.show_sql}")
	private String showSql;

	@Value("${hibernate.jdbc.batch_size}")
	private int jdbcBatchSize;

	@Value("${hibernate.cache.provider_class}")
	private String cacheProviderClass;

	@Value("${hibernate.cache.use_second_level_cache}")
	private boolean useSecondLevelCache;

	@Value("${hibernate.format_sql}")
	private boolean formatSql;

	@Value("${hibernate.use_sql_comments}")
	private boolean useSqlComments;

	@Value("${entitymanager.packagesToScan}")
	private String packageToScan;

	@Value("${entitymanager.hbmList}")
	private String[] hbmFiles;

	@Value("${hibernate.jdbc.lob.non_contextual_creation}")
	private boolean contextualCreation;

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setPackagesToScan(packageToScan);
		
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", dialect);
		hibernateProperties.put("hibernate.show_sql", showSql);
		hibernateProperties.put("hibernate.jdbc.batch_size", jdbcBatchSize);
		hibernateProperties.put("hibernate.cache.provider_class", cacheProviderClass);
		hibernateProperties.put("hibernate.cache.use_second_level_cache", useSecondLevelCache);
		hibernateProperties.put("hibernate.format_sql", formatSql);
		hibernateProperties.put("hibernate.use_sql_comments", useSqlComments);
		hibernateProperties.put("hibernate.jdbc.lob.non_contextual_creation", contextualCreation);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		
		sessionFactoryBean.setMappingResources(hbmFiles);
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
}