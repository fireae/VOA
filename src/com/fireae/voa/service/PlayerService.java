package com.fireae.voa.service;

import java.io.IOException;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.fireae.voa.ui.PlayerUI;

public class PlayerService extends Service {
	public final static String TAG = "PlayerService"; // log TAg
	
	public static MediaPlayer mediaPlayer = null; //播放器
	private String voiceUrl;						//声音路径
	private boolean isPause;		//暂停
	private boolean isLoop;			//循环播放
	private	int curPosition;		//当前进度
	private int duration;			//播放长度
	private int msg;				//播放信息， play， stop， pause
	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 * @brief handler 用来接受消息，来发送广播更新播放时间
	 */
	private Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (mediaPlayer != null) {
					curPosition = mediaPlayer.getCurrentPosition();
					Intent intent = new Intent();
					intent.setAction(AppConstant.PlayAction.MUSIC_CURRENT);
					intent.putExtra(AppConstant.IntentKey.KEY_CURPOSITION, curPosition);
					sendBroadcast(intent);
					handler.sendEmptyMessageDelayed(1, 1000);
				}
			}
		}
	};//handler
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnCompletionListener(new PlayerOnComletionListener());
		//voiceUrl = "http://down.51voa.com/201403/se-health-malaria-04mar14.mp3";	
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		
		Log.i(TAG, "on destroy");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		voiceUrl = intent.getStringExtra(AppConstant.IntentKey.KEY_VOICEURL);
		msg = intent.getIntExtra(AppConstant.IntentKey.KEY_MSG, AppConstant.PlayerMsg.PLAY);
		Log.i(TAG, Integer.toString(msg));
		switch(msg) {
		case AppConstant.PlayerMsg.PLAY:
			playMusic();
			break;
		case AppConstant.PlayerMsg.PAUSE:
			pause();
			break;
		case AppConstant.PlayerMsg.STOP:
			stop();
			break;
		case AppConstant.PlayerMsg.CONTINUE:
			resume();
			break;
		default:
			break;
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void playMusic() {
		try {
			// 设置播放器
			mediaPlayer.reset();
			mediaPlayer.setDataSource(voiceUrl);
			mediaPlayer.prepare();
			mediaPlayer.start();
			mediaPlayer.setLooping(isLoop);
			mediaPlayer.setOnPreparedListener(new PreparedListener(curPosition));
			handler.sendEmptyMessage(1);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // public void playMusic()
	
	private void pause() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			isPause = true;
		}
	}
	
	private void resume() {
		if (isPause) {
			mediaPlayer.start();
			isPause = false;
		}
	}
	
	private void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private final class PreparedListener implements OnPreparedListener {

		private int curPosition;
		public PreparedListener(int curPosition) {
			this.curPosition = curPosition;
		}
		
		@Override
		public void onPrepared(MediaPlayer mp) {
			// TODO Auto-generated method stub
			mediaPlayer.start();
			if (curPosition > 0) {
				mediaPlayer.seekTo(curPosition);
			}
			
			duration = mediaPlayer.getDuration();
			Intent intent = new Intent();
			intent.setAction(AppConstant.PlayAction.MUSIC_DURATION);
			intent.putExtra(AppConstant.IntentKey.KEY_DURATION, duration);
			sendBroadcast(intent);
		}
		
	}
	
	class PlayerOnComletionListener implements MediaPlayer.OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			mediaPlayer.reset();
		
		}
		
	} // class PlayerOnCompletionListener
	
	public class PlayerServiceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int control = intent.getIntExtra("control", -1);
			switch(control){
			case 1:
				//status = 1;
				break;
			case 2:
				//status = 2;
				break;
			}
		}
		
	}
}
