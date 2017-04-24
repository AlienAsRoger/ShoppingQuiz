package com.developer4droid.shoppingquiz.events;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:51
 */

public class AnswerSelectedEvent {

	private String answer;

	public AnswerSelectedEvent(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}
}
