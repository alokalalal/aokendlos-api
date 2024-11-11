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
package com.intentlabs.common.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * This class set default view resolver.
 * 
 * @author nirav
 * @since 30/10/2018
 *
 */

@Configuration
public class FilterConfig extends WebMvcConfigurationSupport {

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true)
				.useJaf(false).defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Bean
	public FilterRegistrationBean<EveryRequestFilter> everyRequestFilter() {
		FilterRegistrationBean<EveryRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new EveryRequestFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<PrivateRequestFilter> authenticationFilter() {
		FilterRegistrationBean<PrivateRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new PrivateRequestFilter());
		filterRegistrationBean.addUrlPatterns("/private/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<MachineRequestFilter> machineFilter() {
		FilterRegistrationBean<MachineRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new MachineRequestFilter());
		filterRegistrationBean.addUrlPatterns("/machine-req/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<PublicRequestFilter> publicRequestFilter() {
		FilterRegistrationBean<PublicRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new PublicRequestFilter());
		filterRegistrationBean.addUrlPatterns("/public/*");
		return filterRegistrationBean;
	}
}