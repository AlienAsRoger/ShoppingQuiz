package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.events.AnswerSelectedEvent;
import com.developer4droid.shoppingquiz.ui.interfaces.ImageLoader;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:25
 */

public class QuizAnswerViewModel extends BaseViewModel {

	private String answer;
	private boolean isSelected;

	public void setAnswer(String answer, ImageLoader imageLoader) {
		this.answer = answer;
		imageLoader.loadImage(answer); // we treat answer as url here, but it can be object
		registerBus();
	}

	public void selectAnswer() {
		setSelected(true);
		eventBus.post(new AnswerSelectedEvent(answer));
	}

	@Bindable
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
		notifyPropertyChanged(BR.selected);
	}

	// --------- //
	// Event Bus //
	// --------- //

	@SuppressWarnings("unused")
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(AnswerSelectedEvent event) {
		setSelected(event.getAnswer().equals(answer));
	}
}
