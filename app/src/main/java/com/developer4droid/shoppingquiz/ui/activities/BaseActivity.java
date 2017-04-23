package com.developer4droid.shoppingquiz.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.developer4droid.shoppingquiz.application.MyApplication;
import com.developer4droid.shoppingquiz.events.StubEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: roger developer4droid@gmail.com
 * Date: 23.04.2017
 * Time: 9:40
 */

public abstract class BaseActivity  extends AppCompatActivity {

	@Inject
	protected EventBus eventBus;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().getGlobalComponent().inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		eventBus.register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		eventBus.unregister(this);
	}

	/**
	 * Stub. Required here to not throw exception that no method with @Subscribe annotation
	 */
	@Subscribe
	public void onEvent(StubEvent event) {
	}
}
