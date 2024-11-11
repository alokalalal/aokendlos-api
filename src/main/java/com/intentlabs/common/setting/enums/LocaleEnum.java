/*******************************************************************************
 * Copyright -2019 @Intentlbas
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
package com.intentlabs.common.setting.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum is used to maintain locale variable.
 * 
 * @author Vishwa.Shah
 * @since 10/11/2020
 */
public enum LocaleEnum {

	TI_ET("ti-ET", "Tigrinya", "Ethiopia"), TA_SG("ta-SG", "Tamil", "Singapore"), EN_NU("en-NU", "English", "Niue"),
	ZH_HANS_SG("zh-Hans-SG", "Chinese", "Singapore"), FF_ADLM_LR("ff-Adlm-LR", "Fulah", "Liberia"),
	EN_JM("en-JM", "English", "Jamaica"), SD_ARAB("sd-Arab", "Sindhi", ""), ES_BO("es-BO", "Spanish", "Bolivia"),
	DZ_BT("dz-BT", "Dzongkha", "Bhutan"), DSB_DE("dsb-DE", "Lower Sorbian", "Germany"),
	EN_LR("en-LR", "English", "Liberia"), AR_TD("ar-TD", "Arabic", "Chad"),
	FF_LATN_MR("ff-Latn-MR", "Fulah", "Mauritania"), SW_UG("sw-UG", "Swahili", "Uganda"),
	TK_TM("tk-TM", "Turkmen", "Turkmenistan"), SR_CYRL_ME("sr-Cyrl-ME", "Serbian", "Montenegro"),
	AR_EG("ar-EG", "Arabic", "Egypt"), YO_NG("yo-NG", "Yoruba", "Nigeria"), SE_NO("se-NO", "Northern Sami", "Norway"),
	VO_001("vo-001", "Volapük", "World"), EN_PW("en-PW", "English", "Palau"), PL_PL("pl-PL", "Polish", "Poland"),
	SR_CS("sr-CS", "Serbian", "Serbia and Montenegro"), NE_IN("ne-IN", "Nepali", "India"),
	AR_EH("ar-EH", "Arabic", "Western Sahara"), BS_LATN_BA("bs-Latn-BA", "Bosnian", "Bosnia & Herzegovina"),
	NDS_DE("nds-DE", "Low German", "Germany"), NB_SJ("nb-SJ", "Norwegian Bokmål", "Svalbard & Jan Mayen"),
	ES_US("es-US", "Spanish", "United States"), EN_US_POSIX("en-US-POSIX", "English", "United States"),
	PT_MO("pt-MO", "Portuguese", "Macao SAR China"), ZH_HANS("zh-Hans", "Chinese", ""),
	LB_LU("lb-LU", "Luxembourgish", "Luxembourg"), SO_KE("so-KE", "Somali", "Kenya"),
	DJE_NE("dje-NE", "Zarma", "Niger"), FF_LATN_GH("ff-Latn-GH", "Fulah", "Ghana"),
	FR_PM("fr-PM", "French", "St Pierre & Miquelon"), AR_KM("ar-KM", "Arabic", "Comoros"),
	NN_NO_1("nn-NO", "Norwegian", "Norway"), AGQ_CM("agq-CM", "Aghem", "Cameroon"), TR_TR("tr-TR", "Turkish", "Turkey"),
	AR_MR("ar-MR", "Arabic", "Mauritania"), ES_DO("es-DO", "Spanish", "Dominican Republic"),
	SU_LATN("su-Latn", "Sundanese", ""), KL_GL("kl-GL", "Kalaallisut", "Greenland"), EN_NR("en-NR", "English", "Nauru"),
	EN_AU("en-AU", "English", "Australia"), EN_CY("en-CY", "English", "Cyprus"), TI_ER("ti-ER", "Tigrinya", "Eritrea"),
	NUS_SS("nus-SS", "Nuer", "South Sudan"), EN_RW("en-RW", "English", "Rwanda"),
	LN_CD("ln-CD", "Lingala", "Congo - Kinshasa"), NNH_CM("nnh-CM", "Ngiemboon", "Cameroon"),
	AR_IL("ar-IL", "Arabic", "Israel"), OR_IN("or-IN", "Odia", "India"), AZ_LATN("az-Latn", "Azerbaijani", ""),
	AR_SY("ar-SY", "Arabic", "Syria"), KSF_CM("ksf-CM", "Bafia", "Cameroon"), TT_RU("tt-RU", "Tatar", "Russia"),
	OM_KE("om-KE", "Oromo", "Kenya"), EN_AS("en-AS", "English", "American Samoa"), FR_VU("fr-VU", "French", "Vanuatu"),
	ZH_TW("zh-TW", "Chinese", "Taiwan"), SD_IN("sd-IN", "Sindhi", "India"), PT_MZ("pt-MZ", "Portuguese", "Mozambique"),
	FR_NE("fr-NE", "French", "Niger"), KSB_TZ("ksb-TZ", "Shambala", "Tanzania"), EN_JE("en-JE", "English", "Jersey"),
	LN_CF("ln-CF", "Lingala", "Central African Republic"), EN_CX("en-CX", "English", "Christmas Island"),
	EN_AT("en-AT", "English", "Austria"), SR_CYRL("sr-Cyrl", "Serbian", ""),
	YUE_HANS_CN("yue-Hans-CN", "Cantonese", "China"), FF_ADLM("ff-Adlm", "Fulah", ""),
	KN_IN("kn-IN", "Kannada", "India"), EN_TZ("en-TZ", "English", "Tanzania"), EN_PR("en-PR", "English", "Puerto Rico"),
	FR_NC("fr-NC", "French", "New Caledonia"), GD_GB("gd-GB", "Scottish Gaelic", "United Kingdom"),
	FR_CM("fr-CM", "French", "Cameroon"), PA_GURU_IN("pa-Guru-IN", "Punjabi", "India"),
	TEO_UG("teo-UG", "Teso", "Uganda"), EN_NL("en-NL", "English", "Netherlands"),
	LN_CG("ln-CG", "Lingala", "Congo - Brazzaville"), MR_IN("mr-IN", "Marathi", "India"),
	EL_CY("el-CY", "Greek", "Cyprus"), KU_TR("ku-TR", "Kurdish", "Turkey"), LRC_IR("lrc-IR", "Northern Luri", "Iran"),
	GSW_FR("gsw-FR", "Swiss German", "France"), ES_HN("es-HN", "Spanish", "Honduras"),
	HU_HU("hu-HU", "Hungarian", "Hungary"), FF_SN("ff-SN", "Fulah", "Senegal"),
	SQ_MK("sq-MK", "Albanian", "North Macedonia"), SR_CYRL_BA("sr-Cyrl-BA", "Serbian", "Bosnia & Herzegovina"),
	ET_EE("et-EE", "Estonian", "Estonia"), AR_OM("ar-OM", "Arabic", "Oman"),
	FY_NL("fy-NL", "Western Frisian", "Netherlands"), TR_CY("tr-CY", "Turkish", "Cyprus"),
	UZ_LATN_UZ("uz-Latn-UZ", "Uzbek", "Uzbekistan"), LRC_IQ("lrc-IQ", "Northern Luri", "Iraq"),
	DUA_CM("dua-CM", "Duala", "Cameroon"), DE_IT("de-IT", "German", "Italy"), VAI_VAII("vai-Vaii", "Vai", ""),
	FR_TN("fr-TN", "French", "Tunisia"), SR_RS("sr-RS", "Serbian", "Serbia"), DE_CH("de-CH", "German", "Switzerland"),
	FR_PF("fr-PF", "French", "French Polynesia"), PT_GQ("pt-GQ", "Portuguese", "Equatorial Guinea"),
	VUN_TZ("vun-TZ", "Vunjo", "Tanzania"), JMC_TZ("jmc-TZ", "Machame", "Tanzania"), EN_TV("en-TV", "English", "Tuvalu"),
	EN_PN("en-PN", "English", "Pitcairn Islands"), ZH_HANS_HK("zh-Hans-HK", "Chinese", "Hong Kong SAR China"),
	CU_RU("cu-RU", "Church Slavic", "Russia"), NL_NL("nl-NL", "Dutch", "Netherlands"),
	EN_GY("en-GY", "English", "Guyana"), BS_LATN("bs-Latn", "Bosnian", ""), DYO_SN("dyo-SN", "Jola-Fonyi", "Senegal"),
	NL_CW("nl-CW", "Dutch", "Curaçao"), AR_DZ("ar-DZ", "Arabic", "Algeria"),
	PT_CH("pt-CH", "Portuguese", "Switzerland"), FR_GQ("fr-GQ", "French", "Equatorial Guinea"),
	EN_NG("en-NG", "English", "Nigeria"), FR_CI("fr-CI", "French", "Côte d’Ivoire"), KI_KE("ki-KE", "Kikuyu", "Kenya"),
	IA_001("ia-001", "Interlingua", "World"), EN_PK("en-PK", "English", "Pakistan"), ZH_CN("zh-CN", "Chinese", "China"),
	EN_LC("en-LC", "English", "St Lucia"), BRX_IN("brx-IN", "Bodo", "India"),
	FF_ADLM_BF("ff-Adlm-BF", "Fulah", "Burkina Faso"), GV_IM("gv-IM", "Manx", "Isle of Man"),
	MK_MK("mk-MK", "Macedonian", "North Macedonia"), EN_TT("en-TT", "English", "TrinlanguageTagad & Tobago"),
	SL_SI("sl-SI", "Slovenian", "Slovenia"), XH_ZA("xh-ZA", "Xhosa", "South Africa"),
	FR_BE("fr-BE", "French", "Belgium"), JGO_CM("jgo-CM", "Ngomba", "Cameroon"),
	FF_ADLM_NE("ff-Adlm-NE", "Fulah", "Niger"), ES_VE("es-VE", "Spanish", "Venezuela"),
	MER_KE("mer-KE", "Meru", "Kenya"), EN_BM("en-BM", "English", "Bermuda"),
	NB_NO("nb-NO", "Norwegian Bokmål", "Norway"), KEA_CV("kea-CV", "Kabuverdianu", "Cape Verde"),
	VI_VN("vi-VN", "Vietcountryse", "Vietnam"), EN_US("en-US", "English", "United States"),
	MFE_MU("mfe-MU", "Morisyen", "Mauritius"), FR_BF("fr-BF", "French", "Burkina Faso"),
	PA_GURU("pa-Guru", "Punjabi", ""), IT_SM("it-SM", "Italian", "San Marino"), FR_YT("fr-YT", "French", "Mayotte"),
	GU_IN("gu-IN", "Gujarati", "India"), FF_LATN_CM("ff-Latn-CM", "Fulah", "Cameroon"),
	FI_FI("fi-FI", "Finnish", "Finland"), YUE_HANS("yue-Hans", "Cantonese", ""), CA_FR("ca-FR", "Catalan", "France"),
	SR_LATN_BA("sr-Latn-BA", "Serbian", "Bosnia & Herzegovina"), FR_DJ("fr-DJ", "French", "Djibouti"),
	KS_ARAB("ks-Arab", "Kashmiri", ""), FF_ADLM_GW("ff-Adlm-GW", "Fulah", "Guinea-Bissau"),
	HA_GH("ha-GH", "Hausa", "Ghana"), DE_BE("de-BE", "German", "Belgium"),
	FF_LATN_GW("ff-Latn-GW", "Fulah", "Guinea-Bissau"), NYN_UG("nyn-UG", "Nyankole", "Uganda"),
	MS_MY("ms-MY", "Malay", "Malaysia"), TA_LK("ta-LK", "Tamil", "Sri Lanka"),
	CEB_PH("ceb-PH", "Cebuano", "Philippines"), TG_TJ("tg-TJ", "Tajik", "Tajikistan"),
	QU_PE("qu-PE", "Quechua", "Peru"), ES_EC("es-EC", "Spanish", "Ecuador"),
	FF_ADLM_CM("ff-Adlm-CM", "Fulah", "Cameroon"), LG_UG("lg-UG", "Ganda", "Uganda"),
	FF_LATN_NE("ff-Latn-NE", "Fulah", "Niger"), ZU_ZA("zu-ZA", "Zulu", "South Africa"),
	SU_LATN_ID("su-Latn-ID", "Sundanese", "Indonesia"), CGG_UG("cgg-UG", "Chiga", "Uganda"),
	KSH_DE("ksh-DE", "Colognian", "Germany"), RU_RU("ru-RU", "Russian", "Russia"),
	PCM_NG("pcm-NG", "Nigerian PlanguageTaggin", "Nigeria"), EN_DM("en-DM", "English", "Dominica"),
	EN_BI("en-BI", "English", "Burundi"), GA_IE("ga-IE", "Irish", "Ireland"), EN_WS("en-WS", "English", "Samoa"),
	FR_LU("fr-LU", "French", "Luxembourg"), MI_NZ("mi-NZ", "Maori", "New Zealand"), AR_ER("ar-ER", "Arabic", "Eritrea"),
	SN_ZW("sn-ZW", "Shona", "Zimbabwe"), HE_IL("he-IL", "Hebrew", "Israel"),
	ES_EA("es-EA", "Spanish", "Ceuta & Melilla"), GA_GB("ga-GB", "Irish", "United Kingdom"),
	TH_TH_U_NU_THAI_X_LVARIANT_TH("th-TH-u-nu-thai-x-lvariant-TH", "Thai", "Thailand"),
	FR_SC("fr-SC", "French", "Seychelles"), EN_SL("en-SL", "English", "Sierra Leone"),
	FF_LATN_NG("ff-Latn-NG", "Fulah", "Nigeria"), NO_NO("no-NO", "Norwegian", "Norway"),
	FF_ADLM_NG("ff-Adlm-NG", "Fulah", "Nigeria"), ZH_HANT_MO("zh-Hant-MO", "Chinese", "Macao SAR China"),
	EN_SH("en-SH", "English", "St Helena"), SD_ARAB_PK("sd-Arab-PK", "Sindhi", "Pakistan"),
	DE_LU("de-LU", "German", "Luxembourg"), DE_DE("de-DE", "German", "Germany"), EN_DK("en-DK", "English", "Denmark"),
	SO_DJ("so-DJ", "Somali", "Djibouti"), LT_LT("lt-LT", "Lithuanian", "Lithuania"),
	EO_001("eo-001", "Esperanto", "World"), PS_PK("ps-PK", "Pashto", "Pakistan"),
	EN_UM("en-UM", "English", "US Outlying Islands"), EN_SI("en-SI", "English", "Slovenia"),
	VAI_VAII_LR("vai-Vaii-LR", "Vai", "Liberia"), LO_LA("lo-LA", "Lao", "Laos"), AR_LB("ar-LB", "Arabic", "Lebanon"),
	AF_ZA("af-ZA", "Afrikaans", "South Africa"), FF_GN("ff-GN", "Fulah", "Guinea"),
	ID_ID("languageTag-ID", "Indonesian", "Indonesia"), ES_BZ("es-BZ", "Spanish", "Belize"),
	AR_AE("ar-AE", "Arabic", "United Arab Emirates"), HR_HR("hr-HR", "Croatian", "Croatia"),
	ROF_TZ("rof-TZ", "Rombo", "Tanzania"), KS_IN("ks-IN", "Kashmiri", "India"),
	MY_MM("my-MM", "Burmese", "Myanmar (Burma)"), MN_MN("mn-MN", "Mongolian", "Mongolia"),
	UR_PK("ur-PK", "Urdu", "Pakistan"), DA_DK("da-DK", "Danish", "Denmark"), EN_FM("en-FM", "English", "Micronesia"),
	EN_BE("en-BE", "English", "Belgium"), FR_WF("fr-WF", "French", "Wallis & Futuna"),
	MZN_IR("mzn-IR", "Mazanderani", "Iran"), EN_SG("en-SG", "English", "Singapore"),
	KLN_KE("kln-KE", "Kalenjin", "Kenya"), FF_LATN_GM("ff-Latn-GM", "Fulah", "Gambia"),
	BEZ_TZ("bez-TZ", "Bena", "Tanzania"), FF_LATN_GN("ff-Latn-GN", "Fulah", "Guinea"),
	EN_SD("en-SD", "English", "Sudan"),
	JA_JP_U_CA_JAPANESE_X_LVARIANT_JP("ja-JP-u-ca-japanese-x-lvariant-JP", "Japanese", "Japan"),
	ES_SV("es-SV", "Spanish", "El Salvador"), PT_BR("pt-BR", "Portuguese", "Brazil"),
	MNI_BENG_IN("mni-Beng-IN", "Manipuri", "India"), ML_IN("ml-IN", "Malayalam", "India"),
	EN_FK("en-FK", "English", "Falkland Islands"), IS_IS("is-IS", "Icelandic", "Iceland"),
	EN_DG("en-DG", "English", "Diego Garcia"), PT_ST("pt-ST", "Portuguese", "São Tomé & Príncipe"),
	AK_GH("ak-GH", "Akan", "Ghana"), UZ_ARAB_AF("uz-Arab-AF", "Uzbek", "Afghanistan"),
	EN_SE("en-SE", "English", "Sweden"), ZH_HANS_CN("zh-Hans-CN", "Chinese", "China"),
	ES_419("es-419", "Spanish", "Latin America"), GUZ_KE("guz-KE", "Gusii", "Kenya"),
	GSW_LI("gsw-LI", "Swiss German", "Liechtenstein"), CCP_BD("ccp-BD", "Chakma", "Bangladesh"),
	ES_IC("es-IC", "Spanish", "Canary Islands"), AR_TN("ar-TN", "Arabic", "Tunisia"), BM_ML("bm-ML", "Bambara", "Mali"),
	KW_GB("kw-GB", "Cornish", "United Kingdom"), UG_CN("ug-CN", "Uyghur", "China"), ES_BR("es-BR", "Spanish", "Brazil"),
	KHQ_ML("khq-ML", "Koyra Chiini", "Mali"), EN_SB("en-SB", "English", "Solomon Islands"),
	CHR_US("chr-US", "Cherokee", "United States"), RW_RW("rw-RW", "Kinyarwanda", "Rwanda"),
	SHI_TFNG_MA("shi-Tfng-MA", "Tachelhit", "Morocco"), AR_IQ("ar-IQ", "Arabic", "Iraq"),
	PA_ARAB("pa-Arab", "Punjabi", ""), EN_DE("en-DE", "English", "Germany"), RO_MD("ro-MD", "Romanian", "Moldova"),
	EN_FI("en-FI", "English", "Finland"), DAV_KE("dav-KE", "Taita", "Kenya"), EN_SC("en-SC", "English", "Seychelles"),
	EN_UG("en-UG", "English", "Uganda"), EN_NZ("en-NZ", "English", "New Zealand"), ES_UY("es-UY", "Spanish", "Uruguay"),
	MAS_KE("mas-KE", "Masai", "Kenya"), MNI_IN("mni-IN", "Manipuri", "India"), RU_UA("ru-UA", "Russian", "Ukraine"),
	SG_CF("sg-CF", "Sango", "Central African Republic"), EN_FJ("en-FJ", "English", "Fiji"),
	DE_LI("de-LI", "German", "Liechtenstein"), EN_BB("en-BB", "English", "Barbados"),
	SMN_FI("smn-FI", "Inari Sami", "Finland"), HR_BA("hr-BA", "Croatian", "Bosnia & Herzegovina"),
	DE_AT("de-AT", "German", "Austria"), LU_CD("lu-CD", "Luba-Katanga", "Congo - Kinshasa"),
	AR_001("ar-001", "Arabic", "World"), SO_SO("so-SO", "Somali", "Somalia"), LV_LV("lv-LV", "Latvian", "Latvia"),
	AR_KW("ar-KW", "Arabic", "Kuwait"), SR_CYRL_RS("sr-Cyrl-RS", "Serbian", "Serbia"),
	EN_LS("en-LS", "English", "Lesotho"), EN_HK("en-HK", "English", "Hong Kong SAR China"),
	CE_RU("ce-RU", "Chechen", "Russia"), KA_GE("ka-GE", "Georgian", "Georgia"), SW_TZ("sw-TZ", "Swahili", "Tanzania"),
	FR_RW("fr-RW", "French", "Rwanda"), MG_MG("mg-MG", "Malagasy", "Madagascar"), OS_RU("os-RU", "Ossetic", "Russia"),
	SR_LATN_RS("sr-Latn-RS", "Serbian", "Serbia"), KY_KG("ky-KG", "Kyrgyz", "Kyrgyzstan"),
	AR_JO("ar-JO", "Arabic", "Jordan"), AM_ET("am-ET", "Amharic", "Ethiopia"), FR_DZ("fr-DZ", "French", "Algeria"),
	BO_CN("bo-CN", "Tibetan", "China"), QU_EC("qu-EC", "Quechua", "Ecuador"), EN_MS("en-MS", "English", "Montserrat"),
	EN_GG("en-GG", "English", "Guernsey"), SAT_IN("sat-IN", "Santali", "India"), SV_SE("sv-SE", "Swedish", "Sweden"),
	SR_ME("sr-ME", "Serbian", "Montenegro"), EN_ZM("en-ZM", "English", "Zambia"), FR_ML("fr-ML", "French", "Mali"),
	HA_NG("ha-NG", "Hausa", "Nigeria"), AR_SA("ar-SA", "Arabic", "Saudi Arabia"),
	FA_AF("fa-AF", "Persian", "Afghanistan"), OS_GE("os-GE", "Ossetic", "Georgia"), EN_MT("en-MT", "English", "Malta"),
	EN_GH("en-GH", "English", "Ghana"), EN_IL("en-IL", "English", "Israel"),
	TZM_MA("tzm-MA", "Central Atlas Tamazight", "Morocco"), SES_ML("ses-ML", "Koyraboro Senni", "Mali"),
	LKT_US("lkt-US", "Lakota", "United States"), SD_DEVA("sd-Deva", "Sindhi", ""),
	VAI_LATN_LR("vai-Latn-LR", "Vai", "Liberia"), YI_001("yi-001", "YlanguageTagdish", "World"),
	SW_CD("sw-CD", "Swahili", "Congo - Kinshasa"), FF_ADLM_MR("ff-Adlm-MR", "Fulah", "Mauritania"),
	FIL_PH("fil-PH", "Filipino", "Philippines"), IT_VA("it-VA", "Italian", "Vatican City"),
	ES_PH("es-PH", "Spanish", "Philippines"), ES_ES("es-ES", "Spanish", "Spain"), ES_CO("es-CO", "Spanish", "Colombia"),
	BG_BG("bg-BG", "Bulgarian", "Bulgaria"), EN_VC("en-VC", "English", "St Vincent & Grenadines"),
	HSB_DE("hsb-DE", "Upper Sorbian", "Germany"), EN_150("en-150", "English", "Europe"),
	AR_SD("ar-SD", "Arabic", "Sudan"), HA_NE("ha-NE", "Hausa", "Niger"), EN_KN("en-KN", "English", "St Kitts & Nevis"),
	RO_RO("ro-RO", "Romanian", "Romania"), SR_LATN_ME("sr-Latn-ME", "Serbian", "Montenegro"),
	ES_GT("es-GT", "Spanish", "Guatemala"), FF_LATN_LR("ff-Latn-LR", "Fulah", "Liberia"),
	BAS_CM("bas-CM", "Basaa", "Cameroon"), FR_MG("fr-MG", "French", "Madagascar"), ES_CL("es-CL", "Spanish", "Chile"),
	KAM_KE("kam-KE", "Kamba", "Kenya"), FA_IR("fa-IR", "Persian", "Iran"), EN_MO("en-MO", "English", "Macao SAR China"),
	SHI_TFNG("shi-Tfng", "Tachelhit", ""), EN_BZ("en-BZ", "English", "Belize"), SQ_AL("sq-AL", "Albanian", "Albania"),
	TWQ_NE("twq-NE", "Tasawaq", "Niger"), NMG_CM("nmg-CM", "Kwasio", "Cameroon"), AZ_CYRL("az-Cyrl", "Azerbaijani", ""),
	EN_MP("en-MP", "English", "Northern Mariana Islands"), EN_GD("en-GD", "English", "Grenada"),
	SHI_LATN("shi-Latn", "Tachelhit", ""), EN_BW("en-BW", "English", "Botswana"), KAB_DZ("kab-DZ", "Kabyle", "Algeria"),
	KDE_TZ("kde-TZ", "Makonde", "Tanzania"), TA_MY("ta-MY", "Tamil", "Malaysia"), SV_FI("sv-FI", "Swedish", "Finland"),
	ND_ZW("nd-ZW", "North Ndebele", "Zimbabwe"), MNI_BENG("mni-Beng", "Manipuri", ""),
	EN_IE("en-IE", "English", "Ireland"), ZH_SG("zh-SG", "Chinese", "Singapore"), EN_KI("en-KI", "English", "Kiribati"),
	OM_ET("om-ET", "Oromo", "Ethiopia"), JA_JP("ja-JP", "Japanese", "Japan"),
	FF_ADLM_GH("ff-Adlm-GH", "Fulah", "Ghana"), FR_MF("fr-MF", "French", "St Martin"),
	MS_ID("ms-ID", "Malay", "Indonesia"), EN_SZ("en-SZ", "English", "Eswatini"), RWK_TZ("rwk-TZ", "Rwa", "Tanzania"),
	ES_PE("es-PE", "Spanish", "Peru"), MGH_MZ("mgh-MZ", "Makhuwa-Meetto", "Mozambique"),
	EN_GB("en-GB", "English", "United Kingdom"), ZH_HANT_HK("zh-Hant-HK", "Chinese", "Hong Kong SAR China"),
	SAT_OLCK_IN("sat-Olck-IN", "Santali", "India"), KK_KZ("kk-KZ", "Kazakh", "Kazakhstan"),
	ES_PA("es-PA", "Spanish", "Panama"), AR_PS("ar-PS", "Arabic", "Palestinian Territories"),
	FR_MC("fr-MC", "French", "Monaco"), VAI_LATN("vai-Latn", "Vai", ""), UR_IN("ur-IN", "Urdu", "India"),
	GSW_CH("gsw-CH", "Swiss German", "Switzerland"), LUY_KE("luy-KE", "Luyia", "Kenya"),
	KKJ_CM("kkj-CM", "Kako", "Cameroon"), ES_GQ("es-GQ", "Spanish", "Equatorial Guinea"),
	AR_YE("ar-YE", "Arabic", "Yemen"), EN_SX("en-SX", "English", "Sint Maarten"),
	RU_KZ("ru-KZ", "Russian", "Kazakhstan"), KO_KP("ko-KP", "Korean", "North Korea"),
	NL_SR("nl-SR", "Dutch", "Suricountry"), EN_BS("en-BS", "English", "Bahamas"),
	NL_BQ("nl-BQ", "Dutch", "Caribbean Netherlands"), FF_ADLM_GN("ff-Adlm-GN", "Fulah", "Guinea"),
	UZ_CYRL_UZ("uz-Cyrl-UZ", "Uzbek", "Uzbekistan"), EN_KE("en-KE", "English", "Kenya"),
	ASA_TZ("asa-TZ", "Asu", "Tanzania"), FR_SN("fr-SN", "French", "Senegal"), FR_MA("fr-MA", "French", "Morocco"),
	PT_LU("pt-LU", "Portuguese", "Luxembourg"), FF_ADLM_GM("ff-Adlm-GM", "Fulah", "Gambia"),
	FR_BL("fr-BL", "French", "St Barthélemy"), MGO_CM("mgo-CM", "Metaʼ", "Cameroon"),
	MAI_IN("mai-IN", "Maithili", "India"), KO_KR("ko-KR", "Korean", "South Korea"), ES_MX("es-MX", "Spanish", "Mexico"),
	BS_CYRL("bs-Cyrl", "Bosnian", ""), SR_LATN("sr-Latn", "Serbian", ""), EN_SS("en-SS", "English", "South Sudan"),
	BO_IN("bo-IN", "Tibetan", "India"), EN_MG("en-MG", "English", "Madagascar"), FR_BI("fr-BI", "French", "Burundi"),
	BN_BD("bn-BD", "Bengali", "Bangladesh"), EN_ZA("en-ZA", "English", "South Africa"),
	FR_FR("fr-FR", "French", "France"), EN_MH("en-MH", "English", "Marshall Islands"),
	FR_BJ("fr-BJ", "French", "Benin"), ZH_HANT("zh-Hant", "Chinese", ""), SAH_RU("sah-RU", "Sakha", "Russia"),
	PS_AF("ps-AF", "Pashto", "Afghanistan"), BS_CYRL_BA("bs-Cyrl-BA", "Bosnian", "Bosnia & Herzegovina"),
	SK_SK("sk-SK", "Slovak", "Slovakia"), WO_SN("wo-SN", "Wolof", "Senegal"), FR_HT("fr-HT", "French", "Haiti"),
	TE_IN("te-IN", "Telugu", "India"), NL_SX("nl-SX", "Dutch", "Sint Maarten"),
	FR_CG("fr-CG", "French", "Congo - Brazzaville"), MT_MT("mt-MT", "Maltese", "Malta"),
	NDS_NL("nds-NL", "Low German", "Netherlands"), EN_VU("en-VU", "English", "Vanuatu"),
	TO_TO("to-TO", "Tongan", "Tonga"), FF_LATN_SL("ff-Latn-SL", "Fulah", "Sierra Leone"),
	SEH_MZ("seh-MZ", "Sena", "Mozambique"), FF_ADLM_SN("ff-Adlm-SN", "Fulah", "Senegal"),
	SU_ID("su-ID", "Sundanese", "Indonesia"), II_CN("ii-CN", "Sichuan Yi", "China"),
	PA_ARAB_PK("pa-Arab-PK", "Punjabi", "Pakistan"), FR_RE("fr-RE", "French", "Réunion"),
	BN_IN("bn-IN", "Bengali", "India"), FR_GP("fr-GP", "French", "Guadeloupe"),
	ZGH_MA("zgh-MA", "Standard Moroccan Tamazight", "Morocco"), UK_UA("uk-UA", "Ukrainian", "Ukraine"),
	EN_NF("en-NF", "English", "Norfolk Island"), FR_CH("fr-CH", "French", "Switzerland"),
	SR_CYRL_XK("sr-Cyrl-XK", "Serbian", "Kosovo"), AR_SS("ar-SS", "Arabic", "South Sudan"),
	EN_GU("en-GU", "English", "Guam"), NL_AW("nl-AW", "Dutch", "Aruba"), EN_AI("en-AI", "English", "Anguilla"),
	XOG_UG("xog-UG", "Soga", "Uganda"), EN_CM("en-CM", "English", "Cameroon"), CS_CZ("cs-CZ", "Czech", "Czechia"),
	CA_ES("ca-ES", "Catalan", "Spain"), RM_CH("rm-CH", "Romansh", "Switzerland"), RU_MD("ru-MD", "Russian", "Moldova"),
	FF_LATN_SN("ff-Latn-SN", "Fulah", "Senegal"), EN_TO("en-TO", "English", "Tonga"),
	FF_ADLM_SL("ff-Adlm-SL", "Fulah", "Sierra Leone"), EN_PG("en-PG", "English", "Papua New Guinea"),
	FR_CF("fr-CF", "French", "Central African Republic"), PT_TL("pt-TL", "Portuguese", "Timor-Leste"),
	EN_ER("en-ER", "English", "Eritrea"), SR_BA("sr-BA", "Serbian", "Bosnia & Herzegovina"),
	ES_PY("es-PY", "Spanish", "Paraguay"), KOK_IN("kok-IN", "Konkani", "India"), FR_TG("fr-TG", "French", "Togo"),
	SR_LATN_XK("sr-Latn-XK", "Serbian", "Kosovo"), EN_PH("en-PH", "English", "Philippines"),
	IG_NG("ig-NG", "Igbo", "Nigeria"), FR_GN("fr-GN", "French", "Guinea"), PRG_001("prg-001", "Prussian", "World"),
	ZH_HANS_MO("zh-Hans-MO", "Chinese", "Macao SAR China"), SAT_OLCK("sat-Olck", "Santali", ""),
	SE_FI("se-FI", "Northern Sami", "Finland"), EN_CK("en-CK", "English", "Cook Islands"),
	AR_MA("ar-MA", "Arabic", "Morocco"), EN_AG("en-AG", "English", "Antigua & Barbuda"),
	FR_TD("fr-TD", "French", "Chad"), EBU_KE("ebu-KE", "Embu", "Kenya"), BEM_ZM("bem-ZM", "Bemba", "Zambia"),
	EWO_CM("ewo-CM", "Ewondo", "Cameroon"), FR_CD("fr-CD", "French", "Congo - Kinshasa"),
	RN_BI("rn-BI", "Rundi", "Burundi"), EN_NA("en-NA", "English", "Namibia"), CA_IT("ca-IT", "Catalan", "Italy"),
	LAG_TZ("lag-TZ", "Langi", "Tanzania"), TEO_KE("teo-KE", "Teso", "Kenya"), CA_AD("ca-AD", "Catalan", "Andorra"),
	QU_BO("qu-BO", "Quechua", "Bolivia"), HAW_US("haw-US", "Hawaiian", "United States"),
	FR_CA("fr-CA", "French", "Canada"), SQ_XK("sq-XK", "Albanian", "Kosovo"),
	EN_KY("en-KY", "English", "Cayman Islands"), IT_CH("it-CH", "Italian", "Switzerland"),
	SI_LK("si-LK", "Sinhala", "Sri Lanka"), LUO_KE("luo-KE", "Luo", "Kenya"),
	EN_AE("en-AE", "English", "United Arab Emirates"), IT_IT("it-IT", "Italian", "Italy"),
	AR_SO("ar-SO", "Arabic", "Somalia"), EN_ZW("en-ZW", "English", "Zimbabwe"),
	NN_NO("nn-NO", "Norwegian Nynorsk", "Norway"), FR_MU("fr-MU", "French", "Mauritius"),
	SE_SE("se-SE", "Northern Sami", "Sweden"), EN_TK("en-TK", "English", "Tokelau"),
	MUA_CM("mua-CM", "Mundang", "Cameroon"), UZ_ARAB("uz-Arab", "Uzbek", ""), SAQ_KE("saq-KE", "Samburu", "Kenya"),
	PT_GW("pt-GW", "Portuguese", "Guinea-Bissau"), MS_SG("ms-SG", "Malay", "Singapore"), EE_TG("ee-TG", "Ewe", "Togo"),
	LN_AO("ln-AO", "Lingala", "Angola"), BE_BY("be-BY", "Belarusian", "Belarus"), YUE_HANT("yue-Hant", "Cantonese", ""),
	PT_CV("pt-CV", "Portuguese", "Cape Verde"), ES_PR("es-PR", "Spanish", "Puerto Rico"),
	WAE_CH("wae-CH", "Walser", "Switzerland"), RU_BY("ru-BY", "Russian", "Belarus"),
	FO_DK("fo-DK", "Faroese", "Denmark"), EE_GH("ee-GH", "Ewe", "Ghana"), AR_BH("ar-BH", "Arabic", "Bahrain"),
	HI_IN("hi-IN", "Hindi", "India"), EN_CH("en-CH", "English", "Switzerland"),
	FO_FO("fo-FO", "Faroese", "Faroe Islands"), YO_BJ("yo-BJ", "Yoruba", "Benin"),
	AST_ES("ast-ES", "Asturian", "Spain"), FR_KM("fr-KM", "French", "Comoros"), FR_MQ("fr-MQ", "French", "Martinique"),
	ES_AR("es-AR", "Spanish", "Argentina"), FF_LATN("ff-Latn", "Fulah", ""), EN_MY("en-MY", "English", "Malaysia"),
	SBP_TZ("sbp-TZ", "Sangu", "Tanzania"), UZ_CYRL("uz-Cyrl", "Uzbek", ""),
	YUE_HANT_HK("yue-Hant-HK", "Cantonese", "Hong Kong SAR China"), HY_AM("hy-AM", "Armenian", "Armenia"),
	EN_GM("en-GM", "English", "Gambia"), NE_NP("ne-NP", "Nepali", "Nepal"), FUR_IT("fur-IT", "Friulian", "Italy"),
	TA_IN("ta-IN", "Tamil", "India"), FR_GF("fr-GF", "French", "French Guiana"), PT_AO("pt-AO", "Portuguese", "Angola"),
	EN_001("en-001", "English", "World"), JV_ID("jv-ID", "Javanese", "Indonesia"),
	RU_KG("ru-KG", "Russian", "Kyrgyzstan"), FR_MR("fr-MR", "French", "Mauritania"),
	FF_LATN_BF("ff-Latn-BF", "Fulah", "Burkina Faso"), ZH_HANT_TW("zh-Hant-TW", "Chinese", "Taiwan"),
	AS_IN("as-IN", "Assamese", "India"), ZH_HK("zh-HK", "Chinese", "Hong Kong SAR China"),
	SW_KE("sw-KE", "Swahili", "Kenya"), TH_TH("th-TH", "Thai", "Thailand"), EN_MW("en-MW", "English", "Malawi"),
	NAQ_NA("naq-NA", "Nama", "Namibia"), EN_IO("en-IO", "English", "British Indian Ocean Territory"),
	AR_QA("ar-QA", "Arabic", "Qatar"), EN_CC("en-CC", "English", "Cocos (Keeling) Islands"),
	PT_PT("pt-PT", "Portuguese", "Portugal"), AZ_CYRL_AZ("az-Cyrl-AZ", "Azerbaijani", "Azerbaijan"),
	CKB_IQ("ckb-IQ", "Central Kurdish", "Iraq"), SHI_LATN_MA("shi-Latn-MA", "Tachelhit", "Morocco"),
	ES_CU("es-CU", "Spanish", "Cuba"), EN_VI("en-VI", "English", "US Virgin Islands"),
	EU_ES("eu-ES", "Basque", "Spain"), UZ_LATN("uz-Latn", "Uzbek", ""), EL_GR("el-GR", "Greek", "Greece"),
	YAV_CM("yav-CM", "Yangben", "Cameroon"), DA_GL("da-GL", "Danish", "Greenland"), KM_KH("km-KH", "Khmer", "Cambodia"),
	CKB_IR("ckb-IR", "Central Kurdish", "Iran"), CA_ES_VALENCIA("ca-ES-VALENCIA", "Catalan", "Spain"),
	SD_DEVA_IN("sd-Deva-IN", "Sindhi", "India"), ES_CR("es-CR", "Spanish", "Costa Rica"),
	FR_GA("fr-GA", "French", "Gabon"), AR_LY("ar-LY", "Arabic", "Libya"), EN_MU("en-MU", "English", "Mauritius"),
	GL_ES("gl-ES", "Galician", "Spain"), AZ_LATN_AZ("az-Latn-AZ", "Azerbaijani", "Azerbaijan"),
	EN_IM("en-IM", "English", "Isle of Man"), EN_GI("en-GI", "English", "Gibraltar"),
	EN_CA("en-CA", "English", "Canada"), FR_SY("fr-SY", "French", "Syria"), SO_ET("so-ET", "Somali", "Ethiopia"),
	NL_BE("nl-BE", "Dutch", "Belgium"), AR_DJ("ar-DJ", "Arabic", "Djibouti"), CY_GB("cy-GB", "Welsh", "United Kingdom"),
	EN_VG("en-VG", "English", "British Virgin Islands"), EN_TC("en-TC", "English", "Turks & Caicos Islands"),
	SV_AX("sv-AX", "Swedish", "Åland Islands"), AF_NA("af-NA", "Afrikaans", "Namibia"),
	EN_IN("en-IN", "English", "India"), ES_NI("es-NI", "Spanish", "Nicaragua"), SD_PK("sd-PK", "Sindhi", "Pakistan"),
	MAS_TZ("mas-TZ", "Masai", "Tanzania"), MS_BN("ms-BN", "Malay", "Brunei"), CCP_IN("ccp-IN", "Chakma", "India"),
	BR_FR("br-FR", "Breton", "France");

	private final String languageTag;
	private final String language;
	private final String country;

	private static final Map<String, LocaleEnum> MAP = new HashMap<>();

	static {
		for (LocaleEnum localeEnum : values()) {
			MAP.put(localeEnum.getLanguageTag(), localeEnum);
		}
	}

	private LocaleEnum(String languageTag, String language, String country) {
		this.languageTag = languageTag;
		this.language = language;
		this.country = country;
	}

	public String getLanguageTag() {
		return languageTag;
	}

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

	public static LocaleEnum fromLanguageTag(String languageTag) {
		return MAP.get(languageTag);
	}
}