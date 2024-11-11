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
import java.time.Instant;
import java.util.concurrent.TimeUnit;

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
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intentlabs.common.enums.JWTExceptionEnum;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.model.JwtTokenModel;
import com.intentlabs.common.modelenums.CommonStatusEnum;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.threadlocal.Uuid;
import com.intentlabs.common.user.model.RoleModel;
import com.intentlabs.common.user.model.TokenBlackListModel;
import com.intentlabs.common.user.model.UserModel;
import com.intentlabs.common.user.model.UserPasswordModel;
import com.intentlabs.common.user.service.RoleService;
import com.intentlabs.common.user.service.TokenBlackListService;
import com.intentlabs.common.user.service.UserPasswordService;
import com.intentlabs.common.user.service.UserService;
import com.intentlabs.common.user.service.UserSessionService;
import com.intentlabs.common.util.Constant;
import com.intentlabs.common.util.JsonUtil;
import com.intentlabs.common.util.JwtUtil;
import com.intentlabs.endlos.customer.model.CustomerModel;
import com.intentlabs.endlos.customer.service.CustomerService;

import io.jsonwebtoken.Claims;

/**
 * This is private request filter. Private request filter authentication users
 * using it's session and browser's informations. It also prepared role module
 * rights map which will be used by authorization annotation to check
 * authorization of user.
 * 
 * @since 30/10/2018
 * @author nirav
 *
 */
@Component
public class PrivateRequestFilter implements Filter {

	private ApplicationContext applicationContext;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setApplicationContext(
				WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()));
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		UserSessionService userSessionService = (UserSessionService) applicationContext.getBean("userSessionService");
		UserPasswordService userPasswordService = (UserPasswordService) applicationContext
				.getBean("userPasswordService");

		UserService userService = (UserService) applicationContext.getBean("userService");
		TokenBlackListService tokenBlackListService = (TokenBlackListService) applicationContext
				.getBean("tokenBlackListService");
		if (httpServletRequest.getRequestURI().endsWith("get-access-token")) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		UserModel userModel = getDeviceCookieAndValidateJWTToken(httpServletRequest, httpServletResponse, userService,
				tokenBlackListService);

		// String device = pairs.getValue();
		try {
			if (!validateUserDetailsAndRequest(userModel, httpServletRequest, httpServletResponse,
					userSessionService)) {
				return;
			}
		} catch (Exception exception) {
			LoggerService.exception(exception);
			return;
		}
		if (userModel.getJwtTokenModel() != null) {
			if (!isPasswordChangeRequired(httpServletRequest, httpServletResponse, userModel)) {
				return;
			}
		}

		if (!isPasswordExpired(userModel, httpServletRequest, httpServletResponse, userPasswordService)) {
			return;
		}
		validateAndSetCustomerRequestModel(httpServletRequest, httpServletResponse, userModel);

		setRoleModuleRights(userModel);
		Auditor.setAuditor(userModel);
		filterChain.doFilter(httpServletRequest, httpServletResponse);

		Auditor.removeAuditor();
		Uuid.removeUuid();

	}

	/**
	 * this method will used to validate request as per JWT token model.
	 * 
	 * @param userModel
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param userSessionService
	 * @return
	 * @throws RTAAPIException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private boolean validateUserDetailsAndRequest(UserModel userModel, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, UserSessionService userSessionService)
			throws EndlosAPIException, JsonGenerationException, JsonMappingException, IOException {

		JwtTokenModel jwtTokenModel = null;
		Claims claims = JwtUtil.extractAllClaims(userModel.getAccessJWTToken());
		if (claims != null && claims.get(Constant.REQUEST_PAYLOAD) != null) {
			jwtTokenModel = JsonUtil.toObject(claims.get(Constant.REQUEST_PAYLOAD).toString(), JwtTokenModel.class);
		}
		if (httpServletRequest.getRequestURI().endsWith("reset-password")
				|| httpServletRequest.getRequestURI().endsWith("activate-through-otp")
				|| httpServletRequest.getRequestURI().endsWith("resent-activation-otp")) {
			if (claims == null || (claims != null && claims.get(Constant.REQUEST_PAYLOAD) == null)) {
				return false;
			}
		}
		if (jwtTokenModel == null) {
			return false;
		} else {
			if (StringUtils.isEmpty(jwtTokenModel.getUniqueToken())) {
				CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
						ResponseCode.INVALID_JSON_TOKEN.getMessage());
				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
				return false;
			} else {
				if (!jwtTokenModel.getUniqueToken().equals(userModel.getUniqueToken())) {
					CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
							ResponseCode.INVALID_JSON_TOKEN.getMessage());
					sendResponse(httpServletRequest, httpServletResponse, commonResponse);
					return false;
				}
			}
			if (jwtTokenModel.isFirstLoginToken()) {
				if (!httpServletRequest.getRequestURI().endsWith("first-time-password-change")) {
					CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
							ResponseCode.INVALID_REQUEST.getMessage());
					sendResponse(httpServletRequest, httpServletResponse, commonResponse);
					return false;
				}
			}
			if (jwtTokenModel.isResetPasswordToken()) {
				if (!httpServletRequest.getRequestURI().endsWith("reset-password")
						&& !httpServletRequest.getRequestURI().endsWith("logout")) {
					CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
							ResponseCode.INVALID_REQUEST.getMessage());
					sendResponse(httpServletRequest, httpServletResponse, commonResponse);
					return false;
				}
			}

			if (jwtTokenModel.isActivationToken() && !userModel.isActive()
					&& (!httpServletRequest.getRequestURI().endsWith("activate-through-otp")
							&& !httpServletRequest.getRequestURI().endsWith("resent-activation-otp"))) {
				CommonResponse commonResponse = CommonResponse.create(ResponseCode.EMAIL_VERIFICATION.getCode(),
						ResponseCode.EMAIL_VERIFICATION.getMessage());
				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
				return false;
			}
			userModel.setJwtTokenModel(jwtTokenModel);
		}
		return true;
	}

	/**
	 * This method is used to validate password expiration.
	 * 
	 * @param userModel
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param userPasswordService
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private boolean isPasswordExpired(UserModel userModel, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, UserPasswordService userPasswordService)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (CommonStatusEnum.YES.equals(SystemSettingModel.getPasswordExpirationMaxAgeNeeded())
				&& !httpServletRequest.getRequestURI().endsWith("change-password")
				&& !httpServletRequest.getRequestURI().endsWith("logout")) {
			UserPasswordModel userPasswordModel = userPasswordService.getCurrent(userModel.getId());
			if (userPasswordModel != null) {
				Long longDate = (userPasswordModel.getCreate()
						+ TimeUnit.DAYS.toSeconds(SystemSettingModel.getPasswordExpirationMaxAgeDays()));
				int retval = longDate.compareTo(Instant.now().getEpochSecond());
				if (retval < 0) {
					CommonResponse commonResponse = CommonResponse.create(ResponseCode.PASSWORD_EXPIRED.getCode(),
							ResponseCode.PASSWORD_EXPIRED.getMessage());
					sendResponse(httpServletRequest, httpServletResponse, commonResponse);
					return false;
				}
			}
		}
		return true;

	}

	/**
	 * This method is used check temp password session.
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param userModel
	 * @param jwtTokenModel
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private boolean isPasswordChangeRequired(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, UserModel userModel)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (CommonStatusEnum.YES.equals(SystemSettingModel.getDefaultPasswordChangeRequired())) {
			if (userModel.getIsTempPassword()) {
				if (!httpServletRequest.getRequestURI().endsWith("first-time-password-change")
						&& (!(userModel.getJwtTokenModel().isResetPasswordToken()
								&& httpServletRequest.getRequestURI().endsWith("reset-password")))) {
					CommonResponse commonResponse = CommonResponse.create(ResponseCode.TEMP_PASSWORD_SESSION.getCode(),
							ResponseCode.TEMP_PASSWORD_SESSION.getMessage());
					sendResponse(httpServletRequest, httpServletResponse, commonResponse);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method is used get jwt token validate user and device cookie related
	 * details.
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param userService
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private UserModel getDeviceCookieAndValidateJWTToken(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, UserService userService,
			TokenBlackListService tokenBlackListService) throws IOException {
		String jwtAccessTokenHeader = httpServletRequest.getHeader("Authorization");
//		String refreshAccessTokenHeader = httpServletRequest.getHeader("refreshToken");

		String accessToken = validateAccessToken(httpServletRequest, jwtAccessTokenHeader, httpServletResponse);
//		String refreshToken = validateRefreshToken(httpServletRequest, refreshAccessTokenHeader, httpServletResponse);
		if (accessToken == null) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		JWTExceptionEnum jwtExceptionEnum = JwtUtil.isValidJWTToken(accessToken);
		if (jwtExceptionEnum == null) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		if (jwtExceptionEnum.equals(JWTExceptionEnum.SIGNAUTURE_EXCEPTION)) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		if (jwtExceptionEnum.equals(JWTExceptionEnum.EXPIRED_JWT_EXCEPTION)) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
//			JWTExceptionEnum refreshJwtExceptionEnum = JwtUtil.isValidJWTToken(refreshToken);
//			if (refreshJwtExceptionEnum == null) {
//				CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
//						ResponseCode.INVALID_JSON_TOKEN.getMessage());
//				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//				return null;
//			}
//			if (refreshJwtExceptionEnum.equals(JWTExceptionEnum.SIGNAUTURE_EXCEPTION)) {
//				CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_REFRESH_JSON_TOKEN.getCode(),
//						ResponseCode.INVALID_REFRESH_JSON_TOKEN.getMessage());
//				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//				return null;
//			}
//			if (refreshJwtExceptionEnum.equals(JWTExceptionEnum.EXPIRED_JWT_EXCEPTION)) {
//				CommonResponse commonResponse = CommonResponse.create(ResponseCode.EXPIRED_JSON_TOKEN.getCode(),
//						ResponseCode.EXPIRED_JSON_TOKEN.getMessage());
//				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//				return null;
//			}
//			UserModel userModel = getUserFromToken(refreshToken, httpServletResponse, userService);
//			if (userModel == null) {
//				CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_REQUEST.getCode(),
//						ResponseCode.INVALID_REQUEST.getMessage());
//				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
//				return null;
//			}
//			JwtTokenModel jwtTokenModel = JwtTokenModel.createLoginToken();
//			try {
//				userModel.setAccessJWTToken(JwtUtil.generateAccessToken(userModel.getEmail(),
//						JsonUtil.toJson(jwtTokenModel), jwtTokenModel));
//			} catch (EndlosAPIException e) {
//				LoggerService.exception(e);
//			}
//			TokenBlackListModel tokenBlackListModel = new TokenBlackListModel();
//			tokenBlackListModel.setUserModel(userModel);
//			tokenBlackListModel.setJwtToken(accessToken);
//			tokenBlackListService.create(tokenBlackListModel);
//			return userModel;
		}
		UserModel userModel = getUserFromToken(accessToken, httpServletResponse, userService);
		if (userModel == null) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		TokenBlackListModel tokenBlackListModel = tokenBlackListService.findByUserAndToken(userModel.getId(),
				accessToken);
		if (tokenBlackListModel != null) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		userModel.setAccessJWTToken(accessToken);
		return userModel;
	}

	/**
	 * Send an error response in json from the filter.
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param commonResponse
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void sendResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			CommonResponse commonResponse) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper json = new ObjectMapper();
		httpServletResponse.setContentType("application/json");
		json.writeValue(httpServletResponse.getOutputStream(), commonResponse);
	}

	@Override
	public void destroy() {

	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * This method is used to set role module rights of the logged in user.
	 * 
	 * @param userSessionModel
	 */
	private void setRoleModuleRights(UserModel userModel) {
		RoleService roleService = (RoleService) applicationContext.getBean("roleService");
		for (RoleModel roleModel : userModel.getRoleModels()) {
			roleModel.setRoleModuleRightsModels(roleService.getRights(roleModel.getId()));
			userModel.addRoleModel(roleModel);
		}
	}

	/**
	 * This method is used to set requested vendor model id into auditor which will
	 * help in all internal requests. If a user is mapped to more then one vendor
	 * then system requires vendor id in header else it throws the exception.
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param userSessionModel
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private void validateAndSetCustomerRequestModel(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, UserModel userModel)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (userModel.getCustomerModels() == null
				|| (userModel).getCustomerModels() != null && userModel.getCustomerModels().isEmpty()) {
			return;
		}

		if (userModel.getCustomerModels() != null && userModel.getCustomerModels().size() == 1) {
			CustomerModel customerModel = userModel.getCustomerModels().iterator().next();
			if (!customerModel.isActive()) {
				CommonResponse commonResponse = CommonResponse.create(
						ResponseCode.CUSTOMER_ACCOUNT_IS_BEEN_DEACTIVATED.getCode(),
						ResponseCode.CUSTOMER_ACCOUNT_IS_BEEN_DEACTIVATED.getMessage());
				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
				return;
			}
			userModel.setRequestedCustomerModel(customerModel);
		} else {
			String customerId = httpServletRequest.getHeader("customer-id");
			if (StringUtils.isBlank(customerId)) {
				CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_CUSTOMER_ID.getCode(),
						ResponseCode.INVALID_CUSTOMER_ID.getMessage());
				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
				return;
			}

			CustomerService customerService = (CustomerService) applicationContext.getBean("customerService");
			CustomerModel customerModel = customerService.get(Long.valueOf(customerId));
			if (customerModel == null) {
				CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_CUSTOMER_ID.getCode(),
						ResponseCode.INVALID_CUSTOMER_ID.getMessage());
				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
				return;
			}
			if (!customerModel.isActive()) {
				CommonResponse commonResponse = CommonResponse.create(
						ResponseCode.CUSTOMER_ACCOUNT_IS_BEEN_DEACTIVATED.getCode(),
						ResponseCode.CUSTOMER_ACCOUNT_IS_BEEN_DEACTIVATED.getMessage());
				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
				return;
			}
			if (!userModel.getCustomerModels().contains(customerModel)) {
				CommonResponse commonResponse = CommonResponse.create(ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
						ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
				sendResponse(httpServletRequest, httpServletResponse, commonResponse);
				return;
			}
			userModel.setRequestedCustomerModel(customerModel);
		}
	}

	/**
	 * This method is used to get user from token.
	 *
	 * @param token
	 * @param httpServletResponse
	 * @param userService
	 * @throws IOException
	 */
	private UserModel getUserFromToken(String jwtAccessToken, HttpServletResponse httpServletResponse,
			UserService userService) throws IOException {
		Claims claims = null;
		UserModel userModel = null;
		String userEmail = null;
		try {
			claims = JwtUtil.extractAllClaims(jwtAccessToken);
		} catch (EndlosAPIException e) {
			LoggerService.exception(e);
		}
		userEmail = JwtUtil.extractData(jwtAccessToken, claims);
		if (userEmail != null) {
			userModel = userService.findByEmail(userEmail);
		}
		return userModel;
	}

	/**
	 * This method is used to validate accessToken.
	 *
	 * @param jwtAccesssTokenHeader
	 * @param httpServletResponse
	 * @throws IOException
	 */
	private String validateAccessToken(HttpServletRequest httpServletRequest, String jwtAccesssTokenHeader,
			HttpServletResponse httpServletResponse) throws IOException {
		if (StringUtils.isBlank(jwtAccesssTokenHeader)) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		String[] accessToken = jwtAccesssTokenHeader.split(" ");
		if (accessToken == null || accessToken.length != 2) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		return accessToken[1];
	}

	/**
	 * This method is used to validate refreshToken.
	 *
	 * @param refreshAccesssTokenHeader
	 * @param httpServletResponse
	 * @throws IOException
	 */
	private String validateRefreshToken(HttpServletRequest httpServletRequest, String refreshAccesssTokenHeader,
			HttpServletResponse httpServletResponse) throws IOException {
		if (StringUtils.isBlank(refreshAccesssTokenHeader)) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		String[] refreshToken = refreshAccesssTokenHeader.split(" ");
		if (refreshToken == null || refreshToken.length != 2) {
			CommonResponse commonResponse = CommonResponse.create(ResponseCode.INVALID_JSON_TOKEN.getCode(),
					ResponseCode.INVALID_JSON_TOKEN.getMessage());
			sendResponse(httpServletRequest, httpServletResponse, commonResponse);
			return null;
		}
		return refreshToken[1];
	}

//	/**
//	 * Send an error response in json from the filter.
//	 *
//	 * @param httpServletResponse
//	 * @param commonResponse
//	 * @throws IOException
//	 */
//	private void sendResponse(HttpServletResponse httpServletResponse, CommonResponse commonResponse)
//			throws IOException {
//		ObjectMapper json = new ObjectMapper();
//		httpServletResponse.setContentType("application/json");
//		json.writeValue(httpServletResponse.getOutputStream(), 
//	}

	/**
	 * // * Send unAutorized response in json from the filter. // * // * @param
	 * httpServletResponse // * @param commonResponse // * @throws IOException //
	 */
//	private void sendUnAuthorizeResponse(HttpServletResponse httpServletResponse) throws IOException {
//		Map<String, Object> errorDetails = new HashMap<>();
//		errorDetails.put("message", ResponseCode.AUTHENTICATION_REQUIRED.getMessage());
//		errorDetails.put("code", ResponseCode.AUTHENTICATION_REQUIRED.getCode());
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//		objectMapper.writeValue(httpServletResponse.getWriter(), errorDetails);
//	}
//
//	/**
//	 * Send token exception response in json from the filter.
//	 *
//	 * @param httpServletResponse
//	 * @param commonResponse
//	 * @throws IOException
//	 */
//	private void sendTokenExceptionResponse(HttpServletResponse httpServletResponse, CommonResponse commonResponse)
//			throws IOException {
//		ObjectMapper objectMapper = new ObjectMapper();
//		Map<String, Object> errorDetails = new HashMap<>();
//		errorDetails.put("message", commonResponse.getMessage());
//		errorDetails.put("code", commonResponse.getCode());
//
//		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
//		objectMapper.writeValue(httpServletResponse.getWriter(), errorDetails);
//	}

}