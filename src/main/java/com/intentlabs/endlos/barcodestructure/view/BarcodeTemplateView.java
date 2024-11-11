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
package com.intentlabs.endlos.barcodestructure.view;

import java.util.List;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.RegexEnum;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.AuditableView;
import com.intentlabs.common.view.KeyValueView;
import com.intentlabs.endlos.machine.view.MachineView;

/**
 * This class is used to represent Barcode Structure Template object in json/in
 * customer response.
 *
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
public class BarcodeTemplateView extends AuditableView {

	private static final long serialVersionUID = -1942730193814291940L;
	private String name;
	private String totalLength;
	private String numberOfMachineAssigned;
	private String fullTextSearch;
	private KeyValueView status;
	private boolean completed;
	private List<MachineView> machineViews;
	private Long barcodeFileId;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(String totalLength) {
		this.totalLength = totalLength;
	}

	public String getNumberOfMachineAssigned() {
		return numberOfMachineAssigned;
	}

	public void setNumberOfMachineAssigned(String numberOfMachineAssigned) {
		this.numberOfMachineAssigned = numberOfMachineAssigned;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}

	public KeyValueView getStatus() {
		return status;
	}

	public void setStatus(KeyValueView status) {
		this.status = status;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public List<MachineView> getMachineViews() {
		return machineViews;
	}

	public void setMachineViews(List<MachineView> machineViews) {
		this.machineViews = machineViews;
	}

	public Long getBarcodeFileId() {
		return barcodeFileId;
	}

	public void setBarcodeFileId(Long barcodeFileId) {
		this.barcodeFileId = barcodeFileId;
	}

	public static void isValid(BarcodeTemplateView barcodeStructureTemplateView) throws EndlosAPIException {
		Validator.STRING
				.isValid(new InputField("BARCODE_TEMPLATE_NAME", barcodeStructureTemplateView.getName(), true, 100));
		Validator.STRING.isValid(new InputField("BARCODE_TEMPLATE", barcodeStructureTemplateView.getTotalLength(), true,
				30, RegexEnum.NUMERIC));
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}