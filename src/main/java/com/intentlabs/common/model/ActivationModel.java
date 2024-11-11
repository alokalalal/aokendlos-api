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
package com.intentlabs.common.model;

import com.intentlabs.common.user.model.UserModel;

/**
 * This is activation model which maps activation by references for every table.
 * @author Nirav.Shah
 * @since 25/12/2019
 */
public abstract class ActivationModel extends AuditableModel {

	private static final long serialVersionUID = 6510352715534906544L;
	private boolean active;
    private Long activationDate;
	private UserModel activationChangeBy;

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Long getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(Long activationDate) {
		this.activationDate = activationDate;
	}
	public UserModel getActivationChangeBy() {
		return activationChangeBy;
	}
	public void setActivationChangeBy(UserModel activationChangeBy) {
		this.activationChangeBy = activationChangeBy;
	}
}
