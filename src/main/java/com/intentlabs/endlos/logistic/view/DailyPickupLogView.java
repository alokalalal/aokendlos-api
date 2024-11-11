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

import java.math.BigDecimal;

import com.intentlabs.common.view.IdentifierView;

/**
 * This class is used to represent PickupRoute object in json/in Branch
 * response.
 *
 * @author Milan Gohil
 * @since 11/12/2023
 */
public class DailyPickupLogView extends IdentifierView {

	private static final long serialVersionUID = -1942730193814291940L;
	private PickupRouteView pickupRouteView;
	private Long generatePlanDate;
	private Long numberOfPatBottle;
	private Long numberOfAluBottle;
	private Long numberOfGlassBottle;
	private Long totalNumberOfPickup;
	private BigDecimal totalWeight;

	public PickupRouteView getPickupRouteView() {
		return pickupRouteView;
	}

	public void setPickupRouteView(PickupRouteView pickupRouteView) {
		this.pickupRouteView = pickupRouteView;
	}

	public Long getGeneratePlanDate() {
		return generatePlanDate;
	}

	public void setGeneratePlanDate(Long generatePlanDate) {
		this.generatePlanDate = generatePlanDate;
	}

	public Long getNumberOfPatBottle() {
		return numberOfPatBottle;
	}

	public void setNumberOfPatBottle(Long numberOfPatBottle) {
		this.numberOfPatBottle = numberOfPatBottle;
	}

	public Long getNumberOfAluBottle() {
		return numberOfAluBottle;
	}

	public void setNumberOfAluBottle(Long numberOfAluBottle) {
		this.numberOfAluBottle = numberOfAluBottle;
	}

	public Long getNumberOfGlassBottle() {
		return numberOfGlassBottle;
	}

	public void setNumberOfGlassBottle(Long numberOfGlassBottle) {
		this.numberOfGlassBottle = numberOfGlassBottle;
	}

	public Long getTotalNumberOfPickup() {
		return totalNumberOfPickup;
	}

	public void setTotalNumberOfPickup(Long totalNumberOfPickup) {
		this.totalNumberOfPickup = totalNumberOfPickup;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
}