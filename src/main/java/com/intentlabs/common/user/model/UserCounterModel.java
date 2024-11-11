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
package com.intentlabs.common.user.model;

import com.intentlabs.common.model.ArchiveModel;

/**
 * This is User counter model which maps user table to class.
 * 
 * @author Vivek.Pandya
 * @since 14/10/2020
 */
public class UserCounterModel extends ArchiveModel {

	private static final long serialVersionUID = -6720581862635960465L;
	private Long totalCustomer;
	
	public Long getTotalCustomer() {
		return totalCustomer;
	}
	public void setTotalCustomer(Long totalCustomer) {
		this.totalCustomer = totalCustomer;
	}
	
	
}