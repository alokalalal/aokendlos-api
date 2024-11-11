/**
 * 
 */
package com.intentlabs.common.util;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.intentlabs.common.setting.enums.LocaleEnum;
import com.intentlabs.common.setting.model.SystemSettingModel;

/**
 * Web request related utility.
 * 
 * @author nirav
 * @since 27/12/2019
 *
 */
public class WebUtil {

	private WebUtil() {
	}

	/**
	 * It is used to get current http servlet request
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getCurrentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	/**
	 * To get the current response object.
	 * 
	 * @return
	 */
	public static HttpServletResponse getCurrentResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
	}

	/**
	 * This method is used to get current language.
	 * 
	 * @return
	 */
	public static LocaleEnum getCurrentLanguage() {
		try {
			Enumeration<Locale> locales = WebUtil.getCurrentRequest().getLocales();
			while (locales.hasMoreElements()) {
				Locale locale = locales.nextElement();
				LocaleEnum tempEnum = LocaleEnum.fromLanguageTag(locale.toLanguageTag());
				if (tempEnum != null && SystemSettingModel.getLocaleSupported().contains(tempEnum.getLanguageTag())) {
					return tempEnum;
				}
			}
			return null;
		} catch (Exception exception) {
			return null;
		}
	}
}