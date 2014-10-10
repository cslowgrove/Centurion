package com.example.centurion;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

	private boolean finished = false; //toggles when timer has ended
	private long breakTime = 0; //milliseconds for each break
	private long studyTime = 0; //milliseconds for each study period
	private int loopCount = 0 ; //number of loops to complete
	private long totalTime = 0; // totAL TIME FOR APP TO RUN
	private boolean set = true;
	private long displayedTime ;
	private int drinkCounter = 1;

	//ProgressBar countDown;
	private TextView timeLeftName;
	private TextView timeLeft;
	private TextView drinkCounterLabel;
	private CountDownTimer counter;
	private Vibrator vibrator;
	private Boolean isBreak;
	private MediaPlayer buzzer;
	private Button stop;

	public void toActivityMain(){
		setContentView(R.layout.activity_main);



	}
	public void setActivityBackgroundColor(int color) {
		View view = this.getWindow().getDecorView();
		if (color == 1) // main menu
			view.setBackgroundColor(Color.WHITE);
		else if (color == 2) // study page
			view.setBackgroundColor(Color.rgb(0,176,52));
		else if (color == 3)// break page
			view.setBackgroundColor(Color.rgb(051,204,255));
		else if (color == 4)// finish screen
			view.setBackgroundColor(Color.rgb(255,0,0));
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		buzzer = MediaPlayer.create(this, R.raw.buzzer);

		displayedTime = 0;

		toActivityMain();
		
		timeLeft = (TextView)findViewById(R.id.timer);
		
		stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if((counter == null)){ 
					timeLeft.setText("Time Left: " + "00:00:00");
					counter.cancel();
				}else if(!(counter == null)){
					counter.cancel();
					timeLeft.setText("Time Left: " + "00:00:00");
				}
				else {
					counter.cancel();
					timeLeft.setText("Time Left: " + "00:00:00");
				}
			}
			
		});
		
	}
	public void setTextViews(){
		timeLeft.setText("Stopped");
		drinkCounterLabel.setText("Stopped");
	}

	public void stop(final View view){
		if(counter != null){
			drinkCounter = 1;
			set = true;
			//isBreak = false;
			displayedTime = 0;
			finished = false;
			counter.cancel();
			toActivityMain();
		}else{
			toActivityMain();
			counter.cancel();
		}
	}


	public String getMillis(long milliseconds){
		String hms = String.format("%02d",

				TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
		return hms;
	}

	public void testBuzzer(final View view){
		buzzer.start();

	}
	public void run(final View view) {
		drinkCounterLabel=(TextView)findViewById(R.id.drinkCounter);
		timeLeft=(TextView)findViewById(R.id.timer);
		timeLeft.setText("test");
		drinkCounterLabel.setText("0");
		drinkCounterLabel.setTextColor(Color.parseColor("#FF0000"));
		drinkCounterLabel.setTextSize(50);
		run2(view);
	}

	//start of countdown, when user presses the go button.
	public void run2(final View view) {
		buzzer.start();
		displayedTime = 6000;

		if (!finished){ // checks if program is finished

			counter = new CountDownTimer(displayedTime, 1000) {
				public void onTick(long millisUntilFinished) { // runs every "tick" - in this case every 1000ms - millisuntilfinished hold the current time to completion
					Log.i("Time",millisUntilFinished+"");
					String hms = getMillis(millisUntilFinished);

					String TD = getMillis(displayedTime);
					displayedTime = displayedTime - 1000;
					timeLeft.setText("Total Time Left: "+ " \n " + "      "+TD );

					//loopCountValue.setText("Loops Remaining: "+loopCount+".");

				}

				public void onFinish() { // runs when countdown is finished - this nows runs a break period
					// vibrator.vibrate(1000);

					Handler handler = new Handler(); 
					handler.postDelayed(new Runnable() { 
						public void run()
						{ setContentView(R.layout.drink);
						} 
					}, 2000); 
					setContentView(R.layout.activity_main);
					drinkCounterLabel.setText(drinkCounter+"");
					buzzer.start();
					drinkCounter++;	
					//countDown.setProgress(drinkCounter);
					if (drinkCounter > 100){
						finished = true;
					}

					if (!finished){run2(view);} // if finished = false, run "run" method again
					//(this loops the countdowns repeatedly unitll the loop counter reaches -1
				} 

			}.start(); // starts countdown (i think)
		}
	}
}