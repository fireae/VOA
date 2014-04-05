package com.fireae.voa;


import com.fireae.voa.activity.CategoryActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {

	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		handler = new Handler();
		SplashRunnable splashRunnable = new SplashRunnable(this);
		new Thread(splashRunnable).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	@Override
	public void onBackPressed(){
		
	}

	public class SplashRunnable implements Runnable {
		
		private Activity context;
		public SplashRunnable(Activity aty) {
			context = aty;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.postDelayed(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent =new Intent();
					intent.setClass(MainActivity.this, CategoryActivity.class);
					startActivity(intent);
					context.finish();
				}
				
			}, 2000);
			
		}
	}
}
