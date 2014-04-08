package com.pbstudio.circleprogress;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	private static final int START_ANIMATION = 1000;
	private static final int STOP_ANIMATION = 1001;
	
	CircleProgressBar progressBar;
	int progress = 0;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what) {
			case START_ANIMATION:
				progressBar.startAnimation();
				break;
			case STOP_ANIMATION:
				progressBar.stopAnimation();
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		progressBar=(CircleProgressBar)findViewById(R.id.circleProgressbar);
		
		progressBar.setMaxProgress(100);
		progressBar.setProgress(0);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				int i = 0;
				
				mHandler.sendEmptyMessage(START_ANIMATION);
				
				while (i <= 100) {
					progressBar.setProgressNotInUiThread(i);
					i++;
					
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				mHandler.sendEmptyMessage(STOP_ANIMATION);
			}
			
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onAdd(View view) {
		progress += 15;
		
		if (progress >= 100) {
			progress = 100;
		}
		
		progressBar.setProgress(progress);
	}
	
	public void onSub(View view) {
		progress -= 15;
		
		if (progress <= 0) {
			progress = 0;
		}
		
		progressBar.setProgress(progress);
	}
}
