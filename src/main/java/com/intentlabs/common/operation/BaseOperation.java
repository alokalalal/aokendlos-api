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
package com.intentlabs.common.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.view.View;

/**
 * This is a base operation which is used to declare common business
 * operations(logic) which can be performed on every entity. Operation bridges
 * the gap between controller & model.
 * 
 * @author Nirav.Shah
 * @since 25/12/2019
 */
public interface BaseOperation<V extends View> extends Operation {

	/**
	 * This method is used to validate add request to add any entity into system.
	 * Once it is been validate. it allow users to add entity data.
	 * 
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doAdd() throws EndlosAPIException;

	/**
	 * This method is used to save entity
	 * 
	 * @param request
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doSave(V view) throws EndlosAPIException;

	/**
	 * This method is used to view entity.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doView(Long id) throws EndlosAPIException;

	/**
	 * This method is used to edit entity.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doEdit(Long id) throws EndlosAPIException;

	/**
	 * This method is used to update entity
	 * 
	 * @param view
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doUpdate(V view) throws EndlosAPIException;

	/**
	 * This method is used delete existing data.
	 * 
	 * @param id
	 * @return
	 */
	Response doDelete(Long id) throws EndlosAPIException;

	/**
	 * This method is used active/inactive entity.
	 * 
	 * @param id
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doActiveInActive(Long id) throws EndlosAPIException;

	/**
	 * This method is used to display data in tabluar format.
	 * 
	 * @param start
	 * @param recordSize
	 * @return
	 */
	Response doDisplayGrid(Integer start, Integer recordSize) throws EndlosAPIException;

	/**
	 * This method is used to display data in table format.
	 * 
	 * @param object
	 * @param start
	 * @param recordSize
	 * @param orderParam
	 * @param orderType
	 * @return
	 */
	Response doSearch(V view, Integer start, Integer recordSize, Integer orderType, Integer orderParam)
			throws EndlosAPIException;

}
