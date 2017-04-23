package com.developer4droid.shoppingquiz.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.developer4droid.shoppingquiz.R;
import com.developer4droid.shoppingquiz.databinding.ActivityMainBinding;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.ui.adapters.ImagesAdapter;
import com.developer4droid.shoppingquiz.ui.interfaces.QuizContract;
import com.developer4droid.shoppingquiz.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements QuizContract.ViewFrame{

	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;

	private ActivityMainBinding binding;
	private MainViewModel viewModel;
	private ImagesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		// Use butter knife for fast binding
		ButterKnife.bind(this);

		init();
		initViews();
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
		adapter = new ImagesAdapter(null);
		viewModel = new MainViewModel();
	}

	/**
	 * Initiate views after main objects
	 */
	private void initViews() {
		GridLayoutManager layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_span_count));
		recyclerView.setLayoutManager(layoutManager);
		DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
				layoutManager.getOrientation());
		recyclerView.addItemDecoration(dividerItemDecoration);
		recyclerView.setAdapter(adapter);
	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //

	@Override
	public void updateData(List<QuizItem> itemList) {
		adapter.updateItems(itemList);

	}
}
