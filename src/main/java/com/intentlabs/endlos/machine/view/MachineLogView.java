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
package com.intentlabs.endlos.machine.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.IdentifierView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;
import com.intentlabs.endlos.machine.enums.MaterialEnum;

/**
 * This is Machine Log view which maps machine log table to class.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@JsonInclude(Include.NON_NULL)
public class MachineLogView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;

	private Long resetDate;
	private KeyValueView materialType;
	private Long materialCount;
	private Long createDate;
	private MachineView machineView;
	private CustomerView customerView;
	private String fullTextSearch;
	private LocationView locationView;
	private Long startDate;
	private Long endDate;
	private Long hardResetDate;
	private boolean isHardReset;

	public Long getResetDate() {
		return resetDate;
	}

	public KeyValueView getMaterialType() {
		return materialType;
	}

	public Long getMaterialCount() {
		return materialCount;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public MachineView getMachineView() {
		return machineView;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public LocationView getLocationView() {
		return locationView;
	}

	public void setResetDate(Long resetDate) {
		this.resetDate = resetDate;
	}

	public void setMaterialType(KeyValueView materialType) {
		this.materialType = materialType;
	}

	public static KeyValueView setMaterialType(Integer materialType) {
		MaterialEnum materialEnum = MaterialEnum.fromId(materialType);
		return KeyValueView.create(materialEnum.getId(), materialEnum.getName());
	}

	public void setMaterialCount(Long materialCount) {
		this.materialCount = materialCount;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public void setLocationView(LocationView locationView) {
		this.locationView = locationView;
	}

	public Long getStartDate() {
		return startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Long getHardResetDate() {
		return hardResetDate;
	}

	public boolean isHardReset() {
		return isHardReset;
	}

	public void setHardResetDate(Long hardResetDate) {
		this.hardResetDate = hardResetDate;
	}

	public void setHardReset(boolean isHardReset) {
		this.isHardReset = isHardReset;
	}
}
