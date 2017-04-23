package com.developer4droid.shoppingquiz.viewmodel;

import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.network.DataLoader;
import com.developer4droid.shoppingquiz.network.DataReceiver;
import com.developer4droid.shoppingquiz.ui.interfaces.QuizContract;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:44
 */

public class MainViewModel extends BaseViewModel implements QuizContract.ActionListener, DataReceiver<List<QuizItem>> {

	@Inject
	DataLoader dataLoader;

	private QuizContract.ViewFrame viewFrame;

	public MainViewModel() {
		MyApplication.getInstance().getGlobalComponent().inject(this);
	}

	// ------------- //
	// Local methods //
	// ------------- //

	/**
	 * Start Quiz and 2 min to solve the quiz
	 */
	public void startQuiz() {
//		viewFrame.
	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //

	@Override
	public void onResume(QuizContract.ViewFrame viewFrame) {
		this.viewFrame = viewFrame;
		dataLoader.loadQuizzes(this);
		registerBus();
	}

	@Override
	public void onDataReceived(List<QuizItem> data) {
		viewFrame.updateData(data);
	}

}
