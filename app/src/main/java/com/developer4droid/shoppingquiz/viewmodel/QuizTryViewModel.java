package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.AnswerSelectedEvent;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.ui.interfaces.QuizTryContract;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:39
 */

public class QuizTryViewModel extends BaseViewModel implements QuizTryContract.ActionListener {

	private QuizItem quizItem;
	private String prompt;
	private QuizTryContract.ViewFrame viewFrame;

	public QuizTryViewModel(QuizItem quizItem) {
		MyApplication.getInstance().getGlobalComponent().inject(this);

		this.quizItem = quizItem;
		setPrompt(quizItem.getName());
	}

	@Bindable
	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
		notifyPropertyChanged(BR.prompt);
	}

	public void submitAnswer() {

	}

	public void onResume(QuizTryContract.ViewFrame viewFrame) {
		this.viewFrame = viewFrame;
		// TODO randomize urls in positions
//		new Random().nextInt(4)
		viewFrame.showAnswers(quizItem.getUrls());
	}

	// --------- //
	// Event Bus //
	// --------- //

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(AnswerSelectedEvent event) {

	}

}
