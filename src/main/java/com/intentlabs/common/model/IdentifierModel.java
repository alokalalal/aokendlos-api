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
package com.intentlabs.common.model;

/**
 * This is identifier interface which maps primary key of table to model.
 * @author Nirav.Shah
 * @since 25/12/2019
 */
public abstract class IdentifierModel implements Model {

	private static final long serialVersionUID = -7471747631485155136L;
	
	private Long id;
	
	public IdentifierModel() {
		super();
	}
	public IdentifierModel(Long id) {
		super();
		this.id = id;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
