/* Copyright -2019 @intentlabs
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.logger.LoggerService;

/**
 * All common operation will be added here.
 * 
 * @author Nirav.Shah
 * @since 27/12/2019
 *
 */
public class Utility {

	private Utility() {
		//
	}

	public static SecureRandom secureRandom = new SecureRandom();

	/**
	 * This method is used to generate an alpha numeric random number based on given
	 * length.
	 * 
	 * @param otpLength
	 * @return String number
	 */
	public static String generateToken(int tokenLength) {
		return RandomStringUtils.randomAlphanumeric(tokenLength);
	}

	/**
	 * This method is used to generate a unique id and replace all the hypen in
	 * generated UUID.
	 * 
	 * @return
	 */
	public static String generateUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * This method is used to read excel file which has .xls format.
	 * 
	 * @param file Excel file
	 * @return List<{@link ExcelRow}>
	 * @throws IOException
	 */
	public static List<ExcelRow> readBinaryFormatFile(File file) throws IOException {
		FileInputStream fileInputStream = null;
		List<ExcelRow> excelRowList = new ArrayList<>();
		try {
			fileInputStream = new FileInputStream(file);

			POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fileInputStream);

			HSSFWorkbook myWorkBook = new HSSFWorkbook(poifsFileSystem);
			HSSFSheet mySheet = myWorkBook.getSheetAt(0);

			Iterator rowIter = mySheet.rowIterator();

			while (rowIter.hasNext()) {
				HSSFRow myRow = (HSSFRow) rowIter.next();
				if (myRow.getRowNum() == 0) {
					continue;
				}
				Iterator cellIter = myRow.cellIterator();
				ExcelRow excelRow = new ExcelRow();
				while (cellIter.hasNext()) {
					HSSFCell myCell = (HSSFCell) cellIter.next();
					switch (myCell.getCellNum()) {
					case 0:
						excelRow.setFirstCell(myCell.toString());
						break;
					case 1:
						excelRow.setSecondCell(myCell.toString());
						break;
					default:
						break;
					}
				}
				excelRowList.add(excelRow);
			}
			return excelRowList;
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

	/**
	 * This method is used to read excel file which has .xlsx format.
	 * 
	 * @param file
	 * @param headers
	 * @return
	 * @throws EndlosAPIException
	 */
	public static List<ExcelRow> readExcelFormat(File file, String[] headers) throws EndlosAPIException {
		FileInputStream fileInputStream = null;
		List<ExcelRow> excelRowList = new ArrayList<>();
		try {
			fileInputStream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() == 0) {
					Iterator<Cell> cellIteratorHeader = row.cellIterator();
					int columnCount = 0;
					while (cellIteratorHeader.hasNext()) {
						Cell cell = cellIteratorHeader.next();
						columnCount = columnCount + 1;
					}
					if (columnCount != headers.length) {
						throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
								ResponseCode.INVALID_FILE_FORMAT.getMessage());
					}
					continue;
				}
				if (row.getRowNum() == 0) {
					continue;
				}
				if (isRowEmpty(row)) {
					continue;
				}
				ExcelRow excelRow = new ExcelRow();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 0:
						checkType(cell);
						excelRow.setFirstCell(cell.toString());
						break;
					case 1:
						checkType(cell);
						excelRow.setSecondCell(cell.toString());
						break;
					case 2:
						checkType(cell);
						excelRow.setThirdCell(cell.toString());
						break;
					case 3:
						checkType(cell);
						excelRow.setFourCell(cell.toString());
						break;
					case 4:
						checkType(cell);
						excelRow.setFiveCell(cell.toString());
						break;
					case 5:
						checkType(cell);
						excelRow.setSixCell(cell.toString());
						break;
					case 6:
						checkType(cell);
						excelRow.setSevenCell(cell.toString());
						break;
					case 7:
						checkType(cell);
						excelRow.setEightCell(cell.toString());
						break;
					case 8:
						checkType(cell);
						excelRow.setNineCell(cell.toString());
						break;
					case 9:
						checkType(cell);
						excelRow.setTenCell(cell.toString());
						break;
					case 10:
						checkType(cell);
						excelRow.setElevenCell(cell.toString());
						break;
					case 11:
						checkType(cell);
						excelRow.setTwelveCell(cell.toString());
						break;
					case 12:
						checkType(cell);
						excelRow.setThirteenCell(cell.toString());
						break;
					case 13:
						checkType(cell);
						excelRow.setFourteenCell(cell.toString());
						break;
					case 14:
						checkType(cell);
						excelRow.setFifteenCell(cell.toString());
						break;

					case 15:
						checkType(cell);
						excelRow.setSixteenCell(cell.toString());
						break;
					}
				}
				excelRowList.add(excelRow);
			}
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
				}
			}
		}
		return excelRowList;
	}

	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

	private static void checkType(Cell cell) throws EndlosAPIException {
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				if (DateUtility.stringToDateForSpecificFormat(cell.toString()) == null) {
					throw new EndlosAPIException(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
							ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
				}
			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}
		}
	}

	public static Boolean validateExcelTemplate(File file, String[] headers) throws EndlosAPIException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() == 0) {
					Iterator<Cell> cellIteratorHeader = row.cellIterator();
					int columnCount = 0;
					while (cellIteratorHeader.hasNext()) {
						Cell cell = cellIteratorHeader.next();
						if (!headers[columnCount].equals(cell.getStringCellValue())) {
							throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
									cell.getStringCellValue() + ResponseCode.INVALID_FILE_FORMAT.getMessage());
						}
						columnCount = columnCount + 1;
					}
					if (columnCount != headers.length) {
						// dont remove unused column header from template - (This message should go to
						// end user)
						throw new EndlosAPIException(ResponseCode.INVALID_FILE_FORMAT.getCode(),
								ResponseCode.INVALID_FILE_FORMAT.getMessage());
					}
					break;
				}
			}
			return true;
		} catch (IOException ioException) {
			LoggerService.exception(ioException);
			throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
					ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ioException) {
					LoggerService.exception(ioException);
					throw new EndlosAPIException(ResponseCode.UNABLE_TO_UPLOAD_FILE.getCode(),
							ResponseCode.UNABLE_TO_UPLOAD_FILE.getMessage());
				}
			}
		}

	}

	/**
	 * This method is used to generate a UUID.
	 * 
	 * @return
	 */
	public static String generateUuidWithHash() {
		return UUID.randomUUID().toString();
	}

	/**
	 * This method is used to generating random Token.
	 * 
	 * @param otpLength
	 * @return String number
	 */
	public static String generateOTP(int tokenLength) {
		return RandomStringUtils.randomNumeric(tokenLength);
	}

	public static boolean isValidPattern(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	public static String indianFormat(String value) {
		String decimal = "";
		if (value.contains(".")) {
			decimal = value.substring(value.indexOf("."), value.length());
			value = value.substring(0, value.indexOf("."));
		}

		value = value.replace(",", "");
		char lastDigit = value.charAt(value.length() - 1);
		String result = "";
		int len = value.length() - 1;
		int nDigits = 0;

		for (int i = len - 1; i >= 0; i--) {
			result = value.charAt(i) + result;
			nDigits++;
			if (((nDigits % 2) == 0) && (i > 0)) {
				result = "," + result;
			}
		}
		return (result + lastDigit + decimal);
	}
	
	public static BigDecimal formateBigDecimal(BigDecimal value, Integer limit) {
		if (value == null) {
			return null;
		}
		return value.setScale(limit, RoundingMode.HALF_UP);
	}
}