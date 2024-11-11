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

import com.intentlabs.common.exception.EndlosAPIException;

/**
 * This is used to define Custom Initialization bean when ApplicationEvent
 * fired.
 * 
 * @author nirav
 * @version 1.0
 */

public interface CustomInitializationBean {

	/**
	 * This method is called when ApplicationEvent is fired by spring.
	 * 
	 * @throws {@link EndlosAPIException}
	 */
	public void onStartUp() throws EndlosAPIException;
}