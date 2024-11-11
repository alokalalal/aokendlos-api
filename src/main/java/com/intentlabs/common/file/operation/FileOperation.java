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
package com.intentlabs.common.file.operation;

import java.io.File;
import java.util.List;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.operation.Operation;
import com.intentlabs.common.response.Response;

/**
 *
 * @author Dhruvang Joshi.
 * @version 1.0
 * @since 25/11/2017
 */
public interface FileOperation extends Operation {

	/**
	 * This method is used to save file name with its details.
	 * 
	 * @param fileName
	 * @param originalName
	 * @param moduleId
	 * @param isPublic
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doSave(String fileName, String originalName, File compressImage, Integer moduleId, boolean isPublic,
			String path, String compressPath ) throws EndlosAPIException;

	/**
	 * This method is used to save file & thumb nail name with its details.
	 * 
	 * @param fileName
	 * @param thumbNailName
	 * @param compressName
	 * @param moduleId
	 * @param ispublic
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doSaveWithThumbNail(String fileName, String originalName, String thumbNailName, File compressName,
			Integer moduleId, String path, String compressPath) throws EndlosAPIException;

	/**
	 * This method is used to prepare model from view.
	 * 
	 * @param fileModel
	 * @param fileName
	 * @param originalName
	 * @param moduleId
	 * @param isPublic
	 * @param compressName
	 * @return
	 */
	FileModel toModel(FileModel fileModel, String originalName, String fileName, Integer moduleId, boolean isPublic,
			String compressName, String path, String compressPath) throws EndlosAPIException;

	/**
	 * This method is used to prepare model from view.
	 * 
	 * @param request
	 * @return
	 */
	FileModel getModel(FileView fileView);

	/**
	 * This method used when require new model for view
	 * 
	 * @param view view of model
	 * @return model
	 */
	FileModel getNewModel();

	/**
	 * This method used when need to convert model to view
	 * 
	 * @param model
	 * @return view
	 */
	FileView fromModel(FileModel fileModel);

	/**
	 * This method convert list of model to list of view
	 * 
	 * @param modelList list of model
	 * @return list of view
	 */
	List<FileView> fromModelList(List<FileModel> fileModels);

	/**
	 * This method use for get Service with respected operation
	 * 
	 * @return FileService
	 */
	FileService getService();

	/**
	 * This method is used to get file details using file Id.
	 * 
	 * @param fileId
	 * @return
	 * @throws IAException
	 */
	FileModel get(String fileId) throws EndlosAPIException;
	

	/**
	 * This method is used to save file name with its details.
	 * 
	 * @param fileName
	 * @param originalName
	 * @param moduleId
	 * @param isPublic
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doSaveInBucket(String fileName, String originalName, File compressImage, Integer moduleId, boolean isPublic,
			String path, String compressPath ) throws EndlosAPIException;
}