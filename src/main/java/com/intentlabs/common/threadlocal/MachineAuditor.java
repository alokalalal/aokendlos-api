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

import com.intentlabs.endlos.machine.model.MachineModel;

/**
 * This class store customer details into thread local to fetch customer details in
 * current request.
 * 
 * @author Nirav.Shah
 * @since 26/03/2019
 */
public class MachineAuditor {

	private static ThreadLocal<MachineModel> machineAuditor = new ThreadLocal<>();

	private MachineAuditor() {
	}

	/**
	 * To get current customer auditor details
	 * 
	 * @return User
	 */
	public static MachineModel getMachineAuditor() {
		return machineAuditor.get();
	}

	/**
	 * To set user details into thread local.
	 * 
	 * @return User
	 */
	public static void setMachineAuditor(MachineModel machineModel) {
		machineAuditor.set(machineModel);
	}

	public static void removeMachineAuditor() {
		machineAuditor.remove();
	}
}
