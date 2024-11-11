/**
 * 
 */
package com.intentlabs.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import com.intentlabs.common.logger.LoggerService;

/**
 * All Date related operation will be performed in this class.
 * 
 * @author Dhruvang
 *
 */
public class DateUtility {

	public static String DD_MM_YYYY = "dd/MM/yyyy";
	public static String DD_MM_YYYY_HH_MM_A = "dd/MM/yyyy hh:mm a";
	public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static String YYYY_MM_DD_T_HH_MM_SS_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static String HH_MM_A = "hh:mm a";

	private DateUtility() {
	}

	/**
	 * To get the local date & time from epoch.
	 * 
	 * @param date
	 * @param timeZoneModel
	 * @return
	 */
	// public static LocalDateTime getLocalDateTime(Long date, TimeZoneModel
	// timeZoneModel) {
	// if (timeZoneModel == null || date == null || date == 0l) {
	// return null;
	// }
	// return LocalDateTime.ofInstant(Instant.ofEpochSecond(date),
	// ZoneId.of(timeZoneModel.getTimezone()));
	// }

	public static LocalDate stringToDateForSpecificFormat(String date) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			return LocalDate.parse(date, formatter);
		} catch (Exception exception) {
			LoggerService.exception(exception);
			return null;
		}
	}

	/**
	 * To get the local date from epoch.
	 * 
	 * @param date
	 * @param timeZoneModel
	 * @return
	 */
	// public static LocalDate getLocalDate(Long date, TimeZoneModel
	// timeZoneModel) {
	// if (timeZoneModel == null || date == null || date == 0l) {
	// return null;
	// }
	// LocalDateTime localDateTime =
	// LocalDateTime.ofInstant(Instant.ofEpochSecond(date),
	// ZoneId.of(timeZoneModel.getTimezone()));
	// if (localDateTime == null) {
	// return null;
	// }
	// return localDateTime.toLocalDate();
	// }

	/**
	 * To format the locate date and time according to the format.
	 * 
	 * @param localDateTime
	 * @param format
	 * @return
	 */
	public static String formateLocalDateTime(LocalDateTime localDateTime, String format) {
		return localDateTime.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * Get current epoch.
	 * 
	 * @return
	 */
	public static long getCurrentEpoch() {
		return Instant.now().getEpochSecond();
	}

	/**
	 * Get future epoch.
	 * 
	 * @return
	 */
	public static long getFutureEpoch(int day) {
		return Instant.now().getEpochSecond();
	}

	/**
	 * Get past epoch.
	 * 
	 * @return
	 */
	public static long getPastEpoch(int day) {
		return Instant.now().minus(day, ChronoUnit.DAYS).getEpochSecond();
	}

	/**
	 * Get the epoch after provided days.
	 * 
	 * @param localDateTime
	 * @param day
	 * @param timeZoneModel
	 * @return
	 */
	// public static long getEpochAfterDays(LocalDateTime localDateTime, int
	// day, TimeZoneModel timeZoneModel) {
	// if (timeZoneModel == null || localDateTime == null || day == 0) {
	// return 0;
	// }
	// return
	// localDateTime.plusDays(day).atZone(ZoneId.of(timeZoneModel.getTimezone())).toEpochSecond();
	// }

	/**
	 * To format the string date into localdate.
	 * 
	 * @param date
	 * @param format
	 *            - dd/MM/yyyy
	 * @return
	 */
	public static LocalDate stringToDate(String date, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalDate.parse(date, formatter);
		} catch (Exception exception) {
			LoggerService.exception(exception);
			return null;
		}
	}

	/**
	 * To format the string date & time to localdatetime.
	 * 
	 * @param date
	 * @param format
	 *            - dd/MM/yyyy HH:mm:ss
	 * @return
	 */
	public static LocalDateTime stringToDateTime(String date, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalDateTime.parse(date, formatter);
		} catch (Exception exception) {
			LoggerService.exception(exception);
			return null;
		}
	}

	/**
	 * This method is used to get epoch from local date.
	 * 
	 * @param localDate
	 * @param timeZoneModel
	 * @return
	 */
	// public static long getEpochFromLocalDate(LocalDate localDate,
	// TimeZoneModel timeZoneModel) {
	// return
	// localDate.atStartOfDay(ZoneId.of(timeZoneModel.getTimezone())).toEpochSecond();
	// }

	/**
	 * This method is used to get epoch from local date & time.
	 * 
	 * @param localDateTime
	 * @param timeZoneModel
	 * @return
	 */
	// public static long getEpochFromLocalDateTime(LocalDateTime localDateTime,
	// TimeZoneModel timeZoneModel) {
	// return
	// localDateTime.atZone(ZoneId.of(timeZoneModel.getTimezone())).toEpochSecond();
	// }

	/**
	 * To get the current year
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		return LocalDateTime.now().getYear();
	}

	/**
	 * To get the current month.
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		return LocalDateTime.now().getMonth().getValue();
	}

	/**
	 * To get current month name
	 * 
	 * @return
	 */
	public static String getCurrentMonthName() {
		return LocalDateTime.now().getMonth().name();
	}

	/**
	 * To get current day.
	 * 
	 * @return
	 */
	public static int getCurrentDay() {
		return LocalDateTime.now().getDayOfMonth();
	}

	/**
	 * This method is used to format local date.
	 * 
	 * @param localDate
	 * @param format
	 * @return
	 */
	public static String formateLocalDate(LocalDate localDate, String format) {
		return localDate.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * This methdo is used to get a number days in a month.
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getDaysInMonth(Integer year, Integer month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		return yearMonth.lengthOfMonth();
	}

	// public static Long noOfDaysBetweenDates(Long startDate, Long endDate,
	// TimeZoneModel timeZoneModel) {
	// LocalDateTime startLocalDateTime = getLocalDateTime(startDate,
	// timeZoneModel);
	// LocalDateTime endLocalDateTime = getLocalDateTime(endDate,
	// timeZoneModel);
	// if (startLocalDateTime == null || endLocalDateTime == null) {
	// return null;
	// }
	// return ChronoUnit.DAYS.between(startLocalDateTime, endLocalDateTime);
	// }

	// public static Long noOfNightsBetweenDates(Long startDate, Long endDate,
	// TimeZoneModel timeZoneModel) {
	// LocalDateTime startLocalDateTime = getLocalDateTime(startDate,
	// timeZoneModel);
	// LocalDateTime endLocalDateTime = getLocalDateTime(endDate,
	// timeZoneModel);
	// if (startLocalDateTime == null || endLocalDateTime == null) {
	// return null;
	// }
	// LocalDateTime startOfDay = startLocalDateTime.with(LocalTime.MIN);
	// Long nightCount = 0l;
	// if (startOfDay.isAfter(endLocalDateTime)) {
	// nightCount++;
	// }
	// return nightCount;
	// }

	/**
	 * To format the string time to localtime.
	 * 
	 * @param time
	 * @param format
	 *            hh:mm a
	 * @return
	 */
	public static LocalTime stringToTime(String time, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalTime.parse(time, formatter);
		} catch (Exception exception) {
			LoggerService.exception(exception);
			return null;
		}
	}

	/**
	 * this method will use to convert date to localdatetime.
	 * 
	 * @param date
	 * @param timeZoneModel
	 * @return
	 */
	// public static LocalDateTime convertDateToLocalDateTime(Date date,
	// TimeZoneModel timeZoneModel) {
	// try {
	// return
	// date.toInstant().atZone(ZoneId.of(timeZoneModel.getTimezone())).toLocalDateTime();
	// } catch (Exception exception) {
	// LoggerService.exception(exception);
	// return null;
	// }
	// }

	public static void main(String[] args) {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.withMonth(2);
		localDate = localDate.withYear(2020);
		System.out.println(localDate.withDayOfMonth(1));
		System.out.println(localDate.withDayOfMonth(localDate.lengthOfMonth()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
	}

	/**
	 * Get is reset password valid minutes.
	 *
	 * @return
	 */
	public static boolean isResetPasswordValidMinutes(Long epoch, Long min) {
		Instant epochInstant = Instant.ofEpochSecond(epoch);
		Instant addMinToEpoch = epochInstant.plus(min, ChronoUnit.MINUTES);
		return addMinToEpoch.isBefore(Instant.now());
	}
}