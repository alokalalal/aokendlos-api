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
package com.intentlabs.common.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.intentlabs.common.logger.LoggerService;

@SpringBootApplication(scanBasePackages = { "com.intentlabs" }, exclude = { HibernateJpaAutoConfiguration.class })
@EnableAutoConfiguration(exclude = { //
		DataSourceAutoConfiguration.class, //
		DataSourceTransactionManagerAutoConfiguration.class, //
		HibernateJpaAutoConfiguration.class })

/**
 * This is a main class from where spring web module start.
 * 
 * @author Nirav.Shah
 * @since 19/04/2018
 */
public class EndlosApiApplication extends SpringBootServletInitializer {
	/**
	 * This is a system variable which define the environment value.
	 */
	public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(EndlosApiApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EndlosApiApplication.class, args);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {

		String activeProfile = System.getProperty(SPRING_PROFILES_ACTIVE);
		System.out.println("activeProfile" + activeProfile);
		if (activeProfile == null) {
			activeProfile = "dev";
		}
		LoggerService.info("current", "activeProfile", activeProfile);
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("application.properties"),
				new ClassPathResource("application-" + activeProfile + ".properties") };
		propertySourcesPlaceholderConfigurer.setLocations(resources);
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		return propertySourcesPlaceholderConfigurer;
	}
}
