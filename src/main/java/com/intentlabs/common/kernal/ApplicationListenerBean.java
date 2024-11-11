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
package com.intentlabs.common.kernal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.intentlabs.common.logger.LoggerService;

/**
 * This is used to catch any ApplicationEvents fired by spring.
 * @version 1.0
 */
@Component
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {

	private static final String APPLICATION_LISTENER_BEAN = "ApplicationListenerBean";
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent paramE) {
		 
		ApplicationContext applicationContext = ((ContextRefreshedEvent) paramE).getApplicationContext();
		String[] bean = applicationContext.getBeanNamesForType(CustomInitializationBean.class);

		for (String name : bean) {
			try {
				((CustomInitializationBean) applicationContext.getBean(name)).onStartUp();
			} catch (Exception exception) {
				LoggerService.exception(exception);
				LoggerService.error(APPLICATION_LISTENER_BEAN, APPLICATION_LISTENER_BEAN, "Unable to load data on server start up");
			}
		}
	}
}
