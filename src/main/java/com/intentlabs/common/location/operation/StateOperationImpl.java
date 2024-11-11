/*******************************************************************************
 * Copyright -2017 @intentlabs
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
package com.intentlabs.common.location.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.location.model.CountryModel;
import com.intentlabs.common.location.model.StateModel;
import com.intentlabs.common.location.service.CountryService;
import com.intentlabs.common.location.service.StateService;
import com.intentlabs.common.response.PageResultResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.view.KeyValueView;

/**
 * This class used to perform all business operation on state model.
 * 
 * @author Nirav
 * @since 14/11/2017
 */
@Component(value = "stateOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class StateOperationImpl implements StateOperation {

	@Autowired
	StateService stateService;

	@Autowired
	CountryService countryService;

	@Override
	public Response doGetAll(Long countryId) throws EndlosAPIException {
		List<KeyValueView> stateList = new ArrayList<>();
		Set<StateModel> stateModels = CountryModel.getCountryWiseStates().get(countryId);

		if (stateModels == null || stateModels.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		for (StateModel stateModel : stateModels) {
			stateList.add(KeyValueView.create(stateModel.getId(), stateModel.getName()));
		}
		Collections.sort(stateList, (o1, o2) -> (o1).getValue().compareTo((o2).getValue()));
		return PageResultResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				stateList.size(), stateList);
	}
}