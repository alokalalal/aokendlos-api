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
package com.intentlabs.endlos.systemspecification.view;

import com.intentlabs.common.view.AuditableView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * @author Milan Gohil
 * @since 1/2/2024
 */
public class SystemSpecificationView extends AuditableView {

	private static final long serialVersionUID = -1942730193814291940L;
	private String anydeskId;
	private String anydeskPassword;
	private String windowsActivationKey;
	private String windowsProductionKey;
	private String windowsPassword;
	private MachineView machineView;
	private Long dateOfUpdated;
	private Long dateOfCreated;

	public String getAnydeskId() {
		return anydeskId;
	}

	public void setAnydeskId(String anydeskId) {
		this.anydeskId = anydeskId;
	}

	public String getAnydeskPassword() {
		return anydeskPassword;
	}

	public void setAnydeskPassword(String anydeskPassword) {
		this.anydeskPassword = anydeskPassword;
	}

	public String getWindowsActivationKey() {
		return windowsActivationKey;
	}

	public void setWindowsActivationKey(String windowsActivationKey) {
		this.windowsActivationKey = windowsActivationKey;
	}

	public String getWindowsProductionKey() {
		return windowsProductionKey;
	}

	public void setWindowsProductionKey(String windowsProductionKey) {
		this.windowsProductionKey = windowsProductionKey;
	}

	public String getWindowsPassword() {
		return windowsPassword;
	}

	public void setWindowsPassword(String windowsPassword) {
		this.windowsPassword = windowsPassword;
	}

	public MachineView getMachineView() {
		return machineView;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public Long getDateOfUpdated() {
		return dateOfUpdated;
	}

	public void setDateOfUpdated(Long dateOfUpdated) {
		this.dateOfUpdated = dateOfUpdated;
	}

	public Long getDateOfCreated() {
		return dateOfCreated;
	}

	public void setDateOfCreated(Long dateOfCreated) {
		this.dateOfCreated = dateOfCreated;
	}
}