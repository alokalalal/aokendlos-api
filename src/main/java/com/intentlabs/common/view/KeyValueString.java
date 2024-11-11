/*******************************************************************************
 * Copyright -2018 @Emotome
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
package com.intentlabs.common.view;

/**
 * This is Key value view which used to send/map dynamic key value json.
 * 
 * @author Nirav.Shah
 * @since 02/08/2018
 */
public class KeyValueString implements View {

	private static final long serialVersionUID = -4330905917750411429L;

	private String value;
	private String key;

	public KeyValueString() {
		super();
	}

	public KeyValueString(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static KeyValueString create(String key, String value) {
		return new KeyValueString(key, value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}

	public void setId(String key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KeyValueString other = (KeyValueString) obj;
		if (getKey() == null) {
			if (other.getKey() != null) {
				return false;
			}
		} else if (!getKey().equals(other.getKey())) {
			return false;
		}
		return true;
	}
}
