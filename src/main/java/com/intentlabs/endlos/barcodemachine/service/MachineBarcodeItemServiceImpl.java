
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
import com.intentlabs.endlos.barcodemachine.model.MachineBarcodeItemModel;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeItemView;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is service for storing all attachment.
 * 
 * @author Milan Gohil.
 * @version 1.0
 * @since 23/08/2023
 */
@Service(value = "MachineBarcodeItemService")
public class MachineBarcodeItemServiceImpl extends AbstractService<MachineBarcodeItemModel>
		implements MachineBarcodeItemService {

	@Override
	public String getEntityName() {
		return MACHINE_BARCODE_ITEM_MODEL;
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
		if (searchObject instanceof MachineBarcodeItemView) {
			MachineBarcodeItemView machineBarcodeItemView = (MachineBarcodeItemView) searchObject;

			commonCriteria.createAlias("machineBarcodeFileModel", "machineBarcodeFileModel", JoinType.LEFT_OUTER_JOIN);

			if (machineBarcodeItemView.getMachineBarcodeFileView() != null && machineBarcodeItemView.getMachineBarcodeFileView().getId() != null) {
				commonCriteria.add(Restrictions.eq("machineBarcodeFileModel.id", machineBarcodeItemView.getMachineBarcodeFileView().getId()));
			}
			if (machineBarcodeItemView.getBarcodeName() != null) {
				commonCriteria.add(Restrictions.like("barcodeName", machineBarcodeItemView.getBarcodeName(), MatchMode.ANYWHERE));
			}
		}
		return commonCriteria;
	}

	@Override
	public List<MachineBarcodeItemModel> getByMachineBarcodeFileViewId(Long barcodeFileId) {
		Criteria criteria = setCommonCriteria(MACHINE_BARCODE_ITEM_MODEL);
		criteria.createAlias("machineBarcodeFileModel", "machineBarcodeFileModel");
		criteria.add(Restrictions.eq("machineBarcodeFileModel.id", barcodeFileId));
		return (List<MachineBarcodeItemModel>) criteria.list();
	}

	@Override
	public List<MachineBarcodeItemModel> setbBarcodeNameAndBarcodeFileId(String barcodeName, Long fileId) {
		Criteria criteria = setCommonCriteria(MACHINE_BARCODE_ITEM_MODEL);
		criteria.createAlias("machineBarcodeFileModel", "machineBarcodeFileModel");
		criteria.add(Restrictions.eq("machineBarcodeFileModel.id", fileId));
		criteria.add(Restrictions.eq("barcodeName", barcodeName));
		return (List<MachineBarcodeItemModel>) criteria.list();
	}

	@Override
	public void delete(Long id) {

		String hql = "DELETE FROM machineBarcodeItemModel WHERE machineBarcodeFileModel.id=" + id;
		getSession().createQuery(hql).executeUpdate();
	}


}