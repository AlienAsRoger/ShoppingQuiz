package com.developer4droid.shoppingquiz.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import butterknife.ButterKnife;
import com.developer4droid.shoppingquiz.R;
import com.developer4droid.shoppingquiz.background.BackgroundTimerService;
import com.developer4droid.shoppingquiz.databinding.ActivityMainBinding;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.ui.fragments.QuizTryFragment;
import com.developer4droid.shoppingquiz.ui.interfaces.MainContract;
import com.developer4droid.shoppingquiz.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity implements MainContract.ViewFrame {


	public static final String STARTED = "started";
	private ActivityMainBinding binding;
	private MainViewModel viewModel;
	private boolean isQuizStarted;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		// Use butter knife for fast binding
		ButterKnife.bind(this);

		if (savedInstanceState != null) {
			isQuizStarted = savedInstanceState.getBoolean(STARTED);
		}

		init();
	}

	@Override
	protected void onResume() {
		super.onResume();

		binding.setModel(viewModel);

		viewModel.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		viewModel.unRegister();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putBoolean(STARTED, viewModel.isQuizStarted());
	}

	// ------------- //
	// Local methods //
	// ------------- //

	/**
	 * Init ViewModel
	 */
	private void init() {
		viewModel = new MainViewModel();
		viewModel.setQuizStarted(isQuizStarted);
	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //


	/**
	 * Start background timer
	 */
	@Override
	public void startTimer() {
		startService(new Intent(this, BackgroundTimerService.class));
	}

	@Override
	public void startQuiz(QuizItem quizItem) {
		// open fragment with quiz
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, QuizTryFragment.createInstance(quizItem));
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void showResults() {
		Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
		if (fragment != null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.remove(fragment);
			transaction.commitAllowingStateLoss();
		}
	}
}
