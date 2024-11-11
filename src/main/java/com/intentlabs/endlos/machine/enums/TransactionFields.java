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
package com.intentlabs.endlos.machine.enums;

import java.util.HashMap;
import java.util.Map;

import com.intentlabs.common.modelenums.ModelEnum;

/**
 * This is enum type of all email common fields.
 * 
 * @author Vyoma.Mehta
 * @since 24/04/2020
 */
public enum TransactionFields implements ModelEnum {

	TRANSACTION_ID(1, "transactionId"),PAT_BOTTLE_COUNT(2, "patBottleCount"),PAT_BOTTLE_VALUE(3, "patBottleValue"),
	ALU_BOTTLE_COUNT(4, "aluBottleCount"),ALU_BOTTLE_VALUE(5, "aluBottleValue"),GLASS_BOTTLE_COUNT(6, "glassBottleCount"),GLASS_BOTTLE_VALUE(7, "glassBottleValue"),
	TOTAL_INSERTED_BOTTLE_COUNT(8, "totalInsertedBottleCount"),TOTAL_VALUE(9, "totalValue"),DATE(10, "date"),TIME(11, "time"),MACHINE_ID(12, "machineId"),
	PATH(13, "path"),BARCODE(14, "barcode");
	
	private final Integer id;
	private final String name;

	public static final Map<Integer, TransactionFields> MAP = new HashMap<>();

	static {
		for (TransactionFields applicationFields : values()) {
			MAP.put(applicationFields.getId(), applicationFields);
		}
	}

	TransactionFields(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public static TransactionFields fromId(Integer id) {
		return MAP.get(id);
	}
}