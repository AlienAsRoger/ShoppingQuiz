package com.developer4droid.shoppingquiz.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import butterknife.ButterKnife;
import com.developer4droid.shoppingquiz.R;
import com.developer4droid.shoppingquiz.databinding.ActivityMainBinding;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.ui.fragments.QuizTryFragment;
import com.developer4droid.shoppingquiz.ui.interfaces.MainContract;
import com.developer4droid.shoppingquiz.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.ViewFrame{


	private ActivityMainBinding binding;
	private MainViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		// Use butter knife for fast binding
		ButterKnife.bind(this);

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

	// ------------- //
	// Local methods //
	// ------------- //

	/**
	 * Init ViewModel
	 */
	private void init() {
		viewModel = new MainViewModel();
	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //

	@Override
	public void startQuiz(List<QuizItem> quizzesToSolve) {
		QuizItem quizItem = quizzesToSolve.get(0);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, QuizTryFragment.createInstance(quizItem));
		transaction.commitAllowingStateLoss();
	}
}
