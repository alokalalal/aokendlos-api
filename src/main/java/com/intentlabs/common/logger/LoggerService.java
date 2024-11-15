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
/**
 * This class is used to log information base on their type.
 * Logging level Hierarchy is as below
 * OFF, FATAL, ERROR, WARN, INFO, DEBUG, TRACE, ALL.
 *
 * 1. OFF - No logging will be done.
 * 2. FATAL - Only fatal logs will be generated in log appender(file/console).
 * 3. ERROR - FATAL & ERROR logs will be generated.
 * 4. WARN - WARN, ERROR & FATAL logs will be generated.
 * 5. INFO - INFO, WARN, ERROR & FATAL logs will be generated.
 * 6. DEBUG - DEBUG, INFO, WARN, ERROR & FATAL logs will be generated.
 * 7. TRACE - TRACE, DEBUG, INFO, WARN, ERROR & FATAL logs will be generated.
 * 8. ALL - ALL logs will be generated.
 *
 *
 * Need to set this property in at run time to enable async logger.
 * -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector.
 *
 * Log4j2 has a shutdown hook (for non-web applications) that takes care of waiting for the
 * background thread to process any events still in the queue.
 * So the best thing to do is to not stop the appenders when they are still being used.
 * Let log4j2 take care of the cleanup.
 *
 *
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.cloud.MonitoredResource;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import com.google.cloud.logging.Payload;
import com.google.cloud.logging.Payload.StringPayload;
import com.google.cloud.logging.Severity;

@Component
public class LoggerService {

	// 1. Exception will be logged as FATAL logger Type.
	private static final Logger FATAL = LogManager.getLogger("exception");

	// 2. Error/custom generated exception will be logged here.
	private static final Logger ERROR = LogManager.getLogger("error");

	// 3. All performance related logs (Time taken by each request) will be logged
	// here.
	private static final Logger INFO = LogManager.getLogger("access");

	// 4. Debug logs will stored in this file. This logs will be useful in
	// development.
	private static final Logger DEBUG = LogManager.getLogger("debug");
	static String logName = "test-log";

	/**
	 * This method is used to log exception.
	 *
	 * @param throwable
	 */
	public static void exception(Throwable throwable) {
		if (GoogleLogConfiguration.isSendGoogleLog()) {
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
		} else {
			FATAL.fatal(Notification.create(), throwable);
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
		if (GoogleLogConfiguration.isSendGoogleLog()) {
			Notification textPayload = Notification.create(className, transactionName, message);

			// Instantiates a client
			try (Logging logging = LoggingOptions.getDefaultInstance().getService()) {

				LogEntry entry = LogEntry.newBuilder(StringPayload.of(textPayload.toString()))
						.setSeverity(Severity.ERROR).setLogName(logName)
						.setResource(MonitoredResource.newBuilder("global").build()).build();

				// Writes the log entry asynchronously
				logging.write(Collections.singleton(entry));

				// Optional - flush any pending log entries just before Logging is closed
				logging.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ERROR.error(Notification.create(className, transactionName, message));
		}
	}

	/**
	 * This method is used to log error messages.
	 *
	 * @param message
	 */
	public static void error(String message) {
		if (GoogleLogConfiguration.isSendGoogleLog()) {

			// The name of the log to write to
			Notification textPayload = Notification.create(message);

			// Instantiates a client
			try (Logging logging = LoggingOptions.getDefaultInstance().getService()) {

				LogEntry entry = LogEntry.newBuilder(StringPayload.of(textPayload.toString()))
						.setSeverity(Severity.ERROR).setLogName(logName)
						.setResource(MonitoredResource.newBuilder("global").build()).build();

				// Writes the log entry asynchronously
				logging.write(Collections.singleton(entry));

				// Optional - flush any pending log entries just before Logging is closed
				logging.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			ERROR.error(Notification.create(message));
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
		if (GoogleLogConfiguration.isSendGoogleLog()) {

			Notification textPayload = Notification.create(className, transactionName, message);

			// Instantiates a client
			try (Logging logging = LoggingOptions.getDefaultInstance().getService()) {

				LogEntry entry = LogEntry.newBuilder(StringPayload.of(textPayload.toString()))
						.setSeverity(Severity.INFO).setLogName(logName)
						.setResource(MonitoredResource.newBuilder("global").build()).build();

				// Writes the log entry asynchronously
				logging.write(Collections.singleton(entry));

				// Optional - flush any pending log entries just before Logging is closed
				logging.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			INFO.info(Notification.create(className, transactionName, message));
		}
	}

	/**
	 * This method is used to log debug messages.
	 *
	 * @param className
	 * @param transactionName
	 * @param message
	 */
	public static void debug(String className, String transactionName, String message) {
		DEBUG.debug(Notification.create(className, transactionName, message));
	}
}
