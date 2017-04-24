package com.developer4droid.shoppingquiz.viewmodel;

import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.developer4droid.shoppingquiz.BR;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.LoadNextQuizEvent;
import com.developer4droid.shoppingquiz.events.QuizCompleteEvent;
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

public class MainViewModel extends BaseViewModel implements MainContract.ActionListener, DataReceiver<List<QuizItem>>,
	Parcelable {

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
		triesCnt = 0;
		quizTryMap.clear();

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

		// search for next not tried quiz
		for (Map.Entry<Integer, Boolean> entry : quizTryMap.entrySet()) {
			Boolean isQuizTried = entry.getValue();
			if (!isQuizTried) {
				// set quiz number
				currentQuizItem = entry.getKey();
				break;
			}
		}

		viewFrame.startQuiz(quizItems.get(currentQuizItem));
	}

	private void onFinished() {
		eventBus.post(new QuizCompleteEvent());

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

		if (!isQuizStarted || quizItems == null) {
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

	// ---------- //
	// Parcelable //
	// ---------- //

	/**
	 * We need to copy since we need to re-inject items
	 */
	public void copyFromRestored(MainViewModel viewModel) {
		isLoading = viewModel.isLoading;
		isQuizStarted = viewModel.isQuizStarted;
		isQuizFinished = viewModel.isQuizFinished;
		currentQuizItem = viewModel.currentQuizItem;
		correctAnswersCnt = viewModel.correctAnswersCnt;
		triesCnt = viewModel.triesCnt;
		quizTryMap = viewModel.quizTryMap;
		result = viewModel.result;
	}

	protected MainViewModel(Parcel in) {
		isLoading = in.readByte() != 0x00;
		isQuizStarted = in.readByte() != 0x00;
		isQuizFinished = in.readByte() != 0x00;
		currentQuizItem = in.readInt();
		correctAnswersCnt = in.readInt();
		triesCnt = in.readInt();
		quizTryMap = (HashMap) in.readValue(HashMap.class.getClassLoader());
		result = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (isLoading ? 0x01 : 0x00));
		dest.writeByte((byte) (isQuizStarted ? 0x01 : 0x00));
		dest.writeByte((byte) (isQuizFinished ? 0x01 : 0x00));
		dest.writeInt(currentQuizItem);
		dest.writeInt(correctAnswersCnt);
		dest.writeInt(triesCnt);
		dest.writeValue(quizTryMap);
		dest.writeString(result);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<MainViewModel> CREATOR = new Parcelable.Creator<MainViewModel>() {
		@Override
		public MainViewModel createFromParcel(Parcel in) {
			return new MainViewModel(in);
		}

		@Override
		public MainViewModel[] newArray(int size) {
			return new MainViewModel[size];
		}
	};

}
