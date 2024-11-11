/* Copyright -2019 @intentlabs
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
package com.intentlabs.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.setting.model.SystemSettingModel;

public class AesCryptUtil {
	Cipher ecipher;
	Cipher dcipher;
	byte[] buf;
	static final String HEXES = "0123456789ABCDEF";

	public AesCryptUtil() {
		this.buf = new byte[1024];
		try {
			final KeyGenerator instance = KeyGenerator.getInstance("AES");
			instance.init(128);
			this.setupCrypto(instance.generateKey());
		} catch (Exception ex) {
			LoggerService.exception(ex);
		}
	}

	public AesCryptUtil(final String s) {
		this.buf = new byte[1024];
		this.setupCrypto(new SecretKeySpec(getMD5(s), "AES"));
	}

	private void setupCrypto(final SecretKey secretKey) {
		final IvParameterSpec ivParameterSpec = new IvParameterSpec(
				new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });
		try {
			this.ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			this.dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			this.ecipher.init(1, secretKey, ivParameterSpec);
			this.dcipher.init(2, secretKey, ivParameterSpec);
		} catch (Exception ex) {
			LoggerService.exception(ex);
		}
	}

	public void encrypt(final InputStream inputStream, final OutputStream os) {
		try {
			final CipherOutputStream cipherOutputStream = new CipherOutputStream(os, this.ecipher);
			int read;
			while ((read = inputStream.read(this.buf)) >= 0) {
				cipherOutputStream.write(this.buf, 0, read);
			}
			cipherOutputStream.close();
		} catch (IOException ex) {
			LoggerService.exception(ex);
		}
	}

	public String encrypt(final String s) {
		try {
			return byteToHex(this.ecipher.doFinal(s.getBytes("UTF-8")));
		} catch (Exception ex) {
			LoggerService.exception(ex);
			return null;
		}
	}

	public void decrypt(final InputStream is, final OutputStream outputStream) {
		try {
			int read;
			while ((read = new CipherInputStream(is, this.dcipher).read(this.buf)) >= 0) {
				outputStream.write(this.buf, 0, read);
			}
			outputStream.close();
		} catch (IOException ex) {
			LoggerService.exception(ex);
		}
	}

	public String decrypt(final String s) {
		try {
			return new String(this.dcipher.doFinal(hexToByte(s)), "UTF-8");
		} catch (Exception ex) {
			LoggerService.exception(ex);
			return null;
		}
	}

	public String decrypt(final byte[] input) {
		try {
			return new String(this.dcipher.doFinal(input), "UTF-8");
		} catch (Exception ex) {
			LoggerService.exception(ex);
			return null;
		}
	}

	private static byte[] getMD5(final String s) {
		try {
			return MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
		} catch (Exception ex) {
			return null;
		}
	}

	public static String byteToHex(final byte[] array) {
		if (array == null) {
			return null;
		}
		String string = "";
		for (int i = 0; i < array.length; ++i) {
			string += Integer.toString((array[i] & 0xFF) + 256, 16).substring(1);
		}
		return string;
	}

	public static byte[] hexToByte(final String s) {
		final int length = s.length();
		final byte[] array = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			array[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return array;
	}

	public static void main(final String[] array) {
//		String x = null;
//		String string = null;
//		String s = null;
//		String s2 = null;
//		String s3 = null;
//		if (array == null || array.length < 3) {
//			string = "error: missing one or more arguments. Usage: AesCryptUtil key data <enc|dec>";
//		} else {
//			s = array[0];
//			s2 = array[1];
//			s3 = array[2];
//			if (s == null) {
//				string = "error: no key";
//			} else if (s.length() < 32) {
//				string = "error: key length less than 32 bytes";
//			} else if (s2 == null || s3 == null) {
//				string = "error: no data";
//			} else if (s3 == null) {
//				string = "error: no action";
//			} else if (!s3.equals("enc") && !s3.equals("dec")) {
//				string = "error: invalid action";
//			}
//		}
//		if (string == null) {
//			try {
//				final AesCryptUtil aesCryptUtil = new AesCryptUtil(s);
//				if (s3.equals("enc")) {
//					x = aesCryptUtil.encrypt(s2);
//				} else {
//					x = aesCryptUtil.decrypt(s2);
//				}
//			} catch (Exception obj) {
//				string = "error : Exception in performing the requested operation : " + obj;
//			}
//		}
//		if (x != null) {
//			System.out.println(x);
//		} else {
//			System.out.println(string);
//		}
		AesCryptUtil aesUtil = new AesCryptUtil("A4F3FCCFF2C7C02C0C5AF11E63ACE5D9");
		System.out.println(aesUtil.decrypt(
				"04c5fa8bba36faf2cd93483226a748e0bb27a246f5c3be8cdca3667ce39193631b80d910d56f426c2f82c02beffc773544be8619cd16bd3a9bab7078853b6be47d7033847b83ddd9fc0077957a12dd5a3459f0028a8348464cd1c9b6071af56a746df52505c733cd5657c7b2323eba632d4834138d42fb2dbd43237e9b86c6027d1f5bbaab8628cc36d38135883a4d75cb5d8c6694274ad70f75f7fdf2628eea7f68896077e2e41d5b981ce66b6eab7ea006faed801c22b70e374b6126c42a1319e0dcf041826eaae40ca331e26090289edcb932faccd7a5356d57b6224cfb5f40025363858db2e8e18ba9a1dbdd1c8cc321c4455968af0ae7365f2a1441c6bf2893a80ebbffd65a14a7529aa20b58e4121f583a2128a36804d5bb53001de32d9a611e56863641102a5d90add0439512d624f73e8d241fccebe1c4621bb588375e3b9036f49c7eec60c182bada12f7ff6fa1e53a4daab0e8c315ebdb72a71c7c4adfe9a0f7ef237260f749e25e44f75d38f3b4d69d325f6c79bf21f8922608165597fbf47b04e88883a47241f9329ac7026bc0c2868ae8ad39c3ffdd9f5aacf9ec6a38fa1fab3231abda5882ba36e89dd9eec255a486da8179ef76e95bdb22a62c01de980df8dfa70b7ab56717a68e4027f2f3ab1e4a8ff5e72f30fef54cd894d2a679c8eefde8d6e3b19b643ad55e6b575b4a077cef3c653a9733ed08a4c439e2a69d41c31b19b094251ac2c5bfe3beee941aa049ab8e718924d0d46343bdf989af5aafedefac6cc473f2f0d35c9d5b064128497fd730b2033b31cc509d83bd236351b148fc1257804b92110220213adcfc16a86e3d5d77554eabb2c548544d49e8cd7d59212d1c68e33e75b2e65e2d90bfbfb02ef81ac8518a7509c01c0fe87f092ed3c8f9c4ae73c5914c676d61e265cc9b734b48e90be64865cbab432c7d765cc65d8b9ee28ed0b2661656443af1e956c5564f05d3dba3f8713aab91500db62a72fc49a0e3bca31992d91cbeb9e8"));
	}
}
