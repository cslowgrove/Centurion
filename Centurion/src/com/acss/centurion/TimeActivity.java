package com.acss.centurion;

import com.example.centurion.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends ActionBarActivity {

	private int time;
	private int no = 0;
	private TextView shots, shotsHad;
	private Button statusButton, stopButton;
	private Handler handler;
	private long delay = 1000;
	private MediaPlayer buzzer;
	private boolean running = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		
		Intent intent = getIntent();
		time = intent.getIntExtra("time", 100);
		
		shots = (TextView) findViewById(R.id.shots);
		
		shotsHad = (TextView) findViewById(R.id.shotsHad);
		
		shots.setText(getResources().getString(R.string.shots) + " " + time);
		shotsHad.setText(getResources().getString(R.string.shotsHad) + " " + no);
		
		statusButton = (Button) findViewById(R.id.statusButton);
		
		stopButton = (Button) findViewById(R.id.stopButton);
		
		handler = new Handler();
		
		buzzer = MediaPlayer.create(this, R.raw.buzzer);
		
		statusButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
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
				
				stop();
				
				
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
	
	private boolean buzz() {
		 buzzer.start();
		 time--;
		 no++;
		 
		 if(time > 0) {
			 shots.setText(getResources().getString(R.string.shots) + " " + time);

				shotsHad.setText(getResources().getString(R.string.shotsHad) + " " + no);
			 return true;
		 }else {
			 
			 shots.setText("Finished!");
			 return false;
		 }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void stop() {
		
		finish();
		
	}
}
