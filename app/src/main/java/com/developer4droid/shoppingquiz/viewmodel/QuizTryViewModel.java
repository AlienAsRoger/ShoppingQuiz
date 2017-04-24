package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import android.util.Log;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.AnswerSelectedEvent;
import com.developer4droid.shoppingquiz.events.SubmitAnswerEvent;
import com.developer4droid.shoppingquiz.events.TimerFinishedEvent;
import com.developer4droid.shoppingquiz.events.TimerUpdateEvent;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.ui.interfaces.QuizTryContract;
import com.developer4droid.shoppingquiz.utils.Utils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:39
 */

public class QuizTryViewModel extends BaseViewModel implements QuizTryContract.ActionListener {

	private QuizTryContract.ViewFrame viewFrame;

	private QuizItem quizItem;
	private String prompt;
	private String timer;
	private boolean isAnswerSelected;
	private boolean isTimerActive;
	private String selectedAnswer;

	public QuizTryViewModel(QuizItem quizItem) {
		MyApplication.getInstance().getGlobalComponent().inject(this);

		this.quizItem = quizItem;
		setPrompt(quizItem.getName());
		setTimerActive(true);
	}

	@Bindable
	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
		notifyPropertyChanged(BR.prompt);
	}

	@Bindable
	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
		notifyPropertyChanged(BR.timer);
	}

	@Bindable
	public boolean isAnswerSelected() {
		return isAnswerSelected;
	}

	public void setAnswerSelected(boolean answerSelected) {
		isAnswerSelected = answerSelected;
		notifyPropertyChanged(BR.answerSelected);
	}

	@Bindable
	public boolean isTimerActive() {
		return isTimerActive;
	}

	public void setTimerActive(boolean timerActive) {
		isTimerActive = timerActive;
		notifyPropertyChanged(BR.timerActive);
	}

	public void submitAnswer() {
		eventBus.post(new SubmitAnswerEvent(selectedAnswer));
	}

	public void onResume(QuizTryContract.ViewFrame viewFrame) {
		this.viewFrame = viewFrame;
		// TODO randomize urls in positions
//		new Random().nextInt(4)
		viewFrame.showAnswers(quizItem.getUrls());
		registerBus();
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
		Log.d("TEST", "TimerUpdateEvent: " + Utils.getTimeFromMilliseconds(event.getMillisecondsLeft()));
		
		setTimer(Utils.getTimeFromMilliseconds(event.getMillisecondsLeft()));
	}

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(TimerFinishedEvent event) {
		Log.d("TEST", "TimerFinishedEvent: ");
		setTimerActive(false);

	}


}
