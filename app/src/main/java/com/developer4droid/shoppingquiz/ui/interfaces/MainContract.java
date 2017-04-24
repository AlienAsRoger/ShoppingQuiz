package com.developer4droid.shoppingquiz.ui.interfaces;

import com.developer4droid.shoppingquiz.model.QuizItem;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:53
 */

public interface MainContract {

	interface ViewFrame {

		void startQuiz(QuizItem quizItem);
	}

	interface ActionListener {
		void onResume(ViewFrame viewFrame);
	}
}
