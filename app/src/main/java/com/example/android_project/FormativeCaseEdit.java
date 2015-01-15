package com.example.android_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class FormativeCaseEdit extends ActionBarActivity {

	private final TaskCallback formativecallback = new TaskCallback() {
		@Override
		public void onTaskDone(JSONObject result) throws JSONException {
			Log.d("Formative", result.toString());
			Toast toast = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
	};
	private EditText m1 = null;
	private EditText m2 = null;
	private EditText m3 = null;
	private EditText comments = null;
	private int t = 0;
	private final TaskCallback getformativeCallback = new TaskCallback() {
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
				if (t == 1) {
					markResults = results.getJSONObject(0);
				} else {
					markResults = results.getJSONObject(1);
				}
				Integer im1 = markResults.getInt("question_one_mark");
				Integer im2 = markResults.getInt("question_two_mark");
				Integer im3 = markResults.getInt("question_three_mark");
				String comment = markResults.getString("comment");

				m1.setText(im1.toString());
				m2.setText(im2.toString());
				m3.setText(im3.toString());
				comments.setText(comment);

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formative_case_edit);
		Intent intent = getIntent();
		final int task = intent.getIntExtra("CASE", 1);
		t = task;

		Button submit = (Button) findViewById(R.id.buttonSubmit);
		m1 = (EditText) findViewById(R.id.m1);
		m2 = (EditText) findViewById(R.id.m2);
		m3 = (EditText) findViewById(R.id.m3);
		comments = (EditText) findViewById(R.id.comments);

		try {
			Api.getStudentFormativeCase(getformativeCallback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(FormativeCaseEdit.this);
				builder.setMessage("Confirm?");
				builder.setCancelable(true);
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {
									Api.setFormativeCase(task, Integer.parseInt(m1.getText().toString()), Integer.parseInt(m2.getText().toString()), Integer.parseInt(m3.getText().toString()), comments.getText().toString(), formativecallback);
								} catch (Exception e) {
									e.printStackTrace();
									Log.e("Formative", e.toString());
								}
							}
						});
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert = builder.create();
				alert.show();

			}
		});


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
