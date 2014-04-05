package com.fireae.voa.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firea.voa.parser.ContentData;
import com.firea.voa.parser.ContentParser;
import com.fireae.voa.R;
import com.fireae.voa.service.AppConstant;
import com.fireae.voa.service.PlayerService;

public class ContentActivity extends Activity {
	public static final String TAG = "ContentActivity";
	// 显示相关
	private TextView txtContentTitle;		//标题
	private TextView txtVoaContent;			//文本内容
	private ScrollView scrollViewContent;	//滚动区域
	private WebView webViewContent;

	// 播放器相关
	private Button btnMediaCtl;				//播放控制按钮
	private SeekBar seekBarMediaCtl;		//播放滑动条
	private boolean isChange;				//滑动条是否改变
	
	private int curPosition;				// 当前位置
	private int msg;						// 播放器当前状态
	
	// 内容
	private String contentUrl;				//文本内容的url
	private String contentTitle;			//标题
	private String voiceUrl;				//声音的url
	
	
	private Handler handler;				

	//广播
	PlayerReceiver playerReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_content);
		//取控件
		getViews();
		setListener();
		
		// 设置播放参数
		msg = AppConstant.PlayerMsg.PLAY;
		
		// 取参数
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		contentUrl = bundle.getString("contentUrl");
		contentTitle = bundle.getString("title");
		
		handler = new Handler();
		
		// 显示标题和文本
		new Thread(new ContentLoadable()).start();
		
		// 广播
		playerReceiver = new PlayerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(AppConstant.PlayAction.MUSIC_CURRENT);
		filter.addAction(AppConstant.PlayAction.MUSIC_DURATION);
		
		registerReceiver(playerReceiver, filter);
	}
	
	private final void getViews() {
		scrollViewContent = (ScrollView)findViewById(R.id.scrollViewContent);
		txtContentTitle = (TextView)findViewById(R.id.txtContentTitle);
		txtVoaContent = (TextView)findViewById(R.id.txtVoaContent);
		btnMediaCtl = (Button)findViewById(R.id.btnMediaCtl);
		seekBarMediaCtl = (SeekBar)findViewById(R.id.seekBarMediaCtl);
		webViewContent = (WebView)findViewById(R.id.webViewContent);
	}
	
	private void setListener() {
		btnMediaCtl.setOnClickListener(new ButtonClickEvent());
		seekBarMediaCtl.setOnSeekBarChangeListener(new SeekBarChangeEvent());
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(playerReceiver);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

	/*
	 * 
	 */
	class ButtonClickEvent implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.i(TAG, "button click play");
			
			Intent intent = new Intent();
			intent.putExtra(AppConstant.IntentKey.KEY_VOICEURL, voiceUrl);
			intent.putExtra(AppConstant.IntentKey.KEY_MSG, msg);
			intent.setClass(ContentActivity.this, PlayerService.class);
			startService(intent);
			
			if (msg == AppConstant.PlayerMsg.PLAY) {
				msg = AppConstant.PlayerMsg.PAUSE;
			} else if (msg == AppConstant.PlayerMsg.PAUSE) {
				msg = AppConstant.PlayerMsg.PLAY;
			} else if (msg == AppConstant.PlayerMsg.STOP) {
				msg = AppConstant.PlayerMsg.PLAY;
			}
		}

	}
	
	/*
	 * 
	 */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			isChange = true;
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			isChange = false;
		}
		
	}
	
	// 加载文本内容的线程
	public class ContentLoadable implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ContentParser contentParser = new ContentParser();
			contentParser.setContentUrl(contentUrl);
			final ContentData contentData = contentParser.getContentData();
			
			handler.post(new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					txtContentTitle.setText(contentTitle);
					Log.i("content parser", contentData.getContent());
					//txtVoaContent.setText(contentData.getContent());
					webViewContent.loadData(contentData.getContent(), "text/html; charset=UTF-8", null);
					voiceUrl = contentData.getVoiceUrl();
				}
				
			}));
		}
	}
	
	
	/*
	 * 用于接受PlayerService 传过来的广播
	 */
	public class PlayerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context ctx, Intent intent) {
			// TODO Auto-generated method stub
			String action  = intent.getAction();
			
			if (action.equals(AppConstant.PlayAction.MUSIC_CURRENT)) {
				Log.i(TAG, "music_current");
				curPosition = intent.getIntExtra(AppConstant.IntentKey.KEY_CURPOSITION, -1);
				Log.i(TAG, Integer.toString(curPosition));
				seekBarMediaCtl.setProgress(curPosition);
			} else if (action.equals(AppConstant.PlayAction.MUSIC_DURATION)) {
				Log.i(TAG, "music duration");
				
				int duration = intent.getIntExtra(AppConstant.IntentKey.KEY_DURATION, -1);
				Log.i(TAG, Integer.toString(duration));
				seekBarMediaCtl.setMax(duration);
			}
		}
		
	}
}
