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
package com.intentlabs.endlos.logistic.model;

import com.intentlabs.common.model.AuditableModel;
import com.intentlabs.common.model.IdentifierModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Milan Gohil
 * @since 11/12/2023
 */
public class PickupRouteModel extends AuditableModel {

	private static final long serialVersionUID = -8686919838798454529L;
	private Integer pickupRouteNo;
	private String area;
	private String pickupRouteName;
	private String comment;
	private Long pickupRoutecreateDate;
	/*private static Map<Long, PickupRouteModel> pickupRouteList = new ConcurrentHashMap<>();
	public static void addPickupRoute(PickupRouteModel pickupRouteModel) {
		pickupRouteList.put(pickupRouteModel.getId(), pickupRouteModel);
	}
	public static Map<Long, PickupRouteModel> getPickupRoutes() {
		return pickupRouteList;
	}*/
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

	public String getPickupRouteName() {
		return pickupRouteName;
	}

	public void setPickupRouteName(String pickupRouteName) {
		this.pickupRouteName = pickupRouteName;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifierModel other = (IdentifierModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}