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
 * This is archive model which maps archive by references for every table.
 * @author Nirav.Shah
 * @since 25/12/2019
 */
public abstract class ArchiveModel extends ActivationModel {


	private static final long serialVersionUID = 2598384568984369681L;
	
	private boolean archive;
	private Long archiveDate;
	private UserModel archiveBy;

	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public Long getArchiveDate() {
		return archiveDate;
	}
	public void setArchiveDate(Long archiveDate) {
		this.archiveDate = archiveDate;
	}
	public UserModel getArchiveBy() {
		return archiveBy;
	}
	public void setArchiveBy(UserModel archiveBy) {
		this.archiveBy = archiveBy;
	}
}
