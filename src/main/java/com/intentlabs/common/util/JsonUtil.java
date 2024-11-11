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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.logger.LoggerService;

/**
 * This class is perform json parsing related operations.
 * 
 * @author Nirav.Shah
 * @since 26/09/2018
 */

public class JsonUtil {

	private JsonUtil() {
	}

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
	}

	/**
	 * To convert json into a pojo.
	 * 
	 * @param json
	 * @param aClass
	 * @return
	 * @throws EndlosAPIException
	 */
	public static <T> T toObject(final byte[] json, Class aClass) throws EndlosAPIException {
		try {
			return (T) mapper.readValue(json, aClass);
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
	}

	public static List<Long> toLongObjectList(String json) throws EndlosAPIException {
		try {
			return Arrays.asList(mapper.readValue(json, Long[].class));
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
	}

	public static String toJsonFromMap(Map<String, ? extends Object> map) throws EndlosAPIException {
		try {
			return mapper.writeValueAsString(map);
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
	}

	public static String toJson(Object aClass) throws EndlosAPIException {
		try {
			return mapper.writeValueAsString(aClass);
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
	}

	public static String getValueOfSpecificKeyFromJsonData(String jsonString, String key)
			throws EndlosAPIException {
		try {
			JsonNode actualObj = mapper.readTree(jsonString);
			if (actualObj != null) {
				JsonNode jsonNode = actualObj.get(key);
				if (jsonNode != null) {
					return jsonNode.toString();
				}
			}
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
		return null;
	}

	public static boolean isValidJSON(final String json) {
		try {
			mapper.readTree(json);
			return true;
		} catch (IOException e) {
			LoggerService.exception(e);
			return false;
		}
	}

	public static <K extends Object, V extends Object> Map<K, V> toSingleMap(String jsonStr)
			throws EndlosAPIException {
		try {
			return mapper.readValue(jsonStr, new TypeReference<HashMap<Object, Object>>() {
			});
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
	}

	public static <K extends Object, V extends Object> Map<K, V> toMap(String jsonStr) throws EndlosAPIException {
		try {
			return mapper.readValue(jsonStr, new TypeReference<HashMap<String, Map<String, String>>>() {
			});
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
	}

	/**
	 * To convert json into a pojo.
	 * 
	 * @param json
	 * @param aClass
	 * @return
	 * @throws EndlosAPIException
	 */
	public static <T> T toObject(String json, Class aClass) throws EndlosAPIException {
		try {
			return (T) mapper.readValue(json, aClass);
		} catch (IOException e) {
			LoggerService.exception(e);
			throw new EndlosAPIException(ResponseCode.INVALID_JSON.getCode(),
					ResponseCode.INVALID_JSON.getMessage());
		}
	}
}