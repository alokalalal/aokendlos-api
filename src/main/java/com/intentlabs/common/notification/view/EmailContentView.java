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
package com.intentlabs.common.notification.view;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.notification.enums.NotificationEnum;
import com.intentlabs.common.validation.InputField;
import com.intentlabs.common.validation.Validator;
import com.intentlabs.common.view.ArchiveView;
import com.intentlabs.common.view.KeyValueView;

/**
 * This class is used to represent emailcontent object in json/in customer
 * response
 * 
 * @author Nirav.Shah
 * @Since 12/07/2018
 */

@JsonInclude(Include.NON_NULL)
public class EmailContentView extends ArchiveView {

	private static final long serialVersionUID = -7632495476077413752L;
	private KeyValueView emailAccountView;
	private String content;
	private String subject;
	private String emailBcc;
	private String emailCc;
	private KeyValueView notificationView;

	public KeyValueView getEmailAccountView() {
		return emailAccountView;
	}

	public void setEmailAccountView(KeyValueView emailAccountView) {
		this.emailAccountView = emailAccountView;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailBcc() {
		return emailBcc;
	}

	public void setEmailBcc(String emailBcc) {
		this.emailBcc = emailBcc;
	}

	public KeyValueView getNotificationView() {
		return notificationView;
	}

	public void setNotificationView(KeyValueView notificationView) {
		this.notificationView = notificationView;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public static void isValid(EmailContentView emailContentView) throws EndlosAPIException {
		if (emailContentView.getEmailAccountView() == null || (emailContentView.getEmailAccountView() != null
				&& emailContentView.getEmailAccountView().getKey() == null)) {
			throw new EndlosAPIException(ResponseCode.EMAIL_ACOUNT_IS_MISSING.getCode(),
					ResponseCode.EMAIL_ACOUNT_IS_MISSING.getMessage());
		}
		if (StringUtils.isBlank(emailContentView.getContent())) {
			throw new EndlosAPIException(ResponseCode.EMAIL_CONTENT_IS_MISSING.getCode(),
					ResponseCode.EMAIL_CONTENT_IS_MISSING.getMessage());
		}
		Validator.STRING.isValid(new InputField("SUBJECT", emailContentView.getSubject(), true, 1000));
		if (emailContentView.getNotificationView() == null || (emailContentView.getNotificationView() != null
				&& emailContentView.getNotificationView().getKey() == null)) {
			throw new EndlosAPIException(ResponseCode.NOTIFICATION_IS_MISSING.getCode(),
					ResponseCode.NOTIFICATION_IS_MISSING.getMessage());
		}
		if (NotificationEnum.fromId(emailContentView.getNotificationView().getKey().intValue()) == null) {
			throw new EndlosAPIException(ResponseCode.NOTIFICATION_IS_INVALID.getCode(),
					ResponseCode.NOTIFICATION_IS_INVALID.getMessage());
		}
	}
}