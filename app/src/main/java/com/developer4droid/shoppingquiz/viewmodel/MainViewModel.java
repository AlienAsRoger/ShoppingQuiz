package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.LoadNextQuizEvent;
import com.developer4droid.shoppingquiz.events.SubmitAnswerEvent;
import com.developer4droid.shoppingquiz.events.TimerFinishedEvent;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.network.DataLoader;
import com.developer4droid.shoppingquiz.network.DataReceiver;
import com.developer4droid.shoppingquiz.ui.interfaces.MainContract;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:44
 */

public class MainViewModel extends BaseViewModel implements MainContract.ActionListener, DataReceiver<List<QuizItem>> {

	private static final int QUESTIONS_COUNT = 20;
	private static final int CORRECT = 0; // first one is always correct

	@Inject
	DataLoader dataLoader;

	private MainContract.ViewFrame viewFrame;
	private List<QuizItem> quizItems;
	private boolean isLoading;
	private boolean isQuizStarted;
	private boolean isQuizFinished;
	private int currentQuizItem;
	private int correctAnswersCnt;
	private int triesCnt; // how many quizzes has been tried
	private HashMap<Integer, Boolean> quizTryMap; // mark quiz numbers as tried to avoid repetition
	private String result;

	public MainViewModel() {
		MyApplication.getInstance().getGlobalComponent().inject(this);
		quizTryMap = new HashMap<>();
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

	@Bindable
	public boolean isQuizFinished() {
		return isQuizFinished;
	}

	public void setQuizFinished(boolean quizFinished) {
		isQuizFinished = quizFinished;
		notifyPropertyChanged(BR.quizFinished);
	}

	@Bindable
	public String getResult() {
		return result;
	}

	private void setResult(String result) {
		this.result = result;
		notifyPropertyChanged(BR.result);
	}

	/**
	 * Start Quiz and 2 min to solve the quiz
	 * Select random 20
	 */
	public void startQuiz() {
		correctAnswersCnt = 0;

		if (!isQuizStarted) {
			generateRandomQuestions();
		}

		setQuizStarted(true);
		setQuizFinished(false);
		viewFrame.startTimer();

		viewFrame.startQuiz(quizItems.get(currentQuizItem));
	}

	private void generateRandomQuestions() {
		Random random = new Random();
		for (int i = 0; i < QUESTIONS_COUNT; i++) {
			// get random number
			int quizNumber = random.nextInt(quizItems.size());
			// add this number to map marked as not tried
			quizTryMap.put(quizNumber, false);

			// if we haven't selected first
			if (currentQuizItem == 0) {
				currentQuizItem = quizNumber;
			}
		}
	}

	private void loadNextQuiz() {
		if (triesCnt >= QUESTIONS_COUNT) {
			onFinished();
			return;
		}

		for (Map.Entry<Integer, Boolean> entry : quizTryMap.entrySet()) {
			Boolean isQuizTried = entry.getValue();
			if (isQuizTried) {
				continue;
			}

			// set quiz number
			currentQuizItem = entry.getKey();
		}

		viewFrame.startQuiz(quizItems.get(currentQuizItem));
	}

	private void onFinished() {
		setResult("Final score " + correctAnswersCnt + "/" + QUESTIONS_COUNT);    // TODO put in xml and use resources
		setQuizStarted(false);
		setQuizFinished(true);

		viewFrame.showResults();
	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //

	@Override
	public void onResume(MainContract.ViewFrame viewFrame) {
		this.viewFrame = viewFrame;

		if (!isQuizStarted) {
			setLoading(true);
			dataLoader.loadQuizzes(this);
		}
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
		QuizItem quizItem = quizItems.get(currentQuizItem);
		String correctAnswer = quizItem.getUrls().get(CORRECT);

		if (event.getSelectedAnswer().equals(correctAnswer)) {
			correctAnswersCnt++;
		}
		triesCnt++;
		quizTryMap.put(currentQuizItem, true);

		loadNextQuiz();
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(LoadNextQuizEvent event) {
		quizTryMap.put(currentQuizItem, true);
		loadNextQuiz();
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(TimerFinishedEvent event) {
		onFinished();
	}


}
