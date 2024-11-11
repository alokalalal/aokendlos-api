
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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.enums.OrderParamEnumType;
import com.intentlabs.common.service.AbstractService;
import com.intentlabs.endlos.barcodestructure.enums.BarcodeTemplateOrderEnum;
import com.intentlabs.endlos.barcodestructure.model.BarcodeTemplateModel;
import com.intentlabs.endlos.barcodestructure.view.BarcodeTemplateView;

/**
 * This is service for storing all attachment.
 * 
 * @author Hemil Shah.
 * @version 1.0
 * @since 19/07/2022
 */
@Service(value = "barcodeTemplateService")
public class BarcodeTemplateServiceImpl extends AbstractService<BarcodeTemplateModel>
		implements BarcodeTemplateService {

	@Override
	public String getEntityName() {
		return BARCODE_TEMPLATE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
//		if (Auditor.getAuditor() != null && Auditor.getAuditor().getRequestedCustomerModel() != null
//				&& Auditor.getAuditor().getRequestedCustomerModel().getId() != null) {
//			criteria.createAlias("createBy", "createBy");
//			criteria.add(Restrictions.eq("createBy.id", Auditor.getAuditor().getRequestedCustomerModel().getId()));
//		}
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		BarcodeTemplateView barcodeTemplateView = (BarcodeTemplateView) searchObject;
		if (!StringUtils.isEmpty(barcodeTemplateView.getFullTextSearch())) {
			commonCriteria.add(Restrictions.disjunction()
					.add(Restrictions.ilike("name", barcodeTemplateView.getFullTextSearch(), MatchMode.START)));
		}
		return commonCriteria;
	}

	@Override
	public void hardDelete(BarcodeTemplateModel barcodeTemplateModel) {
		getSession().delete(barcodeTemplateModel);
	}

	@Override
	protected Class<? extends OrderParamEnumType> getOrderParameterEnum() {
		return BarcodeTemplateOrderEnum.class;
	}

	@Override
	public List<BarcodeTemplateModel> doExport(BarcodeTemplateView barcodeTemplateView, Integer orderType,
			Integer orderParam) {
		Criteria commonCriteria = setCommonCriteria(BARCODE_TEMPLATE_MODEL);
		commonCriteria = setSearchCriteria(barcodeTemplateView, commonCriteria);
		commonCriteria.setProjection(null);
		commonCriteria.setResultTransformer(Criteria.ROOT_ENTITY);
		setOrder(commonCriteria, orderType, orderParam);
		return (List<BarcodeTemplateModel>) commonCriteria.list();
	}

	@Override
	public BarcodeTemplateModel getByName(String name) {
		Criteria criteria = setCommonCriteria(BARCODE_TEMPLATE_MODEL);
		criteria.add(Restrictions.eq("name", name));
		return (BarcodeTemplateModel) criteria.uniqueResult();
	}
}