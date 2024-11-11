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

/**
 * This is Machine Log view which maps machine log table to class.
 * 
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
@JsonInclude(Include.NON_NULL)
public class MachineCapacityView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;

	private MachineView machineView;
	private Long plasticCapacity;
	private Long glassCapacity;
	private Long aluminiumnCapacity;
	private Long printCapacity;
	private Long maxTransaction;
	private Long maxAutoCleaning;
	private Long createDate;
	private Long startDate;
	private Long endDate;

	public MachineView getMachineView() {
		return machineView;
	}

	public Long getPlasticCapacity() {
		return plasticCapacity;
	}

	public Long getGlassCapacity() {
		return glassCapacity;
	}

	public Long getAluminiumnCapacity() {
		return aluminiumnCapacity;
	}

	public Long getPrintCapacity() {
		return printCapacity;
	}

	public Long getMaxTransaction() {
		return maxTransaction;
	}

	public Long getMaxAutoCleaning() {
		return maxAutoCleaning;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setMachineView(MachineView machineView) {
		this.machineView = machineView;
	}

	public void setPlasticCapacity(Long plasticCapacity) {
		this.plasticCapacity = plasticCapacity;
	}

	public void setGlassCapacity(Long glassCapacity) {
		this.glassCapacity = glassCapacity;
	}

	public void setAluminiumnCapacity(Long aluminiumnCapacity) {
		this.aluminiumnCapacity = aluminiumnCapacity;
	}

	public void setPrintCapacity(Long printCapacity) {
		this.printCapacity = printCapacity;
	}

	public void setMaxTransaction(Long maxTransaction) {
		this.maxTransaction = maxTransaction;
	}

	public void setMaxAutoCleaning(Long maxAutoCleaning) {
		this.maxAutoCleaning = maxAutoCleaning;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
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
}
