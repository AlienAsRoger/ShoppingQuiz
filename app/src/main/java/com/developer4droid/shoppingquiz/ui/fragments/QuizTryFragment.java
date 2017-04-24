package com.developer4droid.shoppingquiz.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.developer4droid.shoppingquiz.R;
import com.developer4droid.shoppingquiz.databinding.FragmentQuizBinding;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.ui.adapters.QuizAnswersAdapter;
import com.developer4droid.shoppingquiz.ui.interfaces.QuizTryContract;
import com.developer4droid.shoppingquiz.viewmodel.QuizTryViewModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:37
 */

public class QuizTryFragment extends Fragment implements QuizTryContract.ViewFrame {

	public static final String QUIZ = "quiz";

	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;

	private FragmentQuizBinding binding;
	private QuizTryViewModel viewModel;
	private QuizAnswersAdapter adapter;
	private QuizItem quizItem;

	public static QuizTryFragment createInstance(QuizItem quizItem) {
		QuizTryFragment fragment = new QuizTryFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(QUIZ, quizItem);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			quizItem = savedInstanceState.getParcelable(QUIZ);
		} else {
			quizItem = getArguments().getParcelable(QUIZ);
		}

		viewModel = new QuizTryViewModel(quizItem);
		init();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false);
		View view = binding.getRoot();
		
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initViews();
	}

	@Override
	public void onResume() {
		super.onResume();

		binding.setModel(viewModel);

		viewModel.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();

		viewModel.unRegister();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelable(QUIZ, quizItem);
	}

	// ------------- //
	// Local methods //
	// ------------- //

	/**
	 * Init ViewModel
	 */
	private void init() {
		adapter = new QuizAnswersAdapter(null);
	}

	/**
	 * Initiate views after main objects
	 */
	private void initViews() {
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_span_count));
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //

	@Override
	public void showAnswers(List<String> answers) {
		adapter.updateItems(answers);
	}
}
