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
package com.intentlabs.common.notification.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.modelenums.ModelEnum;
import com.intentlabs.common.notification.model.EmailContentModel;
import com.intentlabs.common.notification.model.NotificationModel;
import com.intentlabs.common.notification.model.TransactionalEmailModel;
import com.intentlabs.common.notification.service.EmailContentService;
import com.intentlabs.common.notification.service.TransactionalEmailService;
import com.intentlabs.common.util.DateUtility;

/**
 * This enum specifies the list of notification event and triggers
 * email/sms/website base notification.
 * 
 * @author Nirav.Shah
 * @since 23/05/2020
 */
public enum NotificationEnum implements ModelEnum {

	MASTER_ADMIN_USER_CREATE(1, "Create master admin", "emailScreenCreateMasterAdmin") {
		@Override
		public void sendNotification(NotificationModel notificationModel, Map<String, String> dynamicFields) {
			if (notificationModel.isEmail()) {
				sendEmail(dynamicFields, NotificationEnum.MASTER_ADMIN_USER_CREATE);
			}
			if (notificationModel.isPush()) {

			}
		}
	},
	USER_RESET_PASSWORD(2, "Reset Password", "emailScreenResetPassword") {
		@Override
		public void sendNotification(NotificationModel notificationModel, Map<String, String> dynamicFields) {
			if (notificationModel.isEmail()) {
				sendEmail(dynamicFields, NotificationEnum.USER_RESET_PASSWORD);
			}
			if (notificationModel.isPush()) {

			}
		}
	},
	RESEND_VERIFICATION_CODE(3, "Resend Verification Code", "resendVerificationCode") {
		@Override
		public void sendNotification(NotificationModel notificationModel, Map<String, String> dynamicFields) {
			if (notificationModel.isEmail()) {
				sendEmail(dynamicFields, NotificationEnum.RESEND_VERIFICATION_CODE);
			}
			if (notificationModel.isPush()) {

			}
		}
	},
	CHANGE_LOCATION(4, "Location Change", "emailScreenChangeLocationMasterAdmin") {
		@Override
		public void sendNotification(NotificationModel notificationModel, Map<String, String> dynamicFields) {
			if (notificationModel.isEmail()) {
				sendEmail(dynamicFields, NotificationEnum.CHANGE_LOCATION);
			}
			if (notificationModel.isPush()) {

			}
		}
	};

	@Component
	public static class EmailContentServiceInjector {
		@Autowired
		private EmailContentService emailContentService;

		@PostConstruct
		public void postConstruct() {
			for (NotificationEnum notificationEnum : EnumSet.allOf(NotificationEnum.class))
				notificationEnum.setEmailContentService(emailContentService);
		}
	}

	@Component
	public static class TransactionEmailServiceInjector {
		@Autowired
		private TransactionalEmailService transactionalEmailService;

		@PostConstruct
		public void postConstruct() {
			for (NotificationEnum notificationEnum : EnumSet.allOf(NotificationEnum.class))
				notificationEnum.setTransactionEmailService(transactionalEmailService);
		}
	}

	private final Integer id;
	private final String name;
	private final String screenName;
	public static EmailContentService emailContentService;
	public static TransactionalEmailService transactionalEmailService;

	public static final Map<Integer, NotificationEnum> MAP = new HashMap<>();

	static {
		for (NotificationEnum communicationTriggerEnum : values()) {
			MAP.put(communicationTriggerEnum.getId(), communicationTriggerEnum);
		}
	}

	NotificationEnum(Integer id, String name, String screenName) {
		this.id = id;
		this.name = name;
		this.screenName = screenName;
	}

	public void setEmailContentService(EmailContentService emailContentService) {
		NotificationEnum.emailContentService = emailContentService;
	}

	public void setTransactionEmailService(TransactionalEmailService transactionalEmailService) {
		NotificationEnum.transactionalEmailService = transactionalEmailService;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public static NotificationEnum fromId(Integer id) {
		return MAP.get(id);
	}

	public abstract void sendNotification(NotificationModel notificationModel, Map<String, String> dynamicFields);

	/**
	 * This method is used to fetch email content and replace the content with
	 * dynamic data and insert into a table.
	 * 
	 * @param dynamicFields
	 * @param notificationEnum
	 */
	private static void sendEmail(Map<String, String> dynamicFields, NotificationEnum notificationEnum) {
		EmailContentModel emailContentModel = emailContentService
				.findByNotification(NotificationModel.getMAP().get(Long.valueOf(notificationEnum.getId())));
		if (emailContentModel == null) {
			LoggerService.error("Unable to find email template for " + notificationEnum.getName() + " trigger");
			return;
		}
		StrSubstitutor sub = new StrSubstitutor(dynamicFields);
		String content = sub.replace(emailContentModel.getContent());
		TransactionalEmailModel transactionalEmailModel = new TransactionalEmailModel(
				emailContentModel.getEmailAccountId(), dynamicFields.get(CommunicationFields.EMAIL_TO.getName()),
				emailContentModel.getEmailCc(), emailContentModel.getEmailBcc(), emailContentModel.getSubject(),
				content, EmailStatusEnum.NEW.getId(), dynamicFields.get(CommunicationFields.ATTACHMENT_PATH.getName()),
				DateUtility.getCurrentEpoch());
		transactionalEmailService.create(transactionalEmailModel);
	}
}