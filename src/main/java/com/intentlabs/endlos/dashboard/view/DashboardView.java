/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.endlos.dashboard.view;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.view.View;

/**
 * This is Customer view which maps Customer table to class.
 * 
 * @author Hemil.Shah
 * @since 04/10/2021
 */
@JsonInclude(Include.NON_NULL)
public class DashboardView implements View {

	private static final long serialVersionUID = -70823349628419359L;
	private String totalValue;
	private String totalBottle;
	private String VoucherBarcode;
	private String totalMachine;
	private String readyMachine;
	private String warningMachine;
	private String errorMachine;
	private String morethen90machine;
	private String between60to90machine;
	private String lessthen90machine;
	private Long totalPatBottleCount;
	private BigDecimal totalPatBottleValue;
	private Long totalGlassBottleCount;
	private BigDecimal totalGlassBottleValue;
	private Long totalAluBottleCount;
	private BigDecimal totalAluBottleValue;

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getTotalBottle() {
		return totalBottle;
	}

	public void setTotalBottle(String totalBottle) {
		this.totalBottle = totalBottle;
	}

	public String getVoucherBarcode() {
		return VoucherBarcode;
	}

	public void setVoucherBarcode(String voucherBarcode) {
		VoucherBarcode = voucherBarcode;
	}

	public String getTotalMachine() {
		return totalMachine;
	}

	public void setTotalMachine(String totalMachine) {
		this.totalMachine = totalMachine;
	}

	public String getReadyMachine() {
		return readyMachine;
	}

	public void setReadyMachine(String readyMachine) {
		this.readyMachine = readyMachine;
	}

	public String getWarningMachine() {
		return warningMachine;
	}

	public void setWarningMachine(String warningMachine) {
		this.warningMachine = warningMachine;
	}

	public String getErrorMachine() {
		return errorMachine;
	}

	public void setErrorMachine(String errorMachine) {
		this.errorMachine = errorMachine;
	}

	public String getMorethen90machine() {
		return morethen90machine;
	}

	public void setMorethen90machine(String morethen90machine) {
		this.morethen90machine = morethen90machine;
	}

	public String getBetween60to90machine() {
		return between60to90machine;
	}

	public void setBetween60to90machine(String between60to90machine) {
		this.between60to90machine = between60to90machine;
	}

	public String getLessthen90machine() {
		return lessthen90machine;
	}

	public void setLessthen90machine(String lessthen90machine) {
		this.lessthen90machine = lessthen90machine;
	}

	public Long getTotalPatBottleCount() {
		return totalPatBottleCount;
	}

	public BigDecimal getTotalPatBottleValue() {
		return totalPatBottleValue;
	}

	public Long getTotalGlassBottleCount() {
		return totalGlassBottleCount;
	}

	public BigDecimal getTotalGlassBottleValue() {
		return totalGlassBottleValue;
	}

	public Long getTotalAluBottleCount() {
		return totalAluBottleCount;
	}

	public BigDecimal getTotalAluBottleValue() {
		return totalAluBottleValue;
	}

	public void setTotalPatBottleCount(Long totalPatBottleCount) {
		this.totalPatBottleCount = totalPatBottleCount;
	}

	public void setTotalPatBottleValue(BigDecimal totalPatBottleValue) {
		this.totalPatBottleValue = totalPatBottleValue;
	}

	public void setTotalGlassBottleCount(Long totalGlassBottleCount) {
		this.totalGlassBottleCount = totalGlassBottleCount;
	}

	public void setTotalGlassBottleValue(BigDecimal totalGlassBottleValue) {
		this.totalGlassBottleValue = totalGlassBottleValue;
	}

	public void setTotalAluBottleCount(Long totalAluBottleCount) {
		this.totalAluBottleCount = totalAluBottleCount;
	}

	public void setTotalAluBottleValue(BigDecimal totalAluBottleValue) {
		this.totalAluBottleValue = totalAluBottleValue;
	}
}