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
package com.intentlabs.endlos.logistic.view;

import com.intentlabs.common.view.AuditableView;

/**
 * This class is used to represent PickupRoute object in json/in Branch response.
 *
 * @author Milan Gohil
 * @since 11/12/2023
 */
public class PickupRouteView extends AuditableView {

	private static final long serialVersionUID = -1942730193814291940L;
	private Integer pickupRouteNo;
	private String area;
	private String name;
	private String comment;
	private Long pickupRoutecreateDate;

	public Integer getPickupRouteNo() {
		return pickupRouteNo;
	}

	public void setPickupRouteNo(Integer pickupRouteNo) {
		this.pickupRouteNo = pickupRouteNo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getPickupRoutecreateDate() {
		return pickupRoutecreateDate;
	}

	public void setPickupRoutecreateDate(Long pickupRoutecreateDate) {
		this.pickupRoutecreateDate = pickupRoutecreateDate;
	}
}