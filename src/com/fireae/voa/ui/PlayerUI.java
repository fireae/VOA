package com.fireae.voa.ui;

import com.fireae.voa.service.AppConstant;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayerUI {
	
	public static SeekBar seekBarPlayer = null; // 播放的进度条
	public static TextView txtTime = null;		//时间
	public static Button btnPlay = null;			//播放按钮
	public static Button btnPause = null;		//暂停按钮
	public static int USR_ACTION = AppConstant.PlayerMsg.PLAY;
	
}
