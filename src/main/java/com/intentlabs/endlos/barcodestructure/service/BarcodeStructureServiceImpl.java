
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
package com.intentlabs.endlos.barcodestructure.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.barcodestructure.model.BarcodeStructureModel;
import com.intentlabs.endlos.barcodestructure.view.BarcodeStructureView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
@Service(value = "barcodeStructureService")
public class BarcodeStructureServiceImpl extends AbstractService<BarcodeStructureModel>
		implements BarcodeStructureService {

	@Override
	public String getEntityName() {
		return BARCODE_STRUCTURE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		return getSession().createCriteria(getEntityName());
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		BarcodeStructureView barcodeStructureView = (BarcodeStructureView) searchObject;
		if (barcodeStructureView.getBarcodeTemplateView() != null) {
			commonCriteria.createAlias("barcodeTemplateModel", "barcodeTemplateModel");
			commonCriteria.add(
					Restrictions.eq("barcodeTemplateModel.id", barcodeStructureView.getBarcodeTemplateView().getId()));
		}
		return commonCriteria;
	}

	@Override
	public List<BarcodeStructureModel> findByBarcodeTemplateId(Long barcodeTemplateId) {
		Criteria criteria = setCommonCriteria(BARCODE_STRUCTURE_MODEL);
		criteria.createAlias("barcodeTemplateModel", "barcodeTemplateModel");
		criteria.add(Restrictions.eq("barcodeTemplateModel.id", barcodeTemplateId));
		return (List<BarcodeStructureModel>) criteria.list();
	}

	@Override
	public void hardDelete(BarcodeStructureModel barcodeStructureModel) {
		getSession().delete(barcodeStructureModel);
	}
}