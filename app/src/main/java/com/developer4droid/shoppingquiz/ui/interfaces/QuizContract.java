package com.developer4droid.shoppingquiz.ui.interfaces;

import com.developer4droid.shoppingquiz.model.QuizItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:53
 */

public interface QuizContract {

	interface ViewFrame {

		void updateData(List<QuizItem> itemList);
	}

	interface ActionListener {
		void onResume(ViewFrame viewFrame);
	}
}
