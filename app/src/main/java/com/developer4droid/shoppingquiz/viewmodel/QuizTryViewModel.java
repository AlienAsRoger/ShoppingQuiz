package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.*;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.ui.interfaces.QuizTryContract;
import com.developer4droid.shoppingquiz.utils.Utils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:39
 */

public class QuizTryViewModel extends BaseViewModel implements QuizTryContract.ActionListener {

	private static final int ANSWER_SIZE = 4;
	private QuizItem quizItem;
	private String prompt;
	private String timer;
	private boolean isAnswerSelected;
	private boolean isTimerActive;
	private String selectedAnswer;
	private HashMap<Integer, Boolean> answerUsedMap;
	List<String> answers;
	private boolean answerGenerated;

	public QuizTryViewModel(QuizItem quizItem) {
		MyApplication.getInstance().getGlobalComponent().inject(this);

		this.quizItem = quizItem;
		setPrompt(quizItem.getName());
		setTimerActive(true);
		answerUsedMap = new HashMap<>();
		answers = new ArrayList<>();
	}

	@Bindable
	public String getPrompt() {
		return prompt;
	}

	private void setPrompt(String prompt) {
		this.prompt = prompt;
		notifyPropertyChanged(BR.prompt);
	}

	@Bindable
	public String getTimer() {
		return timer;
	}

	private void setTimer(String timer) {
		this.timer = timer;
		notifyPropertyChanged(BR.timer);
	}

	@Bindable
	public boolean isAnswerSelected() {
		return isAnswerSelected;
	}

	private void setAnswerSelected(boolean answerSelected) {
		isAnswerSelected = answerSelected;
		notifyPropertyChanged(BR.answerSelected);
	}

	@Bindable
	public boolean isTimerActive() {
		return isTimerActive;
	}

	private void setTimerActive(boolean timerActive) {
		isTimerActive = timerActive;
		notifyPropertyChanged(BR.timerActive);
	}

	public void submitAnswer() {
		eventBus.post(new SubmitAnswerEvent(selectedAnswer));
	}

	public void getNextQuiz() {
		eventBus.post(new LoadNextQuizEvent());
	}

	public void onResume(QuizTryContract.ViewFrame viewFrame) {
		registerBus();

		generateRandomAnswers(viewFrame);
		viewFrame.showAnswers(answers);
	}

	private void generateRandomAnswers(QuizTryContract.ViewFrame viewFrame) {
		if (answerGenerated) {
			return;
		}

		// pre fill with false
		for (int i = 0; i < ANSWER_SIZE; i++) {
			answerUsedMap.put(i, false);
		}

		// generate random order
		Random random = new Random();
		for (int i = 0; i < ANSWER_SIZE; i++) {
			int answerNumber = random.nextInt(ANSWER_SIZE);
			// loop while we search that we didn't use yet
			while (answerUsedMap.get(answerNumber)) {
				answerNumber = random.nextInt(ANSWER_SIZE);
			}
			// save to map
			answerUsedMap.put(answerNumber, true);

			answers.add(quizItem.getUrls().get(answerNumber));
		}

		answerGenerated = true;
	}

	// --------- //
	// Event Bus //
	// --------- //

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(AnswerSelectedEvent event) {
		selectedAnswer = event.getAnswer();
		setAnswerSelected(true);
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(TimerUpdateEvent event) {
		setTimer(Utils.getTimeFromMilliseconds(event.getMillisecondsLeft()));
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(TimerFinishedEvent event) {
		setTimerActive(false);
	}

}
