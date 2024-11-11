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

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.threadlocal.Uuid;
import com.intentlabs.common.util.Utility;

/**
 * This is every request filter. Any request coming from any Device will pass
 * through this filter. It checked cross domain values and allow access based on
 * it.
 * 
 * @author nirav
 * @since 30/10/2018
 */
@Component
public class EveryRequestFilter implements Filter {

	private static final String EVERY_REQUEST_FILTER = "EveryRequestFilter";

	private ApplicationContext applicationContext;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setApplicationContext(
				WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()));
		ImageIO.scanForPlugins();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String originHeader = httpServletRequest.getHeader("Origin");
		AllowDomainConfiguration allowDomainConfiguration = (AllowDomainConfiguration) applicationContext
				.getBean("allowDomainConfiguration");

		if (!StringUtils.isBlank(originHeader)) {
			if (!allowDomainConfiguration.isAllowDomain(originHeader)) {
				return;
			}
			httpServletResponse.addHeader("Access-Control-Allow-Origin", originHeader);
			httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
			httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			httpServletResponse.addHeader("Access-Control-Allow-Headers",
					"Content-Type, cache-control,x-requested-with,Authorization");
			if (httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.name())) {
				httpServletResponse.addHeader("Access-Control-Max-Age", "84600");
				return;
			}
		}
		Uuid.setUuid(Utility.generateUuid());
		filterChain.doFilter(httpServletRequest, httpServletResponse);
		Uuid.removeUuid();
	}

	@Override
	public void destroy() {
		LoggerService.info(EVERY_REQUEST_FILTER, "Destory", "Tomcat is been stopped");
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}