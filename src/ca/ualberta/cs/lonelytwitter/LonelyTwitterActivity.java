package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	ArrayList<LonelyTweetModel> tweets;
	HashSet<String> values;
	private ArrayAdapter<LonelyTweetModel> adapter;
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		values = new HashSet<String>();
		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		Button clearButton = (Button) findViewById(R.id.clear);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
	//			saveInFile(text, new Date(System.currentTimeMillis()));
				tweets.add(new LonelyTweetModel(text));
				adapter.notifyDataSetChanged();
				saveInFile();

			}
		});
	
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	//	LonelyTweetModel[] tweets = loadFromFile();
		loadFromFile();
		if (tweets == null) {
			tweets = new ArrayList<LonelyTweetModel>();
		}
		adapter = new ArrayAdapter<LonelyTweetModel>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	public void counterActivity(MenuItem menu) {
		Intent archivedItemsScreen = new Intent(LonelyTwitterActivity.this, CounterActivity.class);
		startActivity(archivedItemsScreen);
	}
	

	protected void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis); 
			// From http://www.javacreed.com/simple-gson-example/ September 22, 2014
			Gson gson = new GsonBuilder().create();
			tweets = gson.fromJson(isr, new TypeToken<ArrayList<LonelyTweetModel>>(){}.getType());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			// From http://www.javacreed.com/simple-gson-example/ September 22, 2014
			Gson gson = new GsonBuilder().create();
			gson.toJson(tweets, osw);
			osw.flush();
			osw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}