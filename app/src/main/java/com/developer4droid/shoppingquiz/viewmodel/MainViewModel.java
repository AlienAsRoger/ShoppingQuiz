package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import android.util.Log;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.SubmitAnswerEvent;
import com.developer4droid.shoppingquiz.events.TimerFinishedEvent;
import com.developer4droid.shoppingquiz.events.TimerUpdateEvent;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.network.DataLoader;
import com.developer4droid.shoppingquiz.network.DataReceiver;
import com.developer4droid.shoppingquiz.ui.interfaces.MainContract;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
	private static final int CORRECT = 0; // first one is always correct

	@Inject
	DataLoader dataLoader;

	private MainContract.ViewFrame viewFrame;
	private List<QuizItem> quizItems;
	private boolean isLoading;
	private boolean isQuizStarted;
	private int currentQuizItem;
	private List<QuizItem> quizzesToSolve;

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
		if (!isQuizStarted) {
			quizzesToSolve = new ArrayList<>();
			for (int i = 0; i < SIZE; i++) { // TODO get random SIZE
				QuizItem quizItem = quizItems.get(i);
				quizzesToSolve.add(quizItem);
			}

			currentQuizItem = 0;
		}

		setQuizStarted(true);
		viewFrame.startQuiz(quizzesToSolve.get(currentQuizItem));
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

	// --------- //
	// Event Bus //
	// --------- //

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(SubmitAnswerEvent event) {
		// get current solved quiz
		QuizItem quizItem = quizzesToSolve.get(currentQuizItem);
		String correctAnswer = quizItem.getUrls().get(CORRECT);

		if (event.getSelectedAnswer().equals(correctAnswer)) {
			Log.d("TEST", "onEvent: correct");
		} else {
			Log.d("TEST", "onEvent: wrong");
		}
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(TimerUpdateEvent event) {
		Log.d("TEST", "TimerUpdateEvent: ");

	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(TimerFinishedEvent event) {
		Log.d("TEST", "TimerFinishedEvent: ");

	}


}
