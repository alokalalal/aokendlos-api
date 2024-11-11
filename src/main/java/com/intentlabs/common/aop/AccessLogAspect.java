/*******************************************************************************
 * Copyright -2019 @intentlabs
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
package com.intentlabs.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.common.user.model.UserModel;

/**
 * Access logger aspect is log requested URI with the time taken by that URL.
 * 
 * @author Nirav.Shah
 * @since 08/08/2018
 */
@Component
@Aspect
public class AccessLogAspect {

	/**
	 * Used to log requested url & time taken by url.
	 * 
	 * @param pjp
	 * @param performanceLogger
	 * @return Object
	 * @throws Throwable
	 */
	@Around("@annotation(accessLog)")
	public Object around(ProceedingJoinPoint pjp, AccessLog accessLog) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			return pjp.proceed();
		} finally {
			HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes()).getRequest();
			UserModel userModel = Auditor.getAuditor();
			if (userModel == null) {
				LoggerService.info(pjp.getSignature().getName().toUpperCase(), httpServletRequest.getRequestURI(),
						(System.currentTimeMillis() - startTime) + "ms");
			} else {
				if (StringUtils.isBlank(userModel.getEmail())) {
					LoggerService.info(pjp.getSignature().getName().toUpperCase(),
							userModel.getMobile() + " Requested for, " + httpServletRequest.getRequestURI(),
							(System.currentTimeMillis() - startTime) + "ms");
				} else {
					LoggerService.info(pjp.getSignature().getName().toUpperCase(),
							userModel.getEmail() + " Requested for, " + httpServletRequest.getRequestURI(),
							(System.currentTimeMillis() - startTime) + "ms");
				}
			}
		}
	}
}
