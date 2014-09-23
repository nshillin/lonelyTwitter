package ca.ualberta.cs.lonelytwitter;


import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;

public class CounterActivity extends LonelyTwitterActivity {

	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		
		loadFromFile();
		if (tweets == null) {
			tweets = new ArrayList<LonelyTweetModel>();
		}
		
		TextView textView =  (TextView) findViewById( R.id.counter_textView);
		textView.setText("" + tweets.size());
		
	}

}
