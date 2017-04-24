package com.developer4droid.shoppingquiz.events;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 22:44
 */

public class TimerUpdateEvent {

	private long millisecondsLeft;

	public TimerUpdateEvent(long millisUntilFinished) {

		millisecondsLeft = millisUntilFinished;
	}

	public long getMillisecondsLeft() {
		return millisecondsLeft;
	}
}
