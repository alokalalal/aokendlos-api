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
package com.intentlabs.endlos.machine.service;

import com.intentlabs.common.service.BaseService;
import com.intentlabs.endlos.machine.model.MachineCapacityModel;

/**
 * @author Hemil Shah.
 * @version 1.0ka
 * @since 13/06/2023
 */
public interface MachineCapacityService extends BaseService<MachineCapacityModel> {

	String MACHINE_CAPACITY_MODLE = "machineCapacityModel";
}