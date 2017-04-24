package com.developer4droid.shoppingquiz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.developer4droid.shoppingquiz.R;
import com.developer4droid.shoppingquiz.databinding.QuizGridItemBinding;
import com.developer4droid.shoppingquiz.ui.interfaces.ImageLoader;
import com.developer4droid.shoppingquiz.viewmodel.QuizAnswerViewModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:22
 */

public class QuizAnswersAdapter extends RecyclerView.Adapter<QuizAnswersAdapter.QuizViewHolder> {

	private List<String> itemsList;

	public QuizAnswersAdapter(List<String> itemsList) {
		this.itemsList = itemsList;
	}

	@Override
	public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View itemView = inflater.inflate(R.layout.quiz_grid_item, parent, false);
		QuizAnswerViewModel viewModel = new QuizAnswerViewModel();
		QuizGridItemBinding binding = QuizGridItemBinding.bind(itemView);
		binding.setModel(viewModel);

		return new QuizViewHolder(itemView, binding, viewModel);
	}

	@Override
	public void onBindViewHolder(QuizViewHolder holder, int position) {
		holder.setItem(itemsList.get(position));
	}

	@Override
	public int getItemCount() {
		return itemsList == null ? 0 : itemsList.size();
	}

	public void updateItems(List<String> itemList) {
		this.itemsList = itemList;
		notifyDataSetChanged();
	}

	static class QuizViewHolder extends RecyclerView.ViewHolder implements ImageLoader {

		private QuizGridItemBinding binding;
		private QuizAnswerViewModel viewModel;

		public QuizViewHolder(View itemView, QuizGridItemBinding binding, QuizAnswerViewModel viewModel) {
			super(itemView);
			this.binding = binding;
			this.viewModel = viewModel;
		}

		public void setItem(String answer) {
			viewModel.setAnswer(answer, this);
			binding.executePendingBindings();
		}

		@Override
		public void loadImage(String uri) {
			Glide.with(binding.imageView.getContext())
					.load(uri)
					.into(binding.imageView);
		}
	}
}
