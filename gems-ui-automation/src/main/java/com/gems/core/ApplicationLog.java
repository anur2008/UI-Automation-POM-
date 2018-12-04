package com.gems.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gems.constant.ApplicationConstant;

public final class ApplicationLog {
	/** private constructor. */
	private ApplicationLog() {
		// private constructor
	}

	/** Initialize Log4j logs. */
	private static final Logger LOG = LogManager.getLogger(ApplicationLog.class
			.getName());

	/**
	 * Used to log info messages.
	 *
	 * @param message
	 *            message to log
	 */
	public static void info(final String message) {
		LOG.info(message);
	}

	/**
	 * Used to log warn messages.
	 *
	 * @param message
	 *            warning message to log
	 */
	public static void warn(final String message) {
		LOG.warn(message);
	}

	/**
	 * Used to log error messages.
	 *
	 * @param message
	 *            error message to log
	 */
	public static void error(final String message) {
		LOG.error(message);
	}

	/**
	 * This method is used to log exception details in logger file.
	 *
	 * @param customMessage
	 *            Custom exception message
	 * @param exception
	 *            Exception to log
	 */
	public static void logException(final String customMessage,
			final Throwable exception) {
		if (exception != null) {
			LOG.error(
					customMessage + ApplicationConstant.COLON
							+ exception.getLocalizedMessage(), exception);
		}
	}

}
