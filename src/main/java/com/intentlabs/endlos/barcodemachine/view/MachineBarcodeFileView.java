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
package com.intentlabs.endlos.barcodemachine.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.view.ActivationView;

import java.util.List;

/**
 * 
 * @author Milan Gohil
 * @since 23/08/2023
 */
@JsonInclude(Include.NON_NULL)
public class MachineBarcodeFileView extends ActivationView {
	private static final long serialVersionUID = -4444717308537621033L;
	private Long noOfMachineAssigned;
	private Long totalNoOfBarcodes;
	private Long plastic;
	private Long glass;
	private Long alluminium;
	private String fileStatus;
	//private FileView fileView;
	private List<MachineBarcodeItemView> machineBarcodeItemViews;
	private String barcodeFileName;
	private String fullTextSearch;
	public MachineBarcodeFileView() {
		super();
	}
	/*public void setView(MachineBarcodeFileModel machineBarcodeFileModel) {
		this.setId(machineBarcodeFileModel.getId());
	}*/

	/*public void setViewList(Set<MachineBarcodeFileModel> machineBarcodeFileModels, List<MachineBarcodeFileView> machineBarcodeFileViews) {
		for (MachineBarcodeFileModel machineBarcodeFileModel : machineBarcodeFileModels) {
			MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
			machineBarcodeFileView.setView(machineBarcodeFileModel);
			machineBarcodeFileViews.add(machineBarcodeFileView);
		}
	}*/


	public Long getNoOfMachineAssigned() {
		return noOfMachineAssigned;
	}

	public void setNoOfMachineAssigned(Long noOfMachineAssigned) {
		this.noOfMachineAssigned = noOfMachineAssigned;
	}

	public Long getTotalNoOfBarcodes() {
		return totalNoOfBarcodes;
	}

	public void setTotalNoOfBarcodes(Long totalNoOfBarcodes) {
		this.totalNoOfBarcodes = totalNoOfBarcodes;
	}

	public Long getPlastic() {
		return plastic;
	}

	public void setPlastic(Long plastic) {
		this.plastic = plastic;
	}

	public Long getGlass() {
		return glass;
	}

	public void setGlass(Long glass) {
		this.glass = glass;
	}

	public Long getAlluminium() {
		return alluminium;
	}

	public void setAlluminium(Long alluminium) {
		this.alluminium = alluminium;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	/*public FileView getFileView() {
		return fileView;
	}

	public void setFileView(FileView fileView) {
		this.fileView = fileView;
	}*/

	public List<MachineBarcodeItemView> getMachineBarcodeItemViews() {
		return machineBarcodeItemViews;
	}

	public void setMachineBarcodeItemViews(List<MachineBarcodeItemView> machineBarcodeItemViews) {
		this.machineBarcodeItemViews = machineBarcodeItemViews;
	}

	public String getBarcodeFileName() {
		return barcodeFileName;
	}

	public void setBarcodeFileName(String barcodeFileName) {
		this.barcodeFileName = barcodeFileName;
	}

	public String getFullTextSearch() {
		return fullTextSearch;
	}

	public void setFullTextSearch(String fullTextSearch) {
		this.fullTextSearch = fullTextSearch;
	}
}