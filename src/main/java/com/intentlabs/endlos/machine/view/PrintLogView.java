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
import com.intentlabs.endlos.customer.view.CustomerView;
import com.intentlabs.endlos.customer.view.LocationView;

/**
 * This is Machine Log view which maps machine log table to class.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@JsonInclude(Include.NON_NULL)
public class PrintLogView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;

	private Long resetDate;
	private Long printCount;
	private Long createDate;
	private MachineView machineView;
	private CustomerView customerView;
	private String fullTextSearch;
	private LocationView locationView;
	private Long startDate;
	private Long endDate;

	public Long getResetDate() {
		return resetDate;
	}

	public Long getPrintCount() {
		return printCount;
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

	public Long getStartDate() {
		return startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setResetDate(Long resetDate) {
		this.resetDate = resetDate;
	}

	public void setPrintCount(Long printCount) {
		this.printCount = printCount;
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

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
}
