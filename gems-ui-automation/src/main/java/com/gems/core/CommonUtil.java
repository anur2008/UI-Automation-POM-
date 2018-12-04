package com.gems.core;

import java.util.Date;

import com.gems.constant.ApplicationConstant;

/**
 * This class contains common methods used to generate random data and validate
 * data.
 * 
 * @author Anu
 *
 */
public class CommonUtil {

	/**
	 * This method is used to get unique value followed by given prefix.
	 *
	 * @param prefix
	 *            prefix
	 * @return unique value followed by given prefix
	 */

	public static String generateUniqueName(final String prefix) {
		return prefix + new Date().getTime();
	}

	/**
	 * This method is used to generate unique name with prefix autoui.
	 * 
	 * @return Unique name
	 */
	public static String generateAutoUiUniqueName() {
		return generateUniqueName(ApplicationConstant.FOLDER_NAME);
	}

}
