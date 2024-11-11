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
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.view.IdentifierView;
import com.intentlabs.common.view.KeyValueView;

/**
 * This is Transaction view which maps Transaction table to class.
 * 
 * @author Hemil.Shah
 * @since 08/10/2021
 */
@JsonInclude(Include.NON_NULL)
public class TransactionLogView extends IdentifierView {

	private static final long serialVersionUID = -8686919838798454529L;
	private TransactionView transactionView;
	private String barcode;
	private KeyValueView status;
	private String reason;
	private KeyValueView material;
	private String volumn;
	private FileView imageView;
	private String weight;

	public TransactionView getTransactionView() {
		return transactionView;
	}

	public void setTransactionView(TransactionView transactionView) {
		this.transactionView = transactionView;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public KeyValueView getStatus() {
		return status;
	}

	public void setStatus(KeyValueView status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public KeyValueView getMaterial() {
		return material;
	}

	public void setMaterial(KeyValueView material) {
		this.material = material;
	}

	public String getVolumn() {
		return volumn;
	}

	public void setVolumn(String volumn) {
		this.volumn = volumn;
	}

	public FileView getImageView() {
		return imageView;
	}

	public void setImageView(FileView imageView) {
		this.imageView = imageView;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
}