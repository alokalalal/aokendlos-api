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
package com.intentlabs.common.file.view;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.file.model.FileModel;
import com.intentlabs.common.view.IdentifierView;

/**
 * This class is used to represent file object in json/in customer response.
 * 
 * @author Nirav
 * @since 30/11/2017
 */
@JsonInclude(Include.NON_NULL)
public class FileView extends IdentifierView {

	private static final long serialVersionUID = 8692674149531174388L;
	private MultipartFile file;
	private Integer moduleId;
	private String fileId;
	private String thumbNailId;
	private String name;
	private String originalName;
	private String thumbNailName;
	private boolean publicfile;
	private String bytes;
	private String compressName;
	private String path;

	public FileView() {
		super();
	}

	public FileView(String fileId) {
		super();
		this.fileId = fileId;
	}

	public FileView(String fileId, String name, boolean publicfile, String compressName, String path) {
		super();
		this.fileId = fileId;
		this.name = name;
		this.publicfile = publicfile;
		this.compressName = compressName;
		this.path = path;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getThumbNailId() {
		return thumbNailId;
	}

	public void setThumbNailId(String thumbNailId) {
		this.thumbNailId = thumbNailId;
	}

	public boolean isPublicfile() {
		return publicfile;
	}

	public void setPublicfile(boolean publicfile) {
		this.publicfile = publicfile;
	}

	public String getThumbNailName() {
		return thumbNailName;
	}

	public void setThumbNailName(String thumbNailName) {
		this.thumbNailName = thumbNailName;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public String getCompressName() {
		return compressName;
	}

	public void setCompressName(String compressName) {
		this.compressName = compressName;
	}

	public static FileView setView(FileModel fileModel) {
		FileView fileView = new FileView();
		fileView.setFileId(fileModel.getFileId());
		fileView.setId(fileModel.getId());
		fileView.setName(fileModel.getName());
		fileView.setCompressName(fileModel.getCompressName());
		return fileView;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
