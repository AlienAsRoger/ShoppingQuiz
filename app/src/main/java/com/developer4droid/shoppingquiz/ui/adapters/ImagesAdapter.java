package com.developer4droid.shoppingquiz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.developer4droid.shoppingquiz.R;
import com.developer4droid.shoppingquiz.databinding.QuizGridItemBinding;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.viewmodel.QuizViewModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 10:22
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.QuizViewHolder> {

	private List<QuizItem> itemsList;

	public ImagesAdapter(List<QuizItem> itemsList) {
		this.itemsList = itemsList;
	}

	@Override
	public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View itemView = inflater.inflate(R.layout.quiz_grid_item, parent, false);
		QuizViewModel viewModel = new QuizViewModel();
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

	public void updateItems(List<QuizItem> itemList) {
		this.itemsList = itemList;
		notifyDataSetChanged();
	}

	static class QuizViewHolder extends RecyclerView.ViewHolder {

		private QuizGridItemBinding binding;
		private QuizViewModel viewModel;

		public QuizViewHolder(View itemView, QuizGridItemBinding binding, QuizViewModel viewModel) {
			super(itemView);
			this.binding = binding;
			this.viewModel = viewModel;
		}

		public void setItem(QuizItem quiz) {
			viewModel.setQuiz(quiz);
			binding.executePendingBindings();
		}
	}
}
