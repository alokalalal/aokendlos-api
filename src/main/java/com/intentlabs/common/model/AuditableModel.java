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
 * This is update model which maps update by references for every table.
 * @author Nirav.Shah
 * @since 25/12/2019
 */
public abstract class AuditableModel extends CreateModel {

	private static final long serialVersionUID = 4830734796526520159L;
	
	private Long lockVersion;
	private UserModel updateBy;
    private Long updateDate;

	public Long getLockVersion() {
		return lockVersion;
	}
	public void setLockVersion(Long lockVersion) {
		this.lockVersion = lockVersion;
	}
	public UserModel getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(UserModel updateBy) {
		this.updateBy = updateBy;
	}
	public Long getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}
}
