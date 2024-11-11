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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

/**
 * This is public request filter which does not validate any thing. Just pass it
 * to controller.
 * 
 * @version 1.0
 * @since 30/10/2018
 * @author nirav
 */
@Component
public class MachineRequestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

//	
//
//	private ApplicationContext applicationContext;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		this.setApplicationContext(
//				WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()));
//	}
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//			throws IOException, ServletException {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//
//		MachineService machineService = (MachineService) applicationContext.getBean("machineService");
//
//		if (httpServletRequest.getRequestURI().endsWith("get-access-token")) {
//			filterChain.doFilter(httpServletRequest, httpServletResponse);
//			return;
//		}
//		Pair<MachineModel, String> pairs = getDeviceCookieAndValidateJWTToken(httpServletRequest, httpServletResponse,
//				machineService);
//		if (pairs == null) {
//			return;
//		}
//		MachineModel machineModel = pairs.getKey();
//
//		MachineAuditor.setMachineAuditor(machineModel);
//		filterChain.doFilter(httpServletRequest, httpServletResponse);
//
//		MachineAuditor.removeMachineAuditor();
//		Uuid.removeUuid();
//	}
//
//	/**
//	 * This method is used get jwt token validate user and device cookie related
//	 * details.
//	 * 
//	 * @param httpServletRequest
//	 * @param httpServletResponse
//	 * @param userService
//	 * @return
//	 * @throws JsonGenerationException
//	 * @throws JsonMappingException
//	 * @throws IOException
//	 */
//	private Pair<MachineModel, String> getDeviceCookieAndValidateJWTToken(HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse, MachineService machineService)
//			throws JsonGenerationException, JsonMappingException, IOException {
//		String jwtAccesssTokenHeader = httpServletRequest.getHeader("Authorization");
//		Claims claims = null;
//		if (!StringUtils.isBlank(jwtAccesssTokenHeader)) {
//			String accessToken[] = jwtAccesssTokenHeader.split(" ");
//			try {
//				claims = JwtUtil.extractAllClaims(accessToken[1]);
//			} catch (EndlosAPIException e) {
//				LoggerService.exception(e);
//			}
//		}
//		String machineId = null;
//		if (StringUtils.isBlank(jwtAccesssTokenHeader)) {
//			CommonResponse commonResponse = CommonResponse.create(ResponseCode.AUTHENTICATION_REQUIRED.getCode(),
//					ResponseCode.AUTHENTICATION_REQUIRED.getMessage());
//			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//			return null;
//		}
//		String accessToken[] = jwtAccesssTokenHeader.split(" ");
//		if (accessToken == null || (accessToken != null && accessToken.length != 2)) {
//			CommonResponse commonResponse = CommonResponse.create(ResponseCode.AUTHENTICATION_REQUIRED.getCode(),
//					ResponseCode.AUTHENTICATION_REQUIRED.getMessage());
//			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//			return null;
//		}
//		String jwtAccessToken = accessToken[1];
//		Pair<Boolean, Boolean> jwtPair = JwtUtil.isValidJWTToken(jwtAccessToken);
//		if (jwtPair == null) {
//			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
//					ResponseCode.INVALID_REQUEST.getMessage());
//			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//			return null;
//		}
//		if (jwtPair.getKey()) {
//			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
//					ResponseCode.INVALID_JSON_TOKEN.getMessage());
//			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//			return null;
//		}
//		if (jwtPair.getValue()) {
//			CommonResponse commonResponse = CommonResponse.create(ResponseCode.EXPIRED_JSON_TOKEN.getCode(),
//					ResponseCode.EXPIRED_JSON_TOKEN.getMessage());
//			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//			return null;
//		}
//		try {
//			claims = JwtUtil.extractAllClaims(jwtAccessToken);
//		} catch (EndlosAPIException e) {
//			LoggerService.exception(e);
//		}
//		machineId = JwtUtil.extractData(jwtAccessToken, claims);
//		if (StringUtils.isBlank(machineId)) {
//			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
//					ResponseCode.INVALID_JSON_TOKEN.getMessage());
//			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//			return null;
//		}
//		MachineModel machineModel = machineService.getByMachineId(machineId);
//		if (machineModel == null) {
//			CommonResponse commonResponse = CommonResponse.create(ResponseCode.AUTHENTICATION_REQUIRED.getCode(),
//					ResponseCode.AUTHENTICATION_REQUIRED.getMessage());
//			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//			return null;
//		}
//		return new Pair<MachineModel, String>(machineModel, null);
//	}
//
//	/**
//	 * Send an error response in json from the filter.
//	 * 
//	 * @param httpServletRequest
//	 * @param httpServletResponse
//	 * @param commonResponse
//	 * @throws JsonGenerationException
//	 * @throws JsonMappingException
//	 * @throws IOException
//	 */
//	private void sendResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
//			CommonResponse commonResponse) throws JsonGenerationException, JsonMappingException, IOException {
//		ObjectMapper json = new ObjectMapper();
//		httpServletResponse.setContentType("application/json");
//		json.writeValue(httpServletResponse.getOutputStream(), commonResponse);
//	}
//
//	public void setApplicationContext(ApplicationContext applicationContext) {
//		this.applicationContext = applicationContext;
//	}
//
//	@Override
//	public void destroy() {
//	}
}