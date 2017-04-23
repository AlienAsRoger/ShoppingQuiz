package com.developer4droid.shoppingquiz.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.developer4droid.shoppingquiz.R;
import com.developer4droid.shoppingquiz.databinding.ActivityMainBinding;
import com.developer4droid.shoppingquiz.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	private MainViewModel viewModel;

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

	/**
	 * Initiate views after main objects
	 */
	private void initViews() {

	}

	// ------------------------ //
	// Interface Implementation //
	// ------------------------ //

}
