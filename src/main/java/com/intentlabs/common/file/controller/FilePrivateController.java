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
 *****************************************************************************/
package com.intentlabs.common.file.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.enums.ExcelFileExtensionEnum;
import com.intentlabs.common.file.enums.ImageFileExtensionEnum;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.common.util.Constant;
import com.intentlabs.common.util.FileUtility;
import com.intentlabs.common.util.WebUtil;

/**
 * This controller maps all file upload related apis.
 * 
 * @author Dhruvang.Joshi
 * @since 07/12/2017
 */
@Controller
@RequestMapping("/private/file")
public class FilePrivateController {

	@Autowired
	FileOperation fileOperation;

	/**
	 * This method is used to upload an profile pic
	 *
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/upload-profile-pic")
	@ResponseBody
	@AccessLog
	public Response uploadProfilePic(@RequestParam(name = "file", required = false) MultipartFile multipartFile)
			throws EndlosAPIException {
		isValidImageFile(multipartFile);
		String path = SystemSettingModel.getDefaultFilePath() + File.separator + ModuleEnum.USER.getName() + File.separator + Constant.IMAGE_FOLDER;
		File uploadedFile = FileUtility.upload(multipartFile, path, multipartFile.getOriginalFilename());
		String uploadedFileExtension = uploadedFile.getName().substring(uploadedFile.getName().lastIndexOf(".") + 1, uploadedFile.getName().length());
		File compressFile = null;
		if (!ImageFileExtensionEnum.PNG.getName().equals(uploadedFileExtension)) {
			compressFile = FileUtility.compressImage(uploadedFile, path);
		} else {
			compressFile = FileUtility.compressPNGImage(uploadedFile, path);
		}

		return fileOperation.doSave(uploadedFile.getName(), multipartFile.getOriginalFilename(), compressFile, ModuleEnum.USER.getId(), true, uploadedFile.getAbsolutePath(), compressFile.getAbsolutePath());
//		return null;
	}

	/**
	 * This method is used to download profile pic.
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/download-profile-pic")
	@ResponseBody
	@AccessLog
	public Response downloadProfilePic(@RequestParam(value = "fileId") String fileId,
			@RequestParam(value = "requireCompressImage", required = false) boolean requireCompressImage)
			throws EndlosAPIException {
		if (StringUtils.isBlank(fileId)) {
			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_MISSING.getCode(),
					ResponseCode.FILE_ID_IS_MISSING.getMessage());
		}
		FileModel fileModel = fileOperation.get(fileId);
		if (fileModel == null) {
			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_INVALID.getCode(),
					ResponseCode.FILE_ID_IS_INVALID.getMessage());
		}
		String filePath = null;
		if (requireCompressImage) {
			filePath = fileModel.getCompressPath();
		} else {
			filePath = fileModel.getPath();
		}
		setCookie(fileModel.getName(), fileModel.getUpload());
		FileUtility.download(filePath, true);
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	/**
	 * This method is used to validate the image file.
	 * 
	 * @param multipartFile
	 * @throws EndlosAPIException
	 */
	private void isValidImageFile(MultipartFile multipartFile) throws EndlosAPIException {
		if (multipartFile == null || multipartFile.isEmpty()) {
			throw new EndlosAPIException(ResponseCode.FILE_IS_MISSING.getCode(),
					ResponseCode.FILE_IS_MISSING.getMessage());
		}
		if (StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
			throw new EndlosAPIException(ResponseCode.FILE_IS_MISSING.getCode(),
					ResponseCode.FILE_IS_MISSING.getMessage());
		}
		if (!multipartFile.getOriginalFilename().contains(".")) {
			throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
					ResponseCode.INVALID_FILE_FORMAT.getMessage());
		} else {
			if (ImageFileExtensionEnum.fromId(multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)) == null) {
				throw new EndlosAPIException(ResponseCode.UPLOAD_IMAGE_ONLY.getCode(),
						ResponseCode.UPLOAD_IMAGE_ONLY.getMessage());
			}
		}
	}

	private void setCookie(String fileName, Long uploadTime) {
		WebUtil.getCurrentResponse().setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
		WebUtil.getCurrentResponse().setHeader("Cache-control", "max-age=31536000");
		WebUtil.getCurrentResponse().setHeader("Last-Modified", uploadTime.toString());
	}

	/**
	 * This method is used to upload datatable excel file.
	 * 
	 * @param multipartFile
	 * @param uploadName
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping("/upload-datatable-excel-file")
	@ResponseBody
	@AccessLog
	public Response uploadDataTableExcelFile(@RequestParam(name = "file", required = false) MultipartFile multipartFile)
			throws EndlosAPIException {
		isValidExcelFile(multipartFile);
		String filePath = SystemSettingModel.getDefaultFilePath() + File.separator + "employeesheet";
		File uploadedFile = FileUtility.upload(multipartFile, filePath, multipartFile.getOriginalFilename());

		return fileOperation.doSave(uploadedFile.getName(), multipartFile.getOriginalFilename(), null,
				ModuleEnum.MACHINE.getId(), false, uploadedFile.getAbsolutePath(), null);
	}

	private void isValidExcelFile(MultipartFile multipartFile) throws EndlosAPIException {
		if (multipartFile == null || (multipartFile != null && multipartFile.isEmpty())) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
					"File " + ResponseCode.DATA_IS_MISSING.getMessage());
		}
		if (!multipartFile.getOriginalFilename().contains(".")) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(), "Invalid File format");
		} else {
			if (ExcelFileExtensionEnum.fromId(multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)) == null) {
				throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
						"Please upload Excel file(s), Other file types are not allowed.");
			}
		}
	}

//	/**
//	 * This method is used to upload customer logo
//	 * 
//	 * @param multipartFile
//	 * @return
//	 * @throws EndlosAPIException
//	 */
//	@PostMapping(value = "/upload-customer-logo")
//	@ResponseBody
//	@AccessLog
//	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.FILE_UPLOAD)
//	public Response uploadCustomerLogo(@RequestParam(name = "file", required = false) MultipartFile multipartFile)
//			throws EndlosAPIException {
//		isValidImageFile(multipartFile);
//		String path = SystemSettingModel.getDefaultFilePath() + File.separator + ModuleEnum.CUSTOMER.getName()
//				+ File.separator + Constant.IMAGE_FOLDER;
//		File uploadedFile = FileUtility.upload(multipartFile, path, multipartFile.getOriginalFilename());
//		String uploadedFileExtension = uploadedFile.getName().substring(uploadedFile.getName().lastIndexOf(".") + 1,
//				uploadedFile.getName().length());
//		File compressFile = null;
//		if (!ImageFileExtensionEnum.PNG.getName().equals(uploadedFileExtension)) {
//			compressFile = FileUtility.compressImage(uploadedFile, path);
//		} else {
//			compressFile = FileUtility.compressPNGImage(uploadedFile, path);
//		}
//		return fileOperation.doSave(uploadedFile.getName(), multipartFile.getOriginalFilename(), compressFile,
//				ModuleEnum.CUSTOMER.getId(), true, uploadedFile.getAbsolutePath(), compressFile.getAbsolutePath());
//	}
//	
	/**
	 * This method is used to upload customer logo
	 * 
	 * @param multipartFile
	 * @return
	 * @throws EndlosAPIException
	 */
	@PostMapping(value = "/upload-customer-logo")
	@ResponseBody
	@AccessLog
	@Authorization(modules = ModuleEnum.CUSTOMER, rights = RightsEnum.FILE_UPLOAD)
	public Response uploadCustomerLogoInBucket(
			@RequestParam(name = "file", required = false) MultipartFile multipartFile) throws EndlosAPIException {
//		isValidImageFile(multipartFile);
//		byte[] bytes;
//		String imagePath = null;
//		try {
//			String activeProfile = System.getProperty("spring.profiles.active");
//			if (activeProfile == null) {
//				activeProfile = "dev";
//			}
//			bytes = multipartFile.getBytes();
			Storage storage = StorageOptions.getDefaultInstance().getService();
//			imagePath = multipartFile.getOriginalFilename();
//			Bucket bucket = storage.get("customer_logo_" + activeProfile);
//			if (bucket == null) {
//				bucket = storage.create(BucketInfo.of("customer_logo_" + activeProfile));
//			}
//			bucket.create(imagePath, new ByteArrayInputStream(bytes));
//			return null;
//		} catch (IOException e) {
//			LoggerService.exception(e);
//		}
		return fileOperation.doSaveInBucket(multipartFile.getName(), multipartFile.getOriginalFilename(), null,
				ModuleEnum.CUSTOMER.getId(), true, "imagePath", "imagePath");
	}

	/**
	 * 
	 * @param fileId
	 * @param httpServletResponse
	 * @return
	 * @throws NinetyOneERPException
	 */
	@RequestMapping(value = "/download-excel-file", method = RequestMethod.GET)
	@ResponseBody
	Response downloadExcelSheet(@RequestParam(value = "fileId") String fileId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws EndlosAPIException {

		if (StringUtils.isBlank(fileId)) {
			throw new EndlosAPIException(ResponseCode.DATA_IS_MISSING.getCode(),
					"FileId " + ResponseCode.DATA_IS_MISSING.getMessage());
		}
		FileModel fileModel = fileOperation.get(fileId);
		String filePath = SystemSettingModel.getDefaultFilePath() + File.separator + "Excel" + File.separator
				+ fileModel.getName();
		setCookie(fileModel.getName(), fileModel.getUpload());
		FileUtility.download(filePath, false);
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}
}