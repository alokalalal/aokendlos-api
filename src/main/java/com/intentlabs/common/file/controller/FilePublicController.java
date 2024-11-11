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
package com.intentlabs.common.file.controller;

import com.intentlabs.common.setting.model.SystemSettingModel;
import com.intentlabs.common.user.enums.ModuleEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.enums.DocumentExtensionEnum;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.util.FileUtility;
import com.intentlabs.common.util.WebUtil;
import com.intentlabs.endlos.machine.model.MachineModel;
import com.intentlabs.endlos.machine.service.MachineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * This controller maps all file public related apis.
 * 
 * @author Dhruvang.Joshi
 * @since 24/11/2018
 */
@Controller
@RequestMapping("/public/file")
public class FilePublicController {

	@Autowired
	FileOperation fileOperation;

	@Autowired
	MachineService machineService;

	private void setCookie(String fileName, Long uploadTime) {
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

		switch (DocumentExtensionEnum.fromId(fileExtension)) {
		case PDF:
			WebUtil.getCurrentResponse().setContentType("application/pdf");
			break;
		case XLS:
			WebUtil.getCurrentResponse().setContentType("application/xls");
			break;
		case XLSX:
			WebUtil.getCurrentResponse().setContentType("application/xlsx");
			break;
		case JPEG:
			WebUtil.getCurrentResponse().setContentType("application/jpeg");
			break;
		case JPG:
			WebUtil.getCurrentResponse().setContentType("application/jpeg");
			break;
		case PNG:
			WebUtil.getCurrentResponse().setContentType("application/png");
			break;
		case SVG:
			WebUtil.getCurrentResponse().setContentType("image/svg+xml");
			break;
		case CSV:
			WebUtil.getCurrentResponse().setContentType("text/csv"); // Add this case for CSV
			break;
		default:
			break;
		}

		WebUtil.getCurrentResponse().setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
		WebUtil.getCurrentResponse().setHeader("Cache-control", "max-age=31536000");
		WebUtil.getCurrentResponse().setHeader("Last-Modified", uploadTime.toString());
	}

//	@GetMapping(value = "/download-logo")
//	@ResponseBody
//	@AccessLog
//	public Response downloadLogo(@RequestParam(value = "fileId") String fileId,
//			@RequestParam(value = "requireCompressImage", required = false) boolean requireCompressImage)
//			throws EndlosAPIException {
//		if (StringUtils.isBlank(fileId)) {
//			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_MISSING.getCode(),
//					ResponseCode.FILE_ID_IS_MISSING.getMessage());
//		}
//		FileModel fileModel = fileOperation.get(fileId);
//		if (fileModel == null) {
//			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_INVALID.getCode(),
//					ResponseCode.FILE_ID_IS_INVALID.getMessage());
//		}
//		String filePath = null;
//		if (requireCompressImage) {
//			filePath = fileModel.getCompressPath();
//		} else {
//			filePath = fileModel.getPath();
//		}
//		setCookie(fileModel.getName(), fileModel.getUpload());
//		FileUtility.download(filePath, true);
//		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
//	}

	@GetMapping(value = "/download-logo")
	@ResponseBody
	@AccessLog
	public Response downloadLogo(@RequestParam(value = "fileId") String fileId,
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
		String filePath = fileModel.getPath();
		FileUtility.downloadCloudImage(filePath, null);
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}

	/**
	 * This method is used to download cloud image.
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/download-cloud-image")
	@ResponseBody
	@AccessLog
	public Response downloadCloudImage(@RequestParam(value = "fileId") String fileId,
			@RequestParam(value = "machineId") Long machineId) throws EndlosAPIException {
		if (StringUtils.isBlank(fileId)) {
			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_MISSING.getCode(),
					ResponseCode.FILE_ID_IS_MISSING.getMessage());
		}
		MachineModel machineModel = machineService.get(machineId);
		if (machineModel == null) {
			throw new EndlosAPIException(ResponseCode.MACHINE_IS_INVALID.getCode(),
					ResponseCode.MACHINE_IS_INVALID.getMessage());
		}
		FileModel fileModel = fileOperation.get(fileId);
		if (fileModel == null) {
			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_INVALID.getCode(),
					ResponseCode.FILE_ID_IS_INVALID.getMessage());
		}
		String filePath = fileModel.getPath();
		FileUtility.downloadCloudImage(filePath, machineModel);
		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}
	@RequestMapping(value = "/download-csv-file", method = RequestMethod.GET)
	@ResponseBody
	Response downloadCsv(@RequestParam(value = "fileId") String fileId, HttpServletRequest httpServletRequest,
						 HttpServletResponse httpServletResponse) throws EndlosAPIException {

		if (StringUtils.isBlank(fileId)) {
			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_MISSING.getCode(),
					ResponseCode.FILE_ID_IS_MISSING.getMessage());
		}
		FileModel fileModel = fileOperation.get(fileId);
		if (fileModel == null) {
			throw new EndlosAPIException(ResponseCode.FILE_ID_IS_INVALID.getCode(),
					ResponseCode.FILE_ID_IS_INVALID.getMessage());
		}


		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "_dev";
		}
		/*
		// local download code Start
		String path = SystemSettingModel.getDefaultFilePath() + File.separator + "machine_barcode" + activeProfile + File.separator + fileModel.getName();
		setCookie(fileModel.getName(), fileModel.getUpload());
		FileUtility.download(path, false);
		// local download code End
		*/

		String filePath = fileModel.getPath();
		FileUtility.downloadCloudCsv(filePath);

		return CommonResponse.create(ResponseCode.SUCCESSFUL.getCode(), ResponseCode.SUCCESSFUL.getMessage());
	}
}