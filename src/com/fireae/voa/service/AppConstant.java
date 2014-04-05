package com.fireae.voa.service;

public interface AppConstant {
	public class PlayerMsg {
		public static final int PLAY = 1;
		public static final int PAUSE = 2;
		public static final int STOP = 3;
		public static final int CONTINUE = 4;
	}
	
	public class PlayAction {
		public static final String UPDATE_ACTION = "com.fireae.voa.UPDATE_ACTION";
		public static final String CTL_ACTION = "com.fireae.voa.CTL_ACTION";
		public static final String MUSIC_CURRENT = "com.fireae.voa.MUSIC_CURRENT";
		public static final String MUSIC_DURATION = "com.fireae.voa.MUSIC_DURATION";
		public static final String MUSIC_SERVICE = "com.fireae.voa.MUSIC_SERVICE";
	}
	
	public class IntentKey {
		public static final String KEY_CURPOSITION = "curPosition";
		public static final String KEY_DURATION = "duration";
		public static final String KEY_VOICEURL = "voiceUrl";
		public static final String KEY_MSG	    = "msg";
	}
}
