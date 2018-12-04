package com.gems.enums;

/**
 * This enum constant contains all constants values used for sleep/pause in
 * between of execution.
 * 
 * @author Anu
 *
 */
public enum SleepConstant {

	THREE_HUNDRED_MILLISECONDS(300), HALF_SECOND(500), ONE_SECOND(1000), TWO_SECONDS(
			2000), THREE_SECONDS(3000), FOUR_SECONDS(4000), FIVE_SECONDS(5000);
	private long milliSeconds;

	private SleepConstant(final long milliSeconds) {
		this.milliSeconds = milliSeconds;
	}

	public long getMilliSeconds() {
		return milliSeconds;
	}
}
