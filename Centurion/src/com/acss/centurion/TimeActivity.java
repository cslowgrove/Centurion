package com.acss.centurion;

import com.example.centurion.R;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends ActionBarActivity {

	private static int shotsSet;
	private static int time = 0;
	private static int no;
	private static TextView shots, shotsHad;
	private static Button statusButton, stopButton;
	private static Handler handler;
	private static Vibrator vibrator;
	private static boolean first = true;
	private static NotificationManager mNotificationManager;

	//How long between delays in miliseconds
	private long delay = 60000; //1 min
	private MediaPlayer buzzer;
	private static boolean running;

	public static Handler getHandler() {
		return handler;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		if (first) {
			first = false;
			setUp();
		} else {
		
		resume();
		}
		

	}

	@Override
	public void onSaveInstanceState(
			Bundle savedInstanceState) {
		// Always call the superclass so it
		// can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}


	private void setUp() {

		Intent intent = getIntent();
			time = intent.getIntExtra("time", 100);


			shotsSet = time;
			no = 0;
			running = false;

		shots = (TextView) findViewById(R.id.shots);

		shotsHad = (TextView) findViewById(R.id.shotsHad);

		shots.setText(getResources().getString(R.string.shots) + " " + time);
		shotsHad.setText(getResources().getString(R.string.shotsHad) + " " + no);

		statusButton = (Button) findViewById(R.id.statusButton);
		statusButton.setText("Start");

		stopButton = (Button) findViewById(R.id.stopButton);
		stopButton.setText("Stop");

		handler = new Handler();

		buzzer = MediaPlayer.create(this, R.raw.buzzer);

		statusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				notification();

				if(!running) {
					handler.postDelayed(runnable, delay);
					statusButton.setText("Pause");
					running = true;
				} else {
					handler.removeCallbacks(runnable);
					statusButton.setText("Resume");
					running = false;
				}



			}	

		});

		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				handler.removeCallbacks(runnable);	

				shots.setText("Finished!");
				shotsHad.setText("You have had " + no + " shots!");

				stopButton.setText("Quit!");
				stopButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						stop();					

					}	

				});

				statusButton.setText("Restart?");
				statusButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						setUp();				

					}	

				});


			}	

		});
		
		notification();
	}

	private void resume() {

		shots = (TextView) findViewById(R.id.shots);

		shotsHad = (TextView) findViewById(R.id.shotsHad);

		shots.setText(getResources().getString(R.string.shots) + " " + time);
		shotsHad.setText(getResources().getString(R.string.shotsHad) + " " + no);

		statusButton = (Button) findViewById(R.id.statusButton);
		statusButton.setText("Pause");

		stopButton = (Button) findViewById(R.id.stopButton);
		stopButton.setText("Stop");

		handler = new Handler();

		buzzer = MediaPlayer.create(this, R.raw.buzzer);

		statusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				notification();

				if(!running) {
					handler.postDelayed(runnable, delay);
					statusButton.setText("Pause");
					running = true;
				} else {
					handler.removeCallbacks(runnable);
					statusButton.setText("Resume");
					running = false;
				}



			}	

		});

		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				handler.removeCallbacks(runnable);	

				shots.setText("Finished!");
				shotsHad.setText("You have had " + no + " shots!");

				stopButton.setText("Quit!");
				stopButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						stop();					

					}	

				});

				statusButton.setText("Restart?");
				statusButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						setUp();				

					}	

				});


			}	

		});
	}



	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			/* do what you need to do */
			if(buzz()) {
				/* and here comes the "trick" */
				handler.postDelayed(this, delay);

			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time, menu);
		return true;
	}

	private void notification() {
		
		if(mNotificationManager != null)
			mNotificationManager.cancelAll();
		
		NotificationCompat.Builder mBuilder =

					new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle("ACSS CENTURION")
			.setContentText(no + "/" + shotsSet + " SHOTS DRANK!")
			.setOngoing(true);
			
			mNotificationManager =
					(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(0, mBuilder.build());

			
	}

	private boolean buzz() {
		buzzer.start();

		if(vibrator.hasVibrator()) {
			vibrator.vibrate(500);
		}

		time--;
		no++;

		notification();

		if(time > 0) {
			shots.setText(getResources().getString(R.string.shots) + " " + time);

			shotsHad.setText(getResources().getString(R.string.shotsHad) + " " + no);
			return true;
		}else {

			shots.setText("Finished!");
			shotsHad.setText("You have had " + no + " shots!");

			stopButton.setText("Quit!");
			stopButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					quit();					

				}	

			});


			statusButton.setText("Restart?");
			statusButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					setUp();				

				}	

			});

			return false;
		}
	}

	private void stop() {

		quit();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
			quit();

		}
		return super.onKeyDown(keyCode, event);
	}

	private void quit() {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Are you sure you want to quit?")
		.setMessage("Quit?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				handler.removeCallbacks(runnable);

				NotificationManager notificationManager;
				notificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				notificationManager.cancelAll();
				
				first = true;
				
				finish();    
			}

		})
		.setNegativeButton("No", null)
		.show();
	}

	@Override
	public void onDestroy() {
		Log.i("YO", "onDestroy called");

		handler.removeCallbacks(runnable);

		if(mNotificationManager != null)
			mNotificationManager.cancelAll();

		finish(); 
		
		super.onDestroy();
	}
	
}
