package com.developer4droid.shoppingquiz.network;

import com.developer4droid.shoppingquiz.model.QuizItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:55
 */

public interface DataLoader {

	void loadQuizzes(DataReceiver<List<QuizItem>> dataReceiver);
}
