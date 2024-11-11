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
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.file.view.FileView;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.response.ViewResponse;
import com.intentlabs.common.util.DateUtility;
import com.intentlabs.common.util.Utility;

/**
 * This class used to perform all business operation on file model.
 * 
 * @author Dhruvang.Joshi
 * @since 30/11/2017
 */
@Component(value = "fileOperation")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class FileOperationImpl implements FileOperation {

	@Autowired
	private FileService fileService;

	@Override
	public FileService getService() {
		return fileService;
	}

	@Override
	public Response doSave(String fileName, String originalName, File compressImage, Integer moduleId, boolean isPublic,
			String path, String compressPath) throws EndlosAPIException {
		FileModel fileModel = toModel(getNewModel(), fileName, originalName, moduleId, isPublic,
				compressImage != null ? compressImage.getName() : null, path, compressPath);
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				fromModel(fileModel));
	}
	
	public Response doSaveInBucket(String fileName, String originalName, File compressImage, Integer moduleId, boolean isPublic,
			String path, String compressPath) throws EndlosAPIException {
		FileModel fileModel = toModel(getNewModel(), fileName, originalName, moduleId, isPublic,
				compressImage != null ? compressImage.getName() : null, path, compressPath);
		fileService.create(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(),
				fromModel(fileModel));
	}

	@Override
	public Response doSaveWithThumbNail(String fileName, String originalName, String thumbNailName, File compressName,
			Integer moduleId, String path, String compressPath) throws EndlosAPIException {
		FileModel fileModel = toModel(getNewModel(), fileName, originalName, moduleId, true, null, path, compressPath
				);
		fileModel.setCompressName(compressName.getAbsolutePath());
		fileService.create(fileModel);
		FileView fileView = fromModel(fileModel);
		return ViewResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage(), fileView);
	}

	@Override
	public FileModel toModel(FileModel fileModel, String fileName, String originalName, Integer moduleId,
			boolean isPublic, String compressName, String path, String compressPath)
			throws EndlosAPIException {
		if(fileModel.getId() ==null)
			fileModel.setFileId(Utility.generateUuid());
		else {
			fileModel.setId(fileModel.getId());
		}
		fileModel.setName(fileName);
		fileModel.setOriginalName(originalName);
		fileModel.setModule(moduleId.longValue());
		fileModel.setPublicfile(isPublic);
		fileModel.setUpload(DateUtility.getCurrentEpoch());
		fileModel.setCompressName(compressName);
		fileModel.setPath(path);
		fileModel.setCompressPath(compressPath);
		return fileModel;
	}

	@Override
	public FileModel getModel(FileView fileView) {
		return fileService.getByFileId(fileView.getFileId());
	}

	@Override
	public FileModel getNewModel() {
		return new FileModel();
	}

	@Override
	public FileView fromModel(FileModel fileModel) {
		FileView fileView = new FileView();

		if(fileModel != null)
			fileView.setId(fileModel.getId());

		fileView.setName(fileModel.getName());
		fileView.setOriginalName(fileModel.getOriginalName());
		fileView.setCompressName(fileModel.getCompressName());
		fileView.setFileId(fileModel.getFileId());
		fileView.setPublicfile(fileModel.isPublicfile());
		return fileView;
	}

	@Override
	public List<FileView> fromModelList(List<FileModel> fileModels) {
		List<FileView> fileViews = new ArrayList<>(fileModels.size());
		for (FileModel fileModel : fileModels) {
			fileViews.add(fromModel(fileModel));
		}
		return fileViews;
	}

	@Override
	public FileModel get(String fileId) throws EndlosAPIException {
		FileModel fileModel = fileService.getByFileId(fileId);
		if (fileModel == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(),
					ResponseCode.NO_DATA_FOUND.getMessage());
		}
		return fileModel;
	}
}