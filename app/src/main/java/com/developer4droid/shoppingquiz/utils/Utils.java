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
		return  minutes % 60 + ":" + seconds % 60;
	}
}
