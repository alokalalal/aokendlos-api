package com.intentlabs.endlos.machine.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.machine.view.PLCConfigurationView;

public interface PLCConfigurationOperation extends BaseOperation<PLCConfigurationView> {
	/**
	 * 
	 * @param plcConfigurationView
	 * @return
	 * @throws EndlosAPIException
	 */
	Response doUpdatePlcConfiguration(PLCConfigurationView plcConfigurationView) throws EndlosAPIException;

}
