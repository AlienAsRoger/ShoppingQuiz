package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.network.DataLoader;
import com.developer4droid.shoppingquiz.network.DataReceiver;
import com.developer4droid.shoppingquiz.ui.interfaces.MainContract;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:44
 */

public class MainViewModel extends BaseViewModel implements MainContract.ActionListener, DataReceiver<List<QuizItem>> {

	private static final int SIZE = 20;

	@Inject
	DataLoader dataLoader;

	private MainContract.ViewFrame viewFrame;
	private List<QuizItem> quizItems;
	private boolean isLoading;
	private boolean isQuizStarted;

	public MainViewModel() {
		MyApplication.getInstance().getGlobalComponent().inject(this);
	}

	// ------------- //
	// Local methods //
	// ------------- //

	@Bindable
	public boolean isLoading() {
		return isLoading;
	}

	public void setLoading(boolean loading) {
		isLoading = loading;
		notifyPropertyChanged(BR.loading);
	}

	@Bindable
	public boolean isQuizStarted() {
		return isQuizStarted;
	}

	public void setQuizStarted(boolean quizStarted) {
		isQuizStarted = quizStarted;
		notifyPropertyChanged(BR.quizStarted);
	}

	/**
	 * Start Quiz and 2 min to solve the quiz
	 * Select random 20 // TODO
	 */
	public void startQuiz() {

		List<QuizItem> quizzesToSolve = new ArrayList<>();
		for (int i = 0; i < SIZE; i++) { // TODO get random SIZE
			QuizItem quizItem = quizItems.get(i);
			quizzesToSolve.add(quizItem);
		}

		setQuizStarted(true);
		viewFrame.startQuiz(quizzesToSolve);
	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //

	@Override
	public void onResume(MainContract.ViewFrame viewFrame) {
		this.viewFrame = viewFrame;

		setLoading(true);
		dataLoader.loadQuizzes(this);
		registerBus();
	}

	@Override
	public void onDataReceived(List<QuizItem> itemList) {
		setLoading(false);
		quizItems = itemList;
	}



}
