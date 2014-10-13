package com.acss.centurion;

import com.example.centurion.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends ActionBarActivity {

	private int time;
	private int no;
	private TextView shots, shotsHad;
	private Button statusButton, stopButton;
	private Handler handler;
	
	//How long between delays in miliseconds
	private long delay = 1000; //1 min
	private MediaPlayer buzzer;
	private boolean running;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		
		setUp();
		
	}
	
	private void setUp() {
		Intent intent = getIntent();
		time = intent.getIntExtra("time", 100);
		
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	    	handler.removeCallbacks(runnable);	
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
