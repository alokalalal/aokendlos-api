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
package com.intentlabs.common.logger;

import com.intentlabs.common.threadlocal.Uuid;

/**
 * This class prepares data which needs to be logged using log4j2.
 * @author Nirav.Shah
 * @since 03/08/2018
 */

public class Notification {
	
	private String message;
	private String className;
	private String transactionName;
	
	private Notification(String className, String transcationName, String message){
		this.className = className;
		this.transactionName = transcationName;
		this.message = message;
	}
	
	private Notification(String message){
		this.message = message;
	}
	
	private Notification() {
		
	}
	
	/**
	 * To Create object.
	 * @param className
	 * @param transactionName
	 * @param message
	 * @return
	 */
	public static Notification create(String className, String transactionName, String message) {
		return new Notification(className, transactionName, message);
	}
	
	/**
	 * To Create object.
	 * @param message
	 * @return
	 */
	public static Notification create(String message) {
		return new Notification(message);
	}
	
	public static Notification create() {
		return new Notification();
	}
	
	public String getClassName() {
		return className;
	}

	public String getMessage() {
		return message;
	}
	
	public String getTransactionName() {
		return transactionName;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Uuid.getUuid()).append(", ");
		if(className != null) {
			stringBuilder.append(className).append(", ");
		}
		if(transactionName != null) {
			stringBuilder.append(transactionName).append(", ");
		}
		if(message != null) {
			stringBuilder.append(message);
		}
		return stringBuilder.toString();
	}
	
}
