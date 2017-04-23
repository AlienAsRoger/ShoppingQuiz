package com.developer4droid.shoppingquiz.network;

import android.app.Application;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.dataloading.QuizLoadTask;
import com.developer4droid.shoppingquiz.model.QuizItem;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:55
 */

public class DataLoaderImpl implements DataLoader {

	@Inject
	Application application;

	public DataLoaderImpl() {
		MyApplication.getInstance().getGlobalComponent().inject(this);
	}

	@Override
	public void loadQuizzes(DataReceiver<List<QuizItem>> dataReceiver) {
		new QuizLoadTask(dataReceiver).execute(application.getAssets());
	}
}
