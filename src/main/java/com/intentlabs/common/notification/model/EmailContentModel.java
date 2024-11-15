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
package com.intentlabs.common.notification.model;

import com.intentlabs.common.model.AuditableModel;
import com.intentlabs.common.model.IdentifierModel;

/**
 * This is EmailContentModel model which maps EmailContentModel table to class.
 * 
 * @author Vishwa.Shah
 * @since 14/06/2018
 *
 */
public class EmailContentModel extends AuditableModel {

	private static final long serialVersionUID = 4365449160483605482L;

	private Long emailAccountId;
	private String subject;
	private String content;
	private String emailBcc;
	private String emailCc;
	private NotificationModel notificationModel;

	public Long getEmailAccountId() {
		return emailAccountId;
	}

	public void setEmailAccountId(Long emailAccountId) {
		this.emailAccountId = emailAccountId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmailBcc() {
		return emailBcc;
	}

	public void setEmailBcc(String emailBcc) {
		this.emailBcc = emailBcc;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public NotificationModel getNotificationModel() {
		return notificationModel;
	}

	public void setNotificationModel(NotificationModel notificationModel) {
		this.notificationModel = notificationModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifierModel other = (IdentifierModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}