package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FormativeCase extends ActionBarActivity {

	private int task = 0;

	private final TaskCallback formativeCallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("Formative", result.toString());
			int count = result.getInt("count");
			if (count == 0) {
				Toast toast = Toast.makeText(getApplicationContext(), "No case submitted", Toast.LENGTH_LONG);
				toast.show();
			} else {
				JSONArray results = result.getJSONArray("results");
				JSONObject markResults;
				if (task == 1) {
					markResults = results.getJSONObject(0);
				} else {
					markResults = results.getJSONObject(1);
				}
				Integer m1 = markResults.getInt("question_one_mark");
				Integer m2 = markResults.getInt("question_two_mark");
				Integer m3 = markResults.getInt("question_three_mark");
				String comment = markResults.getString("comment");

				tm1.setText(m1.toString());
				tm2.setText(m2.toString());
				tm3.setText(m3.toString());
				tcomments.setText(comment);

			}
		}
	};
	private TextView tm1 = null;
	private TextView tm2 = null;
	private TextView tm3 = null;
	private TextView tcomments = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formative_case);

		Intent intent = getIntent();
		task = intent.getIntExtra("CASE", 1);

		tm1 = (TextView) findViewById(R.id.m1);
		tm2 = (TextView) findViewById(R.id.m2);
		tm3 = (TextView) findViewById(R.id.m3);
		tcomments = (TextView) findViewById(R.id.comment);

		try {
			Api.getFormativeCase(formativeCallback);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Formative", e.toString());
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_formative_case, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
