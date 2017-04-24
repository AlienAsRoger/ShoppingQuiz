package com.developer4droid.shoppingquiz.inject.components;

import com.developer4droid.shoppingquiz.inject.AppModule;
import com.developer4droid.shoppingquiz.inject.NetworkModule;
import com.developer4droid.shoppingquiz.network.DataLoaderImpl;
import com.developer4droid.shoppingquiz.ui.activities.BaseActivity;
import com.developer4droid.shoppingquiz.viewmodel.BaseViewModel;
import com.developer4droid.shoppingquiz.viewmodel.MainViewModel;
import com.developer4droid.shoppingquiz.viewmodel.QuizTryViewModel;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 7:47
 */

@Singleton
@Component(modules = {
		NetworkModule.class,
		AppModule.class
})
public interface GlobalComponent {

	void inject(BaseActivity model);

	void inject(BaseViewModel model);

	void inject(MainViewModel model);
	
	void inject(QuizTryViewModel model);

	void inject(DataLoaderImpl dataLoader);
}
