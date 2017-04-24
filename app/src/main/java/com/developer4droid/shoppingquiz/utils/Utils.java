package com.developer4droid.shoppingquiz.utils;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 22:52
 */

public class Utils {

	public static String getTimeFromMilliseconds(long timeInMilliSeconds) {
		long seconds = timeInMilliSeconds / 1000;
		long minutes = seconds / 60;
		long secondVal = seconds % 60;
		String secondsStr = secondVal < 10 ? "0" + secondVal : String.valueOf(secondVal);
		return minutes % 60 + ":" + secondsStr;
	}
}
