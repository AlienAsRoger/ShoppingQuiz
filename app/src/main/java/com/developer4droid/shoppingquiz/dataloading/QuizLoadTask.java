package com.developer4droid.shoppingquiz.dataloading;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import com.developer4droid.shoppingquiz.model.BaseResponse;
import com.developer4droid.shoppingquiz.model.QuizItem;
import com.developer4droid.shoppingquiz.network.DataReceiver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 8:04
 */

public class QuizLoadTask extends AsyncTask<AssetManager, Void, List<QuizItem>> {

	private WeakReference<DataReceiver<List<QuizItem>>> dataReceiver;

	public QuizLoadTask(DataReceiver<List<QuizItem>> dataReceiver) {

		this.dataReceiver = new WeakReference<>(dataReceiver);
	}

	@Override
	protected List<QuizItem> doInBackground(AssetManager... assetManagers) {

		return loadJsonFromAsset(assetManagers[0]);
	}

	@Override
	protected void onPostExecute(List<QuizItem> quizItems) {
		super.onPostExecute(quizItems);

		DataReceiver<List<QuizItem>> receiver = dataReceiver.get();
		if (receiver != null) {
			receiver.onDataReceived(quizItems);
		}
	}

	private List<QuizItem> loadJsonFromAsset(AssetManager assetManager) {
		BaseResponse baseResponse;
		try {
			InputStream is = assetManager.open("response.json");

			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			Gson gson = new	Gson();
			baseResponse = gson.fromJson(new String(buffer, "UTF-8"), BaseResponse.class);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return baseResponse.getItems();
	}
}
