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
package com.intentlabs.common.logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This class contains all sync method configurations.
 *
 * @author Nirav.Shah
 * @since 15/05/2019
 */
@Service
public class GoogleLogConfiguration {

    private static boolean sendGoogleLog;

    public static boolean isSendGoogleLog() {
        return sendGoogleLog;
    }

    @Value("${send.google.log}")
    public void setSendGoogleLog(boolean log) {
        sendGoogleLog = log;
    }

}