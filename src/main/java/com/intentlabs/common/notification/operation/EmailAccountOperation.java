package com.intentlabs.common.notification.operation;

import com.intentlabs.common.notification.view.EmailAccountView;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;

/**
 * 
 * @author Nisha.Panchal
 * 
 * @since 17/07/2018
 *
 */
public interface EmailAccountOperation extends BaseOperation<EmailAccountView> {
	/**
	 * This method is used to prepare a key value view of email account.
	 * 
	 * @return
	 */
	Response doDropdown();
}
