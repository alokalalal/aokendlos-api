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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.MonitoredResource;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import com.google.cloud.logging.Payload;
import com.google.cloud.logging.Payload.StringPayload;
import com.google.cloud.logging.Severity;

@Component
public class ToBeDeletedGoogleLoggerService {
    @Value("${send.google.log}")
    private boolean sendGoogleLog;

    static String logName = "test-log";

    /**
     * This method is used to log exception.
     *
     * @param throwable
     */
    public void exception(Throwable throwable) {
        if (sendGoogleLog) {
            try (Logging logging = LoggingOptions.getDefaultInstance().getService()) {
                Map<String, Object> jsonPayload = new HashMap<>();
                jsonPayload.put("message", ExceptionUtils.getStackTrace(throwable));

                LogEntry entry = LogEntry.newBuilder(Payload.JsonPayload.of(jsonPayload)).setSeverity(Severity.CRITICAL)
                        .setLogName(logName).setResource(MonitoredResource.newBuilder("global").build()).build();

                logging.write(Collections.singleton(entry));
                logging.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to log error messages.
     *
     * @param className
     * @param transactionName
     * @param message
     */
    public static void error(String className, String transactionName, String message) {
        // The name of the log to write to
        Notification textPayload = Notification.create(className, transactionName, message);

        // Instantiates a client
        try (Logging logging = LoggingOptions.getDefaultInstance().getService()) {

            LogEntry entry = LogEntry.newBuilder(StringPayload.of(textPayload.toString())).setSeverity(Severity.ERROR)
                    .setLogName(logName).setResource(MonitoredResource.newBuilder("global").build()).build();

            // Writes the log entry asynchronously
            logging.write(Collections.singleton(entry));

            // Optional - flush any pending log entries just before Logging is closed
            logging.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to log error messages.
     *
     * @param message
     */
    public static void error(String message) {

        // The name of the log to write to
        Notification textPayload = Notification.create(message);

        // Instantiates a client
        try (Logging logging = LoggingOptions.getDefaultInstance().getService()) {

            LogEntry entry = LogEntry.newBuilder(StringPayload.of(textPayload.toString())).setSeverity(Severity.ERROR)
                    .setLogName(logName).setResource(MonitoredResource.newBuilder("global").build()).build();

            // Writes the log entry asynchronously
            logging.write(Collections.singleton(entry));

            // Optional - flush any pending log entries just before Logging is closed
            logging.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to log information messages.
     *
     * @param className
     * @param transactionName
     * @param message
     */
    public static void info(String className, String transactionName, String message) {
        Notification textPayload = Notification.create(className, transactionName, message);

        // Instantiates a client
        try (Logging logging = LoggingOptions.getDefaultInstance().getService()) {

            LogEntry entry = LogEntry.newBuilder(StringPayload.of(textPayload.toString())).setSeverity(Severity.INFO)
                    .setLogName(logName).setResource(MonitoredResource.newBuilder("global").build()).build();

            // Writes the log entry asynchronously
            logging.write(Collections.singleton(entry));

            // Optional - flush any pending log entries just before Logging is closed
            logging.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
