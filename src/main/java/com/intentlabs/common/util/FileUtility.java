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
package com.intentlabs.common.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.enums.FileUploadType;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.endlos.machine.model.MachineModel;

/**
 * This utility is used to perform all files related operations.
 * 
 * @author Nirav.Shah
 * @since 27/12/2019
 *
 */
public class FileUtility {

	private FileUtility() {
		// Not allow to create any object.
	}

	/**
	 * This method is used to write uploaded file on server. It can create thumb
	 * nail of uploaded image base on given height and size.
	 * 
	 * @param multipartFile - file
	 * @param filePath      - Path where uploaded file will be stored
	 * @param originalName  - name of file.
	 * @return
	 * @throws EndlosAPIException
	 */
	public static File upload(MultipartFile multipartFile, String filePath, String originalName)
			throws EndlosAPIException {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			byte[] bytes = multipartFile.getBytes();
			String uploadedFileName = originalName.substring(0, originalName.lastIndexOf("."));
			String ext = originalName.substring(originalName.lastIndexOf("."));

			File newFile = new File(
					filePath + File.separator + uploadedFileName + "_" + DateUtility.getCurrentEpoch() + ext);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
			bufferedOutputStream.write(bytes);
			return newFile;
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					LoggerService.exception(e);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
				}
			}
		}
	}

	/**
	 * This method is used to write uploaded file on server. It can create thumb
	 * nail of uploaded image base on given height and size.
	 * 
	 * @param multipartFile - file
	 * @param filePath      - Path where uploaded file will be stored
	 * @param originalName  - name of file.
	 * @param newName       - New name of file;
	 * @return
	 * @throws EndlosAPIException
	 */
	public static File upload(MultipartFile multipartFile, String filePath, String originalName, String newName)
			throws EndlosAPIException {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			byte[] bytes = multipartFile.getBytes();

			File newFile = new File(filePath + File.separator + newName);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
			bufferedOutputStream.write(bytes);
			return newFile;
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					LoggerService.exception(e);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
				}
			}
		}
	}

	/**
	 * This method is used to write uploaded file on server. It can create thumb
	 * nail of uploaded image base on given height and size.
	 * 
	 * @param multipartFile
	 * @param module
	 * @return
	 * @throws NinetyOneERPException
	 */
	public static File uploadCroppedImages(MultipartFile multipartFile, String filePath, String originalName)
			throws EndlosAPIException {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			File file = new File(filePath);

			if (!file.exists()) {
				file.mkdirs();
			}

			byte[] bytes = multipartFile.getBytes();

			String uploadedFileName = originalName.substring(0, originalName.lastIndexOf("."));
			String ext = originalName.substring(originalName.lastIndexOf("."));

			String newFileName = uploadedFileName + "_" + DateUtility.getCurrentEpoch() + ext;

			File newFile = new File(filePath + File.separator + newFileName);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
			bufferedOutputStream.write(bytes);
			return newFile;
		} catch (

		IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					LoggerService.exception(e);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
				}
			}
		}
	}

	/**
	 * This method is used to create thumb nail of desired size from original image.
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @param module
	 * @return
	 * @throws EndlosAPIException
	 */
	public static String createThumbNail(File image, Integer width, Integer height, String filePath)
			throws EndlosAPIException {
		try {
			File thumbNailFile = new File(filePath + File.separator + "Thumbnail_" + image.getName());
			String extension = thumbNailFile.getName().substring(thumbNailFile.getName().lastIndexOf(".") + 1,
					thumbNailFile.getName().length());

			BufferedImage originalBufferedImage = ImageIO.read(image);
			Dimension originalDimension = new Dimension(originalBufferedImage.getWidth(),
					originalBufferedImage.getHeight());
			Dimension newDimension = new Dimension(width, height);
			Dimension ratioDimension = getScaledDimension(originalDimension, newDimension);
			BufferedImage newBufferedImage = new BufferedImage(new Double(ratioDimension.getWidth()).intValue(),
					new Double(ratioDimension.getHeight()).intValue(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics()
					.drawImage(originalBufferedImage.getScaledInstance(new Double(ratioDimension.getWidth()).intValue(),
							new Double(ratioDimension.getHeight()).intValue(), Image.SCALE_SMOOTH), 0, 0, null);

			ImageIO.write(newBufferedImage, extension, thumbNailFile);
			return thumbNailFile.getName();
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_CREATE_THUMBNAIL.getCode(),
					ResponseCode.UNABLE_TO_CREATE_THUMBNAIL.getMessage());
		}
	}

	/**
	 * This method is used to get new dimension ratio of it.
	 * 
	 * @param originalDimension
	 * @param newDimension
	 * @return
	 */
	private static Dimension getScaledDimension(Dimension originalDimension, Dimension newDimension) {
		int original_width = originalDimension.width;
		int original_height = originalDimension.height;
		int bound_width = newDimension.width;
		int bound_height = newDimension.height;
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}
		return new Dimension(new_width, new_height);
	}

	/**
	 * This method is used to download the files.
	 * 
	 * @param path
	 * @param httpServletResponse
	 * @param isImage
	 * @throws EndlosAPIException
	 */
	public static void download(String path, boolean isImage) throws EndlosAPIException {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			File file = new File(path);
			inputStream = new FileInputStream(file);

			if (!isImage) {
				FileCopyUtils.copy(inputStream, WebUtil.getCurrentResponse().getOutputStream());
			} else {
				byte[] filebytes = IOUtils.toByteArray(inputStream);
				outputStream = WebUtil.getCurrentResponse().getOutputStream();
				outputStream.write(filebytes);
			}
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
				}
			}
		}
	}

	public static String createCaptchaImage(String filePath, String captcha, String uuid) throws EndlosAPIException {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String newFileName = uuid + ".png";
			File newFile = new File(file.getPath() + File.separator + newFileName);
			if (newFile.exists()) {
				return newFileName;
			}
			File dummyFile = new File(WebUtil.getCurrentRequest().getServletContext().getRealPath(File.separator)
					+ File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "captcha.png");
			LoggerService.error("dummy File" + dummyFile);
			final BufferedImage image = ImageIO.read(dummyFile);
			Graphics2D graphics2d = image.createGraphics();

			graphics2d.setFont(new Font(Font.SANS_SERIF, 0, 20));
			graphics2d.setColor(Color.DARK_GRAY);
			graphics2d.drawString(captcha, 105, 26);
			graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2d.dispose();
			ImageIO.write(image, "png", newFile);
			return newFileName;
		} catch (IOException e) {
			LoggerService.exception(e);
			return null;
		}
	}

	/**
	 * This method is used to store the bytes of the image.
	 * 
	 * @param multipartFile
	 * @param filePath
	 * @return
	 * @throws EndlosAPIException
	 */
	public static File storeByteImage(MultipartFile multipartFile, String filePath) throws EndlosAPIException {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			byte[] bytes = multipartFile.getBytes();

			String uploadedFileName = multipartFile.getName().substring(0, multipartFile.getName().lastIndexOf("."));
			String ext = multipartFile.getName().substring(multipartFile.getName().lastIndexOf("."));

			String newFileName = uploadedFileName + "_" + DateUtility.getCurrentEpoch() + ext;
			File newFile = new File(filePath + File.separator + newFileName);

			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
			bufferedOutputStream.write(bytes);
			return newFile;
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					LoggerService.exception(e);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
				}
			}
		}
	}

	public static String convertToString(String filePath) throws EndlosAPIException {
		byte[] fileContent;
		try {
			fileContent = FileUtils.readFileToByteArray(new File(filePath));
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_CONVERT_INTO_BASE64.getCode(),
					ResponseCode.UNABLE_TO_CONVERT_INTO_BASE64.getMessage());
		}
		return Base64.getEncoder().encodeToString(fileContent);
	}

	/**
	 * This method is used to generate the compress images of jpeg file. This images
	 * will be used in lazy loading.
	 * 
	 * @param imageFile
	 * @param filePath
	 * @return
	 * @throws EndlosAPIException
	 */
	public static File compressImage(File imageFile, String filePath) throws EndlosAPIException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		ImageWriter imageWriter = null;
		ImageOutputStream imageOutputStream = null;
		try {
			File compressedImageFile = new File(
					filePath + File.separator + "Compress_ThumbNail_" + imageFile.getName());

			inputStream = new FileInputStream(imageFile);
			outputStream = new FileOutputStream(compressedImageFile);

			float quality = 0.001f;

			// create a BufferedImage as the result of decoding the supplied
			// InputStream
			BufferedImage image = ImageIO.read(inputStream);

			// get all image writers for JPG format
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

			if (!writers.hasNext()) {
				throw new IllegalStateException("No writers found");
			}
			imageWriter = (ImageWriter) writers.next();
			imageOutputStream = ImageIO.createImageOutputStream(outputStream);
			imageWriter.setOutput(imageOutputStream);

			ImageWriteParam param = imageWriter.getDefaultWriteParam();

			// compress to a given quality
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);

			// appends a complete image stream containing a single image and
			// associated stream and image metadata and thumbnails to the output
			imageWriter.write(null, new IIOImage(image, null, null), param);
			return compressedImageFile;
			// close all streams

		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			try {
				inputStream.close();
				outputStream.close();
				imageOutputStream.close();
				imageWriter.dispose();
			} catch (IOException e) {
				LoggerService.exception(e);
				throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
						ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
			}

		}

	}

	/**
	 * This method is used to compress the png images. In case of png images first
	 * we need to create jpeg from png and convert those jpeg image into compressed
	 * file.
	 * 
	 * @param imageFile
	 * @param filePath
	 * @return
	 * @throws EndlosAPIException
	 */
	public static File compressPNGImage(File imageFile, String filePath) throws EndlosAPIException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		ImageWriter imageWriter = null;
		ImageOutputStream imageOutputStream = null;
		try {
			File compressedImageFile = new File(filePath + File.separator + "Compress_ThumbNail_"
					+ imageFile.getName().substring(0, imageFile.getName().lastIndexOf(".") + 1) + "jpg");
			File intermediateFile = new File(filePath + File.separator + "JPEG_"
					+ imageFile.getName().substring(0, imageFile.getName().lastIndexOf(".") + 1) + "jpg");

			BufferedImage bufferedImage;
			try {
				bufferedImage = ImageIO.read(imageFile);
				BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
				ImageIO.write(newBufferedImage, "jpg", intermediateFile);
				bufferedImage.flush();
			} catch (IOException ioException) {
				LoggerService.exception(ioException);
				throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
						ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
			}

			// inputStream = new FileInputStream(imageFile);
			inputStream = new FileInputStream(intermediateFile);
			outputStream = new FileOutputStream(compressedImageFile);

			float quality = 0.001f;

			// create a BufferedImage as the result of decoding the supplied
			// InputStream
			BufferedImage image = ImageIO.read(inputStream);

			// get all image writers for JPG format
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

			if (!writers.hasNext()) {
				throw new IllegalStateException("No writers found");
			}
			imageWriter = (ImageWriter) writers.next();
			imageOutputStream = ImageIO.createImageOutputStream(outputStream);
			imageWriter.setOutput(imageOutputStream);

			ImageWriteParam param = imageWriter.getDefaultWriteParam();

			// compress to a given quality
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);

			// appends a complete image stream containing a single image and
			// associated stream and image metadata and thumbnails to the output
			imageWriter.write(null, new IIOImage(image, null, null), param);
			return compressedImageFile;
			// close all streams

		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			try {
				inputStream.close();
				outputStream.close();
				imageOutputStream.close();
				imageWriter.dispose();
			} catch (IOException e) {
				LoggerService.exception(e);
				throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
						ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
			}

		}
	}

	/**
	 * This method is used to copy files from source to destination in same system.
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingFileChannels(File source, File dest) throws IOException {

		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	public static void download(String path, String fileName, HttpServletResponse httpServletResponse)
			throws EndlosAPIException {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			File file = new File(path + File.separator + fileName);
			inputStream = new FileInputStream(file);

			if (fileName.contains(FileUploadType.PDF.getName()) || fileName.contains(FileUploadType.PPTX.getName())
					|| fileName.contains(FileUploadType.PPT.getName())) {
				FileCopyUtils.copy(inputStream, httpServletResponse.getOutputStream());
			} else {
				byte[] filebytes = IOUtils.toByteArray(inputStream);
				outputStream = httpServletResponse.getOutputStream();
				outputStream.write(filebytes);
			}
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
				}
			}
		}
	}

	public static void downloadCloudImage(String filePath, MachineModel machineModel) throws EndlosAPIException {
		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "dev";
		}
		Storage storage = StorageOptions.getDefaultInstance().getService();
		Bucket bucket = null;
		if (machineModel != null) {
			bucket = storage.get(machineModel.getCustomerModel().getName().toLowerCase() + "-" + activeProfile);
		} else {
			bucket = storage.get("customer_logo_" + activeProfile);
		}
		if (bucket == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		Blob blob = bucket.get(filePath);
		if (blob == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		OutputStream outputStream = null;
		try {
			byte[] filebytes = blob.getContent();
			outputStream = WebUtil.getCurrentResponse().getOutputStream();
			outputStream.write(filebytes);
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
				}
			}

		}
		/*//local storage start
		String baseDirectory = "C:\\"; // Set the base directory for your local file system
		String filePath2 = "test.png";

		Path localFilePath = Paths.get(baseDirectory, filePath2);

		if (!Files.exists(localFilePath)) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}

		try (OutputStream outputStream = WebUtil.getCurrentResponse().getOutputStream()) {
			Files.copy(localFilePath, outputStream);
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
		}
		//local storage end*/
	}

	public static void downloadCloudCsv(String filePath) throws EndlosAPIException {
		String activeProfile = System.getProperty("spring.profiles.active");
		if (activeProfile == null) {
			activeProfile = "dev";
		}
		Storage storage = StorageOptions.getDefaultInstance().getService();
		Bucket bucket = storage.get("machine_barcode_" + activeProfile);

		if (bucket == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		Blob blob = bucket.get(filePath);
		if (blob == null) {
			throw new EndlosAPIException(ResponseCode.NO_DATA_FOUND.getCode(), ResponseCode.NO_DATA_FOUND.getMessage());
		}
		OutputStream outputStream = null;
		try {
			byte[] fileBytes = blob.getContent();

			HttpServletResponse response = WebUtil.getCurrentResponse();
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + blob.getName());

			outputStream = response.getOutputStream();
			outputStream.write(fileBytes);

		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(), ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());

		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_DOWNLOAD_FILE.getMessage());
				}
			}
		}
	}
}