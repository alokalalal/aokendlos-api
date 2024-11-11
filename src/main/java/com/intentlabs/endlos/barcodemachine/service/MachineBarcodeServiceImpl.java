
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
package com.intentlabs.endlos.barcodemachine.service;

import com.intentlabs.common.service.AbstractService;
import com.intentlabs.common.threadlocal.Auditor;
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeFileModel;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

/**
 * This is service for storing all attachment.
 * 
 * @author Milan Gohil.
 * @version 1.0
 * @since 23/08/2023
 */
@Service(value = "machineBarcodeService")
public class MachineBarcodeServiceImpl extends AbstractService<MachineBarcodeFileModel>
		implements MachineBarcodeService {

	@Override
	public String getEntityName() {
		return MACHINE_BARCODE_FILE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		if (Auditor.getAuditor() != null && Auditor.getAuditor().getRequestedCustomerModel() != null
				&& Auditor.getAuditor().getRequestedCustomerModel().getId() != null) {
			criteria.add(Restrictions.eq("id", Auditor.getAuditor().getRequestedCustomerModel().getId()));
		}
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		if (searchObject instanceof MachineBarcodeFileView) {
			MachineBarcodeFileView machineBarcodeFileView = (MachineBarcodeFileView) searchObject;
			/*commonCriteria.createAlias("fileModel", "fileModel", JoinType.LEFT_OUTER_JOIN);
			if (machineBarcodeFileView.getFileView() != null) {
					commonCriteria.add(Restrictions.like("fileModel.name", machineBarcodeFileView.getFileView().getFileId(), MatchMode.ANYWHERE));
			}*/
			if (machineBarcodeFileView.getBarcodeFileName() != null) {
				commonCriteria.add(Restrictions.like("barcodeFileName", machineBarcodeFileView.getBarcodeFileName(), MatchMode.ANYWHERE));
			}
		}
		return commonCriteria;
	}

	@Override
	public MachineBarcodeFileModel getLastmachineBarcodeFile() {
		Criteria criteria = getSession().createCriteria(getEntityName());
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		return (MachineBarcodeFileModel) criteria.uniqueResult();
	}

	@Override
	public void delete(MachineBarcodeFileModel machineBarcodeFileModel) {
		getSession().delete(machineBarcodeFileModel);
	}

	public MachineBarcodeFileModel getByMachineBarcodeName(String barcodeFileName) {
		Criteria criteria = setCommonCriteria(MACHINE_BARCODE_FILE_MODEL);
		criteria.add(Restrictions.ilike("barcodeFileName", barcodeFileName));
		return (MachineBarcodeFileModel) criteria.uniqueResult();
	}

}