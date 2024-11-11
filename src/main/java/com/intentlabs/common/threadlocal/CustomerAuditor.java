/*******************************************************************************
 * Copyright -2019 @Intentlabs
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
package com.intentlabs.common.threadlocal;

import com.intentlabs.endlos.customer.model.CustomerModel;

/**
 * This class store customer details into thread local to fetch customer details
 * in current request.
 * 
 * @author Nirav.Shah
 * @since 26/03/2019
 */
public class CustomerAuditor {

	private static ThreadLocal<CustomerModel> customerAuditor = new ThreadLocal<>();

	private CustomerAuditor() {
	}

	/**
	 * To get current customer auditor details
	 * 
	 * @return User
	 */
	public static CustomerModel getCustomerAuditor() {
		return customerAuditor.get();
	}

	/**
	 * To set user details into thread local.
	 * 
	 * @return User
	 */
	public static void setCustomerAuditor(CustomerModel customerModel) {
		customerAuditor.set(customerModel);
	}

	public static void removeCustomerAuditor() {
		customerAuditor.remove();
	}
}
