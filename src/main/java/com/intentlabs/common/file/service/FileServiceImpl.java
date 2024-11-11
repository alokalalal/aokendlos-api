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
package com.intentlabs.common.file.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.service.AbstractService;

/**
 * This is service for storing all attachment.
 * 
 * @author Dhruvang Joshi.
 * @version 1.0
 * @since 25/11/2017
 */
@Service(value = "fileService")
public class FileServiceImpl extends AbstractService<FileModel> implements FileService {

	@Override
	public String getEntityName() {
		return FILE_MODEL;
	}

	@Override
	public Criteria setCommonCriteria(String entityName) {
		Criteria criteria = getSession().createCriteria(entityName);
		return criteria;
	}

	@Override
	public Criteria setSearchCriteria(Object searchObject, Criteria commonCriteria) {
		return commonCriteria;
	}

	@Override
	public void delete(FileModel fileModel) {
		getSession().delete(fileModel);
	}

	@Override
	public FileModel getByFileId(String fileId) {
		Criteria criteria = getSession().createCriteria(FILE_MODEL);
		criteria.add(Restrictions.eq("fileId", fileId));
		return (FileModel) criteria.uniqueResult();
	}
}