package com.fireae.voa.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fireae.voa.R;

public class RefreshableView extends LinearLayout implements OnTouchListener{

	//状态
	/*
	 * 下拉状态
	 */
	public static final int STATUS_PULL_TO_REFRESH = 0;
	/*
	 * 释放立即刷新状态
	 */
	public static final int STATUS_RELEASE_TO_REFRESH = 1;
	/*
	 × 正在刷新状态
	 */
	public static final int STATUS_REFRESHING = 2;
	
	/*
	 * 刷新完成或未刷新状态
	 */
	public static final int STATUS_REFRESH_FINISHED = 3;
	
	/*
	 * 下拉头部回滚的速度
	 */
	public static final int SCROLL_SPEED = -20;
	
	/*
	 * 时间
	 */
	public static final long ONE_MINUTE = 60 * 1000;
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	public static final long ONE_DAY = 24 * ONE_HOUR;
	public static final long ONE_MONTH = 30 * ONE_DAY;
	public static final long ONE_YEAR = 12 * ONE_MONTH;
	
	/*
	 * 上次更新时间的字符串常量，用于作为SharedPreferences 的键值。
	 */
	public static final String UPDATE_AT = "updated_at";
	private SharedPreferences preferences;
	
	/*
	 * 下拉刷新的回调接口
	 */
	private PullToRefreshListener refreshListener;
	
	/*
	 * 下拉头的View
	 */
	private View header;
	
	/*
	 * 需要下拉刷新的listView
	 */
	private ListView listView;
	/*
	 * 刷新时显示的进度条
	 */
	private ProgressBar progressBar;
	
	/*
	 * 指示下拉和释放的箭头
	 */
	private ImageView arrow;
	
	/*
	 * 指示下拉和释放的文字描述
	 */
	private TextView description;
	
	/*
	 * 上次更新时间的文字描述
	 */
	private TextView updateAt;
	
	/*
	 * 下拉头的布局参数
	 */
	private MarginLayoutParams headerLayoutParams;
	
	/*
	 * 上次更新时间的毫秒值
	 */
	private long lastUpdateTime;
	
	/*
	 * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来区分。
	 */
	private int id = -1;
	
	/*
	 * 下拉头的高度
	 */
	private int hideHeaderHeight;
	
	/*
	 * 当前处于什么状态
	 */
	private int curStatus = STATUS_REFRESH_FINISHED;
	
	/*
	 * 上次的状态
	 */
	private int lastStatus = curStatus; 
	
	/*
	 * 手指按下时的屏幕纵坐标
	 */
	private float yDown;
	
	/*
	 * 在被判定为滚动之前用户手指可以移动的最大值。
	 */
	private int touchSlop;
	
	/*
	 * 是否已加载一次layout,这里onLayout中的初始化只需加载一次。
	 */
	private boolean loadOnce;
	
	/*
	 * 当前是否可以下拉，只有listView滚动到头的时候才允许下拉。
	 */
	private boolean ableToPull;
	
	public RefreshableView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public RefreshableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true);
		progressBar = (ProgressBar)header.findViewById(R.id.progress_bar);
		arrow = (ImageView)header.findViewById(R.id.arrow);
		updateAt = (TextView)header.findViewById(R.id.update_at);
		description = (TextView)header.findViewById(R.id.description);
		
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		refreshUpdatedAtValue();
		setOrientation(VERTICAL);
		addView(header, 0);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			hideHeaderHeight = -header.getHeight();
			headerLayoutParams = (MarginLayoutParams)header.getLayoutParams();
			headerLayoutParams.topMargin = hideHeaderHeight;
			listView = (ListView)getChildAt(1);
			listView.setOnTouchListener(this);
			loadOnce = true;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		setIsAbleToPull(event);
		if (ableToPull) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				yDown = event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				float yMove = event.getRawY();
				int distance = (int)(yMove - yDown);
				if (distance <= 0 && headerLayoutParams.topMargin <= hideHeaderHeight) {
					return false;
				}
				
				if (distance < touchSlop) {
					return false;
				}
				
				if (curStatus != STATUS_REFRESHING) {
					if (headerLayoutParams.topMargin > 0) {
						curStatus = STATUS_RELEASE_TO_REFRESH;
					} else {
						curStatus = STATUS_PULL_TO_REFRESH;
					}
					
					headerLayoutParams.topMargin = (distance / 2) + hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
				break;
				
			case MotionEvent.ACTION_UP:
			default:
				if (curStatus == STATUS_RELEASE_TO_REFRESH) {
					new RefreshingTask().execute();
				} else if (curStatus == STATUS_PULL_TO_REFRESH) {
					new HideHeaderTask().execute();
				}
				break;
			}
			
			if (curStatus == STATUS_PULL_TO_REFRESH ||
				curStatus == STATUS_RELEASE_TO_REFRESH) {
				updateHeaderView();
				
				listView.setPressed(false);
				listView.setFocusable(false);
				listView.setFocusableInTouchMode(false);
				lastStatus = curStatus;
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 根据当前ListView的滚动状态来设定{@link #ableToPull}的值，
	 * 每次都需要在onTouch中第一个执行，这样可以判断当前应该是滚动ListView,还是应该下拉。
	 */
	private void setIsAbleToPull(MotionEvent event) {
		View firstChild = listView.getChildAt(0);
		if (firstChild != null) {
			int firstVisiblePos = listView.getFirstVisiblePosition();
			if (firstVisiblePos == 0 && firstChild.getTop() == 0) {

				if (!ableToPull) {
					yDown = event.getRawY();
				}
				
				ableToPull = true;
			} else {
				if (headerLayoutParams.topMargin != hideHeaderHeight) {
					headerLayoutParams.topMargin = hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
				ableToPull = false;
			}
		} else {
			ableToPull = true;
		}
	}
	
	public void setOnRefreshListener(PullToRefreshListener listener, int id){
		this.refreshListener = listener;
		this.id  = id;
	}
	
	public void finishedRefreshing() {
		curStatus = STATUS_REFRESH_FINISHED;
		preferences.edit().putLong(UPDATE_AT + id, System.currentTimeMillis());
		new HideHeaderTask().execute();
	}
	
	/*
	 * 更新下拉头中的信息
	 */
	private void updateHeaderView() {
		if (lastStatus != curStatus) {
			if (curStatus == STATUS_PULL_TO_REFRESH) {
				description.setText(getResources().getString(R.string.app_name));
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
				
			} else if (curStatus == STATUS_RELEASE_TO_REFRESH) {
				description.setText(getResources().getString(R.string.app_name));
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
				
			} else if (curStatus == STATUS_REFRESHING) {
				description.setText(getResources().getString(R.string.app_name));
				progressBar.setVisibility(View.VISIBLE);
				arrow.clearAnimation();
				arrow.setVisibility(View.GONE);
			}
			
		}
	}
	
	/*
	 * 根据当前状态来旋转箭头
	 */
	private void rotateArrow() {
		float privotX = arrow.getWidth() / 2f;
		float privotY = arrow.getHeight() / 2f;
		float fromDegress = 0f;
		float toDegress = 0f;
		if (curStatus == STATUS_PULL_TO_REFRESH) {
			fromDegress = 180f;
			toDegress = 360f;
		} else if (curStatus == STATUS_RELEASE_TO_REFRESH) {
			fromDegress = 0f;
			toDegress = 180f;
		}
		
		RotateAnimation animation = new RotateAnimation(fromDegress, toDegress, privotX, privotY);
		animation.setDuration(100);
		animation.setFillAfter(true);
		arrow.startAnimation(animation);
	}
	
	/*
	 * 刷新下拉头中上次更新时间的文字描述
	 */
	private void refreshUpdatedAtValue() {
		lastUpdateTime = preferences.getLong(UPDATE_AT + id, -1);
		long curTime = System.currentTimeMillis();
		long timePassed = curTime - lastUpdateTime;
		long timeIntoFormat;
		String updateAtValue;
		if (lastUpdateTime == -1) {
			updateAtValue = getResources().getString(R.string.app_name);
		} else if (timePassed < 0) {
			updateAtValue = getResources().getString(R.string.app_name);
		} else if (timePassed < ONE_MINUTE) {
			updateAtValue = getResources().getString(R.string.app_name);
		} else if (timePassed < ONE_HOUR) {
			timeIntoFormat = timePassed / ONE_MINUTE;
			String value = timeIntoFormat + "分钟";
			updateAtValue = String.format(getResources().getString(R.string.app_name), value);
		} else if (timePassed < ONE_DAY) {
			timeIntoFormat = timePassed / ONE_HOUR;
			String value = timeIntoFormat + "小时";
			updateAtValue = String.format(getResources().getString(R.string.app_name), value);
			
		} else if (timePassed < ONE_MONTH) {
			timeIntoFormat = timePassed / ONE_DAY;
			String value = timeIntoFormat + "天";
			updateAtValue = String.format(getResources().getString(R.string.app_name), value);
			
		} else if (timePassed < ONE_YEAR) {
			timeIntoFormat = timePassed / ONE_MONTH;
			String value = timeIntoFormat + "个月";
			updateAtValue = String.format(getResources().getString(R.string.app_name), value);
			
		} else {
			timeIntoFormat = timePassed / ONE_YEAR;
			String value = timeIntoFormat + "年";
			updateAtValue = String.format(getResources().getString(R.string.app_name), value);
		}
		updateAt.setText(updateAtValue);
	}

	/*
	 * 正在刷新的任务，在此任务中会去回调注册进来的下拉刷新监听器。
	 */
	class RefreshingTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= 0) {
					topMargin = 0;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			
			curStatus = STATUS_REFRESHING;
			publishProgress(0);
			if (refreshListener != null) {
				refreshListener.onRefresh();
			}
			return null;
		}
		
		@Override 
		protected void onProgressUpdate(Integer... topMargin) {
			updateHeaderView();
			headerLayoutParams.topMargin = topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}
	}
	
	
	
	/*
	 * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏。
	 */
	class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= hideHeaderHeight) {
					topMargin = hideHeaderHeight;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			return topMargin;
		}
		
		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			headerLayoutParams.topMargin = topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}
		
		@Override
		protected void onPostExecute(Integer topMargin) {
			headerLayoutParams.topMargin = topMargin;
			header.setLayoutParams(headerLayoutParams);
			curStatus = STATUS_REFRESH_FINISHED;
			
		}
	}
	
	/*
	 * 使当前线程睡眠制定的毫秒数
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 下拉刷新的监听器
	 */
	public interface PullToRefreshListener {
		/*
		 * 刷新时会调用此方法
		 */
		void onRefresh();
	}
}
