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
package com.intentlabs.common.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This is a transaction email configuration which specify when to run this
 * scheduler and and how many record to be processed at a time.
 * 
 * @author Nirav.Shah
 * @since 15/05/2019
 */

@ComponentScan
@Configuration
public class TransactionalEmailConfiguration {

	// In seconds
	@Value("${transaction.email.runTime}")
	private int teScheduleRunTime;

	@Value("${transaction.email.recordToBeProceesedAtTime}")
	private int teRecordToBeProceesedAtTime;

	public int getTeScheduleRunTime() {
		return teScheduleRunTime;
	}

	public void setTeScheduleRunTime(int teScheduleRunTime) {
		this.teScheduleRunTime = teScheduleRunTime;
	}

	public int getTeRecordToBeProceesedAtTime() {
		return teRecordToBeProceesedAtTime;
	}

	public void setTeRecordToBeProceesedAtTime(int teRecordToBeProceesedAtTime) {
		this.teRecordToBeProceesedAtTime = teRecordToBeProceesedAtTime;
	}
}