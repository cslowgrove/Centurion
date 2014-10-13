package com.acss.centurion;
/**
 *  The Main Class that is run on startup.
 *  Allows the user to test the buzzer and
 *  set the number of minutes to run for.
 */
import java.util.concurrent.TimeUnit;

import com.example.centurion.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private boolean finished = false; //toggles when timer has ended
	private long displayedTime ;
	private int drinkCounter = 1;

	private TextView timeLeft;
	private TextView drinkCounterLabel;
	private CountDownTimer counter;
	private Vibrator vibrator;
	private MediaPlayer buzzer;
	private EditText startTime;
	private int minutesToRun;

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
		
		Button startButton = (Button)findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startTime = (EditText) findViewById(R.id.startTime);
				if(startTime.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(), "Please enter in a time!", Toast.LENGTH_SHORT).show();
				} 
				else if (Integer.parseInt(startTime.getText().toString()) <= 0 ){
					
					Toast.makeText(getApplicationContext(), "Please enter in a time grater than 0 minutes!", Toast.LENGTH_SHORT).show();				
					
				} else {
					minutesToRun = Integer.parseInt(startTime.getText().toString());
					
					Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
					intent.putExtra("time", minutesToRun);
					startActivity(intent);
					
				}
				
			}
			
		});
		
	}
	public void setTextViews(){
		timeLeft.setText("Stopped");
		drinkCounterLabel.setText("Stopped");
	}
	
	public void stop(final View view){
		
		drinkCounter = 1;
		//isBreak = false;
		displayedTime = 0;
		finished = false;
		//endSound.stop();
		toActivityMain();
		counter.cancel();
	}

	
	public String getMillis(long milliseconds){
		String hms = String.format("%02d",
				
				TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
		return hms;
	}

		public void testBuzzer(final View view){
			buzzer.start();

		}
		
		public void run() {
			
		}
		/*public void run(final View view) {
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
		Toast.makeText(getApplicationContext(), startTime.getText().toString(), Toast.LENGTH_SHORT).show();
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
	}*/
}